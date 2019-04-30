package wait.list.manager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import wait.list.manager.R;
import wait.list.manager.adapter.CustomerAdapter;
import wait.list.manager.evenhandler.OkayButtonClickListener;
import wait.list.manager.evenhandler.OnItemClickListenerWithShareOption;
import wait.list.manager.evenhandler.YesAndNoButtonClickHandler;
import wait.list.manager.model.Customer;
import wait.list.manager.utility.AppConstant;
import wait.list.manager.utility.Utility;

import static wait.list.manager.utility.Utility.hideKeyboard;
import static wait.list.manager.utility.Utility.hideSoftKeyboard;


public class ManageCustomerActivity extends AppCompatActivity implements View.OnClickListener {

    private Customer customer;

    private static final int ADD_NEW_CUSTOMER = 1;
    private static final int EDIT_CUSTOMER = 2;


    private RealmResults<Customer> customerRealmList;
    private CustomerAdapter mAdapter;

    private LinearLayout idLlSearchClearButton;
    private EditText idEtSearchCustomer;
    private EditText idEtallocate;
    private TextView idTvNoDataFound,idTvActionbarTitle;

    private Realm realm;

    private boolean isOpenForCustomerSelect;
    private View editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.manage_customer_activity);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(ManageCustomerActivity.this,HistoryActivity.class);
                    startActivity(intent);
                }
            },2500);

            TextView idTvActionbarTitle;
            RecyclerView recyclerView;
            List<Customer> customerList;
            TextView idTvClearSearch,idTvSearch,idTvAddNew,idHistory;
            idHistory =  findViewById(R.id.idHistory);
            idHistory.setVisibility(View.VISIBLE);
            idHistory.setOnClickListener(this);
            idTvAddNew =  findViewById(R.id.idTvAddNew);
            idTvAddNew.setVisibility(View.VISIBLE);
            idTvAddNew.setOnClickListener(this);
            idTvClearSearch =  findViewById(R.id.idTvClearSearch);
            idTvClearSearch.setOnClickListener(this);
            idTvSearch =  findViewById(R.id.idTvSearch);
            idTvSearch.setOnClickListener(this);
            idTvNoDataFound =  findViewById(R.id.idTvNoDataFound);
            idTvNoDataFound.setOnClickListener(this);
            idTvActionbarTitle =  findViewById(R.id.idTvActionbarTitle);


            realm= Realm.getDefaultInstance();


            idLlSearchClearButton = findViewById(R.id.idLlSearchClearButton);
            idEtSearchCustomer = findViewById(R.id.idEtSearch);
            idEtSearchCustomer.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        Utility.hideKeyboard(idEtSearchCustomer,ManageCustomerActivity.this);
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


            recyclerView = findViewById(R.id.idRvCustomers);
            customerList = new ArrayList<>();
            mAdapter = new CustomerAdapter(this, customerList, new OnItemClickListenerWithShareOption() {

                @Override
                public void onItemShareButtonClicked(long id, long pos) {

                }

                @Override
                public void onItemClick(long id) {
                    if(isOpenForCustomerSelect){
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra(AppConstant.ID,id);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    }
                }

                @Override
                public void onItemEditClick(long id, int position) {
                    Intent addCustomerIntent = new Intent(ManageCustomerActivity.this, AddCustomerActivity.class);
                    addCustomerIntent.putExtra(AppConstant.CUSTOMER,id);
                    startActivityForResult(addCustomerIntent, EDIT_CUSTOMER);
                }

                @Override
                public void onItemDeleteClick(final long id, int position) {
                    Utility.showDoubleButtonPopupWindow(ManageCustomerActivity.this, getString(R.string.are_your_sure_to_delete, getString(R.string.customer)), new YesAndNoButtonClickHandler() {
                        @Override
                        public void onButtonYesClicked() {
                            realm.beginTransaction();
                            realm.where(Customer.class).equalTo(AppConstant.ID,id).findFirst().deleteFromRealm();
                            realm.commitTransaction();
                            setDataSet(customerRealmList);
                        }

                        @Override
                        public void onButtonNoClicked() {

                        }
                    });

                }

                @Override
                public void onItemButtonAllocateClick(long id, int position) {
                    Utility.showSingleButtonAllocatePopupWindow(ManageCustomerActivity.this, getString(R.string.please_enter_table_number), new OkayButtonClickListener() {
                        @Override
                        public void onButtonYesClicked(String data) {
                            Customer customer = realm.where(Customer.class).equalTo(AppConstant.ID, id).findFirst();
                            realm.beginTransaction();
                            customer.setTable(data);
                            realm.commitTransaction();
                            //UIUtil.hideKeyboard(ManageCustomerActivity.this);
                            setDataSet(customerRealmList);
                           // hideKeyboard(ManageCustomerActivity.this);
                           // hideSoftKeyboard(ManageCustomerActivity.this);

                        }


                        @Override
                        public void onButtonNoClicked() {
                            hideKeyboard(ManageCustomerActivity.this);
                            hideSoftKeyboard(ManageCustomerActivity.this);
                        }

                    });
                }

                @Override
                public void onItemButtonDeallocateClick(long id, int position) {


                    Customer customer = realm.where(Customer.class).equalTo(AppConstant.ID, id).findFirst();
                    realm.beginTransaction();
                    customer.setStatus(AppConstant.SEATED);
                    Log.d("///////////",customer.getStatus().toString());

                    Toast.makeText(ManageCustomerActivity.this, customer.getStatus().toString(), Toast.LENGTH_SHORT).show();

                }


                @Override
                public void onItemStatusChange(long id, int position) {
                }

                @Override
                public void OnItemClickListenerWithShareOption(long id, int position) {
                }

            });

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            hideSoftKeyboard(ManageCustomerActivity.this);
            hideKeyboard(ManageCustomerActivity.this);
            fetchStoredItems();
            isOpenForCustomerSelect = getIntent().getBooleanExtra(AppConstant.CUSTOMER, false);
            if(isOpenForCustomerSelect)
                changeTextViewText(idTvActionbarTitle,getString(R.string.select_customer));
            else
                idTvActionbarTitle.setText(getText(R.string.manage_customer));


        }catch (Exception e){
            Toast.makeText(ManageCustomerActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }


    public void changeTextViewText(TextView textView, String s){
        textView.setText(s);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.idHistory:
                Intent HistoryIntent = new Intent(this, HistoryActivity.class);
                //startActivityForResult(HistoryIntent, ADD_NEW_CUSTOMER);
                startActivity(HistoryIntent);
                break;
            case R.id.idTvAddNew:
                Intent addCustomerIntent = new Intent(this, AddCustomerActivity.class);
                startActivityForResult(addCustomerIntent, ADD_NEW_CUSTOMER);
                break;
            case R.id.idTvNoDataFound:
                Intent addNewCustomerIntent = new Intent(this, AddCustomerActivity.class);
                startActivityForResult(addNewCustomerIntent, ADD_NEW_CUSTOMER);
                break;
            case R.id.idTvClearSearch:
                idEtSearchCustomer.setText("");
                break;
            case R.id.idTvSearch:
                Utility.hideKeyboard(idEtSearchCustomer,ManageCustomerActivity.this);
                idEtSearchCustomer.setText("");
                break;
        }

    }


    private void fetchStoredItems() {
        customerRealmList = realm.where(Customer.class).equalTo(AppConstant.CUSTOMER_STATUS,AppConstant.ALLOCATED).or().equalTo(AppConstant.CUSTOMER_STATUS,AppConstant.BOOKED).findAll();
        setDataSet(customerRealmList);

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
                setDataSet(customerRealmList);
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