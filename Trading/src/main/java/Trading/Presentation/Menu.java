package Trading.Presentation;

public class Menu {

    private static String[][] operacoes = {
                    // Menu inicial
                    { "1 - Login",
                    "2 - Registar",
                    "0 - Sair"},
                    // Menu Trader
                    { "1 - Consultar mercado",
                    "2 - Consultar portfólio",
                    "3 - Consultar histórico",
                    "4 - Consultar saldo",
                    "5 - Consultar watchlist",
                    "6 - Adicionar fundos",
                    "7 - Levantar fundos",
                    "0 - Logoff"},
        };

    public static String[] getMainMenu(){
        return operacoes[0];
    }

    public static String[] getTraderMenu() {
        return operacoes[1];
    }
}
