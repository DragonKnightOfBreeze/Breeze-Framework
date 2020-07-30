fun dsl(block:Dsl.() -> Unit) = Dsl().apply(block)

class Dsl {
	val nodes = mutableListOf<Node>()

	operator fun String.invoke() = Node(this).also { nodes += it }
	operator fun String.invoke(block:Node.() -> Unit) = Node(this).apply(block).also { nodes += it }
	override fun toString() = nodes.joinToString("\n", "", "\n")
}

class Node(
	val content:String
) {
	val nodes = mutableListOf<Node>()

	operator fun String.invoke() = Node(this).also { nodes += it }
	operator fun String.invoke(block:Node.() -> Unit) = Node(this).apply(block).also { nodes += it }
	override fun toString() = when {
		nodes.isEmpty() -> content
		else -> "$content\n{${nodes.joinToString("\n").prependIndent("  ")}\n}"
	}
}

