package scheduleapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import javax.swing.JCheckBox;

/**
 *
 * @author Connell Boyce
 */
public class ScheduleAppController {

    private final ScheduleAppModel appModel;
    private final ScheduleAppView appView;

    /**
     * Constructor
     */
    public ScheduleAppController() {
        appModel = new ScheduleAppModel();
        appView = new ScheduleAppView(this);
        appView.setVisible(true);
    }

    /**
     * Method to return all of the system's tasks
     *
     * @return List of tasks
     */
    public Map<Task, String> getAllTasks() {
        return appModel.getListOfTasks();
    }

    /**
     * Method to return all of the system's events
     *
     * @return List of events
     */
    public Map<Event, String> getAllEvents() {
        return appModel.getListOfEvents();
    }

    /**
     * Tells model to add the event
     *
     * @param addMe event to add
     */
    public void addThisEvent(Event addMe) {
        appModel.passEventToList(addMe);
    }

    /**
     * Tells model to add the task
     *
     * @param addMe task to add
     */
    public void addThisTask(Task addMe) {
        appModel.passTaskToList(addMe);
    }

    /**
     * Gets the list of checkboxes from model
     *
     * @return ArrayList of all checkboxes
     */
    public ArrayList<JCheckBox> getBoxList() {
        return appModel.getAllTaskBox();
    }

    /**
     * Returns unformatted date object
     * 
     * @return Date today's date
     */
    public Date getUnformattedDate() {
        return appModel.getDate();
    }

    /**
     * Tells the model what date the view is seeing
     * 
     * @param date viewed date
     */
    public void showDate(String date) {
        appModel.dateBeingViewed = date;
    }

    /**
     * Returns the date the view is seeing
     * 
     * @return String date
     */
    public String getViewingDate() {
        return appModel.dateBeingViewed;
    }

    /**
     * Gets the system's current time and date
     *
     * @return the date
     */
    public String getCurrentDate() {
        String unalteredDate = appModel.getDate().toString();
        String[] dateArray = unalteredDate.split(" ");
        String day = dateArray[2];
        String month = dateArray[1];
        String year = dateArray[5];
        switch (month) {
            case "Jan":
                month = "01";
                break;
            case "Feb":
                month = "02";
                break;
            case "Mar":
                month = "03";
                break;
            case "Apr":
                month = "04";
                break;
            case "May":
                month = "05";
                break;
            case "Jun":
                month = "06";
                break;
            case "Jul":
                month = "07";
                break;
            case "Aug":
                month = "08";
                break;
            case "Sep":
                month = "09";
                break;
            case "Oct":
                month = "10";
                break;
            case "Nov":
                month = "11";
                break;
            case "Dec":
                month = "12";
                break;
            default:
                month = "?";
                break;
        }

        if (day.length() < 2) {
            day = "0" + day;
        }

        return month + "/" + day + "/" + year;
    }

    /**
     * Adds a checkbox to the list of known checkboxes
     * 
     * @param box checkbox to add 
     */
    public void addBox(JCheckBox box) {
        appModel.addBoxToList(box);
    }
}
