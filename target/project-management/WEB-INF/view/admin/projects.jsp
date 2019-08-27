<%@ page import="com.pms.entity.Project" %>
<%@ page import="com.pms.utility.TableGen" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%@include file="templates/header.jsp" %>
<%@include file="templates/top-side-nav.jsp" %>

<div id="page-wrapper">
    <div class="container-fluid">
        <div class="col-md-12">
            <div class="row">
                <h2 class="page-header" style="display: inline">
                    <br>Projects
                </h2>
            </div>
            <br>
            <ul class="nav nav-pills">
                <li class="active"><a href="#all-pills" data-toggle="tab">All</a>
                </li>
                <li><a href="#ap-pills" data-toggle="tab">Approval Pending</a>
                </li>
                <li><a href="#a-pills" data-toggle="tab">Approved(Not Started)</a>
                </li>
                <li><a href="#aog-pills" data-toggle="tab">Approved(Ongoing)</a>
                </li>
                <li><a href="#c-pills" data-toggle="tab">Completed</a>
                </li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane fade in active" id="all-pills">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <table id="all-table" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>ProjectId</th>
                                    <th>Title</th>
                                    <th>Company</th>
                                    <th>Guide-1</th>
                                    <th>Guide-2</th>
                                    <th>Status</th>
                                    <th>Project Info.</th>
                                </tr>
                                </thead>
                                <tbody>

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <%=TableGen.genTableProjects("ap")%>
                <%=TableGen.genTableProjects("a")%>
                <%=TableGen.genTableProjects("aog")%>
                <%=TableGen.genTableProjects("c")%>
            </div>

        </div>
    </div>
    <!-- /.row -->
</div>
<!-- /#wrapper -->
</div>
<%@include file="templates/footer.jsp" %>
<script>
    var allData = [
        <c:forEach var="tempProject" items="${allProjects}">
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
            comp: "${tempProject.company.name}",
            details: '<a class="btn btn-primary btn-outline btn-info" href="project?prj=${tempProject.id}">Details</a>'
        },
        </c:forEach>
    ];

    $(document).ready(function () {
        var allTable = $('#all-table').DataTable({
            "data": allData,
            "columns": [
                {"data": "prjId"},
                {"data": "title"},
                {"data": "comp"},
                {"data": "guide1"},
                {"data": "guide2"},
                {"data": "status"},
                {
                    "data": "details",
                    "orderable": false,
                    "searchable": false
                }
            ]
        });
    });

</script>

<%List<Project> allProjects = (List<Project>) pageContext.findAttribute("allProjects");%>
<script>
    <%=TableGen.genTableDataProject(allProjects, 0, "ap")%>
    <%=TableGen.genTableDataProject(allProjects, 1, "a")%>
    <%=TableGen.genTableDataProject(allProjects, 1, "aog")%>
    <%=TableGen.genTableDataProject(allProjects, 2, "c")%>
</script>