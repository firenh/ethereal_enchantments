package aetherales.etherealenchantments.impl;

import aetherales.etherealenchantments.EtherealEnchantments;
import aetherales.etherealenchantments.entitydata.MomentumData;
import aetherales.etherealenchantments.mixin.PlayerAccessor;
import aetherales.etherealenchantments.registry.EEEnchantments;
import aetherales.etherealenchantments.util.EnchantmentUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.function.Consumer;

public class EnchantmentEffectImplementations {
    public static void init() {
        ServerLivingEntityEvents.AFTER_DAMAGE.register(EnchantmentEffectImplementations::afterDamageTaken);
    }

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

            int discordLevel = EnchantmentUtil.getLevel(EEEnchantments.DISCORD.identifier(), enchantments);
            if (discordLevel > 0) {
                DiscordImpl.impl(discordLevel, source, livingAttacker, victim);
            }

            int vampirismLevel = EnchantmentUtil.getLevel(EEEnchantments.VAMPIRISM.identifier(), enchantments);
            if (vampirismLevel > 0) {
                VampirismImpl.impl(vampirismLevel, source, livingAttacker, victim);
            }
        }
    }

    public static void onHitBlock(
        ServerLevel serverLevel,
        ItemStack weapon,
        @Nullable LivingEntity owner,
        Entity entity,
        @Nullable EquipmentSlot slot,
        Vec3 hitLocation,
        BlockState hitBlock,
        Consumer<Item> onBreak
    ) {
        if (!Objects.isNull(owner) && owner instanceof Player player) {
            FrenzyImpl.frenzyImpl(weapon, player);
            MomentumData.updateTime(player, weapon);
        }
    }

    public static void afterDamageTaken(LivingEntity living, DamageSource source, float baseDamage, float damage, boolean blocked) {
        EtherealEnchantments.LOGGER.info("eifhnsejfnbef");

        // only applies to players

        if (!(living instanceof Player player)) return;

        int adrenalineLevel = EnchantmentUtil.getSingleSlotArmorLevel(EEEnchantments.ADRENALINE.identifier(), player, EquipmentSlot.CHEST);
        if (adrenalineLevel > 0) {
            AdrenalineImpl.impl(adrenalineLevel, player, source);
        }

        ((PlayerAccessor)(Object)player).setLastHurtTime(player.level().getGameTime());
    }


}
