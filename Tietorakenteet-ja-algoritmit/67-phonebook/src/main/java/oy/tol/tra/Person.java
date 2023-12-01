package oy.tol.tra;

public class Person implements Comparable<Person> {
    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFullName() {
        return lastName + " " + firstName;
    }

    // TODO: Implement equals(), hashCode() and Comparable interface.
    @Override
    public boolean equals(Object obj){

        if (this == obj) {
            return true;
         }

         if (obj == null) {
            return false;
         }
         if (getClass() != obj.getClass()) {
            return false;
         }

        Person uusi = (Person) obj;

        if(this.firstName.equals(uusi.firstName)){
            if(this.lastName.equals(uusi.lastName)){
                return true;
            } 
        }
        return false;
    }
    
    @Override
    public int compareTo(Person obj) {
        if(obj == null) {
            throw new NullPointerException("joo");
        }
        if(this.lastName.compareTo(obj.lastName) == 0){
            return this.firstName.compareTo(obj.firstName);
        } else {
            return this.lastName.compareTo(obj.lastName);
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for(int i = 0; i < firstName.length(); i++){
            char joo = firstName.charAt(i);
            hash = hash * 65599 + joo;  
        }
        for(int i = 0; i < lastName.length(); i++){
            char joo = lastName.charAt(i);
            hash = hash * 65599 + joo;  
        }
        
        return hash;      
    }
}



    



   

