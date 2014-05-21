package com.sinosoft.one.monitor.db.oracle.model;

/**
 * User: Chunliang.Han
 * Date: 13-2-28
 * Time: 下午2:01
 */
/*
缓冲区击中率 	99 	Unknown
数据字典击中率 	97 % 	Unknown
缓存库击中率 	97 % 	Unknown
可用内存 	20.76 MB 	Unknown
 */
public class SGAStateModel {
    /**
     * 缓冲区命中率
     */
    private String bufferHitRate;
    /**
     * 数据字典命中率
     */
    private String dictHitRate;
    /**
     * 缓存库命中率
     */
    private String libHitRate;
    /**
     * 可用内存
     */
    private String unusedCache;

    public String getBufferHitRate() {
        return bufferHitRate;
    }

    public void setBufferHitRate(String bufferHitRate) {
        this.bufferHitRate = bufferHitRate;
    }

    public String getDictHitRate() {
        return dictHitRate;
    }

    public void setDictHitRate(String dictHitRate) {
        this.dictHitRate = dictHitRate;
    }

    public String getLibHitRate() {
        return libHitRate;
    }

    public void setLibHitRate(String libHitRate) {
        this.libHitRate = libHitRate;
    }

    public String getUnusedCache() {
        return unusedCache;
    }

    public void setUnusedCache(String unusedCache) {
        this.unusedCache = unusedCache;
    }
}
