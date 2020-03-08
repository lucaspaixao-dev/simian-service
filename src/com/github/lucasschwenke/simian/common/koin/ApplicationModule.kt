package com.github.lucasschwenke.simian.common.koin

import com.github.lucasschwenke.simian.application.config.ObjectMapperConfig
import com.github.lucasschwenke.simian.application.web.controllers.HealthCheckController
import org.koin.core.module.Module
import org.koin.dsl.module

val applicationModule: Module = module {
    single { ObjectMapperConfig.configure() }

    single { HealthCheckController(get()) }
}
