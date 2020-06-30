package info.androidhive.barcodereader;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.ViewHolder> {

    Context context;
    List<Sale> sales;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public SalesAdapter(Context context, ArrayList<Sale> sales){
        this.context = context;
        this.sales = sales;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView sn, product_name, date, amount;
        ConstraintLayout itemLayout;

        public ViewHolder(View itemView){
            super(itemView);
            sn = itemView.findViewById(R.id.no);
            product_name = itemView.findViewById(R.id.product_name);
            date = itemView.findViewById(R.id.date);
            amount = itemView.findViewById(R.id.amount);
            itemLayout = itemView.findViewById(R.id.const_itemLayout);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sale_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        holder.itemLayout.setBackgroundColor(Color.WHITE);
        holder.sn.setText(String.valueOf(sales.get(position).getId()));
        holder.sn.setTextColor(Color.BLACK);
        holder.date.setText(simpleDateFormat.format(sales.get(position).getDate()));
        holder.date.setTextColor(Color.BLACK);
        holder.product_name.setText(String.valueOf(sales.get(position).getProduct_name()));
        holder.product_name.setTextColor(Color.BLACK);
        holder.amount.setText(String.valueOf(sales.get(position).getAmount()));
        holder.amount.setTextColor(Color.BLACK);
    }

    @Override
    public int getItemCount() {
        return sales.size();
    }
}
