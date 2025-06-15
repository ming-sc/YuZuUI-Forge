package com.img.mixin;

import com.img.init.InitSounds;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * @author : IMG
 * @create : 2024/10/25
 */
@Mixin(Musics.class)
public abstract class MusicsMixin {
    @Redirect(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/core/Holder;IIZ)Lnet/minecraft/sounds/Music;"))
    private static Music redirectMenuMuisc(final Holder<SoundEvent> eventHolder, int minDelay, int maxDelay, boolean replaceCurrentMusic) {
        if (eventHolder.value() == SoundEvents.MUSIC_MENU.value()) {
            return new Music(InitSounds.YUZU_TITLE_MUSIC, 20, 20, true);
        }
        return new Music(eventHolder, minDelay, maxDelay, replaceCurrentMusic);
    }
}
