package griglog.thaumcraft.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import griglog.thaumcraft.Thaumcraft;
import griglog.thaumcraft.aspect.AspectEntry;
import griglog.thaumcraft.aspect.AspectList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.*;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawHighlightEvent;

import static net.minecraft.util.ColorHelper.PackedColor.*;

public class RenderHelper {
    public static void drawTop(IVertexBuilder builder, Matrix4f mPos, float x1, float x2, float y, float z1, float z2, int a, int r, int g, int b, TextureAtlasSprite sprite){
        builder.pos(mPos, x1, y, z1).color(r, g, b, a).tex(sprite.getMinU(), sprite.getMinV()).lightmap(0, 240).normal(1, 0, 0).endVertex();
        builder.pos(mPos, x1, y, z2).color(r, g, b, a).tex(sprite.getMaxU(), sprite.getMinV()).lightmap(0, 240).normal(1, 0, 0).endVertex();
        builder.pos(mPos, x2, y, z2).color(r, g, b, a).tex(sprite.getMaxU(), sprite.getMaxV()).lightmap(0, 240).normal(1, 0, 0).endVertex();
        builder.pos(mPos, x2, y, z1).color(r, g, b, a).tex(sprite.getMinU(), sprite.getMaxV()).lightmap(0, 240).normal(1, 0, 0).endVertex();
    }

    public static void drawTop(IVertexBuilder builder, Matrix4f mPos, float x1, float x2, float y, float z1, float z2, int a, int color3, TextureAtlasSprite sprite){
        drawTop(builder, mPos, x1, x2, y, z1, z2, a, getRed(color3), getGreen(color3), getBlue(color3), sprite);
    }

    public static void drawBottom(IVertexBuilder builder, Matrix4f mPos, float x1, float x2, float y, float z1, float z2, int a, int r, int g, int b, TextureAtlasSprite sprite){
        drawTop(builder, mPos, x2, x1, y, z1, z2, a, r, g, b, sprite);
    }

    public static void drawSideZPos(IVertexBuilder builder, Matrix4f mPos, float x1, float x2, float y1, float y2, float z, int a, int r, int g, int b, TextureAtlasSprite sprite){
        builder.pos(mPos, x1, y1, z).color(r, g, b, a).tex(sprite.getMinU(), sprite.getMinV()).lightmap(0, 240).normal(1, 0, 0).endVertex();
        builder.pos(mPos, x2, y1, z).color(r, g, b, a).tex(sprite.getMaxU(), sprite.getMinV()).lightmap(0, 240).normal(1, 0, 0).endVertex();
        builder.pos(mPos, x2, y2, z).color(r, g, b, a).tex(sprite.getMaxU(), sprite.getMaxV()).lightmap(0, 240).normal(1, 0, 0).endVertex();
        builder.pos(mPos, x1, y2, z).color(r, g, b, a).tex(sprite.getMinU(), sprite.getMaxV()).lightmap(0, 240).normal(1, 0, 0).endVertex();
    }

    public static void drawSideZPos(IVertexBuilder builder, Matrix4f mPos, float x1, float x2, float y1, float y2, float z, int a, int color3, TextureAtlasSprite sprite){
        drawSideZPos(builder, mPos, x1, x2, y1, y2, z, a, getRed(color3), getGreen(color3), getBlue(color3), sprite);
    }

    public static void drawSideZNeg(IVertexBuilder builder, Matrix4f mPos, float x1, float x2, float y1, float y2, float z, int a, int r, int g, int b, TextureAtlasSprite sprite){
        drawSideZPos(builder, mPos, x2, x1, y1, y2, z, a, r, g, b, sprite);
    }

    public static void drawSideZNeg(IVertexBuilder builder, Matrix4f mPos, float x1, float x2, float y1, float y2, float z, int a, int color3, TextureAtlasSprite sprite){
        drawSideZPos(builder, mPos, x2, x1, y1, y2, z, a, getRed(color3), getGreen(color3), getBlue(color3), sprite);
    }

    public static void drawSideXNeg(IVertexBuilder builder, Matrix4f mPos, float x, float y1, float y2, float z1, float z2, int a, int r, int g, int b, TextureAtlasSprite sprite){
        builder.pos(mPos, x, y1, z1).color(r, g, b, a).tex(sprite.getMinU(), sprite.getMinV()).lightmap(0, 240).normal(1, 0, 0).endVertex();
        builder.pos(mPos, x, y1, z2).color(r, g, b, a).tex(sprite.getMaxU(), sprite.getMinV()).lightmap(0, 240).normal(1, 0, 0).endVertex();
        builder.pos(mPos, x, y2, z2).color(r, g, b, a).tex(sprite.getMaxU(), sprite.getMaxV()).lightmap(0, 240).normal(1, 0, 0).endVertex();
        builder.pos(mPos, x, y2, z1).color(r, g, b, a).tex(sprite.getMinU(), sprite.getMaxV()).lightmap(0, 240).normal(1, 0, 0).endVertex();
    }

    public static void drawSideXPos(IVertexBuilder builder, Matrix4f mPos, float x, float y1, float y2, float z1, float z2, int a, int r, int g, int b, TextureAtlasSprite sprite){
        drawSideXNeg(builder, mPos, x, y2, y1, z1, z2, a, r, g, b, sprite);
    }



    public static void drawAspects(DrawHighlightEvent.HighlightBlock event, World world, BlockPos pos, AspectList aspects){
        Vector3d cameraPos = event.getInfo().getProjectedView();
        MatrixStack ms = event.getMatrix();
        ms.push();
        ms.translate(pos.getX() - cameraPos.getX(), pos.getY() - cameraPos.getY(), pos.getZ() - cameraPos.getZ());
        Matrix4f mPos = ms.getLast().getMatrix();
        //IVertexBuilder builder = event.getBuffers().getBuffer(RenderType.LINES);
        //builder.pos(mPos, 0, 0, 0).color(255, 0, 0, 255).endVertex();
        //builder.pos(mPos, 0, 0, 1).color(255, 0, 0, 255).endVertex();
        ms.translate(0.5, 1.2, 0.5);
        ms.scale(0.01f, 0.01f, 0.01f);
        Quaternion rot = event.getInfo().getRotation();
        ms.rotate(rot);
        ms.scale(-1, -1, -1);
        int len = aspects.size();
        IVertexBuilder transBuilder = event.getBuffers().getBuffer(RenderType.getTranslucent());
        for (int i = 0; i < len; i++) {
            float corner = 50 * (i - len/2f);
            AspectEntry ae = aspects.entries.get(i);
            TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(new ResourceLocation(Thaumcraft.id, "aspects/" + ae.type.tag));
            drawSideZNeg(transBuilder, mPos, corner + 5, corner + 45, -45, -5, 0, 0xc0, ae.type.color, sprite);
            String str = String.valueOf(ae.amount);
            int width = Minecraft.getInstance().fontRenderer.getStringWidth(str);
            Minecraft.getInstance().fontRenderer.drawString(ms, str, corner + 25 - width / 2f, 0, 0xc0ffffff);
        }
        ms.pop();
    }

    /*
    private static void drawTop(IVertexBuilder builder, Matrix4f matrix, Matrix3f normal, float height, TextureAtlasSprite tex, int r, int g, int b, int a, boolean gas, int overlay, int light)
    {
        // Vertex y coodinates
        float minY = gas ? MAX_Y - height * (MAX_Y - MIN_Y) : MIN_Y;
        float maxY = gas ? MAX_Y : MIN_Y + height * (MAX_Y - MIN_Y);

        // Texture coordinates
        float minU = tex.getU(MIN_UV_T);
        float maxU = tex.getU(MAX_UV_T);
        float minV = tex.getV(MIN_UV_T);
        float maxV = tex.getV(MAX_UV_T);

        // Bottom
        drawTopBottomFace(builder, matrix, normal, MAX_X, MIN_Z, MIN_X, MAX_Z, minY, minU, minV, maxU, maxV, -1, r, g, b, a, overlay, light);

        // Top
        drawTopBottomFace(builder, matrix, normal, MIN_X, MIN_Z, MAX_X, MAX_Z, maxY, minU, minV, maxU, maxV, 1, r, g, b, a, overlay, light);
    }

    private static void drawSides(IVertexBuilder builder, Matrix4f matrix, Matrix3f normal, float height, TextureAtlasSprite tex, int r, int g, int b, int a, boolean gas, int overlay, int light)
    {
        // Vertex y coodinates
        float minY = gas ? MAX_Y - height * (MAX_Y - MIN_Y) : MIN_Y;
        float maxY = gas ? MAX_Y : MIN_Y + height * (MAX_Y - MIN_Y);

        // Texture coordinates
        float minU = tex.getU(MIN_U_S);
        float maxU = tex.getU(MAX_U_S);
        float minV = tex.getV(MIN_V_S);
        float maxV = tex.getV(MIN_V_S + height * (MAX_V_S - MIN_V_S));

        // North
        drawSideFace(builder, matrix, normal, MAX_X, minY, MIN_Z, MIN_X, maxY, MIN_Z, minU, minV, maxU, maxV, 0, -1, r, g, b, a, overlay, light);

        // South
        drawSideFace(builder, matrix, normal, MIN_X, minY, MAX_Z, MAX_X, maxY, MAX_Z, minU, minV, maxU, maxV, 0, 1, r, g, b, a, overlay, light);

        // East
        drawSideFace(builder, matrix, normal, MAX_X, minY, MAX_Z, MAX_X, maxY, MIN_Z, minU, minV, maxU, maxV, 1, 0, r, g, b, a, overlay, light);

        // West
        drawSideFace(builder, matrix, normal, MIN_X, minY, MIN_Z, MIN_X, maxY, MAX_Z, minU, minV, maxU, maxV, -1, 0, r, g, b, a, overlay, light);
    }

    private static void drawSideFace(IVertexBuilder builder, Matrix4f matrix, Matrix3f normal, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, float minU, float minV, float maxU, float maxV, int nX, int nZ, int r, int g, int b, int a, int overlay, int light)
    {
        builder.pos(matrix, maxX, maxY, maxZ).color(r, g, b, a).tex(minU, minV).lightmap(light).normal(nX, 0, nZ).endVertex();
        builder.pos(matrix, minX, maxY, minZ).color(r, g, b, a).tex(maxU, minV).lightmap(light).normal(nX, 0, nZ).endVertex();
        builder.pos(matrix, minX, minY, minZ).color(r, g, b, a).tex(maxU, maxV).lightmap(light).normal(nX, 0, nZ).endVertex();
        builder.pos(matrix, maxX, minY, maxZ).color(r, g, b, a).tex(minU, maxV).lightmap(light).normal(nX, 0, nZ).endVertex();
    }

    private static void drawTopBottomFace(IVertexBuilder builder, Matrix4f matrix, float minX, float minZ, float maxX, float maxZ, float y, float minU, float minV, float maxU, float maxV, int nY, int r, int g, int b, int a, int overlay)
    {
        drawTopBottomFace(builder, matrix, );
    }

    private static void drawTopBottomFace(IVertexBuilder builder, Matrix4f matrix, Matrix3f normal, float minX, float minZ, float maxX, float maxZ, float y, float minU, float minV, float maxU, float maxV, int nY, int r, int g, int b, int a, int overlay, int light)
    {
        builder.pos(matrix, maxX, y, minZ).color(r, g, b, a).tex(minU, minV).lightmap(light).normal(0, nY, 0).endVertex();
        builder.pos(matrix, minX, y, minZ).color(r, g, b, a).tex(maxU, minV).lightmap(light).normal(0, nY, 0).endVertex();
        builder.pos(matrix, minX, y, maxZ).color(r, g, b, a).tex(maxU, maxV).lightmap(light).normal(0, nY, 0).endVertex();
        builder.pos(matrix, maxX, y, maxZ).color(r, g, b, a).tex(minU, maxV).lightmap(light).normal(0, nY, 0).endVertex();
    }*/
}
