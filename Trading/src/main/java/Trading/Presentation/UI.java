package Trading.Presentation;

import Trading.Business.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {

    private static Facade facade;
    private static int id_trader;
    private static Scanner sc = new Scanner(System.in);


    private int readOp(){
        System.out.println("Operação a realizar: ");
        return Integer.parseInt(sc.nextLine());
    }


    private void showOps(String[] ops)
    {
        for(String op : ops)
            System.out.println(op);
    }

    private void printSeparador(){
        System.out.println("**************** Trading ****************");
    }

    private void execStartMenu()
    {
        int opcao;
        String[] menu = Menu.getMainMenu();
        do {
            Scanner sc = new Scanner(System.in);
            printSeparador();
            showOps(menu);
            opcao = readOp();
            switch(opcao){
                case 0: break;
                case 1: opcao = 0; id_trader = login(); if(id_trader > 0) execTraderMenu(); break;
                case 2: opcao = 0; registar(); execStartMenu(); break;
                default: System.out.println("Insira uma opção correta");
            }
        } while(opcao != 0);

    }



    private int login() {
        int sair = 0, success = -1;
        String email="", pw="";

        do {
            System.out.println("E-mail: ");
            email = sc.nextLine();

            System.out.println("Password: ");
            pw = sc.nextLine();

            success = facade.login(email, pw);

            if(success == -1){
                System.out.println("E-mail ou Password incorretos. Tente novamente.\n\n1 - Tentar novamente\n0 - Cancelar operação");
                do {
                    sair = Integer.parseInt(sc.nextLine());
                } while(sair != 0 && sair != 1);
            }
        } while(success == -1 && sair != 0);

        return success;
    }

    private void logoff() {
        this.facade.stopNotification(id_trader);
        id_trader = -1;
    }

    private void registar() {
        String email, pw, data_nasc;

        System.out.println("E-mail: ");
        email = sc.nextLine();

        System.out.println("Password: ");
        pw = sc.nextLine();

        System.out.println("Data Nascimento: ");
        data_nasc = sc.nextLine();

        int check_email = facade.registaTrader(email, pw, data_nasc);
        if(check_email == 1) System.out.println("Registado com sucesso!");
        else System.out.println("Erro: Email já está registado.");
    }

    private void execTraderMenu() {
        int opcao;
        String[] menu = Menu.getTraderMenu();
        do {
            printSeparador();
            showOps(menu);
            opcao = readOp();
            switch(opcao){
                case 0: logoff();  break;
                case 1: opcao = 0; execMercado(); break;
                case 2: opcao = 0; execPortfolio(); break;
                case 3: opcao = 0; execHistorico(); break;
                case 4: opcao = 0; execSaldo(); break;
                case 5: opcao = 0; execWatchlist(); break;
                case 6: opcao = 0; execAdicionarFundos(); break;
                case 7: opcao = 0; execLevantarFundos(); break;
                default: System.out.println("Insira uma opção correta");
            }
        } while(opcao != 0);

        execStartMenu();
    }


    private void execMercado() {
        List<Ativo> ativos = facade.getMercado();

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        int i = 1, opcao;
        System.out.println("+-----+-------+--------------+--------------+-------------+----------+");
        System.out.println("|  #  |   ID  | Preço Compra |  Preço Venda |     Nome    |   Tipo   |");
        System.out.println("+-----+-------+--------------+--------------+-------------+----------+");
        for(Ativo a : ativos)
        {
            System.out.print('|');
            System.out.print(String.format(" %-4s", Integer.toString(i)));
            System.out.print("|");
            System.out.print(String.format(" %-6s", a.getID()));
            System.out.print("|");
            System.out.print(String.format(" %-13s", df.format(a.getPrecoCompra())));
            System.out.print("|");
            System.out.print(String.format(" %-13s", df.format(a.getPrecoVenda())));
            System.out.print("|");
            System.out.print(String.format(" %-12s", a.getNome()));
            System.out.print("|");
            if(a instanceof Acao)
            {
                System.out.print(String.format(" %-9s", "Ação"));
            }
            else if(a instanceof Comodity)
            {
                System.out.print(String.format(" %-9s", "Comodity"));
            }
            else if(a instanceof Crypto)
            {
                System.out.print(String.format(" %-9s", "Crypto"));
            }
            System.out.println("|");

            i++;
        }

        System.out.println("+-----+-------+--------------+--------------+-------------+----------+");

        System.out.println("X: Abrir CFD relativo ao Ativo X");
        System.out.println("WX: Adicionar o ativo X à Watchlist");
        System.out.println("0: Retroceder");

        do {
            String op = sc.nextLine();
            // VERIFICA SE É PARA ADICIONAR À WISHLIST OU SE APENAS É PARA ABRIR UM CFD
            if(op.charAt(0) == 'W'){
                String op2 = op.replace('W', '0');
                System.out.println(op2);
                opcao = Integer.parseInt(op2);
                if (isBetween(opcao, 1, ativos.size())){
                    this.facade.adicionaWatchlist(id_trader, ativos.get(opcao-1));
                    System.out.println("Ativo adicionado com sucesso à wishlist");
                    opcao = 0;
                }
                else System.out.println("Insira uma opção correta");
            }
            else {
                opcao = Integer.parseInt(op);

                if (isBetween(opcao, 1, ativos.size())){
                    execAbrirCFD(ativos.get(opcao-1));
                    opcao = 0;
                }
                else System.out.println("Insira uma opção correta");
            }


        } while(opcao != 0);

        execTraderMenu();
    }


    private boolean isBetween(int opcao, int min, int max) {
        if (min <= opcao && opcao <= max) return true;
        else return false;
    }

    private void execAbrirCFD(Ativo ativo) {
        System.out.println("A abrir CFD para " + ativo.getNome());

        System.out.println("Tipo: \n1 - Short \n2 - Long");
        int _tipo = Integer.parseInt(sc.nextLine());
        String tipo = "";
        float preco = 0;
        if(_tipo == 1) {
            preco = ativo.getPrecoVenda();
            tipo = "short";
        }
        else if(_tipo == 2){
            preco = ativo.getPrecoCompra();
            tipo = "long";
        }

        System.out.println("Unidades (> 0):");
        float unidades = Integer.parseInt(sc.nextLine());

        System.out.println("Stop loss (Max: + "+ preco*unidades + "€, 0 para ignorar):");
        float stop_loss = Float.parseFloat(sc.nextLine());

        System.out.println("Take profit (Min: + "+ preco*unidades +"€, 0 para ignorar):");
        float take_profit = Float.parseFloat(sc.nextLine());

        int check_saldo = facade.abrirCFD(id_trader, ativo.getID(), tipo, unidades, stop_loss, take_profit);

        if(check_saldo == 1){
            System.out.println("CFD aberto com sucesso.");
        }
        else{
            System.out.println("Erro: Não possui saldo suficiente para efetuar a operação.");
        }
    }

    private void execWatchlist(){
        List<Ativo> watchlist = facade.getWatchlist();

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        int i = 1;
        System.out.println("+-----+-------+--------------+--------------+-------------+----------+");
        System.out.println("|  #  |   ID  | Preço Compra |  Preço Venda |     Nome    |   Tipo   |");
        System.out.println("+-----+-------+--------------+--------------+-------------+----------+");
        for(Ativo a : watchlist){
            System.out.print('|');
            System.out.print(String.format(" %-4s", Integer.toString(i)));
            System.out.print("|");
            System.out.print(String.format(" %-6s", a.getID()));
            System.out.print("|");
            System.out.print(String.format(" %-13s", df.format(a.getPrecoCompra())));
            System.out.print("|");
            System.out.print(String.format(" %-13s", df.format(a.getPrecoVenda())));
            System.out.print("|");
            System.out.print(String.format(" %-12s", a.getNome()));
            System.out.print("|");
            if(a instanceof Acao)
            {
                System.out.print(String.format(" %-9s", "Ação"));
            }
            else if(a instanceof Comodity)
            {
                System.out.print(String.format(" %-9s", "Comodity"));
            }
            else if(a instanceof Crypto)
            {
                System.out.print(String.format(" %-9s", "Crypto"));
            }
            System.out.println("|");
            i++;
        }

        System.out.println("+-----+-------+--------------+--------------+-------------+----------+");
        System.out.println("Size: " + watchlist.size());
        System.out.println("X: Remover Ativo X da watchlist");
        System.out.println("0: Retroceder");

        int opcao;
        do {
            opcao = readOp();
            if (watchlist.size() > 0 && watchlist.size() >= opcao ){
                if(opcao > 0){
                    facade.removeWatchlist(id_trader, watchlist.get(opcao-1));
                    System.out.println("Removido com sucesso");
                    opcao = 0;
                }
            }
            else System.out.println("Insira uma opção correta");

        } while(opcao != 0);

        execTraderMenu();
    }


    private void execPortfolio() {
        Map<Integer, CFD> portfolio = facade.getPortfolioTrader(id_trader).stream().collect(Collectors.toMap(CFD :: getID, cfd -> cfd));
        Map<String, Ativo> mercado = facade.getMercado().stream().collect(Collectors.toMap(Ativo :: getID, ativo -> ativo));

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        System.out.println("+------+-----------+---------+-----------+-------------+------------+-------------+");
        System.out.println("|  Id  | Total (€) | Unidade | Stop Loss | Take Profit | Live Venda | Live Compra |");
        System.out.println("+------+-----------+---------+-----------+-------------+------------+-------------+");
        //System.out.println("ID \t| Total (€) \t| €/Unidade \t| Stop Loss \t| Take Profit \t| Live Venda \t| Live Compra");
        for(CFD cfd : portfolio.values())
        {
            Ativo ativo_cfd = mercado.get(cfd.getAtivo().getID());
            //System.out.println(cfd.toString() + " \t\t\t| " + ativo_cfd.getPrecoVenda() + " \t\t| " + ativo_cfd.getPrecoCompra());
            System.out.print('|');
            System.out.print(String.format(" %-5s", Integer.toString(cfd.getID())));
            System.out.print("|");
            System.out.print(String.format(" %-10s", df.format(cfd.getTotal())));
            System.out.print("|");
            System.out.print(String.format(" %-8s", df.format(cfd.getUnidades())));
            System.out.print("|");
            System.out.print(String.format(" %-10s", df.format(cfd.getStopLoss())));
            System.out.print("|");
            System.out.print(String.format(" %-12s", df.format(cfd.getTakeProfit())));
            System.out.print("|");
            System.out.print(String.format(" %-11s", df.format(ativo_cfd.getPrecoVenda())));
            System.out.print("|");
            System.out.print(String.format(" %-12s", df.format(ativo_cfd.getPrecoCompra())));
            System.out.println("|");
        }

        System.out.println("+------+-----------+---------+-----------+-------------+------------+-------------+");

        System.out.println("X: Fechar CFD relativo ao Ativo X");
        System.out.println("0: Retroceder");

        int opcao;
        do {
            opcao = readOp();
            if (portfolio.get(opcao) != null){
                facade.fecharCFD(opcao);
                System.out.println("Fechado com sucesso");
                opcao = 0;
            }
            else System.out.println("Insira uma opção correta");

        } while(opcao != 0);

        execTraderMenu();
    }

    private void execHistorico() {
        List<CFD> historico = facade.getHistoricoTrader(id_trader);

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        System.out.println("+------+-----------+---------+-----------+-------------+");
        System.out.println("|  Id  | Total (€) | Unidade | Stop Loss | Take Profit |");
        System.out.println("+------+-----------+---------+-----------+-------------+");
        for(CFD cfd : historico){
            System.out.print('|');
            System.out.print(String.format(" %-5s", Integer.toString(cfd.getID())));
            System.out.print("|");
            System.out.print(String.format(" %-10s", df.format(cfd.getTotal())));
            System.out.print("|");
            System.out.print(String.format(" %-8s", df.format(cfd.getUnidades())));
            System.out.print("|");
            System.out.print(String.format(" %-10s", df.format(cfd.getStopLoss())));
            System.out.print("|");
            System.out.print(String.format(" %-12s", df.format(cfd.getTakeProfit())));
            System.out.println("|");
            //System.out.println(cfd.toString());
        }

        System.out.println("+------+-----------+---------+-----------+-------------+");
        execTraderMenu();
    }

    private void execSaldo() {
        float saldo = facade.getSaldoTrader(id_trader);

        System.out.println("O seu saldo é: " + saldo + "€");

        execTraderMenu();
    }

    private void execAdicionarFundos() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Quantidade a depositar:");
        float valor = Float.parseFloat(sc.nextLine());
        facade.adicionaFundos(id_trader, valor);
        System.out.println("Fundos adicionados com sucesso!");
        execTraderMenu();
    }

    private void execLevantarFundos() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Quantidade a levantar:");
        float valor = Float.parseFloat(sc.nextLine());
        facade.levantaFundos(id_trader, valor);
        System.out.println("Fundos levantados com sucesso!");
        execTraderMenu();
    }


    public static void main(String[] args) {
        UI ui = new UI();
		facade = new Facade(ui);

		ui.execStartMenu();
	}

    public void notifyAtivoChange(Ativo a, float percentage) {
        percentage *= 100.0f;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        System.out.println("O valor do Ativo " + a.getNome() + " variou " + (percentage > 0 ? "+" : "") + df.format(percentage) + "%!");
    }
}