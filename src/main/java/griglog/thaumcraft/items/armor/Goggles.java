package griglog.thaumcraft.items.armor;

import griglog.thaumcraft.api.IGoggles;
import griglog.thaumcraft.api.IVisDiscountGear;
import griglog.thaumcraft.items.ModItems;
import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.ThaumMaterial;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;

public class Goggles extends ArmorItem implements IVisDiscountGear, IGoggles {
    public Goggles() {
        super(ThaumMaterial.SPECIAL, EquipmentSlotType.HEAD, ModTab.props().maxDamage(350));
        setRegistryName("goggles");
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "thaumcraft:textures/entity/armor/goggles.png";
    }

    public Rarity getRarity(ItemStack itemstack) {
        return Rarity.RARE;
    }

    public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == ModItems.brassIngot || super.getIsRepairable(stack1, stack2);
    }

    public int getVisDiscount(ItemStack stack, PlayerEntity player) {
        return 5;
    }

    public boolean showIngamePopups(ItemStack itemstack, LivingEntity player) {
        return true;
    }

    /*
    @OnlyIn(Dist.CLIENT)
    public void onPlayerBaubleRender(ItemStack stack, PlayerEntity player, IRenderBauble.RenderType type, float ticks) {
        if (type == IRenderBauble.RenderType.HEAD) {
            boolean armor = player.getItemStackFromSlot(EquipmentSlotType.HEAD) != null;
            Minecraft.getInstance().renderEngine.bindTexture(tex);
            IRenderBauble.Helper.translateToHeadLevel(player);
            IRenderBauble.Helper.translateToFace();
            IRenderBauble.Helper.defaultTransforms();
            GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(-0.5, -0.5, armor ? 0.11999999731779099 : 0.0);
            UtilsFX.renderTextureIn3D(0.0f, 0.0f, 1.0f, 1.0f, 16, 26, 0.1f);
        }
    }*/
}
