package dev.syoritohatsuki.yacg.common.block.entity

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.Direction

interface ImplementedInventory : SidedInventory {

    var items: DefaultedList<ItemStack>

    companion object {
        private fun of(items: DefaultedList<ItemStack>): ImplementedInventory {
            return object : ImplementedInventory {
                override var items = items
            }
        }

        fun ofSize(size: Int): ImplementedInventory = of(DefaultedList.ofSize(size, ItemStack.EMPTY))
    }

    override fun getAvailableSlots(side: Direction): IntArray = IntArray(items.size).apply {
        for (i in indices) {
            this[i] = i
        }
    }

    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?): Boolean = true

    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction): Boolean = true

    override fun size(): Int = items.size

    override fun isEmpty(): Boolean {
        for (slot in 0 until size()) {
            getStack(slot).apply {
                if (isEmpty) return false
            }
        }
        return true
    }

    override fun getStack(slot: Int): ItemStack = items[slot]

    override fun removeStack(slot: Int, amount: Int): ItemStack {
        Inventories.splitStack(items, slot, amount).apply {
            if (!isEmpty) markDirty()
            return this
        }
    }

    override fun removeStack(slot: Int): ItemStack = Inventories.removeStack(items, slot)

    override fun setStack(slot: Int, stack: ItemStack) {
        items[slot] = stack
        if (stack.count > maxCountPerStack) stack.count = maxCountPerStack
    }

    override fun clear() = items.clear()

    override fun markDirty() {

    }

    override fun canPlayerUse(player: PlayerEntity?): Boolean = true
}
