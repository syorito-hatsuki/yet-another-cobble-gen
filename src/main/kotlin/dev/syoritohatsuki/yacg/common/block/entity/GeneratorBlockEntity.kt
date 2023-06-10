package dev.syoritohatsuki.yacg.common.block.entity

import dev.syoritohatsuki.yacg.CoefficientConfig
import dev.syoritohatsuki.yacg.YetAnotherCobblestoneGenerator.logger
import dev.syoritohatsuki.yacg.registry.BlocksEntityRegistry
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import kotlin.random.Random

class GeneratorBlockEntity(
    blockPos: BlockPos,
    blockState: BlockState,
    private var type: String = ""
) : BlockEntity(BlocksEntityRegistry.GENERATOR_ENTITY, blockPos, blockState), ImplementedInventory {

    private var progress: Byte = 0
    private val maxProcess: Byte = 20

    private val inventory = DefaultedList.ofSize(255, ItemStack.EMPTY)

    companion object {

        fun tick(world: World, blockPos: BlockPos, blockState: BlockState, generatorBlockEntity: GeneratorBlockEntity) {

            if (world.isClient) return

            if (generatorBlockEntity.progress == generatorBlockEntity.maxProcess) {

                val randomBlock = getRandomBlock(
                    generatorBlockEntity.type,
                    CoefficientConfig.getBlocks(generatorBlockEntity.type) ?: return
                ) ?: return

                if (!generatorBlockEntity.items.none { it.isOf(randomBlock.item) }) {
                    val slot = generatorBlockEntity.items.indexOfFirst { it.isOf(randomBlock.item) }
                    generatorBlockEntity.setStack(
                        slot, randomBlock.copyWithCount(generatorBlockEntity.items[slot].count + randomBlock.count)
                    )
                } else generatorBlockEntity.setStack(getEmptySlot(generatorBlockEntity.items) ?: return, randomBlock)

                generatorBlockEntity.progress = 0
            }

            generatorBlockEntity.progress++

            markDirty(world, blockPos, blockState)
        }

        private fun getRandomBlock(type: String, blocks: Set<CoefficientConfig.Generators.GenerateItem>): ItemStack? {
            if (blocks.isEmpty()) {
                logger.error("Blocks list for $type is empty")
                return null
            }

            var randomNumber = Random.nextInt(blocks.sumOf { it.coefficient })
            blocks.forEach { block ->
                if (randomNumber < block.coefficient)
                    return ItemStack(Registries.ITEM.get(Identifier(block.itemId)), block.count)
                randomNumber -= block.coefficient
            }

            logger.error("Blocks list for $type is empty")
            return null
        }

        private fun getEmptySlot(items: DefaultedList<ItemStack>): Int? {
            items.indices.forEach {
                if (items[it].isEmpty) return it
            }
            logger.warn("Can't find any free slot from 255 slot's")
            return null
        }
    }

    override var items: DefaultedList<ItemStack> = inventory

    override fun markDirty() = super<ImplementedInventory>.markDirty()

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        Inventories.writeNbt(nbt, inventory)
        nbt.putByte("yacg.progress", progress)
        nbt.putString("yacg.type", type)
    }

    override fun readNbt(nbt: NbtCompound) {
        Inventories.readNbt(nbt, inventory)
        super.readNbt(nbt)
        progress = nbt.getByte("yacg.progress")
        type = nbt.getString("yacg.type")
    }
}