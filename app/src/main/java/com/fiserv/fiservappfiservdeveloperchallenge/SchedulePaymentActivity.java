package com.fiserv.fiservappfiservdeveloperchallenge;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fiserv.fiservappfiservdeveloperchallenge.banking.BankAccount;
import com.fiserv.fiservappfiservdeveloperchallenge.banking.Customer;
import com.fiserv.fiservappfiservdeveloperchallenge.banking.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpResponseException;

/**
 * The SchedulePaymentActivity class will allow the user to schedule a recurring payment
 * according to his/her necessities.
 *
 * The class will send the appropriate HTTP petitions to Fiserv server so a payment can be scheduled.
 *
 * @author  MarcoAntonioMéndez
 * @version 1.0
 * @since   2020-10-26
 */

public class SchedulePaymentActivity extends AppCompatActivity {
    public static final String NAVIGATION_BAR_COLOR = "#FE3412";
    public static final String STATUS_BAR_COLOR = "#363636";
    public static final String TRANSPARENT_TEXT_VIEW_BACKGROUND_COLOR = "#FF6600";
    public static final String SELECTED_ACCOUNT_COLOR = "#FFFFFF";
    public static final String ACCOUNT_NOT_SELECTED_COLOR = "#DE5900";
    public static final String EVERY_MONTH = "Cada Mes";
    public static final String MONTH_CONSTANT = "MONTH";
    public static final String YEAR_CONSTANT = "YEAR";
    public static final int PAYMENT_SCHEDULED_SNACK_BAR_DURATION = 3000;
    public static final int ORDER_NUMBER_SEED = 1000000;

    private String userEmail;
    private TextView scheduleYourPaymentTitleTextView,beautifulMessageTextView;
    private TextView schedulePaymentFirstInstructionTextView,schedulePaymentSecondInstructionTextView;
    private TextView schedulePaymentThirdInstructionTextView,periodicityTextView,dayTextView;
    private Spinner periodicitySpinner,daySpinner;
    private LinearLayout accountsLinearLayout,customersLinearLayout;
    private Snackbar paymentScheduledSnackBar;
    private Random random;
    private EditText totalAmountOfMoneyEditText;
    private boolean wasPaymentScheduled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_payment);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setNavigationBarColor(Color.parseColor(NAVIGATION_BAR_COLOR));
        getWindow().setStatusBarColor(Color.parseColor(STATUS_BAR_COLOR));
        random = new Random();
        wasPaymentScheduled = false;

        userEmail = getIntent().getExtras().getString(AppGlobalConstants.USER_EMAIL_PUT_EXTRA_CONSTANT);

        // Getting graphic elements references from XML file
        scheduleYourPaymentTitleTextView = findViewById(R.id.schedule_your_payment_title_in_schedule_payment_activity);
        beautifulMessageTextView = findViewById(R.id.beautiful_message_in_schedule_payment_activity);
        schedulePaymentFirstInstructionTextView = findViewById(R.id.schedule_payment_first_instruction_in_schedule_payment_activity);
        periodicityTextView = findViewById(R.id.periodicity_tag_in_schedule_payment_activity);
        periodicitySpinner = findViewById(R.id.periodicity_spinner_in_schedule_payment_activity);
        dayTextView = findViewById(R.id.day_tag_in_schedule_payment_activity);
        daySpinner = findViewById(R.id.day_spinner_in_schedule_payment_activity);
        schedulePaymentSecondInstructionTextView = findViewById(R.id.schedule_payment_second_instruction_in_schedule_payment_activity);
        accountsLinearLayout = findViewById(R.id.accounts_container_in_schedule_payment_activity);
        schedulePaymentThirdInstructionTextView = findViewById(R.id.schedule_payment_third_instruction_in_schedule_payment_activity);
        customersLinearLayout = findViewById(R.id.customers_container_in_schedule_payment_activity);
        totalAmountOfMoneyEditText = findViewById(R.id.total_amount_edit_text_in_schedule_payment_activity);


        // Setting texts for graphic elements
        setTextForGraphicElements();

        // Gets the banking accounts of the user
        getUsersAccountsCards();

        // Gets user's customers information
        getUsersCustomersInformation();

        startLoop();
    }

    /**
     * A loop that will check if payment was scheduled correctly.
     */
    private void startLoop(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{Thread.sleep(30);}catch(InterruptedException e){e.printStackTrace();}
                    checkIfSchedulePaymentWasCorrect();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    /**
     * Checks if the scheduling of the payment was correct. If it was set correctly, then the
     * CompleteMenu.java will start.
     */
    private void checkIfSchedulePaymentWasCorrect(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(wasPaymentScheduled){
                    wasPaymentScheduled = false;
                    paymentScheduledSnackBar.show();
                }
            }
        });
    }

    /**
     * Generates an orderNumber for the scheduling of the payment
     * @return A String containing the order number.
     */
    private String generateOrderNumber(){
        return Integer.toString(random.nextInt(ORDER_NUMBER_SEED));
    }

    /**
     * Gets the current date plus one day in the format YYYY-MM-DD
     * @return A String containing The current date.
     */
    private String getCurrentDatePlusOneDay(){
        Date today = new Date();
        Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
        tomorrow.getTime();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(tomorrow);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(day);
    }

    /**
     * When user touches the button to schedule the payment, a snack bar will appear at the bottom
     * saying the payment was scheduled correctly.
     */
    public void scheduledPayment(View view){
        schedulePaymentUsingRestAPI(totalAmountOfMoneyEditText.getText().toString(),
                                    generateOrderNumber(),
                                    getCurrentDatePlusOneDay(),
                                    daySpinner.getSelectedItem().toString(),
                                    getSpinnerFrequency());
    }

    /**
     * Thes the selected item in periodicitySpinner with correct format
     * @return String containing MONTH or YEAR.
     */
    private String getSpinnerFrequency(){
        if(periodicitySpinner.getSelectedItem().toString().equals(EVERY_MONTH)){
            return MONTH_CONSTANT;
        }else{
            return YEAR_CONSTANT;
        }
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
     * Sets the text for all graphic elements.
     */
    private void setTextForGraphicElements(){
        ArrayAdapter<CharSequence> periodicitySpinnerAdapter;

        ArrayAdapter<CharSequence> daySpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.days_for_spinner, android.R.layout.simple_spinner_item);
        daySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(daySpinnerAdapter);

        // Setting the text for graphic elements
        scheduleYourPaymentTitleTextView.setText(R.string.text_in_spanish_for_schedule_your_payment_title_in_schedule_payment_activity);
        beautifulMessageTextView.setText(R.string.text_for_in_spanish_beautiful_message_in_schedule_payment_activity);
        schedulePaymentFirstInstructionTextView.setText(R.string.text_in_spanish_for_schedule_payment_first_instruction_in_schedule_payment_activity);
        periodicitySpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.periodicity_in_spanish, android.R.layout.simple_spinner_item);
        schedulePaymentSecondInstructionTextView.setText(R.string.text_in_spanish_for_schedule_payment_second_instruction_in_schedule_payment_activity);
        schedulePaymentThirdInstructionTextView.setText(R.string.text_in_spanish_for_schedule_payment_third_instruction_in_schedule_payment_activity);
        paymentScheduledSnackBar = Snackbar.make(findViewById(R.id.schedule_payment_coordinator_layout),
                R.string.text_in_spanish_for_payment_scheduled_snack_bar_in_schedule_payment_activity,
                PAYMENT_SCHEDULED_SNACK_BAR_DURATION);
        periodicityTextView.setText(R.string.text_in_spanish_for_periodicity_tag_in_schedule_payment_activity);
        dayTextView.setText(R.string.text_in_spanish_for_day_tag_in_schedule_payment_activity);

        periodicitySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        periodicitySpinner.setAdapter(periodicitySpinnerAdapter);

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

        //
        totalAmountOfMoneyEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus){
                if(hasFocus){
                   if(totalAmountOfMoneyEditText.getText().toString().equals("Ingrese el monto a pagar")){
                       totalAmountOfMoneyEditText.setText("");
                   }
                }else{
                    if(totalAmountOfMoneyEditText.getText().toString().isEmpty()){
                        totalAmountOfMoneyEditText.setText("Ingrese el monto a pagar");
                    }
                }
            }
        });
    }


    /**
     * Schedules the payment according to what user input in the editTexts and spinners
     * @param total - Total amount of money
     * @param orderNumber - Random number denoting the order number
     * @param currentDate - the date in the format 2020-12-30 YYYY-MM-DD
     * @param day - The day in which the payment will be executed.
     * @param frequency - The frequency month or year.
     */
    private void schedulePaymentUsingRestAPI(String total,String orderNumber,String currentDate,
                                             String day,String frequency){
        RestClient client = new RestClient(getApplicationContext());

        try {
            //Build JSON
            JSONObject saleTransaction = new JSONObject();
            saleTransaction.put("purchaseOrderNumber",orderNumber);
            saleTransaction.put("requestType","PaymentMethodPaymentSchedulesRequest");
            saleTransaction.put("transactionAmount",
                    (new JSONObject())
                            .put("total", total)
                            .put("currency", "MXN")
            );
            saleTransaction.put("startDate",currentDate);
            saleTransaction.put("frequency",
                    (new JSONObject())
                            .put("every", day)
                            .put("unit", frequency)//MONTH YEAR
            );

            saleTransaction.put("paymentMethod",new JSONObject().put(
                    "paymentCard",(new JSONObject())
                            .put("number", "4004430000000007")
                            .put("securityCode","111")
                            .put("expiryDate",(new JSONObject())
                                    .put("month", "12")
                                    .put("year", "24"))
            ));


            //send POST HTTP REQUEST
            client.post("payment-schedules", saleTransaction, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    wasPaymentScheduled = true;
                    Log.d("SuccessObj", response.toString());
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);
                    //handle success when response is an array of objects
                    Log.d("SuccessArr", response.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    //handle error when response is an array of objects
                    Log.d("ErrorArr", errorResponse.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    //handle success when response is a single object
                    Log.d("ErrorObj", errorResponse.toString());
                }
            });

        }catch (HttpResponseException ex){
            Log.d("HttpResponseException", ex.getMessage());
        }
        catch (JSONException ex){
            Log.d("JSONException", ex.getMessage());
        }
        catch (Exception ex){
            Log.d("Exception", ex.getMessage());
        }

    }

}
