package aetherales.etherealenchantments.impl;

import aetherales.etherealenchantments.EtherealEnchantments;
import aetherales.etherealenchantments.registry.EEEnchantments;
import aetherales.etherealenchantments.util.EnchantmentUtil;
import com.google.common.collect.HashMultimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.w3c.dom.Attr;

import java.util.Objects;

public class FrenzyImpl {
    public static final Identifier FRENZY_ATTRIBUTE = EtherealEnchantments.id("mainhand/frenzy");
    private static final double MAX_FRENZY_MINING_EFFICIENCY = 30;
    private static final int MAX_LEVEL = 5;
    private static final double MINING_EFFICIENCY_REQUIRED_TO_INSTAMINE_DEEPLSLATE = 55.28625 - 26; //Efficiency 5 = 26
    private static final double PROPORTION_AT_WHICH_INSTAMINE_DEEPSLATE = 1.0 / 5.0;
    private static final double FRENZY_EFFECTIVENESS_AT_CRITICAL_FRACTION = calcFrenzyAttributeIncrease(1, PROPORTION_AT_WHICH_INSTAMINE_DEEPSLATE);

    public static void frenzyImpl(ItemStack stack, Player player) {
        int level = EnchantmentUtil.getLevel(EEEnchantments.FRENZY.identifier(), stack.getEnchantments());

        if (level <= 0) {
            applyAttributes(0, level, player);
        } else {
            applyAttributes(
                calcFrenzyAttributeValue(calcFrenzyAttributeIncrease(stack)), level, player
            );
        }
    }

    private static void applyAttributes(double frenzyStrength, int level, Player player) {
        if (Objects.isNull(player.getAttribute(Attributes.MINING_EFFICIENCY))) return;
        Objects.requireNonNull(player.getAttribute(Attributes.MINING_EFFICIENCY)).removeModifier(FRENZY_ATTRIBUTE);

        if (frenzyStrength > 0) {
            Objects.requireNonNull(player.getAttribute((Attributes.MINING_EFFICIENCY))).addTransientModifier(
                new AttributeModifier(FRENZY_ATTRIBUTE, ((double) level / MAX_LEVEL) * frenzyStrength, AttributeModifier.Operation.ADD_VALUE)
            );
        }
    }

    private static double calcFrenzyAttributeIncrease(ItemStack stack) {
        final double maxDurability = stack.getMaxDamage();
        final double durability = maxDurability - stack.getDamageValue();

        return calcFrenzyAttributeIncrease(maxDurability, durability);
    }

    private static double calcFrenzyAttributeIncrease(double maxDurability, double durability) {
        return Math.pow(((durability - maxDurability) / maxDurability), 2);

    }

    private static double calcFrenzyAttributeValue(double increaseAmount) {
        return (increaseAmount * MINING_EFFICIENCY_REQUIRED_TO_INSTAMINE_DEEPLSLATE) / FRENZY_EFFECTIVENESS_AT_CRITICAL_FRACTION;
    }
}
