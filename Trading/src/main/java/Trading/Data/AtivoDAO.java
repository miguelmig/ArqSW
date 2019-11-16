package Trading.Data;

import Trading.Business.*;
import com.mysql.cj.jdbc.ConnectionImpl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AtivoDAO implements DAO<String, Ativo> {
    @Override
    public void put(String id, Ativo o) {
        try {
            DBConnection sql = ConnectionManager.getConnection();

            PreparedStatement s = sql.prepareStatement("INSERT INTO ativo (id_ativo, nome, comodity) VALUES (?, ?, ?)");
            s.setString(1, id);
            s.setString(2, o.getNome());
            if (o instanceof Comodity) {
                s.setInt(3, 1);
            } else if (o instanceof Acao) {
                s.setInt(3, 0);
            } else {
                // Ativo não implementado ainda
                System.err.println("Este tipo de ativo não está implementado ainda");
                s.cancel();
                return;
            }

            sql.query(s);

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Ativo get(String id) {
        return null;
    }

    @Override
    public List<Ativo> list() {
        return null;
    }

    @Override
    public void remove(String id)
    {

    }
}