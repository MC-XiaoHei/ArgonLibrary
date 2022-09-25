package xor7studio.argonlibrary.mixin;

import net.minecraft.server.dedicated.EulaReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xor7studio.argonlibrary.ArgonLibrary;

@Mixin(EulaReader.class)
public class EulaReaderMixin {
    @Inject(method = "Lnet/minecraft/server/dedicated/EulaReader;checkEulaAgreement()Z",
            at = @At("RETURN"))
    private void onCheckEula(CallbackInfoReturnable<Boolean> cir){
        if(ArgonLibrary.ifIgnoreEula()) cir.setReturnValue(true);
    }
}
