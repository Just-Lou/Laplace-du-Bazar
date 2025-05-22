public class User {
    protected String firstName, lastName, email, passwordHash, phoneNb;
    protected double clientScore, sellerScore;
    protected int id;

    User(){
        System.out.println("User created (no attributes)");
    }

    User(String firstName, String lastName, String email, String phoneNb){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNb = phoneNb;
    }

    public void showInfo() {
        System.out.println(firstName + " " + lastName + "\n" + email + "\n" + phoneNb);
    }
}
