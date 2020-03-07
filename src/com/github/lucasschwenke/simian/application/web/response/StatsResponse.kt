package com.github.lucasschwenke.simian.application.web.response

import java.math.BigDecimal

data class StatsResponse(
    val countMutantDna: Int,
    val countHumanDna: Int,
    val radio: BigDecimal
)
