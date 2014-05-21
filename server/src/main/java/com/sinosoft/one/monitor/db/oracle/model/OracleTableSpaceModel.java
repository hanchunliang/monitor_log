package com.sinosoft.one.monitor.db.oracle.model;

import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * User: Chunliang.Han
 * Date: 13-2-27
 * Time: 下午10:33
 * 表空间信息对象
 */
/*
表空间名称
总空间大小
总模块数
已使用
使用率
未使用
未使用率
表空间状态
 */
public class OracleTableSpaceModel {
	
	private String id;
	@Transient
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String statusBar = "<div class='bg_bar'><div class='red_bar' style='width:10%'></div></div>";
	@Transient
	public String getStatusBar() {
		return statusBar;
	}

	public void setStatusBar(String statusBar) {
		this.statusBar = statusBar;
	}

	/**
     * 表空间名称
     */
    private String tableSpaceName;
    /**
     * 总空间大小
     */
    private BigDecimal totalSize;
    /**
     * 总模块数
     */
    private BigDecimal totalBlock;
    /**
     * 已使用
     */
    private BigDecimal used;
    /**
     * 使用率
     */
    private BigDecimal usedRate;
    /**
     * 未使用
     */
    private BigDecimal unused;
    /**
     * 未使用率
     */
    private BigDecimal unusedRate;


    public String getTableSpaceName() {
        return tableSpaceName;
    }

    public void setTableSpaceName(String tableSpaceName) {
        this.tableSpaceName = tableSpaceName;
    }

    public BigDecimal getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(BigDecimal totalSize) {
        this.totalSize = totalSize;
    }

    public BigDecimal getTotalBlock() {
        return totalBlock;
    }

    public void setTotalBlock(BigDecimal totalBlock) {
        this.totalBlock = totalBlock;
    }

    public BigDecimal getUsed() {
        return used;
    }

    public void setUsed(BigDecimal used) {
        this.used = used;
    }

    public BigDecimal getUsedRate() {
        return usedRate;
    }

    public void setUsedRate(BigDecimal usedRate) {
        this.usedRate = usedRate;
    }

    public BigDecimal getUnused() {
        return unused;
    }

    public void setUnused(BigDecimal unused) {
        this.unused = unused;
    }

    public BigDecimal getUnusedRate() {
        return unusedRate;
    }

    public void setUnusedRate(BigDecimal unusedRate) {
        this.unusedRate = unusedRate;
    }

}
