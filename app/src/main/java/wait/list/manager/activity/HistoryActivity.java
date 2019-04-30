package wait.list.manager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
//import java.util.logging.Handler;
import java.util.logging.LogRecord;

import io.realm.Realm;
import io.realm.RealmResults;
import wait.list.manager.R;
import wait.list.manager.adapter.HistoryAdapter;
import wait.list.manager.model.Customer;
import wait.list.manager.utility.AppConstant;
import wait.list.manager.utility.Utility;

public class HistoryActivity extends AppCompatActivity {

    private static final int ADD_NEW_CUSTOMER = 1;
    private static final int EDIT_CUSTOMER = 2;

    private RealmResults<Customer> customers;

    ImageView idBack;
    TextView idTvActionbarTitle,idTvFilter;
    private boolean isOpenForCustomerSelect;
    private LinearLayout idLlSearchClearButton;
    private EditText idEtSearchCustomer;
    private TextView idTvSearch,idTvClearSearch,idTvNoDataFound;
    private HistoryAdapter mAdapter;
    Realm realm;
    RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);

        realm= Realm.getDefaultInstance();

        idTvSearch =  findViewById(R.id.idTvSearch);
        idTvClearSearch =  findViewById(R.id.idTvClearSearch);
        idTvNoDataFound = findViewById(R.id.idTvNoDataFound);

        idLlSearchClearButton = findViewById(R.id.idLlSearchClearButton);
        idEtSearchCustomer = findViewById(R.id.idEtSearch);
        idEtSearchCustomer.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Utility.hideKeyboard(idEtSearchCustomer,HistoryActivity.this);
                    return true;
                }
                return false;
            }

        });
        idEtSearchCustomer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getFilter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0&&idLlSearchClearButton.getVisibility()==View.GONE){
                    idLlSearchClearButton.setVisibility(View.VISIBLE);
                }
                if(s.length()==0&&idLlSearchClearButton.getVisibility()==View.VISIBLE)
                    idLlSearchClearButton.setVisibility(View.GONE);

            }
        });

        idTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.hideKeyboard(idEtSearchCustomer,HistoryActivity.this);
                idEtSearchCustomer.setText("");
            }
        });


        idTvClearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idEtSearchCustomer.setText("");
            }
        });

        RealmResults<Customer> customers = realm.where(Customer.class).equalTo("status","Seated").findAll();

        if(customers.isEmpty()){
            idTvNoDataFound.setVisibility(View.VISIBLE);
        }else{
            idTvNoDataFound.setVisibility(View.GONE);
        }


        recyclerview = (RecyclerView) findViewById(R.id.idRvHistory);

        List<Customer> customerList = new ArrayList<>();
        customerList.addAll(customers);
        //Bind data
        mAdapter = new HistoryAdapter(getActivity(), customerList);

        recyclerview.setAdapter(mAdapter);
        fetchStoredItems();


        idBack=findViewById(R.id.idBack);
        idBack.setVisibility(View.VISIBLE);
        idBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        idTvFilter = findViewById(R.id.idTvFilter);
        idTvFilter.setVisibility(View.VISIBLE);
        idTvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistoryActivity.this,FilterActivity.class));
            }
        });
        idTvActionbarTitle =  findViewById(R.id.idTvActionbarTitle);

        isOpenForCustomerSelect = getIntent().getBooleanExtra(AppConstant.HISTORY, false);
        if(isOpenForCustomerSelect)
            changeTextViewText(idTvActionbarTitle,getString(R.string.select_customer));
        else
            idTvActionbarTitle.setText(getText(R.string.manage_customer_history));
    }

    public Activity getActivity() { return HistoryActivity.this; }

    public void changeTextViewText(TextView textView, String s){
        textView.setText(s);
    }

    private void fetchStoredItems() {
        //customers = realm.where(Customer.class).equalTo(AppConstant.CUSTOMER_STATUS,AppConstant.ALLOCATED).or().equalTo(AppConstant.CUSTOMER_STATUS,AppConstant.BOOKED).findAll();
        customers = realm.where(Customer.class).equalTo(AppConstant.CUSTOMER_STATUS,AppConstant.SEATED).findAll();
        setDataSet(customers);
    }

    public void setDataSet(RealmResults customerRealmList){
        mAdapter.setDateSet(customerRealmList);
        if(customerRealmList!=null&& customerRealmList.size()>0){
            idTvNoDataFound.setVisibility(View.GONE);
        } else{
            idTvNoDataFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == ADD_NEW_CUSTOMER) {
            if(resultCode == Activity.RESULT_OK){
                setDataSet(customers);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        if (requestCode == EDIT_CUSTOMER) {
            if(resultCode == Activity.RESULT_OK){

                mAdapter.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }
}
