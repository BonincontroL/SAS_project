package catering.KitchenTaskTests;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchentask.ToDoList;
import catering.businesslogic.recipe.Recipe;

import java.util.ArrayList;

public class Test2a {
    public static void main(String[] args) {
        try {
            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println("Login effettuato con successo per l'utente 'Lidia'");

            ServiceInfo service = ServiceInfo.loadServiceInfoForEvent(1, 2);
            if (service == null) {
                System.out.println("Errore: Servizio non trovato");
                return;
            }
            ToDoList tdl = CatERing.getInstance().getKitchenTaskManager().openToDoList(service);
            System.out.println("ToDoList aperto per il servizio: " + service);
            ArrayList<Recipe> recipes = CatERing.getInstance().getRecipeManager().getRecipes();
            //System.out.println("recipes:"+recipes+"\n");

           // System.out.println(" "+recipes.get(5));
            System.out.println("PRIMA DELLA CANCELLAZIONE");
            System.out.println(tdl);
            System.out.println("Cancellazione: " + recipes.get(5));
            CatERing.getInstance().getKitchenTaskManager().deleteProcedure(recipes.get(5));
            System.out.println("DOPO LA CANCELLAZIONE");
            System.out.println(tdl);


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Test fallito: " + e.getMessage());
            System.exit(1);
        }
    }
}
