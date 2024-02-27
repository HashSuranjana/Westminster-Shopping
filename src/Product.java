public abstract class Product {

    protected String product_ID; //declaring the product_ID attribute
    protected String product_name; //declaring the product_name attribute
    protected int available_number; //declaring the available attribute
    protected double price; //declaring the price attribute


    //default Constructor
    public Product(){

    }

    //Creating overloaded constructor
    public Product(String product_ID,String product_name,int available_number,double price){
        this.product_ID=product_ID;
        this.product_name=product_name;
        this.available_number=available_number;
        this.price=price;
    }


    //declaring getter and setter
    public String getProduct_ID() {

        return product_ID; //returns the product_ID
    }

    public void setProduct_ID(String product_ID) {

        this.product_ID = product_ID; //sets the product_ID
    }

    public String getProduct_name() {

        return product_name; //returns the product_name
    }

    public void setProduct_name(String product_name) {

        this.product_name = product_name; //sets new value to product_name
    }

    public int getAvailable_number() {

        return available_number; //returns the quantity of the product
    }

    public void setAvailable_number(int available_number) {

        this.available_number = available_number; //sets the quantity of the product
    }

    public double getPrice() {

        return price; //returns the price of the product
    }

    public void setPrice(double price) {

        this.price = price; //sets a new price value for the product
    }
    public abstract String getCategory(); // abstract method of getting category

    public abstract String toString(); // abstract method of displaying to the screen

    public abstract String tofileString(); //abstract method of writing to the file
}
