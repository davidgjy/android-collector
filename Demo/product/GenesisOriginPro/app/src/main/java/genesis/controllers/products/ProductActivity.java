package genesis.controllers.products;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

import genesis.domain.models.Product;

public class ProductActivity extends SingleFragmentActivity {

    private static final String EXTRA_PRODUCT_ID =
            "com.genesis.genesis_origin_pro.product_id";

    public static Intent newIntent(Context packageContext, int productId) {
        Intent intent = new Intent(packageContext, ProductActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int productId = getIntent()
                .getIntExtra(EXTRA_PRODUCT_ID, 0);
        return ProductFragment.newInstance(productId);
    }
}
