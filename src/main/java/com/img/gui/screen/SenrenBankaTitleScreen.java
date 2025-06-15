package com.img.gui.screen;

import com.img.constant.TextureConst;
import com.img.gui.*;
import com.img.init.InitSounds;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.RealmsMainScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.client.gui.ModListScreen;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

/**
 * @author : IMG
 * @create : 2025/2/16
 */
public class SenrenBankaTitleScreen extends TitleScreen {
    private static final VirtualScreen VIRTUAL_SCREEN = new VirtualScreen(1920, 1080);
    private long delay = 0L;
    private final List<Tickable> tickables = Lists.newArrayList();
    public static boolean isShowed = false;
    public static short passExitSound = -1;
    private static final ExecutorService executor;

    static {
        executor = Executors.newFixedThreadPool(1);
    }

    public SenrenBankaTitleScreen() {
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
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


        guiGraphics.fill(0, 0, screenWidth, screenHeight, 0xFF000000);
        guiGraphics.enableScissor(currentX, currentY, currentWidth + currentX, currentHeight + currentY);
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        for (Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, delta);
        }
        RenderSystem.disableBlend();
        guiGraphics.disableScissor();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void tick() {
        super.tick();
        this.tickables.forEach(Tickable::tick);
    }

    @Override
    protected void init() {
        this.minecraft.setConnectedToRealms(false);
        initWidgets();
        if (!isShowed) {
            isShowed = true;
        }
    }

    public void initWidgets() {
        this.clearWidgets();
        this.tickables.clear();

        double a = 0.06;
        delay = 1300L;

        if (isShowed) {
            delay = 100L;
        }

        /**
         * 以下数据来源于 https://github.com/paulzzh/YuZuUI-GTNH/blob/master/src/main/java/com/paulzzh/yuzu/gui/YuZuUIGuiMainMenu.java
         */
        Layer yoshino = new Layer(TextureConst.TITLE_YOSHINO, 517, 50, 973, 1058, 1f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN){{
            setDelay(delay);
            setDuration(590L);

            setYFunction((t, now) -> {
                return (22f - 50f) * (float) ((Math.pow(a, t) - 1) / (a - 1))  + 50f;
            });

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 0f;
            });
        }};

        Layer murasame = new Layer(TextureConst.TITLE_MURASAME, 221, 86, 1045, 994, 1f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN){{
            setDelay(delay + 110L);
            setDuration(710L);

            setXFunction((t, now) -> {
                return (175f - 221f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 221f;
            });

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 0f;
            });
        }};

        Layer mako = new Layer(TextureConst.TITLE_MAKO, 805, 386, 1118, 694, 1f, 0,0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN){{
            setDelay(delay + 280L);
            setDuration(680L);

            setXFunction((t, now) -> {
                return (906f - 805f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 805f;
            });

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 0f;
            });
        }};

        Layer lena = new Layer(TextureConst.TITLE_LENA, 1002, 149,876, 1053, 1f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN){{
            setDelay(delay + 440L);
            setDuration(680L);

            setXFunction((t, now) -> {
                return (float)((1074f - 1002f) *  ((Math.pow(0.05, t) - 1) / (0.05 - 1)) + 1002f);
            });

            setYFunction((t, now) -> {
                return (float)((27f - 149f) * ((Math.pow(0.05, t) - 1) / (0.05 - 1)) + 149f);
            });

            setAlphaFunction((t, now) -> {
                return (float)((1f - 0f) * ((Math.pow(0.05, t) - 1) / (0.05 - 1)) + 0f);
            });
        }};

        Layer logo = new Layer(TextureConst.TITLE_LOGO, 17, 57, 442, 188, 1.067f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN){{
            setDelay(delay + 300L);
            setDuration(570L);

            setXFunction((t, now) -> {
                return (36f - 17f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) + 17f;
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

        Layer head = new Layer(TextureConst.TITLE_HEAD, 0, 334, 12, 687, 1f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN){{
            setDelay(delay + 1130L);
            setDuration(530L);

            setAlphaFunction((t, now) -> {
                float v = (1f - 0f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) + 0f;
                if (v == 1f && now != 1f){
                    playSound(InitSounds.YUZU_TITLE_SENREN);
                }
                return v;
            });
        }};

        Layer background = new Layer(TextureConst.BACKGROUND_TEXTURE, -64, -36, 1920, 1080, 1.067f, 0, 0, 256, 256, 256, 256, 1, VIRTUAL_SCREEN){{
            setDelay(delay);
            setDuration(1120L);

            setXFunction((t, now) -> {
                return (64f - 0f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) - 64f;
            });

            setYFunction((t, now) -> {
                return (36f - 0f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) - 36f;
            });

            setScaleFunction((t, now) -> {
                return (1f - 1.067f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) + 1.067f;
            });
        }};

        Layer all = new Layer(TextureConst.TITLE_CHARALL, 0, 0, 1920, 1080, 1f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN){{
            setDelay(delay + 1130L);
            setDuration(530L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) + 0f;
            });
        }};

        this.addChild(background);
        this.addChild(lena);
        this.addChild(mako);
        this.addChild(murasame);
        this.addChild(yoshino);
        this.addChild(all);
        this.addChild(logo);
        this.addChild(head);

        // 添加按钮
        int y = 346;
        int dy = 100;

        // 新建世界
        TitleScreenButton newGameButton = new TitleScreenButton(60, y, 207, 54, TextureConst.TITLE_NEW_GAME_BUTTON_NORMAL, TextureConst.TITLE_NEW_GAME_BUTTON_ON, VIRTUAL_SCREEN, 0f,0){{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        newGameButton.setOnClick((button) -> {
            CreateWorldScreen.openFresh(this.minecraft, this);
        });


        // 选择世界
        TitleScreenButton selectWorldButton = new TitleScreenButton(60, y + dy, 206, 55, TextureConst.TITLE_SELECT_WORLD_BUTTON_NORMAL, TextureConst.TITLE_SELECT_WORLD_BUTTON_ON, VIRTUAL_SCREEN, 0f,1){{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        selectWorldButton.setOnClick((button) -> {
            playSound(InitSounds.YUZU_TITLE_BUTTON_SELECT_WORLD);
            this.minecraft.setScreen(new SelectWorldScreen(this));
        });


        // 多人游戏
        TitleScreenButton continueButton = new TitleScreenButton(66, y + dy * 2, 313, 56, TextureConst.TITLE_CONTINUE_BUTTON_NORMAL, TextureConst.TITLE_CONTINUE_BUTTON_ON, VIRTUAL_SCREEN, 0f,2){{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        continueButton.setOnClick((button) -> {
            Screen screen = this.minecraft.options.skipMultiplayerWarning ? new JoinMultiplayerScreen(this) : new SafetyScreen(this);
            this.minecraft.setScreen(screen);
        });


        // realms
        TitleScreenButton realmsButton = new TitleScreenButton(66, y + dy * 3, 164, 54, TextureConst.TITLE_REAMLS_BUTTON_NORMAL, TextureConst.TITLE_REAMLS_BUTTON_ON, VIRTUAL_SCREEN, 0f,3){{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        realmsButton.setOnClick((button) -> {
            playSound(InitSounds.YUZU_TITLE_BUTTON_REALMS);
            this.minecraft.setScreen(new RealmsMainScreen(this));
        });


        // 模组列表
        TitleScreenButton modListButton = new TitleScreenButton(58, y + dy * 4, 211, 54, TextureConst.TITLE_MOD_LIST_BUTTON_NORMAL, TextureConst.TITLE_MOD_LIST_BUTTON_ON, VIRTUAL_SCREEN, 0f,4){{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        modListButton.setOnClick((button) -> {
            playSound(InitSounds.YUZU_TITLE_BUTTON_MOD_LIST);
            this.minecraft.setScreen(new ModListScreen(this));
        });


        // 设置
        TitleScreenButton optionsButton = new TitleScreenButton(59, y + dy * 5, 253, 56, TextureConst.TITLE_OPTIONS_BUTTON_NORMAL, TextureConst.TITLE_OPTIONS_BUTTON_ON, VIRTUAL_SCREEN, 0f,5){{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        optionsButton.setOnClick((button) -> {
            playSound(InitSounds.YUZU_TITLE_BUTTON_OPTIONS);
            this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options));
        });


        // 退出游戏
        TitleScreenButton quitGameButton = new TitleScreenButton(60, y + dy * 6, 233, 54, TextureConst.TITLE_QUIT_GAME_BUTTON_NORMAL, TextureConst.TITLE_QUIT_GAME_BUTTON_ON, VIRTUAL_SCREEN, 0f,6){{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        quitGameButton.setOnClick((button) -> {
            playSound(InitSounds.YUZU_TITLE_BUTTON_QUIT_GAME);
            passExitSound=0;
            CompletableFuture.completedFuture(null).whenComplete((unused, e) -> {
                executor.execute(() -> {
                try {
                    // 等待音效播放完成
                    for (int i = 0; i < 150; i++) {
                        sleep(10);
                        if (passExitSound==2){
                            break;
                        }
                    }
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                this.minecraft.stop();});
            });
        });

        this.addChild(newGameButton);
        this.addWidget(newGameButton);
        this.addChild(selectWorldButton);
        this.addWidget(selectWorldButton);
        this.addChild(continueButton);
        this.addWidget(continueButton);
        this.addChild(realmsButton);
        this.addWidget(realmsButton);
        this.addChild(modListButton);
        this.addWidget(modListButton);
        this.addChild(optionsButton);
        this.addWidget(optionsButton);
        this.addChild(quitGameButton);
        this.addWidget(quitGameButton);
    }

    public <T extends Renderable & Tickable> void addChild(T child) {
        this.tickables.add(child);
        this.addRenderableOnly(child);
    }

    /**
     * 重写方法，防止清除组件
     */
    @Override
    protected void rebuildWidgets() {
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (passExitSound==0){
            passExitSound=1;
        } else if (passExitSound==1) {
            passExitSound=2;
        }
        return super.mouseClicked(mouseX,mouseY,button);
    }

    public void playSound(RegistryObject<SoundEvent> sound) {
        Minecraft mc = Minecraft.getInstance();
        mc.getSoundManager().play(SimpleSoundInstance.forUI(sound.get(), 1.0f, mc.options.getSoundSourceVolume(SoundSource.VOICE)));
    }
}
