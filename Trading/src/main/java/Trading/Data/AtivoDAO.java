package Trading.Data;

import Trading.Business.*;
import com.mysql.cj.jdbc.ConnectionImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Ativo get(String id) {
        try {
            DBConnection sql = ConnectionManager.getConnection();

            PreparedStatement s = sql.prepareStatement("SELECT * FROM ativo WHERE id_ativo = ?");
            s.setString(1, id);

            ResultSet rs = sql.returnQuery(s);
            if (rs.next()) {
                String nome = rs.getString("nome");
                boolean comodity = rs.getBoolean("comodity");
                if(comodity)
                    return new Comodity(id, nome, 0, 0);
                else
                    return new Acao(id, nome, 0, 0);
            }
            else {
                return null;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Ativo> list() {
        try {
            List<Ativo> ativos = new ArrayList<>();
            DBConnection sql = ConnectionManager.getConnection();

            PreparedStatement s = sql.prepareStatement("SELECT * FROM ativo");

            ResultSet rs = sql.returnQuery(s);
            while (rs.next()){
                String id = rs.getString("id_ativo");
                String nome = rs.getString("nome");
                boolean comodity = rs.getBoolean("comodity");
                if(comodity)
                    ativos.add(new Comodity(id, nome, 0, 0));
                else
                    ativos.add(new Acao(id, nome, 0, 0));
            }

            return ativos;
        }
        catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void remove(String id)
    {
        try
        {
            DBConnection sql = ConnectionManager.getConnection();

            PreparedStatement s = sql.prepareStatement("DELETE FROM ativo WHERE id_ativo = ?");
            s.setString(1, id);

            sql.query(s);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int size() {
        try {
            DBConnection sql = ConnectionManager.getConnection();

            PreparedStatement s = sql.prepareStatement("SELECT COUNT(*) FROM ativo");
            ResultSet rs = sql.returnQuery(s);
            if (rs.next()) {
                return rs.getInt(1);
            }
            else {
                return 0;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}