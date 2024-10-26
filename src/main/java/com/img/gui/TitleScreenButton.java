package com.img.gui;

import com.img.GuiGraphicsMixinInterface;
import com.img.function.AnimationFunction;
import com.img.init.InitSounds;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * @author : IMG
 * @create : 2024/10/26
 */
public class TitleScreenButton implements Renderable, GuiEventListener, LayoutElement, NarratableEntry {
    private float x;
    private float y;
    private float width;
    private float height;
    private float alpha;

    public boolean visible = true;
    private boolean isHovered = false;
    private boolean isFocused = false;

    private ResourceLocation texture;
    private ResourceLocation textureHover;
    private VirtualScreen virtualScreen;

    private Consumer<TitleScreenButton> onClick;

    public TitleScreenButton(float x, float y, float width, float height, ResourceLocation texture, ResourceLocation textureHover, VirtualScreen virtualScreen, float alpha) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = texture;
        this.textureHover = textureHover;
        this.virtualScreen = virtualScreen;
        this.alpha = alpha;
    }

    public void renderButton(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        GuiGraphicsMixinInterface graphics = (GuiGraphicsMixinInterface) guiGraphics;
        if (this.visible) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
            if (this.isHovered()) {
                graphics.blit(
                        textureHover,
                        virtualScreen.toPracticalX(x),
                        virtualScreen.toPracticalY(y),
                        virtualScreen.toPracticalWidth(width),
                        virtualScreen.toPracticalHeight(height),
                        0, 0,
                        256, 256,
                        256, 256
                );
            }else {
                graphics.blit(
                        texture,
                        virtualScreen.toPracticalX(x),
                        virtualScreen.toPracticalY(y),
                        virtualScreen.toPracticalWidth(width),
                        virtualScreen.toPracticalHeight(height),
                        0, 0,
                        256, 256,
                        256, 256
                );
            }
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        if (this.visible) {
            if (!isHovered() && isMouseOver(mouseX, mouseY)) {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(InitSounds.YUZU_TITLE_BUTTON_ON.get(), 1.0f, 1.0f));
            }
            this.isHovered = this.isMouseOver(mouseX, mouseY);
            this.renderButton(guiGraphics, mouseX, mouseY, delta);
        }
    }

    @Override
    public void mouseMoved(double p_94758_, double p_94759_) {
        GuiEventListener.super.mouseMoved(p_94758_, p_94759_);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.visible && this.isMouseOver(mouseX, mouseY)) {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(InitSounds.YUZU_TITLE_BUTTON_CLICK.get(), 1.0f, 1.0f));
            if (this.onClick != null) {
                this.onClick.accept(this);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double p_94753_, double p_94754_, int p_94755_) {
        return GuiEventListener.super.mouseReleased(p_94753_, p_94754_, p_94755_);
    }

    @Override
    public boolean mouseDragged(double p_94740_, double p_94741_, int p_94742_, double p_94743_, double p_94744_) {
        return GuiEventListener.super.mouseDragged(p_94740_, p_94741_, p_94742_, p_94743_, p_94744_);
    }

    @Override
    public boolean mouseScrolled(double p_94734_, double p_94735_, double p_94736_) {
        return GuiEventListener.super.mouseScrolled(p_94734_, p_94735_, p_94736_);
    }

    @Override
    public boolean keyPressed(int p_94745_, int p_94746_, int p_94747_) {
        return GuiEventListener.super.keyPressed(p_94745_, p_94746_, p_94747_);
    }

    @Override
    public boolean keyReleased(int p_94750_, int p_94751_, int p_94752_) {
        return GuiEventListener.super.keyReleased(p_94750_, p_94751_, p_94752_);
    }

    @Override
    public boolean charTyped(char p_94732_, int p_94733_) {
        return GuiEventListener.super.charTyped(p_94732_, p_94733_);
    }

    @Nullable
    @Override
    public ComponentPath nextFocusPath(FocusNavigationEvent p_265234_) {
        return GuiEventListener.super.nextFocusPath(p_265234_);
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        float virtualX = virtualScreen.toVirtualX((float) mouseX) - x;
        float virtualY = virtualScreen.toVirtualY((float) mouseY) - y;
        return virtualX >= 0 && virtualX < width && virtualY >= 0 && virtualY < height;
    }

    @Override
    public void setFocused(boolean b) {
        this.isFocused = b;
    }

    @Override
    public boolean isFocused() {
        return this.isFocused;
    }

    @Nullable
    @Override
    public ComponentPath getCurrentFocusPath() {
        return GuiEventListener.super.getCurrentFocusPath();
    }

    @Override
    public ScreenRectangle getRectangle() {
        return GuiEventListener.super.getRectangle();
    }

    @Override
    public NarrationPriority narrationPriority() {
        if (this.isFocused()) {
            return NarrationPriority.FOCUSED;
        } else {
            return this.isHovered ? NarrationPriority.HOVERED: NarrationPriority.NONE;
        }
    }

    @Override
    public void setX(int i) {
    }

    @Override
    public void setY(int i) {

    }

    @Override
    public int getX() {
        return (int)this.x;
    }

    @Override
    public int getY() {
        return (int)this.y;
    }

    @Override
    public int getWidth() {
        return (int)this.width;
    }

    @Override
    public int getHeight() {
        return (int)this.height;
    }

    @Override
    public void visitWidgets(Consumer<AbstractWidget> consumer) {

    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {

    }

    public boolean isHovered() {
        return isHovered;
    }

    public void setOnClick(Consumer<TitleScreenButton> onClick) {
        this.onClick = onClick;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getAlpha() {
        return alpha;
    }

    /**
     * 动画相关
     */
    private Long duration;
    private Long startTime = null;
    private Long delay;
    private AnimationFunction<Float> alphaFunction;

    public void setAlphaFunction(AnimationFunction<Float> alphaFunction) {
        this.alphaFunction = alphaFunction;
    }

    public void tick(){
        if (delay == null || duration == 0){
            return;
        }

        long currentTime = Util.getEpochMillis();
        if (startTime == null) {
            startTime = currentTime;
        }else {
            long t = currentTime - startTime;
            if (t > delay) {
                float time = Math.min((float) (t - delay) / duration, 1);
                if (alphaFunction != null) {
                    alpha = alphaFunction.apply(time, alpha);
                }
            }
        }
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
