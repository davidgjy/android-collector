package genesis.domain.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import genesis.genesis.database.sqlite.GenesisDbBaseHelper;
import genesis.genesis.database.sqlite.GenesisDbSchema;
import genesis.genesis.database.sqlite.ProductCursorWrapper;
import genesis.util.gdatetime.DateTimeUtil;

/**
 * Created by KG on 16/3/15.
 */
public class ProductLab {
    //private ArrayList<Product> mProducts;
    private static ProductLab sProductLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private ProductLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new GenesisDbBaseHelper(mContext).getWritableDatabase();
        //mProducts = new ArrayList<Product>();
    }

    public static ProductLab get(Context c) {
        if (sProductLab == null) {
            sProductLab = new ProductLab(c.getApplicationContext());
        }
        return sProductLab;
    }

    public void addProduct(Product p) {
        //mProducts.add(p);
        ContentValues values = getContentValues(p);

        mDatabase.insert(GenesisDbSchema.ProductTable.NAME, null, values);
    }

    public void updateProduct(Product product) {
        String idString = product.getId().toString();
        ContentValues values = getContentValues(product);

        mDatabase.update(GenesisDbSchema.ProductTable.NAME, values,
                GenesisDbSchema.ProductTable.Cols.PRODUCT_ID + " = ?",
                new String[]{idString});
    }

    private ProductCursorWrapper queryProducts(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                GenesisDbSchema.ProductTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        return new ProductCursorWrapper(cursor);
    }

    public Product getProduct(UUID id) {
        ProductCursorWrapper cursor = queryProducts(
                GenesisDbSchema.ProductTable.Cols.PRODUCT_ID + " = ?",
                new String[]{id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getProduct();
        } finally {
            cursor.close();
        }
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();

        ProductCursorWrapper cursor = queryProducts(null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            products.add(cursor.getProduct());
            cursor.moveToNext();
        }
        cursor.close();

        return products;
    }

    public File getPhotoFile(Product product) {
        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (externalFilesDir == null) {
            Log.d("ProductLab", "get photo return null");
            return null;
        }

        return new File(externalFilesDir, product.getPhotoFilename());
    }

    private static ContentValues getContentValues(Product product) {
        ContentValues values = new ContentValues();
        values.put(GenesisDbSchema.ProductTable.Cols.PRODUCT_ID, product.getId().toString());
        values.put(GenesisDbSchema.ProductTable.Cols.NAME, product.getName());
        values.put(GenesisDbSchema.ProductTable.Cols.DESCRIPTION, product.getDescription());
        values.put(GenesisDbSchema.ProductTable.Cols.CHANNEL_ID, product.getChannelId());
        values.put(GenesisDbSchema.ProductTable.Cols.COVER, product.getCover());
        values.put(GenesisDbSchema.ProductTable.Cols.ATTACHMENT, product.getAttachment());
        values.put(GenesisDbSchema.ProductTable.Cols.STATUS_ID, product.getStatusId());
        values.put(GenesisDbSchema.ProductTable.Cols.CREATE_TIME, DateTimeUtil.getDateTimeByFormat(product.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        values.put(GenesisDbSchema.ProductTable.Cols.LAST_UPDA, DateTimeUtil.getDateTimeByFormat(product.getLastUpda(), "yyyy-MM-dd HH:mm:ss"));

        return values;
    }
}
