package info.androidhive.barcodereader.SQLiteDatabaseFolder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info.androidhive.barcodereader.Product;
import info.androidhive.barcodereader.Sale;
import info.androidhive.barcodereader.User;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "getcontact_dbase";

    private static final String TABLE_USERS = "users";
    private static final String TABLE_PRODUCT = "products";
    private static final String TABLE_SALES = "sales";

    private static final String KEY_USERS_ID = "id";
    private static final String KEY_USERS_USERNAME = "username";
    private static final String KEY_USERS_PASSWORD = "password";
    //    private static final String KEY_USERS_GENDER = "gender";
    private static final String KEY_USERS_ROLE = "role";

    private static final String KEY_SALE_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_SALE_PRODUCT_ID = "product_name";
    private static final String KEY_AMOUNT = "amount";

    private static final String KEY_PRODUCT_ID = "id";
    private static final String KEY_PRICE = "price";
    private static final String KEY_PRODUCT_NAME = "product_name";
    private static final String KEY_BARCODE = "barcode";

//    String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
//            + KEY_USERS_ID + " INTEGER PRIMARY KEY," + KEY_USERS_USERNAME + " TEXT,"
//            + KEY_USERS_PASSWORD + " TEXT," + KEY_USERS_ROLE + " TEXT"+ ")";

    String CREATE_SALES_TABLE = "CREATE TABLE " + TABLE_SALES + "("
            + KEY_SALE_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " LONG,"
            + KEY_SALE_PRODUCT_ID + " INTEGER," + KEY_AMOUNT + " DOUBLE" + ")";

    String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "("
            + KEY_PRODUCT_ID + " INTEGER PRIMARY KEY," + KEY_PRICE + " LONG,"
            + KEY_PRODUCT_NAME + " TEXT," + KEY_BARCODE + " TEXT" + ")";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PRODUCT_TABLE);
        db.execSQL(CREATE_SALES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SENT_MSG);

        // Create tables again
        onCreate(db);
    }

    public void deleteTableUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.close();
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERS_USERNAME, user.getUsername());
        values.put(KEY_USERS_PASSWORD, user.getPassword());
        values.put(KEY_USERS_ROLE, user.getRole());

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

//    public void addSentMsg(SentMessage sentMessage){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_SENT_MSG_TO,sentMessage.getSentTo());
//        values.put(KEY_SENT_MSG_NUMBER,sentMessage.getPhoneNumber());
//        values.put(KEY_SENT_MSG_DATE,sentMessage.getDate());
//
//        db.insert(TABLE_SENT_MSG,null,values);
//        db.close();
//    }

    public User userLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM USERS WHERE username=? AND password=?", new String[]{username, password});
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return new User(cursor.getString(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3));
    }

    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_USERS_ID, KEY_USERS_USERNAME, KEY_USERS_PASSWORD, KEY_USERS_ROLE},
                KEY_USERS_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }


        return new User(cursor.getString(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3));
    }

//    public  SentMessage getSentMessage(int id){
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_SENT_MSG,new String[] {KEY_SENT_MSG_ID, KEY_SENT_MSG_TO, KEY_SENT_MSG_NUMBER, KEY_SENT_MSG_DATE},
//                KEY_SENT_MSG_ID + "=?",new String[] {String.valueOf(id)}, null,null,null,null);
//        if(cursor != null){
//            cursor.moveToFirst();
//        }
//
//        return new SentMessage(Integer.parseInt(cursor.getString(0)),cursor.getString(1),
//                cursor.getString(2), Long.parseLong(cursor.getString(3)));
//    }

//    public List<User> getAllUsers(){
//        List<User> userList = new ArrayList<>();
//
//        String selectQuery = "SELECT * FROM " + TABLE_USERS;
//
//        SQLiteDatabase db  = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery,null);
//
//        if (cursor.moveToFirst()){
//            do {
//                User user = new User();
//                user.setId(Integer.parseInt(cursor.getString(0)));
//                user.setUsername(cursor.getString(1));
//                user.setPassword(cursor.getString(2));
//                user.setGender(cursor.getString(3));
//                userList.add(user);
//            }while (cursor.moveToNext());
//        }
//
//        cursor.close();
//
//        return userList;
//    }

    public boolean addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_NAME, product.getProduct_name());
        values.put(KEY_PRICE, product.getPrice());
        values.put(KEY_BARCODE, product.getBarcode());

        boolean success = false;

        try {
            db.insert(TABLE_PRODUCT, null, values);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return success;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_PRODUCT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product(cursor.getInt(0), Double.parseDouble(cursor.getString(1)), cursor.getString(2), cursor.getString(3));
//                sale.setId(Integer.parseInt(cursor.getString(0)));
//                sale.setProduct_name(cursor.getString(2));
//                sale.setAmount(Double.parseDouble(cursor.getString(3)));
//                sale.setDate(Long.parseLong(cursor.getString(1)));
                products.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return products;
    }

    public boolean addSale(String barcode) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Product> productList = new ArrayList<>();
        Sale sale = new Sale();

        productList = getAllProducts();

        for(Product product: productList){
            if(product.getBarcode().equals(barcode)){
                sale.setAmount(product.getPrice());
                sale.setDate(new Date().getTime());
                sale.setProduct_name(product.getProduct_name());
                break;
            }
        }

        boolean success = false;

        try {

            ContentValues values = new ContentValues();
            values.put(KEY_SALE_PRODUCT_ID, sale.getProduct_name());
            values.put(KEY_AMOUNT, sale.getAmount());
            values.put(KEY_DATE, sale.getDate());

            db.insert(TABLE_SALES, null, values);
            success = true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return success;
    }

    public ArrayList<Sale> getAllSales() {
        ArrayList<Sale> sales = new ArrayList<>();

//        String selectQuery = "SELECT * FROM " + TABLE_SALES + "," + TABLE_PRODUCT + " ORDER BY date DESC";
        String selectQuery = "SELECT * FROM " + TABLE_SALES + " ORDER BY date DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Sale sale = new Sale();
                sale.setId(cursor.getInt(0));
                sale.setProduct_name(cursor.getString(2));
                sale.setAmount(Double.parseDouble(cursor.getString(3)));
                sale.setDate(Long.parseLong(cursor.getString(1)));
                sales.add(sale);

                System.out.println("=========================================================================="+sale.getProduct_name());
                System.out.println("--------------------------------------------------------------------------"+sale.getId());
            } while (cursor.moveToNext());
        }

//        System.out.println("===============================================" + "the size of the list is: " + productList.size());

        cursor.close();

        return sales;
    }

    public int getUsersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

//    public int getSentMessageCount() {
//        String countQuery = "SELECT  * FROM " + TABLE_SENT_MSG;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery,null);
//        cursor.close();
//        return cursor.getCount();
//    }

//    public int updateUser(User user) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_USERS_USERNAME,user.getUsername());
//        values.put(KEY_USERS_GENDER,user.getGender());
//        values.put(KEY_USERS_PASSWORD,user.getPassword());
//
//        return db.update(TABLE_USERS,values, KEY_USERS_ID + " = ?",
//                new String[] { String.valueOf(user.getId()) });
//    }
//
//    public int updateSentMessage(SentMessage sentMessage) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_SENT_MSG_TO,sentMessage.getSentTo());
//        values.put(KEY_SENT_MSG_NUMBER,sentMessage.getPhoneNumber());
//        values.put(KEY_SENT_MSG_DATE,sentMessage.getDate());
//
//        return db.update(TABLE_SENT_MSG,values, KEY_USERS_ID + " = ?",
//                new String[] {String.valueOf(sentMessage.getId())});
//    }
//
//    public void deleteUser(User user) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_USERS, KEY_USERS_ID + " = ?",
//                new String[] { String.valueOf(user.getId()) });
//        db.close();
//    }
//
//    public void deleteSentMessage(SentMessage sentMessage) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_SENT_MSG, KEY_SENT_MSG_ID + " = ?",
//                new String[] { String.valueOf(sentMessage.getId()) });
//        db.close();
//    }
//
//    public void deleteAllMessages(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        for(SentMessage sentMessage: getAllSentMessages()){
//        db.delete(TABLE_SENT_MSG,KEY_SENT_MSG_ID + " = ?",new String[] { String.valueOf(sentMessage.getId())});
//        }
//    }
}
