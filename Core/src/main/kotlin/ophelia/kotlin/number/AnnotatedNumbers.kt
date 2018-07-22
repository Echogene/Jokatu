package ophelia.kotlin.number

import java.util.*
import java.util.stream.Collectors


interface AnnotatedInt {
	val number: Int
	val annotation: String
}

class IntFrom(override val number: Int, from: String): AnnotatedInt {
	override val annotation = "$number $from"
}

class AnnotatedSum(vararg summands: AnnotatedInt): AnnotatedInt {
	override val number = summands.sumBy { it.number }

	override val annotation = when {
		summands.isEmpty() -> "0, the empty sum"
		summands.size == 1 -> summands[0].annotation
		else -> "$number, the sum of" +
				Arrays.stream(summands)
						.map { "\t${it.annotation}" }
						.collect(Collectors.joining("\n"))
	}
}