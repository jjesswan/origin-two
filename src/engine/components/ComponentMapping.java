package engine.components;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ComponentMapping {
  public static Map<ComponentTag, Class<? extends IComponent>> componentMap = createMap();

  private static Map<ComponentTag,  Class<? extends IComponent>> createMap() {
    Map<ComponentTag,  Class<? extends IComponent>> myMap = new HashMap<>();
    myMap.put(ComponentTag.HEALTH, HealthComponent.class);
    myMap.put(ComponentTag.PHYSICS, PhysicsComponent.class);
    myMap.put(ComponentTag.TICK, TickComponent.class);
    //myMap.put(ComponentTag.PATHFINDING, PathfindingComponent.class);
    myMap.put(ComponentTag.MINI_MAP, MiniMapComponent.class);
    myMap.put(ComponentTag.ANIMATION_SEQUENCE, AnimationSequenceComponent.class);
    myMap.put(ComponentTag.ANIMATION_SINGLE, AnimationComponent.class);
    myMap.put(ComponentTag.CENTER_FOCUS, CenterComponent.class);
    myMap.put(ComponentTag.SPRITE, SpriteComponent.class);
    myMap.put(ComponentTag.COLLISION, CollisionComponent.class);
    myMap.put(ComponentTag.DRAG, DragComponent.class);
    myMap.put(ComponentTag.AI, AIComponent.class);
    myMap.put(ComponentTag.TRANSFORM, TransformComponent.class);
    myMap.put(ComponentTag.DRAW, DrawComponent.class);
    return myMap;
  }





}
