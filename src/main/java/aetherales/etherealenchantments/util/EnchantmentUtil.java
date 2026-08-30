package aetherales.etherealenchantments.util;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.List;

public class EnchantmentUtil {
    public static int getLevel(Identifier enchantment, ItemEnchantments enchantments) {
        List<Object2IntMap.Entry<Holder<Enchantment>>> list = enchantments.entrySet().stream()
            .filter(e -> e.getKey().is(enchantment))
            .toList();

        if (list.isEmpty()) return 0;
        return list.getFirst().getIntValue();
    }

}
