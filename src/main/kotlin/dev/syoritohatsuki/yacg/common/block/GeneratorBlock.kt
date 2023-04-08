package dev.syoritohatsuki.yacg.common.block

import dev.syoritohatsuki.yacg.common.block.entity.GeneratorBlockEntity
import dev.syoritohatsuki.yacg.registry.BlocksEntityRegistry
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Items
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.ItemScatterer
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

@Suppress("OVERRIDE_DEPRECATION", "DEPRECATION")
open class GeneratorBlock(internal val type: String) :
    BlockWithEntity(FabricBlockSettings.of(Material.METAL).strength(2f)), BlockEntityProvider {

    /*   Block Entity   */
    override fun getRenderType(state: BlockState): BlockRenderType = BlockRenderType.MODEL

    override fun onStateReplaced(
        state: BlockState,
        world: World,
        pos: BlockPos,
        newState: BlockState,
        moved: Boolean
    ) {
        if (state.block != newState.block) {
            val blockEntity = world.getBlockEntity(pos)
            if (blockEntity is GeneratorBlockEntity) {
                ItemScatterer.spawn(world, pos, blockEntity)
                world.updateComparators(pos, this)
            }
            super.onStateReplaced(state, world, pos, newState, moved)
        }
    }

    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult {
        if (!world.isClient)
            (world.getBlockEntity(pos) as GeneratorBlockEntity).let { blockEntity ->
                val message = Text.empty()
                blockEntity.items.forEach {
                    if (it.item != Items.AIR) message
                        .append("\n")
                        .append(it.item.name)
                        .append(" x")
                        .append("${it.count}")

                }
                player.sendMessage(message)
            }
        return ActionResult.SUCCESS
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity =
        GeneratorBlockEntity(pos, state, type)

    override fun <T : BlockEntity> getTicker(
        world: World,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? = checkType(type, BlocksEntityRegistry.GENERATOR_ENTITY, GeneratorBlockEntity::tick)

}