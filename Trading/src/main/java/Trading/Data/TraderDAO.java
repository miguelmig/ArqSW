package Trading.Data;

import Trading.Business.*;

import java.sql.*;
import java.util.ArrayList;
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
            DBConnection con = ConnectionManager.getConnection();
            Trader t = new Trader();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM trader WHERE email = ?");
            ps.setString(1, email);

            ResultSet rs = con.returnQuery(ps);
            if(rs.next()){
                //t.setID(rs.getInt("id_trader"));
                t.setEmail(rs.getString("email"));
                t.setPassword(rs.getString("password"));
                t.setDataNasc(rs.getString("data_nasc"));
                t.setSaldo(rs.getFloat("saldo"));

                PreparedStatement ps2 = con.prepareStatement("SELECT * FROM cfd WHERE trader_id = ?");
                ps2.setInt(1, rs.getInt("id_trader"));
                ResultSet cfds_rs = con.returnQuery(ps2);
                List<CFD> cfds = new ArrayList<>();
                while(cfds_rs.next())
                {
                    float stop_loss = rs.getFloat("stop_loss");
                    float take_profit = rs.getFloat("take_profit");
                    float unidades = rs.getFloat("unidades");
                    float total = rs.getFloat("total");
                    boolean closed = rs.getBoolean("closed");
                    String id_ativo = rs.getString("ativo_id");
                    int id_trader = rs.getInt("trader_id");

                    // FIXME 17/11/2019: COMO INSTANCIAR OS CFDS?
                }

                return t;
            }


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
        try
        {
            DBConnection sql = ConnectionManager.getConnection();

            PreparedStatement s = sql.prepareStatement("SELECT COUNT(*) FROM trader");
            ResultSet rs = sql.returnQuery(s);
            if (rs.next())
            {
                Integer count = rs.getInt(0);
                return count;
            }
            else {
                return 0;
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }

    }
}