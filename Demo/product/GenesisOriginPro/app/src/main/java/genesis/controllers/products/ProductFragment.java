package genesis.controllers.products;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

import genesis.domain.models.Product;
import genesis.domain.models.ProductLab;
import genesis.util.gdatetime.DateTimeUtil;

/**
 * Created by KG on 16/3/11.
 */
public class ProductFragment extends Fragment {
    public static final String ARG_PRODUCT_ID = "GenesisOriginPro.PRODUCT_ID";

    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;

    private static final String TAG = "ProductFragment";
    private Product mProduct;
    private EditText mNameField;
    private EditText mDescriptionField;
    private Button mLastUpdate;

    public static ProductFragment newInstance(int productId) {
        Bundle args = new Bundle();
        args.putInt(ARG_PRODUCT_ID, productId);

        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // this is very important!!!
        int productId = getArguments().getInt(ARG_PRODUCT_ID);
        //Log.d(TAG, "product id from intent extra2: " + productId.intValue());
        mProduct = ProductLab.get(getActivity()).getProduct(productId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product, parent, false);

        mNameField = (EditText)v.findViewById(R.id.product_name);
        mNameField.setText(mProduct.getName());
        mNameField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mProduct.setName(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mDescriptionField = (EditText)v.findViewById(R.id.product_description);
        mDescriptionField.setText(mProduct.getDescription());
        mDescriptionField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mProduct.setDescription(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mLastUpdate = (Button)v.findViewById(R.id.product_last_update);
        mLastUpdate.setText(DateTimeUtil.getDateTimeByFormat(mProduct.getLastUpda(), "yyyy-MM-dd hh:mm:ss"));
        updateDate();
        mLastUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mProduct.getLastUpda());
                // this is very important to set target fragment of dialog to ProductFragment!!!
                dialog.setTargetFragment(ProductFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        return v;
    }

    public void updateDate() {
        mLastUpdate.setText(DateTimeUtil.getDateTimeByFormat(mProduct.getLastUpda(), "yyyy-MM-dd hh:mm:ss"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mProduct.setLastUpda(date);
            updateDate();
        }
    }
}
