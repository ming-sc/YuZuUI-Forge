package com.img.mixin;

import com.img.GuiGraphicsMixinInterface;
import com.img.MinecraftClientMixinInterface;
import com.img.YuZuUI;
import com.img.gui.Layer;
import com.img.gui.TitleScreenButton;
import com.img.gui.VirtualScreen;
import com.img.init.InitSounds;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.ModListScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;

/**
 * @author : IMG
 * @create : 2024/10/25
 */
@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    @Shadow @Final private boolean fading;
    @Shadow @Nullable private RealmsNotificationsScreen realmsNotificationsScreen;

    @Shadow protected abstract boolean realmsNotificationsEnabled();

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(YuZuUI.MODID, "textures/gui/background.png");
    private static final ResourceLocation TITLE_YOSHINO = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_yoshino.png");
    private static final ResourceLocation TITLE_MURASAME = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_murasame.png");
    private static final ResourceLocation TITLE_MAKO = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_mako.png");
    private static final ResourceLocation TITLE_LENA = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_lena.png");
    private static final ResourceLocation TITLE_LOGO = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_logo.png");
    private static final ResourceLocation TITLE_HEAD = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_head.png");

    private static final ResourceLocation TITLE_NEW_GAME_BUTTON_NORMAL = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_new_game_button_normal.png");
    private static final ResourceLocation TITLE_NEW_GAME_BUTTON_ON = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_new_game_button_on.png");

    private static final ResourceLocation TITLE_SELECT_WORLD_BUTTON_NORMAL = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_select_world_button_normal.png");
    private static final ResourceLocation TITLE_SELECT_WORLD_BUTTON_ON = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_select_world_button_on.png");

    private static final ResourceLocation TITLE_OPTIONS_BUTTON_NORMAL = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_options_button_normal.png");
    private static final ResourceLocation TITLE_OPTIONS_BUTTON_ON = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_options_button_on.png");

    private static final ResourceLocation TITLE_QUIT_GAME_BUTTON_NORMAL = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_quit_game_button_normal.png");
    private static final ResourceLocation TITLE_QUIT_GAME_BUTTON_ON = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_quit_game_button_on.png");

    private static final ResourceLocation TITLE_CONTINUE_BUTTON_NORMAL = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_continue_button_normal.png");
    private static final ResourceLocation TITLE_CONTINUE_BUTTON_ON = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_continue_button_on.png");

    private static final ResourceLocation TITLE_REAMLS_BUTTON_NORMAL = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_realms_button_normal.png");
    private static final ResourceLocation TITLE_REAMLS_BUTTON_ON = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_realms_button_on.png");

    private static final ResourceLocation TITLE_MOD_LIST_BUTTON_NORMAL = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_mod_list_button_normal.png");
    private static final ResourceLocation TITLE_MOD_LIST_BUTTON_ON = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_mod_list_button_on.png");

    // 人物立绘
    private static Layer yoshinoLayer;
    private static Layer murasameLayer;
    private static Layer makoLayer;
    private static Layer lenaLayer;

    // logo
    private static Layer logoLayer;
    // 左边菜单
    private static Layer headLayer;

    // 按钮
    private static TitleScreenButton newGameButton;
    private static TitleScreenButton selectWorldButton;
    private static TitleScreenButton continueButton;
    private static TitleScreenButton realmsButton;
    private static TitleScreenButton modListButton;
    private static TitleScreenButton optionsButton;
    private static TitleScreenButton quitGameButton;

    private static final VirtualScreen VIRTUAL_SCREEN = new VirtualScreen(1920, 1080);

    private static final double a = 0.06;
    private static int y = 346;
    private static int dy = 100;
    private static Long delay = 1300L;

    protected TitleScreenMixin(Component p_96550_) {
        super(p_96550_);
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        // 背景图片保持比例
        int screenWidth = this.width;
        int screenHeight = this.height;
        // 背景适应后的数据
        int currentWidth;
        int currentHeight;
        if (screenWidth * 9 > screenHeight * 16) {
            currentWidth = screenHeight * 16 / 9;
            currentHeight = screenHeight;
        }else {
            currentWidth = screenWidth;
            currentHeight = screenWidth * 9 / 16;
        }
        int currentX = (screenWidth - currentWidth) / 2;
        int currentY = (screenHeight - currentHeight) / 2;

        VIRTUAL_SCREEN.setPracticalWidth(currentWidth);
        VIRTUAL_SCREEN.setPracticalHeight(currentHeight);
        VIRTUAL_SCREEN.setCurrentX(currentX);
        VIRTUAL_SCREEN.setCurrentY(currentY);

        // 绘制黑色背景
        guiGraphics.fill(0, 0, screenWidth, screenHeight, 0xFF000000);

        // 开启裁剪
        guiGraphics.enableScissor(currentX, currentY, (int) VIRTUAL_SCREEN.toPracticalX(1920), (int) VIRTUAL_SCREEN.toPracticalY(1080));

        // 开启混合处理半透明
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        // 绘制背景
        GuiGraphicsMixinInterface graphics = (GuiGraphicsMixinInterface) guiGraphics;
        graphics.blit(BACKGROUND_TEXTURE, currentX, currentY, currentWidth, currentHeight, 0, 0, 16, 9, 16, 9);

        // 绘制人物立绘
        if (lenaLayer != null) {
            lenaLayer.render(guiGraphics, mouseX, mouseY, delta);
        }
        if (makoLayer != null) {
            makoLayer.render(guiGraphics, mouseX, mouseY, delta);
        }
        if (murasameLayer != null) {
            murasameLayer.render(guiGraphics, mouseX, mouseY, delta);
        }
        if (yoshinoLayer != null) {
            yoshinoLayer.render(guiGraphics, mouseX, mouseY, delta);
        }

        // 绘制左边的菜单 这里就不用Layer了
        if (headLayer != null){
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, headLayer.getAlpha());

            guiGraphics.fill(currentX, currentY, (int) VIRTUAL_SCREEN.toPracticalX(296), (int) VIRTUAL_SCREEN.toPracticalY(1080), 0xFFFFFFFF);
            graphics.fillGradientVertical((int) VIRTUAL_SCREEN.toPracticalX(296), currentY, (int) VIRTUAL_SCREEN.toPracticalX(600), (int) VIRTUAL_SCREEN.toPracticalY(1080), 0, 0xFFFFFFFF, 0x00FFFFFF);

            RenderSystem.enableBlend();
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, headLayer.getAlpha());
            graphics.blit(TITLE_HEAD, currentX, (int) VIRTUAL_SCREEN.toPracticalY(334), (int) VIRTUAL_SCREEN.toPracticalWidth(12), (int) VIRTUAL_SCREEN.toPracticalHeight(687), 0f, 0f, 256, 256, 256, 256);
        }

        // 绘制按钮
        if (newGameButton != null) {
            newGameButton.render(guiGraphics, mouseX, mouseY, delta);
        }
        if (selectWorldButton != null) {
            selectWorldButton.render(guiGraphics, mouseX, mouseY, delta);
        }
        if (continueButton != null) {
            continueButton.render(guiGraphics, mouseX, mouseY, delta);
        }
        if (realmsButton != null) {
            realmsButton.render(guiGraphics, mouseX, mouseY, delta);
        }
        if (optionsButton != null) {
            optionsButton.render(guiGraphics, mouseX, mouseY, delta);
        }
        if (quitGameButton != null) {
            quitGameButton.render(guiGraphics, mouseX, mouseY, delta);
        }
        if (modListButton != null) {
            modListButton.render(guiGraphics, mouseX, mouseY, delta);
        }

        // 绘制logo
        if (logoLayer != null) {
            logoLayer.render(guiGraphics, mouseX, mouseY, delta);
        }
        // 恢复初始状态
        guiGraphics.disableScissor();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        ci.cancel();
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        Overlay overlay = this.minecraft.getOverlay();
        if (overlay == null || (overlay instanceof LoadingOverlay)) {
            if (yoshinoLayer != null) {
                yoshinoLayer.tick();
            }
            if (murasameLayer != null) {
                murasameLayer.tick();
            }
            if (makoLayer != null) {
                makoLayer.tick();
            }
            if (lenaLayer != null) {
                lenaLayer.tick();
            }
            if (headLayer != null) {
                headLayer.tick();
            }
            if (logoLayer != null) {
                logoLayer.tick();
            }
            if (newGameButton != null) {
                newGameButton.tick();
            }
            if (selectWorldButton != null) {
                selectWorldButton.tick();
            }
            if (continueButton != null) {
                continueButton.tick();
            }
            if (realmsButton != null) {
                realmsButton.tick();
            }
            if (optionsButton != null) {
                optionsButton.tick();
            }
            if (quitGameButton != null) {
                quitGameButton.tick();
            }
            if (modListButton != null) {
                modListButton.tick();
            }
        }
    }

    @Inject(method = "createNormalMenuOptions", at = @At("HEAD"), cancellable = true)
    private void createNormalMenuOptions(int y, int spacingY, CallbackInfo ci) {
        MinecraftClientMixinInterface minecraft = (MinecraftClientMixinInterface) this.minecraft;
        if (minecraft.getCurrentIsInGame()) {
            initWidgets();
            minecraft.setCurrentIsInGame(false);
        }

        // 设置按钮的点击事件
        if (newGameButton != null) {
            newGameButton.setOnClick((button) -> {
                CreateWorldScreen.openFresh(this.minecraft, this);
            });
            this.addRenderableWidget(newGameButton);
        }

        if (selectWorldButton != null) {
            selectWorldButton.setOnClick((button) -> {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(InitSounds.YUZU_TITLE_BUTTON_SELECT_WORLD.get(), 1.0f, 1.0f));
                this.minecraft.setScreen(new SelectWorldScreen(this));
            });
            this.addRenderableWidget(selectWorldButton);
        }

        if (continueButton != null) {
            continueButton.setOnClick((button) -> {
                Screen screen = this.minecraft.options.skipMultiplayerWarning ? new JoinMultiplayerScreen(this) : new SafetyScreen(this);
                this.minecraft.setScreen(screen);
            });
            this.addRenderableWidget(continueButton);
        }

        if (realmsButton != null) {
            realmsButton.setOnClick((button) -> {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(InitSounds.YUZU_TITLE_BUTTON_REALMS.get(), 1.0f, 1.0f));
                this.minecraft.setScreen(new RealmsMainScreen(this));
            });
            this.addRenderableWidget(realmsButton);
        }

        if (optionsButton != null) {
            optionsButton.setOnClick((button) -> {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(InitSounds.YUZU_TITLE_BUTTON_OPTIONS.get(), 1.0f, 1.0f));
                this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options));
            });
            this.addRenderableWidget(optionsButton);
        }

        if (quitGameButton != null) {
            quitGameButton.setOnClick((button) -> {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(InitSounds.YUZU_TITLE_BUTTON_QUIT_GAME.get(), 1.0f, 1.0f));
                CompletableFuture.completedFuture(null).whenComplete((unused, e) -> {
                    try {
                        // 等待音效播放完成
                        sleep(1500);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    this.minecraft.stop();
                });
            });
            this.addRenderableWidget(quitGameButton);
        }

        if (modListButton != null) {
            modListButton.setOnClick((button) -> {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(InitSounds.YUZU_TITLE_BUTTON_MOD_LIST.get(), 1.0f, 1.0f));
                this.minecraft.setScreen(new ModListScreen(this));
            });
            this.addRenderableWidget(modListButton);
        }

        ci.cancel();
    }

    /**
     * 初始化组件
     */
    private void initWidgets() {
        // 如果是从游戏中返回标题界面的, 动画延迟时间要短一点
        if (!this.fading) {
            delay = 200L;
        }
        yoshinoLayer = new Layer(TITLE_YOSHINO, 504, 50, 973, 1058, 1f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN){{
            setDelay(delay);
            setDuration(590L);

            setYFunction((t, now) -> {
                return (25f - 50f) * (float) ((Math.pow(a, t) - 1) / (a - 1))  + 50f;
            });

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 0f;
            });
        }};

        murasameLayer = new Layer(TITLE_MURASAME, 221, 90, 1045, 994, 1f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN){{
            setDelay(delay + 110L);
            setDuration(710L);

            setXFunction((t, now) -> {
                return (173f - 221f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 221f;
            });

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 0f;
            });
        }};

        makoLayer = new Layer(TITLE_MAKO, 805, 387, 1118, 694, 1f, 0,0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN){{
            setDelay(delay + 280L);
            setDuration(680L);

            setXFunction((t, now) -> {
                return (898f - 805f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 805f;
            });

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 0f;
            });
        }};

        lenaLayer = new Layer(TITLE_LENA, 1002, 149,876, 1053, 1f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN){{
            setDelay(delay + 440L);
            setDuration(680L);

            setXFunction((t, now) -> {
                return (float)((1065f - 1002f) *  ((Math.pow(0.05, t) - 1) / (0.05 - 1)) + 1002f);
            });

            setYFunction((t, now) -> {
                return (float)((27f - 149f) * ((Math.pow(0.05, t) - 1) / (0.05 - 1)) + 149f);
            });

            setAlphaFunction((t, now) -> {
                return (float)((1f - 0f) * ((Math.pow(0.05, t) - 1) / (0.05 - 1)) + 0f);
            });
        }};

        logoLayer = new Layer(TITLE_LOGO, 17, 57, 442, 188, 1.067f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN){{
            setDelay(delay + 300L);
            setDuration(570L);

            setXFunction((t, now) -> {
                return (31f - 17f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) + 17f;
            });

            setYFunction((t, now) -> {
                return (60f - 57f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) + 57f;
            });

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) + 0f;
            });

            setScaleFunction((t, now) -> {
                return (1f - 1.067f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) + 1.067f;
            });
        }};

        headLayer = new Layer(TITLE_HEAD, 0, 0, 536, 1080, 1f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN){{
            setDelay(delay + 1130L);
            setDuration(530L);

            setAlphaFunction((t, now) -> {
                float v = (1f - 0f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) + 0f;
                if (v == 1f && now != 1f){
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(InitSounds.YUZU_TITLE_SENREN.get(), 1.0f, 10.0f));
                }
                return v;
            });
        }};

        newGameButton = new TitleScreenButton(60, y, 207, 54, TITLE_NEW_GAME_BUTTON_NORMAL, TITLE_NEW_GAME_BUTTON_ON, VIRTUAL_SCREEN, 0f){{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        selectWorldButton = new TitleScreenButton(60, y + dy, 206, 55, TITLE_SELECT_WORLD_BUTTON_NORMAL, TITLE_SELECT_WORLD_BUTTON_ON, VIRTUAL_SCREEN, 0f){{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        continueButton = new TitleScreenButton(66, y + dy * 2, 313, 56, TITLE_CONTINUE_BUTTON_NORMAL, TITLE_CONTINUE_BUTTON_ON, VIRTUAL_SCREEN, 0f){{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        realmsButton = new TitleScreenButton(66, y + dy * 3, 164, 54, TITLE_REAMLS_BUTTON_NORMAL, TITLE_REAMLS_BUTTON_ON, VIRTUAL_SCREEN, 0f){{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        modListButton = new TitleScreenButton(58, y + dy * 4, 211, 54, TITLE_MOD_LIST_BUTTON_NORMAL, TITLE_MOD_LIST_BUTTON_ON, VIRTUAL_SCREEN, 0f){{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        optionsButton = new TitleScreenButton(59, y + dy * 5, 253, 56, TITLE_OPTIONS_BUTTON_NORMAL, TITLE_OPTIONS_BUTTON_ON, VIRTUAL_SCREEN, 0f){{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        quitGameButton = new TitleScreenButton(60, y + dy * 6, 233, 54, TITLE_QUIT_GAME_BUTTON_NORMAL, TITLE_QUIT_GAME_BUTTON_ON, VIRTUAL_SCREEN, 0f){{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};
    }

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/TitleScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;"), cancellable = true)
    private void init(CallbackInfo ci) {
        this.minecraft.setConnectedToRealms(false);
        if (this.realmsNotificationsScreen == null) {
            this.realmsNotificationsScreen = new RealmsNotificationsScreen();
        }

        if (this.realmsNotificationsEnabled()) {
            this.realmsNotificationsScreen.init(this.minecraft, this.width, this.height);
        }
        ci.cancel();
    }
}
