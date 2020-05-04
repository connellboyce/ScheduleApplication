package scheduleapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Connell Boyce
 */
public class TaskList {

    private final Map<Task, String> taskList;

    /**
     * Constructor
     */
    public TaskList() {
        taskList = new HashMap<>();

        File csvFile = new File("tasks.txt");
        try {
            Scanner fileScanner = new Scanner(csvFile);
            while (fileScanner.hasNextLine()) {
                String[] taskInfo = fileScanner.nextLine().split(",");
                Task tempTask = new Task(taskInfo[0], taskInfo[1]);
                taskList.put(tempTask, tempTask.getDate());
            }
        } catch (FileNotFoundException fnf) {
            System.out.println(fnf.getMessage());
        }
    }

    /**
     * Retrieve the stored task list
     *
     * @return the list stored in this class
     */
    public Map<Task, String> getTasks() {
        return taskList;
    }

    /**
     * Adds the new item to the list so it matches updated file
     *
     * @param task is the task to add
     */
    public void newItem(Task task) {
        taskList.put(task, task.getDate());
    }
}
