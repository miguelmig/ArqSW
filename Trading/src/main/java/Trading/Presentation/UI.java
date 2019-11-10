package Trading.Presentation;

import Trading.Business.LiveStock;
import Trading.Business.UserManager;

public class UI {
    public static void main(String[] args) {

        LiveStock ls = new LiveStock();

        ls.refreshMarket();
    }
}