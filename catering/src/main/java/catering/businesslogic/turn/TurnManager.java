package catering.businesslogic.turn;

public class TurnManager {
    private static TurnTable turnTable;

    static TurnTable getInstance()
    {
        turnTable = TurnTable.getInstance();
        return turnTable;
    }

    public TurnTable getTurnTable() {
        return TurnTable.getInstance();
    }


}
