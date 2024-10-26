package catering.KitchenTaskTests;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchentask.ToDoList;


public class Test1a {
    public static void main(String[] args) {
        try{

            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println("Login effettuato con successo per l'utente 'Lidia'");

            System.out.println("Apri ToDoList");
            ServiceInfo service = ServiceInfo.loadServiceInfoForEvent(1, 2);
            if (service == null) {
                System.out.println("Errore: Servizio non trovato");
                return;
            }

            ToDoList tdl = CatERing.getInstance().getKitchenTaskManager().openToDoList(service);
            if (tdl == null) {
                System.out.println("Errore: ToDoList non aperta correttamente");
                return;
            }

            System.out.println("ToDoList aperto per il servizio: " + service);
            System.out.println(tdl);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Test fallito: " + e.getMessage());
            System.exit(1);
        }



    }
}
