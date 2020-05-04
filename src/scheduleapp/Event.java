package scheduleapp;

/**
 *
 * @author Connell Boyce
 */
public class Event implements Comparable<Event> {

    private final String title;
    private final String date;
    private final String startTime;
    private final String endTime;

    /**
     * Constructor
     *
     * @param title the event title
     * @param date the event date formatted as MM-DD-YYYY
     * @param startTime the event start time formatted in military time
     * @param endTime the event end time formatted in military time
     */
    public Event(String title, String date, String startTime, String endTime) {
        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Method to return the title
     *
     * @return the String title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Method to return the date
     * 
     * @return String modified date
     */
    public String getDate() {
        return date;
    }

    /**
     * Method to return the time
     *
     * @return the formatted String start and end times
     */
    public String getTime() {
        return startTime + "-" + endTime;
    }

    /**
     * Method to return the ending time only
     * 
     * @return String of end time
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * String representation
     *
     * @return String Event
     */
    @Override
    public String toString() {
        return title + "," + date + "," + startTime + "," + endTime;
    }

    /**
     * Comparable interface method
     *
     * @param o event to compare to
     * @return comparison value
     */
    @Override
    public int compareTo(Event o) {
        if (this.startTime == null ? o.startTime != null : !this.startTime.equals(o.startTime)) {
            return this.startTime.compareTo(o.startTime);
        } else {
            if (this.endTime == null ? this.endTime != null : !this.endTime.equals(o.endTime)) {
                return this.endTime.compareTo(o.endTime);
            } else {
                return this.title.compareTo(o.title);
            }
        }
    }

}
