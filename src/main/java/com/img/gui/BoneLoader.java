package com.img.gui;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.gson.Gson;
import com.img.YuZuUI;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

/**
 * @author : IMG
 * @create : 2025/2/23
 */
public class BoneLoader {

    public static Map<String, Bone> loadBone(){
        ResourceLocation location = new ResourceLocation(YuZuUI.MODID, "model/jk1.json");
        Optional<Resource> resource = Minecraft.getInstance().getResourceManager().getResource(location);
        InputStream stream = null;
        if (resource.isPresent()) {
            try {
                stream = resource.get().open();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (stream != null) {
            JSONObject jsonObject = JSON.parseObject(new InputStreamReader(stream, StandardCharsets.UTF_8));
//            System.out.println(jsonObject.containsKey("minecraft:geometry"));
            JSONObject model = jsonObject.getJSONArray("minecraft:geometry").getJSONObject(0);
            JSONArray bones = model.getJSONArray("bones");
            Map<String, Bone> rootMap = new HashMap<>();

            // 处理骨骼
            for (Object bone : bones){
                if (bone instanceof JSONObject) {
                    JSONObject boneObject = (JSONObject) bone;
                    Bone handledBone = handleBone(boneObject, rootMap);
                    rootMap.put(handledBone.getName(), handledBone);
                }
            }

            // 处理父子级关系
            for (Bone bone : rootMap.values()) {
                if (bone.getParentName() != null) {
                    Bone parent = rootMap.get(bone.getParentName());
                    if (parent != null) {
                        parent.addChildren(bone);
                        bone.setParent(parent);
                    }
                }
            }

            // 清理非根骨骼
            Iterator<Map.Entry<String, Bone>> iterator = rootMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Bone> bone = iterator.next();
                if (bone.getValue().getParent() != null) {
                    iterator.remove();
                }
            }
            return rootMap;
        } else {
            YuZuUI.LOGGER.error("Can't load bone file!");
        }
        return null;
    }

    public static Bone handleBone(JSONObject jsonBone, Map<String, Bone> rootMap) {
        String name = jsonBone.getString("name");
        Bone.Builder boneBuilder = Bone.builder(name);
        if (jsonBone.containsKey("pivot")) {
            JSONArray pivot = jsonBone.getJSONArray("pivot");
            boneBuilder.pivot(pivot.getFloatValue(0), pivot.getFloatValue(1), pivot.getFloatValue(2));
        }

        if (jsonBone.containsKey("rotation")) {
            JSONArray rotation = jsonBone.getJSONArray("rotation");
            boneBuilder.rotation(rotation.getFloatValue(0), rotation.getFloatValue(1), rotation.getFloatValue(2));
        }

        if (jsonBone.containsKey("parent")) {
            String parentName = jsonBone.getString("parent");
            boneBuilder.parent(rootMap.get(parentName));
        }

        if (jsonBone.containsKey("cubes")) {
            JSONArray cubes = jsonBone.getJSONArray("cubes");
            for (Object cube : cubes) {
                if (cube instanceof JSONObject) {
                    JSONObject cubeObject = (JSONObject) cube;
                    Cube handledCube = handleCube(cubeObject);
                    boneBuilder.addCubes(handledCube);
                }
            }
        }

        return boneBuilder.build();
    }

    public static Cube handleCube(JSONObject jsonCube) {
        Cube.Builder cubeBuilder = Cube.builder();

        if (jsonCube.containsKey("origin")) {
            JSONArray origin = jsonCube.getJSONArray("origin");
            cubeBuilder.origin(origin.getFloatValue(0), origin.getFloatValue(1), origin.getFloatValue(2));
        }

        if (jsonCube.containsKey("size")) {
            JSONArray size = jsonCube.getJSONArray("size");
            cubeBuilder.size(size.getFloatValue(0), size.getFloatValue(1), size.getFloatValue(2));
        }

        if (jsonCube.containsKey("pivot")) {
            JSONArray pivot = jsonCube.getJSONArray("pivot");
            cubeBuilder.pivot(pivot.getFloatValue(0), pivot.getFloatValue(1), pivot.getFloatValue(2));
        } else {
            cubeBuilder.pivot(0, 0, 0);
        }

        if (jsonCube.containsKey("rotation")) {
            JSONArray rotation = jsonCube.getJSONArray("rotation");
            cubeBuilder.rotation(rotation.getFloatValue(0), rotation.getFloatValue(1), rotation.getFloatValue(2));
        } else {
            cubeBuilder.rotation(0, 0, 0);
        }

        if (jsonCube.containsKey("inflate")) {
            cubeBuilder.inflate(jsonCube.getFloatValue("inflate"));
        }


        if (jsonCube.containsKey("uv")) {
            System.out.println("contain uv");
            JSONObject uv = jsonCube.getJSONObject("uv");
            Cube.UV.Builder uvBuilder = Cube.UV.builder();
            if (uv.containsKey("north")) {
                System.out.println("contain north");
                uvBuilder.north(handleUVInfo(uv.getJSONObject("north")));
            }
            if (uv.containsKey("east")) {
                uvBuilder.east(handleUVInfo(uv.getJSONObject("east")));
            }
            if (uv.containsKey("south")) {
                uvBuilder.south(handleUVInfo(uv.getJSONObject("south")));
            }
            if (uv.containsKey("west")) {
                uvBuilder.west(handleUVInfo(uv.getJSONObject("west")));
            }
            if (uv.containsKey("up")) {
                uvBuilder.up(handleUVInfo(uv.getJSONObject("up")));
            }
            if (uv.containsKey("down")) {
                uvBuilder.down(handleUVInfo(uv.getJSONObject("down")));
            }
            cubeBuilder.uv(uvBuilder.build());
        }

        Cube cube = cubeBuilder.build();
        return cube;
    }

    public static Cube.UV.UVInfo handleUVInfo(JSONObject jsonUVInfo) {
        Cube.UV.UVInfo uvInfo = new Cube.UV.UVInfo();
        if (jsonUVInfo.containsKey("uv")) {
            JSONArray uv = jsonUVInfo.getJSONArray("uv");
            uvInfo.setUv(uv.getFloatValue(0), uv.getFloatValue(1));
        }

        if (jsonUVInfo.containsKey("uv_size")) {
            JSONArray uvSize = jsonUVInfo.getJSONArray("uv_size");
            uvInfo.setUvSize(uvSize.getFloatValue(0), uvSize.getFloatValue(1));
        }

        return uvInfo;
    }
}
