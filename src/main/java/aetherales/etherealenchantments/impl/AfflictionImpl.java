package aetherales.etherealenchantments.impl;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Objects;

public class AfflictionImpl {
    private static final float[] AFFLICTION_PROPORTIONS = new float[]{0.25f, 0.5f, 0.875f};

    public static void afflictionImpl(int enchantmentLevel, ItemStack item, LivingEntity owner, @NonNull Entity entity) {
        if (entity instanceof LivingEntity targetLiving && !Objects.isNull(owner)) {
            RandomSource random = owner.getRandom();

            if (owner.getActiveEffects().isEmpty()) return;

            List<MobEffectInstance> effectInstances = owner.getActiveEffects().stream().filter(
                e -> !e.isInfiniteDuration() && !e.getEffect().value().isBeneficial()
            ).toList();

            if (effectInstances.isEmpty()) return;

            MobEffectInstance effect = effectInstances.get(random.nextInt(effectInstances.size()));
            int toApplyDuration = (int) (effect.getDuration() * proportionToTransfer(enchantmentLevel));

            MobEffectInstance toApplyEffect = copy(effect, toApplyDuration);
            MobEffectInstance toReduceEffect = copy(effect, effect.getDuration() - toApplyDuration);

            toApplyEffect.mapDuration(o -> toApplyDuration);
            toReduceEffect.mapDuration(o -> o -toApplyDuration);

            targetLiving.addEffect(toApplyEffect);

            owner.removeEffect(effect.getEffect());
            owner.addEffect(toReduceEffect);
        }
    }

    public static float proportionToTransfer(int lvl) {
        if (lvl > AFFLICTION_PROPORTIONS.length) return 1;
        return AFFLICTION_PROPORTIONS[lvl - 1];
    }

    public static MobEffectInstance copy(MobEffectInstance effect, int newDuration) {
        return new MobEffectInstance(
            effect.getEffect(),
            newDuration,
            effect.getAmplifier(),
            effect.isAmbient(),
            effect.isVisible(),
            effect.showIcon()
        );
    }
}
