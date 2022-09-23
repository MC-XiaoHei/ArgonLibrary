package xor7studio.argonlibrary;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class Menu {
    private Set<PlayerMenu> playerMenus;
    private final int size;
    private Map<Integer,ItemStack> inv;
    private final String name;
    public Menu(int size, String name){
        if(size<9) throw new IllegalArgumentException("菜单大小不能小于9!");
        if(size>54) throw new IllegalArgumentException("菜单大小不能大于54!");
        if(size%9!=0) throw new IllegalArgumentException("菜单大小必须是9的倍数!");
        this.size=size;
        this.name=name;
        this.inv=new HashMap<>();
        playerMenus=new HashSet<>();
    }
    public void openMenu(PlayerEntity player){
        PlayerMenu menu=new PlayerMenu(size,name,player,inv,this);
        playerMenus.add(menu);
        menu.openMenu();
    }
    public void setSlot(int slotId,ItemStack stack){
        for(PlayerMenu menu:playerMenus){
            if(menu==null) playerMenus.remove(menu);
            else menu.setSlot(slotId,stack);
        }
        if(inv.containsKey(slotId)) inv.replace(slotId,stack);
        else inv.put(slotId,stack);
    }
    public void setInv(@NotNull Map<Integer, ItemStack> inv) {
        if(inv.size()>size) throw new IllegalArgumentException("容器大小不能大于菜单大小！");
        for(int key:inv.keySet())
            if(key>size) throw new IndexOutOfBoundsException("容器索引越界:"+key);
        this.inv = inv;
    }
    public Set<PlayerEntity> getPlayers(){
        Set<PlayerEntity> playerEntities = new HashSet<>();
        for(PlayerMenu menu:playerMenus)
            playerEntities.add(menu.getPlayer());
        return playerEntities;
    }
    public abstract void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack);
    public abstract void onPropertyUpdate(ScreenHandler handler, int property, int value);
}