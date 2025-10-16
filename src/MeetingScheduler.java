import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class MeetingScheduler {

    ArrayList<Room> meetingRooms;
    int rooms;

    private static volatile MeetingScheduler instance;

    private MeetingScheduler() {}

    public static MeetingScheduler getInstance() {
        if(instance == null) {
            synchronized(MeetingScheduler.class) {
                if(instance == null) {
                    instance = new MeetingScheduler();
                }
                return instance;
            }
        }
        return instance;
    }

    public void initialize(Scanner sc) {
        System.out.println("Enter number of meeting rooms");
        try {
            rooms = Integer.parseInt(sc.nextLine().trim());

            meetingRooms = new ArrayList<>();

            for(int i = 0; i < rooms; i++) {
                meetingRooms.add(new Room(i));
            }
        } catch(Exception e) {
            System.out.println("Invalid number of meeting rooms entered");
        }
    }

    public void scheduleMeeting(LocalDateTime start, LocalDateTime end) {

        Room bestRoom = null;
        long minSpillage = Long.MAX_VALUE;

        for (Room room : meetingRooms) {
            if (room.canRoomAccomodateMeeting(start, end)) {
                long spillage = room.findSpillageTimeForMeeting(start, end);
                if (spillage < minSpillage) {
                    minSpillage = spillage;
                    bestRoom = room;
                }
            }
        }

        if (Objects.isNull(bestRoom)) {
            System.out.println("Could not schedule meeting from " + start + " to " + end);
        } else {
            bestRoom.addMeetingInTheRoom(start, end);
            System.out.println("Meeting scheduled in Room " + bestRoom.getRoomNumber() + " from " + start + " to " + end);
        }
    }

    public void printMeetingRooms() {
        for(Room room: meetingRooms) {
            System.out.println(room);
        }
    }
}
