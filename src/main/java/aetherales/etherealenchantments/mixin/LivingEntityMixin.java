package aetherales.etherealenchantments.mixin;

import aetherales.etherealenchantments.entitydata.SoulboundInventory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(at = @At("HEAD"), method = "dropAllDeathLoot")
    private void init(CallbackInfo info) {
        if (((LivingEntity)(Object)this) instanceof ServerPlayer serverPlayer) {
            SoulboundInventory.saveItems(serverPlayer);
        }
    }
}