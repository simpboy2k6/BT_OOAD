import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        //Khởi tạo hệ thống và dữ liệu mẫu
        Calendar myCalendar = new Calendar();
        User student = new User("SV001", "Nguyen Van A");
        
        Appointment existingMeeting = new Appointment(
            "Hoc Nhom", 
            "Phong Lab", 
            java.time.LocalDateTime.now().withSecond(0).withNano(0), 
            60
        );
        myCalendar.addAppointment(existingMeeting);

        // Giao diện
        CalendarUI ui = new CalendarUI(myCalendar, student);
        Scanner menuScanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("--- CHƯƠNG TRÌNH QUẢN LÝ LỊCH HẸN ---");

        while (running) {
            System.out.println("\n[MENU]");
            System.out.println("1. Thêm cuộc họp mới (Add Appointment)");
            System.out.println("2. Xem danh sách lịch hiện tại");
            System.out.println("3. Thoát");
            System.out.print("Chọn chức năng: ");
            
            String choice = menuScanner.nextLine();

            switch (choice) {
                case "1":
                    ui.displayForm();
                    break;
                case "2":
                    System.out.println("\n--- DANH SÁCH LỊCH HẸN ---");
                    if (myCalendar.getAppointments().isEmpty()) {
                        System.out.println("(Trống)");
                    } else {
                        myCalendar.getAppointments().forEach(System.out::println);
                    }
                    break;
                case "3":
                    running = false;
                    System.out.println("Đã thoát chương trình.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
        menuScanner.close();
    }
}