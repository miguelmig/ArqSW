package Trading.Data;

public class ConnectionManager
{
    public static DBConnection getConnection()
    {
        return SingletonSQLConnection.getInstance();
    }
}
