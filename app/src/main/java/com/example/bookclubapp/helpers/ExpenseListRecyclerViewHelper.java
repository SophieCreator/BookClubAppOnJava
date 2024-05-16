package com.example.bookclubapp.helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bookclubapp.AdminExpenseDetails;
import com.example.bookclubapp.R;
import com.example.bookclubapp.models.Expense;

import java.util.List;
import java.util.Objects;

public class ExpenseListRecyclerViewHelper extends RecyclerView.Adapter<ExpenseListRecyclerViewHelper.ExpenseListViewHolder> {

    private List<Expense> expenseListItems;
    private Context context;

    public ExpenseListRecyclerViewHelper(List<Expense> expenseListItems, Context context){
        this.expenseListItems = expenseListItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ExpenseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_list, parent, false);
        return new ExpenseListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseListViewHolder holder, int position) {
        Expense expense = this.expenseListItems.get(position);

        holder.expenseName.setText(expense.getName());
        holder.expenseUrl.setText(expense.getUrl());
        holder.expenseSum.setText(String.valueOf(expense.getSum()));
        holder.expenseDate.setText(expense.getDate().toString());
        if (Objects.equals(expense.getIs_regular(), "1")){
            holder.expenseIsRegular.setText("регулярный");
        } else {
            holder.expenseIsRegular.setText("не регулярный");
        }

        holder.expenseItemLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, "You clicked: " + expense.getExpense_id(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, AdminExpenseDetails.class);
                intent.putExtra("expenseId", String.valueOf(expense.getExpense_id()));
                intent.putExtra("expenseName", expense.getName());
                intent.putExtra("expenseUrl", expense.getUrl());
                intent.putExtra("expenseSum", String.valueOf(expense.getSum()));
                intent.putExtra("expenseDate", String.valueOf(expense.getDate()));
                intent.putExtra("expenseIsRegular", expense.getIs_regular());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return expenseListItems.size();
    }

    public class ExpenseListViewHolder extends RecyclerView.ViewHolder{
        public TextView expenseId, expenseName, expenseUrl, expenseSum, expenseDate, expenseIsRegular;
        private LinearLayout expenseItemLayout;
        public ExpenseListViewHolder(@NonNull View itemView) {
            super(itemView);
            expenseName = itemView.findViewById(R.id.expense_name);
            expenseUrl = itemView.findViewById(R.id.expense_url);
            expenseSum = itemView.findViewById(R.id.expense_amount);
            expenseDate = itemView.findViewById(R.id.expense_date);
            expenseIsRegular = itemView.findViewById(R.id.expense_regular);
            expenseItemLayout = itemView.findViewById(R.id.expenseItemLayout);
        }
    }
}


