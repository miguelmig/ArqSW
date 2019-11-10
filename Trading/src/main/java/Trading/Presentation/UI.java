package Trading.Presentation;

import Trading.Business.UserManager;

public class UI {
    public static void main(String[] args) {

        UserManager userManager = new UserManager();

        userManager.registarTrader("asd", "asd", "asd");

        System.out.println(userManager.login("asdd", "asd"));
        System.out.println(userManager.login("asd", "asd"));

    }
}