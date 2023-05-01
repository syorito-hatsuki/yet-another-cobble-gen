package dev.syoritohatsuki.yacg.registry

import dev.syoritohatsuki.yacg.YetAnotherCobblestoneGenerator
import dev.syoritohatsuki.yacg.common.block.entity.GeneratorBlockEntity
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object BlocksEntityRegistry {
    val GENERATOR_ENTITY: BlockEntityType<GeneratorBlockEntity> =
        Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            Identifier(YetAnotherCobblestoneGenerator.MOD_ID, "generator_block_entity"),
            FabricBlockEntityTypeBuilder.create(
                ::GeneratorBlockEntity,
                *BlocksRegistry.BLOCKS.keys.toTypedArray()
            ).build()
        )
}