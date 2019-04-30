package wait.list.manager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import wait.list.manager.R;
import wait.list.manager.adapter.FilterAdapter;
import wait.list.manager.model.Customer;
import wait.list.manager.utility.AppConstant;
import wait.list.manager.utility.Utility;
import wait.list.manager.utility.Validation;

public class FilterActivity extends AppCompatActivity{

    ImageView idBack;
    TextView idTvActionbarTitle;
    EditText idEtStartDate,idEtEndDate;
    Button idBtnReset,idBtnSubmit;
    private FilterActivity myAdapter;
    private FilterAdapter myadapter;
    Spinner mySpinner;
    Realm realm;
    RecyclerView recyclerview;
    RealmResults<Customer> customers;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_activity);
        idBack=findViewById(R.id.idBack);
        idBack.setVisibility(View.VISIBLE);
        idTvActionbarTitle =  findViewById(R.id.idTvActionbarTitle);
        idBtnSubmit = findViewById(R.id.idBtnSubmit);
        idBtnReset = findViewById(R.id.idBtnReset);
        idEtStartDate = findViewById(R.id.idEtStartDate);
        idEtEndDate = findViewById(R.id.idEtEndDate);
        idEtStartDate.addTextChangedListener(startdate);
        idEtEndDate.addTextChangedListener(enddate);
        idEtStartDate.setText(Utility.getDateInFormat(new Date(),Utility.dateddMMyyyyFormat));
        idEtEndDate.setText(Utility.getDateInFormat(new Date(),Utility.dateddMMyyyyFormat));
     //   mySpinner = findViewById(R.id.spinner1);

       // mySpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

   //   ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(FilterActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
   //     ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(FilterActivity.this, R.layout.spinner_item, getResources().getStringArray(R.array.names));

    //    mAdapter.setDropDownViewResource(R.layout.spinner_item);

    //    mySpinner.setAdapter(mAdapter);
        realm= Realm.getDefaultInstance();


        Calendar calendar = Calendar.getInstance();
        Date jan1 = new Date(calendar.getTimeInMillis());
        customers =  realm.where(Customer.class).equalTo("date", "08/04/2019 06:19:43 PM").findAll();
        Log.d("date compare data",customers.toString());
        //customers = realm.where(Customer.class).findAll();
/*        if(customers.isEmpty(c)){
            Log.d("customer data","database not fatched....");
        }else{
            Log.d("customer data",customers.toString());
        }*/

        recyclerview = (RecyclerView) findViewById(R.id.idFtHistory);

        List<Customer> customerList = new ArrayList<>();
        customerList.addAll(customers);

        myadapter = new FilterAdapter(getActivity(), customerList);

        recyclerview.setAdapter(myadapter);

        //mySpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

/*
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    //startActivity(new Intent(FilterActivity.this, FilterActivity.class));
                    Calendar c = GregorianCalendar.getInstance();
                    c.add(Calendar.DAY_OF_MONTH, -10);
                    DateFormat cdate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    //    DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");

                    DateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
                    String endDate = sdf.format(new Date());
                    String startDate = cdate.format(c.getTime());

                    Log.d("currentdate+++++++", endDate);
                    Log.d("enddate+++++++++++", startDate);

        */
/*          String startDate = String.valueOf(sdf.format(new Date()));
                    String endDate = String.valueOf(cdate.format(c.getTime()));
                    customers = realm.where(Customer.class).equalTo("date","03/04/2019").findAll();
                    customers = realm.where(Customer.class).equalTo(AppConstant.CUSTOMER_STATUS,AppConstant.ALLOCATED).findAll();
                    Log.d("customer", String.valueOf(customers));   *//*


                } else if (i == 2) {
                    Calendar c = GregorianCalendar.getInstance();
                    c.add(Calendar.DAY_OF_MONTH, -7);
                    DateFormat cdate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    //    DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");

                    DateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
                    String endDate = sdf.format(new Date());
                    String startDate = cdate.format(c.getTime());

                    Log.d("currentdate+++++++", endDate);
                    Log.d("enddate+++++++++++", startDate);

                    //startActivity(new Intent(FilterActivity.this, FilterActivity.class));
                }else if (i == 3) {
                    Calendar c = GregorianCalendar.getInstance();
                    c.add(Calendar.DAY_OF_MONTH, -31);
                    DateFormat cdate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    //    DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");

                    DateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
                    String endDate = sdf.format(new Date());
                    String startDate = cdate.format(c.getTime());

                    Log.d("currentdate+++++++", endDate);
                    Log.d("enddate+++++++++++", startDate);
                    //startActivity(new Intent(FilterActivity.this, FilterActivity.class));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }); */



        idBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        idBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if (startdateValid() && enddateValid()) {
                    Toast.makeText(FilterActivity.this, "start date is ok.", Toast.LENGTH_SHORT).show();
            //  if(Validation.isthisDateValid(this,idEtStartDate,idEtEndDate,idTvActionbarTitle,AppConstant.DDMMYYYY, getString(R.string.date_required_and_format_error))){}
                }else{
                    Toast.makeText(FilterActivity.this, "start date is not ok", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public Activity getActivity() { return FilterActivity.this; }

    private boolean startdateValid() {

        //if(Validation.isValidMobile(AddCustomerActivity.this,idEtMobile, idEtMobile.getText().toString(),getString(R.string.phone_number),getString(R.string.required_field_message)))

        if(Validation.isThisStartDateValid(this, idEtStartDate, idTvActionbarTitle,AppConstant.DDMMYYYY, getString(R.string.start_date_required_and_format_error)))
            return true;
        else
            return false;

    }

    private boolean enddateValid() {

        //if(Validation.isValidMobile(AddCustomerActivity.this,idEtMobile, idEtMobile.getText().toString(),getString(R.string.phone_number),getString(R.string.required_field_message)))

        if(Validation.isThisEndDateValid(this, idEtEndDate, idTvActionbarTitle,AppConstant.DDMMYYYY, getString(R.string.end_date_required_and_format_error)))
            return true;
        else
            return false;

    }

    TextWatcher startdate = new TextWatcher(){
        int prevLength=0;
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length()==2 && prevLength<2){
                idEtStartDate.setText(s+"/");
                idEtStartDate.setSelection(idEtStartDate.getText().length());
            }
            if(s.length()==5 && prevLength<5) {
                idEtStartDate.setText(s + "/");
                idEtStartDate.setSelection(idEtStartDate.getText().length());
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {
            prevLength=s.length();
        }
    };

    TextWatcher enddate = new TextWatcher(){
        int prevLength=0;
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length()==2 && prevLength<2){
                idEtEndDate.setText(s+"/");
                idEtStartDate.setSelection(idEtEndDate.getText().length());
            }
            if(s.length()==5 && prevLength<5) {
                idEtEndDate.setText(s + "/");
                idEtEndDate.setSelection(idEtEndDate.getText().length());
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {
            prevLength=s.length();
        }
    };

}
