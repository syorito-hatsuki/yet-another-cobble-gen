package dev.syoritohatsuki.yacg.integration.jade

import dev.syoritohatsuki.yacg.YetAnotherCobblestoneGenerator.MOD_ID
import dev.syoritohatsuki.yacg.common.block.entity.GeneratorBlockEntity
import dev.syoritohatsuki.yacg.common.item.UpgradeItem
import dev.syoritohatsuki.yacg.registry.ItemsRegistry
import net.minecraft.block.entity.BlockEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier
import net.minecraft.world.World
import snownee.jade.api.BlockAccessor
import snownee.jade.api.IBlockComponentProvider
import snownee.jade.api.IServerDataProvider
import snownee.jade.api.ITooltip
import snownee.jade.api.config.IPluginConfig

enum class GeneratorComponentProvider : IBlockComponentProvider, IServerDataProvider<BlockEntity> {

    INSTANCE;

    override fun getUid(): Identifier = Identifier(MOD_ID, "generators")

    override fun appendServerData(
        data: NbtCompound,
        serverPlayerEntity: ServerPlayerEntity,
        world: World,
        blockEntity: BlockEntity,
        boolean: Boolean
    ) {
        data.putBoolean("coefficient", false)
        data.putBoolean("count", false)
        data.putBoolean("speed", false)
        (blockEntity as GeneratorBlockEntity).listUpgrades.forEach {
            when (it) {
                UpgradeItem.UpgradesTypes.COEFFICIENT -> data.putBoolean("coefficient", true)
                UpgradeItem.UpgradesTypes.COUNT -> data.putBoolean("count", true)
                UpgradeItem.UpgradesTypes.SPEED -> data.putBoolean("speed", true)
            }
        }
    }

    override fun appendTooltip(tooltip: ITooltip, accessor: BlockAccessor, config: IPluginConfig) {
        val elements = tooltip.elementHelper
        if (accessor.serverData.getBoolean("coefficient")) tooltip.append(
            elements.item(
                ItemStack(ItemsRegistry.COEFFICIENT_UPGRADE),
                0.5f
            )
        )
        if (accessor.serverData.getBoolean("count")) tooltip.append(
            elements.item(
                ItemStack(ItemsRegistry.COUNT_UPGRADE),
                0.5f
            )
        )
        if (accessor.serverData.getBoolean("speed")) tooltip.append(
            elements.item(
                ItemStack(ItemsRegistry.SPEED_UPGRADE),
                0.5f
            )
        )
    }
}