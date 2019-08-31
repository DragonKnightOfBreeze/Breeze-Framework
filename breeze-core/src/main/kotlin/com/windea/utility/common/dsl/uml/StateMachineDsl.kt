package com.windea.utility.common.dsl.uml

import com.windea.utility.common.dsl.*

//DELAY

data class StateMachineDsl<T> @PublishedApi internal constructor(
	override val name: String,
	override val target: T
) : Dsl, StateMachineDslElement<T> {
	override fun toString(): String {
		return super.toString()
	}
}

object StateMachineDslConfig {
	const val defaultName: String = "stateMachine"
	const val defaultTransitionName: String = "unnamed transition"
}


@DslMarker
annotation class StateMachineDslMarker


@StateMachineDslMarker
interface StateMachineDslElement<T> {
	val target: T
}


interface StateMachineScope<T> : StateMachineDslElement<T> {
	val name: String
	val events: MutableList<StateMachineEvent<T>>
	val guards: MutableList<StateMachineGuard<T>>
	val options: MutableList<StateMachineOption<T>>
}

interface StateMachineState<T> : StateMachineDslElement<T> {
	val name: String
	var action: StateMachineAction<T>
	val options: MutableList<StateMachineOption<T>>
	val subStates: MutableList<StateMachineState<T>>
}

interface StateMachineTransition<T> : StateMachineDslElement<T> {
	val name: String
	var fromState: StateMachineState<T>
	var toState: StateMachineState<T>
	val events: MutableList<StateMachineEvent<T>>
	val guards: MutableList<StateMachineGuard<T>>
	val options: MutableList<StateMachineOption<T>>
}

interface StateMachineAction<T> : StateMachineDslElement<T> {
	val execution: (T) -> Unit
}

interface StateMachineEvent<T> : StateMachineDslElement<T> {
	val execution: (T) -> Boolean
}

interface StateMachineGuard<T> : StateMachineDslElement<T> {
	val execution: (T) -> Boolean
}

interface StateMachineOption<T> : StateMachineDslElement<T> {
	val execution: (T) -> Unit
}
