package aetherales.etherealenchantments.mixin;

import aetherales.etherealenchantments.EtherealEnchantments;
import aetherales.etherealenchantments.impl.DisarmingImpl;
import aetherales.etherealenchantments.impl.LurePullStrengthIncreaseImpl;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingHook.class)
public class FishingHookMixin {
    @Shadow @Final
    private int lureSpeed;

    @Shadow
    private Entity hookedIn;

    @Shadow @Final
    private RandomSource syncronizedRandom;

    @Inject(method = "retrieve", at = @At(value = "INVOKE", shift = At.Shift.AFTER,  target = "Lnet/minecraft/world/entity/projectile/FishingHook;pullEntity(Lnet/minecraft/world/entity/Entity;)V"))
    private void disarmItem(ItemStack rod, CallbackInfoReturnable<Integer> cir) {
        DisarmingImpl.impl(rod, hookedIn, syncronizedRandom);
    }

    @ModifyArg(index = 0, method = "pullEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"))
    private Vec3 modifyPullEntity(Vec3 deltaMovement) {
        return LurePullStrengthIncreaseImpl.impl(deltaMovement, lureSpeed);
    }

}
