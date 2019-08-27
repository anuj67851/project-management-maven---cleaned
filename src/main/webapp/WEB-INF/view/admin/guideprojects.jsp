<%@ page import="com.pms.entity.Project" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@include file="templates/header.jsp" %>
<%@include file="templates/top-side-nav.jsp" %>

<div id="page-wrapper">
    <div class="container-fluid">
        <div class="col-md-12">
            <div class="row">
                <h4 class="page-header" style="display: inline">
                    <br>Projects for ${theGuide.name} (${theGuide.username})
                </h4>
            </div>
            <br>
            <ul class="nav nav-pills">
                <li class="active"><a href="#p-pills" data-toggle="tab">Primary Projects</a>
                </li>
                <li><a href="#s-pills" data-toggle="tab">Secondary Projects</a>
                </li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane fade in active" id="p-pills">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <table id="p-table" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>ProjectId</th>
                                    <th>Title</th>
                                    <th>Guide-1</th>
                                    <th>Guide-2</th>
                                    <th>Status</th>
                                    <th>Starting Date</th>
                                    <th>Project Info.</th>
                                </tr>
                                </thead>
                                <tbody>

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" id="s-pills">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <table width="100%" id="s-table" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>ProjectId</th>
                                    <th>Title</th>
                                    <th>Guide-1</th>
                                    <th>Guide-2</th>
                                    <th>Status</th>
                                    <th>Starting Date</th>
                                    <th>Project Info.</th>
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
    </div>
    <!-- /.row -->
</div>
<!-- /#wrapper -->
</div>
<%@include file="templates/footer.jsp" %>
<script>
    var pData = [
        <c:forEach var="tempProject" items="${theGuide.primaryProjects}">
        {
            <% Project project = (Project) pageContext.findAttribute("tempProject");%>
            prjId: "${tempProject.projectId}",
            title: "${tempProject.title}",
            guide1: "${tempProject.primaryGuide.name}",
            guide2: "${tempProject.secondaryGuide.name}",
            status: '<%

                int status = project.getApprovalStatus();
                if (status == 0){
                    out.print("Approval Pending");
                } else if (status == 1 && project.getStartingDate().after(new java.util.Date())){
                    out.print("Approved(Not Started)");
                } else if (status == 1 && project.getStartingDate().before(new java.util.Date())){
                    out.print("Approved(Ongoing)");
                } else if (status == 2){
                    out.print("Finished");
                }
            %>',
            sdate: "<%=new SimpleDateFormat("MMM dd, yyyy").format(project.getStartingDate())%>",
            details: '<a class="btn btn-primary btn-outline btn-info" href="project?prj=${tempProject.id}">Details</a>'
        },
        </c:forEach>
    ];

    $(document).ready(function () {
        var pTable = $('#p-table').DataTable({
            "data": pData,
            "columns": [
                {"data": "prjId"},
                {"data": "title"},
                {"data": "guide1"},
                {"data": "guide2"},
                {"data": "status"},
                {"data": "sdate"},
                {
                    "data": "details",
                    "orderable": false,
                    "searchable": false
                }
            ]
        });
    });

</script>

<script>
    var sData = [
        <c:forEach var="tempProject" items="${theGuide.secondaryProjects}">
        {
            <% Project project = (Project) pageContext.findAttribute("tempProject");%>
            prjId: "${tempProject.projectId}",
            title: "${tempProject.title}",
            guide1: "${tempProject.primaryGuide.name}",
            guide2: "${tempProject.secondaryGuide.name}",
            status: '<%

                int status = project.getApprovalStatus();
                if (status == 0){
                    out.print("Approval Pending");
                } else if (status == 1 && project.getStartingDate().after(new java.util.Date())){
                    out.print("Approved(Not Started)");
                } else if (status == 1 && project.getStartingDate().before(new java.util.Date())){
                    out.print("Approved(Ongoing)");
                } else if (status == 2){
                    out.print("Finished");
                }
            %>',
            sdate: "<%=new SimpleDateFormat("MMM dd, yyyy").format(project.getStartingDate())%>",
            details: '<a class="btn btn-primary btn-outline btn-info" href="project?prj=${tempProject.id}">Details</a>'
        },
        </c:forEach>
    ];

    $(document).ready(function () {
        var sTable = $('#s-table').DataTable({
            "data": sData,
            "columns": [
                {"data": "prjId"},
                {"data": "title"},
                {"data": "guide1"},
                {"data": "guide2"},
                {"data": "status"},
                {"data": "sdate"},
                {
                    "data": "details",
                    "orderable": false,
                    "searchable": false
                }
            ]
        });
    });

</script>
