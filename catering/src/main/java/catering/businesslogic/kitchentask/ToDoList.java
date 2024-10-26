package catering.businesslogic.kitchentask;

import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.recipe.Procedure;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ToDoList {
    private ArrayList<KitchenTask> tasks;
    ServiceInfo service;

    public ToDoList(ServiceInfo service) {
        this.service = service;
        tasks = new ArrayList<>();
    }

    public ArrayList<KitchenTask> getTasks() {
        return tasks;
    }

    /**
     * Aggiunge un'attività di cucina alla lista.
     *
     * @param kitchenTask L'attività di cucina da aggiungere.
     */
    public void add(KitchenTask kitchenTask) {
        tasks.add(kitchenTask);
    }


    /**
     * Carica una ToDoList dal database per un determinato servizio.
     *
     * @param service Il servizio per cui caricare la lista di cose da fare.
     * @return La ToDoList caricata dal database.
     */
    public static ToDoList loadToDoList(ServiceInfo service) {
        ToDoList ret = new ToDoList(service);
        ArrayList<Integer> taskIds = new ArrayList<>();

        String query = "SELECT * FROM ToDoLists WHERE idService = " + service.getId();
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                taskIds.add(rs.getInt("idTask"));
            }
        });
        List<KitchenTask> allTasks = KitchenTask.getAllTasks();

        for (KitchenTask kitchenTask : allTasks) {
            if (taskIds.contains(kitchenTask.getId())) {
                ret.tasks.add(kitchenTask);
            }
        }
        return ret;//ToDoList completa
    }

    /**
     * Ordina le KitchenTask nella lista usando un comparatore.
     *
     * @param comparator Il comparatore usato per ordinare le attività.
     */
    public void sort(Comparator<KitchenTask> comparator) {
        tasks.sort(comparator);
    }

    /**
     * Salva una nuova ToDoList nel database.
     *
     * @param tdl La ToDoList da salvare.
     */
    public static void saveNewToDoList(ToDoList tdl) {
        for (KitchenTask kitchenTask : tdl.tasks) {
            KitchenTask.saveTask(kitchenTask, true);
        }
    }

    /**
     * Aggiunge caratteristiche a un'attività di cucina nella lista.
     *
     * @param kitchenTask L'attività di cucina da aggiornare.
     * @param esteemTime  Il tempo stimato per completare l'attività.
     * @param amount      La quantità associata all'attività.
     * @return La ToDoList aggiornata.
     */
    public ToDoList addFeatures(KitchenTask kitchenTask, Duration esteemTime, Float amount) {
        for (KitchenTask kt : tasks) {
            if (kt.getId() == kitchenTask.getId()) {
                kt.setDuration(esteemTime);
                kt.setAmount(amount);
                break;
            }
        }
        return this;
    }

    /**
     * Rimuove tutte le attività di cucina associate a una procedura dalla lista.
     *
     * @param procedure La procedura da rimuovere.
     * @return La lista di attività di cucina rimosse.
     */
    public ArrayList<KitchenTask> deleteProcedure(Procedure procedure) {
        ArrayList<KitchenTask> removedTasks = new ArrayList<>();

        for (KitchenTask kt : tasks) {
            if (kt.getProcedure() != null && procedure != null && kt.getProcedure().getName().equals(procedure.getName())) {
                removedTasks.add(kt);
            }
        }
        tasks.removeAll(removedTasks);

        return removedTasks;
    }

    /**
     * Cancella tutte le attività di cucina dalla lista nel database.
     *
     * @param tdl La ToDoList da cancellare.
     */
    public static void clearList(ToDoList tdl) {
        String query = "DELETE FROM ToDoLists WHERE idService = " + tdl.service.getId();
        PersistenceManager.executeUpdate(query);
        for (KitchenTask kt : tdl.tasks) {
            KitchenTask.deleteTask(kt);
        }

    }

    /**
     * Salva una nuova attività di cucina nella lista delle cose da fare.
     *
     * @param currentToDoList La lista delle cose da fare corrente.
     * @param kitchenTask     L'attività di cucina da salvare.
     */
    public static void saveNewTask(ToDoList currentToDoList, KitchenTask kitchenTask) {
        String update = "INSERT INTO ToDoLists (idService, idTask) VALUES " +
                "(" + currentToDoList.service.getId() +
                ", " + kitchenTask.getId() +
                ");";
        PersistenceManager.executeUpdate(update);
    }


    /**
     * Pulisce tutte le attività di cucina dalla lista.
     */
    public void clear() {
        tasks.clear();
    }

    /**
     * Verifica se una determinata attività di cucina è contenuta nella lista.
     *
     * @param toUpdate L'attività di cucina da verificare.
     * @return True se la lista contiene l'attività di cucina, altrimenti false.
     */
    public boolean contains(KitchenTask toUpdate) {
        return tasks.contains(toUpdate);
    }

    /**
     * Rimuove una determinata attività di cucina dalla lista.
     *
     * @param toUpdate L'attività di cucina da rimuovere.
     */
    public void deleteTask(KitchenTask toUpdate) {
        tasks.remove(toUpdate);
    }

    @Override
    public String toString() {
        String s = "ToDoList:\n";
        for (KitchenTask kt : tasks) {
            s += kt.toString();
            s+="\n";
        }
        return s + "\n";
    }
}
