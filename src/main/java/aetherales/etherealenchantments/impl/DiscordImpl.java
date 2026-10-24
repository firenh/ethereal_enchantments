package aetherales.etherealenchantments.impl;

import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Objects;

public class DiscordImpl {
    private static final int MAXIMUM_ACTIVE_EFFECTS = 8;

    record DiscordEffect(Holder<MobEffect> effect, int minDuration, int maxDuration, int maxAmplifier) {}

    private static DiscordEffect of(Holder<MobEffect> effect, int minDuration, int maxDuration, int maxAmplifier) {
        return new DiscordEffect(effect, minDuration, maxDuration, maxAmplifier);
    }

    private static final List<DiscordEffect> POSSIBLE_POSITIVE_EFFECTS = List.of(
        of(MobEffects.HASTE, 20 * 5, 20 * 45, 0),
        of(MobEffects.ABSORPTION, 20 * 7, 20 * 20, 1),
        of(MobEffects.FIRE_RESISTANCE, 20 * 5, 20 * 30, 0),
        of(MobEffects.INSTANT_HEALTH, 1, 1, 0),
        of(MobEffects.INVISIBILITY, 20 * 5, 20 * 20, 0),
        of(MobEffects.JUMP_BOOST, 20 * 3, 20 * 10, 3),
        of(MobEffects.NIGHT_VISION, 20 * 15, 20 * 60, 0),
        of(MobEffects.REGENERATION, 20 * 2, 20 * 20, 1),
        of(MobEffects.RESISTANCE, 20 * 3, 20 * 20, 1),
        of(MobEffects.SATURATION, 1, 20, 0),
        of(MobEffects.SLOW_FALLING, 20 * 5, 20 * 20, 0),
        of(MobEffects.SPEED, 20 * 5, 20 * 30, 2),
        of(MobEffects.STRENGTH, 20 * 2, 20 * 15, 1),
        of(MobEffects.WATER_BREATHING, 20 * 10, 20 * 60, 0),
        of(MobEffects.OOZING, 20 * 5, 20 * 120, 0),
        of(MobEffects.WIND_CHARGED, 20 * 5, 20 * 120, 0)
    );

    private static final List<DiscordEffect> POSSIBLE_NEGATIVE_EFFECTS = List.of(
        of(MobEffects.MINING_FATIGUE, 20 * 5, 20 * 30, 1),
        of(MobEffects.BLINDNESS, 20 * 1, 20 * 20, 0),
        of(MobEffects.DARKNESS, 20 * 5, 20 * 30, 0),
        of(MobEffects.GLOWING, 20 * 20, 20 * 45, 0),
        of(MobEffects.HUNGER, 20 * 20, 20 * 40, 3),
        of(MobEffects.INFESTED, 20 * 2, 20 * 6, 0),
        of(MobEffects.INSTANT_DAMAGE, 1, 1, 1),
        of(MobEffects.LEVITATION, 20 * 2, 20 * 10, 0),
        of(MobEffects.NAUSEA, 20 * 5, 20 * 20, 0),
        of(MobEffects.POISON, 20 * 5, 20 * 15, 1),
        of(MobEffects.SLOWNESS, 20 * 10, 20 * 60, 2),
        of(MobEffects.WEAKNESS, 20 * 10, 20 * 40, 2),
        of(MobEffects.WITHER, 20 * 3, 20 * 15, 1)
    );

    private static List<DiscordEffect> listFromLevel(int level, RandomSource random) {
        double chance = 1.0 / (((double) level * 0.5)+ 1.5);
        return random.nextFloat() < chance ? POSSIBLE_POSITIVE_EFFECTS : POSSIBLE_NEGATIVE_EFFECTS;
    }

    private static MobEffectInstance getInstance(DiscordEffect discordEffect, RandomSource random) {
        return new MobEffectInstance(
            discordEffect.effect(),
            random.nextIntBetweenInclusive(discordEffect.minDuration, discordEffect.maxDuration),
            random.nextIntBetweenInclusive(0, discordEffect.maxAmplifier)
        );
    }

    public static void impl(int enchantmentLevel, ItemStack item, LivingEntity owner, @NonNull Entity entity) {
        if (entity instanceof LivingEntity targetLiving && !Objects.isNull(owner)) {
            RandomSource random = owner.getRandom();
            if (random.nextFloat() > 0.1f * (enchantmentLevel + 6)) return;

            if (targetLiving.getActiveEffects().size() > MAXIMUM_ACTIVE_EFFECTS - 1) return;

            List<DiscordEffect> possibleEffects = listFromLevel(enchantmentLevel, random);

            MobEffectInstance effect = getInstance(
                possibleEffects.get(random.nextInt(possibleEffects.size())),
                random
            );

            targetLiving.addEffect(effect);
        }
    }
}
