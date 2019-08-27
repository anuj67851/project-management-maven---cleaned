package com.pms.service;

import com.pms.Dao.*;
import com.pms.entity.*;
import com.pms.utility.BCrypt;
import com.pms.utility.DataWriterStudentInfo;
import com.pms.utility.LinkGen;
import com.pms.utility.MessageSender;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.*;

@Service
public class AdminService {

    private LoginDao loginDao;
    private StudentDao studentDao;
    private EvalDao evalDao;
    private ProjectDao projectDao;
    private GuideDao guideDao;
    private AdminDao adminDao;
    private FilesDao filesDao;
    private S3Repository s3Repository;

    @Autowired
    public AdminService(LoginDao loginDao, StudentDao studentDao, EvalDao evalDao, ProjectDao projectDao, GuideDao guideDao, AdminDao adminDao, FilesDao filesDao, S3Repository s3Repository) {
        this.loginDao = loginDao;
        this.studentDao = studentDao;
        this.evalDao = evalDao;
        this.projectDao = projectDao;
        this.guideDao = guideDao;
        this.adminDao = adminDao;
        this.filesDao = filesDao;
        this.s3Repository = s3Repository;
    }

    public Admin getAdmin(int id) {
        return loginDao.getAdmin(id);
    }


    public List<Student> getAllStudents() {
        return studentDao.getAllStudents();
    }

    public Student getStudentDetail(String roll) {
        return studentDao.getAllDetails(roll);
    }

    public Templates getSynopsis(Integer prjId) {
        Files synopsis = projectDao.getProjectSynopsis(prjId);

        Templates template = new Templates();
        template.setName(synopsis.getName());
        template.setFile(s3Repository.getFile(synopsis.getKey()));
        return template;
    }

    public void deleteStudent(String username) {
        studentDao.deleteStudent(username);
    }

    public Project getProjectDetails(Integer id) {
        return projectDao.getAllDetails(id);
    }

    public List<Student> getFreeStudents() {
        return studentDao.getFreeStudents();
    }

    public void removeStudentFromProj(String username) {
        studentDao.removeProject(username);
    }

    public String registerStudent(String username, String email, String fname, String lname) {
        Student s = new Student();
        s.setFirstname(fname);
        s.setLastname(lname);
        s.setEmail(email);
        s.setUsername(username);
        s.setStatus(0);
        s.setWeeklyAlerts(1);
        s.setWeeklySub(0);
        s.setActivated(0);
        s.setLevel("student");
        s.setConfLetterStatus(0);
        s.setFlag(2);
        s.setAllowProjectEditing(0);
        String statusAq = studentDao.addStudent(s);
        if (statusAq.equals("adds")) {
            LinkGen.sendActivationLinkStudent(s.getFirstname(), s.getUsername(), s.getEmail());
        }
        return statusAq;
    }

    public String registerStudent(CommonsMultipartFile studentData) {
        if (!FilenameUtils.getExtension(studentData.getOriginalFilename()).equalsIgnoreCase("csv")) {
            return "Invalid Extension";
        }
        try {
            CSVParser parser = new CSVParser(new InputStreamReader(studentData.getInputStream()), CSVFormat.DEFAULT);

            Set<String> studentUserSet = new HashSet<>(studentDao.getAllStudentsUsernames());

            Student s = new Student();
            s.setStatus(0);
            s.setConfLetterStatus(0);
            s.setWeeklySub(0);
            s.setWeeklyAlerts(1);
            s.setActivated(0);
            s.setLevel("student");
            s.setFlag(2);
            s.setAllowProjectEditing(0);

            for (CSVRecord record : parser) {
                if (studentUserSet.contains(record.get(0))) {
                    continue;
                }
                if (record.size() != 4) {
                    return "Invalid Contents";
                }
                s.setFirstname(record.get(2));
                s.setLastname(record.get(3));
                s.setEmail(record.get(1).toLowerCase());
                s.setUsername(record.get(0).toUpperCase());
                studentDao.addStudentDirect(s);
                LinkGen.sendActivationLinkStudent(s.getFirstname(), s.getUsername(), s.getEmail());
                studentUserSet.add(s.getUsername());
            }
            parser.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Project> getAllProjects() {
        return projectDao.getAllProjectsInfo();
    }

    public List<Guide> getAllGuides() {
        return guideDao.getAllGuides();
    }

    public Guide getGuideAll(String guide) {
        return guideDao.getAllDetails(guide);
    }

    public String registerGuide(String user, String email, String name) {
        Guide g = new Guide();
        g.setName(name);
        g.setProjectLoadPrimary(0);
        g.setProjectLoadSecondary(0);
        g.setLevel("guide");
        g.setUsername(user);
        g.setEmail(email);
        g.setActivated(0);
        g.setFlag(1);
        g.setIsExaminer(0);
        g.setIsTempExaminer(0);

        String statusAq = guideDao.addGuide(g);
        if (statusAq.equals("addg")) {
            LinkGen.sendActivationLinkGuide(g.getName(), g.getUsername(), g.getEmail());
        }
        return statusAq;
    }

    public String registerGuide(CommonsMultipartFile guideData) {
        if (!FilenameUtils.getExtension(guideData.getOriginalFilename()).equalsIgnoreCase("csv")) {
            return "Invalid Extension";
        }
        try {
            CSVParser parser = new CSVParser(new InputStreamReader(guideData.getInputStream()), CSVFormat.DEFAULT);

            Set<String> guideUserSet = new HashSet<>(guideDao.getAllGuideUsernames());

            Guide g = new Guide();

            g.setProjectLoadPrimary(0);
            g.setProjectLoadSecondary(0);
            g.setLevel("guide");
            g.setActivated(0);
            g.setFlag(1);
            g.setIsExaminer(0);
            g.setIsTempExaminer(0);

            for (CSVRecord record : parser) {
                if (guideUserSet.contains(record.get(0))) {
                    continue;
                }
                if (record.size() != 3) {
                    return "Invalid Contents";
                }
                g.setName(record.get(2));
                g.setUsername(record.get(0).toUpperCase());
                g.setEmail(record.get(1).toLowerCase());
                guideDao.addGuideDirect(g);
                LinkGen.sendActivationLinkGuide(g.getName(), g.getUsername(), g.getEmail());
                guideUserSet.add(g.getUsername());
            }
            parser.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteGuide(String username) {
        guideDao.deleteGuide(username);
    }

    public List<Admin> getAllAdmins() {
        return adminDao.getAllAdmins();
    }

    public String registerAdmin(String user, String email, String name) {
        Admin a = new Admin();
        a.setName(name);
        a.setLevel("admin");
        a.setUsername(user);
        a.setEmail(email);
        a.setActivated(0);
        a.setFlag(0);

        String statusAq = adminDao.addAdmin(a);
        if (statusAq.equals("adda")) {
            LinkGen.sendActivationLinkGuide(a.getName(), a.getUsername(), a.getEmail());
        }
        return statusAq;
    }

    public void addStudentToProject(String sToAdd, Integer projectId) {
        projectDao.addStudentToProject(sToAdd, projectId);
    }

    public void setPrimaryGuide(String guideUsername, Integer projectId) {
        projectDao.setGuide(guideUsername, projectId, 1);
    }

    public void setSecondaryGuide(String guideUsername, Integer projectId) {
        projectDao.setGuide(guideUsername, projectId, 2);
    }

    public void removePrimaryGuide(Integer projectId) {
        projectDao.removeGuide(projectId, 1);
    }

    public void removeSecondaryGuide(Integer projectId) {
        projectDao.removeGuide(projectId, 2);
    }

    public void deleteProject(Integer projectId) {
        String keySynop = projectDao.deleteProject(projectId);
        s3Repository.deleteFile(keySynop);
    }

    public void approveProject(Integer projectId, Integer approve) {
        if (approve == 1) {
            Set<Student> allStudents = projectDao.approveProject(projectId);
            if (allStudents != null) {
                String body = "Dear #name, your project was approved by the administrator.<br/>Please Go and upload your confirmation letter from profile";
                String subject = "Project Approved by Admin";

                for (Student temp : allStudents) {
                    MessageSender.sendMessage(temp.getEmail(), subject, body.replace("#name", temp.getFirstname()));
                }
            }
        } else {
            Project p = projectDao.rejectProject(projectId);
            s3Repository.deleteFile(p.getSynopsis().getKey());
            Set<Student> allStudents = p.getStudents();
            if (allStudents != null) {
                String body = "Dear #name, your project was rejected by the administrator.<br/> please find another project definition and submit it.";
                String subject = "Project Rejected by Admin";

                for (Student temp : allStudents) {
                    MessageSender.sendMessage(temp.getEmail(), subject, body.replace("#name", temp.getFirstname()));
                }
            }
        }
    }

    public Templates getConfLetter(String student) {
        Files confLetter = studentDao.getConfLetter(student);

        Templates template = new Templates();
        template.setName(confLetter.getName());
        template.setFile(s3Repository.getFile(confLetter.getKey()));
        return template;
    }

    public void approveFinReq(String username, Integer finApprove) {
        if (finApprove == 0) {
            studentDao.rejectFinRequest(username);
            return;
        }
        studentDao.approveFinRequest(username);
    }

    public Templates getStudentInfoFile() {
        List<Student> allStudents = studentDao.getAllStudentsWithProject();

        if (allStudents.size() > 0) {

            StringBuilder data = new StringBuilder();
            data.append(DataWriterStudentInfo.getHeader(projectDao.getUniqueProjectIdCount()));
            int counter = 1;
            for (Student tempStudent : allStudents) {
                data.append(counter).append("%").append(DataWriterStudentInfo.getContent(tempStudent));
                counter++;
            }

            Templates templates = new Templates();
            templates.setName("StudentData.xlsx");
            templates.setFile(DataWriterStudentInfo.csv2excel(data.toString(), "%"));
            return templates;
        } else {
            return null;
        }
    }

    public Project getAllProjectReport(Integer id) {
        return projectDao.getDetailsForReport(id);
    }

    public List<Guide> getAllGuidesAlongTemp() {
        return guideDao.getAllGuidesAlongTemp();
    }

    public void allowEditing(String roll) {
        studentDao.allowEditing(roll);
    }

    public List<String> getProjectIds() {
        return projectDao.getUniqueProjectIds();
    }


    public void announce(String subject, String body) {
        List<Student> students = studentDao.getAllStudents();

        for (Student temp : students) {
            MessageSender.sendMessageText(temp.getEmail(), subject, body);
        }
    }

    public void allowSubmission(String roll) {
        studentDao.allowSubmission(roll);
    }

    public List<Templates> getAllTemplates() {
        return filesDao.getallTemplates();
    }

    public Templates getTemplate(Integer id) {
        Templates t = filesDao.getTemplate(id);

        Templates file = new Templates();
        file.setName(t.getName());
        file.setFile(t.getFile());
        return file;
    }

    public void updateTemplate(Integer id, CommonsMultipartFile file) {
        Templates t = new Templates();

        t.setFile(file.getBytes());
        filesDao.updateTemplate(id, t);
    }

    public void updateProfile(int id, String email, String name, String phone) {
        adminDao.updateProfile(id, email, name, phone);
    }

    public void sendMailGrp(String receivers, String subject, String body) {
        String[] recArr = receivers.split(";");
        for (String temp : recArr) {
            MessageSender.sendMessageText(temp, subject, body);
        }
    }

    public Templates getReport(Integer evalId) {
        Files report = evalDao.getReport(evalId);

        Templates template = new Templates();
        template.setName(report.getName());
        template.setFile(s3Repository.getFile(report.getKey()));
        return template;
    }

    public Templates getPpt(Integer presentationId) {
        Files ppt = evalDao.getPpt(presentationId);

        Templates template = new Templates();
        template.setName(ppt.getName());
        template.setFile(s3Repository.getFile(ppt.getKey()));
        return template;
    }

    public String addExaminer(String username, String password, String name, String email, String phone) {
        Guide guide = new Guide();
        guide.setIsExaminer(1);
        guide.setIsTempExaminer(1);
        guide.setActivated(1);
        guide.setEmail(email);
        guide.setUsername(username);

        String salt = BCrypt.gensalt();
        guide.setSalt(salt);
        guide.setPassword(BCrypt.hashpw(password, salt));
        guide.setPhone(phone);
        guide.setName(name);
        guide.setLevel("guide");
        guide.setFlag(1);

        return guideDao.addGuide(guide);
    }

    public List<List<Guide>> getExaminersSep() {
        List<Guide> guides = guideDao.getAllGuidesAlongTemp();

        List<Guide> igExaminers = new ArrayList<>();
        List<Guide> temp = new ArrayList<>();

        for (Guide g : guides) {
            if (g.getIsExaminer() == 1) {
                if (g.getIsTempExaminer() == 1) {
                    temp.add(g);
                } else {
                    igExaminers.add(g);
                }
            }
        }

        List<List<Guide>> guideEx = new ArrayList<>();
        guideEx.add(igExaminers);
        guideEx.add(temp);

        return guideEx;
    }

    public List<String> getAllVenues() {
        Templates file = filesDao.getVenueFile();
        List<String> venues = new ArrayList<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(file.getFile())));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                String s = line.trim();
                venues.add(s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return venues;
    }

}
