package aetherales.etherealenchantments.mixin;

import aetherales.etherealenchantments.impl.VelocityImpl;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {
    @ModifyArg(method = "performShooting", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CrossbowItem;shoot(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;Ljava/util/List;FFZLnet/minecraft/world/entity/LivingEntity;)V"), index = 5)
    private static float modifyPower(float power, @Local(argsOnly = true, name = "weapon") ItemStack weapon) {
        return VelocityImpl.newPower(power, weapon);
    }
}
