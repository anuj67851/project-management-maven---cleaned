package com.pms.controller;

import com.pms.entity.*;
import com.pms.service.AdminService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping("/index")
    public String getIndexPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Admin admin = adminService.getAdmin(user.getId());
        admin.setPassword(null);
        admin.setSalt(null);
        session.setAttribute("user", admin);
        return "redirect:students";
    }

    @RequestMapping("/students")
    public String getAllStudents(Model model, @ModelAttribute("message") String message) {
        List<Student> allStudents = adminService.getAllStudents();
        model.addAttribute("allStudents", allStudents);
        model.addAttribute("message", message);
        return "admin/students";
    }

    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public String getStudentDetails(Model model, @RequestParam("roll") String roll) {

        model.addAttribute("theStudent", adminService.getStudentDetail(roll));
        return "admin/studentDetailsAll";
    }

    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public String getProjectDetails(Model model, @RequestParam("prj") Integer id) {
        Project p = adminService.getProjectDetails(id);
        model.addAttribute("theProject", p);

        if (p.getApprovalStatus() != 2) {
            model.addAttribute("allFreeStudents", adminService.getFreeStudents());
        }

        if (p.getPrimaryGuide() == null || p.getSecondaryGuide() == null) {
            model.addAttribute("allGuide", adminService.getAllGuides());
        }

        return "admin/projectDetailsAll";
    }

    @RequestMapping(value = "/deleteStudent", method = RequestMethod.POST)
    public String deleteStudent(@RequestParam("roll") String username) {
        adminService.deleteStudent(username);
        return "redirect:students";
    }

    @RequestMapping(value = "/removeFromProject", method = RequestMethod.POST)
    public String removeFromProject(RedirectAttributes redirectAttributes, @RequestParam("roll") String username) {
        adminService.removeStudentFromProj(username);
        redirectAttributes.addAttribute("roll", username);
        return "redirect:student";
    }


    @RequestMapping("/addStudentsPage")
    public String addSpage() {
        return "admin/addStudent";
    }

    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public String addStudent(RedirectAttributes redirectAttributes, @RequestParam("roll") String username, @RequestParam("email") String email, @RequestParam("fname") String fname, @RequestParam("lname") String lname) {
        String status = adminService.registerStudent(username.toUpperCase().trim(), email.toLowerCase().trim(), fname, lname);
        redirectAttributes.addAttribute("message", status);
        return "redirect:students";
    }

    @RequestMapping(value = "/addStudentCsv", method = RequestMethod.POST)
    public String addStudentCsv(Model model, RedirectAttributes redirectAttributes, @RequestParam("studentscsv") CommonsMultipartFile studentData) {
        String status = adminService.registerStudent(studentData);
        if (status == null) {
            redirectAttributes.addAttribute("message", "adds");
            return "redirect:students";
        } else {
            model.addAttribute("message", status);
            return "admin/addStudent";
        }
    }

    @RequestMapping(value = "/allowEditing", method = RequestMethod.POST)
    public String allowEditing(RedirectAttributes redirectAttributes, @RequestParam("roll") String roll) {
        adminService.allowEditing(roll);
        redirectAttributes.addAttribute("roll", roll);
        return "redirect:student";
    }

    @RequestMapping(value = "/allowSubmission", method = RequestMethod.POST)
    public String allowSubmission(RedirectAttributes redirectAttributes, @RequestParam("roll") String roll) {
        adminService.allowSubmission(roll);
        redirectAttributes.addAttribute("roll", roll);
        return "redirect:student";
    }

    @RequestMapping("/projects")
    public String getAllProjects(Model model) {
        List<Project> allProjects = adminService.getAllProjects();
        model.addAttribute("allProjects", allProjects);
        return "admin/projects";
    }

    @RequestMapping("/guides")
    public String getAllGuides(Model model, @ModelAttribute("message") String message) {
        List<Guide> allGuides = adminService.getAllGuides();
        model.addAttribute("allGuides", allGuides);
        model.addAttribute("message", message);
        return "admin/guides";
    }

    @RequestMapping("/guideProjects")
    public String projectsForGuide(Model model, @RequestParam("guide") String guide) {
        Guide guideClass = adminService.getGuideAll(guide);
        model.addAttribute("theGuide", guideClass);
        return "admin/guideprojects";
    }

    @RequestMapping("/addGuidePage")
    public String addGpage() {
        return "admin/addGuide";
    }

    @RequestMapping(value = "/addGuide", method = RequestMethod.POST)
    public String addGuide(RedirectAttributes redirectAttributes, @RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("name") String name) {
        String status = adminService.registerGuide(username.toUpperCase().trim(), email.toLowerCase().trim(), name);
        redirectAttributes.addAttribute("message", status);
        return "redirect:guides";
    }

    @RequestMapping(value = "/addGuideCsv", method = RequestMethod.POST)
    public String addGuideCsv(Model model, RedirectAttributes redirectAttributes, @RequestParam("guidescsv") CommonsMultipartFile guideData) {
        String status = adminService.registerGuide(guideData);
        if (status == null) {
            redirectAttributes.addAttribute("message", "addg");
            return "redirect:guides";
        } else {
            model.addAttribute("message", status);
            return "admin/addGuide";
        }
    }

    @RequestMapping(value = "/deleteGuide", method = RequestMethod.POST)
    public String deleteGuide(@RequestParam("user") String username) {
        adminService.deleteGuide(username);
        return "redirect:guides";
    }


    @RequestMapping("/admins")
    public String getAllAdmins(Model model, @ModelAttribute("message") String message) {
        List<Admin> allAdmins = adminService.getAllAdmins();
        model.addAttribute("allAdmins", allAdmins);
        model.addAttribute("message", message);
        return "admin/admins";
    }

    @RequestMapping("/addAdminPage")
    public String addApage() {
        return "admin/addAdmin";
    }

    @RequestMapping(value = "/addAdmin", method = RequestMethod.POST)
    public String addAdmin(RedirectAttributes redirectAttributes, @RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("name") String name) {
        String status = adminService.registerAdmin(username.toUpperCase().trim(), email.toLowerCase().trim(), name);
        redirectAttributes.addAttribute("message", status);
        return "redirect:admins";
    }

    @RequestMapping(value = "/addStudentToProject", method = RequestMethod.POST)
    public String addStoProject(RedirectAttributes redirectAttributes, @RequestParam("grpMember") String sToAdd, @RequestParam("projectId") Integer projectId) {
        adminService.addStudentToProject(sToAdd, projectId);

        redirectAttributes.addAttribute("prj", projectId);
        return "redirect:project";
    }

    @RequestMapping(value = "/addPrimaryGuide", method = RequestMethod.POST)
    public String addPrimaryGuide(RedirectAttributes redirectAttributes, @RequestParam("pguide") String guideUsername, @RequestParam("prjId") Integer projectId) {
        adminService.setPrimaryGuide(guideUsername, projectId);
        redirectAttributes.addAttribute("prj", projectId);
        return "redirect:project";
    }

    @RequestMapping(value = "/addSecondaryGuide", method = RequestMethod.POST)
    public String addSecondaryGuide(RedirectAttributes redirectAttributes, @RequestParam("sguide") String guideUsername, @RequestParam("prjId") Integer projectId) {
        adminService.setSecondaryGuide(guideUsername, projectId);
        redirectAttributes.addAttribute("prj", projectId);
        return "redirect:project";
    }

    @RequestMapping(value = "/removePrimaryGuide", method = RequestMethod.POST)
    public String removePrimaryGuide(RedirectAttributes redirectAttributes, @RequestParam("prjId") Integer projectId) {
        adminService.removePrimaryGuide(projectId);
        redirectAttributes.addAttribute("prj", projectId);
        return "redirect:project";
    }

    @RequestMapping(value = "/removeSecondaryGuide", method = RequestMethod.POST)
    public String removeSecondaryGuide(RedirectAttributes redirectAttributes, @RequestParam("prjId") Integer projectId) {
        adminService.removeSecondaryGuide(projectId);
        redirectAttributes.addAttribute("prj", projectId);
        return "redirect:project";
    }

    @RequestMapping(value = "/deleteProject", method = RequestMethod.POST)
    public String deleteProject(@RequestParam("prjId") Integer projectId) {
        adminService.deleteProject(projectId);

        return "redirect:projects";
    }

    @RequestMapping(value = "/approveProject", method = RequestMethod.POST)
    public String approveProject(RedirectAttributes redirectAttributes, @RequestParam("prjId") Integer projectId, @RequestParam("prjApprove") Integer approve) {
        adminService.approveProject(projectId, approve);
        if (approve == 1) {
            redirectAttributes.addAttribute("prj", projectId);
            return "redirect:project";
        }
        return "redirect:projects";
    }

    @RequestMapping(value = "/finishReq", method = RequestMethod.POST)
    public String finishRequest(RedirectAttributes redirectAttributes, @RequestParam("roll") String username, @RequestParam("finApprove") Integer finApprove) {
        adminService.approveFinReq(username, finApprove);
        redirectAttributes.addAttribute("roll", username);
        return "redirect:student";
    }


    @RequestMapping(value = "/genStudentInfo", method = RequestMethod.GET)
    public String generateStudentsInformation(HttpServletResponse response) {
        Templates studentsInfo = adminService.getStudentInfoFile();
        return getString(response, studentsInfo);
    }

    @RequestMapping(value = "/marksheet", method = RequestMethod.GET)
    public String generateStudentsInformation(Model model, @RequestParam("id") Integer id) {
        Project project = adminService.getAllProjectReport(id);
        if (project != null) {
            model.addAttribute("theProject", project);
            return "admin/projectFinalReport";
        }
        return "redirect:projects";
    }

    @RequestMapping(value = "/synopsis", method = RequestMethod.GET)
    public String downloadSynopsis(@RequestParam("id") Integer prjId, HttpServletResponse response) {

        Templates synopsis = adminService.getSynopsis(prjId);
        return getString(response, synopsis);
    }

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public String downloadReport(@RequestParam("id") Integer evalId, HttpServletResponse response) {
        Templates report = adminService.getReport(evalId);
        return getString(response, report);
    }

    @RequestMapping(value = "/ppt", method = RequestMethod.GET)
    public String downloadPpt(@RequestParam("id") Integer presentationId, HttpServletResponse response) {
        Templates ppt = adminService.getPpt(presentationId);
        return getString(response, ppt);
    }

    @RequestMapping(value = "/confLetter", method = RequestMethod.GET)
    public String downloadConfLetter(@RequestParam("roll") String student, HttpServletResponse response) {
        Templates confLetter = adminService.getConfLetter(student);
        return getString(response, confLetter);
    }

    private String getString(HttpServletResponse response, Templates report) {
        if (report != null) {
            try {
                Blob blob = new SerialBlob(report.getFile());
                response.setHeader("Content-Disposition", "attachment;filename=\"" + report.getName() + "\"");
                OutputStream out = response.getOutputStream();
                IOUtils.copy(blob.getBinaryStream(), out);
                out.flush();
                out.close();

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @RequestMapping("/announcement")
    public String getAnnouncementPage() {
        return "admin/announcement";
    }

    @RequestMapping(value = "/announce", method = RequestMethod.POST)
    public String announce(RedirectAttributes redirectAttributes, @RequestParam("subject") String subject, @RequestParam("body") String body) {
        adminService.announce(subject, body);
        redirectAttributes.addAttribute("message", "successful");
        return "redirect:students";
    }

    @RequestMapping("/templates")
    public String getTemplatesPage(Model model, @ModelAttribute("message") String message) {
        List<Templates> templates = adminService.getAllTemplates();
        model.addAttribute("allTemplates", templates);
        model.addAttribute("message", message);
        return "admin/files";
    }

    @RequestMapping("/template")
    public String getTemplate(@RequestParam("id") Integer id, HttpServletResponse response) {
        Templates template = adminService.getTemplate(id);
        return getString(response, template);
    }

    @RequestMapping(value = "/updateFile", method = RequestMethod.POST)
    public String updateTemplate(RedirectAttributes redirectAttributes, @RequestParam("fileId") Integer id, @RequestParam("file") CommonsMultipartFile file) {
        if (file.getSize() > 8388608) {
            redirectAttributes.addAttribute("message", "Size Invalid");
            return "redirect:templates";
        }

        adminService.updateTemplate(id, file);
        redirectAttributes.addAttribute("message", "Done");
        return "redirect:templates";
    }

    @RequestMapping("/profile")
    public String getProfile(HttpSession session, Model model) {
        Admin a = (Admin) session.getAttribute("user");

        model.addAttribute("theAdmin", a);
        return "admin/profile";
    }

    @RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
    public String updateProfile(HttpSession session, @RequestParam("email") String email, @RequestParam("name") String name, @RequestParam("phone") String phone) {
        Admin a = (Admin) session.getAttribute("user");

        adminService.updateProfile(a.getId(), email, name, phone);
        return "redirect:index";
    }

    @RequestMapping(value = "/groupMails", method = RequestMethod.POST)
    public String getGroupMailPage(Model model, HttpServletRequest request) {
        String[] receivers = request.getParameterValues("receivers");
        if (receivers != null) {
            model.addAttribute("receivers", String.join(";", receivers));
        }
        return "admin/sendGroupMail";
    }

    @RequestMapping(value = "/sendGrpMail", method = RequestMethod.POST)
    public String sendGrpMail(RedirectAttributes redirectAttributes, @RequestParam("receivers") String receivers, @RequestParam("subject") String subject, @RequestParam("body") String body) {
        adminService.sendMailGrp(receivers, subject, body);
        redirectAttributes.addAttribute("message", "successful");
        return "redirect:students";
    }

    @RequestMapping(value = "/addExaminerPage")
    public String getExaminerPage() {
        return "admin/addExaminer";
    }

    @RequestMapping(value = "/addExaminer", method = RequestMethod.POST)
    public String addExaminer(Model model, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("phone") String phone) {
        String ans = adminService.addExaminer(username, password, name, email, phone);
        if (ans.equals("clashg")) {
            model.addAttribute("message", "clashg");
            return "admin/addExaminer";
        }

        return "admin/examiners";
    }

    @RequestMapping(value = "/examiners")
    public String examiners(Model model) {
        List<List<Guide>> allExaminers = adminService.getExaminersSep();
        model.addAttribute("igExaminers", allExaminers.get(0));
        model.addAttribute("tempExaminers", allExaminers.get(1));
        return "admin/examiners";
    }

    @RequestMapping(value = "/deleteExaminer", method = RequestMethod.POST)
    public String deleteExaminer(@RequestParam("examiner") String examiner) {
        adminService.deleteGuide(examiner);
        return "redirect:examiners";
    }
}
