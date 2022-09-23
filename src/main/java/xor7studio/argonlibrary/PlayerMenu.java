package xor7studio.argonlibrary;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static xor7studio.argonlibrary.ArgonLibrary.openMenuFlag;

public class PlayerMenu {
    private final int row;
    private final String name;
    private final ScreenHandlerListener listener;
    private Map<Integer,ItemStack> inventory;
    private ScreenHandler handler;
    private final PlayerEntity player;
    public PlayerMenu(int size, String name, PlayerEntity player, Map<Integer,ItemStack> inventory,Menu menu){
        this.player=player;
        this.name=name;
        row=size/9;
        this.inventory=inventory;
        listener=new ScreenHandlerListener() {
            @Override
            public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {
                menu.onSlotUpdate(handler,slotId,stack);
            }
            @Override
            public void onPropertyUpdate(ScreenHandler handler, int property, int value) {
                menu.onPropertyUpdate(handler, property, value);
            }
        };
    }
    public PlayerEntity getPlayer() {
        return player;
    }
    public void setSlot(int slotId, ItemStack stack){
        if(inventory.containsKey(slotId)) inventory.replace(slotId,stack);
        else inventory.put(slotId,stack);
        handler.setStackInSlot(slotId,handler.getRevision(),stack);
    }
    public void openMenu(){
        openMenuFlag.add(player);
        player.openHandledScreen(new NamedScreenHandlerFactory() {
            @Override
            public Text getDisplayName() {
                return Text.of(name);
            }
            @Override
            public @NotNull ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                handler=switch (row){
                    case 1->GenericContainerScreenHandler.createGeneric9x1(syncId,inv);
                    case 2->GenericContainerScreenHandler.createGeneric9x2(syncId,inv);
                    case 3->GenericContainerScreenHandler.createGeneric9x3(syncId,inv);
                    case 4->GenericContainerScreenHandler.createGeneric9x4(syncId,inv);
                    case 5->GenericContainerScreenHandler.createGeneric9x5(syncId,inv);
                    case 6->GenericContainerScreenHandler.createGeneric9x6(syncId,inv);
                    default -> throw new IllegalStateException("未知错误");
                };
                handler.addListener(listener);
                return handler;
            }
        });
        for(int key:inventory.keySet())
            setSlot(key,inventory.get(key));
    }
}
