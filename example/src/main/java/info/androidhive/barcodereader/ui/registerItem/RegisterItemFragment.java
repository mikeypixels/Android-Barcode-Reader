package info.androidhive.barcodereader.ui.registerItem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.vision.barcode.Barcode;

import info.androidhive.barcodereader.BarcodeScanRegisterItemActivity;
import info.androidhive.barcodereader.Product;
import info.androidhive.barcodereader.R;
import info.androidhive.barcodereader.SQLiteDatabaseFolder.DatabaseHandler;

public class RegisterItemFragment extends Fragment {

    private RegisterItemViewModel galleryViewModel;

    public static EditText barcodeEdit;

    DatabaseHandler db = null;

    String TAG = this.getClass().getSimpleName();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(RegisterItemViewModel.class);
        View root = inflater.inflate(R.layout.fragment_register_item, container, false);
        barcodeEdit = root.findViewById(R.id.barcode_edittext);
        final TextView name_edittext = root.findViewById(R.id.name_edittext);
        final TextView price_edittext = root.findViewById(R.id.price_edittext);
        Button reg_btn = root.findViewById(R.id.reg_button);

        barcodeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BarcodeScanRegisterItemActivity.class);
                startActivity(intent);
            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name_edittext.getText().toString().isEmpty() || price_edittext.getText().toString().isEmpty() || barcodeEdit.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show();
                }else{
                    if(db.addProduct(new Product(1, Double.parseDouble(price_edittext.getText().toString().replaceAll(",","")), name_edittext.getText().toString(), barcodeEdit.getText().toString()))){
                        Toast.makeText(getContext(), "Product added!", Toast.LENGTH_SHORT).show();
                        price_edittext.setText("");
                        name_edittext.setText("");
                        barcodeEdit.setText("");
                    }else{
                        Toast.makeText(getContext(), "Product was not added!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
//
//        Log.d(TAG, "It reaches here and the barcode string is: " + preferences.getString("barcodeString",""));

//        assert getArguments() != null;
//        String barcode = getArguments().getString("barcodeNumber");
//
//        if(!barcode.equals("")){
//            Log.d(TAG, "It reaches here and the barcode string is: " + barcode);
//            barcodeEdit.setText(barcode);
//        }
//        final TextView textView = root.findViewById(R.id.text_gallery);
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        db = new DatabaseHandler(getContext());
    }
}