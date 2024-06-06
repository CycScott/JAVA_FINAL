package AccountComponents;

import java.time.LocalDate;
import java.util.UUID;

public class Record {
    private UUID id;
    private LocalDate date;
    private double amount;
    private String category;
    private String description;

    public Record(LocalDate date, double amount, String category, String description) {
        this.id = UUID.randomUUID();
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return date + ",類型:" + category + ",金額:" + amount + ",物品:" + description;
    }
}
