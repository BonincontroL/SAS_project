package catering.KitchenTaskTests;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchentask.KitchenTask;
import catering.businesslogic.kitchentask.ServiceException;
import catering.businesslogic.kitchentask.ToDoList;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.turn.Turn;
import catering.businesslogic.turn.TurnTable;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;

import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;

public class Test1 {

    public static void main(String[] args) {
        try {
            // Test connessione al database
//            System.out.println("TEST CONNESSIONE DATABASE");
//            PersistenceManager.testSQLConnection();
//            System.out.println("Connessione al database riuscita");

            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println("Login effettuato con successo per l'utente 'Lidia'");

            System.out.println("TEST GENERA FOGLIO DI LAVORO");
            ServiceInfo service = ServiceInfo.loadServiceInfoForEvent(1, 2);
            if (service == null) {
                System.out.println("Servizio non trovato");
                return;
            }

            ToDoList tdl = CatERing.getInstance().getKitchenTaskManager().generateToDoList(service);
            if(tdl == null) {
                System.out.println("Foglio di lavoro non generato");
                return;
            }
            ArrayList<Recipe> recipes = CatERing.getInstance().getRecipeManager().getRecipes();
            System.out.println("Foglio di lavoro generato con successo  per il servizio: "+ service);
            System.out.println(tdl);

            System.out.println("TEST AGGIUNGI PROCEDURA");
            CatERing.getInstance().getKitchenTaskManager().addProcedure(recipes.get(5));
            System.out.println("Procedura aggiunta con successo:");
            System.out.println(tdl);

            System.out.println("TEST ORDINA LISTA");
            Comparator<KitchenTask> comparatorProcedureName = new Comparator<KitchenTask>() {
                public int compare(KitchenTask o1, KitchenTask o2) {
                    if (o1.getProcedure() != null && o2.getProcedure() != null) {
                        return o1.getProcedure().getName().compareTo(o2.getProcedure().getName());
                    } else {
                        return 0;
                    }
                }
            };
            Comparator<KitchenTask> comparatorCookName = new Comparator<KitchenTask>() {
                public int compare(KitchenTask o1, KitchenTask o2) {
                    if (o1.getCooks() != null && !o1.getCooks().isEmpty() && o2.getCooks() != null && !o2.getCooks().isEmpty()) {
                        return o1.getCooks().get(0).getUserName().compareTo(o2.getCooks().get(0).getUserName());
                    } else {
                        return 0;
                    }
                }
            };
            CatERing.getInstance().getKitchenTaskManager().sortToDoList(comparatorProcedureName);
            System.out.println("Lista ordinata per nome procedura con successo:");
            System.out.println(tdl);
            CatERing.getInstance().getKitchenTaskManager().sortToDoList(comparatorCookName);
            System.out.println("Lista ordinata per nome cuoco con successo:");
            System.out.println(tdl);

            // Test per aggiungere lavoro cuoco
            System.out.println("TEST AGGIUNGI LAVORO CUOCO");
            TurnTable turnTable = CatERing.getInstance().getKitchenTaskManager().getTurnTable();
            Turn turn = turnTable.getTurnById(1);

         /*   System.out.println("\n TEST - turni: "+turnTable.getTurns().toString());
            System.out.println("-----------------------------------------------");
            System.out.println("Turno: " + turn.getId() + " -> diponibilità cuochi:");
            ArrayList<Integer> ac= turn.getAvailableCooksIds();
            for (Integer integer : ac) {
                System.out.println("Cook: " + integer);
            }
            System.out.println("-----------------------------------------------\n");*/

            CatERing.getInstance().getUserManager().fakeLogin("Marinella");
            ArrayList<User> cooks = new ArrayList<>();

            cooks.add(CatERing.getInstance().getUserManager().getCurrentUser());

            CatERing.getInstance().getUserManager().fakeLogin("Lidia");

            // Aggiunge tutto
            KitchenTask toUpdate = CatERing.getInstance().getKitchenTaskManager().addTask(recipes.get(8), cooks, turn);
            System.out.println("Attività di cucina con cuochi e turno aggiunta con successo:");
            System.out.println(tdl);

            // Aggiunge solo il turno
            CatERing.getInstance().getKitchenTaskManager().addTask(recipes.get(1), null, turnTable.getRandomTurn());
            System.out.println("Attività di cucina con solo turno aggiunta con successo:");
            System.out.println(tdl);

            System.out.println("TEST AGGIUNGI CARATTERISTICHE");
            CatERing.getInstance().getKitchenTaskManager().addFeatures(toUpdate, Duration.ofMinutes(59), 0.5f);
            System.out.println("Caratteristiche aggiunte con successo:");
            System.out.println(tdl);

            System.out.println("TEST RIMUOVI CARATTERISTICHE");
            CatERing.getInstance().getKitchenTaskManager().addFeatures(toUpdate, null, null);
            System.out.println("Caratteristiche rimosse con successo:");
            System.out.println(tdl);

        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Test fallito: " + e.getMessage());
            System.exit(1);
        }
    }


}
