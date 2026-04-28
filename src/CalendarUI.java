import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CalendarUI {
    private Scanner scanner;
    private Calendar calendar;
    private User currentUser;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public CalendarUI(Calendar calendar, User user) {
        this.calendar = calendar;
        this.currentUser = user;
        this.scanner = new Scanner(System.in);
    }

    public String showConflictWarning() {
        System.out.println("Thời gian bị trùng! Lựa chọn của bạn:");
        System.out.println("- Nhập 'replace': Để ghi đè cuộc hẹn cũ");
        System.out.println("- Nhập 'retry'  : Để nhập lại thông tin mới");
        System.out.println("- Phím bất kỳ  : Để hủy bỏ");
        System.out.print("Lựa chọn: ");
        return scanner.nextLine();
    }

    public void displayForm() { 
        while (true) {
            System.out.println("\n=== Form: Thêm cuộc họp mới ===");

            // 1. Nhập thời gian
            LocalDateTime startTime = inputDateTime(); 
            
            // 2. Nhập tham số cuộc họp 
            System.out.print("Nhập tên cuộc hẹn: ");
            String name = scanner.nextLine();
            System.out.print("Nhập địa điểm: ");
            String location = scanner.nextLine();
            System.out.print("Nhập thời lượng (phút): ");
            int duration = Integer.parseInt(scanner.nextLine());

            // 2.1. Kiểm tra tính hợp lệ 
            if (!validateInput(name, duration)) {
                System.out.println(">>> 2.2. Báo lỗi: Tham số không hợp lệ!");
                System.out.print("Bạn có muốn nhập lại không? (y/n): ");
                if (scanner.nextLine().equalsIgnoreCase("y")) continue;
                else return;
            }

            LocalDateTime endTime = startTime.plusMinutes(duration);

            // 2.3. Kiểm tra xung đột thời gian
            Appointment conflict = calendar.checkConflict(startTime, endTime);

            if (conflict != null) { 
                System.out.println("! Xung đột với: " + conflict.getName());
                
                // Hiển thị 3 lựa chọn: replace, retry, hoặc hủy
                String choice = showConflictWarning(); 
                
                if (choice.equalsIgnoreCase("replace")) {
                    // 3.1. Xóa cuộc hẹn cũ để thay thế 
                    calendar.removeAppointment(conflict);
                    System.out.println("- Đã loại bỏ cuộc hẹn cũ.");
                    // Sau khi xóa, logic sẽ chạy tiếp xuống phần tạo mới ở dưới
                } 
                else if (choice.equalsIgnoreCase("retry")) {
                    System.out.println("- Chuẩn bị nhập lại thông tin...");
                    continue; 
                } 
                else {
                    System.out.println("- Đã hủy thao tác.");
                    return;
                }
            }

            Appointment newAppt = new Appointment(name, location, startTime, duration);
            newAppt.addParticipant(currentUser);
            calendar.addAppointment(newAppt); // 
            
            System.out.println(">>> Cuộc hẹn đã được lưu thành công!");
            handleReminder(newAppt.getStartTime(), newAppt.getName());
            
            return;
        }
    }

    // Các phương thức phụ trợ
    private void handleReminder(LocalDateTime start, String name) {
        System.out.print("Bạn có muốn thêm nhắc nhở cho cuộc hẹn này không? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            // 5.1. Add Reminder
            Reminder reminder = new Reminder(start.minusMinutes(15), "Nhắc nhở: " + name);
            calendar.addReminder(reminder);
            System.out.println("- Đã thêm nhắc nhở trước 15 phút.");
        }
    }

    private LocalDateTime inputDateTime() {
        while (true) {
            System.out.print("Nhập ngày giờ (dd/MM/yyyy HH:mm): ");
            String input = scanner.nextLine();
            try { return LocalDateTime.parse(input, formatter); } 
            catch (Exception e) { System.out.println(">>> Lỗi định dạng!"); }
        }
    }

    public boolean validateInput(String name, int duration) {
        return name != null && !name.trim().isEmpty() && duration > 0;
    }
}
