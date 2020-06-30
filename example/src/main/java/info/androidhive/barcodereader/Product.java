package info.androidhive.barcodereader;

public class Product {
    int id;
    double price;
    String product_name, barcode;

    public Product(int id, double price, String product_name, String barcode) {
        this.id = id;
        this.price = price;
        this.product_name = product_name;
        this.barcode = barcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
