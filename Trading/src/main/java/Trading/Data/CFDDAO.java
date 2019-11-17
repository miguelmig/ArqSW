package Trading.Data;

import Trading.Business.*;
import Trading.Business.Long;
import Trading.Business.Short;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CFDDAO implements DAO<Integer, CFD> {
    @Override
    public void put(Integer id_cfd, CFD cfd) {
        try {
            DBConnection sql = ConnectionManager.getConnection();

            PreparedStatement s = sql.prepareStatement("INSERT INTO cfd (id_cfd, stop_loss, take_profit, unidades, total, closed, ativo_id, trader_id, is_long) " +
                                                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                                                            "ON DUPLICATE KEY UPDATE closed = ?");
            s.setInt(1, id_cfd);
            s.setFloat(2, cfd.getStopLoss());
            s.setFloat(3, cfd.getTakeProfit());
            s.setFloat(4, cfd.getUnidades());
            s.setFloat(5, cfd.getTotal());
            s.setBoolean(6, !cfd.isAberto());
            s.setString(7, cfd.getAtivo().getID());
            s.setInt(8, cfd.getTrader().getID());
            if(cfd instanceof Long) s.setInt(9, 1);
            else if (cfd instanceof Short) s.setInt(9, 0);

            s.setBoolean(10, !cfd.isAberto());

            sql.query(s);

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public CFD get(Integer id) {
        try {
            DBConnection sql = ConnectionManager.getConnection();
            PreparedStatement s = sql.prepareStatement("SELECT * FROM cfd WHERE id_cfd = ?");
            s.setInt(1, id);

            ResultSet rs = sql.returnQuery(s);
            if(rs.next()) {
                float stop_loss = rs.getFloat("stop_loss");
                float take_profit = rs.getFloat("take_profit");
                float unidades = rs.getFloat("unidades");
                float total = rs.getFloat("total");
                boolean closed = rs.getBoolean("closed");
                String id_ativo = rs.getString("ativo_id");
                Ativo ativo = this.getAtivo(id_ativo);
                int id_trader = rs.getInt("trader_id");
                Trader trader = this.getTrader(id_trader);
                boolean is_long = rs.getBoolean("is_long");

                if(is_long) return new Long(id , stop_loss, take_profit, unidades, total, ativo, trader);
                else return new Short(id , stop_loss, take_profit, unidades, total, ativo, trader);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
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
        try {
            DBConnection sql = ConnectionManager.getConnection();

            PreparedStatement s = sql.prepareStatement("SELECT COUNT(*) FROM cfd");
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


    private Trader getTrader(int id){
        TraderDAO traderDAO = new TraderDAO();
        return traderDAO.get(id);
    }

    private Ativo getAtivo(String id_ativo) {
        AtivoDAO ativoDAO = new AtivoDAO();
        return ativoDAO.get(id_ativo);
    }
}