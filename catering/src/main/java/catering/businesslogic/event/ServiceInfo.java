package catering.businesslogic.event;

import catering.businesslogic.menu.Menu;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.util.ArrayList;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Arrays;

public class ServiceInfo implements EventItemInfo {
    private int id;
    private String name;
    private Date date;
    private Time timeStart;
    private Time timeEnd;
    private int participants;
    private Menu menu;

    public ServiceInfo(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Menu getMenu() {
        return menu;
    }

    public String toString() {
        return "{" + name + ": " + date + " (" + timeStart + "-" + timeEnd + "), " + participants + " pp." + "}";
    }


    /**
     * Verifica se il servizio ha una lista di cose da fare associata.
     *
     * @return true se il servizio ha una lista di cose da fare, altrimenti false.
     */
    public boolean hasToDoList() {
        final Boolean[] ret = {false};
        PersistenceManager.executeQuery("SELECT * FROM ToDoLists WHERE idService = " + this.getId(), new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                ret[0] = true;
            }
        });
        return ret[0];
    }

    // STATIC METHODS FOR PERSISTENCE

    /**
     * Carica tutte le informazioni sui servizi per un evento specifico.
     *
     * @param event_id L'ID dell'evento.
     * @return Un ArrayList di oggetti ServiceInfo per l'evento.
     */
    public static ArrayList<ServiceInfo> loadServiceInfoForEvent(int event_id) {
        ArrayList<ServiceInfo> result = new ArrayList<>();
        var col = Menu.loadAllMenus();

        String query = "SELECT id, name, service_date, time_start, time_end, expected_participants, approved_menu_id " +
                "FROM Services WHERE event_id = " + event_id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                String s = rs.getString("name");
                ServiceInfo serv = new ServiceInfo(s);

                serv.id = rs.getInt("id");
                serv.date = rs.getDate("service_date");
                serv.timeStart = rs.getTime("time_start");
                serv.timeEnd = rs.getTime("time_end");
                serv.participants = rs.getInt("expected_participants");
                int menuId = rs.getInt("approved_menu_id");

                for(Menu m : col)
                {
                    if(m.getId() == menuId) serv.menu = m;
                }

                result.add(serv);
            }
        });

        return result;
    }

    /**
     * Carica le informazioni di un servizio specifico all'interno di un evento.
     *
     * @param event_id L'ID dell'evento.
     * @param serviceId L'ID del servizio.
     * @return L'oggetto ServiceInfo corrispondente all'ID del servizio, o null se non trovato.
     */
    public static ServiceInfo loadServiceInfoForEvent(int event_id, int serviceId) {
        ArrayList<ServiceInfo> result = loadServiceInfoForEvent(event_id);
        for(ServiceInfo si : result) //Una volta caricati tutti i ServiceInfo per un evento, questo metodo filtra e restituisce solo l'oggetto ServiceInfo corrispondente a serviceId.
        {
            if (si.getId() == serviceId) return si;
        }
        return null;
    }


}
