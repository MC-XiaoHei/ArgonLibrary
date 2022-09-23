package xor7studio.argonlibrary.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static xor7studio.argonlibrary.ArgonLibrary.openMenuFlag;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(method ="Lnet/minecraft/server/network/ServerPlayerEntity;" +
                    "dropItem(Lnet/minecraft/item/ItemStack;ZZ)" +
                    "Lnet/minecraft/entity/ItemEntity;",
            at = @At("HEAD"),cancellable = true)
    private void onDrop(
            ItemStack stack,
            boolean throwRandomly,
            boolean retainOwnership,
            CallbackInfoReturnable<ItemEntity> cir){
        if(openMenuFlag.contains((PlayerEntity) (Object)this))
            cir.cancel();
    }
    @Inject(method ="Lnet/minecraft/server/network/ServerPlayerEntity;" +
                    "closeScreenHandler()V"
            ,at = @At("HEAD"))
    private void onCloseInventory(CallbackInfo info){
        openMenuFlag.remove((PlayerEntity) (Object)this);
    }
}
