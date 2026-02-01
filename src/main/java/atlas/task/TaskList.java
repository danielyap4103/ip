package atlas.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a list of tasks in Atlas.
 */
public class TaskList {

    private List<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with existing tasks.
     *
     * @param tasks List of tasks
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the internal list of tasks.
     *
     * @return List of tasks
     */
    public List<Task> toList() {
        return tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task Task to add
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task by index.
     *
     * @param index Index of task
     * @return Removed task
     */
    public Task delete(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the task at the given index.
     *
     * @param index Index of task
     * @return Task
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks.
     *
     * @return Task count
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns all tasks.
     *
     * @return List of tasks
     */
    public List<Task> getAll() {
        return tasks;
    }

    /**
     * Marks a task as done.
     *
     * @param index Index of task
     */
    public void mark(int index) {
        tasks.get(index).markDone();
    }

    /**
     * Unmarks a task.
     *
     * @param index Index of task
     */
    public void unmark(int index) {
        tasks.get(index).unmark();
    }
}
