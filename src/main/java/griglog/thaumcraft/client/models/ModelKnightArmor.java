package griglog.thaumcraft.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Field;

public class ModelKnightArmor extends BipedModel<LivingEntity> {
    ModelRenderer Frontcloth1;
    ModelRenderer Helmet;
    ModelRenderer BeltR;
    ModelRenderer Mbelt;
    ModelRenderer MbeltL;
    ModelRenderer MbeltR;
    ModelRenderer BeltL;
    ModelRenderer Chestplate;
    ModelRenderer CloakAtL;
    ModelRenderer Backplate;
    ModelRenderer Cloak3;
    ModelRenderer CloakAtR;
    ModelRenderer Tabbard;
    ModelRenderer Cloak1;
    ModelRenderer Cloak2;
    ModelRenderer ShoulderR1;
    ModelRenderer GauntletR;
    ModelRenderer GauntletstrapR1;
    ModelRenderer GauntletstrapR2;
    ModelRenderer ShoulderR;
    ModelRenderer ShoulderR0;
    ModelRenderer ShoulderR2;
    ModelRenderer ShoulderL1;
    ModelRenderer GauntletL;
    ModelRenderer GauntletstrapL1;
    ModelRenderer GauntletstrapL2;
    ModelRenderer ShoulderL;
    ModelRenderer ShoulderL0;
    ModelRenderer ShoulderL2;
    ModelRenderer SidepanelR3;
    ModelRenderer SidepanelR2;
    ModelRenderer SidepanelL2;
    ModelRenderer SidepanelR0;
    ModelRenderer SidepanelL0;
    ModelRenderer SidepanelR1;
    ModelRenderer SidepanelL3;
    ModelRenderer Frontcloth2;
    ModelRenderer SidepanelL1;

    public ModelKnightArmor() {
        super(0.49f, 0, 128, 64);

        Helmet = new ModelRenderer(this, 41, 8);
        Helmet.addBox(-4.5f, -9.0f, -4.5f, 9, 9, 9);
        setRotation(Helmet, 0.0f, 0.0f, 0.0f);

        (BeltR = new ModelRenderer(this, 76, 44)).addBox(-5.0f, 4.0f, -3.0f, 1, 3, 6);
        setRotation(BeltR, 0.0f, 0.0f, 0.0f);
        (Mbelt = new ModelRenderer(this, 56, 55)).addBox(-4.0f, 8.0f, -3.0f, 8, 4, 1);
        setRotation(Mbelt, 0.0f, 0.0f, 0.0f);
        (MbeltL = new ModelRenderer(this, 76, 44)).addBox(4.0f, 8.0f, -3.0f, 1, 3, 6);
        setRotation(MbeltL, 0.0f, 0.0f, 0.0f);
        (MbeltR = new ModelRenderer(this, 76, 44)).addBox(-5.0f, 8.0f, -3.0f, 1, 3, 6);
        setRotation(MbeltR, 0.0f, 0.0f, 0.0f);
        (BeltL = new ModelRenderer(this, 76, 44)).addBox(4.0f, 4.0f, -3.0f, 1, 3, 6);
        setRotation(BeltL, 0.0f, 0.0f, 0.0f);
        (Tabbard = new ModelRenderer(this, 114, 52)).addBox(-3.0f, 1.2f, -3.5f, 6, 10, 1);
        setRotation(Tabbard, 0.0f, 0.0f, 0.0f);
        (CloakAtL = new ModelRenderer(this, 0, 43)).addBox(2.5f, 1.0f, 2.0f, 2, 1, 3);
        setRotation(CloakAtL, 0.1396263f, 0.0f, 0.0f);
        (Backplate = new ModelRenderer(this, 36, 45)).addBox(-4.0f, 1.0f, 2.0f, 8, 11, 2);
        setRotation(Backplate, 0.0f, 0.0f, 0.0f);
        (Cloak1 = new ModelRenderer(this, 0, 47)).addBox(0.0f, 0.0f, 0.0f, 9, 12, 1);
        Cloak1.setRotationPoint(-4.5f, 1.3f, 4.2f);
        setRotation(Cloak1, 0.1396263f, 0.0f, 0.0f);
        (Cloak2 = new ModelRenderer(this, 0, 59)).addBox(0.0f, 11.7f, -2.0f, 9, 4, 1);
        Cloak2.setRotationPoint(-4.5f, 1.3f, 4.2f);
        setRotation(Cloak2, 0.3069452f, 0.0f, 0.0f);
        (Cloak3 = new ModelRenderer(this, 0, 59)).addBox(0.0f, 15.2f, -4.2f, 9, 4, 1);
        Cloak3.setRotationPoint(-4.5f, 1.3f, 4.2f);
        setRotation(Cloak3, 0.4465716f, 0.0f, 0.0f);
        (CloakAtR = new ModelRenderer(this, 0, 43)).addBox(-4.5f, 1.0f, 2.0f, 2, 1, 3);
        setRotation(CloakAtR, 0.1396263f, 0.0f, 0.0f);
        (Chestplate = new ModelRenderer(this, 56, 45)).addBox(-4.0f, 1.0f, -3.0f, 8, 7, 1);
        setRotation(Chestplate, 0.0f, 0.0f, 0.0f);
        (ShoulderR1 = new ModelRenderer(this, 0, 19)).addBox(-3.3f, 3.5f, -2.5f, 1, 1, 5);
        setRotation(ShoulderR1, 0.0f, 0.0f, 0.7853982f);
        (GauntletR = new ModelRenderer(this, 100, 26)).addBox(-3.5f, 3.5f, -2.5f, 2, 6, 5);
        GauntletR.mirror = true;
        setRotation(GauntletR, 0.0f, 0.0f, 0.0f);
        (GauntletstrapR1 = new ModelRenderer(this, 84, 31)).addBox(-1.5f, 3.5f, -2.5f, 3, 1, 5);
        GauntletstrapR1.mirror = true;
        setRotation(GauntletstrapR1, 0.0f, 0.0f, 0.0f);
        (GauntletstrapR2 = new ModelRenderer(this, 84, 31)).addBox(-1.5f, 6.5f, -2.5f, 3, 1, 5);
        GauntletstrapR2.mirror = true;
        setRotation(GauntletstrapR2, 0.0f, 0.0f, 0.0f);
        (ShoulderR = new ModelRenderer(this, 56, 35)).addBox(-3.5f, -2.5f, -2.5f, 5, 5, 5);
        ShoulderR.mirror = true;
        setRotation(ShoulderR, 0.0f, 0.0f, 0.0f);
        (ShoulderR0 = new ModelRenderer(this, 0, 0)).addBox(-4.3f, -1.5f, -3.0f, 3, 5, 6);
        ShoulderR0.mirror = true;
        setRotation(ShoulderR0, 0.0f, 0.0f, 0.7853982f);
        (ShoulderR2 = new ModelRenderer(this, 0, 11)).addBox(-2.3f, 3.5f, -3.0f, 1, 2, 6);
        ShoulderR2.mirror = true;
        setRotation(ShoulderR2, 0.0f, 0.0f, 0.7853982f);
        ShoulderL1 = new ModelRenderer(this, 0, 19);
        ShoulderL1.mirror = true;
        ShoulderL1.addBox(2.3f, 3.5f, -2.5f, 1, 1, 5);
        setRotation(ShoulderL1, 0.0f, 0.0f, -0.7853982f);
        (GauntletL = new ModelRenderer(this, 114, 26)).addBox(1.5f, 3.5f, -2.5f, 2, 6, 5);
        setRotation(GauntletL, 0.0f, 0.0f, 0.0f);
        GauntletstrapL1 = new ModelRenderer(this, 84, 31);
        GauntletstrapL1.mirror = true;
        GauntletstrapL1.addBox(-1.5f, 3.5f, -2.5f, 3, 1, 5);
        setRotation(GauntletstrapL1, 0.0f, 0.0f, 0.0f);
        GauntletstrapL2 = new ModelRenderer(this, 84, 31);
        GauntletstrapL2.mirror = true;
        GauntletstrapL2.addBox(-1.5f, 6.5f, -2.5f, 3, 1, 5);
        setRotation(GauntletstrapL2, 0.0f, 0.0f, 0.0f);
        (ShoulderL = new ModelRenderer(this, 56, 35)).addBox(-1.5f, -2.5f, -2.5f, 5, 5, 5);
        setRotation(ShoulderL, 0.0f, 0.0f, 0.0f);
        (ShoulderL0 = new ModelRenderer(this, 0, 0)).addBox(1.3f, -1.5f, -3.0f, 3, 5, 6);
        setRotation(ShoulderL0, 0.0f, 0.0f, -0.7853982f);
        (ShoulderL2 = new ModelRenderer(this, 0, 11)).addBox(1.3f, 3.5f, -3.0f, 1, 2, 6);
        setRotation(ShoulderL2, 0.0f, 0.0f, -0.7853982f);
        (SidepanelR3 = new ModelRenderer(this, 116, 13)).addBox(-3.0f, 2.5f, -2.5f, 1, 4, 5);
        setRotation(SidepanelR3, 0.0f, 0.0f, 0.1396263f);
        SidepanelR2 = new ModelRenderer(this, 114, 5);
        SidepanelR2.mirror = true;
        SidepanelR2.addBox(-2.0f, 2.5f, -2.5f, 2, 3, 5);
        setRotation(SidepanelR2, 0.0f, 0.0f, 0.1396263f);
        (SidepanelL2 = new ModelRenderer(this, 114, 5)).addBox(0.0f, 2.5f, -2.5f, 2, 3, 5);
        setRotation(SidepanelL2, 0.0f, 0.0f, -0.1396263f);
        (SidepanelR0 = new ModelRenderer(this, 96, 14)).addBox(-3.0f, -0.5f, -2.5f, 5, 3, 5);
        setRotation(SidepanelR0, 0.0f, 0.0f, 0.1396263f);
        (SidepanelL0 = new ModelRenderer(this, 96, 14)).addBox(-2.0f, -0.5f, -2.5f, 5, 3, 5);
        setRotation(SidepanelL0, 0.0f, 0.0f, -0.1396263f);
        SidepanelR1 = new ModelRenderer(this, 96, 7);
        SidepanelR1.mirror = true;
        SidepanelR1.addBox(0.0f, 2.5f, -2.5f, 2, 2, 5);
        setRotation(SidepanelR1, 0.0f, 0.0f, 0.1396263f);
        (SidepanelL3 = new ModelRenderer(this, 116, 13)).addBox(2.0f, 2.5f, -2.5f, 1, 4, 5);
        setRotation(SidepanelL3, 0.0f, 0.0f, -0.1396263f);
        (SidepanelL1 = new ModelRenderer(this, 96, 7)).addBox(-2.0f, 2.5f, -2.5f, 2, 2, 5);
        setRotation(SidepanelL1, 0.0f, 0.0f, -0.1396263f);
        (Frontcloth1 = new ModelRenderer(this, 120, 39)).addBox(0.0f, 0.0f, 0.0f, 6, 8, 1);
        Frontcloth1.setRotationPoint(-3.0f, 11.0f, -3.5f);
        setRotation(Frontcloth1, -0.1047198f, 0.0f, 0.0f);
        (Frontcloth2 = new ModelRenderer(this, 100, 37)).addBox(0.0f, 7.5f, 1.8f, 6, 3, 1);
        Frontcloth2.setRotationPoint(-3.0f, 11.0f, -3.5f);
        setRotation(Frontcloth2, -0.3316126f, 0.0f, 0.0f);
        bipedHead.addChild(Helmet);
        bipedBody.addChild(Mbelt);
        bipedBody.addChild(MbeltL);
        bipedBody.addChild(MbeltR);
        //if (f >= 1.0f) {
            bipedBody.addChild(Chestplate);
            bipedBody.addChild(Frontcloth1);
            bipedBody.addChild(Frontcloth2);
            bipedBody.addChild(Tabbard);
            bipedBody.addChild(Backplate);
            bipedBody.addChild(Cloak1);
            bipedBody.addChild(Cloak2);
            bipedBody.addChild(Cloak3);
            bipedBody.addChild(CloakAtL);
            bipedBody.addChild(CloakAtR);
       // }
        bipedRightArm.addChild(ShoulderR);
        bipedRightArm.addChild(ShoulderR0);
        bipedRightArm.addChild(ShoulderR1);
        bipedRightArm.addChild(ShoulderR2);
        bipedRightArm.addChild(GauntletR);
        bipedRightArm.addChild(GauntletstrapR1);
        bipedRightArm.addChild(GauntletstrapR2);
        bipedLeftArm.addChild(ShoulderL);
        bipedLeftArm.addChild(ShoulderL0);
        bipedLeftArm.addChild(ShoulderL1);
        bipedLeftArm.addChild(ShoulderL2);
        bipedLeftArm.addChild(GauntletL);
        bipedLeftArm.addChild(GauntletstrapL1);
        bipedLeftArm.addChild(GauntletstrapL2);
        bipedRightLeg.addChild(SidepanelR0);
        bipedRightLeg.addChild(SidepanelR1);
        bipedRightLeg.addChild(SidepanelR2);
        bipedRightLeg.addChild(SidepanelR3);
        bipedLeftLeg.addChild(SidepanelL0);
        bipedLeftLeg.addChild(SidepanelL1);
        bipedLeftLeg.addChild(SidepanelL2);
        bipedLeftLeg.addChild(SidepanelL3);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        bipedHeadwear.showModel = false;
        float legAngle = Math.min(bipedLeftLeg.rotateAngleX, bipedRightLeg.rotateAngleX);
        Frontcloth1.rotateAngleX = legAngle - 0.1047198f;
        Frontcloth2.rotateAngleX = legAngle - 0.3316126f;
        Cloak1.rotateAngleX = -legAngle / 2.0f + 0.1396263f;
        Cloak2.rotateAngleX = -legAngle / 2.0f + 0.3069452f;
        Cloak3.rotateAngleX = -legAngle / 2.0f + 0.4465716f;
        super.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    static void copyAngles(ModelRenderer bodyPart, ModelRenderer ... armorParts){
        for (ModelRenderer part : armorParts){
            part.copyModelAngles(bodyPart);
        }
    }

    /*public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float f5) {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, f5, entity);
        float a = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;
        float b = MathHelper.cos(limbSwing * 0.6662f + 3.1415927f) * 1.4f * limbSwingAmount;
        float c = Math.min(a, b);
        Frontcloth1.rotateAngleX = c - 0.1047198f;
        Frontcloth2.rotateAngleX = c - 0.3316126f;
        Cloak1.rotateAngleX = -c / 2.0f + 0.1396263f;
        Cloak2.rotateAngleX = -c / 2.0f + 0.3069452f;
        Cloak3.rotateAngleX = -c / 2.0f + 0.4465716f;
        if (isChild) {
            float f6 = 2.0f;
            GL11.glPushMatrix();
            GL11.glScalef(1.5f / f6, 1.5f / f6, 1.5f / f6);
            GL11.glTranslatef(0.0f, 16.0f * f5, 0.0f);
            bipedHead.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
            GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f);
            bipedBody.render(f5);
            bipedRightArm.render(f5);
            bipedLeftArm.render(f5);
            bipedRightLeg.render(f5);
            bipedLeftLeg.render(f5);
            bipedHeadwear.render(f5);
            GL11.glPopMatrix();
        } else {
            GL11.glPushMatrix();
            GL11.glScalef(1.01f, 1.01f, 1.01f);
            bipedHead.render(f5);
            GL11.glPopMatrix();
            bipedBody.render(f5);
            bipedRightArm.render(f5);
            bipedLeftArm.render(f5);
            bipedRightLeg.render(f5);
            bipedLeftLeg.render(f5);
            bipedHeadwear.render(f5);
        }
    }*/

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
