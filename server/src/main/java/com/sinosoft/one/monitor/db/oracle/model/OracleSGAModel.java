package com.sinosoft.one.monitor.db.oracle.model;


/**
 * User: Chunliang.Han
 * Date: 13-2-28
 * Time: 上午10:03
 */

/*
缓冲区大小(Buffer Cache Size) 	388.0 	Unknown
共享池大小(Shared Pool Size) 	117.73 	Unknown
RedoLog缓冲区(Redo Buffers) 	6.84 	Unknown
库缓存的大小(library cache) 	7.58 	Unknown
数据字典缓存大小(sga dev dict) 	0.0 	Unknown
SQL区大小(sql area) 14.37 	Unknown
固有区大小(Fixed SGA Size) 	1.16 	Unknown
Java池大小(Java Pool Size) 	4 	Unknown
Large池大小(Large Pool Size) 	8 	Unknown
 */
public class OracleSGAModel {
    /**
     * 缓冲区大小
     */
    private String bufferCacheSize;
    /**
     * 共享池大小
     */
     private String sharePoolSize;
    /**
     * RedoLog缓冲区
     */
    private String redoLogCacheSize;
    /**
     * 库缓存的大小
     */
    private String libCacheSize;
    /**
     * 数据字典缓存大小
     */
    private String dictSize;
    /**
     * SQL区大小
     */
    private String sqlAreaSize;
    /**
     * 固有区大小
     */
    private String fixedSGASize;
    /**
     * Java池大小
     */
     private String javaPoolSize;
    /**
     * Large池大小
     */
    private String largePoolSize;

    public String getBufferCacheSize() {
        return bufferCacheSize;
    }

    public void setBufferCacheSize(String bufferCacheSize) {
        this.bufferCacheSize = bufferCacheSize;
    }

    public String getSharePoolSize() {
        return sharePoolSize;
    }

    public void setSharePoolSize(String sharePoolSize) {
        this.sharePoolSize = sharePoolSize;
    }

    public String getRedoLogCacheSize() {
        return redoLogCacheSize;
    }

    public void setRedoLogCacheSize(String redoLogCacheSize) {
        this.redoLogCacheSize = redoLogCacheSize;
    }

    public String getLibCacheSize() {
        return libCacheSize;
    }

    public void setLibCacheSize(String libCacheSize) {
        this.libCacheSize = libCacheSize;
    }

    public String getDictSize() {
        return dictSize;
    }

    public void setDictSize(String dictSize) {
        this.dictSize = dictSize;
    }

    public String getSqlAreaSize() {
        return sqlAreaSize;
    }

    public void setSqlAreaSize(String sqlAreaSize) {
        this.sqlAreaSize = sqlAreaSize;
    }

    public String getFixedSGASize() {
        return fixedSGASize;
    }

    public void setFixedSGASize(String fixedSGASize) {
        this.fixedSGASize = fixedSGASize;
    }

    public String getJavaPoolSize() {
        return javaPoolSize;
    }

    public void setJavaPoolSize(String javaPoolSize) {
        this.javaPoolSize = javaPoolSize;
    }

    public String getLargePoolSize() {
        return largePoolSize;
    }

    public void setLargePoolSize(String largePoolSize) {
        this.largePoolSize = largePoolSize;
    }
}
