package genesis.domain.models;

import android.content.Context;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by KG on 16/3/15.
 */
public class ProductLab {
    private ArrayList<Product> mProducts;

    private static ProductLab sProductLab;
    //private Context mAppContext;

    private ProductLab(Context appContext) {
        //mAppContext = appContext;
        mProducts = new ArrayList<Product>();
        /*
        for (int i = 0; i < 100; i++) {
            Product product = new Product();
            product.setId(i + 1);
            product.setName("Product #" + i);
            product.setDescription("This is a test description for product # " + i);
            product.setCreateTime(new Date());
            product.setLastUpda(new Date());
            mProducts.add(product);
        }
        */
    }

    public void addProduct(Product p) {
        mProducts.add(p);
    }

    public static ProductLab get(Context c) {
        if (sProductLab == null) {
            sProductLab = new ProductLab(c.getApplicationContext());
        }
        return sProductLab;
    }

    public Product getProduct(int id) {
        for (Product p : mProducts) {
            if (p.getId() == id)
                return p;
        }
        return null;
    }

    public ArrayList<Product> getProducts() {
        return mProducts;
    }
}
