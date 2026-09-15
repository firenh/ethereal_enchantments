package aetherales.etherealenchantments.loot;

import aetherales.etherealenchantments.EtherealEnchantments;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.Absolute;
import net.minecraft.world.level.storage.loot.providers.number.ints.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.UniformGenerator;

public class EELootTableModifications {

    public static final ResourceKey<LootTable> BOOK_SOULBOUND = customLootTable("book/soulbound");
    public static final ResourceKey<LootTable> BOOK_UNTETHERING = customLootTable("book/untethering");
    public static final ResourceKey<LootTable> BOOK_AERODYNAMIC = customLootTable("book/aerodynamic");

    public static void init() {
        addToLootTable("chests/ancient_city", BOOK_SOULBOUND, 0.333333333f);
        addToLootTable("chests/stronghold_corridor", BOOK_SOULBOUND, 0.35f);
        addToLootTable("chests/stronghold_crossing", BOOK_SOULBOUND, 0.35f);
        addToLootTable("chests/stronghold_library", BOOK_SOULBOUND, 1f, 2, 3);
        addToLootTable("chests/ancient_city", BOOK_SOULBOUND, 0.333333333f);

        addToLootTable("chests/trial_chambers/reward_ominous", BOOK_UNTETHERING, 0.16666666f);
        addToLootTable("chests/trial_chambers/reward_ominous", BOOK_AERODYNAMIC, 0.1666666f);
    }

    private static void addToLootTable(String targetTable, ResourceKey<LootTable> addedTable, float chance) {
        addToLootTable(targetTable, addedTable, chance, 1, 1);
    }

    private static void addToLootTable(String targetTable, ResourceKey<LootTable> addedTable, float chance, int min, int max) {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, provider) -> {
            if (key.identifier().equals(EtherealEnchantments.vid(targetTable)) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.lootPool()
                    .add(NestedLootTable.lootTableReference(provider.getOrThrow(addedTable)))
                    .setRolls(Holder.direct(
                        new UniformGenerator(
                            Holder.direct(new ConstantValue(min)),
                            Holder.direct(new ConstantValue(max))
                        )
                    ))
                    .when(LootItemRandomChanceCondition.randomChance(chance));
                tableBuilder.withPool(pool);
            }
        });
    }

    private static ResourceKey<LootTable> customLootTable(String id) {
        return lootTableKey(EtherealEnchantments.id(id));
    }

    private static ResourceKey<LootTable> lootTableKey(Identifier id) {
        return ResourceKey.create(Registries.LOOT_TABLE, id);
    }
}
