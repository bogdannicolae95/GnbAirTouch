package com.example.gnbproject.ux.adaperts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gnbproject.GnbApp;
import com.example.gnbproject.R;
import com.example.gnbproject.domain.Transaction;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.RecyclerViewHolder> {

    List<Transaction> listOfTransaction;

    public void setListOfTransaction(List<Transaction> listOfTransaction) {
        this.listOfTransaction = listOfTransaction;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TransactionAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transaction_row,viewGroup,false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.RecyclerViewHolder viewHolder, int position) {
        Transaction currentTransaction = listOfTransaction.get(position);

        viewHolder.transactionSku.setText(currentTransaction.getSku());
        viewHolder.transactionAmount.setText(currentTransaction.getAmountString());
        viewHolder.transactionCurrency.setText(currentTransaction.getCurrency());
    }

    @Override
    public int getItemCount() {
        return listOfTransaction != null ? listOfTransaction.size() : 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView transactionSku,transactionAmount,transactionCurrency;

        public RecyclerViewHolder(@NonNull View view) {
            super(view);

            transactionSku = view.findViewById(R.id.transaction_row_sku);
            transactionAmount = view.findViewById(R.id.transaction_row_amount);
            transactionCurrency = view.findViewById(R.id.transaction_row_currency);

        }
    }
}
