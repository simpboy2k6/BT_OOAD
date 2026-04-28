import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Appointment {
    private String name;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int duration;
    private List<User> participants;

    private static final DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Appointment(String name, String location, LocalDateTime startTime, int duration) {
        this.name = name;
        this.location = location;
        this.startTime = startTime;
        this.duration = duration;
        // Tính toán endTime dựa trên startTime và duration
        this.endTime = startTime.plusMinutes(duration);
        this.participants = new ArrayList<>();
    }

    public void addParticipant(User user) {
        if (!participants.contains(user)) {
            this.participants.add(user);
        }
    }

    // Getters
    public String getName() { return name; }
    public String getLocation() { return location; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public int getDuration() { return duration; }
    public List<User> getParticipants() { return participants; }

    @Override
    public String toString() {
        return String.format("Cuộc hẹn: %s | Địa điểm: %s | Từ: %s đến: %s (%d phút)", 
                name, 
                location, 
                startTime.format(displayFormatter),
                endTime.format(displayFormatter),
                duration);
    }
}