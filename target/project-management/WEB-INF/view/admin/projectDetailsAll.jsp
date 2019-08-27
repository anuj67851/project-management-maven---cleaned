<%@ page import="com.pms.entity.Project" %>
<%@ page import="com.pms.utility.SUtility" %>
<%@ page import="com.pms.utility.printAlert" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@include file="templates/header.jsp" %>
<%@include file="templates/top-side-nav.jsp" %>
<%Project project = (Project) pageContext.findAttribute("theProject");%>
<div id="page-wrapper">
    <div class="container-fluid">
        <div class="col-md-12">
            <div class="row">
                <h2 class="page-header" style="display: inline">
                    <br>Project Details
                </h2>
            </div>
            <br>
            <ul class="nav nav-pills">
                <li class="active"><a href="#project-pills" data-toggle="tab">Project Info.</a>
                </li>
                <li><a href="#group-pills" data-toggle="tab">Group Members</a>
                </li>
                <li><a href="#guide-pills" data-toggle="tab">Guides</a>
                </li>
                <li class="pull-right"><a href="#action-pills" data-toggle="tab"><span class="fa fa-gear"></span>
                    Actions</a>
                </li>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <div class="tab-pane fade in active" id="project-pills">
                    <div class="col-md-8" style="padding-top: 20px">
                        <div class="form-group">
                            <label>Project Status </label>
                            <input type="text" value="<%
                                if (project == null){
                                    out.print("Not Submitted");
                                } else{
                                    out.print(SUtility.getProjectStatus(project.getApprovalStatus(), project.getStartingDate()));
                                }%>" class="form-control" disabled>

                        </div>
                        <div class="form-group">
                            <label>Project Id </label>
                            <input type="text" value="${theProject.projectId}" class="form-control" disabled>

                        </div>
                        <div class="form-group">
                            <label>Project Title </label>
                            <input type="text" value="${theProject.title}" class="form-control" disabled>

                        </div>


                        <div class="form-group">
                            <label>Project Tools</label>
                            <textarea cols="30" rows="10" class="form-control"
                                      disabled>${theProject.tools}</textarea>
                        </div>

                        <div class="form-group">
                            <label>Project Technologies</label>
                            <textarea cols="30" rows="10" class="form-control"
                                      disabled>${theProject.technologies}</textarea>
                        </div>


                        <div class="form-group row">

                            <div class="col-xs-3">
                                <label>Starting Date</label>
                                <input type="text"
                                       value="<%if (project != null){out.print(new SimpleDateFormat("MMM dd, yyyy").format(project.getStartingDate()));}%>"
                                       class="form-control"
                                       disabled>
                            </div>
                            <c:if test="${theProject != null}">
                                <div class="col-xs-4">
                                    <label>Project Synopsis</label>
                                    <p style="font-size: 16px"><a href="synopsis?id=${theProject.id}"
                                                                  class="btn btn-primary btn-outline">Download</a></p>
                                </div>
                            </c:if>
                        </div>

                    </div>

                    <aside class="col-md-4" style="padding-top: 20px">
                        <div class="form-group">
                            <label>Company Name</label>
                            <input type="text" value="${theProject.company.name}" class="form-control" disabled>
                        </div>
                        <div class="form-group">
                            <label>Contact No</label>
                            <input type="text" value="${theProject.company.contactno}" class="form-control"
                                   disabled>
                        </div>
                        <div class="form-group">
                            <label>External Guide Name </label>
                            <input type="text" value="${theProject.company.externalGuideName}"
                                   class="form-control"
                                   disabled>
                        </div>
                        <div class="form-group">
                            <label>External Guide Email</label>
                            <input type="email" value="${theProject.company.externalGuideEmail}"
                                   class="form-control"
                                   disabled>
                        </div>
                        <div class="form-group">
                            <label>External Guide Contact </label>
                            <input type="text" value="${theProject.company.externalGuideContact}"
                                   class="form-control"
                                   disabled>
                        </div>
                        <div class="form-group">
                            <label>HR Name </label>
                            <input type="text" value="${theProject.company.hrname}" class="form-control"
                                   disabled>
                        </div>
                        <div class="form-group">
                            <label>HR Contact </label>
                            <input type="text" value="${theProject.company.hrContact}" class="form-control"
                                   disabled>
                        </div>
                        <div class="form-group">
                            <label>Company Address </label>
                            <textarea cols="30" rows="10" class="form-control"
                                      disabled>${theProject.company.address}</textarea>
                        </div>
                    </aside>

                </div>
                <div class="tab-pane fade" id="group-pills">
                    <c:if test="${theProject != null}">
                        <div class="form-group col-md-12" style="padding-top: 30px;">
                            <label>All Group Members</label><br/>
                            <c:forEach var="tempGroup" items="${theProject.students}">
                                    <span style="font-size: 16px"><a href="student?roll=${tempGroup.username}"
                                                                     class="btn btn-primary btn-outline">${tempGroup.username}</a></span>
                            </c:forEach>
                        </div>
                        <c:if test="${theProject.approvalStatus != 2}">
                            <div class="form-group col-md-4" style="padding-top: 30px">
                                <form action="addStudentToProject" method="post">

                                    <label>Add Group Member</label>
                                    <select name="grpMember" class="form-control" required>
                                        <c:forEach items="${allFreeStudents}" var="tempStudent">
                                            <option value="${tempStudent.username}">${tempStudent.username}</option>
                                        </c:forEach>
                                    </select>
                                    <input type="hidden" value="${theProject.id}" name="projectId">
                                    <br/>
                                    <input style="font-size: 16px" type="submit" value="Add Student"
                                           class="btn btn-primary btn-outline">

                                </form>
                            </div>
                        </c:if>
                    </c:if>
                </div>

                <div class="tab-pane fade" id="guide-pills">
                    <div class="col-md-4">

                        <c:if test="${theProject != null && theProject.primaryGuide == null && theProject.approvalStatus != 2}">

                            <form action="addPrimaryGuide" method="post" style="padding-top: 30px">
                                <div class="form-group">
                                    <label>Add Primary Guide</label>
                                    <select name="pguide" class="form-control">
                                        <c:forEach var="tempGuide" items="${allGuide}">
                                            <option value="${tempGuide.username}">${tempGuide.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <input type="hidden" name="prjId" value="${theProject.id}">
                                <input type="submit" value="Add" class="btn btn-outline btn-primary">
                            </form>
                        </c:if>

                        <c:if test="${theProject.primaryGuide != null}">
                            <form action="removePrimaryGuide" method="post" style="padding-top: 30px">
                                <div class="form-group">
                                    <label>Primary Guide:
                                        <span style="font-size: 16px">${theProject.primaryGuide.name}</span>
                                    </label>
                                    <c:if test="${theProject.approvalStatus != 2}">
                                        <p style="font-size: 16px"><a href="#" data-toggle="modal"
                                                                      data-target="#removePGuide"
                                                                      class="btn btn-danger btn-outline">Remove</a></p>

                                        <input type="hidden" name="prjId" value="${theProject.id}">
                                        <%=printAlert.getSubmitBox("removePGuide", "Confirm Removal ?", "Select \"Confirm\" below to remove the Guide from the project.")%>
                                    </c:if>
                                </div>
                            </form>
                        </c:if>

                        <c:if test="${theProject != null && theProject.secondaryGuide == null && theProject.approvalStatus != 2}">
                            <form action="addSecondaryGuide" method="post" style="padding-top: 30px">
                                <div class="form-group">
                                    <label>Add Secondary Guide</label>
                                    <select name="sguide" class="form-control">
                                        <c:forEach var="tempGuide" items="${allGuide}">
                                            <option value="${tempGuide.username}">${tempGuide.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <input type="hidden" name="prjId" value="${theProject.id}">
                                <input type="submit" value="Add" class="btn btn-outline btn-primary">
                            </form>
                        </c:if>

                        <c:if test="${theProject.secondaryGuide != null}">
                            <form action="removeSecondaryGuide" method="post" style="padding-top: 30px">
                                <div class="form-group">
                                    <label>Secondary Guide:
                                        <span style="font-size: 16px">${theProject.secondaryGuide.name}</span>
                                    </label>
                                    <c:if test="${theProject.approvalStatus != 2}">
                                        <p style="font-size: 16px"><a href="#" data-toggle="modal"
                                                                      data-target="#removeSGuide"
                                                                      class="btn btn-danger btn-outline">Remove</a></p>

                                        <input type="hidden" name="prjId" value="${theProject.id}">
                                        <%=printAlert.getSubmitBox("removeSGuide", "Confirm Removal ?", "Select \"Confirm\" below to remove the Guide from the project.")%>
                                    </c:if>
                                </div>
                            </form>
                        </c:if>
                    </div>

                </div>
                <div class="tab-pane fade" id="action-pills">
                    <div class="col-md-4" style="padding-top: 20px">
                        <c:if test="${theProject.approvalStatus != 0}">
                            <div class="form-group">
                                <label>Generate Group MarkSheet (Google Chrome Recommended)</label>
                                <p style="font-size: 16px"><a target="_blank" href="marksheet?id=${theProject.id}"
                                                              class="btn btn-primary btn-outline">Generate</a></p>
                            </div>
                        </c:if>
                        <c:if test="${theProject.approvalStatus != 0}">
                            <form action="deleteProject" method="post">
                                <div class="form-group">
                                    <label>Delete Project</label>
                                    <p style="font-size: 16px"><a href="#" data-toggle="modal" data-target="#deleteProject"
                                                                  class="btn btn-danger btn-outline">Delete</a></p>
                                </div>
                                <input type="hidden" name="prjId" value="${theProject.id}">
                                <%=printAlert.getSubmitBox("deleteProject", "Confirm Deletion ?", "Are you sure want to delete the project?")%>
                            </form>
                        </c:if>
                        <c:if test="${theProject.approvalStatus == 0}">
                            <label>Project Approval</label>
                            <h6>Students will be notified about this action via email . (Disapproval will result
                                in deletion of project)</h6>
                            <form action="approveProject" method="post" style="display: inline">
                                <input type="hidden" name="prjApprove" value="1">
                                <input type="hidden" name="prjId" value="${theProject.id}">
                                <input class="btn btn-success btn-outline" type="submit" value="&#10004;"/>
                            </form>
                            <form action="approveProject" method="post" style="display: inline">
                                <input type="hidden" name="prjApprove" value="0">
                                <input type="hidden" name="prjId" value="${theProject.id}">
                                <input class="btn btn-danger btn-outline" type="submit" value="&#10008;"/>
                            </form>
                        </c:if>
                    </div>
                </div>
            </div>

        </div>

    </div>
</div>
<!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<%@include file="templates/footer.jsp" %>