<%@ page import="com.pms.entity.Project" %>
<%@ page import="com.pms.utility.YearChecker" %>
<%@ page import="com.pms.entity.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.pms.utility.TableGen" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%Project p = (Project) pageContext.findAttribute("theProject");%>
<%List<Student> studentsAll = new ArrayList<>(p.getStudents());%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${theProject.projectId}</title>
    <style>
        body {
            background-color: rgba(132, 132, 132, 0.58);
        }

        .page {
            background: white;
            display: block;
            box-shadow: 0 0 0.5cm rgba(0, 0, 0, 0.5);
            margin: 1cm auto 1cm auto;
            padding: 2cm;
            width: 21cm;
            min-height: 29.7cm;
        }

        @media print {
            @page {
                margin: -2cm 0 -10cm -0.5cm;
                size: A4 portrait;
            }

            #pdf-button {
                display: none;
            }

            .page {
                page-break-after: always;
            }


        }
    </style>
    <script>
        function downloadPDF() {
            window.print();
        }
    </script>
</head>
<body>
<div class="page">
    <button style="position:absolute;margin-top: -5%;margin-left: 50.9%" id="pdf-button" type="button"
            onclick="downloadPDF()">Save As PDF
    </button>
    <h3 align="center">
        <strong>Faculty of Technology and Engineering, CHARUSAT<br/>
            Chandubhai S. Patel Institute of Technology<br/>
            U &amp; P U. Patel Department of Computer Engineering<br/>
            CE416 Software Project Major<br/>
        </strong>
    </h3>
    <table width="100%" style="text-decoration: underline; font-weight: bold">
        <tr>
            <td style="text-align: left">
                Continuous Evaluation Sheet
            </td>
            <td style="text-align: right">
                A.Y. <%=YearChecker.getAyProject(p.getStartingDate())%>
            </td>
        </tr>
    </table>
    <p>
        Project ID :
        <c:choose>
            <c:when test="${theProject.projectId != null}">
                ${theProject.projectId}
            </c:when>
            <c:otherwise>
                ____________
            </c:otherwise>
        </c:choose>
    </p>
    <p>
        Project Title: ${theProject.title}

    </p>
    <p>
        Company Name: ${theProject.company.name}
    </p>
    <p>
        Internal Guide-1:<c:choose>
        <c:when test="${theProject.primaryGuide != null}">
            ${theProject.primaryGuide.name}
        </c:when>
        <c:otherwise>
            ____________
        </c:otherwise>
    </c:choose>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        Internal Guide-2:<c:choose>
        <c:when test="${theProject.secondaryGuide != null}">
            ${theProject.secondaryGuide.name}
        </c:when>
        <c:otherwise>
            ____________
        </c:otherwise>
    </c:choose>
    </p>
    <table border="1" cellspacing="0" cellpadding="0" width="100%" style="text-align: center;">
        <thead>
        <tr>
            <td width="135" colspan="2">
                <strong>Student ID </strong>
            </td>
            <%=TableGen.getStudentCol(studentsAll, 0)%>
        </tr>
        <tr>
            <td width="61">
                Week
            </td>
            <td width="74">
                Date
            </td>
            <td width="39">
                TS
            </td>
            <td width="31">
                D
            </td>
            <td width="29">
                T
            </td>
            <td width="34">
                M
            </td>
            <td width="57">
                Total
            </td>
            <td width="39">
                TS
            </td>
            <td width="31">
                D
            </td>
            <td width="29">
                T
            </td>
            <td width="34">
                M
            </td>
            <td width="57">
                Total
            </td>
            <td width="39">
                TS
            </td>
            <td width="31">
                D
            </td>
            <td width="29">
                T
            </td>
            <td width="34">
                M
            </td>
            <td width="57">
                Total
            </td>
        </tr>
        </thead>
        <tbody>

        <tr>
            <%=TableGen.getEvaluationDataThreeStudents(studentsAll, 0)%>
        </tr>
        </tbody>
    </table>

    <p style="margin-bottom: 0.11in"><br/><br/>
    </p>

    <table border="1" cellspacing="0" cellpadding="0" width="100%" style="text-align: center; ">
        <thead>
        <tr>
            <td width="96">
            </td>
            <td width="198" colspan="5">
                <strong>Presentation-1</strong>
            </td>
            <td width="212" colspan="5">
                <strong>Presentation-2</strong>
            </td>
            <td width="201" colspan="6">
                <strong>Presentation-3</strong>
            </td>
        </tr>
        <tr>
            <td width="96">
                <strong>Students ID</strong>
            </td>
            <td width="36">
                TS
            </td>
            <td width="36">
                D
            </td>
            <td width="30">
                T
            </td>
            <td width="30">
                M
            </td>
            <td width="66">
                Total
            </td>
            <td width="36">
                TS
            </td>
            <td width="36">
                D
            </td>
            <td width="30">
                T
            </td>
            <td width="30">
                M
            </td>
            <td width="66">
                Total
            </td>
            <td width="36">
                TS
            </td>
            <td width="36">
                D
            </td>
            <td width="30">
                T
            </td>
            <td width="30">
                M
            </td>
            <td width="66">
                Total
            </td>
        </tr>
        </thead>
        <tbody>
        <%=TableGen.getPresentationData(studentsAll, 0)%>
        </tbody>
    </table>
    <p>
        Note: (Each Component 5 Marks, Total 20 Marks)
    <p style="font-size: 12px">
        TS: Time Sheet Submission &amp; Justification<br/>
        D: Documentation about their discussion, planning, test,
        errors, checklist, weekly report etc..<br/>
        T: Technology Improvement<br/>
        M: Milestone Achievement
    </p>
    </p>
</div>

<%
    if (studentsAll.size() > 3) {
        for (int index = 3; index < studentsAll.size(); index += 3) {
%>

<div class="page">
    <h3 align="center">
        <strong>Faculty of Technology and Engineering, CHARUSAT<br/>
            Chandubhai S. Patel Institute of Technology<br/>
            U &amp; P U. Patel Department of Computer Engineering<br/>
            CE416 Software Project Major<br/>
        </strong>
    </h3>
    <table width="100%" style="text-decoration: underline; font-weight: bold">
        <tr>
            <td style="text-align: left">
                Continuous Evaluation Sheet
            </td>
            <td style="text-align: right">
                A.Y. <%=YearChecker.getAyProject(p.getStartingDate())%>
            </td>
        </tr>
    </table>
    <p>
        Project ID :
        <c:choose>
            <c:when test="${theProject.projectId != null}">
                ${theProject.projectId}
            </c:when>
            <c:otherwise>
                ____________
            </c:otherwise>
        </c:choose>
    </p>
    <p>
        Project Title: ${theProject.title}

    </p>
    <p>
        Company Name: ${theProject.company.name}
    </p>
    <p>
        Internal Guide-1:<c:choose>
        <c:when test="${theProject.primaryGuide != null}">
            ${theProject.primaryGuide.name}
        </c:when>
        <c:otherwise>
            ____________
        </c:otherwise>
    </c:choose>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        Internal Guide-2:<c:choose>
        <c:when test="${theProject.secondaryGuide != null}">
            ${theProject.secondaryGuide.name}
        </c:when>
        <c:otherwise>
            ____________
        </c:otherwise>
    </c:choose>
    </p>
    <table border="1" cellspacing="0" cellpadding="0" width="100%" style="text-align: center;">
        <thead>
        <tr>
            <td width="135" colspan="2">
                <strong>Student ID </strong>
            </td>
            <%=TableGen.getStudentCol(studentsAll, index)%>
        </tr>
        <tr>
            <td width="61">
                Week
            </td>
            <td width="74">
                Date
            </td>
            <td width="39">
                TS
            </td>
            <td width="31">
                D
            </td>
            <td width="29">
                T
            </td>
            <td width="34">
                M
            </td>
            <td width="57">
                Total
            </td>
            <td width="39">
                TS
            </td>
            <td width="31">
                D
            </td>
            <td width="29">
                T
            </td>
            <td width="34">
                M
            </td>
            <td width="57">
                Total
            </td>
            <td width="39">
                TS
            </td>
            <td width="31">
                D
            </td>
            <td width="29">
                T
            </td>
            <td width="34">
                M
            </td>
            <td width="57">
                Total
            </td>
        </tr>
        </thead>
        <tbody>

        <tr>
            <%=TableGen.getEvaluationDataThreeStudents(studentsAll, index)%>
        </tr>
        </tbody>
    </table>

    <p style="margin-bottom: 0.11in"><br/><br/>
    </p>

    <table border="1" cellspacing="0" cellpadding="0" width="100%" style="text-align: center; ">
        <thead>
        <tr>
            <td width="96">
            </td>
            <td width="198" colspan="5">
                <strong>Presentation-1</strong>
            </td>
            <td width="212" colspan="5">
                <strong>Presentation-2</strong>
            </td>
            <td width="201" colspan="6">
                <strong>Presentation-3</strong>
            </td>
        </tr>
        <tr>
            <td width="96">
                <strong>Students ID</strong>
            </td>
            <td width="36">
                TS
            </td>
            <td width="36">
                D
            </td>
            <td width="30">
                T
            </td>
            <td width="30">
                M
            </td>
            <td width="66">
                Total
            </td>
            <td width="36">
                TS
            </td>
            <td width="36">
                D
            </td>
            <td width="30">
                T
            </td>
            <td width="30">
                M
            </td>
            <td width="66">
                Total
            </td>
            <td width="36">
                TS
            </td>
            <td width="36">
                D
            </td>
            <td width="30">
                T
            </td>
            <td width="30">
                M
            </td>
            <td width="66">
                Total
            </td>
        </tr>
        </thead>
        <tbody>
        <%=TableGen.getPresentationData(studentsAll, index)%>
        </tbody>
    </table>
    <p>
        Note: (Each Component 5 Marks, Total 20 Marks)
    <p style="font-size: 12px">
        TS: Time Sheet Submission &amp; Justification<br/>
        D: Documentation about their discussion, planning, test,
        errors, checklist, weekly report etc..<br/>
        T: Technology Improvement<br/>
        M: Milestone Achievement
    </p>
    </p>
</div>

<%
        }
    }
%>
</body>
</html>
