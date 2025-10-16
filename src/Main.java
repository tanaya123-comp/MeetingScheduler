//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public static void main() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Starting Meeting Scheduler ...");
    // Making use of Singleton Design Pattern to create class of Meeting Scheduler
    MeetingScheduler meetingScheduler = MeetingScheduler.getInstance();
    meetingScheduler.initialize(scanner);

    int meetings;
    System.out.println("Enter number of meetings: ");

    meetings = Integer.parseInt(scanner.nextLine().trim());

    meetingScheduler.printMeetingRooms();

    for(int i = 0; i < meetings; i++) {
        LocalDateTime start = null;
        LocalDateTime end = null;

        while(true) {
            try {

                String startTime;
                String endTime;

                System.out.println("Enter start time of the meeting in format (yyyy-MM-dd HH:mm):");
                startTime = scanner.nextLine().trim();
                start = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                System.out.println("Enter end time of the meeting in format (yyyy-MM-dd HH:mm):");
                endTime = scanner.nextLine().trim();
                end = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                if(!end.isAfter(start)) {
                    System.out.println("Entered End Time is before Start Time. Please enter valid time range.");
                }

                break;
            } catch (Exception e) {
                System.out.println("Invalid input format for Meeting entered. Expected yyyy-MM-dd HH:mm");
            }
        }

        meetingScheduler.scheduleMeeting(start, end);
    }

    meetingScheduler.printMeetingRooms();
    scanner.close();
}
