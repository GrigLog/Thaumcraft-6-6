package griglog.thaumcraft.events;

import griglog.thaumcraft.items.infusions.InfusionEnchantment;
import griglog.thaumcraft.items.interfaces.IEssentiaContainerItem;
import griglog.thaumcraft.items.interfaces.IRechargable;
import griglog.thaumcraft.utils.RechargeHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber
public class ClientEvents {
    @SubscribeEvent
    static void tooltip(ItemTooltipEvent event){
        try {
            int charge = getRunicCharge(event.getItemStack());
            if (charge > 0) {
                event.getToolTip().add(new TranslationTextComponent("item.runic.charge").appendString(" +" + charge).mergeStyle(TextFormatting.GOLD));
            }
            int warp = getFinalWarp(event.getItemStack(), event.getPlayer());
            if (warp > 0) {
                event.getToolTip().add(new TranslationTextComponent("item.warping").appendString(" " + warp).mergeStyle(TextFormatting.DARK_PURPLE));
            }
            int al = getFinalDiscount(event.getItemStack(), event.getPlayer());
            if (al > 0) {
                event.getToolTip().add(new TranslationTextComponent("tc.visdiscount").appendString(": " + al + "%").mergeStyle(TextFormatting.DARK_PURPLE));
            }
            if (event.getItemStack().getItem() instanceof IRechargable) {
                int c = Math.round((float) RechargeHelper.getCharge(event.getItemStack()));
                if (c >= 0) {
                    event.getToolTip().add(new TranslationTextComponent("tc.charge").appendString( " " + c).mergeStyle(TextFormatting.YELLOW));
                }
            }
            /*
            if (event.getItemStack().getItem() instanceof IEssentiaContainerItem) {
                AspectList aspects = ((IEssentiaContainerItem) event.getItemStack().getItem()).getAspects(event.getItemStack());
                if (aspects != null && aspects.size() > 0) {
                    for (Aspect tag : aspects.getAspectsSortedByName()) {
                        event.getToolTip().add(tag.getName() + " x" + aspects.getAmount(tag));
                    }
                }
            }*/
            for (InfusionEnchantment.Pair p : InfusionEnchantment.getPairList(event.getItemStack())){
                TranslationTextComponent s = new TranslationTextComponent("enchantment.infusion." + p.type.id.getPath().toUpperCase());
                if (p.level > 1) {
                    s.appendString(" ").appendSibling(new TranslationTextComponent("enchantment.level." + p.level));
                }
                event.getToolTip().add(1, s.mergeStyle(TextFormatting.GOLD));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static int getFinalDiscount(ItemStack itemStack, PlayerEntity player) {
        return 0;
    }

    private static int getFinalWarp(ItemStack itemStack, PlayerEntity player) {
        return 0;
    }

    private static int getRunicCharge(ItemStack itemStack) {
        return 0;
    }
}
