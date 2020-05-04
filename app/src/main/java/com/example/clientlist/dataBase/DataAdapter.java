package com.example.clientlist.dataBase;

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

    private int[] imgColorArray = {R.drawable.circle_red,R.drawable.circle_green,R.drawable.circle_blue};

    public DataAdapter(List<Client> clientListArray, AdapterOnItemClicked adapterOnItemClicked) {
        this.clientListArray = clientListArray;
        this.adapterOnItemClicked = adapterOnItemClicked;
    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ViewHolderData(view,adapterOnItemClicked);
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
            tvName.setText(client.getName());
            tvSecondName.setText(client.getSecond_name());
            tvPhoneNumber.setText(client.getPhone_number());
            imgImportance.setImageResource(imgColorArray[client.getImportance()]);
            if (client.getSpecial() == 1){
                imgSpecial.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            adapterOnItemClicked.onAdapterItemCliked(getAdapterPosition());
        }
    }
    public interface  AdapterOnItemClicked{
        void onAdapterItemCliked(int position);
    }
}
