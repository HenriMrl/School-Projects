package oy.tol.tra;

import java.util.Date;

class Invoice implements Comparable<Invoice>  {
    Integer number;
    Integer sum;
    Date dueDate;

    Invoice(Integer number, Integer sum, long due) {
        assert(number != null && sum != null);
        this.number = number;
        this.sum = sum;
        dueDate = new Date(due);
    }

    Invoice(final Invoice another) {
        if (this != another) {
            this.number = another.number;
            this.sum = another.sum;
            this.dueDate = another.dueDate;    
        }
    }
    
    @Override
    public int compareTo(Invoice invoice) {
        return this.number.compareTo(invoice.number);
    }

    @Override
    public boolean equals(Object invoice) {
        if (invoice instanceof Invoice) {
            return this.number.equals(((Invoice)invoice).number);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

}