package aetherales.etherealenchantments.mixin;

import aetherales.etherealenchantments.registry.EEEnchantments;
import aetherales.etherealenchantments.util.EnchantmentUtil;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {
//    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;get(Lnet/minecraft/core/component/DataComponentType;)Ljava/lang/Object;"), cancellable = true)
//    private static void cancelOnUse(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir, @Local(name = "stack") ItemStack stack) {
//        if (EnchantmentUtil.getLevel(EEEnchantments.UNWIELDINESS_CURSE.identifier(), stack.getEnchantments()) > 0) {
//            cir.setReturnValue(InteractionResult.FAIL);
//        }
//    }

//    @Inject(method = "getDestroySpeed", at = @At("HEAD"), cancellable = true)
//    public void getDestroySpeed(ItemStack itemStack, BlockState state, CallbackInfoReturnable<Float> cir) {
//        if (EnchantmentUtil.getLevel(EEEnchantments.UNWIELDINESS_CURSE.identifier(), itemStack.getEnchantments()) > 0) {
//            cir.setReturnValue(0.0f);
//        }
//    }
}
