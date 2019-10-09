package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Engine implements Runnable {

    private Connection connection;

    public Engine(Connection connection) {
        this.connection = connection;
    }

    public void run() {
        try {
            this.getVillainsNames();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // problem 1. Get Villains Names
    private void getVillainsNames() throws SQLException {
        String query = "SELECT v.name, count(v2.minion_id) FROM villains v JOIN minions_villains v2 on v.id = v2.villain_id GROUP BY v.name HAVING count(v2.minion_id) > ? ORDER BY count(v2.minion_id) DESC";
        PreparedStatement preparedStatement = this.connection.prepareStatement(query);

        preparedStatement.setInt(1, 10);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            System.out.println(String.format("%s %d", resultSet.getString(1),
                    resultSet.getInt(2)));
        }
    }
}
