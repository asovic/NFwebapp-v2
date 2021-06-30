package si.asovic.backend.model;

import java.time.LocalDate;

public class HistoryOrder {

    private LocalDate date;
    private int bottles;
    private String comment;
    private String status;

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getBottles() {
        return bottles;
    }
    public void setBottles(int bottles) {
        this.bottles = bottles;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
