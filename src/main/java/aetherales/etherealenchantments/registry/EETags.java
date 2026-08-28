package aetherales.etherealenchantments.registry;

import aetherales.etherealenchantments.EtherealEnchantments;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.Item;


public class EETags {
    // Enchantment Tags
    public static final TagKey<Enchantment> NOT_OBLITERATABLE = eof("not_obliteratable");

    // Item Tags
    public static final TagKey<Item> OBLITERATION_IMMUNE = iof("obliteration_immune");

    @SuppressWarnings("null")
    private static <T> TagKey<T> of(ResourceKey<Registry<T>> registry, String id) {
        return TagKey.create(registry, EtherealEnchantments.id(id));
    }

    private static TagKey<Enchantment> eof(String id) {
        return of(Registries.ENCHANTMENT, id);
    }

    private static TagKey<Item> iof(String id) {
        return of(Registries.ITEM, id);
    }
}
