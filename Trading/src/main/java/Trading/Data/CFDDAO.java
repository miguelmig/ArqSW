package Trading.Data;

import Trading.Business.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CFDDAO implements DAO<Integer, CFD> {
    @Override
    public void put(Integer integer, CFD o) {
        try {
            DBConnection sql = ConnectionManager.getConnection();

            PreparedStatement s = sql.prepareStatement("INSERT INTO cfd (id_cfd, stop_loss, take_profit, unidades, total, closed, ativo_id, trader_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            s.setInt(1, integer);
            s.setFloat(2, o.getStopLoss());
            s.setFloat(3, o.getTakeProfit());
            s.setFloat(4, o.getUnidades());
            s.setFloat(5, o.getTotal());
            s.setBoolean(6, !o.isAberto());
            s.setString(7, o.getAtivo().getID());
            s.setInt(8, o.getTrader().getID());

            sql.query(s);

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public CFD get(Integer integer) {
        try {
            DBConnection sql = ConnectionManager.getConnection();
            PreparedStatement s = sql.prepareStatement("SELECT * FROM cfd WHERE id_cfd = ?");
            s.setInt(1, integer);

            ResultSet rs = sql.returnQuery(s);
            if(rs.next())
            {
                float stop_loss = rs.getFloat("stop_loss");
                float take_profit = rs.getFloat("take_profit");
                float unidades = rs.getFloat("unidades");
                float total = rs.getFloat("total");
                boolean closed = rs.getBoolean("closed");
                String id_ativo = rs.getString("ativo_id");
                int id_trader = rs.getInt("trader_id");

                // Como retornar agora um CFD?
                // FIXME 17/11/2019: COMO INSTANCIAR OS CFDS?
            }
            else
            {
                return null;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<CFD> list() {
        return null;
    }

    @Override
    public void remove(Integer integer) {

    }

    @Override
    public int size() {
        return 0;
    }
}