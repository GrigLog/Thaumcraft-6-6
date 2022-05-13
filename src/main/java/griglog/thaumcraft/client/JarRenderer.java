package griglog.thaumcraft.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import griglog.thaumcraft.Thaumcraft;
import griglog.thaumcraft.aspect.AspectEntry;
import griglog.thaumcraft.blocks.tiles.TileJar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;

public class JarRenderer extends TileEntityRenderer<TileJar> {
    public JarRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }
    public static ResourceLocation EMPTY_TEXTURE = new ResourceLocation(Thaumcraft.id, "empty");
    //public static TextureAtlasSprite sprite;
    public static final RenderMaterial mat = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, EMPTY_TEXTURE);

    @Override
    public void render(TileJar tile, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        render(tile.ae, ms, buffer, combinedLight, combinedOverlay);
    }

    public static void render(AspectEntry ae, MatrixStack ms, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay){
        int color = ae.type.color;
        float filled = (float) ae.amount / TileJar.CAPACITY;
        if (filled > 0){
            ms.push();
            IVertexBuilder builder = mat.getBuffer(buffer, (resourceLocation -> RenderType.getCutout()));
            float r = ((color & 0xff0000) >> 16) / 256f;
            float g = ((color & 0x00ff00) >> 8) / 256f;
            float b = (color & 0x0000ff) / 256f;
            ModelRenderer mr = new ModelRenderer(32, 32, 0, 0);
            mr.addBox(4, 1, 4, 8, 10 * filled, 8);
            mr.render(ms, builder, combinedLight, combinedOverlay, r, g, b, 1f);
            ms.pop();
        }

    }
}
