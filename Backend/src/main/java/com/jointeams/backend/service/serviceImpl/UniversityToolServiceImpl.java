package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.University;
import com.jointeams.backend.repositery.UniversityRepository;
import com.jointeams.backend.service.UniversityToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniversityToolServiceImpl implements UniversityToolService {

    @Autowired
    private UniversityRepository universityRepository;

    @Override
    public University findById(Long id) {
        return universityRepository.findById(id).orElse(null);
    }
}
