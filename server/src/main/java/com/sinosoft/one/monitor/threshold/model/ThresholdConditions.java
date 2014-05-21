package com.sinosoft.one.monitor.threshold.model;

/**
 * 阈值条件枚举类.
 * User: carvin
 * Date: 13-3-1
 * Time: 下午6:25
 */
public enum ThresholdConditions {
	EQ("=", "!="),
	LT("<", ">="),
	GT(">", "<="),
	LE("<=", ">"),
	GE(">=", "<"),
	NE("!=", "=");

	private String _symbol;
	private String _revertSymbol;

	private ThresholdConditions(String symbol, String revertSymbol) {
		this._symbol = symbol;
		this._revertSymbol = revertSymbol;
	}

	public String symbol() {
		return _symbol;
	}

	public String revertSymbol() {
		return _revertSymbol;
	}
}
