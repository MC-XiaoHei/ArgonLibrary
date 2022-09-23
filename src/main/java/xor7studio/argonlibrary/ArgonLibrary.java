package xor7studio.argonlibrary;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import xor7studio.util.Xor7IO;

import java.util.*;

public class ArgonLibrary implements ModInitializer {
    public static MinecraftServer server;
    public static MinecraftClient client;
    public static Map<String,ServerScoreboard> scoreboards;
    public static Set<String> haveScoreboard;//By UUID
    public static Set<PlayerEntity> openMenuFlag;
    static Object getFromRegistry(@NotNull String name, Registry<?> registry){
        if (!name.contains(":"))
            return registry.get(
                    new Identifier("minecraft", name));
        else
            return registry.get(
                    new Identifier(name.split(":")[0], name.split(":")[1]));
    }
    @Override
    public void onInitialize() {
        Xor7IO.modId="UtilityLibrary";
        scoreboards=new HashMap<>();
        haveScoreboard =new HashSet<>();
        openMenuFlag =new HashSet<>();
        Command.initCommand();
        Xor7IO.println("UtilityLibrary加载完成.");
    }
}
