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


    private static ResourceKey<Enchantment> key(final String id) {
        return ResourceKey.create(Registries.ENCHANTMENT, EtherealEnchantments.id(id));
    }

//    public static void bootstrap(final BootstrapContext<Enchantment> context) {
//        HolderGetter<Item> items = context.lookup(Registries.ITEM);
//
//        register(context, AFFLICTION,
//            Enchantment.enchantment(
//                Enchantment.definition(
//                    items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
//                    items.getOrThrow(ItemTags.MELEE_WEAPON_ENCHANTABLE),
//                    5,
//                    5,
//                    Enchantment.dynamicCost(5, 8),
//                    Enchantment.dynamicCost(25, 8),
//                    2,
//                    EquipmentSlotGroup.MAINHAND
//                )
//            )
//                .withEffect(
//                    EnchantmentEffectComponents.POST_ATTACK,
//                    EnchantmentTarget.ATTACKER,
//                    EnchantmentTarget.VICTIM,
//                    new TransferStatusEffects(
//                        LevelBasedValue.lookup(List.of(0.25f, 0.5f, 0.875f), LevelBasedValue.constant(1f))
//                    )
//                )
//        );
//    }

    private static void register(final BootstrapContext<Enchantment> context, final ResourceKey<Enchantment> key, final Enchantment.Builder builder) {
        context.register(key, builder.build(key.identifier()));
    }
}
