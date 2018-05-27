package reflect

import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf


@Suppress("UNCHECKED_CAST")
fun <T: Any> KClass<*>.asSubclass(base: KClass<T>) = when {
	isSubclassOf(base) -> this as KClass<out T>
	else -> throw ClassCastException("$simpleName is not castable as a subclass of ${base.simpleName}.")
}