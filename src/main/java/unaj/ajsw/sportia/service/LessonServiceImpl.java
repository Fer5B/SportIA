package unaj.ajsw.sportia.service;

import org.springframework.stereotype.Service;
import unaj.ajsw.sportia.model.Lesson;
import unaj.ajsw.sportia.repository.LessonRepository;

import java.util.List;

@Service("lessonService")
public class LessonServiceImpl implements LessonService{

    private final LessonRepository lessonRepository;

    public LessonServiceImpl(LessonRepository lessonRepository){
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }
}
