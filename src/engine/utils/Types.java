package engine.utils;


import engine.support.Vec2d;

public class Types {
    public enum FillType {FILL, STROKE}
    public enum ShapeType {ARC, OVAL, POLYGON, RECT, ROUND_RECT, TEXT}
    public enum UIType{BUTTON, DISPLAY}
    public enum DisplayMode{SHOW, HIDE}
    public enum Alignment{CENTER, VERT_CENTER, HORIZ_CENTER,
        NONE, LEFT, RIGHT, TOP, BOTTOM,
        TOPRIGHT, TOPLEFT, BOTTOMRIGHT, BOTTOMLEFT }
    public enum CollisionShapeType{
        AAB,
        CIRCLE,
        POLY,
        POINT
    }

    public enum CollisionBehaviorType{
        DYNAMIC,
        STATIC,
        PASS_THRU

    }

    public static class AABBBoundingBox{
        public Vec2d min;
        public Vec2d max;
    }
}
