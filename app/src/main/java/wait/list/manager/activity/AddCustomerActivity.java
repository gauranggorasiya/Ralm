package wait.list.manager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import wait.list.manager.R;
import wait.list.manager.model.Customer;
import wait.list.manager.utility.AppConstant;
import wait.list.manager.utility.Utility;
import wait.list.manager.utility.Validation;


public class AddCustomerActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView idBack;
    private TextView idTvActionbarTitle;
    private Button idBtnSubmit,idBtnReset;

    private EditText idEtCustName,idEtAddress,idEtNumber,idEtEmail,idEtMobile;
    private Customer customer;
    private Realm realm;

    private long customerId;

    double outStanding = 0.0;
    int paidBill = 0, unpaidBill = 0;

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
    Date dateobj = new Date();
    private String date = df.format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer_activity);
        initControlAndVariables();
        realm = Realm.getDefaultInstance();
        try{
            customerId = getIntent().getLongExtra(AppConstant.CUSTOMER, AppConstant.DEFAULT_LONG_VALUE);
            if(customerId==AppConstant.DEFAULT_LONG_VALUE)
                changeTextViewText(idTvActionbarTitle,getString(R.string.add_new_customer));
            else{
                changeTextViewText(idTvActionbarTitle,getString(R.string.update_customer_details));
                customer=realm.where(Customer.class).equalTo(AppConstant.ID,customerId).findFirst();
                bindData(customer);
            }
        }catch (Exception e){
            changeTextViewText(idTvActionbarTitle,getString(R.string.add_new_customer));
            e.printStackTrace();
        }finally {

        }

    }

    public void initControlAndVariables(){
        idBack=findViewById(R.id.idBack);
        idBack.setVisibility(View.VISIBLE);
        idBack.setOnClickListener(this);
        idTvActionbarTitle=findViewById(R.id.idTvActionbarTitle);
        idEtCustName=findViewById(R.id.idEtCustName);
    //  idEtAddress=findViewById(R.id.idEtAddress);
        idEtNumber=findViewById(R.id.idEtNumber);
    //  idEtEmail=findViewById(R.id.idEtEmail);
        idEtMobile=findViewById(R.id.idEtMobile);
        idBtnReset=findViewById(R.id.idBtnReset);
        idBtnReset.setOnClickListener(this);
        idBtnSubmit=findViewById(R.id.idBtnSubmit);
        idBtnSubmit.setOnClickListener(this);
    }
    public void bindData(Customer customer){
      //idEtAddress.setText(customer.getAddress());
        idEtNumber.setText(customer.getGstNumber());
        idEtCustName.setText(customer.getFullName());
      //idEtEmail.setText(customer.getEmail());
        idEtMobile.setText(customer.getMobile());
        date = customer.getDate();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.idBack:
                finish();
            break;
            case R.id.idBtnReset:
                clearAllFields();
            break;
            case R.id.idBtnSubmit:
                if(validate() && mobileValidate() && numberValidate()){
                    if(customer==null) {
                        saveCustomerData();
                    }
                    else{
                        updateCustomerData(customer);
                    }
                }
            break;

        }
    }
    public long getUniqueId(){
        int key;
        try {
            key = realm.where(Customer.class).max(AppConstant.ID).intValue() + 1;
        } catch(Exception ex) {
            key = 0;
        }
        return key;
    }

    private void saveCustomerData() {

        realm.beginTransaction();
        Customer customer=new Customer(getUniqueId(),idEtCustName.getText().toString(),idEtNumber.getText().toString(),idEtMobile.getText().toString(),date,outStanding, paidBill,unpaidBill);
        realm.insertOrUpdate(customer);
        realm.commitTransaction();
        Utility.hideKeyboard(idBtnSubmit,AddCustomerActivity.this);
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK,returnIntent);
        finish();

    }


    private void updateCustomerData(Customer customer) {
        DateFormat dd = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
        Date dateobject = new Date();
        String date = dd.format(new Date());

        realm.beginTransaction();
        customer.setFullName(idEtCustName.getText().toString());
     // customer.setAddress(idEtAddress.getText().toString());
        customer.setGstNumber(idEtNumber.getText().toString());
     // customer.setEmail(idEtEmail.getText().toString());
        customer.setMobile(idEtMobile.getText().toString());
        customer.setDate(date);
        realm.insertOrUpdate(customer);
        realm.commitTransaction();
        Utility.hideKeyboard(idBtnSubmit,AddCustomerActivity.this);
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    private boolean validate() {
        if(Validation.chkRequired(AddCustomerActivity.this,idEtCustName,idTvActionbarTitle,getString(R.string.customer_name),getString(R.string.required_field_message),getString(R.string.length_error),3,64))
            return true;
        else
            return false;
    }

    private boolean mobileValidate() {
        //if(Validation.isValidMobile(AddCustomerActivity.this,idEtMobile, idEtMobile.getText().toString(),getString(R.string.phone_number),getString(R.string.required_field_message)))

        if(Validation.isValidMobile(AddCustomerActivity.this,idEtMobile,idTvActionbarTitle,getString(R.string.phone_number),getString(R.string.required_field_message),getString(R.string.mobile_error),10,13))
            return true;
        else
            return false;
    }

    private boolean numberValidate() {
        if(Validation.isValidNumber(AddCustomerActivity.this,idEtNumber,idTvActionbarTitle,getString(R.string.number_of_person),getString(R.string.required_field_message),getString(R.string.person_error),0,200))
            return true;
        else
            return false;
    }

    private void clearAllFields() {
        idEtCustName.setText("");
      //idEtAddress.setText("");
        idEtNumber.setText("");
      //idEtEmail.setText("");
        idEtMobile.setText("");
    }

    public void changeTextViewText(TextView textView, String s){
        textView.setText(s);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}