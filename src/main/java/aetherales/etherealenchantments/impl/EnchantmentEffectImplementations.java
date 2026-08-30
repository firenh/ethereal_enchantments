package aetherales.etherealenchantments.impl;

import aetherales.etherealenchantments.registry.EEEnchantments;
import aetherales.etherealenchantments.util.EnchantmentUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.TargetedConditionalEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.function.Consumer;

public class EnchantmentEffectImplementations {
    public static void doPostAttack(
        ServerLevel serverLevel,
        Entity victim,
        DamageSource damageSource,
        @Nullable ItemStack source,
        @Nullable Consumer<Item> attackerlessOnBreak
    ) {
        if (
            !Objects.isNull(source) &&
            !Objects.isNull(damageSource.getEntity()) &&
            damageSource.getEntity() instanceof LivingEntity livingAttacker
        ) {
            ItemEnchantments enchantments = source.getEnchantments();

            int afflictionLevel = EnchantmentUtil.getLevel(EEEnchantments.AFFLICTION.identifier(), enchantments);
            if (afflictionLevel > 0) {
                AfflictionImpl.afflictionImpl(afflictionLevel, source, livingAttacker, victim);
            }
        }
    }
}
