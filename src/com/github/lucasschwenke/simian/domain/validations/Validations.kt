package com.github.lucasschwenke.simian.domain.validations

class Validations(
    horizontalValidator: HorizontalValidator,
    verticalValidator: VerticalValidator,
    diagonalValidator: DiagonalValidator,
    invertedDiagonalValidator: InvertedDiagonalValidator
) {

    private val validatorList = listOf<DnaValidator>(
        horizontalValidator,
        verticalValidator,
        diagonalValidator,
        invertedDiagonalValidator
    )

    fun getValidations() = validatorList
}
