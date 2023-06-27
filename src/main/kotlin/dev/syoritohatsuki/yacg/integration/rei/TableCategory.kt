package dev.syoritohatsuki.yacg.integration.rei

import dev.syoritohatsuki.yacg.integration.rei.YacgReiPlugin.GENERATORS_CATEGORY
import dev.syoritohatsuki.yacg.registry.BlocksRegistry
import me.shedaniel.math.Point
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.client.gui.Renderer
import me.shedaniel.rei.api.client.gui.widgets.Widget
import me.shedaniel.rei.api.client.gui.widgets.Widgets
import me.shedaniel.rei.api.client.registry.display.DisplayCategory
import me.shedaniel.rei.api.common.util.EntryStacks
import net.minecraft.item.ItemStack
import net.minecraft.text.Text

class TableCategory : DisplayCategory<TableDisplay> {
    override fun getCategoryIdentifier() = GENERATORS_CATEGORY

    override fun getTitle(): Text = Text.translatable("emi.category.yacg.generators")

    override fun getIcon(): Renderer = EntryStacks.of(ItemStack(BlocksRegistry.BLOCKS.keys.random()))

    override fun getDisplayHeight() = 144

    override fun setupDisplay(display: TableDisplay, bounds: Rectangle): MutableList<Widget> {
        val center = Point(bounds.centerX, bounds.centerY)
        val widgets = mutableListOf<Widget>()

        val hasInputs = display.inputEntries.size == 2

        if (hasInputs) widgets.add(
            Widgets.createSlot(Point(center.x - 20, bounds.y + 10))
                .entries(display.inputEntries[1]).markInput()
        )

        widgets.add(
            Widgets.createSlot(Point(center.x + if (hasInputs) 0 else -10, bounds.y + 10))
                .entries(display.inputEntries[0])
        )

        val outBounds = Rectangle(bounds.x, bounds.y + 30, bounds.width, bounds.height - 30)
        widgets.add(Widgets.createSlotBase(outBounds))
        widgets.add(ScrollableSlotsWidget(outBounds, display.outputEntries))

        return widgets
    }
}