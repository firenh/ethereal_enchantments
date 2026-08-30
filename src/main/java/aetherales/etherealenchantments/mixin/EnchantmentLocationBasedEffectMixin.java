package aetherales.etherealenchantments.mixin;

import aetherales.etherealenchantments.EtherealEnchantments;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.world.item.enchantment.effects.EnchantmentLocationBasedEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentLocationBasedEffect.class)
public interface EnchantmentLocationBasedEffectMixin {
	@Inject(at = @At("RETURN"), method = "bootstrap")
	private static void bootstrap(Registry<MapCodec<? extends EnchantmentLocationBasedEffect>> registry, CallbackInfoReturnable<MapCodec<? extends EnchantmentLocationBasedEffect>> info) {
//		EtherealEnchantments.LOGGER.info("registering enchantment effects!!");
//
//		Registry.register(registry, EtherealEnchantments.id("transfer_status_effects"), TransferStatusEffects.CODEC);
//
//		registry.listElementIds().forEach(e -> {
//			EtherealEnchantments.LOGGER.info(e.toString());
//		});
	}
}