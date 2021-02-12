// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("JpaExtensions")

package com.windea.breezeframework.springboot.extension

import com.querydsl.core.types.*
import org.springframework.data.jpa.domain.*
import org.springframework.data.jpa.repository.*
import org.springframework.data.jpa.repository.support.*

/**根据Jpa Specification查询一个结果，返回一个可空对象。*/
fun <T> JpaSpecificationExecutor<T>.findOneOrNull(spec: Specification<T>): T? = this.findOne(spec).orElse(null)

/**根据QueryDsl Predicate查询一个结果，返回一个可空对象。*/
fun <T> QuerydslJpaPredicateExecutor<T>.findOneOrNull(predicate: Predicate): T? = this.findOne(predicate).orElse(null)
