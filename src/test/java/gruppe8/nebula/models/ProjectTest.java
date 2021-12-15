package gruppe8.nebula.models;

import gruppe8.nebula.entities.ProjectEntity;
import gruppe8.nebula.entities.TaskEntity;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

class ProjectTest {

    Project project;
    Project projectEmpty;

    @BeforeEach
    public void init() {
        //Arrange
        project = new Project(1L,"testproject");
        projectEmpty = new Project(1L,"testprojectEmpty");
        ArrayList<TaskEntity> list = new ArrayList<>();
        LocalDateTime dt1s = LocalDateTime.of(2015, Month.JULY, 22, 19, 30, 40);
        LocalDateTime dt1e = LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40);
        TaskEntity e1 = new TaskEntity(1L,1L,0L,"task1",dt1s,dt1e,10L,null,null);

        LocalDateTime dt2s = LocalDateTime.of(2015, Month.JULY, 22, 19, 30, 40);
        LocalDateTime dt2e = LocalDateTime.of(2015, Month.JULY, 25, 19, 30, 40);
        TaskEntity e2 = new TaskEntity(2L,1L,1L,"task1",dt1s,dt1e,8L,null,null);

        LocalDateTime dt3s = LocalDateTime.of(2015, Month.JULY, 24, 19, 30, 40);
        LocalDateTime dt3e = LocalDateTime.of(2015, Month.JULY, 28, 19, 30, 40);
        TaskEntity e3 = new TaskEntity(3L,1L,2L,"task1",dt1s,dt1e,11L,null,null);

        LocalDateTime dt4s = LocalDateTime.of(2015, Month.JULY, 25, 19, 30, 40);
        LocalDateTime dt4e = LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40);
        TaskEntity e4 = new TaskEntity(4L,1L,0L,"task1",dt1s,dt1e,30L,null,null);

        LocalDateTime dt5s = LocalDateTime.of(2015, Month.JULY, 25, 19, 30, 40);
        LocalDateTime dt5e = LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40);
        TaskEntity e5 = new TaskEntity(5L,1L,1L,"task1",dt1s,dt1e,5L,null,null);

        list.add(e1);
        list.add(e2);
        list.add(e3);
        list.add(e4);
        list.add(e5);

        project.setSubtasks(list);
    }

    @Test
    void getTotalProjectSpanDays() {
        // Act
        int days = project.getTotalProjectSpanDays();
        int daysNull = projectEmpty.getTotalProjectSpanDays();

        //Assert
        assertEquals(8,days);
        assertEquals(0,daysNull);
    }

    @Test
    void levelOrderTraversal() {
        System.out.println("test 2");
        // Arrange

        List<List<Integer>> layers = new ArrayList<>();
        List<Integer> emptyList = new ArrayList<>();
        List<Integer> whenTaskNull = new ArrayList<>();

        // Act
        for (Task task : project.getSubtasks()) {
            layers.add(project.LevelOrderTraversal(task));
        }

        whenTaskNull = project.LevelOrderTraversal(null);
        // Assert
        assertEquals(10, layers.get(0).get(0));
        assertEquals(13, layers.get(0).get(1));
        assertEquals(11, layers.get(0).get(2));

        assertEquals(30, layers.get(1).get(0));

        assertEquals(emptyList, whenTaskNull);

    }

    @Test
    void getTotalProjectSpanDates() {
        // Act
        List<String> projectSpan = project.getTotalProjectSpanDates();

        // Assert
        assertEquals(project.getTotalProjectSpanDays(),projectSpan.size());
        assertEquals("2015-07-22",projectSpan.get(0));
        assertEquals("2015-07-23",projectSpan.get(1));
        assertEquals("2015-07-24",projectSpan.get(2));
        assertEquals("2015-07-25",projectSpan.get(3));
        assertEquals("2015-07-26",projectSpan.get(4));
        assertEquals("2015-07-27",projectSpan.get(5));
        assertEquals("2015-07-28",projectSpan.get(6));
        assertEquals("2015-07-29",projectSpan.get(projectSpan.size()-1));


    }
}