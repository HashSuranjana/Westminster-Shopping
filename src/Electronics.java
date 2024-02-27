public class Electronics extends Product {

    private String brand; //declaring the brand attribute
    private int warranty; //declaring the warranty attribute

    
    //Creating overloaded constructor
    public Electronics(String product_ID, String product_name, int available_number, double price, String brand, int warranty) {
        super(product_ID, product_name, available_number, price);
        this.brand = brand;
        this.warranty = warranty;
    }

    //declaring getters and setter
    public String getBrand(){ 

        return this.brand; //This method returns the brand of the electronic item
    }
    public void setBrand(String brand){

        this.brand=brand; //This method sets the brand of the electronic item
    }
    public int getWarranty(){

        return this.warranty; //This method returns the warranty of the electronic item
    }
    public void setWarranty(int warranty){

        this.warranty=warranty; //This method sets the warranty of the electronic item
    }

    @Override
    public String getCategory(){
        
        return "Electronics"; //This method returns the 'Electronics' of the Electronic item(returns which category)
    }

    //The method to display the item details in printScreen
    @Override
    public String toString(){
        return "Product Category : "+getCategory()+"\n"+
                "Product Name : " + getProduct_name() +"\n"+
                "Product ID : " + getProduct_ID()+"\n"+
                "Brand : " + getBrand() + "\n" +
                "Warranty : " + getWarranty() +" Year(s)" +"\n"+
                "Available Quantity : "+getAvailable_number()+"\n"+
                "Price : "+getPrice()+"$"+"\n";
    }
    
    //The method to write information on the data.txt file
    @Override
    public String tofileString(){
        return  "ProductCategory:"+getCategory()+", "+ getProduct_name()+", "+getProduct_ID()+", "+getBrand() + ", " +
                getWarranty() + ", "+ +getAvailable_number()+", "+ +getPrice()+System.lineSeparator();

    }
    
}
