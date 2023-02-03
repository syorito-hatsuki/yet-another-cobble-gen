package dev.syoritohatsuki.yacg.common.block.entity

import dev.syoritohatsuki.yacg.YetAnotherCobbleGen
import dev.syoritohatsuki.yacg.common.block.GeneratorBlock
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class GeneratorBlockEntity(
    blockPos: BlockPos,
    blockState: BlockState
) : BlockEntity(type, blockPos, blockState), ImplementedInventory {

    var progress: Int = 0
    val maxProcess: Int = 20

    companion object {
        const val slotCount = 0

        val type: BlockEntityType<GeneratorBlockEntity> = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier(YetAnotherCobbleGen.MOD_ID, "generator_block_entity"),
            FabricBlockEntityTypeBuilder.create(
                { blockPos, blockState ->
                    GeneratorBlockEntity(blockPos, blockState)
                },
                GeneratorBlock()
            ).build()
        )

        fun tick(world: World, blockPos: BlockPos, blockState: BlockState, generatorBlockEntity: GeneratorBlockEntity) {
            if (world.isClient) return

            if (generatorBlockEntity.progress == generatorBlockEntity.maxProcess) {

                if (generatorBlockEntity.items[0].isEmpty)
                    generatorBlockEntity.items[0] = Items.COBBLESTONE.defaultStack.apply { count = 1 }
                else
                    generatorBlockEntity.items[0].count + 1

                generatorBlockEntity.progress = 0
            }
            generatorBlockEntity.progress++

            markDirty(world, blockPos, blockState)
        }
    }

    override var items: DefaultedList<ItemStack> = DefaultedList.ofSize(slotCount, ItemStack.EMPTY)

    override fun markDirty() = super<ImplementedInventory>.markDirty()

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        Inventories.writeNbt(nbt, items)
        nbt.putInt("yacg.progress", progress)
    }

    override fun readNbt(nbt: NbtCompound) {
        Inventories.readNbt(nbt, items)
        super.readNbt(nbt)
        progress = nbt.getInt("yacg.progress")
    }
}