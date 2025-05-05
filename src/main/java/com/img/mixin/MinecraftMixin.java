package com.img.mixin;

import com.img.gui.screen.SenrenBankaTitleScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

/**
 * @author : IMG
 * @create : 2024/10/26
 */
@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    @ModifyVariable(method = "setScreen", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private Screen setScreen(Screen screen) {
        if (screen instanceof TitleScreen && !(screen instanceof SenrenBankaTitleScreen)) {
            return new SenrenBankaTitleScreen();
        }
        return screen;
    }
}
