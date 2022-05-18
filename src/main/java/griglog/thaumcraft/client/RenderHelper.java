package griglog.thaumcraft.client;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import static net.minecraft.util.ColorHelper.PackedColor.*;

public class RenderHelper {
    public static void drawTop(IVertexBuilder builder, Matrix4f mPos, float x1, float x2, float y, float z1, float z2, int a, int r, int g, int b, TextureAtlasSprite sprite){
        builder.pos(mPos, x1, y, z1).color(r, g, b, a).tex(sprite.getMinU(), sprite.getMinV()).lightmap(0, 240).normal(1, 0, 0).endVertex();
        builder.pos(mPos, x1, y, z2).color(r, g, b, a).tex(sprite.getMaxU(), sprite.getMinV()).lightmap(0, 240).normal(1, 0, 0).endVertex();
        builder.pos(mPos, x2, y, z2).color(r, g, b, a).tex(sprite.getMaxU(), sprite.getMaxV()).lightmap(0, 240).normal(1, 0, 0).endVertex();
        builder.pos(mPos, x2, y, z1).color(r, g, b, a).tex(sprite.getMinU(), sprite.getMaxV()).lightmap(0, 240).normal(1, 0, 0).endVertex();
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
