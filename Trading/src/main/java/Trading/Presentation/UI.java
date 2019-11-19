package Trading.Presentation;

import Trading.Business.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

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


        System.out.println(".------------------------------------------------------------");
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
                case 0: id_trader = -1; execStartMenu(); break;
                case 1: opcao = 0; execMercado(); break;
                case 2: opcao = 0; execPortfolio(); break;
                case 3: opcao = 0; execHistorico(); break;
                case 4: opcao = 0; execSaldo(); break;
                case 5: opcao = 0; execAdicionarFundos(); break;
                case 6: opcao = 0; execLevantarFundos(); break;
                default: System.out.println("Insira uma opção correta");
            }
        } while(opcao != 0);
    }


    private void execMercado() {
        System.out.println(facade.getMercado());
        execTraderMenu();
    }

    private void execPortfolio() {

    }

    private void execHistorico() {

    }

    private void execSaldo() {

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

		//facade.registaTrader("teste", "teste", "teste");
		//facade.printPrecos();
		//facade.abrirCFD(1, "AAPL", "long", 1, 100, 200);
		//facade.fecharCFD(1);
		//facade.adicionaFundos(1, 100);
		//facade.levantaFundos(1, 200);
	}
}