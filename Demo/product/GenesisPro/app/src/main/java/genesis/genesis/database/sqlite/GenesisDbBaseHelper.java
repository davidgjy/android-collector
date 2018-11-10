package genesis.genesis.database.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by KG on 16/4/13.
 */
public class GenesisDbBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "genesis.db";

    public GenesisDbBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + GenesisDbSchema.ProductTable.NAME + "(" +
                        " _id integer primary key autoincrement, " +
                        GenesisDbSchema.ProductTable.Cols.PRODUCT_ID + ", " +
                        GenesisDbSchema.ProductTable.Cols.NAME + ", " +
                        GenesisDbSchema.ProductTable.Cols.DESCRIPTION + ", " +
                        GenesisDbSchema.ProductTable.Cols.CHANNEL_ID + ", " +
                        GenesisDbSchema.ProductTable.Cols.COVER + ", " +
                        GenesisDbSchema.ProductTable.Cols.ATTACHMENT + ", " +
                        GenesisDbSchema.ProductTable.Cols.STATUS_ID + ", " +
                        GenesisDbSchema.ProductTable.Cols.CREATE_TIME + ", " +
                        GenesisDbSchema.ProductTable.Cols.LAST_UPDA +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
