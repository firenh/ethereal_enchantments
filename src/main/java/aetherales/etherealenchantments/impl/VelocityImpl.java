package aetherales.etherealenchantments.impl;

import aetherales.etherealenchantments.registry.EEEnchantments;
import aetherales.etherealenchantments.util.EnchantmentUtil;
import net.minecraft.world.item.ItemStack;

public class VelocityImpl {
    public static float newPower(float oldPower, ItemStack stack) {
        int level = EnchantmentUtil.getLevel(EEEnchantments.VELOCITY.identifier(), stack.getEnchantments());
        return oldPower * (1.0f + (0.2f * level));
    }
}
