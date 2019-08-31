package com.windea.utility.springboot.data

import com.querydsl.core.types.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.domain.*
import org.springframework.data.jpa.repository.*
import org.springframework.data.jpa.repository.support.*
import org.springframework.data.repository.query.*

/**将当前列表转化为分页。*/
fun <T> List<T>.toPage(): Page<T> = PageImpl<T>(this)

/**将当前列表转化为分页。*/
fun <T> List<T>.toPage(pageable: Pageable): Page<T> = PageImpl<T>(this, pageable, this.size.toLong())


/**根据Example查询一个结果，返回一个可空对象。*/
fun <T> QueryByExampleExecutor<T>.findOneOrNull(example: Example<T>): T? = this.findOne(example).orElse(null)

/**根据Jpa Specification查询一个结果，返回一个可空对象。*/
fun <T> JpaSpecificationExecutor<T>.findOneOrNull(spec: Specification<T>): T? = this.findOne(spec).orElse(null)

/**根据Querydsl Predicate查询一个结果，返回一个可空对象。*/
fun <T> QuerydslJpaPredicateExecutor<T>.findOneOrNull(predicate: Predicate): T? = this.findOne(predicate).orElse(null)
