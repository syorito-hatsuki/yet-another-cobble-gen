package dev.syoritohatsuki.yacg.registry

import dev.syoritohatsuki.yacg.YetAnotherCobblestoneGenerator
import dev.syoritohatsuki.yacg.common.block.GeneratorBlock
import dev.syoritohatsuki.yacg.config.GeneratorsConfig
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry


object BlocksRegistry {
    val BLOCKS: MutableMap<Block, Identifier> = LinkedHashMap()

    init {
        GeneratorsConfig.getTypes().forEach {
            GeneratorBlock(it).create()
        }

        BLOCKS.keys.forEach { block ->
            Registry.register(Registry.BLOCK, BLOCKS[block], block)
            Registry.register(
                Registry.ITEM, BLOCKS[block], BlockItem(
                    block, Item.Settings().group(YetAnotherCobblestoneGenerator.YACG_ITEM_GROUP)
                )
            )
        }
    }

    private fun Block.create(): Block = this.apply {
        BLOCKS[this] = Identifier(YetAnotherCobblestoneGenerator.MOD_ID, (this as GeneratorBlock).type)
    }
}
