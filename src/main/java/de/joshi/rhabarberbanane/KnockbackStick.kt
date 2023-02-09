package de.joshi.rhabarberbanane

import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.Rarity
import net.minecraft.util.NonNullList
import net.minecraft.world.World


class KnockbackStick(
        val knockback: Int,
        val rarity: Rarity
) : Item(
        Properties()
                .group(ItemGroup.COMBAT)
                .maxStackSize(1)
                .rarity(rarity)
) {

    override fun fillItemGroup(group: ItemGroup, items: NonNullList<ItemStack>) {
        super.fillItemGroup(group, items)
        for (stack in items) {
            if (stack.item == this) {
                stack.addEnchantment(Enchantments.KNOCKBACK, knockback)
            }
        }
    }

    override fun onCreated(stack: ItemStack, worldIn: World, playerIn: PlayerEntity) {
        addKnockback(stack)
    }

    override fun inventoryTick(stack: ItemStack, worldIn: World, entityIn: Entity, itemSlot: Int, isSelected: Boolean) {
        addKnockback(stack)
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected)
    }

    private fun addKnockback(stack: ItemStack) {
        if (!stack.isEnchanted) {
            stack.addEnchantment(Enchantments.KNOCKBACK, knockback)
        } else {
            if (EnchantmentHelper.getEnchantments(stack)[Enchantments.KNOCKBACK] == null) {
                stack.addEnchantment(Enchantments.KNOCKBACK, knockback)
            }
        }
    }
}