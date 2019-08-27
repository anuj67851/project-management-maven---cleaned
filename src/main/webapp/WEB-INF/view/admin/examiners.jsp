<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="templates/header.jsp" %>
<%@include file="templates/top-side-nav.jsp" %>

<div id="page-wrapper">
    <div class="container-fluid">
        <div class="col-md-12">
            <div class="row">
                <h2 class="page-header" style="display: inline">
                    <br>Examiners
                </h2>
            </div>
            <br>
            <ul class="nav nav-pills">
                <li class="active"><a href="#fe-pills" data-toggle="tab">Faculty Examiners</a>
                </li>
                <li><a href="#ee-pills" data-toggle="tab">External Examiners</a>
                </li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane fade in active" id="fe-pills">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <table id="fe-table" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>Username</th>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Phone No.</th>
                                    <th>Exams</th>
                                </tr>
                                </thead>
                                <tbody>

                                </tbody>

                            </table>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" id="ee-pills">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <table id="ee-table" class="table table-striped table-bordered table-hover" width="100%">
                                <thead>
                                <tr>
                                    <th>Username</th>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Phone No.</th>
                                    <th>Exams</th>
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
        </div>
        <!-- /.row -->
    </div>
    <!-- /#wrapper -->
</div>

<%@include file="templates/footer.jsp" %>
<script>
    var feData = [
        <c:forEach var="tempExaminer" items="${igExaminers}">
        {
            code: "${tempExaminer.username}",
            email: "${tempExaminer.email}",
            name: "${tempExaminer.name}",
            phone: "${tempExaminer.phone}",
            exams: '<a class="btn btn-primary btn-outline btn-info" href="exams?examiner=${tempExaminer.username}">Details</a>'
        },
        </c:forEach>
    ];

    $(document).ready(function () {
        var allTable = $('#fe-table').DataTable({
            "data": feData,
            "columns": [
                {"data": "code"},
                {"data": "name"},
                {"data": "email"},
                {"data": "phone"},
                {
                    "data": "exams",
                    "orderable": false,
                    "searchable": false
                }
            ]
        });
    });

</script>

<script>
    var eeData = [
        <c:forEach var="tempExaminer" items="${tempExaminers}">
        {
            code: "${tempExaminer.username}",
            email: "${tempExaminer.email}",
            name: "${tempExaminer.name}",
            phone: "${tempExaminer.phone}",
            exams: '<a class="btn btn-primary btn-outline btn-info" href="exams?examiner=${tempExaminer.username}">Details</a>',
            del: '<form action="deleteExaminer" method="post"><input name="examiner" type="hidden" value="${tempExaminer.username}"/> <input type="submit" value="Delete" class="btn btn-primary btn-outline btn-danger"/></form>'
        },
        </c:forEach>
    ];

    $(document).ready(function () {
        var eeTable = $('#ee-table').DataTable({
            "data": eeData,
            "columns": [
                {"data": "code"},
                {"data": "name"},
                {"data": "email"},
                {"data": "phone"},
                {
                    "data": "exams",
                    "orderable": false,
                    "searchable": false
                },
                {
                    "data": "del",
                    "orderable": false,
                    "searchable": false
                }
            ]
        });
    });

</script>