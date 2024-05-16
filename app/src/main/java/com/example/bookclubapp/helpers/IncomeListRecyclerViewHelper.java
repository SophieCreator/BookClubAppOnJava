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


import com.example.bookclubapp.AdminIncomeDetails;
import com.example.bookclubapp.R;
import com.example.bookclubapp.models.Income;

import java.util.List;

public class IncomeListRecyclerViewHelper extends RecyclerView.Adapter<IncomeListRecyclerViewHelper.IncomeListViewHolder> {

    private List<Income> incomeListItems;
    private Context context;

    public IncomeListRecyclerViewHelper(List<Income> incomeListItems, Context context){
        this.incomeListItems = incomeListItems;
        this.context = context;
    }

    @NonNull
    @Override
    public IncomeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.income_list, parent, false);
        return new IncomeListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeListViewHolder holder, int position) {
        Income income = this.incomeListItems.get(position);

        holder.incomeName.setText(income.getName());
        holder.incomeAmount.setText(String.valueOf(income.getAmount()));
        holder.incomeDate.setText(income.getDate_get().toString());
        holder.incomeItemLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, "You clicked: " + income.getIncome_id(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, AdminIncomeDetails.class);
                intent.putExtra("incomeId", String.valueOf(income.getIncome_id()));
                intent.putExtra("incomeName", income.getName());
                intent.putExtra("incomeAmount", String.valueOf(income.getAmount()));
                intent.putExtra("incomeDate", String.valueOf(income.getDate_get()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return incomeListItems.size();
    }

    public class IncomeListViewHolder extends RecyclerView.ViewHolder{
        public TextView incomeId, incomeName, incomeAmount, incomeDate;
        private LinearLayout incomeItemLayout;
        public IncomeListViewHolder(@NonNull View itemView) {
            super(itemView);
            incomeName = itemView.findViewById(R.id.income_name);
            incomeAmount = itemView.findViewById(R.id.income_amount);
            incomeDate = itemView.findViewById(R.id.income_date);
            incomeItemLayout = itemView.findViewById(R.id.incomeItemLayout);
        }
    }
}


