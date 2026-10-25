package aetherales.etherealenchantments.registry;

import aetherales.etherealenchantments.EtherealEnchantments;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.LevelBasedValue;

import java.util.List;

public class EEEnchantments {
    public static final ResourceKey<Enchantment> OBLITERATION = key("obliteration_curse");
    public static final ResourceKey<Enchantment> AFFLICTION = key("affliction");
    public static final ResourceKey<Enchantment> UNWIELDINESS_CURSE = key("unwieldiness_curse");
    public static final ResourceKey<Enchantment> BUTTERFINGERS_CURSE = key("butterfingers_curse");
    public static final ResourceKey<Enchantment> FRENZY = key("frenzy");
    public static final ResourceKey<Enchantment> MOMENTUM = key("momentum");
    public static final ResourceKey<Enchantment> SOULBOUND = key("soulbound");
    public static final ResourceKey<Enchantment> DISCORD = key("discord");
    public static final ResourceKey<Enchantment> VELOCITY = key("velocity");
    public static final ResourceKey<Enchantment> VAMPIRISM = key("vampirism");
    public static final ResourceKey<Enchantment> ADRENALINE = key("adrenaline");
    public static final ResourceKey<Enchantment> BOUNCY = key("bouncy");



    private static ResourceKey<Enchantment> key(final String id) {
        return ResourceKey.create(Registries.ENCHANTMENT, EtherealEnchantments.id(id));
    }

    private static void register(final BootstrapContext<Enchantment> context, final ResourceKey<Enchantment> key, final Enchantment.Builder builder) {
        context.register(key, builder.build(key.identifier()));
    }
}
