package catering.businesslogic.kitchentask;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.menu.MenuItem;
import catering.businesslogic.menu.Section;
import catering.businesslogic.recipe.Procedure;
import catering.businesslogic.turn.Turn;
import catering.businesslogic.turn.TurnTable;
import catering.businesslogic.user.User;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;

public class KitchenTaskManager {

    ToDoList currentToDoList;
    ArrayList<KitchenTaskEventReceiver> receivers;

    public KitchenTaskManager() {
        receivers = new ArrayList<>();
    }


    /**
     * Genera una nuova lista di cose da fare per un determinato servizio.
     *
     * @param service Il servizio per cui generare la lista di cose da fare.
     * @return La ToDoList generata.
     * @throws UseCaseLogicException Se l'utente corrente non è un chef o se la ToDoList corrente è null.
     * @throws ServiceException      Se il servizio ha già una ToDoList.
     */
    public ToDoList generateToDoList(ServiceInfo service) throws UseCaseLogicException, ServiceException {
        if (service.hasToDoList()) {
            throw new ServiceException();
        }
        Menu m = service.getMenu();
        //System.out.println("menu: " + m + " service: " + service);

        if (!CatERing.getInstance().getUserManager().getCurrentUser().isChef()) throw new UseCaseLogicException();
        if (!m.isOwner(CatERing.getInstance().getUserManager().getCurrentUser())) throw new UseCaseLogicException();

        currentToDoList = new ToDoList(service);
        ArrayList<MenuItem> freeItems = m.getFreeItems();
        for (MenuItem item : freeItems) {
            KitchenTask kitchenTask = new KitchenTask(item);
            currentToDoList.add(kitchenTask);
        }
        ArrayList<Section> sections = m.getSections();
        for (Section section : sections) {
            ArrayList<MenuItem> items = section.getItems();
            for (MenuItem item : items) {
                KitchenTask kitchenTask = new KitchenTask(item);
                currentToDoList.add(kitchenTask);
            }
        }

        notifyNewListCreated();

        return currentToDoList;
    }


    /**
     * Apre una ToDoList esistente per un determinato servizio.
     *
     * @param service Il servizio per cui aprire la lista di cose da fare.
     * @return La ToDoList aperta.
     * @throws UseCaseLogicException Se l'utente corrente non è un chef o se la ToDoList corrente è null.
     * @throws ServiceException      Se il servizio non ha una ToDoList.
     */
    public ToDoList openToDoList(ServiceInfo service) throws UseCaseLogicException, ServiceException {
        if (!service.hasToDoList()) {
            throw new ServiceException();
        }
        Menu m = service.getMenu();

        if (!CatERing.getInstance().getUserManager().getCurrentUser().isChef()) throw new UseCaseLogicException();
        if (!m.isOwner(CatERing.getInstance().getUserManager().getCurrentUser())) throw new UseCaseLogicException();

        currentToDoList = ToDoList.loadToDoList(service);

       //System.out.println("KitchenTaskManager -> openToDoList() -> currentToDoList.getTasks(): "+currentToDoList.getTasks());

        return currentToDoList;
    }

    /**
     * Aggiunge una procedura alla ToDoList corrente.
     *
     * @param procedure La procedura da aggiungere.
     * @return L'attività di cucina creata.
     * @throws UseCaseLogicException Se l'utente corrente non è un chef o se la ToDoList corrente è null.
     */
    public KitchenTask addProcedure(Procedure procedure) throws UseCaseLogicException {
        if (!CatERing.getInstance().getUserManager().getCurrentUser().isChef() || currentToDoList == null)
            throw new UseCaseLogicException();

        KitchenTask kitchenTask = new KitchenTask(procedure);
        currentToDoList.add(kitchenTask);

        //System.out.println(this.currentToDoList);

        notifyNewTaskAdded(kitchenTask);

        return kitchenTask;
    }


    /**
     * Ordina la ToDoList corrente usando un comparatore.
     *
     * @param comparator Il comparatore usato per ordinare le attività di cucina.
     * @throws UseCaseLogicException Se l'utente corrente non è un chef o se la ToDoList corrente è null.
     */
    public void sortToDoList(Comparator<KitchenTask> comparator) throws UseCaseLogicException {
        if (!CatERing.getInstance().getUserManager().getCurrentUser().isChef() || currentToDoList == null)
            throw new UseCaseLogicException();

        currentToDoList.sort(comparator);
    }

    /**
     * Restituisce la tabella dei turni.
     *
     * @return La tabella dei turni.
     */
    public TurnTable getTurnTable() {
        return CatERing.getInstance().getTurnManager().getTurnTable();
    }

    /**
     * Aggiunge una nuova attività di cucina alla ToDoList corrente.
     *
     * @param procedure La procedura associata all'attività di cucina.
     * @param cooks     I cuochi assegnati all'attività di cucina.
     * @param turn      Il turno assegnato all'attività di cucina.
     * @return L'attività di cucina creata.
     * @throws UseCaseLogicException Se l'utente corrente non è un chef, se la ToDoList corrente è null, o se ci sono errori logici.
     * @throws ServiceException      Se ci sono errori di servizio.
     */
    public KitchenTask addTask(Procedure procedure, ArrayList<User> cooks, Turn turn) throws UseCaseLogicException, ServiceException {
        if (!CatERing.getInstance().getUserManager().getCurrentUser().isChef() || currentToDoList == null)
            throw new UseCaseLogicException();

        KitchenTask kitchenTask = new KitchenTask(procedure);

        if (cooks != null) {
            if (turn != null) {
                kitchenTask.setTurn(turn);
                kitchenTask.addCooks(cooks);
            } else {
                throw new UseCaseLogicException();
            }
        } else {
            if (turn != null) {
                kitchenTask.setTurn(turn);
            }
        }
        currentToDoList.add(kitchenTask);

        notifyNewTaskAdded(kitchenTask);

        return kitchenTask;
    }

    /**
     * Aggiunge caratteristiche a un'attività di cucina nella ToDoList corrente.
     *
     * @param kitchenTask L'attività di cucina da aggiornare.
     * @param esteemTime  Il tempo stimato per completare l'attività.
     * @param amount      La quantità associata all'attività.
     * @return La ToDoList aggiornata.
     * @throws UseCaseLogicException Se l'utente corrente non è un chef, se la ToDoList corrente è null, o se l'attività di cucina non è contenuta nella ToDoList.
     */
    public ToDoList addFeatures(KitchenTask kitchenTask, Duration esteemTime, Float amount) throws UseCaseLogicException {
        if (!CatERing.getInstance().getUserManager().getCurrentUser().isChef() || currentToDoList == null || !currentToDoList.contains(kitchenTask))
            throw new UseCaseLogicException();

        currentToDoList.addFeatures(kitchenTask, esteemTime, amount);

        notifyTaskChanged(kitchenTask);

        return currentToDoList;
    }

    /**
     * Rimuove tutte le attività di cucina associate a una procedura dalla ToDoList corrente.
     *
     * @param procedure La procedura da rimuovere.
     * @throws UseCaseLogicException Se l'utente corrente non è un chef o se la ToDoList corrente è null.
     */
    public void deleteProcedure(Procedure procedure) throws UseCaseLogicException {
        if (!CatERing.getInstance().getUserManager().getCurrentUser().isChef() || currentToDoList == null)
            throw new UseCaseLogicException();

        ArrayList<KitchenTask> removedTasks = currentToDoList.deleteProcedure(procedure);
        for (KitchenTask kt : removedTasks) {
            notifyTaskRemoved(kt);
        }
    }

    /**
     * Aggiorna i cuochi assegnati a una determinata attività di cucina.
     *
     * @param toUpdate L'attività di cucina da aggiornare.
     * @param cooks    I nuovi cuochi da assegnare all'attività.
     * @throws UseCaseLogicException Se l'utente corrente non è un chef o se la ToDoList corrente è null.
     */
    public void updateTask(KitchenTask toUpdate, ArrayList<User> cooks) throws UseCaseLogicException {
        if (!CatERing.getInstance().getUserManager().getCurrentUser().isChef() || currentToDoList == null)
            throw new UseCaseLogicException();

        toUpdate.updateTask(cooks);
        notifyTaskChanged(toUpdate);
    }

    /**
     * Aggiorna il turno assegnato a una determinata attività di cucina.
     *
     * @param toUpdate L'attività di cucina da aggiornare.
     * @param turn     Il nuovo turno da assegnare all'attività.
     * @throws UseCaseLogicException Se l'utente corrente non è un chef o se la ToDoList corrente è null.
     */
    public void updateTask(KitchenTask toUpdate, Turn turn) throws UseCaseLogicException {
        if (!CatERing.getInstance().getUserManager().getCurrentUser().isChef() || currentToDoList == null)
            throw new UseCaseLogicException();

        toUpdate.updateTask(turn);
        notifyTaskChanged(toUpdate);
    }

    /**
     * Aggiorna la procedura assegnata a una determinata attività di cucina.
     *
     * @param toUpdate  L'attività di cucina da aggiornare.
     * @param procedure La nuova procedura da assegnare all'attività.
     * @throws UseCaseLogicException Se l'utente corrente non è un chef o se la ToDoList corrente è null.
     */
    public void updateTask(KitchenTask toUpdate, Procedure procedure) throws UseCaseLogicException {
        if (!CatERing.getInstance().getUserManager().getCurrentUser().isChef() || currentToDoList == null)
            throw new UseCaseLogicException();
        toUpdate.updateTask(procedure);
        notifyTaskChanged(toUpdate);
    }


    /**
     * Aggiorna una determinata attività di cucina con una nuova procedura, cuochi e turno.
     *
     * @param toUpdate  L'attività di cucina da aggiornare.
     * @param procedure La nuova procedura da assegnare all'attività.
     * @param cooks     I nuovi cuochi da assegnare all'attività.
     * @param turn      Il nuovo turno da assegnare all'attività.
     * @throws UseCaseLogicException Se l'utente corrente non è un chef o se la ToDoList corrente è null.
     */
    public void updateTask(KitchenTask toUpdate, Procedure procedure, ArrayList<User> cooks, Turn turn) throws UseCaseLogicException {
        if (!CatERing.getInstance().getUserManager().getCurrentUser().isChef() || currentToDoList == null)
            throw new UseCaseLogicException();

        if (procedure != null) {
            toUpdate.updateTask(procedure);
        }
        if (cooks != null) {
            toUpdate.updateTask(cooks);
        }
        if (turn != null) {
            toUpdate.updateTask(turn);
        }

        notifyTaskChanged(toUpdate);
    }

    /**
     * Cancella una ToDoList esistente per un determinato servizio.
     *
     * @param service Il servizio per cui cancellare la lista di cose da fare.
     * @return La ToDoList cancellata.
     * @throws UseCaseLogicException Se l'utente corrente non è un chef o se la ToDoList corrente è null.
     * @throws ServiceException      Se ci sono errori di servizio.
     */
    public ToDoList deleteToDoList(ServiceInfo service) throws UseCaseLogicException, ServiceException {
        openToDoList(service);
        notifyListDeleted();

        currentToDoList.clear();

        return currentToDoList;
    }


    /**
     * Cancella una determinata attività di cucina dalla ToDoList corrente.
     *
     * @param toUpdate L'attività di cucina da cancellare.
     * @throws UseCaseLogicException Se l'utente corrente non è un chef, se la ToDoList corrente è null o se l'attività di cucina non è contenuta nella ToDoList.
     */
    public void deleteTask(KitchenTask toUpdate) throws UseCaseLogicException {
        if (!CatERing.getInstance().getUserManager().getCurrentUser().isChef() || currentToDoList == null || !currentToDoList.contains(toUpdate))
            throw new UseCaseLogicException();

        currentToDoList.deleteTask(toUpdate);
        notifyTaskRemoved(toUpdate);
    }

    @Override
    public String toString() {
        return "KitchenTaskManager{" +
                "currentToDoList=" + currentToDoList +
                ", receivers=" + receivers +
                '}';
    }

    public ToDoList getCurrentToDoList() {
        return currentToDoList;
    }

    //////////////////////////Event Receivers Methods//////////////////////////////

    public void addEventReceiver(KitchenTaskEventReceiver kitchenTaskEventReceiver) {
        this.receivers.add(kitchenTaskEventReceiver);
    }

    private void notifyNewListCreated() {
        for (KitchenTaskEventReceiver receiver : receivers) {
            receiver.updateNewListCreated(currentToDoList);
        }
    }

    private void notifyListDeleted() {
        for (KitchenTaskEventReceiver receiver : receivers) {
            receiver.updateListDeleted(currentToDoList);
        }
    }

    private void notifyNewTaskAdded(KitchenTask kitchenTask) {
        for (KitchenTaskEventReceiver receiver : receivers) {
            receiver.updateNewTaskAdded(kitchenTask);
        }
    }

    private void notifyTaskRemoved(KitchenTask kitchenTask) {
        for (KitchenTaskEventReceiver receiver : receivers) {
            receiver.updateTaskRemoved(kitchenTask);
        }
    }

    private void notifyTaskChanged(KitchenTask kitchenTask) {
        for (KitchenTaskEventReceiver receiver : receivers) {
            receiver.updateTaskChanged(kitchenTask);
        }
    }

}
