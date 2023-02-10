package de.joshi.rhabarberbanane;

import net.minecraft.data.RecipeProvider;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class Knuebbl extends SwordItem {
    private final int knockback;

    public Knuebbl(int knockback, int attackDamage, Rarity rarity) {
        super(new IItemTier() {

            @Override
            public int getMaxUses() {
                return 512;
            }

            @Override
            public float getEfficiency() {
                return 0;
            }

            @Override
            public float getAttackDamage() {
                return 0;
            }

            @Override
            public int getHarvestLevel() {
                return 0;
            }

            @Override
            public int getEnchantability() {
                return 0;
            }

            @NotNull
            @Override
            public Ingredient getRepairMaterial() {
                return Ingredient.EMPTY;
            }
        }, attackDamage, 1f, new Properties()
                .group(ItemGroup.COMBAT)
                .rarity(rarity));

        this.knockback = knockback;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        addKnockback(stack);
        return super.getDisplayName(stack);
    }

    @Override
    public int getDamage(ItemStack stack) {
        addKnockback(stack);
        return super.getDamage(stack);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        addKnockback(stack);
        return 512;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        super.fillItemGroup(group, items);

        for (ItemStack stack : items) {
            if (stack.getItem() == this) {
                stack.addEnchantment(Enchantments.KNOCKBACK, knockback);
            }
        }
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        addKnockback(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        addKnockback(stack);

        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @NotNull
    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        addKnockback(stack);
        return stack;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack stack = super.getContainerItem(itemStack);
        addKnockback(stack);
        return stack;
    }

    @Override
    public boolean updateItemStackNBT(CompoundNBT nbt) {

        return super.updateItemStackNBT(nbt);
    }

    private void addKnockback(ItemStack stack) {
        if (!stack.isEnchanted()) {
            stack.addEnchantment(Enchantments.KNOCKBACK, knockback);
        } else {
            if (EnchantmentHelper.getEnchantments(stack).get(Enchantments.KNOCKBACK) == null || EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, stack) != knockback) {
                stack.addEnchantment(Enchantments.KNOCKBACK, knockback);
            }
        }
    }
}
