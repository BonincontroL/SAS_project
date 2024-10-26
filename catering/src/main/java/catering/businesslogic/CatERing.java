package catering.businesslogic;

import catering.businesslogic.event.EventManager;
import catering.businesslogic.kitchentask.KitchenTaskManager;
import catering.businesslogic.menu.MenuManager;
import catering.businesslogic.recipe.RecipeManager;
import catering.businesslogic.turn.TurnManager;
import catering.businesslogic.user.UserManager;
import catering.persistence.KitchenTaskPersistence;
import catering.persistence.MenuPersistence;


public class CatERing {
    private static CatERing singleInstance;

    public static CatERing getInstance() {
        if (singleInstance == null) {
            singleInstance = new CatERing();
        }
        return singleInstance;
    }

    private MenuManager menuMgr;
    private RecipeManager recipeMgr;
    private UserManager userMgr;
    private EventManager eventMgr;
    private KitchenTaskManager kitchenTaskManager;
    private TurnManager turnManager;

    private MenuPersistence menuPersistence;
    private KitchenTaskPersistence kitchenTaskPersistence;

    private CatERing() {
        menuMgr = new MenuManager();
        recipeMgr = new RecipeManager();
        userMgr = new UserManager();
        eventMgr = new EventManager();
        menuPersistence = new MenuPersistence();
        kitchenTaskPersistence = new KitchenTaskPersistence();
        kitchenTaskManager = new KitchenTaskManager();
        turnManager = new TurnManager();
        menuMgr.addEventReceiver(menuPersistence);
        kitchenTaskManager.addEventReceiver(kitchenTaskPersistence);
    }

    public MenuManager getMenuManager() {
        return menuMgr;
    }

    public RecipeManager getRecipeManager() {
        return recipeMgr;
    }

    public UserManager getUserManager() {
        return userMgr;
    }

    public EventManager getEventManager() { return eventMgr; }

    public KitchenTaskManager getKitchenTaskManager() { return kitchenTaskManager; }

    public TurnManager getTurnManager() { return turnManager; }

}
