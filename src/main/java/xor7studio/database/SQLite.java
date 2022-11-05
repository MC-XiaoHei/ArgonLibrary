package xor7studio.database;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLite {
    public static void init(){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static Connection getConnection(String dbFileName){
        dbFileName=dbFileName.replace(".db","");
        try {
            return DriverManager.getConnection(String.format("jdbc:sqlite:%s.db",dbFileName));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Statement getStatement(@NotNull Connection connection){
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
