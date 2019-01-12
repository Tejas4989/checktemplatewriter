package kw.check.template;

public class CheckTemplate {

    private String date;
    private String amountInWords;
    private String payee;
    private double amount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmountInWords() {
        return amountInWords;
    }

    public void setAmountInWords(String amountInWords) {
        this.amountInWords = amountInWords;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Amount= " + amount + "\n" +
                "AmountInWords= " + amountInWords + "\n" +
                "Date= " + date + "\n" +
                "Payee= " + payee + "\n";
    }
}
