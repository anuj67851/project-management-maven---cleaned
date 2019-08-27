<%@ page import="com.pms.utility.printAlert" %>
<%@include file="templates/header.jsp" %>
<%@include file="templates/top-side-nav.jsp" %>

<div id="page-wrapper">
    <div class="container-fluid">
        <div class="col-md-12">
            <div class="row">
                <h2 class="page-header" style="display: inline">
                    <br>Admins
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
        <c:forEach var="tempAdmin" items="${allAdmins}">
        {
            user: "${tempAdmin.username}",
            name: "${tempAdmin.name}",
            email: "${tempAdmin.email}",
            phone: "${tempAdmin.phone}"
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
                {"data": "phone"}
            ]
        });
    });

</script>
<%
    String message = (String) request.getAttribute("message");
    if (message != null && message.equals("clasha")) {
        out.print(printAlert.getAlertBox("User Already Exists",
                "The Username you entered already exists in database, Please check Again"));
    } else if (message != null && message.equals("adda")) {
        out.print(printAlert.getAlertBox("Added Successfully",
                "Admin registered successfully."));
    }
%>