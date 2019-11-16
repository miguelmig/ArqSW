package Trading.Data;

import java.sql.*;

public class SingletonSQLConnection implements DBConnection
{
    private Connection conn;

    // Database configurations, ideally they would be hidden in a configuration file and not on code
    private final String uri = "jdbc:mysql://localhost:3306/trading";
    private String user = "root";
    private String password = "123456";

    private static SingletonSQLConnection instance = null;

    // It's a singleton so we hide the constructor
    private SingletonSQLConnection()
    {
        this.openConnection();
    }

    @Override
    public void openConnection()
    {
        try
        {
            this.conn = DriverManager.getConnection(this.uri, this.user, this.password);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public ResultSet returnQuery(PreparedStatement s) {
        return null;
    }

    @Override
    public void closeConnection()
    {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement prepareStatement(String format)
    {
        try {
            return this.conn.prepareStatement(format);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void query(PreparedStatement s)
    {
        try {
            s.execute();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static SingletonSQLConnection getInstance() {
        if(instance == null)
        {
            instance = new SingletonSQLConnection();
        }
        return instance;
    }

}