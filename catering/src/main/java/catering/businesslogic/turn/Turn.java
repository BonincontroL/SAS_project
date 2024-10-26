package catering.businesslogic.turn;

import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Turn {

    private ArrayList<Integer> availableCooksIds;
    int id;

    public int getId() {
        return id;
    }
    public static Turn loadTurnById(int id)
    {
        Turn t = new Turn();
        t.id = id;
        t.availableCooksIds = new ArrayList<>();

        PersistenceManager.executeQuery("Select * FROM Availability WHERE idTurn = " + id, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                t.availableCooksIds.add(rs.getInt("idUser"));
            }
        });
        return t;
    }

    public void addAvailability(User u)
    {
        if (u.isCook()) {
            availableCooksIds.add(u.getId());
        } else {
            System.err.println("The user is not a cook!");
        }
    }

    public boolean isAvailable(User cook) {
        return availableCooksIds.contains(cook.getId());
    }

    public ArrayList<Integer> getAvailableCooksIds() {
        return availableCooksIds;
    }

    @Override
    public String toString() {
        return "Turn-> " + id;
    }



}
