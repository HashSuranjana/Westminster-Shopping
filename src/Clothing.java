public class Clothing extends Product {

    private String size; //declaring the size attribute
    private String color; //declaring the color attribute

    //Creating overloaded constructor
    public Clothing(String product_ID, String product_name, int available_number, double price, String size, String color) {
        super(product_ID, product_name, available_number, price);
        this.size = size;
        this.color = color;
    }
    // declaring getters and setters
    public String getSize() {

        return size; //This method returns the size of the clothing item

    }

    public void setSize(String size) {

        this.size = size;//This method sets the size of the clothing item
    }

    public String getColor() {

        return color; //This method returns the color of the clothing item
    }

    public void setColor(String color) {

        this.color = color; //This method sets the color of the clothing item
    }
    @Override
    public String getCategory(){

        return "Clothing"; //This method returns the 'Clothing' of the clothing item(returns which category)
    }

    //The method to display the item details in printScreen
    @Override
    public String toString(){
        return  "Product Category : "+getCategory()+"\n"+
                "Product Name : " + getProduct_name() +"\n"+
                "Product ID : " + getProduct_ID()+"\n"+
                "Size : " + getSize().toUpperCase() + "\n" +
                "Color : " + getColor() + "\n"+
                "Available Quantity : "+getAvailable_number()+"\n"+
                "Price : "+getPrice()+"$"+"\n";

    }

    //The method to write information on the data.txt file
    @Override
    public String tofileString(){
        return "ProductCategory:"+getCategory()+", "+ getProduct_name()+", "+getProduct_ID()+", "+getSize() + ", " +
                getColor() + ", "+ +getAvailable_number()+", "+ +getPrice()+System.lineSeparator();
    }
}
