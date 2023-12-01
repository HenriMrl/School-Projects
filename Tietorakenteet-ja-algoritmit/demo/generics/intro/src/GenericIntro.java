import java.util.Arrays;

public class GenericIntro {

    public static void main(String[] args) throws Exception {
    
        Integer [] source = {1,2,3,4,5,6,7,8,9,10};
        Integer [] destination = {99,88,77,66,55};
        MyArrays.copy(source, 2, 3, destination, 1);
        System.out.println("Destination: " + Arrays.toString(destination));

        String [] strSource = {"sika", "koira", "kissa", "hevonen", "nauta"};
        String [] strDestination = {"lato", "navetta", "haka", "niitty", "piha"};
        MyArrays.copy(strSource, 3, 2, strDestination, 3);
        System.out.println("Destination: " + Arrays.toString(strDestination));
        
    }

}
