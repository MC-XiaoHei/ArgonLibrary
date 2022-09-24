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
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import xor7studio.util.Xor7IO;

import java.util.*;

import static net.minecraft.block.Block.dropStacks;

public class ArgonLibrary implements ModInitializer {
    public static MinecraftServer server;
    public static MinecraftClient client;
    public static Set<PlayerEntity> openMenuFlag;
    static Object getFromRegistry(@NotNull String name, Registry<?> registry){
        if (!name.contains(":"))
            return registry.get(
                    new Identifier("minecraft", name));
        else
            return registry.get(
                    new Identifier(name.split(":")[0], name.split(":")[1]));
    }
    public static boolean move(World world, BlockPos pos, Direction dir, boolean retract, boolean sticky, DirectionProperty facing) {
        BlockPos blockPos = pos.offset(dir);
        if (!retract && world.getBlockState(blockPos).isOf(Blocks.PISTON_HEAD)) {
            world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 20);
        }

        PistonHandler pistonHandler = new PistonHandler(world, pos, dir, retract);
        if (!pistonHandler.calculatePush()) {
            return false;
        } else {
            Map<BlockPos, BlockState> map = Maps.newHashMap();
            List<BlockPos> list = pistonHandler.getMovedBlocks();
            List<BlockState> list2 = Lists.newArrayList();

            for(int i = 0; i < list.size(); ++i) {
                BlockPos blockPos2 = (BlockPos)list.get(i);
                BlockState blockState = world.getBlockState(blockPos2);
                list2.add(blockState);
                map.put(blockPos2, blockState);
            }

            List<BlockPos> list3 = pistonHandler.getBrokenBlocks();
            BlockState[] blockStates = new BlockState[list.size() + list3.size()];
            Direction direction = retract ? dir : dir.getOpposite();
            int j = 0;

            int k;
            BlockPos blockPos3;
            BlockState blockState2;
            for(k = list3.size() - 1; k >= 0; --k) {
                blockPos3 = (BlockPos)list3.get(k);
                blockState2 = world.getBlockState(blockPos3);
                BlockEntity blockEntity = blockState2.hasBlockEntity() ? world.getBlockEntity(blockPos3) : null;
                dropStacks(blockState2, world, blockPos3, blockEntity);
                world.setBlockState(blockPos3, Blocks.AIR.getDefaultState(), 18);
                if (!blockState2.isIn(BlockTags.FIRE)) {
                    world.addBlockBreakParticles(blockPos3, blockState2);
                }

                blockStates[j++] = blockState2;
            }

            for(k = list.size() - 1; k >= 0; --k) {
                blockPos3 = (BlockPos)list.get(k);
                blockState2 = world.getBlockState(blockPos3);
                blockPos3 = blockPos3.offset(direction);
                map.remove(blockPos3);
                BlockState blockState3 = (BlockState)Blocks.MOVING_PISTON.getDefaultState().with(facing, dir);
                world.setBlockState(blockPos3, blockState3, 68);
                world.addBlockEntity(PistonExtensionBlock.createBlockEntityPiston(blockPos3, blockState3, (BlockState)list2.get(k), dir, retract, false));
                blockStates[j++] = blockState2;
            }

            if (retract) {
                PistonType pistonType = sticky ? PistonType.STICKY : PistonType.DEFAULT;
                BlockState blockState4 = (BlockState)((BlockState)Blocks.PISTON_HEAD.getDefaultState().with(PistonHeadBlock.FACING, dir)).with(PistonHeadBlock.TYPE, pistonType);
                blockState2 = (BlockState)((BlockState)Blocks.MOVING_PISTON.getDefaultState().with(PistonExtensionBlock.FACING, dir)).with(PistonExtensionBlock.TYPE, sticky ? PistonType.STICKY : PistonType.DEFAULT);
                map.remove(blockPos);
                world.setBlockState(blockPos, blockState2, 68);
                world.addBlockEntity(PistonExtensionBlock.createBlockEntityPiston(blockPos, blockState2, blockState4, dir, true, true));
            }

            BlockState blockState5 = Blocks.AIR.getDefaultState();
            Iterator var25 = map.keySet().iterator();

            while(var25.hasNext()) {
                BlockPos blockPos4 = (BlockPos)var25.next();
                world.setBlockState(blockPos4, blockState5, 82);
            }

            var25 = map.entrySet().iterator();

            BlockPos blockPos5;
            while(var25.hasNext()) {
                Map.Entry<BlockPos, BlockState> entry = (Map.Entry)var25.next();
                blockPos5 = (BlockPos)entry.getKey();
                BlockState blockState6 = (BlockState)entry.getValue();
                blockState6.prepare(world, blockPos5, 2);
                blockState5.updateNeighbors(world, blockPos5, 2);
                blockState5.prepare(world, blockPos5, 2);
            }

            j = 0;

            int l;
            for(l = list3.size() - 1; l >= 0; --l) {
                blockState2 = blockStates[j++];
                blockPos5 = (BlockPos)list3.get(l);
                blockState2.prepare(world, blockPos5, 2);
                world.updateNeighborsAlways(blockPos5, blockState2.getBlock());
            }

            for(l = list.size() - 1; l >= 0; --l) {
                world.updateNeighborsAlways((BlockPos)list.get(l), blockStates[j++].getBlock());
            }

            if (retract) {
                world.updateNeighborsAlways(blockPos, Blocks.PISTON_HEAD);
            }

            return true;
        }
    }
    @Override
    public void onInitialize() {
        Xor7IO.modId="Argon Library";
        openMenuFlag =new HashSet<>();
        Command.initCommand();
        Xor7IO.println("UtilityLibrary加载完成.");
    }
}
