package aetherales.etherealenchantments.impl;

import aetherales.etherealenchantments.EtherealEnchantments;
import aetherales.etherealenchantments.mixin.PlayerAccessor;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.consume_effects.TeleportRandomlyConsumeEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class WarpingImpl {
    private static final float ACTIVATION_HEALTH = 6.0f;

    private static int getDiameter(int level) {
        return (int) (1.5 * Math.pow(2 + level, 2));
    }

    public static void impl(int enchantmentLevel, LivingEntity entity, DamageSource source) {
        if (entity.getHealth() <= ACTIVATION_HEALTH) teleport(enchantmentLevel, entity);
    }

    private static void teleport(int enchantmentLevel, LivingEntity entity) {
        Level level = entity.level();
        int diameter = getDiameter(enchantmentLevel);

        TeleportRandomlyConsumeEffect teleportRandomly = new TeleportRandomlyConsumeEffect(diameter, true);
        teleportRandomly.apply(level, null, entity);
    }
}
