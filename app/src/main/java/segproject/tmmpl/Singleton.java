package segproject.tmmpl;

/**
 * Created by Parastoo on 11/27/2017.
 */

public class Singleton {
    private static User[] userArray = new User[1];
    private static Singleton singleton = new Singleton( );


    private Singleton() { }

    public static User getInstance(User user) {
        if (userArray[0] == null){
            userArray[0] = user;

        }

        return userArray[0];
    }

    public static User getInstance(){
            return userArray[0];
    }






}
