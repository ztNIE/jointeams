package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.University;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.repositery.UniversityRepository;
import com.jointeams.backend.service.UniversityService;
import com.jointeams.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniversityServiceImpl implements UniversityService {
    @Autowired
    private UniversityRepository universityRepository;

    @Override
    public University findById(long id) {
        University university = universityRepository.findById(id).orElse(null);
        return university;
    }

    @Override
    public void saveAUniversity(String name) {
        University university = new University();
        university.setName(name);
        universityRepository.save(university);
    }


}
