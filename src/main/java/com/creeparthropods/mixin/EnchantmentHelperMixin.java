package com.creeparthropods.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
    private static final Logger LOGGER = LoggerFactory.getLogger("ENCHANTABILITY_DEBUG");

    @Inject(method = "getPossibleEntries", at = @At("RETURN"), cancellable = true)
    private static void creeparthropods$addBaneForPickaxes(
            int power,
            ItemStack stack,
            boolean treasureAllowed,
            CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir
    ) {
        if (!(stack.getItem() instanceof PickaxeItem)) return;

        List<EnchantmentLevelEntry> list = cir.getReturnValue();

        LOGGER.info("=== getPossibleEntries result for PICKAXE (power={}) ===", power);
        for (EnchantmentLevelEntry e : list) {
            LOGGER.info(" - {} level {}", e.enchantment.getTranslationKey(), e.level);
        }

        Enchantment bane = Enchantments.BANE_OF_ARTHROPODS;

        if (!bane.isAvailableForRandomSelection()) return;
        if (!treasureAllowed && bane.isTreasure()) return;

        for (EnchantmentLevelEntry e : list) {
            if (e.enchantment == bane) return;
        }

        int chosenLevel = -1;
        for (int lvl = bane.getMaxLevel(); lvl >= bane.getMinLevel(); lvl--) {
            if (power >= bane.getMinPower(lvl) && power <= bane.getMaxPower(lvl)) {
                chosenLevel = lvl;
                break;
            }
        }
        if (chosenLevel < 0) return;

        ArrayList<EnchantmentLevelEntry> out = new ArrayList<>(list);
        out.add(new EnchantmentLevelEntry(bane, chosenLevel));
        cir.setReturnValue(out);

        LOGGER.info("Added BANE_OF_ARTHROPODS level {} to PICKAXE pool", chosenLevel);
    }
}
