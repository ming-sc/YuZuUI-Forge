package com.img.event;

import com.img.gui.screen.SenrenBankaTitleScreen;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author : IMG
 * @create : 2025/2/18
 */
@Mod.EventBusSubscriber
public class PlayerJoinEventHandler {
    @SubscribeEvent
    public static void onPlayerJoin(ClientPlayerNetworkEvent.LoggingIn event) {
        System.out.println("Player join event!");
//        SenrenBankaTitleScreen.isShowed = false;
    }
}
