package genesis.controllers.products;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import genesis.domain.models.Product;
import genesis.domain.models.ProductLab;
import genesis.util.gdatetime.DateTimeUtil;

/**
 * Created by KG on 16/3/15.
 */
public class ProductListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mCrimeRecyclerView;
    private ProductAdapter mAdapter;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.product_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_product_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_product:
                Product product = new Product();
                ProductLab.get(getActivity()).addProduct(product);
                Intent intent = ProductPagerActivity
                        .newIntent(getActivity(), product.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        ProductLab productLab = ProductLab.get(getActivity());
        int productsCount = productLab.getProducts().size();
        String subtitle = getString(R.string.subtitle_format, productsCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        ProductLab productLab = ProductLab.get(getActivity());
        List<Product> products = productLab.getProducts();

        if (mAdapter == null) {
            mAdapter = new ProductAdapter(products);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private class ProductHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mNameTextView;
        private TextView mDescriptionTextView;
        private TextView mLastUpda;

        private Product mProduct;

        public ProductHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mNameTextView = (TextView) itemView.findViewById(R.id.product_list_item_nameTextView);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.product_list_item_descriptionTextView);
            mLastUpda = (TextView) itemView.findViewById(R.id.product_list_item_dateLastUpda);
        }

        public void bindProduct(Product product) {
            mProduct = product;
            mNameTextView.setText(mProduct.getName());
            mDescriptionTextView.setText(mProduct.getDescription());
            mLastUpda.setText(DateTimeUtil.getDateTimeByFormat(mProduct.getLastUpda(), "yyyy-MM-dd hh:mm:ss"));
        }

        @Override
        public void onClick(View v) {
            /*
            Toast.makeText(getActivity(),
                    mProduct.getName() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
            */
            //Intent intent = ProductActivity.newIntent(getActivity(), mProduct.getId());
            Intent intent = ProductPagerActivity.newIntent(getActivity(), mProduct.getId());
            startActivity(intent);
        }
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {

        private List<Product> mProducts;

        public ProductAdapter(List<Product> products) {
            mProducts = products;
        }

        @Override
        public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_product, parent, false);
            return new ProductHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductHolder holder, int position) {
            Product product = mProducts.get(position);
            holder.bindProduct(product);
        }

        @Override
        public int getItemCount() {
            return mProducts.size();
        }
    }
}
