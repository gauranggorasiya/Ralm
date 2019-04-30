package wait.list.manager.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;
import wait.list.manager.R;
import wait.list.manager.model.Customer;
import wait.list.manager.utility.AppConstant;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private List<Customer> customerListOriginal;
    private List<Customer> customerList=new ArrayList<>();
    private TextView idTvnodatafound;
    private List<Customer> customerData;
    private HistoryAdapter mAdapter;
    Context context;

    public void setDataSet(RealmResults customerRealmList){
        mAdapter.setDateSet(customerRealmList);
        if(customerRealmList!=null&& customerRealmList.size()>0){
            idTvnodatafound.setVisibility(View.GONE);
        } else{
            idTvnodatafound.setText(View.VISIBLE);
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView CustomerName,Numberofcuctomer,idTvDate;
        private ImageView idIvWhatsappCustomer,idIvCallCustomer,idIvMessageCustomer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            CustomerName = itemView.findViewById(R.id.idTvCustomerName);
            idTvDate = itemView.findViewById(R.id.idTvDate);
            Numberofcuctomer = itemView.findViewById(R.id.idTvNumberOfPerson);
            idIvWhatsappCustomer = itemView.findViewById(R.id.idIVWhatsAppCustomer);
            idIvMessageCustomer = itemView.findViewById(R.id.idIVMessageCustomer);
            idIvCallCustomer = itemView.findViewById(R.id.idTvCustomerPhone);
        }
    }

    public void setDateSet(List<Customer> dataSet){
        customerListOriginal=dataSet;
        customerList.clear();
        customerList.addAll(dataSet);
        notifyDataSetChanged();
    }

    public HistoryAdapter(@NonNull Context context, List<Customer> customerList) {
        this.context = context;
        this.customerData = customerList;
        this.customerListOriginal=customerList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View customerview = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.history_customer_list, viewGroup, false);
        return new MyViewHolder(customerview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Customer customer = customerData.get(i);
        if(customer.getStatus().equals(AppConstant.SEATED)){
            myViewHolder.CustomerName.setText(customer.getFullName());
            myViewHolder.idTvDate.setText(customer.getDate());
            myViewHolder.Numberofcuctomer.setText(customer.getTable());
            //myViewHolder.
            myViewHolder.idIvWhatsappCustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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

            myViewHolder.idIvMessageCustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  String number = "9824506713";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms",customer.getMobile(), null));
                    intent.putExtra("sms_body", "Hello....!");
                    // Log.d("send sms ", String.valueOf(intent));
                    context.startActivity(intent);

                }

            });

            myViewHolder.idIvCallCustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+customer.getMobile()));
                    context.startActivity(intent);

                }
            });

       }

    }

    @Override
    public int getItemCount() {
        return customerData.size();
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
        customerData.clear();
        customerData.addAll(filteredList);
        notifyDataSetChanged();
    }


}
