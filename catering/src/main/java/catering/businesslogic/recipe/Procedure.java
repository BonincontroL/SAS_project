package catering.businesslogic.recipe;

import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Procedure {

    protected String name;

    public abstract int getDataBaseId();


/*    public int getDataBaseId()
    {
        final Integer[] id = {-1};
        String query = "SELECT id FROM Recipes WHERE Recipes.name = " + name;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                id[0] = rs.getInt("id");
            }
        });

        if (id[0] == -1) {
           System.out.println("No id found for recipe " + name);
        }

        return id[0];
    }*/

    public String getName() {
        return name;
    }
}
