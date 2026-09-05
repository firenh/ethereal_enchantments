package aetherales.etherealenchantments.mixin;

import aetherales.etherealenchantments.impl.EnchantmentEffectImplementations;
import aetherales.etherealenchantments.util.EnchantmentUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Inject(
        method = "doPostAttackEffectsWithItemSourceOnBreak",
        at = @At("RETURN")
    )
    private static void doPostAttack(
        ServerLevel serverLevel,
        Entity victim,
        DamageSource damageSource,
        @Nullable ItemStack source,
        @Nullable Consumer<Item> attackerlessOnBreak,
        CallbackInfo info
    ) {
        EnchantmentEffectImplementations.doPostAttack(
            serverLevel,
            victim,
            damageSource,
            source,
            attackerlessOnBreak
        );
    }

    @Inject(method = "onHitBlock", at = @At("HEAD"), cancellable = true)
    private static void cancelHitBlock(ServerLevel serverLevel, ItemStack weapon, @Nullable LivingEntity owner, Entity entity, @Nullable EquipmentSlot slot, Vec3 hitLocation, BlockState hitBlock, Consumer<Item> onBreak, CallbackInfo ci) {
        if (entity instanceof Player player) {
            boolean dropped = EnchantmentUtil.maybeDropHandItem(weapon, player, InteractionHand.MAIN_HAND, 0.005f);
            if (dropped) ci.cancel();
        }
    }

    @Inject(method = "onHitBlock", at = @At("RETURN"))
    private static void onHitBlock(ServerLevel serverLevel, ItemStack weapon, @Nullable LivingEntity owner, Entity entity, @Nullable EquipmentSlot slot, Vec3 hitLocation, BlockState hitBlock, Consumer<Item> onBreak, CallbackInfo ci) {
        EnchantmentEffectImplementations.onHitBlock(
            serverLevel,
            weapon,
            owner,
            entity,
            slot,
            hitLocation,
            hitBlock,
            onBreak
        );
    }
}
