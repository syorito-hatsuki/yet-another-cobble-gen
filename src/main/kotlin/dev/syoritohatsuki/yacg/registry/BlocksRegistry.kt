package dev.syoritohatsuki.yacg.registry

import dev.syoritohatsuki.yacg.YetAnotherCobbleGen
import dev.syoritohatsuki.yacg.YetAnotherCobbleGen.GENERATOR_ITEM_GROUP
import dev.syoritohatsuki.yacg.block.CobbleGeneratorBlock
import dev.syoritohatsuki.yacg.block.OreGeneratorBlock
import dev.syoritohatsuki.yacg.block.StoneGeneratorBlock
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier


object BlocksRegistry {
    private val BLOCKS: MutableMap<Block, Identifier> = LinkedHashMap()

    val COBBLE_GENERATOR = CobbleGeneratorBlock().create("cobble_generator")
    private val ORE_GENERATOR = OreGeneratorBlock().create("ore_generator")
    private val STONE_GENERATOR = StoneGeneratorBlock().create("stone_generator")

    init {
        BLOCKS.keys.forEach { block ->
            Registry.register(Registries.BLOCK, BLOCKS[block], block)
            Registry.register(Registries.ITEM, BLOCKS[block], BlockItem(block, Item.Settings()).also { item ->
                ItemGroupEvents.modifyEntriesEvent(GENERATOR_ITEM_GROUP).register(ModifyEntries {
                    it.add(item)
                })
            })
        }
    }
    private fun Block.create(name: String): Block = this.apply {
        BLOCKS[this] = Identifier(YetAnotherCobbleGen.MOD_ID, name)
    }
}
