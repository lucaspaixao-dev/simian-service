package com.github.lucasschwenke.simian.domain.dna.repositories

import com.github.lucasschwenke.simian.domain.dna.Dna
import com.github.lucasschwenke.simian.domain.dna.DnaType

interface DnaRepository {

    fun create(dna: Dna)
    fun exists(dna: Array<String>): Boolean
    fun countByType(type: DnaType): Int
}
