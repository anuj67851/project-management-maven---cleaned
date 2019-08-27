<%@ page import="com.pms.utility.printAlert" %>
<%@include file="templates/header.jsp" %>
<%@include file="templates/top-side-nav.jsp" %>

<div id="page-wrapper">
    <div class="container-fluid">
        <div class="col-md-12">
            <div class="row">
                <h2 class="page-header" style="display: inline">
                    <br>Templates
                </h2>
            </div>
            <br>
            <div class="table-responsive" style="padding-top: 20px">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th width="15%">File Name</th>
                        <th width="10%">Download</th>
                        <th>Update (8MB Max)</th>
                        <th width="40%">Description</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="temp" items="${allTemplates}">
                        <tr>
                            <td>${temp.name}</td>
                            <td> <span><a href="template?id=${temp.id}"
                                          class="btn btn-primary btn-outline">Download</a></span></td>
                            <td>
                                <form action="updateFile" method="post" enctype="multipart/form-data">
                                    <input name="fileId" type="hidden" value="${temp.id}">
                                    <input type="file" class="form-control" name="file" required>
                                    <br/>
                                    <input type="submit" value="Update" class="btn btn-primary btn-outline">
                                </form>
                            </td>
                            <td>${temp.description}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

    </div>
    <!-- /#wrapper -->
</div>

<%@include file="templates/footer.jsp" %>
<%
    String message = (String) request.getAttribute("message");
    if (message != null && message.equals("Size Invalid")) {
        out.print(printAlert.getAlertBox("Size Exceed",
                "The File you uploaded has size greater than the max size, unable to update"));
    } else if (message != null && message.equals("Done")) {
        out.print(printAlert.getAlertBox("Update Successfull",
                "Template was update successfully"));
    }
%>