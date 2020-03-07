package com.github.lucasschwenke.simian.domain.dna.repositories

import com.github.lucasschwenke.simian.domain.dna.Dna

interface DnaRepository {

    fun create(dna: Dna)
    fun exists(dna: Array<String>) : Boolean
    fun findAll() : List<Dna>
}