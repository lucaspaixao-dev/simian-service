package com.github.lucasschwenke.simian.common.koin

import com.github.lucasschwenke.simian.domain.validations.DiagonalValidator
import com.github.lucasschwenke.simian.domain.validations.HorizontalValidator
import com.github.lucasschwenke.simian.domain.validations.InvertedDiagonalValidator
import com.github.lucasschwenke.simian.domain.validations.Validations
import com.github.lucasschwenke.simian.domain.validations.VerticalValidator
import org.koin.core.module.Module
import org.koin.dsl.module

val validatorsModule: Module = module {
    single { HorizontalValidator() }
    single { VerticalValidator() }
    single { DiagonalValidator() }
    single { InvertedDiagonalValidator() }
    single { Validations(get(), get(), get(), get()) }
}