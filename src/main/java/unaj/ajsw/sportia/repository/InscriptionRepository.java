package unaj.ajsw.sportia.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import unaj.ajsw.sportia.model.Inscription;
import unaj.ajsw.sportia.model.Lesson;

import java.util.List;

public interface InscriptionRepository extends MongoRepository<Inscription, ObjectId> {
    List<Inscription> findInscriptionByUserId(ObjectId userId);
}
