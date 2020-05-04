/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduleapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Connell Boyce
 */
public class ScheduleAppView extends JFrame {

    private final ScheduleAppController appController;

    private JTextArea events;
    private JTextArea eventNameArea;
    private JTextArea eventStartHourInput;
    private JTextArea eventStartMinInput;
    private JTextArea eventEndHourInput;
    private JTextArea eventEndMinInput;
    private JTextArea eventDayInput;
    private JTextArea eventMonthInput;
    private JTextArea eventYearInput;
    private JTextArea taskNameArea;
    private JTextArea taskDayInput;
    private JTextArea taskMonthInput;
    private JTextArea taskYearInput;
    private Box taskBox;
    private JLabel showing;
    private JFrame newDateFrame;

    /**
     * Constructor
     *
     * @param appController the controller for the MVC style application
     */
    public ScheduleAppView(ScheduleAppController appController) {
        this.appController = appController;
        initComponents();
    }

    /**
     * Initializations for the UI
     */
    private void initComponents() {
        String todaysDate = appController.getCurrentDate();

        //Basic app qualities
        setTitle("Schedule App");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Creating boxes for the events and tasks
        taskBox = Box.createVerticalBox();
        events = new JTextArea();
        events.setEditable(false);

        //Adding label and empty space following it for inside the boxes
        taskBox.add(new JLabel("Your Tasks:"));
        taskBox.add(new JLabel(" "));
        events.setText("Your Events:\n\n");

        //Iterates through the task list to get all elements and add them
        //as check boxes       
        Set<Task> taskItemNames = appController.getAllTasks().keySet();
        Iterator<Task> taskIterator = taskItemNames.iterator();
        while (taskIterator.hasNext()) {
            Task item = taskIterator.next();
            if (item.getDate().equals(appController.getViewingDate())) {
                JCheckBox tempCheckBox = new JCheckBox(item.toString());
                taskBox.add(tempCheckBox);
                appController.getBoxList().add(tempCheckBox);
            }
        }

        //Iterates throught the event list to get all elements and add them
        //as text
        Set<Event> eventItemNames = appController.getAllEvents().keySet();
        Iterator<Event> eventIterator = eventItemNames.iterator();
        while (eventIterator.hasNext()) {
            Event item = eventIterator.next();
            if (item.getDate().equals(todaysDate)) {
                events.setText(events.getText() + item.getTitle() + "\n"
                        + item.getTime() + "\n\n");
            }
        }

        //Framing for the scroll panes
        JScrollPane jscrlpBox = new JScrollPane(taskBox);
        jscrlpBox.setPreferredSize(new Dimension(550, 200));
        JScrollPane scrollEvent = new JScrollPane(events);
        scrollEvent.setPreferredSize(new Dimension(550, 200));

        //Creating and etching panels for task and event
        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.PAGE_AXIS));
        taskPanel.setBorder(BorderFactory.createEtchedBorder());
        JPanel eventPanel = new JPanel();
        eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.PAGE_AXIS));
        eventPanel.setBorder(BorderFactory.createEtchedBorder());

        //Creating inner JPanels
        JPanel topTask = new JPanel();
        topTask.add(new JLabel("Add New Task"));
        topTask.setPreferredSize(new Dimension(550, -275));
        JPanel topEvent = new JPanel();
        topEvent.add(new JLabel("Add New Event"));
        topEvent.setPreferredSize(new Dimension(550, -275));

        //Adding elements to task panel
        JButton addTask = new JButton("+");
        topTask.add(addTask);
        topTask.setBackground(Color.LIGHT_GRAY);
        taskPanel.add(topTask);
        taskPanel.add(jscrlpBox);

        //Adding elements to event panel
        JButton addEvent = new JButton("+");
        topEvent.add(addEvent);
        topEvent.setBackground(Color.LIGHT_GRAY);
        eventPanel.add(topEvent);
        eventPanel.add(scrollEvent);

        //Action listeners for the buttons
        addTask.addActionListener(event -> addNewTask());
        addEvent.addActionListener(event -> addNewEvent());

        //Top panel creation and additions
        JPanel today = new JPanel();
        showing = new JLabel("Showing: " + todaysDate);
        today.add(showing);
        JButton changeButton = new JButton("Change");
        today.add(changeButton);
        changeButton.addActionListener(event -> changeDate());
        today.setBackground(Color.cyan);

        //Setting up bottom task items
        JPanel bottomTaskPanel = new JPanel();
        JButton showTask = new JButton("Show Completed");
        JButton hideTask = new JButton("Hide Completed");
        bottomTaskPanel.add(showTask);
        bottomTaskPanel.add(hideTask);
        bottomTaskPanel.setPreferredSize(new Dimension(550, -262));
        taskPanel.add(bottomTaskPanel);
        showTask.addActionListener(event -> showTasks());
        hideTask.addActionListener(event -> hideTasks());
        bottomTaskPanel.setBackground(Color.cyan);

        //Setting up bottom event items
        JPanel bottomEventPanel = new JPanel();
        JButton showPassed = new JButton("Show Concluded Events");
        JButton hidePassed = new JButton("Hide Concluded Events");
        bottomEventPanel.add(showPassed);
        bottomEventPanel.add(hidePassed);
        bottomEventPanel.setPreferredSize(new Dimension(550, -262));
        eventPanel.add(bottomEventPanel);
        showPassed.addActionListener(event -> showEvents());
        hidePassed.addActionListener(event -> hideEvents());
        bottomEventPanel.setBackground(Color.cyan);

        //finalizing content pane
        setContentPane(new JPanel(new BorderLayout()));
        getContentPane().add(taskPanel, BorderLayout.WEST);
        getContentPane().add(eventPanel, BorderLayout.EAST);
        getContentPane().add(today, BorderLayout.NORTH);
        getContentPane().setBackground(Color.cyan);
    }

    /**
     * Method for when add new task button is pressed
     */
    private void addNewTask() {
        //Create new frame to pop up
        JFrame newTaskFrame = new JFrame("New Task");
        newTaskFrame.setSize(400, 150);
        newTaskFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newTaskFrame.setLocationRelativeTo(null);

        //Adding label and text area for name
        JLabel taskNameLabel = new JLabel("Task Name");
        taskNameArea = new JTextArea(1, 25);
        taskNameArea.setEditable(true);
        JPanel topPanel = new JPanel();

        //Adding new labels and text areas for date
        JLabel taskDay = new JLabel("Task Day");
        JLabel taskMonth = new JLabel("Task Month");
        JLabel taskYear = new JLabel("Task Year");
        taskDayInput = new JTextArea(1, 2);
        taskMonthInput = new JTextArea(1, 2);
        taskYearInput = new JTextArea(1, 4);
        JPanel bottomPanel = new JPanel();

        //Adding task name fields to a new panel
        topPanel.add(taskNameLabel);
        topPanel.add(taskNameArea);

        //adding task date fields to a new panel     
        bottomPanel.add(taskMonth);
        bottomPanel.add(taskMonthInput);
        bottomPanel.add(taskDay);
        bottomPanel.add(taskDayInput);
        bottomPanel.add(taskYear);
        bottomPanel.add(taskYearInput);

        //Button creation and adding to panel with listener
        JButton taskSubmit = new JButton("Add");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(taskSubmit);
        taskSubmit.addActionListener(event -> addTaskToFile(taskNameArea.getText(), taskDayInput.getText(),
                taskMonthInput.getText(), taskYearInput.getText()));

        //merging panels together
        JPanel newTaskPanel = new JPanel();
        newTaskPanel.setLayout(new BoxLayout(newTaskPanel, BoxLayout.PAGE_AXIS));
        newTaskPanel.add(topPanel);
        newTaskPanel.add(bottomPanel);
        newTaskPanel.add(buttonPanel);

        //setting up content pane
        newTaskFrame.getContentPane().add(newTaskPanel);
        newTaskFrame.setVisible(true);
        newTaskFrame.setResizable(false);
    }

    /**
     * Writes to the text file and calls to refresh the UI
     *
     * @param name task name
     * @param day task day
     * @param month task month
     * @param year task year
     */
    private void addTaskToFile(String name, String day, String month, String year) {
        boolean notInt = false;

        //Check if user's input are valid, and pop up if they are not
        try {
            Integer.parseInt(day);
            Integer.parseInt(month);
            Integer.parseInt(year);
        } catch (IllegalArgumentException iae) {
            JOptionPane.showMessageDialog(this, "Make sure date inputs are numbers.");
            notInt = true;
        }
        if ("".equals(name) || day.length() != 2 || "".equals(day) || month.length() != 2 || "".equals(month) || year.length() != 4 || "".equals(year)
                || notInt == true) {
            JOptionPane.showMessageDialog(this, "Please follow the input conventions:\n"
                    + "No field should be empty.\nFormat day and month as two digits (1 should be 01).\nFormat year as four digits");
        } else {
            //If they are valid, write to file and refresh UI
            try {
                Task tempTask = new Task(name, month + "/" + day + "/" + year);
                appController.addThisTask(tempTask);
                try (BufferedWriter taskWriter = new BufferedWriter(new FileWriter("tasks.txt", true))) {
                    taskWriter.newLine();
                    taskWriter.write(tempTask.toString() + "," + tempTask.getDate());
                    refreshTasks(tempTask);
                }
            } catch (FileNotFoundException fnf) {
                System.out.println(fnf.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
    }

    /**
     * Method for when add new event button is pressed
     */
    private void addNewEvent() {
        //Create new frame
        JFrame newEventFrame = new JFrame("New Event");
        newEventFrame.setSize(400, 200);
        newEventFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newEventFrame.setLocationRelativeTo(null);

        //create name fields
        JLabel eventNameLabel = new JLabel("Event Name");
        eventNameArea = new JTextArea(1, 25);
        eventNameArea.setEditable(true);
        JPanel topPanel = new JPanel();

        //create start time fields
        JLabel eventStartTime = new JLabel("Starting Time");
        eventStartHourInput = new JTextArea(1, 2);
        JLabel eventStartTimeDivider = new JLabel(":");
        eventStartMinInput = new JTextArea(1, 2);
        JPanel midStartPanel = new JPanel();

        //create end time fields
        JLabel eventEndTime = new JLabel("Ending Time");
        eventEndHourInput = new JTextArea(1, 2);
        JLabel eventEndTimeDivider = new JLabel(":");
        eventEndMinInput = new JTextArea(1, 2);
        JPanel midEndPanel = new JPanel();

        //create day fields
        JLabel eventDay = new JLabel("Event Day");
        JLabel eventMonth = new JLabel("Event Month");
        JLabel eventYear = new JLabel("Event Year");
        eventDayInput = new JTextArea(1, 2);
        eventMonthInput = new JTextArea(1, 2);
        eventYearInput = new JTextArea(1, 4);
        JPanel bottomPanel = new JPanel();

        //adding name fields to a panel
        topPanel.add(eventNameLabel);
        topPanel.add(eventNameArea);

        //adding start time fields to panel
        midStartPanel.add(eventStartTime);
        midStartPanel.add(eventStartHourInput);
        midStartPanel.add(eventStartTimeDivider);
        midStartPanel.add(eventStartMinInput);

        //adding end time fields to panel
        midEndPanel.add(eventEndTime);
        midEndPanel.add(eventEndHourInput);
        midEndPanel.add(eventEndTimeDivider);
        midEndPanel.add(eventEndMinInput);

        //adding date fields to panel
        bottomPanel.add(eventMonth);
        bottomPanel.add(eventMonthInput);
        bottomPanel.add(eventDay);
        bottomPanel.add(eventDayInput);
        bottomPanel.add(eventYear);
        bottomPanel.add(eventYearInput);

        //setting up button with listener
        JButton eventSubmit = new JButton("Add");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(eventSubmit);
        eventSubmit.addActionListener(event -> addEventToFile(eventNameArea.getText(), eventStartHourInput.getText(), eventStartMinInput.getText(),
                eventEndHourInput.getText(), eventEndMinInput.getText(), eventDayInput.getText(), eventMonthInput.getText(), eventYearInput.getText()));

        //Adding panels to a single panel
        JPanel newEventPanel = new JPanel();
        newEventPanel.setLayout(new BoxLayout(newEventPanel, BoxLayout.PAGE_AXIS));
        newEventPanel.add(topPanel);
        newEventPanel.add(midStartPanel);
        newEventPanel.add(midEndPanel);
        newEventPanel.add(bottomPanel);
        newEventPanel.add(buttonPanel);

        //Setting up content pane
        newEventFrame.getContentPane().add(newEventPanel);
        newEventFrame.setVisible(true);
        newEventFrame.setResizable(false);
    }

    /**
     * Method for adding user input to text file
     */
    private void addEventToFile(String name, String startHour, String startMin, String endHour, String endMin, String day, String month, String year) {
        boolean notInt = false;

        //check if input meets required criteria
        try {
            Integer.parseInt(day);
            Integer.parseInt(month);
            Integer.parseInt(year);
            Integer.parseInt(startHour);
            Integer.parseInt(startMin);
            Integer.parseInt(endHour);
            Integer.parseInt(endMin);
        } catch (IllegalArgumentException iae) {
            JOptionPane.showMessageDialog(this, "Make sure date and time inputs are numbers.");
            notInt = true;
        }
        if ("".equals(name) || day.length() != 2 || "".equals(day) || month.length() != 2 || "".equals(month) || year.length() != 4 || "".equals(year)
                || "".equals(startHour) || "".equals(startMin) || "".equals(endHour) || "".equals(endMin) || notInt == true || startHour.length() != 2
                || startMin.length() != 2 || endHour.length() != 2 || endMin.length() != 2) {
            JOptionPane.showMessageDialog(this, "Please follow the input conventions:\n"
                    + "No field should be empty.\nFormat day and month as two digits (1 should be 01).\nFormat year as four digits."
                    + "\nInput times in military time.");
        } else {
            //When validated, write to file and refresh UI
            try {
                Event tempEvent = new Event(name, month + "/" + day + "/" + year, startHour + ":" + startMin, endHour + ":" + endMin);
                appController.addThisEvent(tempEvent);
                try (BufferedWriter eventWriter = new BufferedWriter(new FileWriter("events.txt", true))) {
                    eventWriter.newLine();
                    eventWriter.write(tempEvent.toString());
                    refreshEvents();
                }
            } catch (FileNotFoundException fnf) {
                System.out.println(fnf.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
    }

    /**
     * Method for when change date button is pressed
     */
    private void changeDate() {
        //Create new frame to pop up
        newDateFrame = new JFrame("Change Date");
        newDateFrame.setSize(400, 150);
        newDateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newDateFrame.setLocationRelativeTo(null);

        //Adding new labels and text areas for date
        JLabel taskDay = new JLabel("Day");
        JLabel taskMonth = new JLabel("Month");
        JLabel taskYear = new JLabel("Year");
        taskDayInput = new JTextArea(1, 2);
        taskMonthInput = new JTextArea(1, 2);
        taskYearInput = new JTextArea(1, 4);
        JPanel datePanel = new JPanel();

        //adding date fields to a new panel
        datePanel.add(taskMonth);
        datePanel.add(taskMonthInput);
        datePanel.add(taskDay);
        datePanel.add(taskDayInput);
        datePanel.add(taskYear);
        datePanel.add(taskYearInput);

        //Button creation and adding to panel with listener
        JButton taskSubmit = new JButton("Change");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(taskSubmit);
        taskSubmit.addActionListener(event -> refreshToNewDate(taskDayInput.getText(),
                taskMonthInput.getText(), taskYearInput.getText()));

        //merging panels together
        JPanel newDatePanel = new JPanel();
        newDatePanel.setLayout(new BoxLayout(newDatePanel, BoxLayout.PAGE_AXIS));
        newDatePanel.add(datePanel);
        newDatePanel.add(buttonPanel);

        //setting up content pane
        newDateFrame.getContentPane().add(newDatePanel);
        newDateFrame.setVisible(true);
        newDateFrame.setResizable(false);
    }

    /**
     * Method for when show tasks is pressed
     */
    private void showTasks() {
        //Makes every task visible
        appController.getBoxList().stream().forEach(task -> {
            task.setVisible(true);
        });
    }

    /**
     * Method for when hide tasks is pressed
     */
    private void hideTasks() {
        //Hides checked tasks
        appController.getBoxList().stream().forEach(task -> {
            task.setVisible(!task.isSelected());
        });
    }

    /**
     * Method for when show events is pressed
     */
    private void showEvents() {
        events.setText("Your Events:\n\n");
        Set<Event> eventItemNames = appController.getAllEvents().keySet();
        Iterator<Event> eventIterator = eventItemNames.iterator();
        while (eventIterator.hasNext()) {
            Event item = eventIterator.next();
            if (item.getDate().equals(appController.getViewingDate())) {
                events.setText(events.getText() + item.getTitle() + "\n"
                        + item.getTime() + "\n\n");
            }
        }
    }

    /**
     * Method for when hide events is pressed
     */
    private void hideEvents() {
        events.setText("Your Events:\n\n");
        Set<Event> eventItemNames = appController.getAllEvents().keySet();
        Iterator<Event> eventIterator = eventItemNames.iterator();
        while (eventIterator.hasNext()) {
            Event item = eventIterator.next();
            if (item.getDate().equals(appController.getViewingDate())) {
                if (Integer.parseInt(item.getEndTime().split(":")[0]) >= Integer.parseInt(interpretTime()[0])) {
                    //Compares minutes 
                    if (Integer.parseInt(item.getEndTime().split(":")[0]) >= Integer.parseInt(interpretTime()[0])
                            || Integer.parseInt(item.getEndTime().split(":")[1]) >= Integer.parseInt(interpretTime()[1])) {
                        events.setText(events.getText() + item.getTitle() + "\n"
                                + item.getTime() + "\n\n");
                    }
                }
            }
        }
    }

    /**
     * Updates GUI and blanks input fields
     */
    private void refreshEvents() {
        //Updates displayed text
        events.setText("Your Events: \n\n");
        Set<Event> eventItemNames = appController.getAllEvents().keySet();
        Iterator<Event> eventIterator = eventItemNames.iterator();
        while (eventIterator.hasNext()) {
            Event item = eventIterator.next();
            if (item.getDate().equals(appController.getViewingDate())) {
                events.setText(events.getText() + item.getTitle() + "\n"
                        + item.getTime() + "\n\n");
            }
        }
        this.repaint();
        this.validate();

        //blanks input fields
        eventNameArea.setText("");
        eventStartHourInput.setText("");
        eventStartMinInput.setText("");
        eventEndHourInput.setText("");
        eventEndMinInput.setText("");
        eventDayInput.setText("");
        eventMonthInput.setText("");
        eventYearInput.setText("");
    }

    /**
     * Updates GUI and blanks input fields
     */
    private void refreshTasks(Task temp) {
        //add new checkbox and refresh
        if (temp.getDate().equals(appController.getViewingDate())) {
            JCheckBox tempBox = new JCheckBox(temp.toString());
            appController.addBox(tempBox);
            taskBox.add(tempBox);
        }
        this.repaint();
        this.validate();

        //blank input fields
        taskNameArea.setText("");
        taskDayInput.setText("");
        taskMonthInput.setText("");
        taskYearInput.setText("");
    }

    /**
     * Puts time into a readable format
     *
     * @return array of time as 0 being hour, 1 being minutes, and 2 being
     * seconds
     */
    private String[] interpretTime() {
        return appController.getUnformattedDate().toString().split(" ")[3].split(":");
    }

    /**
     * Updates GUI to match requested date
     *
     * @param day requested day
     * @param month requested month
     * @param year requested year
     */
    private void refreshToNewDate(String day, String month, String year) {
        boolean notInt = false;

        //check if input meets required criteria
        try {
            Integer.parseInt(day);
            Integer.parseInt(month);
            Integer.parseInt(year);
        } catch (IllegalArgumentException iae) {
            JOptionPane.showMessageDialog(this, "Make sure date inputs are numbers.");
            notInt = true;
        }
        if (day.length() != 2 || "".equals(day) || month.length() != 2 || "".equals(month) || year.length() != 4 || "".equals(year) || notInt == true) {
            JOptionPane.showMessageDialog(this, "Please follow the input conventions:\n"
                    + "No field should be empty.\nFormat day and month as two digits (1 should be 01).\nFormat year as four digits.");
        } else {
            String date = month + "/" + day + "/" + year;
            appController.showDate(date);
            showing.setText("Showing: " + date);
            events.setText("Your Events:\n\n");
            taskBox.removeAll();
            taskBox.add(new JLabel("Your Tasks:"));
            taskBox.add(new JLabel(" "));

            //Iterates through the task list to get all elements and add them
            //as check boxes       
            Set<Task> taskItemNames = appController.getAllTasks().keySet();
            Iterator<Task> taskIterator = taskItemNames.iterator();
            while (taskIterator.hasNext()) {
                Task item = taskIterator.next();
                if (item.getDate().equals(appController.getViewingDate())) {
                    JCheckBox tempCheckBox = new JCheckBox(item.toString());
                    taskBox.add(tempCheckBox);
                    appController.getBoxList().add(tempCheckBox);
                }
            }

            //Iterates throught the event list to get all elements and add them
            //as text
            Set<Event> eventItemNames = appController.getAllEvents().keySet();
            Iterator<Event> eventIterator = eventItemNames.iterator();
            while (eventIterator.hasNext()) {
                Event item = eventIterator.next();
                if (item.getDate().equals(appController.getViewingDate())) {
                    events.setText(events.getText() + item.getTitle() + "\n"
                            + item.getTime() + "\n\n");
                }
            }
            getContentPane().validate();
            getContentPane().repaint();
            newDateFrame.dispose();
        }
    }
}
