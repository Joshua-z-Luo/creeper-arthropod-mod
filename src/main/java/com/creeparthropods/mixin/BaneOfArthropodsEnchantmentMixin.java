package com.creeparthropods.mixin;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageEnchantment.class)
public abstract class BaneOfArthropodsEnchantmentMixin {
    private static final Logger LOGGER = LoggerFactory.getLogger("ENCHANTABILITY_DEBUG");

    @Inject(method = "isAcceptableItem", at = @At("HEAD"))
    private void creeparthropods$debugCalled(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() instanceof PickaxeItem) {
            DamageEnchantment self = (DamageEnchantment) (Object) this;
            LOGGER.info("DamageEnchantment#isAcceptableItem called: typeIndex={} stack={}",
                    self.typeIndex, stack.getItem().toString());
        }
    }

    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
    private void creeparthropods$allowPickaxes(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        DamageEnchantment self = (DamageEnchantment) (Object) this;

        if (self.typeIndex == DamageEnchantment.ARTHROPODS_INDEX
                && stack.getItem() instanceof PickaxeItem) {
            LOGGER.info("ALLOWING BANE ON PICKAXE via isAcceptableItem override");
            cir.setReturnValue(true);
        }
    }
}
