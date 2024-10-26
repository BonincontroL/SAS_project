package catering.businesslogic.kitchentask;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.menu.MenuItem;
import catering.businesslogic.recipe.Procedure;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.turn.Turn;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;

public class KitchenTask {
    private ArrayList<User> cooks;
    private Turn turn;
    private Procedure procedure;
    private Duration esteemTime;
    private Float amount;
    private int id;

    public KitchenTask(ArrayList<User> cooks, Turn turn, Procedure procedure, Duration esteemTime, Float amount, int id) {
        this.cooks = cooks;
        this.turn = turn;
        this.procedure = procedure;
        this.esteemTime = esteemTime;
        this.amount = amount;
        this.id = id;
    }

    /**
     * Costruttore che crea un'attività di cucina a partire da un elemento del menu.
     *
     * @param item L'elemento del menu da cui creare l'attività.
     */
    public KitchenTask(MenuItem item) {
        this.procedure = item.getItemRecipe();
    }

    /**
     * Costruttore che crea un'attività di cucina a partire da una procedura.
     *
     * @param procedure La procedura da cui creare l'attività.
     */
    public KitchenTask(Procedure procedure) {
        this.procedure = procedure;
    }

    /**
     * Recupera tutte le attività di cucina dal database.
     *
     * @return Una lista di tutte le attività di cucina.
     */
    public static ArrayList<KitchenTask> getAllTasks() {

        //Il metodo getAllTasks() restituisce una lista di KitchenTask,
        // ognuno dei quali include tutte le informazioni correlate, come cuochi, turno e ricetta.

        ArrayList<KitchenTask> tasks = new ArrayList<>();

        //I LEFT JOIN per garantire
        // che tutte le KitchenTasks vengano recuperate,
        // anche se non hanno un turno o una procedura  associati.

        //recupera tutte le attività di cucina (KitchenTask) dal database,
        // inclusi tutti i dettagli associati come i cuochi, il turno e la ricetta.

        String query = "SELECT * FROM KitchenTasks " +
                "LEFT JOIN Turns on (KitchenTasks.idTurn = Turns.id) " +
                "LEFT JOIN Recipes on (KitchenTasks.idProcedure = Recipes.id)";

        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int idTask = rs.getInt("id");
                ArrayList<User> cooks = new ArrayList<>();
                String query2="SELECT * FROM CookTask JOIN Users on (CookTask.idCook = Users.id) WHERE CookTask.idTask = " + idTask;
                PersistenceManager.executeQuery(query2, new ResultHandler() {
                    @Override
                    public void handle(ResultSet rs1) throws SQLException {
                        User u = User.loadUserById(rs1.getInt("CookTask.idCook"));
                        cooks.add(u);
                    }
                });
                Turn turn = rs.getInt("idTurn") == 0 ? null : Turn.loadTurnById(rs.getInt("idTurn"));
                Procedure p = Recipe.loadRecipeById(rs.getInt("idProcedure"));
                String durationString = rs.getString("duration");
                Float amount = rs.getFloat("amount");
                KitchenTask kitchenTask = new KitchenTask(cooks.isEmpty() ? null : cooks, turn, p, durationString == null ? null : Duration.parse(durationString), amount == 0 ? null : amount, idTask);
                tasks.add(kitchenTask);
            }
        });

        //System.out.println("Sono dentro ->\n KitchenTasks in getAllTasks(): " + tasks);
        return tasks;
    }

    /**
     * Restituisce i cuochi assegnati all'attività.
     *
     * @return Una lista di cuochi.
     */
    public ArrayList<User> getCooks() {
        return this.cooks;
    }

    /**
     * Restituisce la procedura associata all'attività.
     *
     * @return La procedura associata.
     */
    public Procedure getProcedure() {
        return procedure;
    }

    /**
     * Aggiorna un'attività di cucina nel database.
     *
     * @param kitchenTask L'attività di cucina da aggiornare.
     */
    public static void updateTask(KitchenTask kitchenTask) {
        deleteTask(kitchenTask);
        saveTask(kitchenTask, false);
    }

    /**
     * Elimina un'attività di cucina dal database.
     *
     * @param kitchenTask L'attività di cucina da eliminare.
     */
    public static void deleteTask(KitchenTask kitchenTask) {

       // System.out.println("Sono dentro ->\n KitchenTask.deleteTask() - kitchenTask: " + kitchenTask);

        PersistenceManager.executeUpdate("DELETE FROM KitchenTasks WHERE id = " + kitchenTask.getId());
        PersistenceManager.executeUpdate("DELETE FROM CookTask WHERE idTask = " + kitchenTask.getId());
    }


  /*  private static ArrayList<User> getCooksByTaskId(int idTask) {
        ArrayList<User> cooks = new ArrayList<>();

        String cookQuery = "SELECT * FROM CookTask JOIN Users on (CookTask.idCook = Users.id) WHERE CookTask.idTask = " + idTask;

        PersistenceManager.executeQuery(cookQuery, rs -> {
            while (rs.next()) {
                User u = User.loadUserById(rs.getInt("CookTask.idCook"));
                cooks.add(u);
            }
        });
        System.out.println(" Sono dentro -> KitchenTask.getCooksByTaskId() - cooks: " + cooks.toString());

        return cooks;
    }*/


    @Override
    public String toString() {
        return "KitchenTask{" +
                "cooks=" + cooks +
                ", turn=" + turn +
                ", procedure=" + procedure +
                ", esteemTime=" + formatDuration(esteemTime) +
                ", amount=" + amount +
                ", id=" + id +
                '}';
    }

    /**
     * Aggiunge cuochi all'attività di cucina.
     *
     * @param cooks I cuochi da aggiungere.
     */
    public void addCooks(ArrayList<User> cooks) {
        if (turn == null) System.err.println("Error - Adding cooks without turn");
        if (this.cooks == null) this.cooks = new ArrayList<>();

        for (User u : cooks) {
            if (!u.isCook() || !turn.isAvailable(u))
                System.err.println("Error - Cook Unavailable" + u.getUserName());
            else this.cooks.add(u);
        }
    }

    public int getId() {
        return id;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public void setDuration(Duration esteemTime) {
        this.esteemTime = esteemTime;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void updateTask(Procedure procedure) {
        this.procedure = procedure;
    }

    /**
     * Aggiorna i cuochi assegnati all'attività di cucina.
     *
     * @param cooks I nuovi cuochi da assegnare.
     * @throws UseCaseLogicException Se il turno non è impostato ma i cuochi sono presenti.
     */
    public void updateTask(ArrayList<User> cooks) throws UseCaseLogicException {
        if (cooks == null) {
            this.cooks = null;
            System.err.println("cooks is null");
            return;
        }
        if (this.turn == null && !cooks.isEmpty()) throw new UseCaseLogicException();

        this.cooks = new ArrayList<>(); //rimuove l'elenco precedente dei cuochi

        addCooks(cooks);

        //System.out.println("KitchenTask.updateTask() - this.cooks: " + this.cooks.toString());
    }

    /**
     * Aggiorna il turno assegnato all'attività di cucina.
     *
     * @param turn Il nuovo turno da assegnare.
     */
    public void updateTask(Turn turn) {
        this.turn = turn;
        if (cooks != null && turn != null) {
            cooks.removeIf(user -> !turn.isAvailable(user));

            //System.out.println("KitchenTask.updateTask() - this.cooks: " + this.cooks.toString());
        }


    }

    public Turn getTurn() {
        return turn;
    }

    public Duration getEsteemTime() {
        return esteemTime;
    }

    public Float getAmount() {
        return amount;
    }

    private static int getMaxId() {
        final Integer[] maxId = new Integer[1];
        String query = "SELECT MAX(id) AS m FROM KitchenTasks";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                maxId[0] = rs.getInt("m");
            }
        });

        return maxId[0];
    }

    /**
     * Salva un'attività di cucina nel database.
     *
     * @param kitchenTask L'attività di cucina da salvare.
     * @param isNewTask   Indica se l'attività è nuova.
     */
    public static void saveTask(KitchenTask kitchenTask, boolean isNewTask) {
        //conversione in stringhe da utilizzare nella query

        String turn = kitchenTask.getTurn() == null ? "null" : String.valueOf(kitchenTask.getTurn().getId());
        String procedure = kitchenTask.getProcedure() == null ? "null" : String.valueOf(kitchenTask.getProcedure().getDataBaseId());
        String time = kitchenTask.getEsteemTime() == null ? "null" : "\"" + kitchenTask.getEsteemTime().toString() + "\"";

        try{
            if (isNewTask) {
                String newKitchenTaskUpdate = "INSERT INTO KitchenTasks (idTurn, idProcedure, amount, duration)" +
                        " VALUES (" + turn +
                        ", " + procedure +
                        ", " + kitchenTask.getAmount() +
                        ", " + time +
                        ");";
                PersistenceManager.executeUpdate(newKitchenTaskUpdate);

                kitchenTask.id = getMaxId();//Aggiorna l'oggetto con l'ultimo ID

                ToDoList.saveNewTask(CatERing.getInstance().getKitchenTaskManager().getCurrentToDoList(), kitchenTask);
            } else {
                String newKitchenTaskUpdate = "INSERT INTO KitchenTasks (id, idTurn, idProcedure, amount, duration)" +
                        " VALUES (" + kitchenTask.getId() +
                        ", " + turn +
                        ", " + procedure +
                        ", " + kitchenTask.getAmount() +
                        ", " + time +
                        ");";
                PersistenceManager.executeUpdate(newKitchenTaskUpdate);
            }


            if (kitchenTask.cooks != null) {
                for (User u : kitchenTask.cooks) {
                    String insertionCookTask = "INSERT INTO CookTask (idCook, idTask) VALUES " +
                            "(" + u.getId() +
                            ", " + kitchenTask.getId() +
                            ");";
                    PersistenceManager.executeUpdate(insertionCookTask);

                }
            }
        }catch(Exception e){
            System.err.println("saveTask() - Error!!!!!!!!!!!: " + e.getMessage());
        }


    }

    private static String formatDuration(Duration duration) {
        if (duration == null) {
            return "null";
        }
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
