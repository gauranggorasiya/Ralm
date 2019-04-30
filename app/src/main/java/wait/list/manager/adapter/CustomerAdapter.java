package wait.list.manager.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import wait.list.manager.R;
import wait.list.manager.activity.ManageCustomerActivity;
import wait.list.manager.evenhandler.OkayButtonClickListener;
import wait.list.manager.evenhandler.OnItemClickListener;
import wait.list.manager.evenhandler.OnItemClickListenerWithShareOption;
import wait.list.manager.model.Customer;
import wait.list.manager.utility.AppConstant;
import wait.list.manager.utility.Utility;

import static wait.list.manager.utility.Utility.hideSoftKeyboard;


public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {
    private Context context;
    private List<Customer> customerList=new ArrayList<>();

    private List<Customer> customerListOriginal;
    private RealmResults<Customer> customerRealmList;
    private ImageView idIvContextMenu;

    //private final OnItemClickListener listener;
    long id;
    private TextView idTvnodatafound;
    private CustomerAdapter mAdapter;
    private final OnItemClickListenerWithShareOption listener;

    private Realm realm;;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView idTvCustomerName, idTvCustomerNumber, tableno , status;
        public ImageView idIvWhatsappCustomer,idIvCallCustomer,idIvMessageCustomer,getIvContextMenulist;
        public Button idBtnStatusChange;
        public EditText idEditText;
        public View view;

        public MyViewHolder(View view) {
            super(view);
            this.view=view;
            idTvCustomerName = view.findViewById(R.id.idTvCustomerName);
            idIvMessageCustomer = view.findViewById(R.id.idIVMessageCustomer);
            idTvCustomerNumber = view.findViewById(R.id.idTvNumberOfPerson);
        //  idIvDeleteCustomer = view.findViewById(R.id.idIvDeleteCustomer);
        //  idIvEditCustomer = view.findViewById(R.id.idIvEditCustomer);
            getIvContextMenulist = view.findViewById(R.id.idIvContextMenu);
            idIvWhatsappCustomer = view.findViewById(R.id.idIVWhatsAppCustomer);
            idIvCallCustomer = view.findViewById(R.id.idTvCustomerPhone);
            idBtnStatusChange = view.findViewById(R.id.idBtnStatusChange);
            idEditText = view.findViewById(R.id.idEtAllocate);
            tableno = view.findViewById(R.id.tableno);
            status = view.findViewById(R.id.status);
            idTvnodatafound = view.findViewById(R.id.idTvNoDataFound);

        }
    }


    public void setDateSet(List<Customer> dataSet){
        customerListOriginal=dataSet;
        customerList.clear();
        customerList.addAll(dataSet);
        notifyDataSetChanged();
    }

    public CustomerAdapter(Context context, List<Customer> customerList, OnItemClickListener listener) {
        this.context = context;
        this.customerList.addAll(customerList);
        this.customerListOriginal=customerList;
        this.listener = (OnItemClickListenerWithShareOption) listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_list_item, parent, false);

        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        realm= Realm.getDefaultInstance();
        try {
            final Customer customer = customerList.get(position);
            holder.idTvCustomerName.setText(customer.getFullName());
            holder.idTvCustomerNumber.setText(customer.getGstNumber());

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(customer.getId());
                }
            });

        /*  holder.idIvEditCustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemEditClick(customer.getId(),position);
                }
            });
            holder.idIvDeleteCustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemDeleteClick(customer.getId(),position);
                }
            });*/

            if(customer.getStatus().equals(AppConstant.BOOKED))
                holder.idBtnStatusChange.setText(R.string.allocate_table);
            //if(customer.getStatus().equals(AppConstant.ALLOCATED))
            if(customer.getTable().length() > 0)
                holder.idBtnStatusChange.setText(R.string.seated);


            //Utility.hideKeyboard(holder.idBtnStatusChange, (Activity) context);


            holder.idBtnStatusChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
/*                  if (customer.getTable().length() > 0) {
                        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                    } else  {
                        Toast.makeText(context, "not success", Toast.LENGTH_SHORT).show();
                    }*/
                    String status = "";
                    if(customer.getStatus().equals(AppConstant.BOOKED) || customer.getTable().isEmpty())
                        listener.onItemButtonAllocateClick(customer.getId(),position);
                        status=AppConstant.ALLOCATED;
                    //if(customer.getStatus().equals(AppConstant.ALLOCATED))
                    if(customer.getTable().length() > 0)
                        status=AppConstant.SEATED;
                        realm.beginTransaction();
                        customer.setStatus(status);
                        UIUtil.hideKeyboard((Activity) context);
                       // Utility.hideKeyboard(context,holder.idBtnStatusChange);
                    if(customer.getStatus().equals(AppConstant.SEATED))
                        customerList.remove(position);
                        realm.commitTransaction();
                        notifyDataSetChanged();
                }
            });



            holder.getIvContextMenulist.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Context wrapper = new ContextThemeWrapper(context, R.style.YOURSTYLE);
                    PopupMenu popup = new PopupMenu(wrapper, holder.getIvContextMenulist);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.context_menu, popup.getMenu());
                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {
                                case R.id.edit_item:
                                    listener.onItemEditClick(customer.getId(), position);
                                    return true;
                                case R.id.delete_item:
                                    listener.onItemDeleteClick(customer.getId(), position);
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });

                    popup.show(); //showing popup menu

                }

            });

            holder.idIvWhatsappCustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String msg = "hello..!";
                    //listener.onItemDeleteClick(customer.getId(),position);
                    boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
                       if(isWhatsappInstalled){
                           Intent sendIntent = new Intent("android.intent.action.MAIN");
                           sendIntent.setAction(Intent.ACTION_VIEW);
                           sendIntent.setPackage("com.whatsapp");
                           String url = "https://api.whatsapp.com/send?phone= +91" + customer.getMobile() + "&text=" + msg;
                           sendIntent.setData(Uri.parse(url));
                           if(sendIntent.resolveActivity(getPackageManager()) != null){
                               context.startActivity(sendIntent);
                           }
                       } else{
                           Uri uri = Uri.parse("market://details?id=com.whatsapp");
                           Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                           Toast.makeText(context, "App is currently not installed on your phone", Toast.LENGTH_SHORT).show();
                          // context.startActivity(goToMarket);

                       }
                }

                private boolean whatsappInstalledOrNot(String uri) {
                    PackageManager pm = getPackageManager();
                    boolean app_installed;
                    try {
                        pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
                        app_installed = true;
                    } catch (PackageManager.NameNotFoundException e) {
                        app_installed = false;
                        Toast.makeText(context, "App is currently not installed on your phone.........!", Toast.LENGTH_SHORT).show();
                    }
                    return app_installed;
                }


                private PackageManager getPackageManager() {
                    return context.getPackageManager();
                }

            });

            holder.idIvMessageCustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   //  String number = "9824506713";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms",customer.getMobile(), null));
                    intent.putExtra("sms_body", "Hello....!");
                   // Log.d("send sms ", String.valueOf(intent));
                    context.startActivity(intent);

                }

            });

            holder.idIvCallCustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+customer.getMobile()));
                    context.startActivity(intent);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


    public void setDataSet(RealmResults customerRealmList){
        mAdapter.setDateSet(customerRealmList);
        if(customerRealmList!=null&& customerRealmList.size()>0){
            idTvnodatafound.setVisibility(View.GONE);
        } else{
            idTvnodatafound.setText(View.VISIBLE);
        }
    }

    public void updateCustomer(Customer customer,int position){
        customerList.set(position,customer);
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

        public void getFilter(CharSequence charSequence) {

        String charString = charSequence.toString();
        List<Customer> filteredList=new ArrayList<>();
        if (charString.isEmpty()) {
            filteredList = customerListOriginal;
        } else {

            for (Customer row : customerListOriginal) {
                // name match condition. this might differ depending on your requirement
                // here we are looking for name or phone number match
                if (row.toString().toLowerCase().contains(charString.toLowerCase())) {
                    filteredList.add(row);
                }
            }

        }
        customerList.clear();
        customerList.addAll(filteredList);
        notifyDataSetChanged();
    }
}