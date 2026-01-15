package com.creeparthropods.mixin;

import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.creeparthropods.config.CreeperArthropodsConfig;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@Inject(method = "getGroup", at = @At("RETURN"), cancellable = true)
	private void modifyGetGroup(CallbackInfoReturnable<EntityGroup> info) {
		if (CreeperArthropodsConfig.INSTANCE != null
				&& CreeperArthropodsConfig.INSTANCE.disableCreeperArthropod) {
			return;
		}
		if (((LivingEntity)(Object)this) instanceof CreeperEntity) {
			info.setReturnValue(EntityGroup.ARTHROPOD);
		}
	}
}
