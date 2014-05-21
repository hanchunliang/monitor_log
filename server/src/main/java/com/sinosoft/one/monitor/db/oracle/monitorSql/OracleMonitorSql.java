package com.sinosoft.one.monitor.db.oracle.monitorSql;

/**
 * User: Chunliang.Han
 * Date: 13-3-1
 * Time: 下午12:12
 * 装载监控相关的SQL语句
 */
public interface OracleMonitorSql {
    /**
     * 数据库驱动
     */
    public static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    /**
     * 获取版本信息和启动时间
     */
    public static final String versionAndStartUpTime = "Select a.version VERSIONLABLE, b.startup_time STARTUPTIME FROM Product_component_version a,v$instance b Where SUBSTR(PRODUCT,1,6)='Oracle'" ;
    /**
     * 数据库明细（数据库创建时间，open模式，log模式）
     */
    public static final String dbInfo ="select CREATED,OPEN_MODE,LOG_MODE from v$database";
    /**
     * 表空间详细信息
     */
    public static final String tableSpaceInfo ="SELECT D.TABLESPACE_NAME  \"tableSpaceName\",\n" +
            "\t\t\t\t   SPACE \"totalSize\",\n" +
            "\t\t\t\t   BLOCKS  \"totalBlock\",\n" +
            "\t\t\t\t   USED_SPACE \"used\",\n" +
            "\t\t\t\t   ROUND(NVL(USED_SPACE, 0) / SPACE * 100, 2) \"usedRate\",\n" +
            "\t\t\t\t   NVL(FREE_SPACE, 0) \"unused\",\n" +
            "\t\t\t\t   ROUND(NVL(FREE_SPACE, 0) / SPACE * 100, 2) \"unusedRate\"\n" +
            "\n" +
            "\t\t\t  FROM (SELECT TABLESPACE_NAME,\n" +
            "\t\t\t\t\t\t   ROUND(SUM(BYTES) / (1024 * 1024), 2) SPACE,\n" +
            "\t\t\t\t\t\t   SUM(BLOCKS) BLOCKS\n" +
            "\t\t\t\t\t  FROM DBA_TEMP_FILES\n" +
            "\t\t\t\t\t GROUP BY TABLESPACE_NAME) D,\n" +
            "\t\t\t\t   (SELECT TABLESPACE_NAME,\n" +
            "\t\t\t\t\t\t   ROUND(SUM(BYTES_USED) / (1024 * 1024), 2) USED_SPACE,\n" +
            "\t\t\t\t\t\t   ROUND(SUM(BYTES_FREE) / (1024 * 1024), 2) FREE_SPACE\n" +
            "\t\t\t\t\t  FROM V$TEMP_SPACE_HEADER\n" +
            "\t\t\t\t\t GROUP BY TABLESPACE_NAME) F\n" +
            "\t\t\t WHERE D.TABLESPACE_NAME = F.TABLESPACE_NAME(+)";
    /**
     * sga详细信息一(缓冲区大小 共享池大小 redolog缓冲区 固有区大小 java池大小 large池大小)
     */
    public static final String   sgaInfo1 = "select name \"name\",bytes/1024/1024 \"bytes\" from v$sgainfo where name in('Fixed SGA Size','Redo Buffers','Buffer Cache Size','Shared Pool Size','Large Pool Size','Java Pool Size')";
    /**
     * sga详细信息二（数据字典大小，库缓存大小，sql区大小）
     */
    public static final String  sgaInfo2 ="select name \"name\",bytes/1024/1024 \"bytes\" from v$sgastat where name in( 'sql area' ,'library cache','sga dev dict')";
    /**
     * 缓冲器击中率：bufferRatio
     */
    public  static final String bufferRatio = "SELECT 1 - (PHYSICAL_READS / (DB_BLOCK_GETS + CONSISTENT_GETS)) \"Hit Ratio\" FROM V$BUFFER_POOL_STATISTICS WHERE NAME='DEFAULT'";
    /**
     * 数据字典击中率：dictionaryRatio
     */
     public static final String dictionaryRatio = "select sum(gets-getmisses-usage-fixed)/sum(gets) \"dictRatio\" from v$rowcache";
    /**
     * 库击中率：libraryRatio
     */
    public static final String libraryRatio = "SELECT SUM(pinhits)/sum(pins) \"libHitRatio\" FROM V$LIBRARYCACHE";
    /**
     * SGA可用内存：sgaFreeMemory
     */
    public static final String sgaFreeMemory = "select sum(bytes / 1024)/1024　\"free memory\" from (select sum(bytes) sgasize from sys.v_$sgastat) s, sys.v_$sgastat f where f.name = 'free memory'";
    /**
     * 用户数查询:avtiveCount
     */
    public static final String activeCount = "select count(distinct terminal) \"active\" from v$session";
    /**
     * 获取操作系统名称：
     */
    public static final String osName = "select platform_name \"platform_name\"  from v$database";
}