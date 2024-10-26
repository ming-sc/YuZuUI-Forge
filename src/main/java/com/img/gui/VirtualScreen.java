package com.img.gui;

/**
 * @author : IMG
 * @create : 2024/10/26
 */
public class VirtualScreen {
    // 虚拟宽高
    private int virtualWidth;
    private int virtualHeight;

    // 实际宽高
    private int practicalWidth;
    private int practicalHeight;

    private int currentX;
    private int currentY;

    public VirtualScreen() {
    }

    public VirtualScreen(int virtualWidth, int virtualHeight) {
        this.virtualWidth = virtualWidth;
        this.virtualHeight = virtualHeight;
    }

    public float toPracticalX(float x) {
        return currentX + x * (float) practicalWidth / (float) virtualWidth;
    }

    public float toPracticalY(float y) {
        return currentY + y * (float) practicalHeight / (float) virtualHeight;
    }

    public float toPracticalWidth(float width) {
        return width * (float) practicalWidth / (float) virtualWidth;
    }

    public float toPracticalHeight(float height) {
        return height * (float) practicalHeight / (float) virtualHeight;
    }

    public float toVirtualX(float x) {
        return (x - currentX) * (float) virtualWidth / (float) practicalWidth;
    }

    public float toVirtualY(float y) {
        return (y - currentY) * (float) virtualHeight / (float) practicalHeight;
    }

    public int getVirtualWidth() {
        return virtualWidth;
    }

    public void setVirtualWidth(int virtualWidth) {
        this.virtualWidth = virtualWidth;
    }

    public int getVirtualHeight() {
        return virtualHeight;
    }

    public void setVirtualHeight(int virtualHeight) {
        this.virtualHeight = virtualHeight;
    }

    public int getPracticalWidth() {
        return practicalWidth;
    }

    public void setPracticalWidth(int practicalWidth) {
        this.practicalWidth = practicalWidth;
    }

    public int getPracticalHeight() {
        return practicalHeight;
    }

    public void setPracticalHeight(int practicalHeight) {
        this.practicalHeight = practicalHeight;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }
}
