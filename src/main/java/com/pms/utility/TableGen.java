package com.pms.utility;

import com.pms.entity.*;

import java.text.SimpleDateFormat;
import java.util.List;

public class TableGen {


    public static String genTable(String id) {

        return " <div class=\"tab-pane fade\" id=\"" + id + "-pills" + "\">\n" +
                "                    <div class=\"panel panel-default\">\n" +
                "                        <!-- /.panel-heading -->\n" +
                "                        <div class=\"panel-body\">\n" +
                "                            <table width=\"100%\" class=\"table table-striped table-bordered table-hover\" id=\"" + id + "-table" + "\">\n" +
                "                                <thead>\n" +
                "                                <tr>\n" +
                "                                    <th>Roll</th>\n" +
                "                                    <th>Full Name</th>\n" +
                "                                    <th>Email</th>\n" +
                "                                    <th>Phone No</th>\n" +
                "                                    <th>Student Info.</th>\n" +
                "                                </tr>\n" +
                "                                </thead>\n" +
                "                                <tbody>\n" +
                "\n" +
                "                                </tbody>\n" +
                "                            </table>\n" +
                "                            <!-- /.table-responsive -->\n" +
                "                        </div>\n" +
                "                        <!-- /.panel-body -->\n" +
                "                    </div>\n" +
                "\n" +
                "                </div>";
    }


    public static String genTableData(List<Student> allStudents, int status, String tableId) {
        StringBuilder ans = new StringBuilder();
        ans.append("var ").append(tableId).append("Data = [");

        for (Student tempStudent : allStudents) {

            if (tempStudent.getStatus() == status) {
                ans.append("{");

                ans.append("roll:'").append(tempStudent.getUsername()).append("',");
                ans.append("email:'").append(tempStudent.getEmail()).append("',");
                ans.append("name:'").append(tempStudent.getFirstname()).append(" ").append(tempStudent.getLastname()).append("',");
                ans.append("phone:'");
                if (tempStudent.getPhone() != null) {
                    ans.append(tempStudent.getPhone());
                }
                ans.append("',");

                ans.append("details: '<a class=\"btn btn-primary btn-outline btn-info\" href=\"student?roll=").append(tempStudent.getUsername()).append("\">Details</a>'");
                ans.append("},");
            }
        }

        ans.append("];");


        ans.append("$(document).ready(function () {");
        ans.append("var ").append(tableId).append("Table = $('#").append(tableId).append("-table').DataTable({");
        ans.append(" 'data': ").append(tableId).append("Data").append(",");
        ans.append("'columns': [");
        ans.append("{'data': 'roll'},{'data': 'name'},{'data': 'email'},{'data': 'phone'},{'data': 'details','orderable': false,'searchable': false}");

        ans.append("]});});");
        return ans.toString();
    }

    public static String genTableProjects(String id) {

        return "<div class=\"tab-pane fade\" id=\"" + id + "-pills\">\n" +
                "                    <div class=\"panel panel-default\">\n" +
                "                        <div class=\"panel-body\">\n" +
                "                            <table width=\"100%\" id=\"" + id + "-table\" class=\"table table-striped table-bordered table-hover\">\n" +
                "                                <thead>\n" +
                "                                <tr>\n" +
                "                                    <th>ProjectId</th>\n" +
                "                                    <th>Title</th>\n" +
                "                                    <th>Company</th>\n" +
                "                                    <th>Guide-1</th>\n" +
                "                                    <th>Guide-2</th>\n" +
                "                                    <th>Project Info.</th>\n" +
                "                                </tr>\n" +
                "                                </thead>\n" +
                "                                <tbody>\n" +
                "\n" +
                "                                </tbody>\n" +
                "                            </table>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>";
    }

    public static String genTableDataProject(List<Project> allProjects, int status, String tableId) {
        StringBuilder ans = new StringBuilder();
        ans.append("var ").append(tableId).append("Data = [");

        for (Project tempProject : allProjects) {

            if (tempProject.getApprovalStatus() == status) {
                if (tableId.equals("a") && !tempProject.getStartingDate().after(new java.util.Date())) {
                    continue;
                }
                if (tableId.equals("aog") && !tempProject.getStartingDate().before(new java.util.Date())) {
                    continue;
                }
                ans.append("{");

                ans.append("prjId:'");
                if (tempProject.getProjectId() != null) {
                    ans.append(tempProject.getProjectId());
                }
                ans.append("',");
                ans.append("title:'").append(tempProject.getTitle()).append("',");
                ans.append("comp:'").append(tempProject.getCompany().getName()).append("',");

                ans.append("guide1:'");
                if (tempProject.getPrimaryGuide() != null) {
                    ans.append(tempProject.getPrimaryGuide().getName());
                }
                ans.append("',");

                ans.append("guide2:'");
                if (tempProject.getSecondaryGuide() != null) {
                    ans.append(tempProject.getSecondaryGuide().getName());
                }
                ans.append("',");
                ans.append("details: '<a class=\"btn btn-primary btn-outline btn-info\" href=\"project?prj=").append(tempProject.getId()).append("\">Details</a>'");
                ans.append("},");
            }
        }

        ans.append("];");


        ans.append("$(document).ready(function () {");
        ans.append("var ").append(tableId).append("Table = $('#").append(tableId).append("-table').DataTable({");
        ans.append(" 'data': ").append(tableId).append("Data").append(",");
        ans.append("'columns': [");
        ans.append("{'data': 'prjId'},{'data': 'title'},{'data': 'comp'},{'data': 'guide1'},{'data': 'guide2'},{'data': 'details','orderable': false,'searchable': false}");

        ans.append("]});});");
        return ans.toString();
    }

    private static String getTd(int number) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < number; i++) {
            sb.append("<td></td>");
        }
        return sb.toString();
    }

    private static String getEvalReviewRows(Review review) {

        return "<td>" + review.getTs() + "</td>" +
                "<td>" + review.getD() + "</td>" +
                "<td>" + review.getT() + "</td>" +
                "<td>" + review.getM() + "</td>" +
                "<td>" + (review.getTs() + review.getM() + review.getD() + review.getT()) + "</td>";
    }

    public static String getStudentCol(List<Student> students, int index) {
        StringBuilder sb = new StringBuilder();
        for (int i = index; i < index + 3; i++) {
            sb.append("<td width='189' colspan='5'>");
            if (students.size() > i) {
                sb.append("<strong>").append(students.get(i).getUsername()).append("</strong>");
            }
            sb.append("</td>");
        }
        return sb.toString();
    }

    public static String getEvaluationDataThreeStudents(List<Student> students, int index) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy");
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < 25; k++) {
            sb.append("<tr>");
            sb.append("<td>").append(k + 1).append("</td>");
            sb.append("<td>");
            if (students.get(index).getEvaluation().size() > k) {
                sb.append(format.format(students.get(index).getEvaluation().get(k).getSubdate()));
            }
            sb.append("</td>");
            for (int i = index; i < index + 3; i++) {
                if (students.size() > i) {
                    List<Evaluation> evaluations = students.get(i).getEvaluation();
                    if (evaluations.size() <= k) {
                        sb.append(getTd(5));
                    } else {
                        sb.append(getEvalReviewRows(evaluations.get(k).getRatings()));
                    }
                } else {
                    sb.append(getTd(5));
                }
            }
            sb.append("<tr>");
        }
        return sb.toString();
    }

    public static String getPresentationData(List<Student> studentsAll, int i) {
        StringBuilder sb = new StringBuilder();

        for (int j = i; j < i + 3; j++) {
            sb.append("<tr>");

            if (studentsAll.size() > j) {
                sb.append("<td height='20px'>").append(studentsAll.get(j).getUsername()).append("</td>");
                List<Presentation> presentations = studentsAll.get(j).getPresentation();

                for (int k = 0; k < 3; k++) {
                    if (presentations.size() > k) {
                        sb.append(getEvalReviewRows(presentations.get(k).getRatings()));
                    } else {
                        sb.append(getTd(5));
                    }
                }
            } else {
                sb.append("<td height='20px'></td>");
                sb.append(getTd(15));
            }

            sb.append("</tr>");
        }

        sb.append("<tr><td width=\"96\" height=\"120\">Comments Given :</td>");
        for (int l = 0; l < 3; l++) {
            if (l == 1) {
                sb.append("<td width=\"198\" colspan=\"5\">");
            } else if (l == 2) {
                sb.append("<td width=\"212\" colspan=\"5\">");
            } else {
                sb.append("<td width=\"200\" colspan=\"5\">");
            }

            for (int m = i; m < 3 + i; m++) {
                if (studentsAll.size() > m && studentsAll.get(m).getPresentation().size() > l) {
                    Presentation p = studentsAll.get(m).getPresentation().get(l);
                    if (p != null && p.getRatings().getComment() != null && !p.getRatings().getComment().trim().equals("")) {
                        sb.append("(").append(p.getStudent().getUsername()).append(")");
                        sb.append(p.getRatings().getComment());
                        sb.append("<br/>");
                    }
                }
            }

            sb.append("</td>");
        }

        sb.append("</tr>");

        return sb.toString();
    }
}