package oy.tol.tra;

class Payment implements Comparable<Payment>  {
    Integer number;
    Integer sum;

    Payment(Integer number, Integer sum) {
        assert(number != null && sum != null);
        this.number = number;
        this.sum = sum;
    }

    @Override
    public int compareTo(Payment payment) {
        return this.number.compareTo(((Payment)payment).number);
    }

    @Override
    public boolean equals(Object payment) {
        if (payment instanceof Payment) {
            return this.number.equals(((Payment)payment).number);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

}
