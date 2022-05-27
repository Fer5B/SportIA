package unaj.ajsw.sportia.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import unaj.ajsw.sportia.model.Lesson;

public interface LessonRepository extends MongoRepository<Lesson, ObjectId> {
}
