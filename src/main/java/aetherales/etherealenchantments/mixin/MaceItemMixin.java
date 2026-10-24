package aetherales.etherealenchantments.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.MaceItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MaceItem.class)
public class MaceItemMixin {
    @Inject(method = "getAttackDamageBonus", at = @At("RETURN"), cancellable = true)
    private void adjustForGravity(Entity victim, float ignoredDamage, DamageSource damageSource, CallbackInfoReturnable<Float> cir) {
        Entity entity = damageSource.getDirectEntity();

        if (entity instanceof LivingEntity living) {
            cir.setReturnValue(
                (float) (cir.getReturnValueF() * Math.pow((living.getAttributeValue(Attributes.GRAVITY) / living.getAttributeBaseValue((Attributes.GRAVITY))), 2))
            );
        }
    }

}
