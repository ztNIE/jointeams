package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.Semester;
import com.jointeams.backend.repositery.SemesterRepository;
import com.jointeams.backend.service.SemesterService;
import com.jointeams.backend.util.JsonResult;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SemesterServiceImpl implements SemesterService {
    @Autowired
    private SemesterRepository semesterRepository;

    @Override
    public JsonResult findCurrentSemesterFeedback() {
        Semester currentSemester = semesterRepository.findSemestersByIsCurrent(true).orElse(null);
        JsonResult jsonResult = new JsonResult();
        if(currentSemester == null)
        {
            jsonResult.setStatus(0);
            jsonResult.setMsgAndData("The current semester isn't set!", Optional.empty());
        }
        else
        {
            jsonResult.setStatus(1);
            jsonResult.setMsgAndData( "The current semester is found successfully!",
                    currentSemester);
        }
        return jsonResult;
    }

    public int changeCurrentSemester(int year, int semesterNumber) {
        Semester currentSemesterOld = semesterRepository.findSemestersByIsCurrent(true).orElse(null);
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
    public JsonResult changeCurrentSemesterFeedback(int year, int semesterNumber) {
        JsonResult jsonResult = new JsonResult();
        int resultCode = changeCurrentSemester(year, semesterNumber);
        jsonResult.setStatus(resultCode);
        if(resultCode == 0)
        {
            jsonResult.setMsgAndData("The current semester isn't changed, because it's the same to the one stored " +
                    "in the database!", Optional.empty());
        }
        else if (resultCode == 1)
        {
            jsonResult.setMsgAndData("The current semester has been changed to Year: " +year + " ,Semester: " +
                    semesterNumber + " successfully.", Optional.empty());
        }
        return  jsonResult;
    }
}
