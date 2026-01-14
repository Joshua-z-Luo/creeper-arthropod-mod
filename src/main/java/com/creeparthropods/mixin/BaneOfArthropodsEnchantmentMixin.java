package com.creeparthropods.mixin;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageEnchantment.class)
public class BaneOfArthropodsEnchantmentMixin {

    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
    private void creeparthropods$allowPickaxes(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        DamageEnchantment self = (DamageEnchantment)(Object)this;

        if (self.typeIndex == DamageEnchantment.ARTHROPODS_INDEX
                && stack.isIn(ItemTags.PICKAXES)) {
            cir.setReturnValue(true);
        }
    }
}
