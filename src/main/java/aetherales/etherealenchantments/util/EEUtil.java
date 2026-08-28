package aetherales.etherealenchantments.util;

import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class EEUtil {
    public static void sendSound(ServerPlayer player, SoundEvent sound, float volume, float pitch) {
        player.connection.send(
                new ClientboundSoundPacket(
                        Holder.direct(sound),
                        SoundSource.PLAYERS,
                        player.getX(), player.getY(), player.getZ(),
                        volume,
                        pitch,
                        player.getRandom().nextInt()
                )
        );
    }
}
