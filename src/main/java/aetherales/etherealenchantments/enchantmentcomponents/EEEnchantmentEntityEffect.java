package aetherales.etherealenchantments.enchantmentcomponents;

import aetherales.etherealenchantments.EtherealEnchantments;
import eu.pb4.polymer.core.api.utils.PolymerObject;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.ApplyMobEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

public interface EEEnchantmentEntityEffect extends EnchantmentEntityEffect, PolymerObject {
    public static void init() {
//        Registry.register(BuiltInRegistries.ENCHANTMENT_ENTITY_EFFECT_TYPE, EtherealEnchantments.id("affliction"), AfflictionEnchantmentEffect.CODEC);
    }
}
