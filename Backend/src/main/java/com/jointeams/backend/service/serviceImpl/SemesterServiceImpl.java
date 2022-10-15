package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.Semester;
import com.jointeams.backend.repositery.SemesterRepository;
import com.jointeams.backend.service.CourseToolService;
import com.jointeams.backend.service.SemesterService;
import com.jointeams.backend.util.IsCommentAvailable;
import com.jointeams.backend.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SemesterServiceImpl implements SemesterService {
    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private CourseToolService courseToolService;

    @Override
    public JsonResult findCurrentSemesterFeedback() {
        Semester currentSemester = semesterRepository.findSemestersByIsCurrent(true).orElse(null);
        JsonResult jsonResult = new JsonResult();
        if(currentSemester == null)
        {
            jsonResult.setStatus(0);
            jsonResult.setMsgAndData("The current semester is not set!", Optional.empty());
        }
        else
        {
            jsonResult.setStatus(1);
            jsonResult.setMsgAndData( "The current semester is found successfully!",
                    currentSemester);
            jsonResult.setData("IsCommentAvailable", IsCommentAvailable.Flag.getValue());
        }
        return jsonResult;
    }

    private void saveNewSemester(int year, int semesterNumber)
    {
        Semester currentSemesterNew = new Semester();
        currentSemesterNew.setYear(year);
        currentSemesterNew.setSemesterNumber(semesterNumber);
        currentSemesterNew.setCurrent(true);
        semesterRepository.save(currentSemesterNew);
        courseToolService.reSetNextGroupNameId();
        IsCommentAvailable.Flag.setValue(false);
    }

    private int changeCurrentSemester(int year, int semesterNumber) {
        Semester currentSemesterOld = semesterRepository.findSemestersByIsCurrent(true).orElse(null);
        if(currentSemesterOld == null)
        {
            saveNewSemester(year, semesterNumber);
            return 1;
        }
        else
        {
            if(currentSemesterOld.getYear() != year || currentSemesterOld.getSemesterNumber() != semesterNumber)
            {
                currentSemesterOld.setCurrent(false);
                semesterRepository.save(currentSemesterOld);
                Semester currentSemesterNew = semesterRepository.findSemesterByYearAndSemesterNumber(year, semesterNumber).orElse(null);
                if(currentSemesterNew == null)
                    saveNewSemester(year, semesterNumber);
                else
                {
                    currentSemesterNew.setCurrent(true);
                    semesterRepository.save(currentSemesterNew);
                    IsCommentAvailable.Flag.setValue(false);
                }
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
