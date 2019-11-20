package Trading.Data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface DBConnection
{
    void query(PreparedStatement s);

    ResultSet returnQuery(PreparedStatement s);

    PreparedStatement prepareStatement(String s);
}