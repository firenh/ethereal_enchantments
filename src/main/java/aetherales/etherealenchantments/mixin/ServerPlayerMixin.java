package aetherales.etherealenchantments.mixin;

import aetherales.etherealenchantments.entitydata.SoulboundInventory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    @Inject(method = "readAdditionalSaveData", at = @At("RETURN"))
    private void readAdditionalSaveData(ValueInput input, CallbackInfo info) {
        if (input.contains(SoulboundInventory.NBT_KEY_FOR_SOULBOUND_INVENTORY)) {
            ((PlayerAccessor) (Object) this).setSoulboundInventory(
                input.read(SoulboundInventory.NBT_KEY_FOR_SOULBOUND_INVENTORY, SoulboundInventory.CODEC)
            );
        } else {
            ((PlayerAccessor) (Object) this).setSoulboundInventory(Optional.empty());
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("RETURN"))
    protected void addAdditionalSaveData(ValueOutput output, CallbackInfo info) {
        if (((PlayerAccessor) (Object) this).getSoulboundInventory().isPresent()) {
            output.store(
                SoulboundInventory.NBT_KEY_FOR_SOULBOUND_INVENTORY,
                SoulboundInventory.CODEC,
                ((PlayerAccessor) (Object) this).getSoulboundInventory().get()
            );
        }
    }
}
