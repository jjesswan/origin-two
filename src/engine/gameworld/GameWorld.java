package engine.gameworld;

import engine.components.ComponentTag;
import engine.components.SaveComponent;
import engine.inventory.InventorySystem;
import engine.inventory.ui.InventoryUI;
import engine.raycast.RayCollisionInfo;
import engine.raycast.Raycaster;
import engine.support.Vec2d;
import engine.systems.AISystem;
import engine.systems.CharacterControllerSystem;
import engine.systems.CollisionSystem;
import engine.systems.DragSystem;
import engine.systems.DrawSystem;
import engine.systems.ISystem;
import engine.systems.PhysicsSystem;
import engine.systems.SystemTag;
import engine.systems.TickSystem;
import engine.utils.SizeConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GameWorld {
  private Map<SystemTag, ISystem> systems;
  private Map<String, GameObject> gameObjects, updateGOQueue;
  private Vec2d worldSize;
  private Vec2d windowSize = SizeConstants.INIT_WINDOW_SIZE;
  private List<String> toBeDeleted;
  private Vec2d gameWorldTopLeft;
  private Raycaster raycaster;



  public GameWorld(Map<String, GameObject> gameObjects){
    this.systems = new HashMap<>();
    this.gameObjects = gameObjects;
    this.updateGOQueue = new HashMap<>();
    this.toBeDeleted = new ArrayList<>();
    this.raycaster = new Raycaster(this.gameObjects);
    this.setGameWorldSize();
    this.initSystems();

  }

  public void updateGameObjects(String name, GameObject go){
    this.gameObjects.put(name, go);
    this.updateSystemGameObjects(name, go);
    this.raycaster.updateGameObjects(this.gameObjects);
    this.setGameWorldSize();

    // if player is added, then add the char controller system
    if (name.equals("player") && this.systems.get(SystemTag.CHAR_CONTROLLER_SYSTEM) == null) this.addSystem(new CharacterControllerSystem(this.gameObjects.get("player")));
  }

  public void addGameObject(String name, GameObject go){
    this.updateGOQueue.put(name, go);
  }

  public void deleteGameObject(String name){
    this.toBeDeleted.add(name);
  }

  public GameObject getGameObject(String name){
    return this.gameObjects.get(name);
  }

  public Map<String, GameObject> getAllGameObjects(){
    return this.gameObjects;
  }


  public void setGameWorldSize(){
    double biggestDim_x = 0, biggestDim_y = 0;
    double smallestPos_x = 1000, smallestPos_y = 1000;
    for (GameObject go : this.gameObjects.values()){
      if (go.getTransform().getPosition().x < smallestPos_x) smallestPos_x = go.getTransform().getPosition().x;
      if (go.getTransform().getPosition().y < smallestPos_y) smallestPos_y = go.getTransform().getPosition().y;

      Vec2d span = go.getTransform().getPosition().plus(go.getTransform().getSize());
      if (span.x > biggestDim_x) biggestDim_x = span.x;
      if (span.y > biggestDim_y) biggestDim_y = span.y;
    }

    this.gameWorldTopLeft = new Vec2d(smallestPos_x, smallestPos_y);
    this.worldSize = new Vec2d(biggestDim_x - smallestPos_x, biggestDim_y-smallestPos_y);
  }

  public Vec2d getWorldSize(){
    return this.worldSize;
  }

  public Vec2d getWorldCenter(){
    return this.worldSize.sdiv(2);
  }

  public Vec2d getWorldTopLeft(){
    return this.gameWorldTopLeft;
  }

  public void initSystems(){
    this.addSystem(new DrawSystem(this.gameObjects));
    this.addSystem(new DragSystem(this.gameObjects));
    this.addSystem(new TickSystem(this.gameObjects));
    this.addSystem(new AISystem(this.gameObjects));
    CollisionSystem collisionSystem = new CollisionSystem(this.gameObjects);
    this.addSystem(collisionSystem);
    this.addSystem(new PhysicsSystem(this.gameObjects, collisionSystem));




    // add character controller system if player character exists
    if (this.gameObjects.get("player") != null) this.addSystem(new CharacterControllerSystem(this.gameObjects.get("player")));

  }

  public void addInventoryFeature(InventoryUI inventory){
    this.addSystem(new InventorySystem(this.gameObjects, this, inventory));
  }

  public void addSystem(ISystem system){
    this.systems.put(system.getTag(), system);
  }
  public ISystem getSystem(SystemTag tag){
    return this.systems.get(tag);
  }


  public void updateSystemGameObjects(String label, GameObject go){
    for (ISystem system : this.systems.values()){
      system.updateGameObjects(label, go);
    }
  }

  public void tick(long nanosSinceLastTick) {
    // remove or add any game objects first
    for (String name : this.toBeDeleted){
      this.gameObjects.remove(name);
      for (ISystem system : this.systems.values()){
        system.removeGameObjects(name);
      }
      this.raycaster.updateGameObjects(this.gameObjects);
    }

    for (String go : this.updateGOQueue.keySet()){
      this.updateGameObjects(go, this.updateGOQueue.get(go));
      this.raycaster.updateGameObjects(this.gameObjects);
    }

    this.updateGOQueue.clear();
    this.toBeDeleted = new ArrayList<>();

    for (ISystem system : this.systems.values()){
      system.tick(nanosSinceLastTick);
    }
  }

  public void draw(GraphicsContext g) {
    this.systems.get(SystemTag.DRAW_SYSTEM).draw(g);
  }

  public void onMousePressed(Vec2d pos) {
    for (ISystem system : this.systems.values()){
      system.onMousePressed(pos);
    }
  }

  public void onMouseReleased(Vec2d pos) {
    for (ISystem system : this.systems.values()){
      system.onMouseReleased(pos);
    }
  }

  public void onMouseDragged(Vec2d pos) {
    for (ISystem system : this.systems.values()){
      system.onMouseDragged(pos);
    }
  }

  public RayCollisionInfo shootRay(String sourceID, Vec2d gamePoint){
    return this.raycaster.shootRay(sourceID, gamePoint);
  }

  public void onMouseClicked(Vec2d pos) {
    for (ISystem system : this.systems.values()){
      system.onMouseClicked(pos);
    }
  }

  public void onMouseMoved(Vec2d pos) {
    for (ISystem system : this.systems.values()){
      system.onMouseMoved(pos);
    }
  }


  public void onResize(Vec2d newSize){
//    double percent = newSize.x/this.windowSize.x;
//    for (GameObject go : this.gameObjects.values()){
//      go.getTransform().resize(newSize);
//    }
  }

  public Set<Pair<GameObject, GameObject>> getCollidingGameObjects(){
    return this.getSystem(SystemTag.COLLISION_SYSTEM).getCollidingPairs();
  }

  public void updateCollidingGameObjects(Set<Pair<GameObject, GameObject>> pairs){
    this.getSystem(SystemTag.COLLISION_SYSTEM).updateCollidingPairs(pairs);
  }

  public void onLateTick() {
    for (ISystem s : this.systems.values()){
      s.lateTick();
    }
  }

  public Element saveGameWorld(Document doc){
    Element world = doc.createElement("GameWorld");

    for (GameObject go : this.gameObjects.values()){
      if (go.hasComponent(ComponentTag.SAVE)){
        world.appendChild(((SaveComponent)go.getComponent(ComponentTag.SAVE)).save(doc));
      }
    }

    return world;
  }

  public void onKeyTyped(KeyEvent e) {
  }

  public void onKeyPressed(KeyEvent e) {
    for (ISystem s : this.systems.values()){
      s.onKeyPress(e);
    }
  }

  public void onKeyReleased(KeyEvent e) {
  }

  public void onMouseWheelMoved(ScrollEvent e) {
  }

  public void onFocusChanged(boolean newVal) {
  }

  public void onShutdown() {

  }

  public void onStartup() {

  }


}
