package genesis.controllers.products;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import genesis.domain.models.Product;
import genesis.domain.models.ProductLab;

/**
 * Created by KG on 16/3/20.
 */
public class ProductPagerActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private ArrayList<Product> mProducts;

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

        int productId = getIntent().getIntExtra(ProductFragment.EXTRA_PRODUCT_ID, 0);
        for (int i = 0; i < mProducts.size(); i++) {
            if (mProducts.get(i).getId() == productId) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
