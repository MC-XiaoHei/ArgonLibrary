package xor7studio.argonlibrary;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EggItem;
import net.minecraft.item.Item;
import net.minecraft.network.packet.s2c.play.ScoreboardObjectiveUpdateS2CPacket;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.event.GameEvent;
import xor7studio.util.Xor7IO;

import static net.minecraft.server.command.CommandManager.*;
import static xor7studio.argonlibrary.ArgonLibrary.server;

public class Command {
    public static void initCommand(){
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {dispatcher
            .register(literal("run")
            .executes(context -> {
                SingleScoreboard singleScoreboard=new SingleScoreboard(context.getSource().getPlayer(),"aaa");
                singleScoreboard.setLine(5,"aaa");
                Xor7IO.println("command run.");
                return 1;
            }));
        });
    }
}
