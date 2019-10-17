import services.uiService.MenuService;
import ui.Menu;
import ui.MenuEntry;

public class Main {
    public static void main(String[] args) throws Exception {

        MenuService menuService = new MenuService();
        Menu mainMenu = new Menu();


        mainMenu.addEntry(new MenuEntry("Add customer") {
            @Override
            public void run() {
                System.out.println("Add customer");
                menuService.addCustomerMenu();
            }
        });
        mainMenu.addEntry(new MenuEntry("Delete customer") {
            @Override
            public void run() {
                System.out.println("Delete customer and all customer's account");
                menuService.deleteCustomerMenu();
            }
        });

        mainMenu.addEntry(new MenuEntry("Show customer's accounts...") {
            @Override
            public void run() {
                System.out.println("Show customer's account");
                menuService.showAccountsCustomerMenu();
            }
        });

        mainMenu.addEntry(new MenuEntry("Accounts") {

            @Override
            public void run() {
                System.out.println("Show Accounts");
                Menu menuAccounts = new Menu();
                menuAccounts.addEntry(new MenuEntry("Add account") {
                    @Override
                   public void run()  {
                   menuService.addAccountMenu();
                    }
                });

                menuAccounts.addEntry(new MenuEntry("Delete account") {
                    @Override
                    public void run() {
                    menuService.deleteAccountMenu();
                    }
                });


                menuAccounts.addEntry(new MenuEntry("Block or Unblocked account") {
                    @Override
                    public void run() {
                       menuService.blockOrUnblockedAccountMenu();

                    }
                });

                menuAccounts.addEntry(new MenuEntry("Change Balance") {
                    @Override
                    public void run() {
                        menuService.changeBalanceMenu();
                   }
                });

                menuAccounts.addEntry(new MenuEntry("Account statistics") {
                    @Override
                    public void run() {
                        {
                           menuService.showStat();

                    }}
                });
                menuAccounts.run();
            }
        });
        mainMenu.run();
    }
}