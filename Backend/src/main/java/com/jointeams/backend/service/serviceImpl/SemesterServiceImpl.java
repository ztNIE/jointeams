package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.Semester;
import com.jointeams.backend.repositery.SemesterRepository;
import com.jointeams.backend.service.SemesterService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SemesterServiceImpl implements SemesterService {
    @Autowired
    private SemesterRepository semesterRepository;

    @Override
    public JSONObject findCurrentSemesterFeedback() {
        Semester currentSemester = semesterRepository.findSemestersByCurrent().orElse(null);
        JSONObject jsonResult = new JSONObject();
        if(currentSemester == null)
        {
            jsonResult.put("finding the current semester status", 0);
            jsonResult.put("finding current semester status msg", "The current semester isn't set!");
        }
        else
        {
            jsonResult.put("finding the current semester status", 1);
            jsonResult.put("current semester", currentSemester);
        }
        return jsonResult;
    }

    @Override
    public int changeCurrentSemester(int year, int semesterNumber) {
        Semester currentSemesterOld = semesterRepository.findSemestersByCurrent().orElse(null);
        if(currentSemesterOld == null)
        {
            Semester currentSemesterNew = new Semester();
            currentSemesterNew.setYear(year);
            currentSemesterNew.setSemesterNumber(semesterNumber);
            currentSemesterNew.setCurrent(true);
            semesterRepository.save(currentSemesterNew);
            return 1;
        }
        else
        {
            if(currentSemesterOld.getYear() != year || currentSemesterOld.getSemesterNumber() != semesterNumber)
            {
                currentSemesterOld.setCurrent(false);
                semesterRepository.save(currentSemesterOld);
                Semester currentSemesterNew = new Semester();
                currentSemesterNew.setYear(year);
                currentSemesterNew.setSemesterNumber(semesterNumber);
                currentSemesterNew.setCurrent(true);
                semesterRepository.save(currentSemesterNew);
                return 1;
            }
            else
            {
                return 0;
            }
        }
    }

    @Override
    public JSONObject changeCurrentSemesterFeedback(int year, int semesterNumber) {
        JSONObject jsonResult = new JSONObject();
        int resultCode = changeCurrentSemester(year, semesterNumber);
        if(resultCode == 0)
        {
            jsonResult.put("changing the current semester status", 0);
            jsonResult.put("changing the current semester status msg", "The current semester isn't changed, because it's the " +
                    "same to the one stored in the database!");
        }
        else if (resultCode == 1)
        {
            jsonResult.put("changing the current semester status", 1);
            jsonResult.put("changing the current semester status msg", "The current semester has been changed to Year: " +year +
                    " ,Semester: " + semesterNumber + ".");
        }
        return  jsonResult;
    }
}
