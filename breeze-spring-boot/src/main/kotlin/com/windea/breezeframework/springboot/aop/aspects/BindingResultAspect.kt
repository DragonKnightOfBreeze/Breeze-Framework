package com.windea.breezeframework.springboot.aop.aspects

import org.aspectj.lang.annotation.*
import org.springframework.validation.*

/**控制器的切面。*/
@Aspect
class ControllerAspect {
	/**参数验证的切面。在bindingResult有错时，抛出ValidationException。*/
	@Before("within(com.windea.demo.cloudcollect.core.controller..*) && args(bindingResult, ..)")
	fun validationAdvice(bindingResult: BindingResult) {
		if(bindingResult.hasErrors()) {
		}
	}
}
