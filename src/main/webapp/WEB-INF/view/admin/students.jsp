<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.pms.entity.Student" %>
<%@ page import="com.pms.utility.TableGen" %>
<%@ page import="com.pms.utility.printAlert" %>
<%@ page import="java.util.List" %>
<%@include file="templates/header.jsp" %>
<%@include file="templates/top-side-nav.jsp" %>

<div id="page-wrapper">
    <div class="container-fluid">
        <div class="col-md-12">
            <div class="row">
                <h2 class="page-header" style="display: inline">
                    <br>Students
                </h2>
            </div>
            <br>
            <ul class="nav nav-pills">
                <li class="active"><a href="#all-pills" data-toggle="tab">All</a>
                </li>
                <li><a href="#na-pills" data-toggle="tab">Registration Pending</a>
                </li>
                <li><a href="#ap-pills" data-toggle="tab">Approval Pending</a>
                </li>
                <li><a href="#og-pills" data-toggle="tab">Approved</a>
                </li>
                <li><a href="#finpen-pills" data-toggle="tab">Requested Completion</a>
                </li>
                <li><a href="#fin-pills" data-toggle="tab">Completed</a>
                </li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane fade in active" id="all-pills">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <form action="groupMails" method="post">
                                <table width="100%">
                                    <tr>
                                        <td class="pull-left"><input type="submit" value="Send Mail to Selected"
                                                                     class="btn btn-primary btn-outline"/></td>
                                        <td class="pull-right" width="22.7%">Conf Letter:<input type="text"
                                                                                                id="conf_search"/></td>
                                    </tr>
                                </table>
                                <br/>
                                <table id="all-table" class="table table-striped table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" id="all-box" class="form-control"/></th>
                                        <th>Roll</th>
                                        <th>Full Name</th>
                                        <th>Email</th>
                                        <th>Phone No.</th>
                                        <th>Status</th>
                                        <th>Conf. Letter</th>
                                        <th>Student Info.</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    </tbody>

                                </table>
                            </form>
                        </div>
                    </div>
                </div>
                <%=TableGen.genTable("na")%>
                <%=TableGen.genTable("ap")%>
                <%=TableGen.genTable("og")%>
                <%=TableGen.genTable("finpen")%>
                <%=TableGen.genTable("fin")%>

            </div>
        </div>
        <!-- /.row -->
    </div>
    <!-- /#wrapper -->
</div>

<%@include file="templates/footer.jsp" %>
<script>
    var allData = [
        <c:forEach var="tempStudent" items="${allStudents}">
        {
            <% Student student = (Student) pageContext.findAttribute("tempStudent");%>
            chc: '<input type="checkbox" name="receivers" value="${tempStudent.email}" class="form-control"/>',
            roll: "${tempStudent.username}",
            email: "${tempStudent.email}",
            name: "${tempStudent.firstname} ${tempStudent.lastname}",
            phone: "${tempStudent.phone}",
            status: "<%

                int status = student.getStatus();
                if (status == 0){
                    out.print("Inactive");
                } else if (status == 1){
                    out.print("Approval Pending");
                } else if (status == 2){
                    out.print("Approved");
                } else if (status == 3){
                    out.print("Req. for Completion");
                } else if (status == 4){
                    out.print("Completed");
                }
            %>",
            conf: "<%
            if(student.getConfLetterStatus() == 0){
                out.print("N");
            } else {
                out.print("Y");
            }
            %>",
            details: '<a class="btn btn-primary btn-outline btn-info" href="student?roll=${tempStudent.username}">Details</a>'
        },
        </c:forEach>
    ];

    $(document).ready(function () {
        var allTable = $('#all-table').DataTable({
            "pagingType": "full_numbers",
            "lengthMenu": [
                [10, 25, 50, -1],
                [10, 25, 50, "All"]
            ],
            "data": allData,
            "columns": [
                {
                    "data": "chc",
                    "orderable": false,
                    "searchable": false
                },
                {"data": "roll"},
                {"data": "name"},
                {"data": "email"},
                {"data": "phone"},
                {"data": "status"},
                {"data": "conf"},
                {
                    "data": "details",
                    "orderable": false,
                    "searchable": false
                }
            ]
        });
        $('#conf_search').on('keyup', function () {
            allTable.columns(6)
                .search(this.value)
                .draw();
        });
    });
    $('#all-box').click(function () {
        if ($(this).is(':checked')) {
            $('input[name=receivers]').attr('checked', true);
        } else {
            $('input[name=receivers]').attr('checked', false);
        }
    });

</script>
<style>
    input[type=checkbox] {
        -ms-transform: scale(1.5); /* IE */
        -moz-transform: scale(1.5); /* FF */
        -webkit-transform: scale(1.5); /* Safari and Chrome */
        -o-transform: scale(1.5); /* Opera */
        padding: 10px;
    }
</style>

<%List<Student> allStudents = (List<Student>) pageContext.findAttribute("allStudents");%>
<script>
    <%=TableGen.genTableData(allStudents, 0, "na")%>
    <%=TableGen.genTableData(allStudents, 1, "ap")%>
    <%=TableGen.genTableData(allStudents, 2, "og")%>
    <%=TableGen.genTableData(allStudents, 3, "finpen")%>
    <%=TableGen.genTableData(allStudents, 4, "fin")%>
</script>

<%
    String message = (String) request.getAttribute("message");
    if (message != null && message.equals("clashs")) {
        out.print(printAlert.getAlertBox("Roll Already Exists",
                "The Roll Number you entered already exists in database, Please check Again"));
    } else if (message != null && message.equals("adds")) {
        out.print(printAlert.getAlertBox("Added Successfully",
                "Student(s) registered successfully."));
    } else if (message != null && message.equals("successful")) {
        out.print(printAlert.getAlertBox("Mails sent Successfully",
                "It may take some time to send all the mails , student wont receive mail if email is not correct."));
    }
%>