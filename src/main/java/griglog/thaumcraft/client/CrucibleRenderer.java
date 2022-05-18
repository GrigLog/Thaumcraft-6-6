package griglog.thaumcraft.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import griglog.thaumcraft.Thaumcraft;
import griglog.thaumcraft.aspect.Aspects;
import griglog.thaumcraft.blocks.tiles.CrucibleTile;
import griglog.thaumcraft.utils.Utils;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeColors;

public class CrucibleRenderer extends TileEntityRenderer<CrucibleTile> {
    public CrucibleRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(CrucibleTile tile, float partialTicks, MatrixStack ms, IRenderTypeBuffer buff, int combinedLightIn, int combinedOverlayIn) {
        if (tile.getWorld() == null)
            return;
        if (tile.water == 0)
            return;
        ms.push();
        IVertexBuilder builder = buff.getBuffer(RenderType.getSolid());
        //TextureAtlasSprite sprite = Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelShapes().getTexture(Blocks.WATER.getDefaultState());
        TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(new ResourceLocation("block/water_still"));
        int base = BiomeColors.getWaterColor(tile.getWorld(), tile.getPos());
        float t = Math.min(tile.aspects.visSize() / 500f, 1);
        int col = Utils.scaleColor(base, Aspects.FLUX.color, t);
        int r = ColorHelper.PackedColor.getRed(col);
        int g = ColorHelper.PackedColor.getGreen(col);
        int b = ColorHelper.PackedColor.getBlue(col);
        float lowest = 4/16f;
        float highest = 15/16f;
        float y = lowest + (highest - lowest) * tile.water / 1000f;
        RenderHelper.drawTop(builder, ms.getLast().getMatrix(), 0, 1, y, 0, 1, 255, r, g, b, sprite);
        ms.pop();
    }
}
