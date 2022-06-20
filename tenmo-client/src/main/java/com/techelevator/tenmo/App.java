        package com.techelevator.tenmo;

        import com.techelevator.tenmo.model.*;
        import com.techelevator.tenmo.services.AccountService;
        import com.techelevator.tenmo.services.AuthenticationService;
        import com.techelevator.tenmo.services.ConsoleService;
        import com.techelevator.tenmo.services.TransferService;

        import java.math.BigDecimal;
        import java.util.*;

        public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);


    private AuthenticatedUser currentUser;
    private AccountService accountService;
    private TransferService transferService;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        accountService = new AccountService(API_BASE_URL, currentUser);
        transferService = new TransferService(API_BASE_URL, currentUser);
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 6) {
                viewSingleTransfer();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void viewCurrentBalance() {
        // TODO Auto-generated method stub

        System.out.println(accountService.getBalance());

    }

    private void viewTransferHistory() {
        // TODO Auto-generated method stub
        //includes ability to view details on specific transactions


        for(Transfer transfer : transferService.getPastTransfers()){
            System.out.println(transferService.transferDetails(transfer, authenticationService));
        }

    }



    private void viewPendingRequests() {
        // TODO Auto-generated method stub

    }

    private void sendBucks() {
        // TODO Auto-generated method stub
        /*  As an authenticated user of the system, I need to be able to send a transfer of a specific
            amount of TE Bucks to a registered user.
                I should be able to choose from a list of users to send TE Bucks to.
                I must not be allowed to send money to myself.
                A transfer includes the User IDs of the from and to users and the amount of TE Bucks.
                The receiver's account balance is increased by the amount of the transfer.
                The sender's account balance is decreased by the amount of the transfer.
                I can't send more TE Bucks than I have in my account.
                I can't send a zero or negative amount.
                A Sending Transfer has an initial status of Approved.
        */

        Map<Integer, Account> menuMap = new HashMap<>();
        LinkedHashMap<String, Integer> users = authenticationService.getUsers();
        StringBuilder menuString = new StringBuilder("");

        int k = 1;
        for(Map.Entry<String, Integer> user: users.entrySet()){
            Long userId = (long)user.getValue();
            if(userId.equals(currentUser.getUser().getId())){
                continue;
            }
            String username = user.getKey();
            menuString.append(String.format("%d: %s%n", k, username));
            User thisUser = new User();
            thisUser.setUsername(username);
            thisUser.setId(userId);

            menuMap.put(k, accountService.getAccount(thisUser));
            k++;
        }
        int menuSelection = consoleService.promptForMenuSelection("Please choose a user: \n" + menuString.toString());
        BigDecimal amountToSend = consoleService.promptForBigDecimal("How much would you like to send?");
        Transfer thisTransfer = new Transfer(accountService.getAccount(), menuMap.get(menuSelection), amountToSend);


        boolean sent = transferService.sendCash(thisTransfer);

    }

    private void requestBucks() {
        // TODO Auto-generated method stub
        //Optional use case
    }

    private void viewSingleTransfer(){
        int transferId = consoleService.promptForInt("Which transfer would you like to see? ");
        System.out.println(transferService.transferDetails(
                transferService.getTransferById(transferId), authenticationService)
        );



    }

}
