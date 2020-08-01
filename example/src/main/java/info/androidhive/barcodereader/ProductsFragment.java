package info.androidhive.barcodereader;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.androidhive.barcodereader.SQLiteDatabaseFolder.DatabaseHandler;

public class ProductsFragment extends Fragment {

    RecyclerView.LayoutManager layoutManager;
    DatabaseHandler db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ProductsAdapter adapter = new ProductsAdapter(getContext(), db.getAllProducts());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        db = new DatabaseHandler(getContext());
    }
}