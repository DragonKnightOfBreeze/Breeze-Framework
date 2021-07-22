package icu.windea.breezeframework.dsl.cmd

import icu.windea.breezeframework.dsl.cmd.CmdDsl.*
import kotlin.test.*

class CmdDslTest {
	@Test
	fun test1() {
		println(cmdDsl {
			color("123", Color.Red)
		})
	}

	@Test
	fun testColor() {
		println(cmdDsl { color("hello world!", Color.Black) })
		println(cmdDsl { color("hello world!", Color.Red) })
		println(cmdDsl { color("hello world!", Color.Green) })
		println(cmdDsl { color("hello world!", Color.Yellow) })
		println(cmdDsl { color("hello world!", Color.Blue) })
		println(cmdDsl { color("hello world!", Color.Magenta) })
		println(cmdDsl { color("hello world!", Color.Cyan) })
		println(cmdDsl { color("hello world!", Color.LightGray) })
	}

	@Test
	fun test2() {
		println(cmdDsl {
			bgColor("123", Color.Red)
		})
	}

	@Test
	fun test3() {
		println(cmdDsl {
			"""
			=================================================================
			There is a story of ${color("dragons and dragon knights", Color.LightBlue)}
			who have been traveling all over the known universes
			and telling ${color("stories and legends", Color.Cyan)} to ${color("mortal beings", Color.DarkGray)}
			from time immemorial
			for an immeasurable time,
			which used to be very well-known ${color("across the galaxy", Color.Yellow)}
			even in the far-long past.
			=================================================================
			""".trimIndent()
		})
	}

	@Test
	fun test4() {
		println(cmdDsl {
			"""
			=================================================================
			There is a story of ${bold(color("dragons and dragon knights", Color.LightBlue))}
			who have been ${style("traveling all over the known universes", Style.Underline, Style.Italic)}
			and telling ${color("stories and legends", Color.Cyan)} to ${color("mortal beings", Color.Red)}
			from time immemorial
			for an immeasurable time,
			which used to be very well-known ${color("across the galaxy", Color.Yellow)}
			long-long ago in the river of time.

			Few people know ${italic("who they are")},
			but once all intelligence knew, that is, ${bold(color("Breeze Knights", Color.Blue))}.

			${underline(color("And also, that is in the daydream fairy tale.", Color.DarkGray))}
			=================================================================
			""".trimIndent()
		})
	}
}
