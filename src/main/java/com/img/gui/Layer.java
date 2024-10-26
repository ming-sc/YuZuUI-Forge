package com.img.gui;

import com.img.function.AnimationFunction;
import com.img.GuiGraphicsMixinInterface;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

/**
 * @author : IMG
 * @create : 2024/10/26
 */
public class Layer {
    private ResourceLocation texture;

    private float x;
    private float y;
    private float width;
    private float height;
    private float scale;

    private int u;
    private int v;
    private int regionWidth;
    private int regionHeight;
    private int textureWidth;
    private int textureHeight;

    private float alpha;

    private VirtualScreen virtualScreen;

    public Layer() {
    }

    public Layer(ResourceLocation texture, float x, float y, float width, float height, float scale, int u, int v, int regionWidth, int regionHeight, int textureWidth, int textureHeight, float alpha, VirtualScreen virtualScreen) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.u = u;
        this.v = v;
        this.regionWidth = regionWidth;
        this.regionHeight = regionHeight;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.alpha = alpha;
        this.virtualScreen = virtualScreen;
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, alpha);
        GuiGraphicsMixinInterface graphics = (GuiGraphicsMixinInterface) guiGraphics;
        graphics.blit(
                texture,
                virtualScreen.toPracticalX(x),
                virtualScreen.toPracticalY(y),
                virtualScreen.toPracticalWidth(width * scale),
                virtualScreen.toPracticalHeight(height * scale),
                u, v,
                regionWidth, regionHeight,
                textureWidth, textureHeight
        );
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public void setTexture(ResourceLocation texture) {
        this.texture = texture;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public int getU() {
        return u;
    }

    public void setU(int u) {
        this.u = u;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public int getRegionWidth() {
        return regionWidth;
    }

    public void setRegionWidth(int regionWidth) {
        this.regionWidth = regionWidth;
    }

    public int getRegionHeight() {
        return regionHeight;
    }

    public void setRegionHeight(int regionHeight) {
        this.regionHeight = regionHeight;
    }

    public int getTextureWidth() {
        return textureWidth;
    }

    public void setTextureWidth(int textureWidth) {
        this.textureWidth = textureWidth;
    }

    public int getTextureHeight() {
        return textureHeight;
    }

    public void setTextureHeight(int textureHeight) {
        this.textureHeight = textureHeight;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public VirtualScreen getVirtualScreen() {
        return virtualScreen;
    }

    public void setVirtualScreen(VirtualScreen virtualScreen) {
        this.virtualScreen = virtualScreen;
    }

    // 动画相关
    private Long duration;
    private Long startTime = null;
    private Long delay;
    private AnimationFunction<Float> xFunction;
    private AnimationFunction<Float> yFunction;
    private AnimationFunction<Float> alphaFunction;
    private AnimationFunction<Float> scaleFunction;

    public void tick() {

        if (delay == null || duration == 0) {
            return;
        }

        long currentTime = Util.getEpochMillis();
        if (startTime == null) {
            startTime = currentTime;
        } else {
            float t = currentTime - startTime;
            if (t > delay) {
                float time = Math.min((float) (t - delay) / duration, 1);
                if (xFunction != null){
                    x = xFunction.apply(time, x);
                }
                if (yFunction != null){
                    y = yFunction.apply(time, y);
                }
                if (alphaFunction != null){
                    alpha = alphaFunction.apply(time, alpha);
                }
                if (scaleFunction != null) {
                    scale = scaleFunction.apply(time, scale);
                }
            }
        }
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getDelay() {
        return delay;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    public AnimationFunction<Float> getXFunction() {
        return xFunction;
    }

    public void setXFunction(AnimationFunction<Float> xFunction) {
        this.xFunction = xFunction;
    }

    public AnimationFunction<Float> getYyFunction() {
        return yFunction;
    }

    public void setYFunction(AnimationFunction<Float> yFunction) {
        this.yFunction = yFunction;
    }

    public AnimationFunction<Float> getAlphaFunction() {
        return alphaFunction;
    }

    public void setAlphaFunction(AnimationFunction<Float> alphaFunction) {
        this.alphaFunction = alphaFunction;
    }

    public AnimationFunction<Float> getScaleFunction() {
        return scaleFunction;
    }

    public void setScaleFunction(AnimationFunction<Float> scaleFunction) {
        this.scaleFunction = scaleFunction;
    }
}
