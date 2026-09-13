package aetherales.etherealenchantments.util;

import aetherales.etherealenchantments.registry.EEEnchantments;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Prediction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

    public static boolean unwieldy(ItemStack stack, LivingEntity entity) {
        if (getLevel(EEEnchantments.UNWIELDINESS_CURSE.identifier(), stack.getEnchantments()) <= 0) {
            return false;
        }

        return !entity.getOffhandItem().isEmpty();
    }

    public static boolean butterfingers(ItemStack stack, LivingEntity entity, float chancePerLevel) {
        int level = getLevel(EEEnchantments.BUTTERFINGERS_CURSE.identifier(), stack.getEnchantments());

        if (level <= 0) {
            return false;
        }

        return entity.getRandom().nextFloat() < (chancePerLevel * level);
    }

    public static boolean maybeDropHandItem(ItemStack stack, Player player, InteractionHand hand, float butterfingersChancePerLevel) {
        if (unwieldy(stack, player) || butterfingers(stack, player, butterfingersChancePerLevel)) {
            player.drop(stack, true, Prediction.SERVER_ONLY);
            player.setItemInHand(hand, ItemStack.EMPTY);
            return true;
        }

        return false;
    }


}
