package genesis.genesis.database.sqlite;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import genesis.domain.models.Product;
import genesis.util.gdatetime.DateTimeUtil;

/**
 * Created by KG on 16/4/14.
 */
public class ProductCursorWrapper extends CursorWrapper {
    public ProductCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Product getProduct() {
        String productId = getString(getColumnIndex(GenesisDbSchema.ProductTable.Cols.PRODUCT_ID));
        String name = getString(getColumnIndex(GenesisDbSchema.ProductTable.Cols.NAME));
        String description = getString(getColumnIndex(GenesisDbSchema.ProductTable.Cols.DESCRIPTION));
        int channelId = getInt(getColumnIndex(GenesisDbSchema.ProductTable.Cols.CHANNEL_ID));
        String cover = getString(getColumnIndex(GenesisDbSchema.ProductTable.Cols.COVER));
        String attachment = getString(getColumnIndex(GenesisDbSchema.ProductTable.Cols.ATTACHMENT));
        int statusId = getInt(getColumnIndex(GenesisDbSchema.ProductTable.Cols.STATUS_ID));
        String createTime = getString(getColumnIndex(GenesisDbSchema.ProductTable.Cols.CREATE_TIME));
        String lastUpda = getString(getColumnIndex(GenesisDbSchema.ProductTable.Cols.LAST_UPDA));

        Product product = new Product(UUID.fromString(productId));
        product.setName(name);
        product.setDescription(description);
        product.setChannelId(channelId);
        product.setCover(cover);
        product.setAttachment(attachment);
        product.setStatusId(statusId);
        product.setCreateTime(DateTimeUtil.convertDateFromStringFormat(createTime, "yyyy-MM-dd HH:mm:ss"));
        product.setLastUpda(DateTimeUtil.convertDateFromStringFormat(lastUpda, "yyyy-MM-dd HH:mm:ss"));

        return product;
    }
}
