package com.learning_platform.lectureMgmt.services.graphqlResolver;

import com.learning_platform.lectureMgmt.models.LectureModel;
import com.learning_platform.lectureMgmt.repos.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class LectureMutationResolverService {

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    LectureQueryResolverService lectureQueryResolverService;


    public LectureModel createLecture(LectureModel lectureModel){
        return lectureRepository.save(lectureModel);
    }

    public LectureModel updateTopics(String lectureId, List<String> topic){
        // TODO: check if the lecture is created by the same user
        LectureModel lectureModel = lectureQueryResolverService.getLectureById(lectureId);
        List<String> existingTopics = lectureModel.getTopics();
        Set<String> uniqueTopics = new HashSet<>();
        topic.stream().forEach(t -> uniqueTopics.add(t));
        existingTopics.stream().forEach(t-> uniqueTopics.add(t));
        lectureModel.setTopics(uniqueTopics.stream().toList());
        return lectureRepository.save(lectureModel);

    }

}
