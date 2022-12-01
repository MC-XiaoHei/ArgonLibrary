package xor7studio.argonlibrary;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import xor7studio.util.Xor7IO;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class ArgonLibrary implements ModInitializer {
    public static MinecraftServer server;
    public static MinecraftClient client;
    public static Set<PlayerEntity> openMenuFlag;
    public static String MCColor="§";
    public static Object getFromRegistry(@NotNull String name, Registry<?> registry){
        if (!name.contains(":"))
            return registry.get(
                    new Identifier("minecraft", name));
        else
            return registry.get(
                    new Identifier(name.split(":")[0], name.split(":")[1]));
    }
    public static ServerPlayerEntity getServerPlayer(String name){
        return server.getPlayerManager().getPlayer(name);
    }
    public static UUID getUUID(String name){
        return Objects.requireNonNull(server.getPlayerManager().getPlayer(name)).getUuid();
    }
    @Override
    public void onInitialize() {
        Xor7IO.modId="Argon Library";
        openMenuFlag =new HashSet<>();
        Command.initCommand();
        Xor7IO.println("Argon Library加载完成.");
    }
    public static class EulaAPI{
        // 当您调用本类中的ignoreEula或setTrueWhenCreateEula方法
        // 或通过其他手段修改本类中的ignoreEulaFlag或setTrueWhenCreateEulaFlag变量为True时
        // 您必须向用户说明:这意味着用户同意了Minecraft Eula协议(https://account.mojang.com/documents/minecraft_eula)
        private static boolean ignoreEulaFlag=false;
        private static boolean setTrueWhenCreateEulaFlag=false;
        public static void ignoreEula(){ignoreEulaFlag=true;}
        public static void setTrueWhenCreateEula(){setTrueWhenCreateEulaFlag=true;}
        public static boolean ifIgnoreEula(){return ignoreEulaFlag;}
        public static boolean ifSetTrueWhenCreateEula(){return setTrueWhenCreateEulaFlag;}
    }
}
