import java.time.LocalDateTime;

public class Reminder {
    private LocalDateTime triggerTime;
    private String message;

    public Reminder(LocalDateTime triggerTime, String message) {
        this.triggerTime = triggerTime;
        this.message = message;
    }

    // Getters
    public LocalDateTime getTriggerTime() { return triggerTime; }
    public String getMessage() { return message; }
}