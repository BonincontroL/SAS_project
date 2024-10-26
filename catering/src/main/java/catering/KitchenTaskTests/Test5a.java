package catering.KitchenTaskTests;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchentask.KitchenTask;
import catering.businesslogic.kitchentask.ToDoList;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.turn.TurnTable;
import catering.businesslogic.user.User;

import java.util.ArrayList;

public class Test5a {
    public  static void main(String[] args) {

        try {
            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println("Login effettuato con successo per l'utente 'Lidia'");

            System.out.println("TEST APRI FOGLIO");
            ServiceInfo service = ServiceInfo.loadServiceInfoForEvent(1, 2);
            if(service == null) {
                System.out.println("Service not found");
                System.exit(1);
            }
            ToDoList tdl = CatERing.getInstance().getKitchenTaskManager().openToDoList(service);
            if (tdl == null) {
                System.out.println("Errore: ToDoList non aperta correttamente");
                return;
            }

            System.out.println("ToDoList aperto per il servizio: " + service);


            //Recupera tutte le ricette disponibili e la tabella dei turni.
            ArrayList<Recipe> recipes = CatERing.getInstance().getRecipeManager().getRecipes();
            TurnTable turnTable = CatERing.getInstance().getKitchenTaskManager().getTurnTable();

            CatERing.getInstance().getUserManager().fakeLogin("Marinella");

            ArrayList<User> cooks = new ArrayList<>();
            cooks.add(CatERing.getInstance().getUserManager().getCurrentUser());

            CatERing.getInstance().getUserManager().fakeLogin("Lidia");

            System.out.println("Stato attuale del ToDoList: " + tdl);

            KitchenTask toUpdate = CatERing.getInstance().getKitchenTaskManager().addTask(recipes.get(2),
                    null,
                    null);

            System.out.println("Nuova task aggiunta: " + toUpdate);

            System.out.println("Stato attuale del ToDoList: " + tdl);

            System.out.println("Aggiornamento task ["+ toUpdate+"] con i seguenti dati: \n"+turnTable.getTurnById(1)+", "+cooks+", "+recipes.get(3)+"\n");

            //Aggiornamento del task
            CatERing.getInstance().getKitchenTaskManager().updateTask(toUpdate,turnTable.getTurnById(1));
            CatERing.getInstance().getKitchenTaskManager().updateTask(toUpdate,cooks);
            CatERing.getInstance().getKitchenTaskManager().updateTask(toUpdate,recipes.get(3));

            System.out.println("Stato finale del ToDoList: " + tdl);

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Test fallito: " + e.getMessage());
            System.exit(1);
        }


    }
}
