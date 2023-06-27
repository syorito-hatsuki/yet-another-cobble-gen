package dev.syoritohatsuki.yacg.integration.rei

import dev.syoritohatsuki.yacg.integration.rei.YacgReiPlugin.GENERATORS_CATEGORY
import me.shedaniel.rei.api.common.display.basic.BasicDisplay
import me.shedaniel.rei.api.common.entry.EntryIngredient

class TableDisplay(input: EntryIngredient, outputs: List<EntryIngredient>) : BasicDisplay(listOf(input), outputs) {
    override fun getCategoryIdentifier() = GENERATORS_CATEGORY
}
