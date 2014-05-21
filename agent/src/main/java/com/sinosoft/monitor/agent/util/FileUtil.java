package com.sinosoft.monitor.agent.util;

import com.sinosoft.monitor.agent.logging.APMInsightLogger;

import java.io.*;
import java.util.logging.Level;

public class FileUtil {
	private static final int FILEBUFSIZE = 128;
	private static FileUtil fSingleton;

	public static synchronized FileUtil getInstance() {
		if (fSingleton == null) {
			fSingleton = new FileUtil();
		}
		return fSingleton;
	}

	public String readCharacterFile(String fileName)
			throws IOException {
		if (fileName == null) {
			throw new IllegalArgumentException("supplied filename to FileUtil was null");
		}
		return readCharacterFile(new FileReader(fileName));
	}

	public String readCharacterFile(File theFile)
			throws IOException {
		if (theFile == null) {
			throw new IllegalArgumentException("supplied File object to FileUtil was null");
		}
		return readCharacterFile(new FileReader(theFile));
	}

	public String readCharacterFile(Reader theReader)
			throws IOException {
		if (theReader == null) {
			throw new IllegalArgumentException("supplied Reader object to FileUtil was null");
		}

		try {
			StringWriter sw = new StringWriter();
			char[] text = new char[''];
			int n;
			while ((n = theReader.read(text, 0, 128)) > 0) {
				sw.write(text, 0, n);
			}
			return sw.toString();
		} finally {
			if (theReader != null) {
				theReader.close();
			}
		}
	}

	public String readCharacterFile(InputStream inputStream)
			throws IOException {
		if (inputStream == null) {
			throw new IllegalArgumentException("supplied InputStream object to FileUtil was null");
		}

		try {
			return readCharacterFile(new InputStreamReader(inputStream));
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	public void writeCharacterFile(String fileName, String contents)
			throws IOException {
		if (fileName == null) {
			throw new IllegalArgumentException("Supplied filename to FileUtil was null");
		}
		writeCharacterFile(new FileWriter(fileName), contents);
	}

	public void writeCharacterFile(File file, String contents)
			throws IOException {
		if (file == null) {
			throw new IllegalArgumentException("Supplied File object to FileUtil was null");
		}
		writeCharacterFile(new FileWriter(file), contents);
	}

	public void writeCharacterFile(Writer fileWriter, String contents)
			throws IOException {
		if (fileWriter == null) {
			throw new IllegalArgumentException("supplied FileWriter to FileUtil was null");
		}

		try {
			StringReader sr = new StringReader(contents);
			char[] text = new char[''];
			int n;
			while ((n = sr.read(text, 0, 128)) > 0) {
				fileWriter.write(text, 0, n);
			}
		} finally {
			if (fileWriter != null) {
				fileWriter.close();
			}
		}
	}

	public void createDirectory(String name) throws Exception {
		try {
			File f = new File(name);
			if (!f.exists()) {
				f.mkdir();
			}
		} catch (Exception ex) {
			APMInsightLogger.getLogger().log(Level.WARNING, "Failed to create the directory '" + name + "'to store traces of slow transactions");
			throw new RuntimeException("Check read write permisions for the directory '" + name + "'", ex);
		}
	}

	public void deleteDirectory(String name) {
		if (name == null) {
			APMInsightLogger.getLogger().warning("Directory path to be deleted can't be null");
			return;
		}
		deleteDirectory(new File(name));
	}

	public void deleteDirectory(File dirName) {
		if (dirName == null) {
			APMInsightLogger.getLogger().warning("Directory name to be deleted can't be null");
			return;
		}

		try {
			cleanDirectory(dirName);
			if ((dirName.exists()) && (!dirName.delete())) {
				APMInsightLogger.getLogger().log(Level.WARNING, "Unable to delete the traces directory " + dirName);
			}
		} catch (Exception e) {
			APMInsightLogger.getLogger().log(Level.WARNING, "Exception while deleting the traces directory " + dirName, e);
		}
	}

	private void cleanDirectory(File cleanDir) {
		if (cleanDir.isDirectory()) {
			File[] innerFilesAndFolders = cleanDir.listFiles();
			if (innerFilesAndFolders != null) {
				for (File innerFileOrFolder : innerFilesAndFolders) {
					if (innerFileOrFolder.isDirectory()) {
						deleteDirectory(innerFileOrFolder.getPath());
					} else {
						innerFileOrFolder.delete();
					}
				}
			}
		}
	}
}
