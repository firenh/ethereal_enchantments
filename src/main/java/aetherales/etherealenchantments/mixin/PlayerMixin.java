package aetherales.etherealenchantments.mixin;

import aetherales.etherealenchantments.entitydata.MomentumData;
import aetherales.etherealenchantments.entitydata.SoulboundInventory;
import aetherales.etherealenchantments.util.EnchantmentUtil;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Unique
    private MomentumData momentumData = new MomentumData(0, 0, Optional.empty());

    @Unique
    Optional<SoulboundInventory> soulboundInventory = Optional.empty();

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;createAttackSource(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/damagesource/DamageSource;"), cancellable = true)
    private void cancelMyAttack(Entity entity, CallbackInfo ci, @Local(name = "attackingItemStack") ItemStack attackingItemStack) {
        boolean dropped = EnchantmentUtil.maybeDropHandItem(attackingItemStack, (Player) (Object) this, InteractionHand.MAIN_HAND, 0.015f);
        if (dropped) ci.cancel();
    }
}
