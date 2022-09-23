package xor7studio.argonlibrary;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.network.packet.s2c.play.ScoreboardObjectiveUpdateS2CPacket;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.network.ServerPlayerEntity;
import sun.misc.Unsafe;
import xor7studio.argonlibrary.scoreboard.ScoreboardHandler;
import xor7studio.util.Xor7IO;

import java.lang.reflect.Field;

import static net.minecraft.server.command.CommandManager.*;

public class Command {
    public static void initCommand(){
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {dispatcher
            .register(literal("run")
            .requires(source -> source.hasPermissionLevel(4))
            .executes(context -> {
                ScoreboardHandler.INSTANCE.init(context.getSource().getPlayer());
                Xor7IO.println("command run.");
                return 1;
            }));
        });
    }
}
