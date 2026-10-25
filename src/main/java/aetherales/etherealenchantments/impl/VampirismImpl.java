package aetherales.etherealenchantments.impl;

import aetherales.etherealenchantments.EtherealEnchantments;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Objects;

public class VampirismImpl {
    public static void impl(int enchantmentLevel, ItemStack item, LivingEntity owner, @NonNull Entity entity) {
        if (
            entity instanceof LivingEntity targetLiving
            && !Objects.isNull(owner)
            && targetLiving.getHealth() <= 0
//            && targetLiving.is(EntityTypeTags.UNDEAD)
        ) {
            float targetMaxHealth = targetLiving.getMaxHealth();

            owner.addEffect(new MobEffectInstance(
                MobEffects.REGENERATION,
                regenDuration(targetMaxHealth * proportionToTransfer(enchantmentLevel)),
                1
            ));
        }
    }

    private static int regenDuration(float toHeal) {
        return (int) (toHeal * 25);
    }

    private static float proportionToTransfer(int lvl) {
        return ((float) lvl) / 4;
    }
}
