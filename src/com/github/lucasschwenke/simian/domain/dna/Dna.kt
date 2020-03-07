package com.github.lucasschwenke.simian.domain.dna

data class Dna(val dna: Array<String>, val type: DnaType) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Dna

        if (!dna.contentEquals(other.dna)) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = dna.contentHashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}
