package aetherales.etherealenchantments.mixin;

import aetherales.etherealenchantments.entitydata.MomentumData;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Player.class)
public interface PlayerAccessor {
    @Accessor("momentumData")
    public MomentumData getMomentumData();
}
