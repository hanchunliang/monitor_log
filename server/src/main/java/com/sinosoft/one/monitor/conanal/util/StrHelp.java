package com.sinosoft.one.monitor.conanal.util;

import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: king-bj
 * Date: 13-11-5
 * Time: 下午2:57
 * To change this template use File | Settings | File Templates.
 */
public class StrHelp {
    public static String getChinese(String s) {
        try {
            return new String(s.getBytes("utf-8"), "iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }
}
