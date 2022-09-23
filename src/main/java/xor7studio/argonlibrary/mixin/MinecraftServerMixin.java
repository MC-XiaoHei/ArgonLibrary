package xor7studio.argonlibrary.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import xor7studio.argonlibrary.ArgonLibrary;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    public void getServer(){
        ArgonLibrary.server = (MinecraftServer) (Object) this;
    }
}
