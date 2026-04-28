import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Calendar {
    private List<Appointment> appointments; // Danh sách quản lý các cuộc hẹn
    private List<Reminder> reminders; // Danh sách quản lý các nhắc nhở

    public Calendar() {
        this.appointments = new ArrayList<>();
        this.reminders = new ArrayList<>();
    }

    public Appointment checkConflict(LocalDateTime start, LocalDateTime end) {
        for (Appointment appt : appointments) {
            if (start.isBefore(appt.getEndTime()) && end.isAfter(appt.getStartTime())) {
                return appt; // Trả về cuộc hẹn bị xung đột 
            }
        }
        return null;
    }

    public Appointment findSimilarMeeting(String name, int duration) {
        for (Appointment appt : appointments) {
            if (appt.getName().equalsIgnoreCase(name) && appt.getDuration() == duration) {
                return appt;
            }
        }
        return null;
    }

    public void addAppointment(Appointment appt) {
        appointments.add(appt);
    }

    public void addReminder(Reminder reminder) {
        reminders.add(reminder);
    }

    public void removeAppointment(Appointment appt) {
        appointments.remove(appt);
    }

    // Getters để UI có thể lấy dữ liệu hiển thị
    public List<Appointment> getAppointments() {
        return appointments;
    }
}