public class Main {

    public static void main(String[] args) {
        System.out.println("Customer Segmentation App is running!");

        String[] customers = {"Alice", "Bob", "Charlie"};
        int[] ages = {22, 45, 70};

        for (int i = 0; i < customers.length; i++) {
            System.out.println(customers[i] + " -> " + getSegment(ages[i]));
        }
    }

    public static String getSegment(int age) {
        if (age < 30) {
            return "Young";
        } else if (age < 60) {
            return "Adult";
        } else {
            return "Senior";
        }
    }
}