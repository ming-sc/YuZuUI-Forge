package com.img.gui;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : IMG
 * @create : 2025/2/22
 */
public class Bone {
    private String name;
    private String parentName;
    private Bone parent;
    private Vector3f pivot;
    private Vector3f rotation;
    private boolean mirror;
    private List<Cube> cubes;
    private List<Bone> children;

    public Bone() {
    }

    public Bone(String name, Bone parent, Vector3f pivot, Vector3f rotation, boolean mirror, List<Cube> cubes, List<Bone> children) {
        this.name = name;
        if (parent != null) {
            this.parent = parent;
            this.parentName = parent.getName();
        }
        this.pivot = pivot;
        this.rotation = rotation;
        this.mirror = mirror;
        this.cubes = cubes;
        this.children = children;
    }

    public static Bone.Builder builder(String name) {
        return new Bone.Builder(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Bone getParent() {
        return parent;
    }

    public void setParent(Bone parent) {
        this.parent = parent;
        if (parent != null) {
            this.parentName = parent.getName();
        } else {
            this.parentName = null;
        }
    }

    public Vector3f getPivot() {
        return pivot;
    }

    public void setPivot(Vector3f pivot) {
        this.pivot = pivot;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public boolean isMirror() {
        return mirror;
    }

    public void setMirror(boolean mirror) {
        this.mirror = mirror;
    }

    public List<Cube> getCubes() {
        return cubes;
    }

    public void addCubes(Cube cube) {
        this.cubes.add(cube);
    }

    public List<Bone> getChildren() {
        return children;
    }

    public void addChildren(Bone children) {
        this.children.add(children);
    }

    public static class Builder {
        private String name;
        private Bone parent;
        private final Vector3f pivot = new Vector3f();
        private final Vector3f rotation = new Vector3f();
        private boolean mirror = false;
        private final List<Cube> cubes = new ArrayList<>();
        private final List<Bone> children = new ArrayList<>();

        public Builder(String name) {
            this.name = name;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder parent(Bone parent) {
            this.parent = parent;
            return this;
        }

        public Builder pivot(float x, float y, float z) {
            this.pivot.set(x, y, z);
            return this;
        }

        public Builder rotation(float x, float y, float z) {
            this.rotation.set(x, y, z);
            return this;
        }

        public Builder mirror(boolean mirror) {
            this.mirror = mirror;
            return this;
        }

        public Builder addCubes(Cube cube) {
            this.cubes.add(cube);
            return this;
        }

        public Builder addChildren(Bone children) {
            this.children.add(children);
            return this;
        }

        public Bone build() {
            return new Bone(name, parent, pivot, rotation, mirror, cubes, children);
        }
    }

    @Override
    public String toString() {
        return "Bone{" +
                "name='" + name + '\'' +
                ", parentName='" + parentName + '\'' +
                ", parent=" + parent +
                ", pivot=" + pivot +
                ", rotation=" + rotation +
                ", mirror=" + mirror +
                ", cubes=" + cubes +
                ", children=" + children +
                '}';
    }
}
