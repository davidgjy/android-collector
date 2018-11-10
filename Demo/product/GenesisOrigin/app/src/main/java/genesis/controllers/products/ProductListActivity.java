package genesis.controllers.products;

/**
 * Created by KG on 16/3/15.
 */

import android.support.v4.app.Fragment;

public class ProductListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ProductListFragment();
    }
}
