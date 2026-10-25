package aetherales.etherealenchantments.impl;

import aetherales.etherealenchantments.EtherealEnchantments;
import aetherales.etherealenchantments.mixin.PlayerAccessor;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public class AdrenalineImpl {
    private static final int COOLDOWN = 2 * 60 * 20;
    public static final String NBT_KEY_FOR_LAST_HURT_TIME = EtherealEnchantments.MOD_ID + "$last_hurt_time";


    public static void impl(int enchantmentLevel, Player player, DamageSource source) {
        long gameTime = player.level().getGameTime();
        if (gameTime - ((PlayerAccessor)player).getLastHurtTime() < COOLDOWN) return;

        applyEffects(player, enchantmentLevel);
    }

    private static void applyEffects(Player player, int level) {
        applyEffect(player, MobEffects.ABSORPTION, 20 * 5 * level, 0);
        applyEffect(player, MobEffects.RESISTANCE, 20 * 5 * level, 0);
        applyEffect(player, MobEffects.FIRE_RESISTANCE, 20 * 12 * level, 0);
        applyEffect(player, MobEffects.REGENERATION, 50, level - 1);
        applyEffect(player, MobEffects.STRENGTH, 30, level - 1);
        applyEffect(player, MobEffects.WATER_BREATHING, 7 * level, 0);
    }

    private static void applyEffect(LivingEntity entity, Holder<MobEffect> effect, int duration, int amplifier) {
        entity.addEffect(
            new MobEffectInstance(
                effect, duration, amplifier
            )
        );
    }
}
