package com.github.lucasschwenke.simian.common.koin

import com.github.lucasschwenke.simian.application.web.controllers.StatsController
import com.github.lucasschwenke.simian.domain.services.StatsService
import org.koin.core.module.Module
import org.koin.dsl.module

val statsModule: Module = module {
    single { StatsService(get()) }
    single { StatsController(get()) }
}