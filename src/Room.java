import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Room {
    int roomNumber;
    NavigableSet<Interval> freeTime;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.freeTime = new TreeSet<>((i1,i2) -> i1.start.compareTo(i2.start));
        this.freeTime.add(new Interval(LocalDateTime.MIN, LocalDateTime.MAX));
    }

    public boolean canRoomAccomodateMeeting(LocalDateTime startTime, LocalDateTime endTime) {
        lock.readLock().lock();
        try {
            Interval interval = new Interval(startTime, endTime);
            Interval floor = freeTime.floor(interval);
            return floor != null && !floor.end.isBefore(endTime);
        } finally {
            lock.readLock().unlock();
        }

    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void addMeetingInTheRoom(LocalDateTime start, LocalDateTime end) {
        lock.writeLock().lock();
        try {
            Interval interval = new Interval(start, end);
            Interval floor = freeTime.floor(interval);

            if (floor != null && !floor.end.isBefore(end)) {
                freeTime.remove(floor);
                if (floor.start.isBefore(start)) freeTime.add(new Interval(floor.start, start));
                if (end.isBefore(floor.end)) freeTime.add(new Interval(end, floor.end));
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public long findSpillageTimeForMeeting(LocalDateTime start,LocalDateTime end) {
        lock.readLock().lock();
        try {
            Interval interval = new Interval(start, end);
            Interval floor = freeTime.floor(interval);

            if (floor == null || floor.end.isBefore(end)) return Long.MAX_VALUE;

            Duration spillage;
            if (floor.start.equals(start) && floor.end.equals(end)) spillage = Duration.ZERO;
            else if (floor.start.equals(start)) spillage = Duration.between(end, floor.end);
            else if (floor.end.equals(end)) spillage = Duration.between(floor.start, start);
            else spillage = Duration.between(floor.start, start).plus(Duration.between(end, floor.end));

            return spillage.toMinutes();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public String toString()  {
       String res = "";
       for(Interval interval: freeTime) {
          res = res.concat(interval.toString());
       }
       return res;
    }
}
