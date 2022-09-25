package xor7studio.argonlibrary.mixin;

import net.minecraft.server.dedicated.EulaReader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xor7studio.argonlibrary.ArgonLibrary;
import xor7studio.util.Xor7IO;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

@Mixin(EulaReader.class)
public class EulaReaderMixin {
    @Shadow
    @Final
    Path eulaFile;
    @Inject(method = "Lnet/minecraft/server/dedicated/EulaReader;checkEulaAgreement()Z",
            at = @At("RETURN"))
    private void onCheckEula(CallbackInfoReturnable<Boolean> cir){
        if(ArgonLibrary.EulaAPI.ifIgnoreEula()) cir.setReturnValue(true);
    }

    @Inject(method = "Lnet/minecraft/server/dedicated/EulaReader;createEulaFile()V",
            at = @At("HEAD"), cancellable = true)
    private void createTrueEulaFile(CallbackInfo ci){
        if(ArgonLibrary.EulaAPI.ifSetTrueWhenCreateEula()){
            try {
                OutputStream outputStream = Files.newOutputStream(this.eulaFile);
                try {
                    Properties properties = new Properties();
                    properties.setProperty("eula", "true");
                    properties.store(outputStream,"本文件由Argon Library Eula API创建,但并非Argon Library行为,这表明有其他Mod调用了此API.");
                    properties.store(outputStream, "By changing the setting below to TRUE you are indicating your agreement to our EULA (https://account.mojang.com/documents/minecraft_eula).");
                } catch (Throwable var5) {
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (Throwable var4) {
                            var5.addSuppressed(var4);
                        }
                    }
                    throw var5;
                }
                if (outputStream != null)
                    outputStream.close();
            } catch (Exception var6) {
                Xor7IO.error("自动保存Eula文件失败!");
            }
            ci.cancel();
        }
    }
}
