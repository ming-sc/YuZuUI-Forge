package com.img;

import net.minecraft.resources.ResourceLocation;

/**
 * @author : IMG
 * @create : 2024/10/26
 */
public interface GuiGraphicsMixinInterface {

    default void blit(ResourceLocation texture, float x, float y, float width, float height, float u, float v, int regionWidth, int regionHeight, int textureWidth, int textureHeight) {
    }

    default void blit(ResourceLocation texture, float x1, float x2, float y1, float y2, float z, int regionWidth, int regionHeight, float u, float v, int textureWidth, int textureHeight) {
    }

    default void innerBlit(ResourceLocation texture, float x1, float x2, float y1, float y2, float z, float u1, float u2, float v1, float v2) {
    }

    /**
     * 填充横向渐变色
     * @param startX 起始X
     * @param startY 起始Y
     * @param endX 结束X
     * @param endY 结束Y
     * @param z Z
     * @param colorStart 起始颜色
     * @param colorEnd 结束颜色
     */
    default void fillGradientVertical(int startX, int startY, int endX, int endY, int z, int colorStart, int colorEnd) {
    }
}
