package service;

import entity.Subject;
import repository.SubjectDao;

import java.util.List;

public class SubjectService {

    SubjectDao subjectDao = new SubjectDao();

    public List<Subject> getListSubject() {
        return subjectDao.getAllSubject();
    }

}
