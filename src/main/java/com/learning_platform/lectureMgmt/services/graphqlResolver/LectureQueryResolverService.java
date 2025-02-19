package com.learning_platform.lectureMgmt.services.graphqlResolver;


import com.learning_platform.lectureMgmt.models.LectureModel;
import com.learning_platform.lectureMgmt.repos.LectureRepository;
import org.springframework.stereotype.Component;
import graphql.kickstart.tools.GraphQLQueryResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class LectureQueryResolverService implements GraphQLQueryResolver {

    private final LectureRepository lectureRepository;

    public LectureQueryResolverService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    public LectureModel getLectureById(String id) {
        System.out.println("QUERY CALLED: " + id);
        return lectureRepository.findById(id).orElse(null);
    }

    public List<LectureModel> listLectures(){

        return lectureRepository.findAll();
    }


    public List<LectureModel> getLecturesByTopic(String topic){
        List<LectureModel> lectureModels =  lectureRepository.findByTopic(topic);
        return lectureModels;
    }

}
