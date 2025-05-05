package com.img;

import com.img.init.InitSounds;
import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(YuZuUI.MODID)
public class YuZuUI
{
    public static final String MODID = "yuzu";
    public static final Logger LOGGER = LogUtils.getLogger();

    public YuZuUI() {
        InitSounds.SOUND_EVENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
