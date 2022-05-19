package griglog.thaumcraft.events;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import griglog.thaumcraft.api.IGoggles;
import griglog.thaumcraft.api.aspect.IAspectContainer;
import griglog.thaumcraft.api.aspect.IAspectHolder;
import griglog.thaumcraft.client.RenderHelper;
import griglog.thaumcraft.items.infusions.InfusionEnchantment;
import griglog.thaumcraft.api.IRechargable;
import griglog.thaumcraft.api.IVisDiscountGear;
import griglog.thaumcraft.api.IWarpingGear;
import griglog.thaumcraft.utils.RechargeHelper;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.LightType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.DrawHighlightEvent;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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

    @SubscribeEvent
    static void draw(DrawHighlightEvent.HighlightBlock event){
        ClientWorld world = Minecraft.getInstance().world;
        if (world == null)
            return;
        Entity e = event.getInfo().getRenderViewEntity();
        if (!(e instanceof PlayerEntity))
            return;
        PlayerEntity player = (PlayerEntity) e;
        if (!(player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() instanceof IGoggles))
            return;
        BlockPos pos = event.getTarget().getPos();
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof IAspectHolder)
            RenderHelper.drawAspects(event, world, pos, ((IAspectHolder) tile).readList());
    }

    public static int getRunicCharge(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getByte("TC.RUNIC") : 0;
    }

    public static int getFinalWarp(ItemStack stack, PlayerEntity player) {
        if (stack == null || stack.isEmpty()) {
            return 0;
        }
        int warp = 0;
        if (stack.getItem() instanceof IWarpingGear) {
            IWarpingGear armor = (IWarpingGear) stack.getItem();
            warp += armor.getWarp(stack, player);
        }
        if (stack.hasTag() && stack.getTag().contains("TC.WARP")) {
            warp += stack.getTag().getByte("TC.WARP");
        }
        return warp;
    }

    public static int getFinalDiscount(ItemStack stack, PlayerEntity player) {
        if (stack == null || stack.isEmpty() || !(stack.getItem() instanceof IVisDiscountGear)) {
            return 0;
        }
        IVisDiscountGear gear = (IVisDiscountGear) stack.getItem();
        return gear.getVisDiscount(stack, player);
    }
}
