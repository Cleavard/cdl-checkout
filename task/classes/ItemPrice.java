package task.classes;


public class ItemPrice {
    private double price;
    private int specialAmount = 0;
    private double specialPrice;

    public ItemPrice(double price, int specialAmount, double specialPrice) {
        this.price = price;
        this.specialAmount = specialAmount;
        this.specialPrice = specialPrice;
    }

    public double getPrice() { return price; }

    public int getSpecialAmount() { return  specialAmount; }

    public double getSpecialPrice() { return specialPrice; }
}

