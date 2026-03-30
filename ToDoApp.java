import java.util.*;
import java.io.*;
public class ToDoApp {

    static ArrayList<Task> tasks = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        loadTasks(); // load saved tasks
        while (true) {
            System.out.println("\n1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Complete");
            System.out.println("4. Delete Task");
            System.out.println("5. Check Reminder");
            System.out.println("6. Exit");
            int choice = sc.nextInt();
            switch (choice) {
                case 1: addTask(); break;
                case 2: viewTasks(); break;
                case 3: markComplete(); break;
                case 4: deleteTask(); break;
                case 5: checkReminder(); break;
                case 6: System.exit(0);
                default: System.out.println("Invalid choice");
            }
        }
    }
    //ADD TASK
    static void addTask() {
        System.out.print("Enter Task: ");
        sc.nextLine();
        String title = sc.nextLine();

        System.out.print("Enter reminder time (HH:MM): ");
        String time = sc.nextLine();

        tasks.add(new Task(title, time));
        saveTasks();

        System.out.println("Task Added!");
    }

    //VIEW TASKS
    static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            System.out.println(i + ". " + t.title + " at " + t.reminderTime +
                    " [" + (t.isCompleted ? "Done" : "Pending") + "]");
        }
    }

    // MARK COMPLETE
    static void markComplete() {
        System.out.print("Enter task index: ");
        int index = sc.nextInt();

        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).isCompleted = true;
            saveTasks();
            System.out.println("Marked as complete!");
        } else {
            System.out.println("Invalid index");
        }
    }

    //DELETE TASK
    static void deleteTask() {
        System.out.print("Enter task index: ");
        int index = sc.nextInt();

        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            saveTasks();
            System.out.println("Task deleted!");
        } else {
            System.out.println("Invalid index");
        }
    }

    // SAVE TASK TO FILE
    static void saveTasks() {
        try {
            FileWriter fw = new FileWriter("tasks.txt");

            for (Task t : tasks) {
                fw.write(t.title + "," + t.reminderTime + "," + t.isCompleted + "\n");
            }

            fw.close();
        } catch (Exception e) {
            System.out.println("Error saving tasks");
        }
    }

    //LOAD TASK FROM FILES
    static void loadTasks() {
        try {
            File file = new File("tasks.txt");
            if (!file.exists()) return;

            Scanner fileSc = new Scanner(file);

            while (fileSc.hasNextLine()) {
                String line = fileSc.nextLine();
                String[] parts = line.split(",");

                String title = parts[0];
                String time = parts[1];
                boolean status = Boolean.parseBoolean(parts[2]);

                Task t = new Task(title, time);
                t.isCompleted = status;

                tasks.add(t);
            }

            fileSc.close();
        } catch (Exception e) {
            System.out.println("Error loading tasks");
        }
    }

    // REMINDER CHECK
    static void checkReminder() {
        System.out.print("Enter current time (HH:MM): ");
        sc.nextLine();
        String currentTime = sc.nextLine();

        for (Task t : tasks) {
            if (t.reminderTime.equals(currentTime) && !t.isCompleted) {
                System.out.println("🔔 Reminder: " + t.title);
            }
        }
    }

    //TASK CLASS
    static class Task {
        String title;
        boolean isCompleted;
        String reminderTime;

        Task(String title, String reminderTime) {
            this.title = title;
            this.reminderTime = reminderTime;
            this.isCompleted = false;
        }
    }
}