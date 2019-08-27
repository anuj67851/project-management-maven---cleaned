package com.pms.controller;

import com.pms.entity.Project;
import com.pms.entity.Student;
import com.pms.entity.Templates;
import com.pms.entity.User;
import com.pms.service.StudentService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping("/index")
    public String getIndexPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Student student = studentService.getStudent(user.getId());
        student.setPassword(null);
        student.setSalt(null);
        session.setAttribute("user", student);
        Project project = student.getProject();

        if (project == null) {
            List<String> studentRolls = studentService.getRollsActivatedAndFree(student.getId());
            model.addAttribute("allStudents", studentRolls);

            if (student.getStatus() == 1) {
                model.addAttribute("message", "Project Not Approved");
                studentService.setStudentStatus(student.getId(), 0);
            }

            return "student/submitProject";
        } else if (project.getApprovalStatus() == 0 && student.getStatus() == 1) {
            return "student/approvalPending";
        } else if (student.getStatus() == 2 && project.getApprovalStatus() == 1) {
            return "student/index";
        } else if (student.getStatus() == 3 && project.getApprovalStatus() == 1) {
            return "student/finishPendingIndividual";
        } else if (student.getStatus() == 4 && project.getApprovalStatus() == 1) {
            return "student/finishIndividual";
        } else if (student.getStatus() == 4 && project.getApprovalStatus() == 2) {
            return "student/finishProjectTotal";
        }
        return "login";
    }

    @RequestMapping("/profile")
    public String getProfile(Model model, HttpSession session) {
        Student student = (Student) session.getAttribute("user");
        model.addAttribute("theUser", student);
        if (student.getProject() == null || student.getProject().getApprovalStatus() == 0 || student.getProject().getApprovalStatus() == 1) {
            return "student/presubmitProfile";
        } else {
            return "student/profile";
        }
    }

    @RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
    public String updateProfile(HttpSession session, @RequestParam("fname") String fname, @RequestParam("lname") String lname, @RequestParam("mname") String mname,
                                @RequestParam("email") String email, @RequestParam("phone") String phone) {
        Student student = (Student) session.getAttribute("user");
        student = studentService.updateProfile(student, fname, mname, lname, email, phone);
        session.setAttribute("user", student);
        return "redirect:profile";
    }

    @RequestMapping(value = "/submitProject", method = RequestMethod.POST)
    public String submitProject(HttpServletRequest request, Model model, HttpSession session, @RequestParam("title") String title, @RequestParam("tools") String tools, @RequestParam("technologies") String technologies, @RequestParam("sdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date sdate,
                                @RequestParam("synopsis") CommonsMultipartFile synopsis, @RequestParam("cmpname") String cmpname, @RequestParam("cmpcontact") String cmpcontact,
                                @RequestParam("cmpextname") String cmpextname, @RequestParam("cmpextemail") String cmpextemail, @RequestParam("cmpaddress") String cmpaddress,
                                @RequestParam("cmpextcontact") String cmpextcontact, @RequestParam("cmphrname") String cmphrname, @RequestParam("cmphrcontact") String hrcontact) {

        Student student = (Student) session.getAttribute("user");
        boolean alreadySubmitFlag = studentService.checkProject(student.getId());
        if (alreadySubmitFlag) {
            String extension = FilenameUtils.getExtension(synopsis.getOriginalFilename());
            boolean ext = (extension.equalsIgnoreCase("pdf") || extension.equalsIgnoreCase("docx") || extension.equalsIgnoreCase("doc")) && (synopsis.getSize() < 8388608) && (synopsis.getOriginalFilename().length() <= 50);
            String gm1 = request.getParameter("gm1");
            String gm2 = request.getParameter("gm2");
            String gm3 = request.getParameter("gm3");

            boolean check = studentService.checkProject(gm1, gm2, gm3);
            if (ext && check) {
                studentService.submitProject(student.getId(), title, tools, technologies, sdate, synopsis, cmpname, cmpcontact, cmpextname, cmpextemail, cmpaddress, cmpextcontact, cmphrname, hrcontact, gm1, gm2, gm3);
                return "student/approvalPending";
            } else {
                model.addAttribute("title", title);
                model.addAttribute("tools", tools);
                model.addAttribute("technologies", technologies);
                model.addAttribute("cmpname", cmpname);
                model.addAttribute("cmpcontact", cmpcontact);
                model.addAttribute("cmpextname", cmpextname);
                model.addAttribute("cmpextemail", cmpextemail);
                model.addAttribute("cmpextcontact", cmpextcontact);
                model.addAttribute("comhrname", cmphrname);
                model.addAttribute("cmphrcontact", hrcontact);
                model.addAttribute("cmpaddress", cmpaddress);
                model.addAttribute("cmphrname", cmphrname);
                List<String> studentRolls = studentService.getRollsActivatedAndFree(student.getId());
                model.addAttribute("allStudents", studentRolls);

                if (ext && !check) {
                    model.addAttribute("message", "Occupied");
                } else if (!ext && check) {
                    model.addAttribute("message", "Extension");
                } else if (!ext && !check) {
                    model.addAttribute("message", "Extension and Occupied");
                }

                return "student/submitProject";
            }
        } else {
            session.setAttribute("user", studentService.getStudent(student.getId()));
            model.addAttribute("message", "already submitted");
            return "student/approvalPending";
        }
    }


    @RequestMapping("/files/synopsis")
    public String downloadSynopsis(HttpServletResponse response) {

        Templates doc = studentService.getSynopsis();
        try {
            Blob blob = new SerialBlob(doc.getFile());
            response.setHeader("Content-Disposition", "attachment;filename=\"" + doc.getName() + "\"");
            OutputStream out = response.getOutputStream();
            IOUtils.copy(blob.getBinaryStream(), out);
            out.flush();
            out.close();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
