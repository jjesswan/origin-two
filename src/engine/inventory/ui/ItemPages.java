package engine.inventory.ui;

import o2.BlackBoard;
import engine.inventory.InventoryObject;
import engine.inventory.ItemType;
import engine.screen.Screen;
import engine.support.Vec2d;
import engine.ui.UIDivisibleRect;
import engine.ui.UISprite;
import engine.utils.Types.FillType;
import engine.utils.Types.UIType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import o2.files.ImageReference;

public class ItemPages {
  private Map<ItemType, List<ItemPage>> inventory;
  private List<InventoryTab> tabs;
  private ItemType activeTab = ItemType.LOOT;
  private int currentPage = 0;
  private BlackBoard blackBoard;
  private Screen screen;
  private UIDivisibleRect itemContainer, tabContainer;
  private UIDivisibleRect pagination;
  private SideBlurb blurb;
  double HEIGHT = 300;


  public ItemPages(BlackBoard blackBoard, Screen screen){
    this.screen = screen;
    this.blackBoard = blackBoard;

    this.tabContainer = new UIDivisibleRect(new Vec2d(0), new Vec2d(50, HEIGHT), Color.BLUE, FillType.FILL, UIType.DISPLAY, 4, 1);
    this.itemContainer = new UIDivisibleRect(new Vec2d(0), new Vec2d(300, HEIGHT), Color.RED, FillType.FILL, UIType.DISPLAY, 4, 4);
    this.itemContainer.setAlpha(0);

    this.screen.horizontalAlignUIElement(tabContainer, 100);

    this.tabContainer.appendToRight(itemContainer, 5);
    this.tabContainer.horizCenterElement(itemContainer);
    this.tabContainer.addChildElement(itemContainer);
    this.screen.addUIElement(tabContainer);
    this.blurb = new SideBlurb(this.itemContainer, blackBoard);


    this.initEmptyInventory();
    this.initPagination();
    this.initTabs();
    this.drawCurrentPage();
  }

  public void initTabs(){
    this.tabs = new ArrayList<>();
    int j = 0;
    Vec2d imgIndexIdle, imgIndexActive;
    for (ItemType itemType : ItemType.values()){
      if (j == 0 ){
        imgIndexIdle = new Vec2d(0);
        imgIndexActive = new Vec2d(1,0);
      } else {
        imgIndexIdle = new Vec2d(0, 1);
        imgIndexActive = new Vec2d(1);
      }

        InventoryTab tab = new InventoryTab(itemType, imgIndexIdle, imgIndexActive, ImageReference.spriteReferences.get("ui_tabs")){
          @Override
          public void mouseClickCommand(MouseEvent e) {
            drawCurrentTab(this);
          }
        };

      this.tabs.add(tab);
      j = 1;
    }



    // append to upper tab

    for (int i=0; i<this.tabs.size(); i++){
      if (i == 0){
        this.tabContainer.addAtIndex(0,0, tabs.get(0));
        this.tabContainer.addChildElement(tabs.get(0));
        this.tabContainer.setAlpha(0);

      } else {
        tabs.get(i - 1).appendToBottom(tabs.get(i), -7);
        tabs.get(i - 1).vertCenterElement(tabs.get(i));
        tabs.get(i - 1).addChildElement(tabs.get(i));
      }

      tabs.get(i).updateTextLocation();
    }

    this.drawCurrentTab(tabs.get(3));
  }

  public void initPagination(){
    this.pagination = new UIDivisibleRect(new Vec2d(100, 30), new Vec2d(100, 40), Color.DARKGRAY, FillType.FILL, UIType.DISPLAY, 1, 2);
    this.pagination.setAlpha(0);
    this.itemContainer.appendToBottom(pagination, 10);
    this.itemContainer.vertCenterElement(pagination);

    UISprite next = new UISprite(new Vec2d(1,4), ImageReference.spriteReferences.get("ui_icons"), new Vec2d(0), new Vec2d(20), UIType.BUTTON){
      @Override
      public void mouseClickCommand(MouseEvent e){
        nextPage();
      }

    };
    UISprite previous = new UISprite(new Vec2d(0,4), ImageReference.spriteReferences.get("ui_icons"), new Vec2d(0), new Vec2d(20), UIType.BUTTON){
      @Override
      public void mouseClickCommand(MouseEvent e){
        previousPage();
      }

    };

    pagination.addAtIndex(0, 0, previous);
    pagination.addAtIndex(0, 1, next);
    pagination.addChildElements(Arrays.asList(next, previous));
    this.itemContainer.addChildElement(pagination);

  }

  // initialize an empty default 16 set
  public void initEmptyInventory(){
    this.inventory = new HashMap<>();
    int pageLength = 50;
    for (ItemType itemType : ItemType.values()){
      ItemPage itemPage = new ItemPage(this.itemContainer, 4, 4, Color.BLACK, this.blurb);
      pageLength += 50;
      this.inventory.put(itemType, new ArrayList<>(Arrays.asList(itemPage)));
    }
  }

  public ItemPage addNewPage(){
    ItemPage itemPage = new ItemPage(this.itemContainer, 4, 4, Color.BLACK, this.blurb);
    return itemPage;
  }

  public void nextPage(){
    this.currentPage++;
    if (this.currentPage >= this.inventory.get(this.activeTab).size())
      this.currentPage = this.inventory.get(this.activeTab).size() - 1;

    this.drawCurrentPage();
  }

  public void previousPage(){
    this.currentPage--;
    if (this.currentPage < 0) this.currentPage = 0;

    this.drawCurrentPage();
  }

  public void drawCurrentPage(){
    for (int i=0; i<this.inventory.get(this.activeTab).size(); i++){
      this.inventory.get(this.activeTab).get(i).hidePage();
    }

    this.inventory.get(this.activeTab).get(this.currentPage).showPage();
  }

  public void drawCurrentTab(InventoryTab tab){
    this.activeTab = tab.itemType;
    // loads the associated page
    for (ItemType itemType : this.inventory.keySet()){
      for (int i=0; i<this.inventory.get(itemType).size(); i++){
        this.inventory.get(itemType).get(i).hidePage();
      }
    }

    this.inventory.get(this.activeTab).get(this.currentPage).showPage();

    // highlight the current tab
    for (InventoryTab tab1 : this.tabs){
      tab1.deactivateTab();
    }
    tab.activateTab();
  }

  /// ITEM QUERYER

  public void addItem(InventoryObject inventoryObject){
    boolean newPage = false;
    for (int i=0; i<this.inventory.get(inventoryObject.itemType).size(); i++){

      // if item could not be inserted into existing pages, then create new page
      if (!this.inventory.get(inventoryObject.itemType).get(i).addItem(inventoryObject)){
        // check if its the last page
        if (i == this.inventory.get(inventoryObject.itemType).size() - 1){
          newPage = true;
        }
        continue;
      };
    }

    if (newPage){
      this.inventory.get(inventoryObject.itemType).add(this.addNewPage());
    }
  }

}
