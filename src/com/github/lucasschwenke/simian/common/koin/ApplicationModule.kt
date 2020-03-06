package com.github.lucasschwenke.simian.common.koin

import com.github.lucasschwenke.simian.application.web.controllers.SimianController
import com.github.lucasschwenke.simian.domain.services.SimianService
import com.github.lucasschwenke.simian.domain.validations.*
import org.koin.core.module.Module
import org.koin.dsl.module

val applicationModule : Module = module {
    single { HorizontalValidator() }
    single { VerticalValidator() }
    single { DiagonalValidator() }
    single { InvertedDiagonalValidator() }
    single { Validations(get(), get(), get(), get()) }

    single { SimianService(get()) }
    single { SimianController(get()) }
}