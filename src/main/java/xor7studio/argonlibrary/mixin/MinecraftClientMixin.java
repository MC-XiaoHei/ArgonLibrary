package xor7studio.argonlibrary.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import xor7studio.argonlibrary.ArgonLibrary;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    public void getClient(){
        ArgonLibrary.client = (MinecraftClient) (Object) this;
    }
}
