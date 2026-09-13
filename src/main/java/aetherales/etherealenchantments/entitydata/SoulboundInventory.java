package aetherales.etherealenchantments.entitydata;

import aetherales.etherealenchantments.EtherealEnchantments;
import aetherales.etherealenchantments.mixin.PlayerAccessor;
import aetherales.etherealenchantments.registry.EEEnchantments;
import aetherales.etherealenchantments.util.EnchantmentUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Prediction;
import net.minecraft.world.ItemStackWithSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.gossip.GossipContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public record SoulboundInventory(List<ItemStackWithSlot> items) {
    private static final Codec<Integer> SLOT_CODEC = Codec.intRange(0, 255);
    public static final String NBT_KEY_FOR_SOULBOUND_INVENTORY = EtherealEnchantments.MOD_ID + "$soulbound_inventory";

    public static final Codec<SoulboundInventory> CODEC =
        ItemStackWithSlot.CODEC.listOf().xmap(
            SoulboundInventory::new, SoulboundInventory::items);


    public static SoulboundInventory fromInventory(Inventory inventory) {
        final int inventorySize = inventory.getContainerSize();
        ArrayList<ItemStackWithSlot> items = new ArrayList<>();

        for (int i = 0; i < inventorySize; i += 1) {
            ItemStack inventoryStack = inventory.getItem(i);

            if (!inventoryStack.isEmpty()) {
                items.add(new ItemStackWithSlot(i, inventoryStack));
            }
        }

        return new SoulboundInventory(items);
    }


    public void fillInventory(Inventory inventory, LivingEntity entity) {
        final int itemsSize = inventory.getContainerSize();

        items.forEach((stackAndSlot) -> {
            if (inventory.getItem(stackAndSlot.slot()).isEmpty()) {
                inventory.setItem(stackAndSlot.slot(), stackAndSlot.stack());
            } else {
                entity.drop(stackAndSlot.stack(), true, Prediction.SERVER_ONLY);
            }
        });
    }


    //

    public static void saveItems(ServerPlayer player) {
        Inventory inventory = player.getInventory();
        final int size = inventory.getContainerSize();
        ArrayList<ItemStackWithSlot> soulboundItems = new ArrayList<>();

        EtherealEnchantments.LOGGER.info("Saving items!");

        for (int i = 0; i < size; i += 1) {
            ItemStack item = inventory.getItem(i);

            if (EnchantmentUtil.getLevel(EEEnchantments.SOULBOUND.identifier(), item.getEnchantments()) > 0) {
                EtherealEnchantments.LOGGER.info("Saving this item: {}", item);

                soulboundItems.add(new ItemStackWithSlot(i, item));
                inventory.setItem(i, ItemStack.EMPTY);
            }
        }

        if (!soulboundItems.isEmpty()) {
            ((PlayerAccessor)(Object)player).setSoulboundInventory(Optional.of(
                new SoulboundInventory(soulboundItems)
            ));
        }

    }

    public static void restoreItemsFromSoulbound(ServerPlayer oldPlayer, ServerPlayer player) {
        if (((PlayerAccessor)oldPlayer).getSoulboundInventory().isEmpty()) {
            return;
        }

        SoulboundInventory soulboundInventory = ((PlayerAccessor)oldPlayer).getSoulboundInventory().get();
        soulboundInventory.fillInventory(player.getInventory(), player);

        ((PlayerAccessor)oldPlayer).setSoulboundInventory(Optional.empty());
        ((PlayerAccessor)player).setSoulboundInventory(Optional.empty());
    }
}