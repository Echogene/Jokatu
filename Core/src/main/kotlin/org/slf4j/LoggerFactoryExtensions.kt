package org.slf4j

import kotlin.reflect.KClass


fun getLogger(clazz: KClass<*>) = LoggerFactory.getLogger(clazz.java)!!