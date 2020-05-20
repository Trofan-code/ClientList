package com.example.clientlist.dataBase;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clientlist.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolderData> {
    private List<Client> clientListArray;
    private  AdapterOnItemClicked adapterOnItemClicked;
    private Context context;
    private SharedPreferences def_pref;

    private int[] imgColorArray = {R.drawable.circle_red,R.drawable.circle_blue,R.drawable.circle_green};

    public DataAdapter(List<Client> clientListArray, AdapterOnItemClicked adapterOnItemClicked, Context context) {
        this.clientListArray = clientListArray;
        this.adapterOnItemClicked = adapterOnItemClicked;
        this.context = context;
        def_pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ViewHolderData(view, adapterOnItemClicked);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderData holder, int position) {
        holder.setData(clientListArray.get(position));

    }

    @Override
    public int getItemCount() {
        return clientListArray.size();
    }

    public class ViewHolderData extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvName;
        ImageView imgImportance;
        ImageView imgSpecial;
        TextView tvSecondName;
        TextView tvPhoneNumber;
        private  AdapterOnItemClicked adapterOnItemClicked;


        public ViewHolderData(@NonNull View itemView, AdapterOnItemClicked adapterOnItemClicked) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            imgImportance = itemView.findViewById(R.id.imageImportance);
            imgSpecial = itemView.findViewById(R.id.imageSpecial);
            tvSecondName = itemView.findViewById(R.id.tvSecName);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhone);
            this.adapterOnItemClicked = adapterOnItemClicked;
            itemView.setOnClickListener(this);
        }

        public void setData(Client client) {
            tvName.setTextColor(Color.parseColor(def_pref.getString(context.getResources()
                            .getString(R.string.text_name_colors_key),"#000000")));
            tvName.setText(client.getName());
            tvSecondName.setTextColor(Color.parseColor(def_pref.getString(context.getResources()
                    .getString(R.string.text_surname_colors_key),"#000000")));
            tvSecondName.setText(client.getSecond_name());
            tvPhoneNumber.setText(client.getPhone_number());
            imgImportance.setImageResource(imgColorArray[client.getImportance()]);
            if (client.getSpecial() == 1){
                imgSpecial.setVisibility(View.VISIBLE);
            }else{
                imgSpecial.setVisibility(View.GONE );
            }
        }

        @Override
        public void onClick(View v) {
            adapterOnItemClicked.onAdapterItemClicked(getAdapterPosition());
        }
    }
    public interface  AdapterOnItemClicked{
        void onAdapterItemClicked(int position);
    }
    public void updateAdapter(List<Client> clientList){

            clientListArray.clear();
            clientListArray.addAll(clientList);
            notifyDataSetChanged();

    }
}
