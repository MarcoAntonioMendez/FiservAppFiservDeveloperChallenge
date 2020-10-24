package com.fiserv.fiservappfiservdeveloperchallenge;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fiserv.fiservappfiservdeveloperchallenge.banking.BankAccount;
import com.fiserv.fiservappfiservdeveloperchallenge.banking.Customer;

import java.util.ArrayList;
import java.util.Locale;

public class SchedulePaymentActivity extends AppCompatActivity {
    public static final String NAVIGATION_BAR_COLOR = "#FE3412";
    public static final String STATUS_BAR_COLOR = "#363636";
    public static final String TRANSPARENT_TEXT_VIEW_BACKGROUND_COLOR = "#FF6600";
    public static final String SELECTED_ACCOUNT_COLOR = "#FFFFFF";
    public static final String ACCOUNT_NOT_SELECTED_COLOR = "#DE5900";
    public static final int PAYMENT_SCHEDULED_SNACK_BAR_DURATION = 3000;

    private String userEmail;
    private TextView scheduleYourPaymentTitleTextView,beautifulMessageTextView;
    private TextView schedulePaymentFirstInstructionTextView,schedulePaymentSecondInstructionTextView;
    private TextView schedulePaymentThirdInstructionTextView;
    private Spinner monthSpinner,daySpinner;
    private LinearLayout accountsLinearLayout,customersLinearLayout;
    private Snackbar paymentScheduledSnackBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_payment);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setNavigationBarColor(Color.parseColor(NAVIGATION_BAR_COLOR));
        getWindow().setStatusBarColor(Color.parseColor(STATUS_BAR_COLOR));

        userEmail = getIntent().getExtras().getString(AppGlobalConstants.USER_EMAIL_PUT_EXTRA_CONSTANT);

        // Getting graphic elements references from XML file
        scheduleYourPaymentTitleTextView = findViewById(R.id.schedule_your_payment_title_in_schedule_payment_activity);
        beautifulMessageTextView = findViewById(R.id.beautiful_message_in_schedule_payment_activity);
        schedulePaymentFirstInstructionTextView = findViewById(R.id.schedule_payment_first_instruction_in_schedule_payment_activity);
        monthSpinner = findViewById(R.id.month_spinner_in_schedule_payment_activity);
        daySpinner = findViewById(R.id.day_spinner_in_schedule_payment_activity);
        schedulePaymentSecondInstructionTextView = findViewById(R.id.schedule_payment_second_instruction_in_schedule_payment_activity);
        accountsLinearLayout = findViewById(R.id.accounts_container_in_schedule_payment_activity);
        schedulePaymentThirdInstructionTextView = findViewById(R.id.schedule_payment_third_instruction_in_schedule_payment_activity);
        customersLinearLayout = findViewById(R.id.customers_container_in_schedule_payment_activity);


        // Setting texts for graphic elements
        setTextForGraphicElements();

        // Gets the banking accounts of the user
        getUsersAccountsCards();

        // Gets user's customers information
        getUsersCustomersInformation();
    }

    /**
     * When user touches the button to schedule the payment, a snack bar will appear at the bottom
     * saying the payment was scheduled correctly.
     */
    public void scheduledPayment(View view){
        paymentScheduledSnackBar.show();
    }

    /**
     * This method retrieves the user's customers information.
     * 1. Retrieves the user's customers information.
     * 2. Creates a panel (LinearLayout) to place customer information with graphic elements.
     *    Stuff like: customerName, card type, last four digits of the card etc.
     */
    private void getUsersCustomersInformation(){
        // 1. Retrieves the user's customers information.
        ArrayList<Customer> usersCustomers = retrieveUsersUsersCustomersInformation();

        // 2. Placing user's customer information in scrollable LinearLayout
        for(int x = 0; x < usersCustomers.size(); x++){
            final LinearLayout customerPanel = new LinearLayout(this);
            customerPanel.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            customerPanel.setOrientation(LinearLayout.VERTICAL);

            // Creating the graphic elements
            TextView accountCompanyNameAndType =
                    setTextViewForCompanyNameAndType(new BankAccount("","",
                            "","",
                            usersCustomers.get(x).getCustomerName(),""));
            LinearLayout cardIconAndLastFourNumbers =
                    setCardIconAndLastFourNumbers(new BankAccount("","",
                            "",usersCustomers.get(x).getBankAccountNumber(),
                            usersCustomers.get(x).getCompany(),""));
            TextView transparentBlock = getTransparentBlock();

            // Adding graphic elements to the accountPanel
            customerPanel.addView(accountCompanyNameAndType);
            customerPanel.addView(cardIconAndLastFourNumbers);
            customerPanel.addView(transparentBlock);
            customerPanel.setSelected(false);

            // Setting functionality for the account to be selected
            customerPanel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(customerPanel.isSelected()){
                        customerPanel.setSelected(false);
                        customerPanel.setBackgroundColor(Color.parseColor(ACCOUNT_NOT_SELECTED_COLOR));
                    }else{
                        customerPanel.setBackgroundColor(Color.parseColor(SELECTED_ACCOUNT_COLOR));
                        customerPanel.setSelected(true);
                    }
                }
            });

            customersLinearLayout.addView(customerPanel);
        }
    }

    /**
     * This method retrieves the user's customer information from a remote database.
     * WARNING: Since this app was made for the FiservDeveloperChallenge.
     *          The team "TrekingDevs" didn't want to spend money in a remote database, for now:
     *          this method uses hardcoded information. But the final form of this method is
     *          supposed to actually retrieve user's customers information from a remote database.
     * @return - A list of customers.
     */
    private ArrayList<Customer> retrieveUsersUsersCustomersInformation(){
        ArrayList<Customer> listOfCustomers = new ArrayList<>();

        Customer customer;
        String customerId,customerBankAccountNumber,customerName,company;

        customerId = "1";
        customerBankAccountNumber = "1234567891234567";
        customerName = "Escuela de Piano";
        company = BankAccount.COMPANY_VISA_CONSTANT;
        customer = new Customer(customerId,customerBankAccountNumber,customerName,company);
        listOfCustomers.add(customer);

        customerId = "2";
        customerBankAccountNumber = "1234567891234567";
        customerName = "Compañía de Internet";
        company = BankAccount.COMPANY_MASTERCARD_CONSTANT;
        customer = new Customer(customerId,customerBankAccountNumber,customerName,company);
        listOfCustomers.add(customer);

        customerId = "3";
        customerBankAccountNumber = "1234567891234567";
        customerName = "Compañía de Luz";
        company = BankAccount.COMPANY_DISCOVER_CONSTANT;
        customer = new Customer(customerId,customerBankAccountNumber,customerName,company);
        listOfCustomers.add(customer);

        customerId = "4";
        customerBankAccountNumber = "1234567891234567";
        customerName = "Tamales 'El Jonchito'";
        company = BankAccount.COMPANY_VISA_CONSTANT;
        customer = new Customer(customerId,customerBankAccountNumber,customerName,company);
        listOfCustomers.add(customer);

        return listOfCustomers;
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
            final LinearLayout accountPanel = new LinearLayout(this);
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
            accountPanel.setSelected(false);

            // Setting functionality for the account to be selected
            accountPanel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(accountPanel.isSelected()){
                        accountPanel.setSelected(false);
                        accountPanel.setBackgroundColor(Color.parseColor(ACCOUNT_NOT_SELECTED_COLOR));
                    }else{
                        accountPanel.setBackgroundColor(Color.parseColor(SELECTED_ACCOUNT_COLOR));
                        accountPanel.setSelected(true);
                    }
                }
            });

            accountsLinearLayout.addView(accountPanel);
        }
    }

    /**
     * This method retrieves the user's banking information from a remote database.
     * WARNING: Since this app was made for the FiservDeveloperChallenge.
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

        switch(Locale.getDefault().getLanguage()){
            case AppGlobalConstants.ENGLISH_LANGUAGE:
                if(account.getType().equals(BankAccount.DEBIT_TYPE_CONSTANT)){
                    type = "Debit";
                }else if(account.getType().equals(BankAccount.CREDIT_TYPE_CONSTANT)){
                    type = "Credit";
                }
                break;
            case AppGlobalConstants.SPANISH_LANGUAGE:
                if(account.getType().equals(BankAccount.DEBIT_TYPE_CONSTANT)){
                    type = "Débito";
                }else if(account.getType().equals(BankAccount.CREDIT_TYPE_CONSTANT)){
                    type = "Crédito";
                }
                break;
            default:
                if(account.getType().equals(BankAccount.DEBIT_TYPE_CONSTANT)){
                    type = "Debit";
                }else if(account.getType().equals(BankAccount.CREDIT_TYPE_CONSTANT)){
                    type = "Credit";
                }
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
     * Sets the text for all graphic elements according to the language.
     * Supported Languages: English and Spanish.
     */
    private void setTextForGraphicElements(){
        String language = Locale.getDefault().getLanguage();
        ArrayAdapter<CharSequence> monthSpinnerAdapter = null;

        ArrayAdapter<CharSequence> daySpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.days_for_spinner, android.R.layout.simple_spinner_item);
        daySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(daySpinnerAdapter);

        switch(language){
            case AppGlobalConstants.ENGLISH_LANGUAGE:
                scheduleYourPaymentTitleTextView.setText("Schedule your Payment");
                beautifulMessageTextView.setText(R.string.text_for_in_english_beautiful_message_in_schedule_payment_activity);
                schedulePaymentFirstInstructionTextView.setText(R.string.text_in_english_for_schedule_payment_first_instruction_in_schedule_payment_activity);
                monthSpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.months_in_english, android.R.layout.simple_spinner_item);
                schedulePaymentSecondInstructionTextView.setText(R.string.text_in_english_for_schedule_payment_second_instruction_in_schedule_payment_activity);
                schedulePaymentThirdInstructionTextView.setText(R.string.text_in_english_for_schedule_payment_third_instruction_in_schedule_payment_activity);
                paymentScheduledSnackBar = Snackbar.make(findViewById(R.id.schedule_payment_coordinator_layout),
                        R.string.text_in_english_for_payment_scheduled_snack_bar_in_schedule_payment_activity,
                        PAYMENT_SCHEDULED_SNACK_BAR_DURATION);
                break;
            case AppGlobalConstants.SPANISH_LANGUAGE:
                scheduleYourPaymentTitleTextView.setText("Agenda tu Pago");
                beautifulMessageTextView.setText(R.string.text_for_in_spanish_beautiful_message_in_schedule_payment_activity);
                schedulePaymentFirstInstructionTextView.setText(R.string.text_in_spanish_for_schedule_payment_first_instruction_in_schedule_payment_activity);
                monthSpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.months_in_spanish, android.R.layout.simple_spinner_item);
                schedulePaymentSecondInstructionTextView.setText(R.string.text_in_spanish_for_schedule_payment_second_instruction_in_schedule_payment_activity);
                schedulePaymentThirdInstructionTextView.setText(R.string.text_in_spanish_for_schedule_payment_third_instruction_in_schedule_payment_activity);
                paymentScheduledSnackBar = Snackbar.make(findViewById(R.id.schedule_payment_coordinator_layout),
                        R.string.text_in_spanish_for_payment_scheduled_snack_bar_in_schedule_payment_activity,
                        PAYMENT_SCHEDULED_SNACK_BAR_DURATION);
                break;
            default:
                scheduleYourPaymentTitleTextView.setText("Schedule your Payment");
                beautifulMessageTextView.setText(R.string.text_for_in_english_beautiful_message_in_schedule_payment_activity);
                schedulePaymentFirstInstructionTextView.setText(R.string.text_in_english_for_schedule_payment_first_instruction_in_schedule_payment_activity);
                monthSpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.months_in_english, android.R.layout.simple_spinner_item);
                schedulePaymentSecondInstructionTextView.setText(R.string.text_in_english_for_schedule_payment_second_instruction_in_schedule_payment_activity);
                schedulePaymentThirdInstructionTextView.setText(R.string.text_in_english_for_schedule_payment_third_instruction_in_schedule_payment_activity);
                paymentScheduledSnackBar = Snackbar.make(findViewById(R.id.schedule_payment_coordinator_layout),
                        R.string.text_in_english_for_payment_scheduled_snack_bar_in_schedule_payment_activity,
                        PAYMENT_SCHEDULED_SNACK_BAR_DURATION);
        }

        monthSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthSpinnerAdapter);

        // Setting the listener on snackBar so the CompleteMenu.java can start after the snackBar
        // is dismissed.
        final Context context = this;
        paymentScheduledSnackBar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event){
                Intent intent = new Intent(context,CompleteMenu.class);
                intent.putExtra(AppGlobalConstants.USER_EMAIL_PUT_EXTRA_CONSTANT,userEmail);
                finish();
            }
            @Override
            public void onShown(Snackbar snackbar) { }
        });
    }

}
