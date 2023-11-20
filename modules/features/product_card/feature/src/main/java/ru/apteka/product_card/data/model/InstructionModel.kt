package ru.apteka.product_card.data.model

/**
 * Instruction model
 */
data class InstructionModel(
    val instructions: List<InstructionItem>
) {
    data class InstructionItem(
        val title: String,
        val desc: String
    )
}
