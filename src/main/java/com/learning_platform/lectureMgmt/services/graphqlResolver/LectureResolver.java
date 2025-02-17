package com.learning_platform.lectureMgmt.services.graphqlResolver;


import com.learning_platform.lectureMgmt.models.LectureModel;
import com.learning_platform.lectureMgmt.repos.LectureRepository;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;
import graphql.kickstart.tools.GraphQLQueryResolver;

import java.util.List;


@Component
public class LectureResolver implements GraphQLQueryResolver {

    private final LectureRepository lectureRepository;

    public LectureResolver(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    public LectureModel getLectureById(String id) {
        System.out.println("QUERY CALLED: " + id);
        return lectureRepository.findById(id).orElse(null);
    }

    public List<LectureModel> listLectures(){
        System.out.println("QUERY CALLED: " );
        return lectureRepository.findAll();
    }

}
