package com.sinosoft.one.monitor.controllers;

import java.lang.annotation.*;

/**
 * 这是一个annotation，所谓annotation就是一个“标签”，他的职责是“表明”
 * 至于表明之后该怎么样？则由其他代码来处理
 * 对于LoginRequired具体的处理代码是LoginCheckInterceptor.java
 */
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRequired {

}
