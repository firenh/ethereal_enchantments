package aetherales.etherealenchantments.impl;

import net.minecraft.world.phys.Vec3;

public class LurePullStrengthIncreaseImpl {
    public static Vec3 impl(Vec3 deltaMovement, int lureSpeed) {
        return deltaMovement.scale(scale(lureSpeed));
    }

    private static double scale(int lureSpeed) {
        double lureLevel = (double) lureSpeed / 100;

        return 1.0 + ((lureLevel) / 3.0);
    }
}
