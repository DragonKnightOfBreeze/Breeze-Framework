package icu.windea.breezeframework.aviator

import javax.script.ScriptEngineManager

val scriptEngineManager = ScriptEngineManager()
val engine = scriptEngineManager.getEngineByName("AviatorScript")

fun main() {
	engine.eval("""println("hello, AviatorScript!")""")
}
