package xor7studio.argonlibrary;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

import static xor7studio.argonlibrary.ArgonLibrary.getFromRegistry;

public class ItemManager {
    public static @NotNull Item getItem(@NotNull String name){
        return (Item) getFromRegistry(name,Registry.ITEM);
    }
    public static Enchantment getEnchantment(@NotNull String name){
        return (Enchantment) getFromRegistry(name,Registry.ENCHANTMENT);
    }
    private static @NotNull ItemStack getItemStack(
            Item item, int num,boolean e, Ench [] enchs){
        ItemStack itemStack=new ItemStack(item,num);
        if(e)
            for(Ench ench:enchs)
                itemStack.addEnchantment(ench.enchantment,ench.level);
        return itemStack;
    }
    public static @NotNull ItemStack getItemStack(Item item, int num){
        return getItemStack(item,num,false,null);
    }
    public static @NotNull ItemStack getItemStack(Item item, int num, Ench [] enchs){
        return getItemStack(item,num,true,enchs);
    }
}
