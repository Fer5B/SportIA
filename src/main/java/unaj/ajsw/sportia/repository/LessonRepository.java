package unaj.ajsw.sportia.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import unaj.ajsw.sportia.model.Lesson;
import unaj.ajsw.sportia.model.Location;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public interface LessonRepository extends MongoRepository<Lesson, ObjectId> {
    @Query("{_id: {$in: ?0}}")
    List<Lesson> findLessonsByListId(Set<ObjectId> lessonsId);
    List<Lesson> findLessonByIdIn(Set<ObjectId> lessonsId);
    List<Lesson> findLessonsByType(String type);
//    @Query("{'startTime':{$gte:ISODate(?0),$lt:ISODate(?1)}}")
//    List<Lesson> findLessonByRangeTime(String startTime, String endTime);
    List<Lesson> findLessonByTypeAndStartTimeBetween(String type, String startTime, String endTime);
    List<Lesson> findLessonsByLocation(Location location);

}
