package Trading.Data;

public class ConnectionManager
{

    public static DBConnection getConnection()
    {
        return SingletonSQLConnectionPool.getInstance().getConnection();
    }
}
