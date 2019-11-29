package Trading.Data;

import Trading.Business.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TraderDAO implements DAO<Integer, Trader> {
    @Override
    public void put(Integer id, Trader trader) {
        try {
            DBConnection con = ConnectionManager.getConnection();

            PreparedStatement s = con.prepareStatement("INSERT INTO trader (id_trader, email, password, data_nasc, saldo) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE saldo = ?");
            s.setInt(1, id);
            s.setString(2, trader.getEmail());
            s.setString(3, trader.getPassword());
            s.setString(4, trader.getDataNasc());
            s.setFloat(5, trader.getSaldo());
            s.setFloat(6, trader.getSaldo());

            con.query(s);
            PreparedStatement s_delete = con.prepareStatement("DELETE FROM trader_watchlist WHERE id_trader = ? ");
            s_delete.setInt(1, id);
            con.query(s_delete);

            for(Ativo a : trader.getWatchlist())
            {
                System.err.println("Saving watchlist asset: " + a.getNome());
                PreparedStatement s2 = con.prepareStatement("INSERT IGNORE INTO trader_watchlist (id_trader, id_ativo) VALUES (?, ?)");
                s2.setInt(1, id);
                s2.setString(2, a.getID());
                con.query(s2);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Trader get(Integer id_trader) {
        try {
            DBConnection con = ConnectionManager.getConnection();
            Trader trader = new Trader();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM trader WHERE id_trader = ?");
            ps.setInt(1, id_trader);

            ResultSet rs = con.returnQuery(ps);
            if(rs.next()){
                trader.setID(rs.getInt("id_trader"));
                trader.setEmail(rs.getString("email"));
                trader.setPassword(rs.getString("password"));
                trader.setDataNasc(rs.getString("data_nasc"));
                trader.setSaldo(rs.getFloat("saldo"));

                // FIXME: 29/11/2019 REFACTOR : método getCurrentCFDs
                PreparedStatement ps2 = con.prepareStatement("SELECT * FROM cfd WHERE trader_id = ?");
                ps2.setInt(1, rs.getInt("id_trader"));
                ResultSet cfds_rs = con.returnQuery(ps2);
                List<CFD> cfds = new ArrayList<>();
                while(cfds_rs.next()) {
                    int id_cfd = cfds_rs.getInt("id_cfd");
                    float stop_loss = cfds_rs.getFloat("stop_loss");
                    float take_profit = cfds_rs.getFloat("take_profit");
                    float unidades = cfds_rs.getFloat("unidades");
                    float total = cfds_rs.getFloat("total");
                    boolean closed = cfds_rs.getBoolean("closed");
                    String id_ativo = cfds_rs.getString("ativo_id");
                    Ativo ativo = this.getAtivo(id_ativo);
                    String tipo = cfds_rs.getString("tipo");
                    Date fecho = null;
                    if(closed)
                    {
                        fecho = cfds_rs.getDate("data_fecho");
                    }

                    CreatorCFD creatorCFD = new CreatorCFD();
                    CFD cfd = creatorCFD.loadMethod(id_cfd, trader, ativo, unidades, total, tipo, !closed, stop_loss, take_profit, fecho);

                    cfds.add(cfd);
                }

                trader.setCurrentCFDs(cfds);
                trader.setWatchlist(getAtivoWatchList(id_trader));
                return trader;
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return new Trader();
        }

        return new Trader();
    }

    @Override
    public List<Trader> list() {
        try {
            List<Trader> traders = new ArrayList<>();
            DBConnection con = ConnectionManager.getConnection();

            // FIXME: 29/11/2019 ir buscar so os ids e depois fazer get(id) para nao ser um método gigante?
            PreparedStatement ps = con.prepareStatement("SELECT * FROM trader");

            ResultSet rs = con.returnQuery(ps);
            while(rs.next())
            {
                Trader trader = new Trader();
                trader.setID(rs.getInt("id_trader"));
                trader.setEmail(rs.getString("email"));
                trader.setPassword(rs.getString("password"));
                trader.setDataNasc(rs.getString("data_nasc"));
                trader.setSaldo(rs.getFloat("saldo"));

                PreparedStatement ps2 = con.prepareStatement("SELECT * FROM cfd WHERE trader_id = ?");
                ps2.setInt(1, rs.getInt("id_trader"));
                ResultSet cfds_rs = con.returnQuery(ps2);
                List<CFD> cfds = new ArrayList<>();
                while(cfds_rs.next()) {
                    int id_cfd = cfds_rs.getInt("id_cfd");
                    float stop_loss = cfds_rs.getFloat("stop_loss");
                    float take_profit = cfds_rs.getFloat("take_profit");
                    float unidades = cfds_rs.getFloat("unidades");
                    float total = cfds_rs.getFloat("total");
                    boolean closed = cfds_rs.getBoolean("closed");
                    String id_ativo = cfds_rs.getString("ativo_id");
                    Ativo ativo = this.getAtivo(id_ativo);
                    String tipo = cfds_rs.getString("tipo");
                    Date fecho = null;
                    if(closed)
                    {
                        fecho = cfds_rs.getDate("data_fecho");
                    }

                    CreatorCFD creatorCFD = new CreatorCFD();
                    CFD cfd = creatorCFD.loadMethod(id_cfd, trader, ativo, unidades, total, tipo, !closed, stop_loss, take_profit, fecho);

                    cfds.add(cfd);
                }

                trader.setCurrentCFDs(cfds);
                traders.add(trader);
            }

            return traders;

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void remove(Integer id) {
        try
        {
            DBConnection con = ConnectionManager.getConnection();

            CFDDAO cfd_dao = new CFDDAO();
            PreparedStatement ps = con.prepareStatement("SELECT id_cfd FROM cfd WHERE trader_id = ?");
            ps.setInt(1, id);
            ResultSet cfds_rs = con.returnQuery(ps);
            while(cfds_rs.next()) {
                int id_cfd = cfds_rs.getInt("id_cfd");
                cfd_dao.remove(id_cfd);
            }

            PreparedStatement s_delete = con.prepareStatement("DELETE FROM trader_watchlist WHERE id_trader = ? ");
            s_delete.setInt(1, id);
            con.query(s_delete);


            PreparedStatement ps2 = con.prepareStatement("DELETE FROM trader WHERE id_trader = ? ");
            ps2.setInt(1, id);
            con.query(ps2);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int size() {
        try {
            DBConnection sql = ConnectionManager.getConnection();

            PreparedStatement s = sql.prepareStatement("SELECT COUNT(*) FROM trader");
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
    private Ativo getAtivo(String id_ativo) {
        AtivoDAO ativoDAO = new AtivoDAO();
        return ativoDAO.get(id_ativo);
    }

    public Trader getByEmail(String email) {
        try {
            DBConnection con = ConnectionManager.getConnection();
            Trader trader = new Trader();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM trader WHERE email = ?");

            ps.setString(1, email);


            ResultSet rs = con.returnQuery(ps);
            if (rs.next()) {
                trader.setID(rs.getInt("id_trader"));
                trader.setEmail(rs.getString("email"));
                trader.setPassword(rs.getString("password"));
                trader.setDataNasc(rs.getString("data_nasc"));
                trader.setSaldo(rs.getFloat("saldo"));
            }

            return trader;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<CFD> getClosedCFD(Integer id)
    {
        Trader trader = get(id);
        try {
            DBConnection sql = ConnectionManager.getConnection();
            PreparedStatement ps = sql.prepareStatement("SELECT * FROM cfd WHERE trader_id = ? AND closed = 1 ORDER BY data_fecho DESC;");
            ps.setInt(1, id);
            ResultSet cfds_rs = sql.returnQuery(ps);
            List<CFD> cfds = new ArrayList<>();
            while (cfds_rs.next()) {
                int id_cfd = cfds_rs.getInt("id_cfd");
                float stop_loss = cfds_rs.getFloat("stop_loss");
                float take_profit = cfds_rs.getFloat("take_profit");
                float unidades = cfds_rs.getFloat("unidades");
                float total = cfds_rs.getFloat("total");
                boolean closed = cfds_rs.getBoolean("closed");
                String id_ativo = cfds_rs.getString("ativo_id");
                Ativo ativo = this.getAtivo(id_ativo);
                String tipo = cfds_rs.getString("tipo");
                Date fecho = null;
                if (closed) {
                    fecho = cfds_rs.getDate("data_fecho");
                }

                CreatorCFD creatorCFD = new CreatorCFD();
                CFD cfd = creatorCFD.loadMethod(id_cfd, trader, ativo, unidades, total, tipo, !closed, stop_loss, take_profit, fecho);

                cfds.add(cfd);
            }
            return cfds;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<Ativo> getAtivoWatchList(Integer id_trader)
    {
        try {
            DBConnection con = ConnectionManager.getConnection();
            List<Ativo> ativos = new ArrayList<>();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM trader_watchlist WHERE id_trader = ?");
            ps.setInt(1, id_trader);
            ResultSet rs = con.returnQuery(ps);
            while (rs.next()) {
                String id_ativo = rs.getString("id_ativo");
                AtivoDAO ativoDAO = new AtivoDAO();
                Ativo ativo = ativoDAO.get(id_ativo);
                ativos.add(ativo);
            }

            return ativos;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}