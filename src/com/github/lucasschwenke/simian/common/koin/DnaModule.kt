package com.github.lucasschwenke.simian.common.koin

import com.github.lucasschwenke.simian.application.config.DatabaseConfig
import com.github.lucasschwenke.simian.application.web.controllers.DnaController
import com.github.lucasschwenke.simian.domain.dna.repositories.DnaRepository
import com.github.lucasschwenke.simian.domain.services.DnaService
import com.github.lucasschwenke.simian.resource.repository.dna.DnaRepositoryDb
import org.koin.core.module.Module
import org.koin.dsl.module

val dnaModule: Module = module {
    single { DnaService(get(), get()) }
    single { DnaController(get()) }
    single { DnaRepositoryDb(get(), DatabaseConfig.getDatabase(), get()) as DnaRepository }
}