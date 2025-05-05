package com.img.utils;


import com.img.gui.Bone;
import com.img.gui.Cube;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * @author : IMG
 * @create : 2025/2/16
 * @description : 来自<a href="https://github.com/paulzzh/YuZuUI-GTNH/blob/master/src/main/java/com/paulzzh/yuzu/gui/RenderUtils.java">paulzzh/YuZuUI-GTNH</a>, 有部分修改
 */
public class RenderUtils {
    private static float TEXTURE_WIDTH;
    private static float TEXTURE_HEIGHT;

    public static void blit(float x, float y, float width, float height, PoseStack poseStack) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        Matrix4f matrix4f = poseStack.last().pose();
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(matrix4f, x, y, 0).uv(0, 0).endVertex();
        bufferBuilder.vertex(matrix4f, x, y + height, 0).uv(0, 1).endVertex();
        bufferBuilder.vertex(matrix4f, x + width, y + height, 0).uv(1, 1).endVertex();
        bufferBuilder.vertex(matrix4f, x + width, y, 0).uv(1, 0).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());
    }

    public static void blit(float x, float y,float z, float width, float height, PoseStack poseStack, float u1, float v1, float u2, float v2, VertexConsumer buffer) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        Matrix4f matrix4f = poseStack.last().pose();
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(matrix4f, x, y, z).uv(u1, v1).endVertex();
        bufferBuilder.vertex(matrix4f, x, y - height, z).uv(u1, v2).endVertex();
        bufferBuilder.vertex(matrix4f, x - width, y - height, z).uv(u2, v2).endVertex();
        bufferBuilder.vertex(matrix4f, x - width, y, z).uv(u2, v1).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());
//        buffer.vertex(matrix4f, x, y, z).uv(u1, v1).color(0, 0, 0, 255).normal(0,0,0).uv2(1).overlayCoords(1).endVertex();
//        buffer.vertex(matrix4f, x, y - height, z).uv(u1, v2).color(0, 0, 0, 255).normal(0,0,0).uv2(1).overlayCoords(1).endVertex();
//        buffer.vertex(matrix4f, x - width, y - height, z).uv(u2, v2).color(0, 0, 0, 255).normal(0,0,0).uv2(1).overlayCoords(1).endVertex();
//        buffer.vertex(matrix4f, x - width, y, z).uv(u2, v1).color(0, 0, 0, 255).normal(0,0,0).uv2(1).overlayCoords(1).endVertex();
    }

    public static void drawFace(PoseStack poseStack, float width, float height, float depth, float u1, float v1, float u2, float v2, VertexConsumer buffer) {
        float widthHalf = width / 2;
        float heightHalf = height / 2;
        float depthHalf = depth / 2;
        blit(widthHalf, heightHalf, -depthHalf, width, height, poseStack, u1, v1, u2, v2, buffer);
    }

    public static void drawCube(PoseStack poseStack, Cube cube, VertexConsumer buffer) {
        drawCube(poseStack, cube.getOrigin(), cube.getSize(), cube.getPivot(), cube.getRotation(), cube.getUv(), cube.getInflate(), buffer);
    }

    public static void drawCube(PoseStack poseStack, Vector3f origin, Vector3f size, Vector3f pivot, Vector3f rotation, Cube.UV uv, float infalte, VertexConsumer buffer) {
        float x = origin.x();
        float y = origin.y();
        float z = origin.z();
        float width = size.x() + 2 * infalte;
        float height = size.y() + 2 * infalte;
        float depth = size.z() + 2 * infalte;
        float widthHalf = width / 2;
        float heightHalf = height / 2;
        float depthHalf = depth / 2;
        poseStack.pushPose();
        // origin
        poseStack.translate(-(x - pivot.x / 16f), y, (z - pivot.z / 16f));
        // scale
//        poseStack.scale(1, 1, 1);
        // rotate
//        pivot = new Vector3f(pivot.x() + size.x() / 2f, pivot.y() + size.y() / 2f, pivot.z() + size.z() / 2f);
        rotateForCube(poseStack, pivot, rotation);
        // origin to left bottom
        poseStack.translate(-size.x() / 2f , size.y() / 2f, size.z() / 2f);


        Cube.UV.UVInfo uvInfo;
        float u1;
        float v1;
        float u2;
        float v2;

        // front
        uvInfo = uv.getNorth();
        u1 = uvInfo.getUv().x();
        v1 = uvInfo.getUv().y();
        u2 = uvInfo.getUvSize().x() + u1;
        v2 = uvInfo.getUvSize().y() + v1;
        blit(widthHalf, heightHalf, -depthHalf, width, height, poseStack,u1 / TEXTURE_WIDTH, v1 / TEXTURE_HEIGHT, u2 / TEXTURE_WIDTH, v2 / TEXTURE_HEIGHT, buffer);

        // back
        uvInfo = uv.getSouth();
        u1 = uvInfo.getUv().x();
        v1 = uvInfo.getUv().y();
        u2 = uvInfo.getUvSize().x() + u1;
        v2 = uvInfo.getUvSize().y() + v1;
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(180));
        blit(widthHalf, heightHalf, -depthHalf, width, height, poseStack, u1 / TEXTURE_WIDTH, v1 / TEXTURE_HEIGHT, u2 / TEXTURE_WIDTH, v2 / TEXTURE_HEIGHT, buffer);
        poseStack.popPose();

        // right
        uvInfo = uv.getEast();
        u1 = uvInfo.getUv().x();
        v1 = uvInfo.getUv().y();
        u2 = uvInfo.getUvSize().x() + u1;
        v2 = uvInfo.getUvSize().y() + v1;
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(-90));
        blit(depthHalf, heightHalf, -widthHalf, depth, height, poseStack, u1 / TEXTURE_WIDTH, v1 / TEXTURE_HEIGHT, u2 / TEXTURE_WIDTH, v2 / TEXTURE_HEIGHT, buffer);
        poseStack.popPose();

        // left
        uvInfo = uv.getWest();
        u1 = uvInfo.getUv().x();
        v1 = uvInfo.getUv().y();
        u2 = uvInfo.getUvSize().x() + u1;
        v2 = uvInfo.getUvSize().y() + v1;
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(90));
        blit(depthHalf, heightHalf, -widthHalf, depth, height, poseStack, u1 / TEXTURE_WIDTH, v1 / TEXTURE_HEIGHT, u2 / TEXTURE_WIDTH, v2 / TEXTURE_HEIGHT, buffer);
        poseStack.popPose();

        // top
        uvInfo = uv.getUp();
        u1 = uvInfo.getUv().x();
        v1 = uvInfo.getUv().y();
        u2 = uvInfo.getUvSize().x() + u1;
        v2 = uvInfo.getUvSize().y() + v1;
        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(90));
        blit(widthHalf, depthHalf, -heightHalf, width, depth, poseStack, u1 / TEXTURE_WIDTH, v1 / TEXTURE_HEIGHT, u2 / TEXTURE_WIDTH, v2 / TEXTURE_HEIGHT, buffer);
        poseStack.popPose();

        // bottom
        uvInfo = uv.getDown();
        u1 = uvInfo.getUv().x();
        v1 = uvInfo.getUv().y();
        u2 = uvInfo.getUvSize().x() + u1;
        v2 = uvInfo.getUvSize().y() + v1;
        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(-90));
        blit(widthHalf, depthHalf, -heightHalf, width, depth, poseStack, u1 / TEXTURE_WIDTH, v1 / TEXTURE_HEIGHT, u2 / TEXTURE_WIDTH, v2 / TEXTURE_HEIGHT, buffer);
        poseStack.popPose();

        poseStack.popPose();
    }

    public static void drawBone(PoseStack poseStack, Bone bone, VertexConsumer buffer) {
        poseStack.pushPose();
        Vector3f pivot = bone.getPivot();
        Vector3f rotation = bone.getRotation();
//        poseStack.translate(pivot.x() / 16f, pivot.y() / 16f, pivot.z() / 16f);
        rotateByPivot(poseStack, pivot, rotation);

        for (Cube cube : bone.getCubes()) {
            drawCube(poseStack, cube, buffer);
        }
        for (Bone child : bone.getChildren()) {
            drawBone(poseStack, child, buffer);
        }

        poseStack.popPose();
    }

    public static void rotateByPivot(PoseStack poseStack, Vector3f pivot, Vector3f rotation) {
        poseStack.translate(pivot.x() / 16f, pivot.y() / 16f, pivot.z() / 16f);
        poseStack.mulPose(Axis.ZP.rotationDegrees(rotation.z()));
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation.y()));
        poseStack.mulPose(Axis.XP.rotationDegrees(rotation.x()));
        poseStack.translate(-pivot.x() / 16f, -pivot.y() / 16f, -pivot.z() / 16f);
    }

    public static void rotateForCube(PoseStack poseStack, Vector3f pivot, Vector3f rotation) {
        poseStack.translate(pivot.x() / 16f, pivot.y() / 16f, pivot.z() / 16f);
        poseStack.mulPose(new Quaternionf().rotationXYZ(0, 0, (float) Math.toRadians(rotation.z())));
        poseStack.mulPose(new Quaternionf().rotationXYZ(0, (float) Math.toRadians(rotation.y()), 0));
        poseStack.mulPose(new Quaternionf().rotationXYZ((float) (Math.toRadians(0 - rotation.x())), 0, 0));
        poseStack.translate(-pivot.x() / 16f, -pivot.y() / 16f, -pivot.z() / 16f);
    }

    public static void setTextureWidth(float textureWidth) {
        TEXTURE_WIDTH = textureWidth;
    }

    public static void setTextureHeight(float textureHeight) {
        TEXTURE_HEIGHT = textureHeight;
    }
}
