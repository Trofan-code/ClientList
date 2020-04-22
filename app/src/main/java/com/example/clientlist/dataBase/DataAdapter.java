package com.example.clientlist.dataBase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clientlist.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolderData> {
    private List<Client> clientListArray;

    public DataAdapter(List<Client> clientListArray) {
        this.clientListArray = clientListArray;
    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ViewHolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderData holder, int position) {
        holder.setData(clientListArray.get(position));

    }

    @Override
    public int getItemCount() {
        return clientListArray.size();
    }

    public class ViewHolderData extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvImportance;
        TextView tvSecondName;
        TextView tvPhoneNumber;
        public ViewHolderData(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvImportance = itemView.findViewById(R.id.tvImportance);
            tvSecondName = itemView.findViewById(R.id.tvSecName);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhone);
        }

        public void setData(Client client) {
            tvName.setText(client.getName());
            tvImportance.setText(String.valueOf(client.getImportance()));
            tvSecondName.setText(client.getSecond_name());
            tvPhoneNumber.setText(client.getPhone_number());
        }
    }
}
