// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("TemporalAmountExtensions")

package com.windea.breezeframework.core.extension

import java.time.*

//region Operator Extensions
operator fun Duration.unaryPlus(): Duration = this

/**
 * @see java.time.Duration.negated
 */
operator fun Duration.unaryMinus(): Duration = this.negated()

/**
 * @see java.time.Duration.multipliedBy
 */
operator fun Duration.times(n: Long): Duration = this.multipliedBy(n)

/**
 * @see java.time.Duration.dividedBy
 */
operator fun Duration.div(n: Long): Duration = this.dividedBy(n)

/**
 * @see java.time.Duration.getSeconds
 */
operator fun Duration.component1(): Long = this.seconds

/**
 * @see java.time.Duration.getNano
 */
operator fun Duration.component2(): Int = this.nano


operator fun Period.unaryPlus(): Period = this

/**
 * @see java.time.Period.negated
 */
operator fun Period.unaryMinus(): Period = this.negated()

/**
 * @see java.time.Period.multipliedBy
 */
operator fun Period.times(n: Int): Period = this.multipliedBy(n)

/**
 * @see java.time.Period.getYears
 */
operator fun Period.component1(): Int = this.years

/**
 * @see java.time.Period.getMonths
 */
operator fun Period.component2(): Int = this.months

/**
 * @see java.time.Period.getDays
 */
operator fun Period.component3(): Int = this.days
//endregion
