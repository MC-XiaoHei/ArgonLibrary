package xor7studio.argonlibrary;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.Registry;

public class EntityManager {
    public Entity getEntity(String name){
        return (Entity) ArgonLibrary.getFromRegistry(name, Registry.ENTITY_TYPE);
    }
    public void summonEntity(ServerWorld world,float x,float y,float z){

    }
}
