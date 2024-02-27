public class User{
    private String user_name; //declaring user_name attribute
    private String password; // declaring password attribute

    public User() {

    }

    //Creating overloaded constructor
    public User(String user_name, String password) {
        this.user_name = user_name;
        this.password = password;
    }

    //declaring getters and setters
    public String getUser_name(){

        return this.user_name; //returns name of the user
    }
    public void setUser_name(String userName){

        this.user_name=userName; //sets a value to the user_name
    }

    public String getPassword() {

        return password; //returns a password
    }

    public void setPassword(String password) {

        this.password = password;  //sets a new value to the password
    }
    public String UserString(){ //toString method of user class

        return "UserProfile:, "+ getUser_name() + ", "+ getPassword();
    }
}
