package catering.businesslogic.turn;

import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class TurnTable {
    private static TurnTable instance;
    private ArrayList<Turn> turns;

    private TurnTable() {
        turns = new ArrayList<>();
        PersistenceManager.executeQuery("SELECT * FROM Turns", new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                turns.add(Turn.loadTurnById(rs.getInt("id")));
            }
        });
        //System.out.println("Totale turni caricati: " + turns.size());
    }
    public static TurnTable getInstance() {
        if(instance == null) { instance = new TurnTable();}
        return instance;
    }

    /**
     * Restituisce un turno per un dato ID.
     *
     * @param id L'ID del turno da cercare.
     * @return Il turno corrispondente all'ID, oppure null se non trovato.
     */
    public Turn getTurnById(int id) {
        for(Turn t : turns)
        {
            if(t.id == id){
                return t;
            }
        }
        return null;
    }

    /**
     * Restituisce un turno casuale dalla lista dei turni disponibili.
     *
     * @return Un turno casuale.
     */
    public Turn getRandomTurn() {
        Random r = new Random();
        return turns.get(r.nextInt(turns.size()));
    }

    public ArrayList<Turn> getTurns() {
        return turns;
    }

    @Override
    public String toString() {
        String result = "TurnTable{\n";
        for (Turn turn : turns) {
            result += "  " + turn.toString() + "\n";
        }
        result += "}";
        return result;
    }


}
