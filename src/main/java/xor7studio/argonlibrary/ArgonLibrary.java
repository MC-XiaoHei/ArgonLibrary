package xor7studio.argonlibrary;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonExtensionBlock;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.PistonType;
import net.minecraft.block.piston.PistonHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.State;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;
import xor7studio.util.Xor7IO;

import java.util.*;

import static net.minecraft.block.Block.dropStacks;
import static net.minecraft.block.PistonBlock.EXTENDED;

public class ArgonLibrary implements ModInitializer {
    public static MinecraftServer server;
    public static MinecraftClient client;
    public static Set<PlayerEntity> openMenuFlag;
    private static boolean ignoreEulaFlag=false;
    private static boolean autoCreateEulaFlag=false;
    public static void ignoreEula(){ignoreEulaFlag=true;}
    public static void autoCreateEula(){autoCreateEulaFlag=true;}
    public static boolean ifIgnoreEula(){return ignoreEulaFlag;}
    public static boolean ifAutoCreateEula(){return autoCreateEulaFlag;}
    public static Object getFromRegistry(@NotNull String name, Registry<?> registry){
        if (!name.contains(":"))
            return registry.get(
                    new Identifier("minecraft", name));
        else
            return registry.get(
                    new Identifier(name.split(":")[0], name.split(":")[1]));
    }
    @Override
    public void onInitialize() {
        Xor7IO.modId="Argon Library";
        openMenuFlag =new HashSet<>();
        Command.initCommand();
        Xor7IO.println("Argon Library加载完成.");
    }
}
