package griglog.thaumcraft.items.armor;

import griglog.thaumcraft.events.PlayerEvents;
import griglog.thaumcraft.items.ModItems;
import griglog.thaumcraft.items.ModTab;
import griglog.thaumcraft.items.ThaumMaterial;
import griglog.thaumcraft.items.interfaces.IRechargable;
import griglog.thaumcraft.utils.RechargeHelper;
import griglog.thaumcraft.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class TravellerBoots extends ArmorItem implements IRechargable {
    public TravellerBoots() {
        super(ThaumMaterial.SPECIAL, EquipmentSlotType.FEET, ModTab.props().maxDamage(350));
        setRegistryName("traveller_boots");
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "thaumcraft:textures/entity/armor/bootstraveler.png";
    }


    public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
        return stack2.isItemEqual(new ItemStack(Items.LEATHER)) || super.getIsRepairable(stack1, stack2);
    }

    public Rarity getRarity(ItemStack itemstack) {
        return Rarity.RARE;
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        boolean hasCharge = RechargeHelper.getCharge(stack) > 0;
        if (!world.isRemote && player.ticksExisted % 20 == 0) {
            CompoundNBT tag = Utils.safeTag(stack);
            int e = tag.getInt("energy");
            if (e-- <= 0 && RechargeHelper.consumeCharge(stack, player, 1)) {
                e = 60;
            }
            tag.putInt("energy", e);
        }
        if (hasCharge && !player.abilities.isFlying && player.moveForward > 0.0f) {
            if (player.world.isRemote && !player.isSneaking()) {
                if (!prevStep.containsKey(player.getEntityId())) {
                   prevStep.put(player.getEntityId(), player.stepHeight);
                }
                player.stepHeight = 1.0f;
            }
            if (player.isOnGround()) {
                float bonus = 0.05f;
                if (player.isInWater()) {
                    bonus /= 4.0f;
                }
                player.moveRelative(1, new Vector3d(0, 0, bonus));
            } else {
                if (player.isInWater()) {
                    player.moveRelative(1, new Vector3d(0, 0, 0.025));
                }
                player.jumpMovementFactor = 0.05f;
            }
        }
    }

    public static Map<Integer, Float> prevStep = new HashMap<>();
    @SubscribeEvent
    static void playerTick(TickEvent.PlayerTickEvent event){
        PlayerEntity player = event.player;
        if (event.phase == TickEvent.Phase.END && player.world.isRemote && (player.isSneaking() || player.inventory.armorInventory.get(0).getItem() != ModItems.travellerBoots) && prevStep.containsKey(player.getEntityId())) {
            player.stepHeight = prevStep.get(player.getEntityId());
            prevStep.remove(player.getEntityId());
        }
    }


    public int getMaxCharge(ItemStack stack, LivingEntity player) {
        return 240;
    }

    public EnumChargeDisplay showInHud(ItemStack stack, LivingEntity player) {
        return EnumChargeDisplay.PERIODIC;
    }
}
