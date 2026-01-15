package com.creeparthropods.mixin;

import com.creeparthropods.config.CreeperArthropodsConfig;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageEnchantment.class)
public abstract class BaneOfArthropodsEnchantmentMixin {

    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
    private void creeparthropods$allowPickaxes(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (CreeperArthropodsConfig.INSTANCE != null
                && CreeperArthropodsConfig.INSTANCE.disableAnvil) {
            return;
        }

        if (!(stack.getItem() instanceof PickaxeItem)) return;

        DamageEnchantment self = (DamageEnchantment) (Object) this;
        if (self.typeIndex == DamageEnchantment.ARTHROPODS_INDEX) {
            cir.setReturnValue(true);
        }
    }
}
