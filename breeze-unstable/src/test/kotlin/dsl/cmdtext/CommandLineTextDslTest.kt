package icu.windea.breezeframework.dsl.cmdtext

import icu.windea.breezeframework.dsl.cmdtext.CmdTextDsl.*
import kotlin.test.*

class CommandLineTextDslTest {
	@Test
	fun test1() {
		println(cmdTextDsl {
			color("123", Color.Red)
		})
	}

	@Test
	fun testColor() {
		println(cmdTextDsl { color("hello world!", Color.Black) })
		println(cmdTextDsl { color("hello world!", Color.Red) })
		println(cmdTextDsl { color("hello world!", Color.Green) })
		println(cmdTextDsl { color("hello world!", Color.Yellow) })
		println(cmdTextDsl { color("hello world!", Color.Blue) })
		println(cmdTextDsl { color("hello world!", Color.Magenta) })
		println(cmdTextDsl { color("hello world!", Color.Cyan) })
		println(cmdTextDsl { color("hello world!", Color.LightGray) })
	}

	@Test
	fun test2() {
		println(cmdTextDsl {
			bgColor("123", Color.Red)
		})
	}

	@Test
	fun test3() {
		println(cmdTextDsl {
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
		println(cmdTextDsl {
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
