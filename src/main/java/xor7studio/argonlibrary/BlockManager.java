package xor7studio.argonlibrary;

import net.minecraft.block.Block;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

public class BlockManager {
    public static Block getBlock(String name){
        return (Block) ArgonLibrary.getFromRegistry(name, Registry.BLOCK);
    }
    public static void setBlock(BlockPos pos, Block block){
        setBlock(ArgonLibrary.server.getOverworld(),pos,block);
    }
    public static void setBlock(@NotNull ServerWorld world, BlockPos pos,Block block){
        if(!world.isClient)
            world.setBlockState(pos,
                    block.getDefaultState());
    }
}
