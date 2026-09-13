package aetherales.etherealenchantments.enchantmentcomponents;

import aetherales.etherealenchantments.impl.AfflictionImpl;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;

public record AfflictionEnchantmentEffect(LevelBasedValue level) implements EEEnchantmentEntityEffect {
//    public static final MapCodec<AfflictionEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(
//        i -> i.(
//            LevelBasedValue.CODEC
//                .fieldOf("level")
//                .forGetter(AfflictionEnchantmentEffect::level)
//        ).apply(i, AfflictionEnchantmentEffect::new)
//    );

    @Override
    public void apply(ServerLevel serverLevel, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 position) {
        if (entity instanceof LivingEntity targetLiving && !Objects.isNull(item.owner())) {
            LivingEntity owner = item.owner();
            RandomSource random = owner.getRandom();

            if (owner.getActiveEffects().isEmpty()) return;

            List<MobEffectInstance> effectInstances = owner.getActiveEffects().stream().filter(
                e -> !e.isInfiniteDuration() && !e.getEffect().value().isBeneficial()
            ).toList();

            if (effectInstances.isEmpty()) return;

            MobEffectInstance effect = effectInstances.get(random.nextInt(effectInstances.size()));
            int toApplyDuration = (int) (effect.getDuration() * AfflictionImpl.proportionToTransfer(enchantmentLevel));

            MobEffectInstance toApplyEffect = AfflictionImpl.copy(effect, toApplyDuration);
            MobEffectInstance toReduceEffect = AfflictionImpl.copy(effect, effect.getDuration() - toApplyDuration);

            toApplyEffect.mapDuration(o -> toApplyDuration);
            toReduceEffect.mapDuration(o -> o -toApplyDuration);

            targetLiving.addEffect(toApplyEffect);

            owner.removeEffect(effect.getEffect());
            owner.addEffect(toReduceEffect);
        }
    }

    @Override
    public void onChangedBlock(ServerLevel serverLevel, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 position, boolean becameActive) {
        EEEnchantmentEntityEffect.super.onChangedBlock(serverLevel, enchantmentLevel, item, entity, position, becameActive);
    }

    @Override
    public void onDeactivated(EnchantedItemInUse item, Entity entity, Vec3 position, int level) {
        EEEnchantmentEntityEffect.super.onDeactivated(item, entity, position, level);
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return null;
    }
}
