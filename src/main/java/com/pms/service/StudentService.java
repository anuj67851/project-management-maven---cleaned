package com.pms.service;

import com.pms.Dao.*;
import com.pms.entity.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private LoginDao loginDao;
    private StudentDao studentDao;
    private ProjectDao projectDao;
    private FilesDao filesDao;
    private S3Repository s3Repository;

    @Autowired
    public StudentService(LoginDao loginDao, StudentDao studentDao, ProjectDao projectDao, FilesDao filesDao, S3Repository s3Repository) {
        this.loginDao = loginDao;
        this.studentDao = studentDao;
        this.projectDao = projectDao;
        this.filesDao = filesDao;
        this.s3Repository = s3Repository;
    }

    public Student getStudent(int id) {
        return loginDao.getStudent(id);
    }

    public boolean checkProject(int id) {
        Student student = loginDao.getStudent(id);
        return student.getProject() == null;
    }


    public boolean checkProject(String gm1, String gm2, String gm3) {

        Student s1, s2, s3;
        s1 = studentDao.getStudentByRollProject(gm1);
        s2 = studentDao.getStudentByRollProject(gm2);
        s3 = studentDao.getStudentByRollProject(gm3);

        boolean ans = true;

        if (s1 != null && s1.getProject() != null) {
            ans = false;
        }
        if (s2 != null && s2.getProject() != null) {
            ans = false;
        }
        if (s3 != null && s3.getProject() != null) {
            ans = false;
        }

        return ans;
    }

    public void submitProject(Integer sid, String title, String tools, String technologies, Date sdate, CommonsMultipartFile synopsis, String cmpname, String cmpcontact, String cmpextname, String cmpextemail, String cmpaddress, String cmpextcontact, String cmphrname, String hrcontact, String gm1, String gm2, String gm3) {

        Company company = new Company();
        company.setName(cmpname);
        company.setAddress(cmpaddress);
        company.setContactno(cmpcontact);
        company.setExternalGuideContact(cmpextcontact);
        company.setExternalGuideEmail(cmpextemail);
        company.setExternalGuideName(cmpextname);
        company.setHrContact(hrcontact);
        company.setHrname(cmphrname);

        Project project = new Project();
        project.setCompany(company);
        project.setStartingDate(sdate);
        project.setApprovalStatus(0);
        project.setTitle(title);
        project.setTools(tools);
        project.setTechnologies(technologies);

        Student s1 = loginDao.getStudent(sid);
        company.setProject(project);

        Set<Student> students = new HashSet<>();

        students.add(s1);

        Student s4, s2, s3;

        s2 = studentDao.getStudentByRollProject(gm1);
        s3 = studentDao.getStudentByRollProject(gm2);
        s4 = studentDao.getStudentByRollProject(gm3);

        if (s2 != null) {
            students.add(s2);
        }
        if (s3 != null) {
            students.add(s3);
        }
        if (s4 != null) {
            students.add(s4);
        }

        Integer projectId = projectDao.addProject(project, company, students);

        s3Repository.saveSynopsis(synopsis, projectId);
        Files synop = new Files();
        synop.setKey("synopsis/" + projectId + "." + FilenameUtils.getExtension(synopsis.getOriginalFilename()));
        synop.setName(synopsis.getOriginalFilename());

        filesDao.saveSynopsis(synop, projectId);

    }

    public List<String> getRollsActivatedAndFree(int id) {

        List<Student> students = studentDao.getRollsActivatedAndFree(id);

        return students.stream().map(Student::getUsername).collect(Collectors.toList());
    }

    public Templates getSynopsis() {
        return filesDao.getSynopsis();
    }

    public Student updateProfile(Student student, String fname, String mname, String lname, String email, String phone) {
        return studentDao.updateStudentProfile(student.getId(), fname, mname, lname, email, phone);

    }

    public void setStudentStatus(int id, int i) {
        studentDao.setStudentStatus(id, i);
    }
}
