package dev.syoritohatsuki.yacg.registry

import dev.syoritohatsuki.yacg.YetAnotherCobblestoneGenerator.MOD_ID
import dev.syoritohatsuki.yacg.YetAnotherCobblestoneGenerator.YACG_ITEM_GROUP
import dev.syoritohatsuki.yacg.common.block.GeneratorBlock
import dev.syoritohatsuki.yacg.config.GeneratorsConfig
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier


object BlocksRegistry {
    val BLOCKS: MutableMap<Block, Identifier> = LinkedHashMap()

    init {
        GeneratorsConfig.getTypes().forEach {
            GeneratorBlock(it).create()
        }

        BLOCKS.keys.forEach { block ->
            Registry.register(Registries.BLOCK, BLOCKS[block], block)
            Registry.register(Registries.ITEM, BLOCKS[block], BlockItem(block, Item.Settings()))
            ItemGroupEvents.modifyEntriesEvent(YACG_ITEM_GROUP).register(ModifyEntries {
                it.add(block)
            })
        }
    }

    private fun Block.create(): Block = this.apply {
        BLOCKS[this] = Identifier(MOD_ID, (this as GeneratorBlock).type)
    }
}
