package atlas.task;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {

    @Test
    public void add_oneTask_sizeIncreases() {
        TaskList tasks = new TaskList(new ArrayList<>());

        tasks.add(new Todo("read book"));

        assertEquals(1, tasks.size());
        assertEquals("[T][ ] read book", tasks.get(0).toString());
    }

    @Test
    public void delete_middleTask_remainingTasksShiftCorrectly() {
        TaskList tasks = new TaskList(new ArrayList<>());

        tasks.add(new Todo("task 1"));
        tasks.add(new Todo("task 2"));
        tasks.add(new Todo("task 3"));

        tasks.delete(1); // delete "task 2"

        assertEquals(2, tasks.size());
        assertEquals("[T][ ] task 1", tasks.get(0).toString());
        assertEquals("[T][ ] task 3", tasks.get(1).toString());
    }
}
