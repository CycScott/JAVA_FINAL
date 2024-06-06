package AccountComponents.Resourse;
import java.time.LocalDate;

public class Record {
    private LocalDate date;
    private double amount;
    private String category;
    private String description;

    public Record(LocalDate date, double amount, String category, String description) {
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return date + "," + amount + "," + category + "," + description;
    }
}
