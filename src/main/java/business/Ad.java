package business;

public class Ad {
    private String title, description, date, image;
    private double price;

    public double returnPrice() {
        return price;
    }
    public void setPrice(double price) {this.price = price;}
}
