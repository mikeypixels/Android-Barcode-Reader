package info.androidhive.barcodereader;

public class Sale {
    int id;
    double amount;
    String product_name;
    long date;

//    public Sale(int id, double amount, Long date, String product_name) {
//        this.id = id;
//        this.amount = amount;
//        this.date = date;
//        this.product_name = product_name;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
