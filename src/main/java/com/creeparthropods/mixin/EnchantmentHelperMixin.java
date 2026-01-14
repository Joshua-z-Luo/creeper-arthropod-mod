package com.creeparthropods.mixin;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    private static final int BANE_WEIGHT_MULTIPLIER = 6; // 1 = normal, 6 = ~6x more likely

    @Inject(method = "getPossibleEntries", at = @At("RETURN"), cancellable = true)
    private static void creeparthropods$boostBaneChance(
            int power, ItemStack stack, boolean treasureAllowed,
            CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir
    ) {
        if (!stack.isIn(ItemTags.PICKAXES)) return;

        List<EnchantmentLevelEntry> original = cir.getReturnValue();
        if (original == null || original.isEmpty()) return;

        ArrayList<EnchantmentLevelEntry> boosted = new ArrayList<>(original);

        for (EnchantmentLevelEntry entry : original) {
            if (entry.enchantment instanceof DamageEnchantment de
                    && de.typeIndex == DamageEnchantment.ARTHROPODS_INDEX) {

                for (int i = 1; i < BANE_WEIGHT_MULTIPLIER; i++) {
                    boosted.add(entry);
                }
            }
        }

        cir.setReturnValue(boosted);
    }
}
