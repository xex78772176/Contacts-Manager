package hk.polyu.eie.eie3109;

public class Customer {
    private int id;
    private String name;
    private String gender;

    public Customer(int theId, String theName, String theGender){
        id = theId;
        name = theName;
        gender = theGender;
    }

    public Customer(String theName, String theGender){
        this(-1, theName, theGender);
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getGender(){
        return gender;
    }

    

}
