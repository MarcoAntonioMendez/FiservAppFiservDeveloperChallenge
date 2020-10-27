package com.fiserv.fiservappfiservdeveloperchallenge;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fiserv.fiservappfiservdeveloperchallenge.banking.BankAccount;

import java.util.ArrayList;
import java.util.Locale;

public class OperationsActivity extends AppCompatActivity {
    public static final String NAVIGATION_BAR_COLOR = "#FE3412";
    public static final String STATUS_BAR_COLOR = "#363636";
    public static final String TRANSPARENT_TEXT_VIEW_BACKGROUND_COLOR = "#FF6600";
    public static final String USER_NAME = "USER_NAME";

    private String userName,userEmail;
    private TextView greetUserTextView,accountsTextView,logOutTextView;
    private TextView plusSignTextView;
    private LinearLayout accountsLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setNavigationBarColor(Color.parseColor(NAVIGATION_BAR_COLOR));
        getWindow().setStatusBarColor(Color.parseColor(STATUS_BAR_COLOR));

        // Getting graphic components from xml file
        greetUserTextView = findViewById(R.id.greet_user_text_view_operations);
        accountsTextView = findViewById(R.id.operations_account_text_view);
        accountsLinearLayout = findViewById(R.id.linear_layout_for_accounts);
        logOutTextView = findViewById(R.id.log_out_text_view_in_operations_activity);
        plusSignTextView = findViewById(R.id.plus_sign_text_view_in_operations_activity);

        // Getting the userName
        userName = getIntent().getExtras().getString(USER_NAME);
        userEmail = getIntent().getExtras().getString(AppGlobalConstants.USER_EMAIL_PUT_EXTRA_CONSTANT);

        setTextForGraphicComponents();

        // Getting the user's accounts cards
        getUsersAccountsCards();
    }

    /**
     * When user touches the log out icon (the little door), the LogInActivity will show up.
     * @param view - The log_out_icon_in_operations_activity
     */
    public void goBackToLogInActivityFromOperationsActivity(View view){
        Intent intent = new Intent(this,LogInActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * When user touches the plus sign, a whole activity will show offering the users all the
     * different options he/she can make in the FiservApp
     * @param view - ImageView plusSign.
     */
    public void startCompleteMenuActivity(View view){
        Intent intent = new Intent(this,CompleteMenu.class);
        intent.putExtra(AppGlobalConstants.USER_EMAIL_PUT_EXTRA_CONSTANT,userEmail);
        startActivity(intent);
    }

    /**
     * This method retrieves the banking information of the user.
     * 1. Retrieves the banking information of the user.
     * 2. Creates a panel (LinearLayout) to place banking information with graphic elements.
     *    Stuff like: cardIcon, company name, last four digits of the card etc.
     */
    private void getUsersAccountsCards(){
        // 1. Retrieves the banking information of the user.
        ArrayList<BankAccount> usersBankAccounts = retrieveUsersBankAccountsInformation();

        // 2. Placing user's account in scrollable LinearLayout
        for(int x = 0; x < usersBankAccounts.size(); x++){
            LinearLayout accountPanel = new LinearLayout(this);
            accountPanel.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            accountPanel.setOrientation(LinearLayout.VERTICAL);

            // Creating the graphic elements
            TextView accountCompanyNameAndType =
                                         setTextViewForCompanyNameAndType(usersBankAccounts.get(x));
            LinearLayout cardIconAndLastFourNumbers = setCardIconAndLastFourNumbers(usersBankAccounts.get(x));
            TextView transparentBlock = getTransparentBlock();

            // Adding graphic elements to the accountPanel
            accountPanel.addView(accountCompanyNameAndType);
            accountPanel.addView(cardIconAndLastFourNumbers);
            accountPanel.addView(transparentBlock);

            accountsLinearLayout.addView(accountPanel);
        }
    }

    /**
     * This method retrieves the user's banking information from a remote database.
     * WARNING: Since this app was made in a hackathon (FiservDeveloperChallenge).
     *          The team "TrekingDevs" didn't want to spend money in a remote database, for now:
     *          this method uses hardcoded information. But the final form of this method is
     *          supposed to actually retrieve user's banking account from a remote database.
     * @return - A list of bank accounts.
     */
    private ArrayList<BankAccount> retrieveUsersBankAccountsInformation(){
        ArrayList<BankAccount> usersBankAccounts = new ArrayList<>();

        // Creating a bank account just for testing
        String expirationDate = "31/12";
        String securityCodeCvv = "111";
        String bankAccountNumber = "1234567891234567";
        String company = BankAccount.COMPANY_VISA_CONSTANT;
        String type = BankAccount.DEBIT_TYPE_CONSTANT;

        BankAccount bankAccount = new BankAccount(
                bankAccountNumber+expirationDate+securityCodeCvv+company,
                expirationDate, securityCodeCvv,bankAccountNumber,company,type);
        usersBankAccounts.add(bankAccount);

        // Creating a bank account just for testing
        expirationDate = "31/12";
        securityCodeCvv = "111";
        bankAccountNumber = "1234567891234567";
        company = BankAccount.COMPANY_DISCOVER_CONSTANT;
        type = BankAccount.DEBIT_TYPE_CONSTANT;

        bankAccount = new BankAccount(
                bankAccountNumber+expirationDate+securityCodeCvv+company,
                expirationDate, securityCodeCvv,bankAccountNumber,company,type);
        usersBankAccounts.add(bankAccount);

        // Creating a bank account just for testing
        expirationDate = "31/12";
        securityCodeCvv = "111";
        bankAccountNumber = "1234567891234567";
        company = BankAccount.COMPANY_MASTERCARD_CONSTANT;
        type = BankAccount.CREDIT_TYPE_CONSTANT;

        bankAccount = new BankAccount(
                bankAccountNumber+expirationDate+securityCodeCvv+company,
                expirationDate, securityCodeCvv,bankAccountNumber,company,type);
        usersBankAccounts.add(bankAccount);

        // Creating a bank account just for testing
        expirationDate = "31/12";
        securityCodeCvv = "111";
        bankAccountNumber = "1234567891234567";
        company = BankAccount.COMPANY_VISA_CONSTANT;
        type = BankAccount.DEBIT_TYPE_CONSTANT;

        bankAccount = new BankAccount(
                bankAccountNumber+expirationDate+securityCodeCvv+company,
                expirationDate, securityCodeCvv,bankAccountNumber,company,type);
        usersBankAccounts.add(bankAccount);

        // Creating a bank account just for testing
        expirationDate = "31/12";
        securityCodeCvv = "111";
        bankAccountNumber = "1234567891234567";
        company = BankAccount.COMPANY_DISCOVER_CONSTANT;
        type = BankAccount.DEBIT_TYPE_CONSTANT;

        bankAccount = new BankAccount(
                bankAccountNumber+expirationDate+securityCodeCvv+company,
                expirationDate, securityCodeCvv,bankAccountNumber,company,type);
        usersBankAccounts.add(bankAccount);

        // Creating a bank account just for testing
        expirationDate = "31/12";
        securityCodeCvv = "111";
        bankAccountNumber = "1234567891234567";
        company = BankAccount.COMPANY_MASTERCARD_CONSTANT;
        type = BankAccount.CREDIT_TYPE_CONSTANT;

        bankAccount = new BankAccount(
                bankAccountNumber+expirationDate+securityCodeCvv+company,
                expirationDate, securityCodeCvv,bankAccountNumber,company,type);
        usersBankAccounts.add(bankAccount);

        return usersBankAccounts;
    }

    /**
     * It sets in a TextView the account company(Visa, Mastercard)
     *  and the account type(debit, credit).
     *  ------------------------------------------------------------------------------------
     *  Example of a resulting TextViews:
     *  * VISA Debit
     *  * MASTERCARD Debit
     *  * Discover Credit
     *  ------------------------------------------------------------------------------------
     * @param account - An account object containing all the necessary information.
     * @return - A TextView that will contain the following information:
     * Account Company(Visa, Mastercard etc.) and account type(debit, credit etc.);
     */
    private TextView setTextViewForCompanyNameAndType(BankAccount account){
        TextView textView = new TextView(this);
        String type = "";

        if(account.getType().equals(BankAccount.DEBIT_TYPE_CONSTANT)){
            type = "Débito";
        }else if(account.getType().equals(BankAccount.CREDIT_TYPE_CONSTANT)){
            type = "Crédito";
        }

        textView.setTypeface(Typeface.SANS_SERIF);
        textView.setTextSize(getResources().getDimension(R.dimen.operations_accounts_text_size));
        textView.setText(account.getCompany()+" "+type);
        return textView;
    }

    /**
     * Creates a "panel" (A LinearLayout) containing an icon for a card and the last 4 numbers of
     * the account.
     * ---------------------------------------------------------------------------
     * Example of resulting panels (LinearLayouts).
     * ||||||||||||
     * |||||||||||| *1234
     * ||||||||||||
     *
     * ||||||||||||
     * |||||||||||| *5678
     * ||||||||||||
     * ---------------------------------------------------------------------------
     * @param account - BankAccount object that contains the necessary information to create graphic
     *                elements.
     * @return A linearLayout containing those graphic elements.
     */
    private LinearLayout setCardIconAndLastFourNumbers(BankAccount account){
        // Creating the "panel" (LinearLayout).
        LinearLayout panel = new LinearLayout(this);
        panel.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        panel.setOrientation(LinearLayout.HORIZONTAL);

        // Creating the cardIcon
        ImageView cardIcon = new ImageView(this);
        switch(account.getCompany()){
            case BankAccount.COMPANY_VISA_CONSTANT:
                cardIcon.setImageResource(R.drawable.visa_card_icon);
            break;
            case BankAccount.COMPANY_MASTERCARD_CONSTANT:
                cardIcon.setImageResource(R.drawable.mastercard_icon);
            break;
            case BankAccount.COMPANY_DISCOVER_CONSTANT:
                cardIcon.setImageResource(R.drawable.discover_card_icon);
            break;
            default:
                cardIcon.setImageResource(R.drawable.visa_card_icon);
        }


        // Creating the lastFourNumbersTextView
        String accountNumber = account.getBankAccountNumber();
        String lastFourNumbers = " *"+
                accountNumber.substring(accountNumber.length()-4,accountNumber.length());
        TextView lastFourNumbersTextView = new TextView(this);
        lastFourNumbersTextView.setTypeface(Typeface.SANS_SERIF);
        lastFourNumbersTextView.setTextSize(getResources().getDimension(
                R.dimen.operations_accounts_text_size_for_card_numbers));
        lastFourNumbersTextView.setText(lastFourNumbers);

        // Adding all graphic elements to the panel (LinearLayout)
        panel.addView(cardIcon);
        panel.addView(lastFourNumbersTextView);

        return panel;
    }

    /**
     * Creates an empty TextView for the "Your Accounts" scrollable LinearLayout, it's placed
     * between accounts.
     * @return - An empty TextView
     */
    private TextView getTransparentBlock(){
        TextView transparentBlock = new TextView(this);
        transparentBlock.setTypeface(Typeface.SANS_SERIF);
        transparentBlock.setTextSize(getResources().getDimension(R.dimen.transparent_block_text_size));
        transparentBlock.setText("      ");
        transparentBlock.setBackgroundColor(Color.parseColor(TRANSPARENT_TEXT_VIEW_BACKGROUND_COLOR));
        return transparentBlock;
    }

    /**
     * Graphic components are assigned the text.
     */
    private void setTextForGraphicComponents(){
        greetUserTextView.setText("Hola, " + userName);
        accountsTextView.setText("Tus Cuentas");
        logOutTextView.setText("Salir");
        plusSignTextView.setText("Más");
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

}
