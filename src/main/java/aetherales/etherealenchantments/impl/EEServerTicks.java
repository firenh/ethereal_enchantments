package aetherales.etherealenchantments.impl;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

public class EEServerTicks {
    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(EEServerTicks::endTick);
    }

    private static void endTick(MinecraftServer server) {
        int tick = server.getTickCount();
        ObliterationImpl.obliterationTick(server, tick);
    }
}
