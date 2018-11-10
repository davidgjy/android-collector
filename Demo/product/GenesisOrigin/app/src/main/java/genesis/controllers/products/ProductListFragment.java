package genesis.controllers.products;

import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import genesis.domain.models.Product;
import genesis.domain.models.ProductLab;
import genesis.util.gdatetime.DateTimeUtil;

/**
 * Created by KG on 16/3/15.
 */
public class ProductListFragment extends ListFragment {
    private static final String TAG = "ProductListFragment";

    private ArrayList<Product> mProducts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.products_title);
        mProducts = ProductLab.get(getActivity()).getProducts();
        ProductAdapter adapter = new ProductAdapter(mProducts);
        setListAdapter(adapter);
    }

    /*
    @Override
    public void onResume() {
        super.onResume();
        ((ProductAdapter)getListAdapter()).notifyDataSetChanged();
    }
    */

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Product product = (Product)(getListAdapter()).getItem(position);
        Log.d(TAG, product.getName() + " was clicked, product id: " + product.getId());

        // start an instance of ProductPagerActivity
        Intent i = new Intent(getActivity(), ProductPagerActivity.class);

        i.putExtra(ProductFragment.EXTRA_PRODUCT_ID, product.getId());
        startActivityForResult(i, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((ProductAdapter)getListAdapter()).notifyDataSetChanged();
    }

    // provide data for ListView
    private class ProductAdapter extends ArrayAdapter<Product> {
        public ProductAdapter(ArrayList<Product> products) {
            super(getActivity(), android.R.layout.simple_list_item_1, products);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_product, null);
            }

            // configure the view for this Crime
            Product product = getItem(position);

            // Name
            TextView nameTextView =
                    (TextView)convertView.findViewById(R.id.product_list_item_nameTextView);
            nameTextView.setText(product.getName());

            // Description
            TextView descriptionTextView =
                    (TextView)convertView.findViewById(R.id.product_list_item_descriptionTextView);
            descriptionTextView.setText(product.getDescription());

            TextView dateTextView =
                    (TextView)convertView.findViewById(R.id.product_list_item_dateLastUpda);
            dateTextView.setText(DateTimeUtil.getDateTimeByFormat(product.getLastUpda(), "yyyy-MM-dd hh:mm:ss"));

            return convertView;
        }
    }
}
