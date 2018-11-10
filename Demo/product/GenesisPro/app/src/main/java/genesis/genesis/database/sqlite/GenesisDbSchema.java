package genesis.genesis.database.sqlite;

/**
 * Created by KG on 16/4/13.
 */
public class GenesisDbSchema {
    public static final class ProductTable {
        public static final String NAME = "products";

        public static final class Cols {
            public static final String PRODUCT_ID = "product_id";
            public static final String NAME = "name";
            public static final String DESCRIPTION = "description";
            public static final String CHANNEL_ID = "channel_id";
            public static final String COVER = "cover";
            public static final String ATTACHMENT = "attachment";
            public static final String STATUS_ID = "status_id";
            public static final String CREATE_TIME = "create_time";
            public static final String LAST_UPDA = "last_upda";
        }
    }
}

