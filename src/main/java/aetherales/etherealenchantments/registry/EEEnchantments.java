package aetherales.etherealenchantments.registry;

import aetherales.etherealenchantments.EtherealEnchantments;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class EEEnchantments {
    public static final ResourceKey<Enchantment> OBLITERATION = key("obliteration_curse");

    private static ResourceKey<Enchantment> key(final String id) {
        return ResourceKey.create(Registries.ENCHANTMENT, EtherealEnchantments.id(id));
    }
}
