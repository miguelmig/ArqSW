package Trading.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLConnection implements DBConnection
{
    private Connection conn;
    SQLConnection(Connection conn)
    {
        this.conn = conn;
    }

    @Override
    public ResultSet returnQuery(PreparedStatement s) {
        try {
            return s.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
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

}
