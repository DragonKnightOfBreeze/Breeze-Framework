package icu.windea.breezeframework.aviator

import com.googlecode.aviator.*
import com.googlecode.aviator.script.AviatorScriptEngine
import icu.windea.breezeframework.core.extension.cast
import org.junit.Before
import org.junit.Test
import javax.script.ScriptEngineManager

class AviatorTest {
	private lateinit var aviator : AviatorEvaluatorInstance

	@Before
	fun before(){
		val engineManager = ScriptEngineManager();
		val engine = engineManager.getEngineByName("AviatorScript")
		val aviatorEngine = engine.cast<AviatorScriptEngine>().engine
		aviatorEngine.setOption(Options.FEATURE_SET,Feature.getCompatibleFeatures())
	}

	@Test
	fun test1(){
		println(engine.eval("""1+1""").cast<Long>())
	}
}
