package ie.ul.android.lab_week5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class BelgianBeersDB {

    /*********************
     * Definition of table columns
     ***********************************************/
    //The index (key) column name for use in where clauses.
    public static final String KEY_ID = "_id";

    //The name and column index of each column in your database.
    //These should be descriptive.
    public static final String KEY_BEER_NAME =
            "beer_name";
    public static final String KEY_ALCOHOL_PERCENTAGE =
            "alcohol_percentage";
    public static final String KEY_PRICE =
            "price";

    private Context context;

    // Database open/upgrade helper
    private ModuleDBOpenHelper moduleDBOpenHelper;

    /************************
     * Constructor
     ***************************************************************/
    public BelgianBeersDB(Context context) {
        this.context = context;
        moduleDBOpenHelper = new ModuleDBOpenHelper(context, ModuleDBOpenHelper.DATABASE_NAME, null,
                ModuleDBOpenHelper.DATABASE_VERSION);

        // populate the database with some data in case it is empty
        if (getAll().length == 0) {
            this.addRow("La Chouffe", 6.0f, 2.3f);
            this.addRow("La Trappe", 5.0f, 2.4f);
            this.addRow("Delirium Tremens", 9.0f, 3.5f);
            this.addRow("Duvel", 6.3f, 2.6f);
            this.addRow("Rochefort Nr10", 7.1f, 3.4f);
            this.addRow("Rochefort Nr12", 8.1f, 5.0f);
            this.addRow("Affligem", 4.5f, 1.2f);
            this.addRow("Kriek", 3.5f, 1.39f);
            this.addRow("Bush Beer", 13f, 5.3f);
            this.addRow("Mort Subite", 6.0f, 2.3f);
            this.addRow("Agnus Dei", 4.3f, 3.56f);
        }
    }

    /************************
     * Standard Database methods
     *************************************************/

    // Called when you no longer need access to the database.
    public void closeDatabase() {
        moduleDBOpenHelper.close();
    }

    public void addRow(String beerName, float alcoholPercentage, float price) {
        // Create a new row of values to insert.
        ContentValues newValues = new ContentValues();

        // Assign values for each row.
        newValues.put(KEY_BEER_NAME, beerName);
        newValues.put(KEY_ALCOHOL_PERCENTAGE, alcoholPercentage);
        newValues.put(KEY_PRICE, price);

        // Insert the row into your table
        SQLiteDatabase db = moduleDBOpenHelper.getWritableDatabase();
        db.insert(ModuleDBOpenHelper.DATABASE_TABLE, null, newValues);
    }

    public void deleteRow(int idNr) {
        // Specify a where clause that determines which row(s) to delete.
        // Specify where arguments as necessary.
        String where = KEY_ID + "=" + idNr;
        String whereArgs[] = null;

        // Delete the rows that match the where clause.
        SQLiteDatabase db = moduleDBOpenHelper.getWritableDatabase();
        db.delete(ModuleDBOpenHelper.DATABASE_TABLE, where, whereArgs);
    }

    public void deleteAll() {
        String where = null;
        String whereArgs[] = null;

        // Delete the rows that match the where clause.
        SQLiteDatabase db = moduleDBOpenHelper.getWritableDatabase();
        db.delete(ModuleDBOpenHelper.DATABASE_TABLE, where, whereArgs);
    }

    /************************
     * User specific database queries
     *******************************************/

    /*
     * Obtain all database entries and return as human readable content in a String array
     * A query with all fields set to null will result in the whole database being returned
     * The following SQL query is implemented: SELECT * FROM  Beers
     */
    public String[] getAll() {

        ArrayList<String> outputArray = new ArrayList<String>();
        String[] result_columns = new String[]{
                KEY_BEER_NAME, KEY_ALCOHOL_PERCENTAGE, KEY_PRICE};

        String beerName;
        float alcoholPercentage;
        float price;

        String where = null;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;

        SQLiteDatabase db = moduleDBOpenHelper.getWritableDatabase();
        Cursor cursor = db.query(ModuleDBOpenHelper.DATABASE_TABLE,
                result_columns, where,
                whereArgs, groupBy, having, order);
        //
        boolean result = cursor.moveToFirst();
        while (result) {
            beerName = cursor.getString(cursor.getColumnIndex(KEY_BEER_NAME));
            alcoholPercentage = cursor.getFloat(cursor.getColumnIndex(KEY_ALCOHOL_PERCENTAGE));
            price = cursor.getFloat(cursor.getColumnIndex(KEY_PRICE));

            outputArray.add(beerName + " " + context.getString(R.string.cost_str_1) + " " + alcoholPercentage + context.getString(R.string.cost_str_2) + price);
            result = cursor.moveToNext();

        }
        return outputArray.toArray(new String[outputArray.size()]);
    }

    /*
     * Obtain all database entries with a price lower than maxPrice and return as human readable content in a String array
     * The following SQL query is implemented: SELECT * FROM Beers WHERE price < maxPrice
     */
    public String[] getAllCheaperThan(Float maxPrice) {

        ArrayList<String> outputArray = new ArrayList<String>();
        String[] result_columns = new String[]{
                KEY_BEER_NAME, KEY_ALCOHOL_PERCENTAGE, KEY_PRICE};

        String beerName;
        float alcoholPercentage;
        float price;

        String where = KEY_PRICE + "< ?";
        String whereArgs[] = {maxPrice.toString()};
        String groupBy = null;
        String having = null;
        String order = null;

        SQLiteDatabase db = moduleDBOpenHelper.getWritableDatabase();
        Cursor cursor = db.query(ModuleDBOpenHelper.DATABASE_TABLE,
                result_columns, where,
                whereArgs, groupBy, having, order);
        //
        boolean result = cursor.moveToFirst();
        while (result) {
            beerName = cursor.getString(cursor.getColumnIndex(KEY_BEER_NAME));
            alcoholPercentage = cursor.getFloat(cursor.getColumnIndex(KEY_ALCOHOL_PERCENTAGE));
            price = cursor.getFloat(cursor.getColumnIndex(KEY_PRICE));

            outputArray.add(beerName + " " + context.getString(R.string.cost_str_1) + " " + alcoholPercentage + context.getString(R.string.cost_str_2) + price);
            result = cursor.moveToNext();
        }
        return outputArray.toArray(new String[outputArray.size()]);
    }


    /*
     * Obtain all database entries with an alcohol percentage higher than minPercentage and return as human readable content in a String array
     * The following SQL query is implemented: SELECT * FROM Beers WHERE alcohol_percentage > minPercentage
     */


    public String[] getAllStrongerThan(Float minPercentage) {
        ArrayList<String> outputArray = new ArrayList<String>();
        String[] result_columns = new String[]{
                KEY_BEER_NAME, KEY_ALCOHOL_PERCENTAGE, KEY_PRICE};

        String beerName;
        float alcoholPercentage;
        float price;

        String where = KEY_ALCOHOL_PERCENTAGE +"> ?";
        String whereArgs[] = {minPercentage.toString()};
        String groupBy = null;
        String having = null;
        String order = null;

        SQLiteDatabase db = moduleDBOpenHelper.getWritableDatabase();
        Cursor cursor = db.query(ModuleDBOpenHelper.DATABASE_TABLE,
                result_columns, where,
                whereArgs, groupBy, having, order);
        //
        boolean result = cursor.moveToFirst();
        while (result) {
            beerName = cursor.getString(cursor.getColumnIndex(KEY_BEER_NAME));
            alcoholPercentage = cursor.getFloat(cursor.getColumnIndex(KEY_ALCOHOL_PERCENTAGE));
            price = cursor.getFloat(cursor.getColumnIndex(KEY_PRICE));

            outputArray.add(beerName + " " + context.getString(R.string.cost_str_1) + " " + alcoholPercentage + context.getString(R.string.cost_str_2) + price);
            result = cursor.moveToNext();
        }
        return outputArray.toArray(new String[outputArray.size()]);
    }


    /*
     * Obtain all beer names from the database and return in String[]
     */
    public String[] getBeerNames() {

        ArrayList<String> outputArray = new ArrayList<String>();
        String[] result_columns = new String[]{
                KEY_BEER_NAME};

        String beerName;

        String where = null;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;

        SQLiteDatabase db = moduleDBOpenHelper.getWritableDatabase();
        Cursor cursor = db.query(ModuleDBOpenHelper.DATABASE_TABLE,
                result_columns, where,
                whereArgs, groupBy, having, order);
        //
        boolean result = cursor.moveToFirst();
        while (result) {
            beerName = cursor.getString(cursor.getColumnIndex(KEY_BEER_NAME));

            outputArray.add(beerName);
            result = cursor.moveToNext();
        }
        return outputArray.toArray(new String[outputArray.size()]);
    }

    /*
     * Return alcohol percentage of first beer found with name beerName. The following SQL query is implemented:
     * SELECT _id, alcohol_percentage FROM Beers WHERE beer_name = beerName
     */
    public float getAlcohol(String beerName) {
        String[] result_columns = new String[]{
                KEY_ID, KEY_ALCOHOL_PERCENTAGE};


        String where = KEY_BEER_NAME + "= ?";
        String whereArgs[] = {beerName};
        String groupBy = null;
        String having = null;
        String order = null;

        SQLiteDatabase db = moduleDBOpenHelper.getWritableDatabase();
        Cursor cursor = db.query(ModuleDBOpenHelper.DATABASE_TABLE,
                result_columns, where,
                whereArgs, groupBy, having, order);
        if (cursor.moveToFirst()) {
            int columnAlcoholPercentage = cursor.getColumnIndex(KEY_ALCOHOL_PERCENTAGE);
            return cursor.getFloat(columnAlcoholPercentage);
        } else return 0;
    }

    /*
     * Return price of first beer found with name beerName. The following SQL query is implemented:
     * SELECT _id, price FROM Beers WHERE beer_name = beerName
     */
    public float getPrice(String beerName) {

        String[] result_columns = new String[]{
                KEY_ID, KEY_PRICE};


        String where = KEY_BEER_NAME + "= ?";
        String whereArgs[] = {beerName};
        String groupBy = null;
        String having = null;
        String order = null;

        SQLiteDatabase db = moduleDBOpenHelper.getWritableDatabase();
        Cursor cursor = db.query(ModuleDBOpenHelper.DATABASE_TABLE, result_columns, where, whereArgs, groupBy, having, order);
        if (cursor.moveToFirst()) {
            int columnAlcoholPrice = cursor.getColumnIndex(KEY_PRICE);
            return cursor.getFloat(columnAlcoholPrice);
        }

        else return 0;
        // return 0 provided to prevent errors. Remove when implementing real functionality


    }



    /*
     * This is a helper class that takes a lot of the hassle out of using databases. Use as is and complete the following as required:
     * 	- DATABASE_TABLE
     * 	- DATABASE_CREATE
     */
    private static class ModuleDBOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "myDatabase.db";
        private static final String DATABASE_TABLE = "Beers";
        private static final int DATABASE_VERSION = 1;

        // SQL Statement to create a new database.
        private static final String DATABASE_CREATE = "create table " +
                DATABASE_TABLE + " (" + KEY_ID +
                " integer primary key autoincrement, " +
                KEY_BEER_NAME + " text not null, " +
                KEY_ALCOHOL_PERCENTAGE + " float, " +
                KEY_PRICE + " float);";


        public ModuleDBOpenHelper(Context context, String name,
                                  CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // Called when no database exists in disk and the helper class needs
        // to create a new one.
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        // Called when there is a database version mismatch meaning that
        // the version of the database on disk needs to be upgraded to
        // the current version.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            // Log the version upgrade.
            Log.w("TaskDBAdapter", "Upgrading from version " +
                    oldVersion + " to " +
                    newVersion + ", which will destroy all old data");

            // Upgrade the existing database to conform to the new
            // version. Multiple previous versions can be handled by
            // comparing oldVersion and newVersion values.

            // The simplest case is to drop the old table and create a new one.
        //***    db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
            // Create a new one.
            onCreate(db);
        }
    }
}

