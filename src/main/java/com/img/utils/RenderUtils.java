package com.img.utils;

import com.mojang.blaze3d.vertex.*;
import org.joml.Matrix4f;

/**
 * @author : IMG
 * @create : 2025/2/16
 * @description : 来自<a href="https://github.com/paulzzh/YuZuUI-GTNH/blob/master/src/main/java/com/paulzzh/yuzu/gui/RenderUtils.java">paulzzh/YuZuUI-GTNH</a>, 有部分修改
 */
public class RenderUtils {

    public static void blit(float x, float y, float width, float height, PoseStack poseStack) {
        Matrix4f matrix4f = poseStack.last().pose();
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(matrix4f, x, y, 0).uv(0, 0).endVertex();
        bufferBuilder.vertex(matrix4f, x, y + height, 0).uv(0, 1).endVertex();
        bufferBuilder.vertex(matrix4f, x + width, y + height, 0).uv(1, 1).endVertex();
        bufferBuilder.vertex(matrix4f, x + width, y, 0).uv(1, 0).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());
    }
}
