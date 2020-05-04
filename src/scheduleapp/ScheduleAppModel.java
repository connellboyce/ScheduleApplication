/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduleapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import javax.swing.JCheckBox;

/**
 *
 * @author Connell Boyce
 */
public class ScheduleAppModel {

    private final TaskList taskList;
    private final EventList eventList;
    private final ArrayList<JCheckBox> allTaskBox;
    private final Date currentDate;
    public String dateBeingViewed;

    /**
     * Constructor
     */
    public ScheduleAppModel() {
        this.taskList = new TaskList();
        this.eventList = new EventList();
        this.allTaskBox = new ArrayList<>();
        this.currentDate = new Date();

        String[] dateArray = new Date().toString().split(" ");
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

        this.dateBeingViewed = month + "/" + day + "/" + year;
    }

    /**
     * Send the list of tasks further down the line
     *
     * @return Map of Tasks
     */
    public Map<Task, String> getListOfTasks() {
        return taskList.getTasks();
    }

    /**
     * Send the list of events further down the line
     *
     * @return Map of Events
     */
    public Map<Event, String> getListOfEvents() {
        return eventList.getEvents();
    }

    /**
     * Tells the EventList to add this event
     *
     * @param event is the event to add
     */
    public void passEventToList(Event event) {
        eventList.newItem(event);
    }

    /**
     * Tells the TaskList to add this task
     *
     * @param task is the task to add
     */
    public void passTaskToList(Task task) {
        taskList.newItem(task);
    }

    /**
     * Gets all task boxes in an arraylist
     *
     * @return ArrayList of checkboxes
     */
    public ArrayList<JCheckBox> getAllTaskBox() {
        return allTaskBox;
    }

    /**
     * Gets the current date
     *
     * @return system date
     */
    public Date getDate() {
        return currentDate;
    }

    public void addBoxToList(JCheckBox box) {
        this.allTaskBox.add(box);
    }
}
