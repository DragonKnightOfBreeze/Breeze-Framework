import com.windea.utility.common.dsl.*
import com.windea.utility.common.dsl.data.*
import com.windea.utility.common.dsl.text.*
import org.junit.*

class DslTests {
	@Test
	fun xmlDslTest() {
		val str = Dsl.xml {
			comment("123456")
			element("123", "a" to 1) {
				comment("333").n()
				element("abc")
				element("a") { text("text") }
				element("a") { +"text2" }.n(false)
			}
		}.toString()
		//<!--123456-->
		//<123 a="1">
		//  <!--
		//    333
		//  -->
		//  <abc></abc>
		//  <a>
		//    text
		//  </a>
		//  <a>text2</a>
		//</123>
		println(str)
	}
	
	//任意一种形式都是可以的！！！推荐纯dsl或者纯模版字符串。
	@Test
	fun starBoundTextDslTest() {
		val str1 = Dsl.starBoundText {
			t("信息 ")
			pht("player_name")
			+"：这是一段"
			ct("blue") {
				ct("green") {
					+"彩"
				}
				+"色"
			}
			+"文本。"
		}
		//<player_name>：这是一段^blue;^green;彩^reset;色^reset;文本。
		println(str1)
		
		val str2 = Dsl.starBoundText {
			t("信息 ");pht("player_name");+"：这是一段";ct("blue") { ct("green") { +"彩" };+"色" };+"文本。"
		}
		//<player_name>：这是一段^blue;^green;彩^reset;色^reset;文本。
		println(str2)
		
		val str3 = Dsl.starBoundText {
			+"信息 " + pht("player_name") + (+"：这是一段") + ct("blue") { ct("green") { +"彩" } + (+"色") } + (+"文本。")
		}
		//信息 <player_name>：这是一段^blue;^green;彩^reset;色^reset;文本。
		println(str3)
		
		val str4 = Dsl.starBoundText {
			+"信息 " + pht("player_name") + "：这是一段" + ct("blue") { ct("green") { +"彩" } + "色" } + "文本。"
		}
		//信息 <player_name>：这是一段^blue;^green;彩^reset;色^reset;文本。
		println(str4)
		
		val str5 = Dsl.starBoundText {
			-"信息 ${pht("player_name")}：这是一段${ct("blue") { ct("green") { +"彩" } + "色" }}文本。"
		}
		//信息 <player_name>：这是一段^blue;^green;彩^reset;色^reset;文本。
		println(str5)
	}
}
