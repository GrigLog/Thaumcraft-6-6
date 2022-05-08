package griglog.thaumcraft;

import griglog.thaumcraft.client.SoundsTC;
import griglog.thaumcraft.items.ModItems;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.IRegistryDelegate;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Register {
    @SubscribeEvent
    static void regItems(RegistryEvent.Register<Item> event) throws IllegalAccessException {
        //TODO: reflection is bad for performance. Check execution time when there will be more entries
        for (Field f : ModItems.class.getFields()){
            Object obj = f.get(null);
            if (obj instanceof Item)
                event.getRegistry().register((Item)obj);
        }
    }

    @SubscribeEvent
    static void setupClient(FMLClientSetupEvent event){

        ItemColors colors = event.getMinecraftSupplier().get().getItemColors();
        IItemColor color = (stack, tintIndex) -> {
            DyeableArmorItem item = (DyeableArmorItem) stack.getItem();
            return (tintIndex > 0) ? -1 : item.getColor(stack);
        };
        colors.register(color, ModItems.clothBoots, ModItems.clothLegs, ModItems.clothChest, ModItems.voidRobeChest, ModItems.voidRobeLegs, ModItems.voidRobeHelm);
    }

    @SubscribeEvent
    static void regSounds(RegistryEvent.Register<SoundEvent> event){
        SoundsTC.registerSounds(event);
    }
}
