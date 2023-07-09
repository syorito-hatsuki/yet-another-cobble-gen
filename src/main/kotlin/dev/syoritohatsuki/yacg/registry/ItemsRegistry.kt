package dev.syoritohatsuki.yacg.registry

import dev.syoritohatsuki.yacg.YetAnotherCobblestoneGenerator.MOD_ID
import dev.syoritohatsuki.yacg.common.item.UpgradeItem
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object ItemsRegistry {

    private val ITEMS: MutableMap<Item, Identifier> = LinkedHashMap()

    val COEFFICIENT_UPGRADE = UpgradeItem(UpgradeItem.UpgradesTypes.COEFFICIENT).create("upgrade_coefficient")
    val COUNT_UPGRADE = UpgradeItem(UpgradeItem.UpgradesTypes.COUNT).create("upgrade_count")
    val SPEED_UPGRADE = UpgradeItem(UpgradeItem.UpgradesTypes.SPEED).create("upgrade_speed")

    init {
        ITEMS.keys.forEach { item ->
            Registry.register(Registry.ITEM, ITEMS[item], item)
        }
    }

    private fun Item.create(id: String): Item = this.apply {
        ITEMS[this] = Identifier(MOD_ID, id)
    }
}