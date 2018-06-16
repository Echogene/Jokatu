package test

import org.junit.Assert.fail

fun assertFailure(runnable: Runnable) {
	try {
		runnable.run()
		fail()
	} catch (ignore: Exception) {}
}


fun assertFailure(runnable: () -> Any) {
	try {
		runnable()
		fail()
	} catch (ignore: Exception) {}
}