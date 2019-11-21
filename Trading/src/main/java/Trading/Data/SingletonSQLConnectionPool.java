package Trading.Data;

import java.sql.*;

public class SingletonSQLConnectionPool
{
    private Connection[] conns;
    private static final int MAX_CONNECTIONS = 10;
    private int current_conn = 0;

    // Database configurations, ideally they would be hidden in a configuration file and not on code
    private final String uri = "jdbc:mysql://localhost:3306/trading";
    private String user = "root";
    private String password = "123456";

    private static SingletonSQLConnectionPool instance = null;

    // It's a singleton so we hide the constructor
    private SingletonSQLConnectionPool()
    {
        this.conns = new Connection[MAX_CONNECTIONS];
        this.openConnections();
    }

    private void openConnections()
    {
        try
        {
            for(int i = 0; i < MAX_CONNECTIONS; i++)
            {
                this.conns[i] = DriverManager.getConnection(this.uri, this.user, this.password);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }


    public static SingletonSQLConnectionPool getInstance() {
        if(instance == null)
        {
            instance = new SingletonSQLConnectionPool();
        }
        return instance;
    }

    public DBConnection getConnection()
    {
        Connection c = this.conns[this.current_conn];
        try {
            if (c == null || c.isClosed())
            {
                this.conns[this.current_conn] = DriverManager.getConnection(this.uri, this.user, this.password);
            }
            SQLConnection conn_wraped = new SQLConnection(this.conns[this.current_conn]);
            this.current_conn = (this.current_conn + 1) % MAX_CONNECTIONS;
            return conn_wraped;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            try
            {
                this.conns[this.current_conn] = DriverManager.getConnection(this.uri, this.user, this.password);
                SQLConnection conn_wraped = new SQLConnection(this.conns[this.current_conn]);
                this.current_conn = (this.current_conn + 1) % MAX_CONNECTIONS;
                return conn_wraped;
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
                return null;
            }
        }
    }

}