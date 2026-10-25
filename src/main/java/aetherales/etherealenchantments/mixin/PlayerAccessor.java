package aetherales.etherealenchantments.mixin;

import aetherales.etherealenchantments.entitydata.MomentumData;
import aetherales.etherealenchantments.entitydata.SoulboundInventory;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Optional;

@Mixin(Player.class)
public interface PlayerAccessor {
    @Accessor("momentumData")
    public MomentumData getMomentumData();

    @Accessor("momentumData")
    public void setMomentumData(MomentumData data);

    @Accessor("soulboundInventory")
    public Optional<SoulboundInventory> getSoulboundInventory();

    @Accessor("soulboundInventory")
    public void setSoulboundInventory(Optional<SoulboundInventory> inventory);

    @Accessor("inventory")
    public void setInventory(Inventory inventory);

    @Accessor("lastHurtTime")
    public long getLastHurtTime();

    @Accessor("lastHurtTime")
    public void setLastHurtTime(long lastHurtTime);

}
