package xor7studio.argonlibrary.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static xor7studio.argonlibrary.ArgonLibrary.openMenuFlag;

@Mixin(ScreenHandler.class)
public class ScreenHandlerMixin {
    @Inject(method ="Lnet/minecraft/screen/ScreenHandler;" +
                    "internalOnSlotClick(" +
                    "IILnet/minecraft/screen/slot/SlotActionType;" +
                    "Lnet/minecraft/entity/player/PlayerEntity;)V",
            at = @At("HEAD"),
            cancellable = true)
    private void onInternal(
            int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo info){
        if(openMenuFlag.contains(player)) info.cancel();
    }
}
