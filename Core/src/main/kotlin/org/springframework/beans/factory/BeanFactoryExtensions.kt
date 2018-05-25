package org.springframework.beans.factory

import kotlin.reflect.KClass


fun <T: Any> ListableBeanFactory.getBeansOfType(clazz: KClass<T>) = getBeansOfType(clazz.java).values