package com.img.init;

import com.img.YuZuUI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author : IMG
 * @create : 2024/10/25
 */
public class InitSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, YuZuUI.MODID);

    public static RegistryObject<SoundEvent> YUZU_TITLE_MUSIC = register("yuzu_title_music");
    public static RegistryObject<SoundEvent> YUZU_TITLE_BUTTON_ON = register("yuzu_title_button_on");
    public static RegistryObject<SoundEvent> YUZU_TITLE_BUTTON_CLICK = register("yuzu_title_button_click");
    public static RegistryObject<SoundEvent> YUZU_TITLE_BUTTON_NEW_GAME = register("yuzu_title_button_new_game");
    public static RegistryObject<SoundEvent> YUZU_TITLE_BUTTON_SELECT_WORLD = register("yuzu_title_button_select_world");
    public static RegistryObject<SoundEvent> YUZU_TITLE_BUTTON_OPTIONS = register("yuzu_title_button_options");
    public static RegistryObject<SoundEvent> YUZU_TITLE_BUTTON_QUIT_GAME = register("yuzu_title_button_quit_game");
    public static RegistryObject<SoundEvent> YUZU_TITLE_SENREN = register("yuzu_title_senren");
    public static RegistryObject<SoundEvent> YUZU_TITLE_BUTTON_REALMS = register("yuzu_title_button_realms");
    public static RegistryObject<SoundEvent> YUZU_TITLE_BUTTON_MOD_LIST = register("yuzu_title_button_mod_list");

    public static RegistryObject<SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(YuZuUI.MODID, name)));
    }
}
