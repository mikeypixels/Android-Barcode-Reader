package info.androidhive.barcodereader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.barcodereader.SQLiteDatabaseFolder.DatabaseHandler;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    Context context;
    List<Product> products;
    DatabaseHandler db;

    public ProductsAdapter(Context context, ArrayList<Product> products){
        this.context = context;
        this.products = products;
        db = new DatabaseHandler(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView id, product_name, quantity, barcode;

        public ViewHolder(View itemView){
            super(itemView);

            id = itemView.findViewById(R.id.sn);
            product_name = itemView.findViewById(R.id.product_name);
            quantity = itemView.findViewById(R.id.quantity);
            barcode = itemView.findViewById(R.id.barcode);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id.setText(String.valueOf(products.get(position).getId()));
        holder.product_name.setText(products.get(position).getProduct_name());
        holder.quantity.setText(String.valueOf(db.getQuantity(products.get(position).getBarcode())));
        holder.barcode.setText(products.get(position).getBarcode());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
