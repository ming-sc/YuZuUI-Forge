package com.img;

/**
 * @author : IMG
 * @create : 2024/10/26
 */
public interface MinecraftClientMixinInterface {
    boolean getCurrentIsInGame();

    void setCurrentIsInGame(boolean isInGame);
}
