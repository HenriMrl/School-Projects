public class App {
    public static void main(String[] args) throws Exception {
        
        int x = 10;
        int y = 11;
        
        System.out.println("Largest of 10 and 11 is: " + Utility.max(x, y));

        double p = 11.0;
        double q = 11.1;

        System.out.println("Largest of 11.0 and 11.1 is: " + Utility.max(p, q));
    }
}
