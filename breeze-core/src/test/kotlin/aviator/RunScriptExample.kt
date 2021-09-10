package icu.windea.breezeframework.aviator

import com.googlecode.aviator.AviatorEvaluator

fun main() {
	val exp = AviatorEvaluator.getInstance().compileScript("aviator/hello.av")
	exp.execute()
}
