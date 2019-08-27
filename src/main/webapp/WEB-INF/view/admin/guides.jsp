<%@ page import="com.pms.utility.printAlert" %>
<%@include file="templates/header.jsp" %>
<%@include file="templates/top-side-nav.jsp" %>

<div id="page-wrapper">
    <div class="container-fluid">
        <div class="col-md-12">
            <div class="row">
                <h2 class="page-header" style="display: inline">
                    <br>Guides
                </h2>
            </div>
            <br>
            <div class="panel panel-default">
                <div class="panel-body">
                    <table id="all-table" class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>Username</th>
                            <th>Full Name</th>
                            <th>Email</th>
                            <th>Phone No.</th>
                            <th>Projects</th>
                            <th>Primary Load</th>
                            <th>Secondary Load</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </div>
    <!-- /#wrapper -->
</div>

<%@include file="templates/footer.jsp" %>
<script>
    var allData = [
        <c:forEach var="tempGuide" items="${allGuides}">
        {
            user: "${tempGuide.username}",
            name: "${tempGuide.name}",
            email: "${tempGuide.email}",
            phone: "${tempGuide.phone}",
            project: '<a class="btn btn-primary btn-outline btn-info" href="guideProjects?guide=${tempGuide.username}">Projects</a>',
            countP: "${tempGuide.projectLoadPrimary}",
            countS: "${tempGuide.projectLoadSecondary}",
            del: '<form action="deleteGuide" method="post"><input name="user" type="hidden" value="${tempGuide.username}"/> <input type="submit" value="Delete" class="btn btn-primary btn-outline btn-danger"/></form>'
        },
        </c:forEach>
    ];

    $(document).ready(function () {
        var allTable = $('#all-table').DataTable({
            "data": allData,
            "columns": [
                {"data": "user"},
                {"data": "name"},
                {"data": "email"},
                {"data": "phone"},
                {"data": "project"},
                {"data": "countP"},
                {"data": "countS"},
                {
                    "data": "del",
                    "orderable": false,
                    "searchable": false
                }
            ]
        });
    });

</script>
<%
    String message = (String) request.getAttribute("message");
    if (message != null && message.equals("clashg")) {
        out.print(printAlert.getAlertBox("User Already Exists",
                "The Username you entered already exists in database, Please check Again"));
    } else if (message != null && message.equals("addg")) {
        out.print(printAlert.getAlertBox("Added Successfully",
                "Guide(s) registered successfully."));
    }
%>