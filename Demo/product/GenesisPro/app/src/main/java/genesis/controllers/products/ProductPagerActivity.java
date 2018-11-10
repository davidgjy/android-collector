package genesis.controllers.products;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import genesis.domain.models.Product;
import genesis.domain.models.ProductLab;

/**
 * Created by KG on 16/3/20.
 */
public class ProductPagerActivity extends AppCompatActivity {
    private static final String EXTRA_PRODUCT_ID =
            "com.genesis.genesis_origin_pro.product_id";

    private ViewPager mViewPager;
    private List<Product> mProducts;

    public static Intent newIntent(Context packageContext, UUID productId) {
        Intent intent = new Intent(packageContext, ProductPagerActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        mProducts = ProductLab.get(this).getProducts();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Product product = mProducts.get(position);
                return ProductFragment.newInstance(product.getId());
            }

            @Override
            public int getCount() {
                return mProducts.size();
            }
        });

        UUID productId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_PRODUCT_ID);
        for (int i = 0; i < mProducts.size(); i++) {
            if (mProducts.get(i).getId().equals(productId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
