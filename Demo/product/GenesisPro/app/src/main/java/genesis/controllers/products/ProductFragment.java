package genesis.controllers.products;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.Date;
import java.util.UUID;

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
    private static final int REQUEST_PHOTO= 1;

    private static final String TAG = "ProductFragment";
    private Product mProduct;
    private File mPhotoFile;
    private EditText mNameField;
    private EditText mDescriptionField;
    private Button mLastUpdate;
    private Button mReportButton;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;

    public static ProductFragment newInstance(UUID productId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT_ID, productId);

        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // this is very important!!!
        UUID productId = (UUID) getArguments().getSerializable(ARG_PRODUCT_ID);
        //Log.d(TAG, "product id from intent extra2: " + productId.intValue());
        mProduct = ProductLab.get(getActivity()).getProduct(productId);

        mPhotoFile = ProductLab.get(getActivity()).getPhotoFile(mProduct);
    }

    @Override
    public void onPause() {
        super.onPause();

        ProductLab.get(getActivity())
                .updateProduct(mProduct);
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

        mReportButton = (Button)v.findViewById(R.id.crime_report);
        mReportButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getProductReport());
                i.putExtra(Intent.EXTRA_SUBJECT,
                        getString(R.string.product_report_subject));
                i = Intent.createChooser(i, getString(R.string.send_report));

                startActivity(i);
            }
        });

        mPhotoButton = (ImageButton) v.findViewById(R.id.product_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // To check if there is no camera app or there is no location
        // at which to save the photo
        PackageManager packageManager = getActivity().getPackageManager();

        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);

        /*
        if (!canTakePhoto) {
            Toast.makeText(getActivity(),
                    "Can not take photo!", Toast.LENGTH_SHORT)
                    .show();
        }
        */

        if (canTakePhoto) {
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mPhotoView = (ImageView) v.findViewById(R.id.product_photo);

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

    private String getProductReport() {
        String createTimeString = DateTimeUtil.getDateTimeByFormat(mProduct.getCreateTime(), "yyyy-MM-dd hh:mm:ss");
        String lastUpdaString = DateTimeUtil.getDateTimeByFormat(mProduct.getLastUpda(), "yyyy-MM-dd hh:mm:ss");
        String report = getString(R.string.product_report,
                mProduct.getName(), mProduct.getDescription(), createTimeString, lastUpdaString);
        return report;
    }
}
