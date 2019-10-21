package com.windea.breezeframework.dsl.text

import com.windea.breezeframework.dsl.text.CommandLineColor.*
import org.junit.*

class CommandLineTextDsl {
	@Test //TESTED OK
	fun test1() {
		commandLineText {
			color(Red, "123").toString()
		}.also { println(it) }
	}
	
	@Test //TESTED OK
	fun test2() {
		commandLineText {
			bgColor(Red, "123").toString()
		}.also { println(it) }
	}
	
	@Test //TESTED OK
	fun test3() {
		println(commandLineText {
			"""
			There is a story of ${color(LightBlue, "dragons and dragon knights")}
			who have been traveling all over the known universes
			and telling ${color(Cyan, "stories and legends")} to ${color(DarkGray, "mortal beings")}
			from time immemorial
			for an immeasurable time
			which used to be very well known ${color(Yellow, "across the galaxy")}
			long long ago.
			""".trimIndent()
		})
	}
	
	@Test //TESTED OK
	fun test4() {
		println(commandLineText {
			"""
			There is a story of ${color(LightBlue, "dragons and dragon knights")}
			who have been traveling all over the known universes
			and telling ${color(Cyan, "stories and legends")} to ${color(DarkGray, "mortal beings")}
			from time immemorial
			for an immeasurable time
			which used to be very well known ${color(Yellow, "across the galaxy")}
			long long ago.
			
			Few people know who they are and what mission they bear now,
			but once all intelligence knew, that is, ${bgColor(LightCyan, color(Blue, "Breeze Knights"))}
			""".trimIndent()
		})
	}
}
