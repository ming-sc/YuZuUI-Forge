package com.img.gui;

import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * @author : IMG
 * @create : 2025/2/22
 */
public class Cube {
    private Vector3f origin;
    private Vector3f size;
    private Vector3f pivot;
    private Vector3f rotation;
    private UV uv;
    private float inflate;

    public static class UV {
        private UVInfo north;
        private UVInfo east;
        private UVInfo south;
        private UVInfo west;
        private UVInfo up;
        private UVInfo down;

        public static class UVInfo {
            private Vector2f uv;
            private Vector2f uvSize;

            public UVInfo() {
                this(new Vector2f(), new Vector2f());
            }

            public UVInfo(Vector2f uv, Vector2f uvSize) {
                this.uv = uv;
                this.uvSize = uvSize;
            }

            public Vector2f getUv() {
                return uv;
            }

            public void setUv(Vector2f uv) {
                this.uv = uv;
            }

            public void setUv(float u, float v) {
                this.uv.set(u, v);
            }

            public Vector2f getUvSize() {
                return uvSize;
            }

            public void setUvSize(Vector2f uvSize) {
                this.uvSize = uvSize;
            }

            public void setUvSize(float u, float v) {
                this.uvSize.set(u, v);
            }
        }

        public UV() {
            this(new UVInfo(), new UVInfo(), new UVInfo(), new UVInfo(), new UVInfo(), new UVInfo());
        }

        public UV(UVInfo north, UVInfo east, UVInfo south, UVInfo west, UVInfo up, UVInfo down) {
            this.north = north;
            this.east = east;
            this.south = south;
            this.west = west;
            this.up = up;
            this.down = down;
        }

        public static Cube.UV.Builder builder() {
            return new Cube.UV.Builder();
        }

        public UVInfo getNorth() {
            return north;
        }

        public void setNorth(UVInfo north) {
            this.north = north;
        }

        public UVInfo getEast() {
            return east;
        }

        public void setEast(UVInfo east) {
            this.east = east;
        }

        public UVInfo getSouth() {
            return south;
        }

        public void setSouth(UVInfo south) {
            this.south = south;
        }

        public UVInfo getWest() {
            return west;
        }

        public void setWest(UVInfo west) {
            this.west = west;
        }

        public UVInfo getUp() {
            return up;
        }

        public void setUp(UVInfo up) {
            this.up = up;
        }

        public UVInfo getDown() {
            return down;
        }

        public void setDown(UVInfo down) {
            this.down = down;
        }

        public static class Builder {
            private UVInfo north;
            private UVInfo east;
            private UVInfo south;
            private UVInfo west;
            private UVInfo up;
            private UVInfo down;

            public Builder() {
                this.north = new UVInfo();
                this.east = new UVInfo();
                this.south = new UVInfo();
                this.west = new UVInfo();
                this.up = new UVInfo();
                this.down = new UVInfo();
            }

            public Builder north(float u, float v, float uSize, float vSize) {
                this.north.setUv(u, v);
                this.north.setUvSize(uSize, vSize);
                return this;
            }

            public Builder north(UVInfo north) {
                this.north = north;
                return this;
            }

            public Builder east(float u, float v, float uSize, float vSize) {
                this.east.setUv(u, v);
                this.east.setUvSize(uSize, vSize);
                return this;
            }

            public Builder east(UVInfo east) {
                this.east = east;
                return this;
            }

            public Builder south(float u, float v, float uSize, float vSize) {
                this.south.setUv(u, v);
                this.south.setUvSize(uSize, vSize);
                return this;
            }

            public Builder south(UVInfo south) {
                this.south = south;
                return this;
            }

            public Builder west(float u, float v, float uSize, float vSize) {
                this.west.setUv(u, v);
                this.west.setUvSize(uSize, vSize);
                return this;
            }

            public Builder west(UVInfo west) {
                this.west = west;
                return this;
            }

            public Builder up(float u, float v, float uSize, float vSize) {
                this.up.setUv(u, v);
                this.up.setUvSize(uSize, vSize);
                return this;
            }

            public Builder up(UVInfo up) {
                this.up = up;
                return this;
            }

            public Builder down(float u, float v, float uSize, float vSize) {
                this.down.setUv(u, v);
                this.down.setUvSize(uSize, vSize);
                return this;
            }

            public Builder down(UVInfo down) {
                this.down = down;
                return this;
            }

            public UV build() {
                return new UV(north, east, south, west, up, down);
            }
        }
    }

    public Cube() {
        this(new Vector3f(), new Vector3f(), new Vector3f().zero(), new Vector3f().zero(), new UV());
    }

    public Cube(Vector3f origin, Vector3f size, Vector3f pivot, Vector3f rotation, UV uv) {
        this(origin, size, pivot, rotation, uv, 0);
    }

    public Cube(Vector3f origin, Vector3f size, Vector3f pivot, Vector3f rotation, UV uv, float inflate) {
        this.origin = origin;
        this.size = size;
        this.pivot = pivot;
        this.rotation = rotation;
        this.uv = uv;
        this.inflate = inflate;
    }

    public static Cube.Builder builder() {
        return new Cube.Builder();
    }

    public Vector3f getOrigin() {
        return origin;
    }

    public void setOrigin(Vector3f origin) {
        this.origin = origin;
    }

    public Vector3f getSize() {
        return size;
    }

    public void setSize(Vector3f size) {
        this.size = size;
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

    public UV getUv() {
        return uv;
    }

    public void setUv(UV uv) {
        this.uv = uv;
    }

    public float getInflate() {
        return inflate;
    }

    public void setInflate(float inflate) {
        this.inflate = inflate;
    }

    public static class Builder {
        private Vector3f origin;
        private Vector3f size;
        private Vector3f pivot;
        private Vector3f rotation;
        private UV uv;
        private float inflate;

        public Builder() {
            this.origin = new Vector3f();
            this.size = new Vector3f();
            this.pivot = new Vector3f().zero();
            this.rotation = new Vector3f().zero();
            this.uv = new UV();
            this.inflate = 0;
        }

        public Builder origin(float x, float y, float z) {
            this.origin.set(x, y, z);
            return this;
        }

        public Builder size(float x, float y, float z) {
            this.size.set(x, y, z);
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

        public Builder uv(UV uv) {
            this.uv = uv;
            return this;
        }

        public Builder inflate(float inflate) {
            this.inflate = inflate;
            return this;
        }

        public Cube build() {
            return new Cube(origin, size, pivot, rotation, uv, inflate);
        }
    }
}
