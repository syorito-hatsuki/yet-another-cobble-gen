package dev.syoritohatsuki.yacg.common.block

import dev.syoritohatsuki.yacg.common.block.entity.GeneratorBlockEntity
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.ItemScatterer
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

open class GeneratorBlock(settings: Settings = FabricBlockSettings.of(Material.METAL).strength(2f)) :
    BlockWithEntity(settings), BlockEntityProvider {

    /*   Block Entity   */
    @Deprecated(
        "Deprecated in Java", ReplaceWith(
            "BlockRenderType.MODEL",
            "net.minecraft.block.BlockRenderType"
        )
    )
    override fun getRenderType(state: BlockState): BlockRenderType = BlockRenderType.MODEL

    @Deprecated(
        "Deprecated in Java", ReplaceWith(
            "super.onStateReplaced(state, world, pos, newState, moved)",
            "net.minecraft.block.BlockWithEntity"
        )
    )
    override fun onStateReplaced(
        state: BlockState,
        world: World,
        pos: BlockPos,
        newState: BlockState,
        moved: Boolean
    ) {
        if (state.block != newState.block) {
            world.getBlockEntity(pos).apply {
                if (this is GeneratorBlockEntity) {
                    ItemScatterer.spawn(world, pos, this)
                    world.updateComparators(pos, this@GeneratorBlock)
                }
            }
            super.onStateReplaced(state, world, pos, newState, moved)
        }
    }

    @Deprecated(
        "Deprecated in Java", ReplaceWith(
            "super.onUse(state, world, pos, player, hand, hit)",
            "net.minecraft.block.BlockWithEntity"
        )
    )
    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult {
        if (!world.isClient) player.sendMessage(Text.literal("Кря ${(world.getBlockEntity(pos) as GeneratorBlockEntity).items[0].item.name}"))
        return ActionResult.PASS
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = GeneratorBlockEntity(pos, state)

    override fun <T : BlockEntity> getTicker(
        world: World?,
        state: BlockState?,
        type: BlockEntityType<T>?
    ): BlockEntityTicker<T>? {
        return checkType(type, GeneratorBlockEntity.type, GeneratorBlockEntity::tick)
    }

}