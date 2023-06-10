package dev.syoritohatsuki.yacg.registry

import dev.syoritohatsuki.yacg.YetAnotherCobblestoneGenerator
import dev.syoritohatsuki.yacg.YetAnotherCobblestoneGenerator.GENERATOR_ITEM_GROUP
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object ItemsRegistry {

    private val ITEMS: MutableMap<Item, Identifier> = LinkedHashMap()

    private val TIER_FIRST = Item(Item.Settings()).create("tier_first")
    private val TIER_SECOND = Item(Item.Settings()).create("tier_second")
    private val TIER_THIRD = Item(Item.Settings()).create("tier_third")

    init {
        ITEMS.keys.forEach { item ->
            Registry.register(Registries.ITEM, ITEMS[item], item).also {
                ItemGroupEvents.modifyEntriesEvent(GENERATOR_ITEM_GROUP).register(ItemGroupEvents.ModifyEntries {
                    it.add(item)
                })
            }
        }
    }

    private fun Item.create(id: String): Item = this.apply {
        ITEMS[this] = Identifier(YetAnotherCobblestoneGenerator.MOD_ID, id)
    }
}