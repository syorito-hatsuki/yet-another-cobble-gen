package dev.syoritohatsuki.yacg

import com.mojang.logging.LogUtils
import dev.syoritohatsuki.yacg.registry.BlocksEntityRegistry
import dev.syoritohatsuki.yacg.registry.BlocksRegistry
import dev.syoritohatsuki.yacg.registry.ItemsRegistry
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.slf4j.Logger


object YetAnotherCobblestoneGenerator : ModInitializer {

    const val MOD_ID = "yacg"
    val logger: Logger = LogUtils.getLogger()

    val YACG_ITEM_GROUP: RegistryKey<ItemGroup> =
        RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier(MOD_ID, MOD_ID))

    override fun onInitialize() {
        BlocksRegistry
        BlocksEntityRegistry
        ItemsRegistry

        Registry.register(
            Registries.ITEM_GROUP,
            YACG_ITEM_GROUP,
            FabricItemGroup.builder().icon {
                ItemStack(BlocksRegistry.BLOCKS.keys.first())
            }.displayName(Text.literal(MOD_ID.uppercase())).build()
        )
    }
}