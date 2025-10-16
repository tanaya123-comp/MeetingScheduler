import java.time.LocalDateTime;

public class Interval {

    public final LocalDateTime start;
    public final LocalDateTime end;

    public Interval(LocalDateTime start, LocalDateTime end) {
        if(start.isAfter(end)||start.equals(end)) {
            throw new IllegalArgumentException("Start Time should be before End Time");
        }
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return String.format("[%d, %d]", start, end);
    }
}
