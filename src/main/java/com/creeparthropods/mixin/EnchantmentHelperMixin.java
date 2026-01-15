package com.creeparthropods.mixin;

import com.creeparthropods.config.CreeperArthropodsConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    @Inject(method = "getPossibleEntries", at = @At("RETURN"), cancellable = true)
    private static void creeparthropods$addBaneForPickaxes(
            int power,
            ItemStack stack,
            boolean treasureAllowed,
            CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir
    ) {
        if (CreeperArthropodsConfig.INSTANCE != null
                && CreeperArthropodsConfig.INSTANCE.disableEnchantingTable) {
            return;
        }

        if (stack == null || !(stack.getItem() instanceof PickaxeItem)) return;

        List<EnchantmentLevelEntry> list = cir.getReturnValue();
        if (list == null) return;

        Enchantment bane = Enchantments.BANE_OF_ARTHROPODS;

        if (!bane.isAvailableForRandomSelection()) return;
        if (!treasureAllowed && bane.isTreasure()) return;

        for (EnchantmentLevelEntry e : list) {
            if (e.enchantment == bane) return;
        }

        int chosenLevel = -1;
        for (int lvl = bane.getMaxLevel(); lvl >= bane.getMinLevel(); lvl--) {
            if (power >= bane.getMinPower(lvl)) {
                chosenLevel = lvl;
                break;
            }
        }
        if (chosenLevel < 0) return;

        ArrayList<EnchantmentLevelEntry> out = new ArrayList<>(list);
        out.add(new EnchantmentLevelEntry(bane, chosenLevel));
        cir.setReturnValue(out);
    }
}
