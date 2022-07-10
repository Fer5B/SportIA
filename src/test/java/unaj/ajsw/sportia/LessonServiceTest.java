package unaj.ajsw.sportia;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import unaj.ajsw.sportia.model.Lesson;
import unaj.ajsw.sportia.model.LessonType;
import unaj.ajsw.sportia.repository.LessonRepository;
import unaj.ajsw.sportia.service.LessonService;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LessonServiceTest {

    @InjectMocks
    private LessonService lessonService;

    @Mock
    LessonRepository lessonRepository;

    @Test
    void findLessonsByRangeTimeTest(){
        List<Lesson> lessons = Arrays.asList(
                Lesson.builder().name("Billar").type(LessonType.RECREATIONAL).startTime("14:30").build(),
                Lesson.builder().name("Fútbol").type(LessonType.SPORTS).startTime("19:00").build(),
                Lesson.builder().name("Poker").type(LessonType.RECREATIONAL).startTime("22:45").build()
        );

        when(lessonRepository.findAll()).thenReturn(lessons);
        when(lessonRepository.findLessonByTypeAndStartTimeBetween("recreational",
                "12:00", "16:00")).thenReturn(Arrays.asList(lessons.get(1)));

        assertEquals(3, lessonService.findAll().size());
        verify(lessonRepository, times(1)).findAll();

        assertEquals("Billar", lessonService.findLessonByTypeAndStartTimeBetween(
                "recreational", "12:00", "16:00"));
        verify(lessonRepository, times(1)).findLessonByTypeAndStartTimeBetween(
                "recreational", "12:00", "16:00");
    }
}
