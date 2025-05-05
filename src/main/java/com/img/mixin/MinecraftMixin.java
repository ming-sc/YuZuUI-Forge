package com.img.mixin;

import com.img.MinecraftClientMixinInterface;
import com.img.gui.screen.SenrenBankaTitleScreen;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;

import javax.annotation.Nullable;

/**
 * @author : IMG
 * @create : 2024/10/26
 */
@Mixin(Minecraft.class)
public abstract class MinecraftMixin implements MinecraftClientMixinInterface {

    @Shadow @Nullable public Screen screen;
    @Unique
    private boolean currentIsInGame = true;

//    @ModifyVariable(method = "setScreen", at = @At("HEAD"))
//    public Minecraft setScreen(Minecraft value) {
//
//        return value;
//    }

    @ModifyVariable(method = "setScreen", at = @At("HEAD"), ordinal = 0)
    private Screen setScreen(Screen screen) {
        Screen currentScreen = this.screen;
        // 如果当前屏幕为空并且不是以下几种情况, 则认为之前在游戏中, 需要重置动画状态
        if (screen == null &&
                !(
                    currentScreen instanceof CreateWorldScreen ||
                    currentScreen instanceof JoinMultiplayerScreen ||
                    currentScreen instanceof SafetyScreen ||
                    currentScreen instanceof RealmsMainScreen ||
                    currentScreen instanceof OptionsScreen ||
                    currentScreen instanceof RealmsGenericErrorScreen
                )
        ) {
            if (!currentIsInGame) {
                this.currentIsInGame = true;
            }
        }
        if (screen instanceof TitleScreen && !(screen instanceof SenrenBankaTitleScreen)) {
//            TitleScreen titleScreen = (TitleScreen) screen;
            return new SenrenBankaTitleScreen();
        }
        return screen;
    }

    @Override
    public boolean getCurrentIsInGame() {
        return currentIsInGame;
    }

    @Override
    public void setCurrentIsInGame(boolean isInGame) {
        this.currentIsInGame = isInGame;
    }
}
