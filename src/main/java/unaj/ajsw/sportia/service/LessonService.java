package unaj.ajsw.sportia.service;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import unaj.ajsw.sportia.model.Lesson;
import unaj.ajsw.sportia.model.LessonModality;
import unaj.ajsw.sportia.model.LessonType;
import unaj.ajsw.sportia.model.Location;
import unaj.ajsw.sportia.repository.LessonRepository;


import java.time.LocalTime;
import java.util.*;

@Service("lessonService")
public class LessonService {

    private final LessonRepository lessonRepository;
    private final MongoTemplate mongoTemplate;

    public LessonService(LessonRepository lessonRepository, MongoTemplate mongoTemplate){
        this.lessonRepository = lessonRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    public void saveLesson(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    public Lesson findLessonById(String id) {
        Lesson lesson = null;
        Optional<Lesson> lessonData = lessonRepository.findById(new ObjectId(id));

        if(lessonData.isPresent()){
            lesson = lessonData.get();
        }

        return lesson;
    }

    public List<Lesson> findLessonsByListId(Set<ObjectId> lessonsId) {
        return lessonRepository.findLessonsByListId(lessonsId);
    }

    public List<Lesson> findLessonsByType(String type) {
        return lessonRepository.findLessonsByType(type.toUpperCase());
    }

    public List<Lesson> findLessonByTypeAndStartTimeBetween(String type, String startTime, String endTime) {
        return lessonRepository.findLessonByTypeAndStartTimeBetween(type, startTime, endTime);
    }

    public String priceFormat(float price) {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.ITALY);
        formatter.format("$ %(,.2f", price);
        return sb.toString();
    }

    public List<String> getTimeRange(int startTime, int endTime){
        List<String> fullTimeRange = new ArrayList<>();

        for(int i = startTime; i <= endTime; i++){
            String hour =  i+"";
            if(i < 10)
                hour = "0"+i;

            if(i == endTime)
                fullTimeRange.add(hour+":00");
            else
                fullTimeRange.addAll(Arrays.asList(hour+":00", hour+":30"));
        }
        return fullTimeRange;
    }

    public List<Lesson> filter(String type, String range_time, String zone) {

        if(type == null)
            return null;

        List<Criteria> filters = new ArrayList<>();
        filters.add(Criteria.where("type").is(type.toUpperCase()));

        if(range_time != null) {
            String[] range_time_split = range_time.split("-");
            filters.add(Criteria.where("startTime")
                    .gte(range_time_split[0])
                    .lte(range_time_split[1])
            );
        }

        if(zone != null && !zone.equals("Todas")) {
            filters.add(Criteria.where("location.city").is(zone));
        }

        Query q = Query.query(new Criteria().andOperator(filters.toArray(new Criteria[filters.size()])));
        List<Lesson> lessons = mongoTemplate.find(q, Lesson.class);

        return lessons;
    }

    //    for test
    public void createLessons() {

        List<String> names = Arrays.asList("Fútbol", "Bascket", "Tenis", "Ping Pong", "Voley",
                "Ciclismo", "Motociclismo", "Senderismo", "Pesca", "Esquí");

        List<String> cities = Arrays.asList("Berazategui", "Florencio Varela", "Quilmes", "Berazategui", "Florencio Varela",
                "Berazategui", "Berazategui", "Florencio Varela", "Lobos", "Quilmes");

        List<String> startTimes = Arrays.asList("14:30", "16:00", "17:00", "10:00", "11:30", "19:00", "18:30",
                "13:00", "15:30", "08:30");

        List<Double> prices = Arrays.asList(10500.80, 8200.6, 9000.0, 6435.99, 7200.3, 11000.0, 12500.5, 13000.75, 9340.55, 12890.4);


        for(int i = 0, j=0; i < 10; i++, j++){
            LessonType type = LessonType.SPORTS;
            LessonModality modality = LessonModality.PRESENCIAL;

            if(i >= 5){
                type = LessonType.RECREATIONAL;
            }
            if(i == 3)
                modality = LessonModality.HIBRIDA;

            lessonRepository.save(
                Lesson.builder()
                    .name(names.get(i))
                    .type(type)
                    .weekDayNumber(j)
                    .startTime(startTimes.get(i))
                    .durationInMinutes(120)
                    .location(Location.builder().country("Argentina").state("Buenos Aires")
                            .city(cities.get(i)).build())
                    .capacity(40)
                    .price(prices.get(i))
                    .modality(modality)
                    .build()
            );

            if(j == 6)
                j = 0;
        }
    }

    private Location getLocationDefault(){
        return Location.builder()
                .country("Argentina")
                .state("Buenos Aires")
                .city("Florencio Varela")
                .zipCode("1888")
                .street("Calchaquí")
                .number(6200)
                .build();
    }

}
