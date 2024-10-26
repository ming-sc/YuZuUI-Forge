package com.img.mixin;

import com.img.GuiGraphicsMixinInterface;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author : IMG
 * @create : 2024/10/26
 */
@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin implements GuiGraphicsMixinInterface {
    @Shadow @Final private PoseStack pose;

    @Shadow @Final private MultiBufferSource.BufferSource bufferSource;

    @Shadow @Deprecated protected abstract void flushIfUnmanaged();

    @Override
    public void blit(ResourceLocation texture, float x, float y, float width, float height, float u, float v, int regionWidth, int regionHeight, int textureWidth, int textureHeight) {
        blit(texture, x, x + width, y, y + height, 0, regionWidth, regionHeight, u, v, textureWidth, textureHeight);
    }

    @Override
    public void blit(ResourceLocation texture, float x1, float x2, float y1, float y2, float z, int regionWidth, int regionHeight, float u, float v, int textureWidth, int textureHeight) {
        innerBlit(texture, x1, x2, y1, y2, z, (u + 0.0F) / (float) textureWidth, (u + (float) regionWidth) / (float) textureWidth, (v + 0.0F) / (float) textureHeight, (v + (float) regionHeight) / (float) textureHeight);
    }

    @Override
    public void innerBlit(ResourceLocation texture, float x1, float x2, float y1, float y2, float z, float u1, float u2, float v1, float v2) {
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        Matrix4f matrix4f = this.pose.last().pose();
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(matrix4f, x1, y1, z).uv(u1, v1).endVertex();
        bufferBuilder.vertex(matrix4f, x1, y2, z).uv(u1, v2).endVertex();
        bufferBuilder.vertex(matrix4f, x2, y2, z).uv(u2, v2).endVertex();
        bufferBuilder.vertex(matrix4f, x2, y1, z).uv(u2, v1).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());
    }

    @Override
    public void fillGradientVertical(int startX, int startY, int endX, int endY, int z, int colorStart, int colorEnd) {
        float f = (float) FastColor.ARGB32.alpha(colorStart) / 255.0F;
        float f1 = (float) FastColor.ARGB32.red(colorStart) / 255.0F;
        float f2 = (float) FastColor.ARGB32.green(colorStart) / 255.0F;
        float f3 = (float) FastColor.ARGB32.blue(colorStart) / 255.0F;
        float f4 = (float) FastColor.ARGB32.alpha(colorEnd) / 255.0F;
        float f5 = (float) FastColor.ARGB32.red(colorEnd) / 255.0F;
        float f6 = (float) FastColor.ARGB32.green(colorEnd) / 255.0F;
        float f7 = (float) FastColor.ARGB32.blue(colorEnd) / 255.0F;
        Matrix4f matrix4f = this.pose.last().pose();
        RenderType gui = RenderType.gui();
        VertexConsumer vertexConsumer = this.bufferSource.getBuffer(gui);
        vertexConsumer.vertex(matrix4f, (float)startX, (float)startY, (float)z).color(f1, f2, f3, f).endVertex();
        vertexConsumer.vertex(matrix4f, (float)startX, (float)endY, (float)z).color(f1, f2, f3, f).endVertex();
        vertexConsumer.vertex(matrix4f, (float)endX, (float)endY, (float)z).color(f5, f6, f7, f4).endVertex();
        vertexConsumer.vertex(matrix4f, (float)endX, (float)startY, (float)z).color(f5, f6, f7, f4).endVertex();
        this.flushIfUnmanaged();
    }
}
