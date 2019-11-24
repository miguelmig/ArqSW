package Trading.Presentation;

import Trading.Business.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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


    private void showOps(String[] ops){
        for(String op : ops)
            System.out.println(op);
    }

    private void printSeparador(){
        System.out.println("**************** Trading ****************");
    }

    private void execStartMenu(){
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
                    sair = sc.nextInt();
                } while(sair != 0 && sair != 1);
            }
        } while(success == -1 && sair != 0);

        return success;
    }

    private void registar() {
        int sair = 0, success = -1;
        String email, pw, data_nasc;

        do {
            System.out.println("E-mail: ");
            email = sc.nextLine();

            System.out.println("Password: ");
            pw = sc.nextLine();

            System.out.println("Data Nascimento: ");
            data_nasc = sc.nextLine();

            facade.registaTrader(email, pw, data_nasc);

            /*
            if(success == -1){
                System.out.println("E-mail já utilizado. Tente novamente.\n\n1 - Tentar novamente\n0 - Cancelar operação");
                do {
                    sair = sc.nextInt();
                } while(sair != 0 && sair != 1);
            }

             */
        } while(success == -1 && sair != 0);

    }

    private void execTraderMenu() {
        int opcao;
        String[] menu = Menu.getTraderMenu();
        do {
            printSeparador();
            showOps(menu);
            opcao = readOp();
            switch(opcao){
                case 0: id_trader = -1;  break;
                case 1: opcao = 0; execMercado(); break;
                case 2: opcao = 0; execPortfolio(); break;
                case 3: opcao = 0; execHistorico(); break;
                case 4: opcao = 0; execSaldo(); break;
                case 5: opcao = 0; execAdicionarFundos(); break;
                case 6: opcao = 0; execLevantarFundos(); break;
                default: System.out.println("Insira uma opção correta");
            }
        } while(opcao != 0);

        execStartMenu();
    }


    private void execMercado() {
        List<Ativo> ativos = facade.getMercado();

        int i = 1, opcao;
        for(Ativo a : ativos) {
            System.out.print(i + ": " + a.toString());
            i++;
        }

        System.out.println("X: Abrir CFD relativo ao Ativo X");
        System.out.println("0: Retroceder");

        do {
            opcao = readOp();
            if (isBetween(opcao, 1, ativos.size())){
                execAbrirCFD(ativos.get(opcao-1));
                opcao = 0;
            }
            else System.out.println("Insira uma opção correta");

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

        // FIXME: 23/11/2019 verificar se tem dinheiro, retorna 1 se for válido, 0 se não tiver dinheiro
        facade.abrirCFD(id_trader, ativo.getID(), tipo, unidades, stop_loss, take_profit);

    }

    private void execPortfolio() {
        Map<Integer, CFD> portfolio = facade.getPortfolioTrader(id_trader).stream().collect(Collectors.toMap(CFD :: getID, cfd -> cfd));
        Map<String, Ativo> mercado = facade.getMercado().stream().collect(Collectors.toMap(Ativo :: getID, ativo -> ativo));

        System.out.println("ID \t| Total (€) \t| €/Unidade \t| Stop Loss \t| Take Profit \t| Live Venda \t| Live Compra");
        for(CFD cfd : portfolio.values()){
            Ativo ativo_cfd = mercado.get(cfd.getAtivo().getID());
            System.out.println(cfd.toString() + " \t\t\t| " + ativo_cfd.getPrecoVenda() + " \t\t| " + ativo_cfd.getPrecoCompra());
        }

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

        for(CFD cfd : historico){
            System.out.println(cfd.toString());
        }

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
		facade = new Facade();
        UI ui = new UI();

		ui.execStartMenu();
	}
}