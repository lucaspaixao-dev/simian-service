package com.github.lucasschwenke.simian.common.koin

import com.github.lucasschwenke.simian.application.config.DatabaseConfig
import org.koin.core.module.Module
import org.koin.dsl.module

val databaseModule: Module = module {
    single { DatabaseConfig.connect() }
}
