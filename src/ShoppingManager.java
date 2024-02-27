public interface ShoppingManager {
    void Add_item(Product product); //abstract method to add product items to productList
    void Remove_item( ); //abstract method to remove product items from productList
    void Print_items(); //abstract method to print product details to the screen
    void save_items(); //abstract method to save product items into data.txt file
    void load_item() ; //abstract method to load product details again from data.txt file

    void ConsoleMenu(); //abstract method to run console

}
