package genesis.controllers.products;

import android.support.v4.app.Fragment;
import genesis.domain.models.Product;

public class ProductActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        int productId = getIntent()
                .getIntExtra(ProductFragment.EXTRA_PRODUCT_ID, 0);
        return ProductFragment.newInstance(productId);
    }
}
