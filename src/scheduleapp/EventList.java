package scheduleapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author Connell Boyce
 */
public class EventList {

    private Map<Event, String> eventList;

    /**
     * Constructor
     */
    public EventList() {
        eventList = new TreeMap<>();
        File csvFile = new File("events.txt");
        try {
            Scanner fileScanner = new Scanner(csvFile);
            while (fileScanner.hasNextLine()) {
                String[] eventInfo = fileScanner.nextLine().split(",");
                eventList.put(new Event(eventInfo[0], eventInfo[1], eventInfo[2], eventInfo[3]), eventInfo[1]);
                eventList = sortByValues(eventList);
            }
        } catch (FileNotFoundException fnf) {
            System.out.println(fnf.getMessage());
        }
    }

    /**
     * Retrieve the stored event list
     *
     * @return the list stored in this class
     */
    public Map<Event, String> getEvents() {
        return eventList;
    }

    /**
     * Adds the new item to the list so it matches updated file
     *
     * @param event is the event to add
     */
    public void newItem(Event event) {
        eventList.put(event, event.getDate());
    }

    /**
     * String representation
     *
     * @return String EventList
     */
    @Override
    public String toString() {
        String returnMe = "";
        eventList = sortByValues(eventList);
        Iterator<String> iterator = eventList.values().iterator();
        while (iterator.hasNext()) {
            returnMe = iterator.next() + "\n";
        }
        return returnMe;
    }

    /**
     * Compares TreeMap by value instead of key
     *
     * @param <K> key
     * @param <V> value
     * @param map Event List to sort
     * @return sorted list
     */
    public static <K, V extends Comparable<V>> Map<K, V> sortByValues(Map<K, V> map) {
        Comparator<K> comparator = (K item1, K item2) -> {
            if (map.get(item1).compareTo(map.get(item2)) == 0) {
                return 1;
            } else {
                return map.get(item1).compareTo(map.get(item2));
            }
        };
        Map<K, V> sortedEventList = new TreeMap<>(comparator);
        sortedEventList.putAll(map);
        return sortedEventList;
    }
}
