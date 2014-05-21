package com.sinosoft.monitor.agent.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentWeakDictionary<K, V> {
	Fragment<K, V>[] fragments;
	static final int MAX_CONCURRENCY_LEVEL = 65536;
	int fragmentShift;
	int fragmentMask;

	public ConcurrentWeakDictionary(int initialCap, float loadFactor, int concurrencyLevel) {
		if (concurrencyLevel > 65536) {
			concurrencyLevel = 65536;
		}

		int partition = 1;
		int partitionShift = 0;

		while (partition < concurrencyLevel) {
			partition <<= 1;
			partitionShift++;
		}
		this.fragmentShift = (32 - partitionShift);
		this.fragmentMask = (partition - 1);
		this.fragments = new Fragment[partition];

		int roughCapacity = initialCap / partition;
		if (roughCapacity * partition < initialCap) {
			roughCapacity++;
		}
		int fragmentCapacity = 1;
		while (fragmentCapacity < roughCapacity) {
			fragmentCapacity <<= 1;
		}

		for (int i = 0; i < partition; i++)
			this.fragments[i] = new Fragment(fragmentCapacity, loadFactor);
	}

	public V put(K key, V value) {
		if (key == null) {
			return null;
		}
		int hash = hash(key);
		return getFragment(hash).put(key, value, hash, false);
	}

	public boolean putIfNotExists(K key, V value) {
		if (key == null) {
			return false;
		}
		int hash = hash(key);
		return getFragment(hash).put(key, value, hash, true) == null;
	}

	public V get(Object key) {
		if (key == null) {
			return null;
		}
		int hash = hash(key);
		return getFragment(hash).get(key, hash);
	}

	public V remove(Object key) {
		int hash = hash(key);
		return getFragment(hash).remove(key, hash);
	}

	Fragment<K, V> getFragment(int hash) {
		return this.fragments[(hash >>> this.fragmentShift & this.fragmentMask)];
	}

	static int hash(Object x) {
		int h = x.hashCode();
		h += (h << 9 ^ 0xFFFFFFFF);
		h ^= h >>> 14;
		h += (h << 4);
		h ^= h >>> 10;
		return h;
	}

	public int size() {
		int size = 0;
		for (int i = this.fragments.length - 1; i >= 0; i--) {
			size += this.fragments[i].count;
		}
		return size;
	}

	public boolean remove(Object key, Object value) {
		return remove(key) != null;
	}

	public void clear() {
		for (int i = this.fragments.length; i >= 0; i--)
			this.fragments[i].clear();
	}

	public String toString() {
		return Arrays.deepToString(this.fragments);
	}

	static final class Element<K, V> extends WeakReference<K> {
		V value;
		int hash;
		Element<K, V> next;

		Element(K key, V value, int hash, ReferenceQueue<K> queue) {
			super((K) queue);
			this.hash = hash;
			this.value = value;
			this.next = null;
		}

		public String toString() {
			String str = "E_" + get() + "@  ";
			Element next = this.next;
			while (next != null) {
				str = str + "E_" + next.get() + "@  ";
				next = next.next;
			}
			return str;
		}
	}

	static final class Fragment<K, V> {
		ReferenceQueue<K> refQ;
		ReentrantLock locker;
		ConcurrentWeakDictionary.Element<K, V>[] elements;
		int count;
		int threshold;
		float loadFactor;
		static int ROOM_INCREMENT_SLAB = 1;

		Fragment(int initalCap, float loadFactor) {
			this.elements = new ConcurrentWeakDictionary.Element[initalCap];
			this.loadFactor = loadFactor;
			this.threshold = ((int) (initalCap * loadFactor));
			this.count = 0;
			this.refQ = new ReferenceQueue();
			this.locker = new ReentrantLock(false);
		}

		V put(K key, V value, int keyHash, boolean putIfNotExists) {
			this.locker.lock();
			try {
				wipeoutStaleElements();

				if (this.count + 1 >= this.threshold) {
					makeRoom(this.elements.length << ROOM_INCREMENT_SLAB);
				}

				int index = computeIndex(keyHash, this.elements.length - 1);
				ConcurrentWeakDictionary.Element existingEle = this.elements[index];
				Object localObject1;
				while (existingEle != null) {
					if ((keyHash == existingEle.hash) && (checkEq(key, existingEle.get()))) {
						Object oldValue = existingEle.value;
						if (!putIfNotExists) {
							existingEle.value = value;
						}
						return (V) oldValue;
					}
					existingEle = existingEle.next;
				}

				ConcurrentWeakDictionary.Element element = new ConcurrentWeakDictionary.Element(key, value, keyHash, this.refQ);
				element.next = this.elements[index];
				this.count += 1;
				this.elements[index] = element;
				return null;
			} finally {
				this.locker.unlock();
			}
		}

		V get(Object key, int hash) {
			if (this.count == 0) {
				return null;
			}

			ConcurrentWeakDictionary.Element[] elements = this.elements;

			int index = computeIndex(hash, elements.length - 1);
			ConcurrentWeakDictionary.Element e = elements[index];
			while (e != null) {
				if ((hash == e.hash) && (checkEq(key, e.get()))) {
					return (V) e.value;
				}
				e = e.next;
			}
			return null;
		}

		V remove(Object key, int hash) {
			if (this.count == 0) {
				return null;
			}
			this.locker.lock();
			try {
				int index = computeIndex(hash, this.elements.length - 1);
				ConcurrentWeakDictionary.Element e = this.elements[index];
				ConcurrentWeakDictionary.Element prev = e;
				Object localObject1;
				while (e != null) {
					if ((hash == e.hash) && (checkEq(key, e.get()))) {
						if (prev == e) {
							this.elements[index] = e.next;
						} else {
							prev.next = e.next;
						}
						this.count -= 1;
						return (V) e.value;
					}
					prev = e;
					e = e.next;
				}
				return null;
			} finally {
				this.locker.unlock();
			}
		}

		void clear() {
			if (this.count == 0) {
				return;
			}
			this.locker.lock();
			try {
				for (int i = this.elements.length; i >= 0; i--) {
					this.elements[i] = null;
				}
				this.count = 0;
			} finally {
				this.locker.unlock();
			}
		}

		void makeRoom(int newCapacity) {
			ConcurrentWeakDictionary.Element[] elements = new ConcurrentWeakDictionary.Element[newCapacity];
			transferData(this.elements, elements);
			this.threshold = ((int) (this.loadFactor * newCapacity));
			this.elements = elements;
		}

		void transferData(ConcurrentWeakDictionary.Element<K, V>[] src, ConcurrentWeakDictionary.Element<K, V>[] dest) {
			int mask = dest.length - 1;

			for (int i = src.length - 1; i >= 0; i--) {
				ConcurrentWeakDictionary.Element e = src[i];
				while (e != null) {
					int index = computeIndex(e.hash, mask);
					ConcurrentWeakDictionary.Element next = e.next;
					e.next = dest[index];
					dest[index] = e;
					e = next;
				}
			}
		}

		void wipeoutStaleElements() {
			ConcurrentWeakDictionary.Element[] elements = this.elements;
			int size = this.count;

			int mask = elements.length - 1;
			ConcurrentWeakDictionary.Element qElement;
			while ((qElement = (ConcurrentWeakDictionary.Element) this.refQ.poll()) != null) {
				int index = computeIndex(qElement.hash, mask);
				ConcurrentWeakDictionary.Element ele = elements[index];
				ConcurrentWeakDictionary.Element prev = ele;
				while (ele != null) {
					ConcurrentWeakDictionary.Element next = ele.next;
					if (ele == qElement) {
						if (prev == ele) {
							elements[index] = next;
						} else {
							prev.next = next;
						}
						size--;

						ele.value = null;
						ele.next = null;
						break;
					}
					prev = ele;
					ele = ele.next;
				}
			}
			this.count = size;
			this.elements = elements;
		}

		int computeIndex(int hash, int mask) {
			return hash & mask;
		}

		boolean checkEq(Object o1, Object o2) {
			return o1 == o2 ? true : o1.equals(o2);
		}

		public String toString() {
			return Arrays.deepToString(this.elements);
		}
	}
}
