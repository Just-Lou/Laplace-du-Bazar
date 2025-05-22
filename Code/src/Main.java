public class Main {

    public static void main(String[] args) {
        User newUser = new User();

        System.out.println("\nFirst run");
        newUser.showInfo();
        newUser.email = "test";
        System.out.println("\nSecond run");
        newUser.showInfo();

        User newUser2 = new User("Louis", "Gagnon", "gagl@test.com", "819-819-8119");
        System.out.println("\nThird run");
        newUser2.showInfo();
    }
}