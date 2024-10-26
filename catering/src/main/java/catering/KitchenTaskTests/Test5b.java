package catering.KitchenTaskTests;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchentask.KitchenTask;
import catering.businesslogic.kitchentask.ToDoList;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.turn.TurnTable;
import catering.businesslogic.user.User;

import java.util.ArrayList;

public class Test5b {
    public static void main(String[] args) {

        try{
            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println("Login effettuato con successo per l'utente 'Lidia'");

            System.out.println("TEST APRI FOGLIO");
            ServiceInfo service = ServiceInfo.loadServiceInfoForEvent(1, 2);
            if(service == null) {
                System.out.println("Service not found");
                return;
            }

            ToDoList tdl = CatERing.getInstance().getKitchenTaskManager().openToDoList(service);
            System.out.println("ToDoList aperto per il servizio: " + service);

            ArrayList<Recipe> recipes = CatERing.getInstance().getRecipeManager().getRecipes();

            TurnTable turnTable = CatERing.getInstance().getKitchenTaskManager().getTurnTable();
            //System.out.println("TurnTable: "+turnTable);

            CatERing.getInstance().getUserManager().fakeLogin("Marinella");
            ArrayList<User> cooks = new ArrayList<>();
            cooks.add(CatERing.getInstance().getUserManager().getCurrentUser());
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");

//            KitchenTask toUpdate = CatERing.getInstance().getKitchenTaskManager().addTask(recipes.get(2),
//                    cooks,
//                    turnTable.getRandomTurn());

            KitchenTask toUpdate = CatERing.getInstance().getKitchenTaskManager().addTask(recipes.get(2),
                    null,
                    null);

            System.out.println("Prima: ");
            System.out.println(tdl);

            CatERing.getInstance().getKitchenTaskManager().deleteTask(toUpdate);

            System.out.println("Task ["+toUpdate+"] eliminato dalla lista.\nStato della lista dopo la cancellazione:");
            System.out.println(tdl);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Test fallito: " + e.getMessage());
            System.exit(1);
        }


    }
}
