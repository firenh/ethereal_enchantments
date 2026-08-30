package aetherales.etherealenchantments.mixin;

import aetherales.etherealenchantments.registry.EEEnchantments;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Enchantments.class)
public class EnchantmentsMixin {
    @Inject(method = "bootstrap", at = @At("RETURN"))
    private static void bootstrap(BootstrapContext<Enchantment> context, CallbackInfo info) {
//        EEEnchantments.bootstrap(context);
    }
}
