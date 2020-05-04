package scheduleapp;

/**
 *
 * @author Connell Boyce
 */
public class Task {

    private final String title;
    private final String date;

    /**
     * Constructor
     *
     * @param title the task title
     * @param date the task date
     */
    public Task(String title, String date) {
        this.title = title;
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }
    
    /**
     * Task object to String
     *
     * @return the Task as a readable string
     */
    @Override
    public String toString() {
        return this.title;
    }
}
