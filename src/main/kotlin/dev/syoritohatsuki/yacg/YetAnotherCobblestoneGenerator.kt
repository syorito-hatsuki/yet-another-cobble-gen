package dev.syoritohatsuki.yacg

import com.mojang.logging.LogUtils
import dev.syoritohatsuki.yacg.registry.BlocksEntityRegistry
import dev.syoritohatsuki.yacg.registry.BlocksRegistry
import dev.syoritohatsuki.yacg.registry.ItemsRegistry
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import org.slf4j.Logger


object YetAnotherCobblestoneGenerator : ModInitializer {

    const val MOD_ID = "yacg"
    val logger: Logger = LogUtils.getLogger()

    val YACG_ITEM_GROUP: ItemGroup = FabricItemGroupBuilder.create(Identifier(MOD_ID, "generator")).icon {
        ItemStack(BlocksRegistry.BLOCKS.keys.first())
    }.build()

    override fun onInitialize() {
        BlocksRegistry
        BlocksEntityRegistry
        ItemsRegistry
    }
}