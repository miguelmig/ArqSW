package Trading.Data;

import Trading.Business.*;

import java.sql.*;
import java.util.List;

public class TraderDAO implements DAO<String, Trader> {
    @Override
    public void put(String email, Trader t) {
        try {
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/trading","root","123456");

            PreparedStatement s = con.prepareStatement("INSERT INTO trader (email, password, data_nasc, saldo) VALUES (?, ?, ?, ?)");
            s.setString(1, email);
            s.setString(2, t.getPassword());
            s.setString(3, t.getDataNasc());
            s.setFloat(4, t.getSaldo());

            s.execute();

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Trader get(String email) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trading","root","123456");
            Trader t = new Trader();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM trader WHERE email = ?");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                //t.setID(rs.getInt("id_trader"));
                t.setEmail(rs.getString("email"));
                t.setPassword(rs.getString("password"));
                t.setDataNasc(rs.getString("data_nasc"));
                t.setSaldo(rs.getFloat("saldo"));
                // FIXME: 10/11/2019 FALTAM OS CFDS
                return t;
            }
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return new Trader();
        }

        return new Trader();
    }

    @Override
    public List<Trader> list() {
        return null;
    }

    @Override
    public void remove(String email) {

    }

    @Override
    public int size() {
        return 0;
    }
}