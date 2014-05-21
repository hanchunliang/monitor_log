package com.sinosoft.one.monitor.utils;

import java.lang.annotation.ElementType;
import javax.validation.Path;
import javax.validation.TraversableResolver;


public class ExtTraversableResolver implements TraversableResolver {


	public final boolean isReachable(Object traversableObject, Path.Node traversableProperty, Class<?> rootBeanType, Path pathToTraversableObject, ElementType elementType) {
		return true;
	}


	public final boolean isCascadable(Object traversableObject, Path.Node traversableProperty, Class<?> rootBeanType, Path pathToTraversableObject, ElementType elementType) {
		return true;
	}
}