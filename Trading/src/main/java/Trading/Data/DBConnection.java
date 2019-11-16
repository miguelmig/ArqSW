package Trading.Data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface DBConnection
{
    public void openConnection();

    public void query(PreparedStatement s);

    public ResultSet returnQuery(PreparedStatement s);

    public void closeConnection();

    public PreparedStatement prepareStatement(String s);
}