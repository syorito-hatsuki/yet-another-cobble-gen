package dev.syoritohatsuki.yacg.common.block.entity

import dev.syoritohatsuki.yacg.registry.BlocksEntityRegistry
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class GeneratorBlockEntity(
    blockPos: BlockPos,
    blockState: BlockState
) : BlockEntity(BlocksEntityRegistry.GENERATOR_ENTITY, blockPos, blockState), ImplementedInventory {

    var progress: Int = 0
    val maxProcess: Int = 20

    companion object {
        const val slotCount = 1

        fun tick(world: World, blockPos: BlockPos, blockState: BlockState, generatorBlockEntity: GeneratorBlockEntity) {
            if (world.isClient) return

            if (generatorBlockEntity.progress == generatorBlockEntity.maxProcess) {

                val output = ItemStack(Items.STONE, 1)

                world.players.forEach { playerEntity ->
                    val blockInv = generatorBlockEntity.items[0]
                    if (ItemStack.areItemsEqual(output, blockInv)) {
                        generatorBlockEntity.setStack(0, ItemStack(Items.STONE, blockInv.count + 1))
                        playerEntity.sendMessage(Text.of("Stone count: ${blockInv.count}"))
                    } else {
                        generatorBlockEntity.setStack(0, output)
                        playerEntity.sendMessage(Text.of("Stone count: ${blockInv.count}"))
                    }
                }

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