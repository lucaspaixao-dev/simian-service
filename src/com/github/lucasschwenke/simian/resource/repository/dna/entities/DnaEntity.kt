package com.github.lucasschwenke.simian.resource.repository.dna.entities

import com.github.lucasschwenke.simian.domain.dna.DnaType

data class DnaEntity(
    val id: String,
    val dna: Array<String>,
    val type: DnaType
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DnaEntity

        if (id != other.id) return false
        if (!dna.contentEquals(other.dna)) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + dna.contentHashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}
