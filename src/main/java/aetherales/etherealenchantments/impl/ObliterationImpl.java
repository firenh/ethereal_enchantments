package aetherales.etherealenchantments.impl;

import aetherales.etherealenchantments.registry.EEEnchantments;
import aetherales.etherealenchantments.registry.EETags;
import aetherales.etherealenchantments.util.EEUtil;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.ArrayList;

public class ObliterationImpl {
    public static final ResourceKey<Enchantment> OBLITERATION = EEEnchantments.OBLITERATION;
    private static final float MAX_LEVEL_CHANCE_RECIPROCAL = 1f;
    public static final int OBLITERATION_CHECK_INTERVAL = 3 * 60 * 20; // = 3 minutes

    /*
        Max sword has 19 lvls of ench
        w/ check interval at 3 mins, should decay fully in 57 mins at max Oblit
        Seems reasonable to me!
     */

    public static void obliterationTick(MinecraftServer server, int tick) {
        if (tick % OBLITERATION_CHECK_INTERVAL == 0) obliterateIterServer(server);
    }

    private static void obliterateIterServer(MinecraftServer server) {
        for (ServerLevel level : server.getAllLevels()) {
            for (ServerPlayer player : level.players()) {
                obliterateIterPlayer(player);
            }
        }
    }

    private static void obliterateIterPlayer(Player player) {
        RandomSource random = player.getRandom();

        Inventory inventory = player.getInventory();
        final int size = inventory.getContainerSize();
        ArrayList<Integer> affectedSlots = new ArrayList<>();

        for (int i = 0; i < size; i += 1) {
            ItemStack item = inventory.getItem(i);

            if (obliterateItem(item, random)) {
                inventory.setItem(i, item);
                affectedSlots.add(i);
            }
        }

        if (player instanceof ServerPlayer serverPlayer) {
            if (!affectedSlots.isEmpty()) {
                sendSound(serverPlayer);
            }

            for (int i : affectedSlots) {
                serverPlayer.connection.send(inventory.createInventoryUpdatePacket(i));
            }
        }
    }

    private static void sendSound(ServerPlayer player) {
        EEUtil.sendSound(player, SoundEvents.CHAIN_BREAK, 1, 1);
    }

    public static boolean obliterateItem(ItemStack item, RandomSource random) {
        if (item.is(EETags.OBLITERATION_IMMUNE) || !shouldObliterateItem(item, random)) return false;

        DataComponentType<ItemEnchantments> componentType = EnchantmentHelper.getComponentType(item);
        Pair<ItemEnchantments, Boolean> obliterationResult = obliterateOne(EnchantmentHelper.getEnchantmentsForCrafting(item), random);

        if (!obliterationResult.getSecond()) return false;

        item.set(componentType, obliterationResult.getFirst());

        return true;
    }


    public static boolean shouldObliterateItem(ItemStack itemStack, RandomSource random) {
        var list = itemStack.getEnchantments().entrySet().stream().filter(
                e -> e.getKey().is(OBLITERATION)
        ).toList();

        if (list.isEmpty()) return false;
        Object2IntMap.Entry<Holder<Enchantment>> obliteration = list.getFirst();
        int maxLevel = obliteration.getKey().value().getMaxLevel();

        return obliterationChanceLevelled(obliteration.getIntValue(), maxLevel, random);
    }

    private static boolean obliterationChanceLevelled(int level, int maxLevel, RandomSource random) {
        float chance = (float) level / (MAX_LEVEL_CHANCE_RECIPROCAL * (float) maxLevel);
        return random.nextFloat() < chance;
    }

    public static Pair<ItemEnchantments, Boolean> obliterateOne(ItemEnchantments enchantments, RandomSource random) {
        ArrayList<Object2IntMap.Entry<Holder<Enchantment>>> enchantmentList = new ArrayList<>(enchantments.entrySet().stream().toList());

        ArrayList<Object2IntMap.Entry<Holder<Enchantment>>> obliteratable = new ArrayList<>(
                enchantmentList.stream().filter(
                        e -> !e.getKey().is(EETags.NOT_OBLITERATABLE)
                ).toList()
        );

        if (obliteratable.isEmpty()) return Pair.of(enchantments, false);

        ArrayList<Object2IntMap.Entry<Holder<Enchantment>>> notObliteratable = new ArrayList<>(
                enchantmentList.stream().filter(
                        e -> e.getKey().is(EETags.NOT_OBLITERATABLE)
                ).toList()
        );

        int index = obliteratable.size() > 1 ? random.nextInt(obliteratable.size()) : 0;
        Object2IntMap.Entry<Holder<Enchantment>> ench = obliteratable.get(index);
        ench.setValue(ench.getIntValue() - 1);
        obliteratable.set(index, ench);

        ArrayList<Object2IntMap.Entry<Holder<Enchantment>>> resultList = new ArrayList<>(obliteratable.stream().filter(e -> e.getIntValue() >= 1).toList());
        resultList.addAll(notObliteratable);
        ItemEnchantments.Mutable mutableEnchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);

        for (Object2IntMap.Entry<Holder<Enchantment>> e : resultList) {
            mutableEnchantments.set(e.getKey(), e.getIntValue());
        }

        return Pair.of(mutableEnchantments.toImmutable(), true);
    }
}
