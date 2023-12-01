import java.util.concurrent.ThreadLocalRandom;

public class App {
    public static void main(String[] args) throws Exception {
        useIntBucket();
        useStringBucket();
        usePersonBucket();
    }

    private static void useIntBucket() {
        UniqueBucket<Integer> bucket = new UniqueBucket<>(4);

        int randomCount = ThreadLocalRandom.current().nextInt(7) + 3;
        System.out.println("Trying to add " + randomCount + " values to bucket");
        for (int counter = 0; counter < randomCount; counter++) {
            if (bucket.add(ThreadLocalRandom.current().nextInt(10) + 10)) {
                System.out.println("Value added to bucket");
            } else {
                System.out.println("Value already in bucket, didn't add");
            }
        }
        System.out.println("Values in bucket: " + bucket.count());

        int index = 0;
        int count = bucket.count();
        while (index < count) {
            Integer value = bucket.get(index++);
            System.out.print(value + " ");
        }
        System.out.println("");
    }

    private static void useStringBucket() {
        UniqueBucket<String> bucket = new UniqueBucket<>(4);
        String [] randomStrings = {"Aku", "Roope", "Hannu", "Iines", "Minni", "Mikki", "Hessu", "Tupu", "Lupu", "Hupu"};

        int randomCount = ThreadLocalRandom.current().nextInt(7) + 3;
        System.out.println("Trying to add " + randomCount + " values to bucket");
        for (int counter = 0; counter < randomCount; counter++) {
            if (bucket.add(randomStrings[ThreadLocalRandom.current().nextInt(randomStrings.length)])) {
                System.out.println("Value added to bucket");
            } else {
                System.out.println("Value already in bucket, didn't add");
            }
        }
        System.out.println("Values in bucket: " + bucket.count());

        int index = 0;
        int count = bucket.count();
        while (index < count) {
            String value = bucket.get(index++);
            System.out.print(value + ", ");
        }
        System.out.println("");
    }





    private static void usePersonBucket() {
        UniqueBucket<Person> bucket = new UniqueBucket<>(4);
        String [] firstNames = {"Aku", "Roope", "Hannu", "Iines", "Minni", "Mikki", "Hessu", "Tupu", "Lupu", "Hupu"};
        String [] lastNames = {"Ankka", "Ankka", "Hanhi", "Ankka", "Hiiri", "Hiiri", "Hopo", "Ankka", "Ankka", "Ankka"};

        int randomCount = ThreadLocalRandom.current().nextInt(7) + 3;
        for (int counter = 0; counter < randomCount; counter++) {
            int nameIndex = ThreadLocalRandom.current().nextInt(firstNames.length);
            final String firstName = firstNames[nameIndex];
            final String lastName = lastNames[nameIndex];
            if (bucket.add(new Person(firstName, lastName))) {
                System.out.println("Value added to bucket");
            } else {
                System.out.println("Value already in bucket, didn't add");
            }
        }
        System.out.println("Values in bucket: " + bucket.count());

        int index = 0;
        int count = bucket.count();
        while (index < count) {
            Person value = bucket.get(index++);
            System.out.print(value + " ");
        }
        System.out.println("");
    }
}
