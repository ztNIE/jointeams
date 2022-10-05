package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.University;
import com.jointeams.backend.repositery.UniversityRepository;
import com.jointeams.backend.service.UniversityService;
import com.jointeams.backend.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UniversityServiceImpl implements UniversityService {
    @Autowired
    private UniversityRepository universityRepository;

    @Override
    public University addUniversity(University university) {
        return universityRepository.save(university);
    }

    @Override
    public JsonResult findAllFeedback()
    {
        List<University> universities = (List<University>) universityRepository.findAll();
        JsonResult jsonResult = new JsonResult();
        if(universities.size() == 0)
        {
            jsonResult.setStatus(0);
            jsonResult.setMsgAndData("No university is found!", Optional.empty());
        }
        else
        {
            jsonResult.setStatus(1);
            jsonResult.setMsgAndData("Finding all universities successful!", universities);
        }
        return jsonResult;
    }

    @Override
    public Iterable<University> getAllUniversities() {
        return universityRepository.findAll();
    }
}
