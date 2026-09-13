package aetherales.etherealenchantments.entitydata;

import aetherales.etherealenchantments.EtherealEnchantments;
import aetherales.etherealenchantments.mixin.PlayerAccessor;
import aetherales.etherealenchantments.registry.EEEnchantments;
import aetherales.etherealenchantments.util.EEUtil;
import aetherales.etherealenchantments.util.EnchantmentUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.Objects;
import java.util.Optional;

public class MomentumData {
    private static final int MAX_TIME_FOR_STREAK = 5 * 20;
    private static final int MAX_LEVEL = 5;
    private static final double MINING_EFFICIENCY_PER_LEVEL = (1.0 / 5.0) * (0.5); // requires ~ 100 blocks to reach Eff 5
    public static final Identifier MOMENTUM_ATTRIBUTE = EtherealEnchantments.id("mainhand/momentum");


    private long lastMined;
    private int streak;
    private Optional<Identifier> target;

    public static final Codec<MomentumData> CODEC = RecordCodecBuilder.create(
        i -> i.group(
                Codec.LONG
                    .fieldOf("last_mined")
                    .orElseGet(() -> 0L)
                    .forGetter(MomentumData::getLastMined),
                Codec.INT
                    .fieldOf("streak")
                    .orElseGet(() -> 0)
                    .forGetter(MomentumData::getStreak),
                Codec.optionalField(
                    "target",
                    Identifier.CODEC,
                    true
                ).forGetter(MomentumData::getTarget)
        ).apply(i, MomentumData::new)
    );

    public MomentumData(long lastMined, int streak, Optional<Identifier> target) {
        this.lastMined = lastMined;
        this.streak = streak;
        this.target = target;
    }

    public static void tryUpdatePlayerEndOfMine(Player player, Block block) {
        int level = EnchantmentUtil.getLevel(EEEnchantments.MOMENTUM.identifier(), player.getMainHandItem().getEnchantments());
//        EtherealEnchantments.LOGGER.info("Momentum Level: {}", level);

        if (level > 0) {
            updatePlayerEndOfMine(player, block, level);
        } else {
            clearStreak(player);
        }
    }

    public static void updatePlayerEndOfMine(Player player, Block block, int level) {
        try {
            long time = Objects.requireNonNull(player.level().getServer()).getTickCount();

            MomentumData momentumData = ((PlayerAccessor)(Object)player).getMomentumData();

            momentumData.updateStreak(
                BuiltInRegistries.BLOCK.getKey(block),
                time, level
            );

            momentumData.updateAttribute(player);

        } catch (NullPointerException _ ) {}
    }

    public void updateStreak(Identifier newTarget, long time, int increment) {
        if (target.isEmpty()) {
            setTarget(Optional.of(newTarget));
            setLastMined(time);
            return;
        }

        if (
            target.get().equals(newTarget)
        ) {
            incrementStreak(increment);
            setLastMined(time);
        } else {
            setTarget(Optional.empty());
            setStreak(0);
        }
    }

    private static void clearStreak(Player player) {
        if (Objects.isNull(((PlayerAccessor)(Object)player).getMomentumData())) return;

        MomentumData momentumData = ((PlayerAccessor)(Object)player).getMomentumData();
        momentumData.setStreak(0);
        momentumData.setTarget(Optional.empty());
        momentumData.updateAttribute(player);
    }

    public static void updateTime(Player player, ItemStack stack) {
        if (EnchantmentUtil.getLevel(EEEnchantments.MOMENTUM.identifier(), stack.getEnchantments()) == 0) {
            return;
        }

        try {
            long time = Objects.requireNonNull(player.level().getServer()).getTickCount();
            MomentumData momentumData = ((PlayerAccessor)(Object)player).getMomentumData();

            momentumData.updateTime(time, player);
        } catch (NullPointerException _) {}
    }

    public void updateTime(long time, Player player) {
        if (outOfTime(time)) {
            setTarget(Optional.empty());
            setStreak(0);
            updateAttribute(player);
        }
    }

    private boolean outOfTime(long time) {
        EtherealEnchantments.LOGGER.info("time since: {}", time - lastMined < MAX_TIME_FOR_STREAK);

        return (time - lastMined > MAX_TIME_FOR_STREAK);
    }

    public double getAttributeStrength() {
        return streak * MINING_EFFICIENCY_PER_LEVEL;
    }

    public void updateAttribute(Player player) {
        if (Objects.isNull(player.getAttribute(Attributes.MINING_EFFICIENCY))) return;

        Objects.requireNonNull(player.getAttribute(Attributes.MINING_EFFICIENCY)).removeModifier(MOMENTUM_ATTRIBUTE);
        if (streak == 0) return;

        double strength = getAttributeStrength();
        Objects.requireNonNull(player.getAttribute(Attributes.MINING_EFFICIENCY)).addTransientModifier(
            new AttributeModifier(MOMENTUM_ATTRIBUTE, strength, AttributeModifier.Operation.ADD_VALUE)
        );

    }








    public long getLastMined() {
        return lastMined;
    }

    public void setLastMined(long lastMined) {
        this.lastMined = lastMined;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public void incrementStreak(int i) {
        this.streak += i;
    }

    public Optional<Identifier> getTarget() {
        return target;
    }

    public void setTarget(Optional<Identifier> target) {
        this.target = target;
    }
}
