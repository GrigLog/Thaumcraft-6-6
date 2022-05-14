package griglog.thaumcraft.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import griglog.thaumcraft.Thaumcraft;
import griglog.thaumcraft.blocks.ModBlocks;
import griglog.thaumcraft.blocks.tiles.TileJar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.data.EmptyModelData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JarRenderer extends TileEntityRenderer<TileJar> {
    public JarRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }
    public static ResourceLocation EMPTY_TEXTURE = new ResourceLocation(Thaumcraft.id, "empty");
    public static RenderMaterial mat = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, JarRenderer.EMPTY_TEXTURE);
    public static List<BakedQuad> quads;

    public static void saveModelQuads(){
        quads = new ArrayList<>();
        IBakedModel model = Minecraft.getInstance().getBlockRendererDispatcher().getModelForState(ModBlocks.jar.getDefaultState());
        Random r = new Random();
        for (Direction side : new Direction[]{null, Direction.DOWN}) //null collects all quads except the ones being on block borders, so I have to pass DOWN direction to get the last one (in the bottom of the jar)
            quads.addAll(model.getQuads(ModBlocks.jar.getDefaultState(), side, r, EmptyModelData.INSTANCE));
    }

    @Override
    public void render(TileJar tile, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        int color = tile.ae.type.color;
        float filled = (float) tile.ae.amount / TileJar.CAPACITY;
        ms.push();
        if (filled > 0){ //render liquid
            IVertexBuilder emptyBuilder = mat.getBuffer(buffer, (resourceLocation -> RenderType.getCutout()));
            float r = (color >> 16 & 0xff) / 256f;
            float g = (color >> 8 & 0xff) / 256f;
            float b = (color & 0xff) / 256f;
            ModelRenderer mr = new ModelRenderer(32, 32, 0, 0);
            mr.addBox(4, 1, 4, 8, 10 * filled, 8);
            mr.render(ms, emptyBuilder, combinedLight, combinedOverlay, r, g, b, 1f);
        }

        //render block model
        if (quads == null)
            saveModelQuads();
        IVertexBuilder transBuilder = buffer.getBuffer(RenderType.getTranslucent());
        for (BakedQuad quad : quads){
            transBuilder.addQuad(ms.getLast(), quad, 1f, 1f, 1f, combinedLight, combinedOverlay);
        }
        ms.pop();
    }
    //another way of getting IBakedModel:
    //IBakedModel model = Minecraft.getInstance().getModelManager().getModel(BlockModelShapes.getModelLocation(ModBlocks.jar.getDefaultState()));
}
