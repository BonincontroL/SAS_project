package catering.KitchenTaskTests;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchentask.ToDoList;

public class Test1b {
    public static void main(String[] args) {
        try {

            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println("Fake login eseguito con successo per l'utente 'Lidia'");

            ServiceInfo service = ServiceInfo.loadServiceInfoForEvent(1, 2);
            if (service == null) {
                System.out.println("Errore: Servizio non trovato");
                return;
            }
            System.out.println("Informazioni sul servizio caricate con successo per l'evento ID= 1 e servizio ID= 2: "+ service);
            ToDoList tdl = CatERing.getInstance().getKitchenTaskManager().openToDoList(service);
            if (tdl == null) {
                System.out.println("Errore: ToDoList non aperta correttamente");
                return;
            }
            System.out.println("ToDoList aperto per il servizio: " + service);
            System.out.println("ToDoList prima:");
            System.out.println(tdl);

            tdl = CatERing.getInstance().getKitchenTaskManager().deleteToDoList(service);
            System.out.println("ToDoList eliminata con successo per il servizio: "+service);

            System.out.println("ToDoList dopo l'eliminazione:");
            if(tdl.getTasks().isEmpty()){
                System.out.println("ToDoList vuota: ");
                System.out.print("{ "+tdl+" }");
            }else{
                System.out.println("ToDoList NON vuota - deleteToDoList non ha funzionato correttamente");
                System.out.println(tdl);
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Test fallito: " + e.getMessage());
            System.exit(1);
        }
    }
}
