package aetherales.etherealenchantments;

import aetherales.etherealenchantments.enchantmentcomponents.EEEnchantmentEffects;
import aetherales.etherealenchantments.enchantmentcomponents.TransferStatusEffects;
import aetherales.etherealenchantments.impl.EEServerTicks;
import aetherales.etherealenchantments.util.EEUtil;
import net.fabricmc.api.ModInitializer;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EtherealEnchantments implements ModInitializer {
	public static final String MOD_ID = "ethereal_enchantments";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		EEServerTicks.init();
//		EEEnchantmentEffects.init();
		LOGGER.info("Hello Fabric world!");


//		Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, id("transfer_status_effects"), TransferStatusEffects.CODEC);

	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
