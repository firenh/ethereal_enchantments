package aetherales.etherealenchantments.impl;

import aetherales.etherealenchantments.registry.EEEnchantments;
import aetherales.etherealenchantments.util.EnchantmentUtil;
import net.minecraft.util.Prediction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemStackWithSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

public class DisarmingImpl {
    public static void impl(ItemStack rod, @Nullable Entity hookedIn, RandomSource random) {
        if (Objects.isNull(hookedIn) || !(hookedIn instanceof LivingEntity living)) return;

        int disarmingLevel = EnchantmentUtil.getLevel(EEEnchantments.DISARMING.identifier(), rod.getEnchantments());
        float chance = disarmingChance(disarmingLevel);

        if (random.nextFloat() > chance) return;

        dropItemFromEntity(living);
    }

    private static float disarmingChance(int level) {
        return 0.5f * ((float) level) / 3;
    }

    private static void dropItemFromEntity(LivingEntity entity) {
        ItemStack handItem = entity.getWeaponItem();

        if (!handItem.isEmpty()) {
            entity.drop(handItem, true, Prediction.SERVER_ONLY);
            entity.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);

            if (entity instanceof Mob mob) {
                mob.setCanPickUpLoot(true);
            }
        }
    }
}
