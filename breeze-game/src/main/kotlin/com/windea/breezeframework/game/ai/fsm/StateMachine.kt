package com.windea.breezeframework.game.ai.fsm

import java.util.*
import java.util.concurrent.*

/**状态机。*/
open class StateMachine<S : State<S>>(
	val initialState: S,
	val finalizeState: S
) {
	/**已激活的状态列表。*/
	val activeStates: CopyOnWriteArrayList<S> = CopyOnWriteArrayList()
	
	/**当前的状态。*/
	val currentState: S
		get() = if(currentSubStates.isNotEmpty()) currentSubStates.last else currentParentState
	
	/**当前的父状态。*/
	var currentParentState: S = initialState
		private set(value) {
			require(!value.isSubState) { "Parent state '$value' cannot be a subState." }
			field = value
		}
	
	/**当前的子状态。*/
	val currentSubStates = ArrayDeque<S>()
	
	init {
		currentParentState = initialState
		initialState.onCreate()
		activeStates += currentParentState
	}
	
	/**适用已激活的状态。*/
	fun runActiveStates(action: (S) -> Unit) {
		activeStates.forEach { action(it) }
	}
	
	/**改变当前状态。*/
	fun changeState(nextState: S) {
		when {
			//父状态 -> 子状态
			currentSubStates.isEmpty() && nextState.isSubState -> {
				val previousState = currentState
				nextState.onCreate()
				previousState.onExit(nextState)
				currentSubStates.addLast(nextState)
				nextState.onEnter(currentParentState)
			}
			//父状态 -> 父状态
			currentSubStates.isEmpty() -> {
				val prevState = currentState
				nextState.onCreate()
				prevState.onExit(nextState)
				currentParentState = nextState
				nextState.onEnter(prevState)
				prevState.onDestroy()
			}
			//子状态 -> 子状态
			currentSubStates.isEmpty() && nextState.isSubState -> {
				val previousState = currentState
				nextState.onCreate()
				previousState.onExit(nextState)
				currentSubStates.addLast(nextState)
				nextState.onEnter(previousState)
			}
			//子状态 -> 父状态
			//NOTE 子状态已被激活时，不允许从子状态切换到父状态
			else -> return
		}
		
		updateActiveStates()
	}
	
	/**出栈子状态。*/
	fun popSubState(): Boolean {
		if(currentSubStates.isEmpty()) return false
		
		val previousState = currentState
		val nextState = if(currentSubStates.size == 1) currentParentState else currentSubStates.elementAt(currentSubStates.size - 2)
		
		previousState.onExit(nextState)
		currentSubStates.removeLast()
		nextState.onEnter(previousState)
		previousState.onDestroy()
		
		updateActiveStates()
		return true
	}
	
	/**更新已激活的状态列表。*/
	private fun updateActiveStates() {
		activeStates.clear()
		
		if(currentSubStates.isEmpty()) {
			activeStates += currentParentState
			return
		}
		
		for(s in currentSubStates.reversed()) {
			if(s.isConcurrent) {
				activeStates += s
			} else {
				activeStates += s
				break
			}
		}
		
		//如果仅第一个子状态允许并发，则添加父状态
		if(currentSubStates.first.isConcurrent) activeStates += currentParentState
		
		activeStates.reverse()
	}
}
