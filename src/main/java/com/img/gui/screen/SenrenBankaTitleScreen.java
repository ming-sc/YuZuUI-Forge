package com.img.gui.screen;

import com.img.constant.TextureConst;
import com.img.gui.*;
import com.img.init.InitSounds;
import com.img.utils.RenderUtils;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.gui.ModListScreen;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.compress.utils.Lists;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;

/**
 * @author : IMG
 * @create : 2025/2/16
 */
public class SenrenBankaTitleScreen extends TitleScreen {
    private static final VirtualScreen VIRTUAL_SCREEN = new VirtualScreen(1920, 1080);
    private boolean fading;
    private long delay = 0L;
    private final List<Tickable> tickables = Lists.newArrayList();
    public boolean isShowed = false;

//    "north": {"uv": [114, 58], "uv_size": [4, 2]},
//    "east": {"uv": [15, 107], "uv_size": [6, 2]},
//    "south": {"uv": [61, 114], "uv_size": [4, 2]},
//    "west": {"uv": [107, 21], "uv_size": [6, 2]},
//    "up": {"uv": [88, 0], "uv_size": [4, 6]},
//    "down": {"uv": [88, 53], "uv_size": [4, -6]}
    public static final Cube.UV.UVInfo north = new Cube.UV.UVInfo(new Vector2f(114, 58), new Vector2f(4, 2));
    public static final Cube.UV.UVInfo east = new Cube.UV.UVInfo(new Vector2f(15, 107), new Vector2f(6, 2));
    public static final Cube.UV.UVInfo south = new Cube.UV.UVInfo(new Vector2f(61, 114), new Vector2f(4, 2));
    public static final Cube.UV.UVInfo west = new Cube.UV.UVInfo(new Vector2f(107, 21), new Vector2f(6, 2));
    public static final Cube.UV.UVInfo up = new Cube.UV.UVInfo(new Vector2f(88, 0), new Vector2f(4, 6));
    public static final Cube.UV.UVInfo down = new Cube.UV.UVInfo(new Vector2f(88, 53), new Vector2f(4, -6));
    public static final Cube.UV uv = new Cube.UV(north, east, south, west, up, down);
    public static final Cube cube = new Cube(new Vector3f(0, -50, 10), new Vector3f(3.625f, 2f, 6.075f), new Vector3f(1.8125f, 1f, 3.0375f), new Vector3f(50, 50, 0), uv);
    public static Bone RightArm;
    public static Bone RightForeArm;
    public static Map<String, Bone> boneMap;

    static {
        RightArm = Bone.builder("RightArm")
                .pivot(3.25f, 29.1f,0)
                .rotation(0, 0, 18)
                .build();
        RightForeArm = Bone.builder("RightForeArm")
                .pivot(3.25f, 23.1f, 0)
                .rotation(0, 0, 0)
                .build();
        RightArm.addChildren(RightForeArm);
        RightForeArm.setParent(RightArm);
//        "north": {"uv": [70, 89], "uv_size": [3, 7]},
//        "east": {"uv": [81, 42], "uv_size": [4, 7]},
//        "south": {"uv": [74, 89], "uv_size": [3, 7]},
//        "west": {"uv": [47, 81], "uv_size": [4, 7]},
//        "up": {"uv": [101, 98], "uv_size": [3, 4]},
//        "down": {"uv": [102, 4], "uv_size": [3, -4]}
        Cube.UV UV = Cube.UV.builder()
                .north(70, 89, 3, 7)
                .east(81, 42, 4, 7)
                .south(74, 89, 3, 7)
                .west(47, 81, 4, 7)
                .up(101, 98, 3, 4)
                .down(102, 4, 3, -4)
                .build();

        Cube rightArmCube = Cube.builder()
                .origin(1.9f, 23.15f, -1.8f)
                .size(3.1f, 7f, 3.6f)
                .uv(UV)
                .build();

        RightArm.addCubes(rightArmCube);

//        "north": {"uv": [3, 93], "uv_size": [-3, 6]},
//        "east": {"uv": [87, 0], "uv_size": [-4, 6]},
//        "south": {"uv": [96, 0], "uv_size": [-3, 6]},
//        "west": {"uv": [86, 20], "uv_size": [-4, 6]},
//        "up": {"uv": [104, 38], "uv_size": [-3, 4]},
//        "down": {"uv": [104, 51], "uv_size": [-3, -4]}
        UV = Cube.UV.builder()
                .north(3, 93, -3, 6)
                .east(87, 0, -4, 6)
                .south(96, 0, -3, 6)
                .west(86, 20, -4, 6)
                .up(104, 38, -3, 4)
                .down(104, 51, -3, -4)
                .build();

        Cube RightForeArmCube = Cube.builder()
                .origin(1.975f, 18.975f, -1.775f)
                .size(3.075f, 4.8f, 3.6f)
                .inflate(0.001f)
                .rotation(0, 0, 10)
                .uv(UV)
                .build();

        RightForeArm.addCubes(RightForeArmCube);

//        "north": {"uv": [75, 110], "uv_size": [3, 3]},
//        "east": {"uv": [110, 78], "uv_size": [3, 3]},
//        "south": {"uv": [110, 82], "uv_size": [3, 3]},
//        "west": {"uv": [92, 110], "uv_size": [3, 3]},
//        "up": {"uv": [100, 110], "uv_size": [3, 3]},
//        "down": {"uv": [110, 109], "uv_size": [3, -3]}
        UV = Cube.UV.builder()
                .north(75, 110, 3, 3)
                .east(110, 78, 3, 3)
                .south(110, 82, 3, 3)
                .west(92, 110, 3, 3)
                .up(100, 110, 3, 3)
                .down(110, 109, 3, -3)
                .build();

        RightForeArmCube = Cube.builder()
                .origin(2.025f, 14.775f, -1.45f)
                .size(3, 3, 3)
                .uv(UV)
                .build();

        RightForeArm.addCubes(RightForeArmCube);

//        "north": {"uv": [41, 7], "uv_size": [-3, 2]},
//        "east": {"uv": [38, 16], "uv_size": [-4, 2]},
//        "south": {"uv": [41, 16], "uv_size": [-3, 2]},
//        "west": {"uv": [38, 7], "uv_size": [-4, 2]},
//        "up": {"uv": [155, 3], "uv_size": [-3, 4]},
//        "down": {"uv": [8, 156], "uv_size": [-3, -4]}
        UV = Cube.UV.builder()
                .north(41, 7, -3, 2)
                .east(38, 16, -4, 2)
                .south(41, 16, -3, 2)
                .west(38, 7, -4, 2)
                .up(155, 3, -3, 4)
                .down(8, 156, -3, -4)
                .build();

        RightForeArmCube = Cube.builder()
                .origin(1.975f, 17.075f, -1.775f)
                .size(3.075f, 2.05f, 3.6f)
                .uv(UV)
                .build();

        RightForeArm.addCubes(RightForeArmCube);

//        "north": {"uv": [30, 8], "uv_size": [-3, 1]},
//        "east": {"uv": [8, 25], "uv_size": [-4, 1]},
//        "south": {"uv": [31, 17], "uv_size": [-3, 1]},
//        "west": {"uv": [4, 25], "uv_size": [-4, 1]},
//        "up": {"uv": [134, 58], "uv_size": [-3, 4]},
//        "down": {"uv": [155, 11], "uv_size": [-3, -4]}
        UV = Cube.UV.builder()
                .north(30, 8, -3, 1)
                .east(8, 25, -4, 1)
                .south(31, 17, -3, 1)
                .west(4, 25, -4, 1)
                .up(134, 58, -3, 4)
                .down(155, 11, -3, -4)
                .build();

        RightForeArmCube = Cube.builder()
                .origin(1.975f, 18.275f, -1.775f)
                .size(3.075f, 0.2f, 3.6f)
                .uv(UV)
                .inflate(0.001f)
                .build();

        RightForeArm.addCubes(RightForeArmCube);

//        "north": {"uv": [28, 17], "uv_size": [-3, 1]},
//        "east": {"uv": [26, 24], "uv_size": [-4, 1]},
//        "south": {"uv": [29, 24], "uv_size": [-3, 1]},
//        "west": {"uv": [15, 7], "uv_size": [-4, 1]},
//        "up": {"uv": [103, 51], "uv_size": [-3, 4]},
//        "down": {"uv": [79, 104], "uv_size": [-3, -4]}

        UV = Cube.UV.builder()
                .north(28, 17, -3, 1)
                .east(26, 24, -4, 1)
                .south(29, 24, -3, 1)
                .west(15, 7, -4, 1)
                .up(103, 51, -3, 4)
                .down(79, 104, -3, -4)
                .build();

        RightForeArmCube = Cube.builder()
                .origin(1.975f, 17.675f, -1.775f)
                .size(3.075f, 0.2f, 3.6f)
                .uv(UV)
                .inflate(0.001f)
                .build();

        RightForeArm.addCubes(RightForeArmCube);

        boneMap = BoneLoader.loadBone();
        boneMap.forEach((name, bone) -> {
            System.out.println("loaded root bone: " + name);
        });
    }

    public SenrenBankaTitleScreen() {
        this(false);
    }

    public SenrenBankaTitleScreen(boolean fading) {
        this.fading = fading;
        System.out.println("new SenrenBankaTitleScreen!");
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

        // 背景图片
        /*
        for (Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, delta);
        }
        */

        PoseStack poseStack = new PoseStack();
//        poseStack.translate(VIRTUAL_SCREEN.toPracticalX(960), VIRTUAL_SCREEN.toPracticalY(540), 0);
//        poseStack.scale(20f, 20f, 20f);
//        poseStack.mulPose(Axis.YP.rotationDegrees(45));

        Matrix4f projection = new Matrix4f();
        Window window = minecraft.getWindow();
        projection.setPerspective(1.4835298F, (float)minecraft.getWindow().getWidth() / (float)minecraft.getWindow().getHeight(), 0.05F, 1000.0F);
        RenderSystem.backupProjectionMatrix();
        RenderSystem.setProjectionMatrix(projection, VertexSorting.DISTANCE_TO_ORIGIN);

        PoseStack modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.pushPose();
        modelViewStack.setIdentity();
        modelViewStack.translate(0, -23, -30);
//        modelViewStack.mulPose(Axis.YP.rotationDegrees(180));
        modelViewStack.mulPose(new Quaternionf().rotationZYX(0, (float) Math.toRadians(180), (float) Math.toRadians(0)));
//        modelViewStack.mulPose(Axis.XP.rotationDegrees(20));
        RenderSystem.applyModelViewMatrix();



        RenderSystem.setShaderTexture(0, TextureConst.SKIN);
        RenderSystem.clear(GL11.GL_DEPTH_BUFFER_BIT, Minecraft.ON_OSX);
        RenderSystem.enableDepthTest();
//        RenderSystem.enableCull();
        Lighting.setupFor3DItems();
        RenderUtils.setTextureWidth(256);
        RenderUtils.setTextureHeight(256);
        VertexConsumer buffer = guiGraphics.bufferSource().getBuffer(RenderType.entityCutoutNoCull(TextureConst.SKIN));
//        RenderUtils.drawBone(poseStack, RightArm, buffer);
        boneMap.forEach((name, bone) -> {
            RenderUtils.drawBone(poseStack, bone, buffer);
        });
        RenderSystem.restoreProjectionMatrix();

        modelViewStack.popPose();
        RenderSystem.applyModelViewMatrix();

        RenderSystem.disableDepthTest();

        RenderSystem.disableBlend();
        guiGraphics.disableScissor();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

//    public void drawFace(PoseStack poseStack, float width, float height, float z) {
//        float widthHalf = width / 2;
//        float heightHalf = height / 2;
//
//         RenderUtils.blit(-widthHalf, -height, width, height, poseStack);
//    }

    @Override
    public void tick() {
        super.tick();
        this.tickables.forEach(Tickable::tick);
    }

    @Override
    protected void init() {
        this.minecraft.setConnectedToRealms(false);
        if (!isShowed) {
            initWidgets();
            isShowed = true;
        }
    }

    public void initWidgets() {
        this.clearWidgets();
        this.tickables.clear();

        double a = 0.06;
        delay = 1300L;
//        if (!this.fading) {
//            delay = 200L;
//        }

        Layer yoshino = new Layer(TextureConst.TITLE_YOSHINO, 504, 50, 973, 1058, 1f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN){{
            setDelay(delay);
            setDuration(590L);

            setYFunction((t, now) -> {
                return (25f - 50f) * (float) ((Math.pow(a, t) - 1) / (a - 1))  + 50f;
            });

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 0f;
            });
        }};

        Layer murasame = new Layer(TextureConst.TITLE_MURASAME, 221, 90, 1045, 994, 1f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN){{
            setDelay(delay + 110L);
            setDuration(710L);

            setXFunction((t, now) -> {
                return (173f - 221f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 221f;
            });

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 0f;
            });
        }};

        Layer mako = new Layer(TextureConst.TITLE_MAKO, 805, 387, 1118, 694, 1f, 0,0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN){{
            setDelay(delay + 280L);
            setDuration(680L);

            setXFunction((t, now) -> {
                return (898f - 805f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 805f;
            });

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 0f;
            });
        }};

        Layer lena = new Layer(TextureConst.TITLE_LENA, 1002, 149,876, 1053, 1f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN){{
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

        Layer logo = new Layer(TextureConst.TITLE_LOGO, 17, 57, 442, 188, 1.067f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN){{
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

        Layer head = new Layer(TextureConst.TITLE_HEAD, 0, 334, 12, 687, 1f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN){{
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

        Layer background = new Layer(TextureConst.BACKGROUND_TEXTURE, 0, 0, 1920, 1080, 1f, 0, 0, 256, 256, 256, 256, 1, VIRTUAL_SCREEN);

        this.addChild(background);
        this.addChild(lena);
        this.addChild(mako);
        this.addChild(murasame);
        this.addChild(yoshino);
        this.addChild(logo);
        this.addChild(head);

        // 添加按钮
        int y = 346;
        int dy = 100;

        // 新建世界
        TitleScreenButton newGameButton = new TitleScreenButton(60, y, 207, 54, TextureConst.TITLE_NEW_GAME_BUTTON_NORMAL, TextureConst.TITLE_NEW_GAME_BUTTON_ON, VIRTUAL_SCREEN, 0f){{
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
        TitleScreenButton selectWorldButton = new TitleScreenButton(60, y + dy, 206, 55, TextureConst.TITLE_SELECT_WORLD_BUTTON_NORMAL, TextureConst.TITLE_SELECT_WORLD_BUTTON_ON, VIRTUAL_SCREEN, 0f){{
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
        TitleScreenButton continueButton = new TitleScreenButton(66, y + dy * 2, 313, 56, TextureConst.TITLE_CONTINUE_BUTTON_NORMAL, TextureConst.TITLE_CONTINUE_BUTTON_ON, VIRTUAL_SCREEN, 0f){{
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
        TitleScreenButton realmsButton = new TitleScreenButton(66, y + dy * 3, 164, 54, TextureConst.TITLE_REAMLS_BUTTON_NORMAL, TextureConst.TITLE_REAMLS_BUTTON_ON, VIRTUAL_SCREEN, 0f){{
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
        TitleScreenButton modListButton = new TitleScreenButton(58, y + dy * 4, 211, 54, TextureConst.TITLE_MOD_LIST_BUTTON_NORMAL, TextureConst.TITLE_MOD_LIST_BUTTON_ON, VIRTUAL_SCREEN, 0f){{
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
        TitleScreenButton optionsButton = new TitleScreenButton(59, y + dy * 5, 253, 56, TextureConst.TITLE_OPTIONS_BUTTON_NORMAL, TextureConst.TITLE_OPTIONS_BUTTON_ON, VIRTUAL_SCREEN, 0f){{
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
        TitleScreenButton quitGameButton = new TitleScreenButton(60, y + dy * 6, 233, 54, TextureConst.TITLE_QUIT_GAME_BUTTON_NORMAL, TextureConst.TITLE_QUIT_GAME_BUTTON_ON, VIRTUAL_SCREEN, 0f){{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        quitGameButton.setOnClick((button) -> {
            playSound(InitSounds.YUZU_TITLE_BUTTON_QUIT_GAME);
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

    @Override
    protected void rebuildWidgets() {
    }

    public void playSound(RegistryObject<SoundEvent> sound) {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(sound.get(), 1.0f, 1.0f));
    }
}
