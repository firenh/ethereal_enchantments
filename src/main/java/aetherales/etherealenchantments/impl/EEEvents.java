package aetherales.etherealenchantments.impl;

import aetherales.etherealenchantments.entitydata.MomentumData;
import aetherales.etherealenchantments.entitydata.SoulboundInventory;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EEEvents {
    public static void init() {
        PlayerBlockBreakEvents.AFTER.register(EEEvents::afterBlockBreak);
        ServerPlayerEvents.AFTER_RESPAWN.register(
            (oldPlayer, newPlayer, alive) ->
                SoulboundInventory.restoreItemsFromSoulbound(oldPlayer, newPlayer)
        );
    }

    private static void afterBlockBreak(Level level, Player player, BlockPos blockPos, BlockState state, BlockEntity blockEntity) {
        MomentumData.tryUpdatePlayerEndOfMine(player, state.getBlock());
    }

}
