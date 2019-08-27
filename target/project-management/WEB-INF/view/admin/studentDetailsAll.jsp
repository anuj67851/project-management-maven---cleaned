<%@ page import="com.pms.entity.Evaluation" %>
<%@ page import="com.pms.entity.Presentation" %>
<%@ page import="com.pms.entity.Student" %>
<%@ page import="com.pms.utility.SUtility" %>
<%@ page import="com.pms.utility.printAlert" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@include file="templates/header.jsp" %>
<%@include file="templates/top-side-nav.jsp" %>
<%Student student = (Student) pageContext.findAttribute("theStudent");%>
<div id="page-wrapper">
    <div class="container-fluid">


        <div class="col-md-12">
            <div class="row">
                <h2 class="page-header" style="display: inline">
                    <br>Student Details (${theStudent.username})
                </h2>
            </div>
            <br>
            <ul class="nav nav-pills">
                <li class="active"><a href="#profile-pills" data-toggle="tab">Profile</a>
                </li>
                <li><a href="#project-pills" data-toggle="tab">Project Info.</a>
                </li>
                <li><a href="#weval-pills" data-toggle="tab">Weekly Evaluations</a>
                </li>
                <li><a href="#presentation-pills" data-toggle="tab">Presentations</a>
                </li>
                <li class="pull-right"><a href="#action-pills" data-toggle="tab"><span class="fa fa-gear"></span>
                    Actions</a>
                </li>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <div class="tab-pane fade in active" id="profile-pills">
                    <div class="col-md-4" style="padding-top: 20px">
                        <div class="form-group">
                            <label>Roll Number</label>
                            <input type="text" value="${theStudent.username}" class="form-control" disabled>
                        </div>
                        <div class="form-group">
                            <label>Full Name </label>
                            <input type="text"
                                   value="${theStudent.firstname} ${theStudent.middlename} ${theStudent.lastname}"
                                   class="form-control" disabled>
                        </div>
                        <div class="form-group">
                            <label>Contact No</label>
                            <input type="number" value="${theStudent.phone}" class="form-control" disabled>
                        </div>
                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" value="${theStudent.email}" class="form-control" disabled>
                        </div>
                        <div class="form-group">
                            <label>Current Work Status</label>

                            <input type="text" value="<%
                                   out.print(SUtility.getWorkStatus(student.getStatus()));
                                %>" class="form-control" disabled>
                        </div>
                        <div class="form-group">
                            <label>Project Details and Actions : </label>
                            <c:choose>
                                <c:when test="${theStudent.project != null}">
                                    <span><a href="project?prj=${theStudent.project.id}"
                                             class="btn btn-primary btn-outline">Details</a></span>
                                </c:when>
                                <c:otherwise>
                                    Project Details Not Submitted
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="form-group">
                            <label>Confirmation Letter :</label>
                            <c:choose>
                                <c:when test="${theStudent.confLetterStatus == 0}">
                                    Not Submitted
                                </c:when>
                                <c:otherwise>
                                    <p style="font-size: 16px"><a href="confLetter?roll=${theStudent.username}"
                                                                  class="btn btn-primary btn-outline">Download</a>
                                    </p>
                                </c:otherwise>
                            </c:choose>

                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" id="weval-pills">

                    <div class="table-responsive" style="padding-top: 20px">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th colspan="3"></th>
                                <th colspan="4" class="text-center">${theStudent.project.primaryGuide.name}</th>
                                <th></th>
                            </tr>
                            <tr>
                                <th>Week</th>
                                <th>Date</th>
                                <th>Report</th>
                                <th>TS</th>
                                <th>D</th>
                                <th>T</th>
                                <th>M</th>
                                <th>Total</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="tempEval" items="${theStudent.evaluation}" varStatus="index">
                                <%
                                    Evaluation eval = (Evaluation) pageContext.findAttribute("tempEval");
                                    Date evalDate = eval.getSubdate();
                                %>
                                <tr>
                                    <td>${index.count}</td>
                                    <td><%=new SimpleDateFormat("MMM dd, yyyy").format(evalDate)%>
                                    </td>
                                    <td> <span style="font-size: 16px"><a href="report?id=${tempEval.report.id}"
                                                                          class="btn btn-primary btn-outline">Download</a></span>
                                    </td>
                                    <td>${tempEval.ratings.ts}</td>
                                    <td>${tempEval.ratings.d}</td>
                                    <td>${tempEval.ratings.t}</td>
                                    <td>${tempEval.ratings.m}</td>
                                    <td><c:if test="${tempEval.ratings != null}">
                                        ${tempEval.ratings.ts + tempEval.ratings.d + tempEval.ratings.t + tempEval.ratings.m}
                                    </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                        <p style="padding-top: 20px">Note: (Each Component 5 Marks, Total 20 Marks)<br/>
                            TS: Time Sheet Submission &amp; Justification<br/>
                            D: Documentation about their discussion, planning, test, errors, checklist, weekly
                            report etc..<br/>
                            T: Technology improvement<br/>
                            M: Milestone achievement</p>
                    </div>
                    <!-- /.table-responsive -->
                </div>
                <div class="tab-pane fade" id="presentation-pills">
                    <div class="table-responsive" style="padding-top: 20px">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th colspan="3"></th>
                                <th colspan="4" class="text-center">${theStudent.project.primaryGuide.name}</th>
                                <th></th>
                            </tr>
                            <tr>
                                <th>Week</th>
                                <th>Date</th>
                                <th>File</th>
                                <th>TS</th>
                                <th>D</th>
                                <th>T</th>
                                <th>M</th>
                                <th>Total</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="tempEval" items="${theStudent.presentation}" varStatus="index">
                                <%
                                    Presentation eval = (Presentation) pageContext.findAttribute("tempEval");
                                    Date evalDate = eval.getSubdate();
                                %>
                                <tr>
                                    <td>${index.count}</td>
                                    <td><%=new SimpleDateFormat("MMM dd, yyyy").format(evalDate)%>
                                    </td>
                                    <td> <span style="font-size: 16px"><a href="ppt?id=${tempEval.ppt.id}"
                                                                          class="btn btn-primary btn-outline">Download</a></span>
                                    </td>
                                    <td>${tempEval.ratings.ts}</td>
                                    <td>${tempEval.ratings.d}</td>
                                    <td>${tempEval.ratings.t}</td>
                                    <td>${tempEval.ratings.m}</td>
                                    <td><c:if test="${tempEval.ratings != null}">
                                        ${tempEval.ratings.ts + tempEval.ratings.d + tempEval.ratings.t + tempEval.ratings.m}
                                    </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                        <p style="padding-top: 20px">Note: (Each Component 5 Marks, Total 20 Marks)<br/>
                            TS: Time Sheet Submission &amp; Justification<br/>
                            D: Documentation about their discussion, planning, test, errors, checklist, weekly
                            report etc..<br/>
                            T: Technology improvement<br/>
                            M: Milestone achievement</p>
                    </div>
                    <!-- /.table-responsive -->
                </div>
                <div class="tab-pane fade" id="project-pills">
                    <div class="col-md-8" style="padding-top: 20px">
                        <div class="form-group">
                            <label>Project Status </label>
                            <input type="text" value="<%
                                if (student.getProject() == null){
                                    out.print("Not Submitted");
                                } else{
                                    out.print(SUtility.getProjectStatus(student.getProject().getApprovalStatus(), student.getProject().getStartingDate()));
                                }%>" class="form-control" disabled>

                        </div>
                        <div class="form-group">
                            <label>Project Id </label>
                            <input type="text" value="${theStudent.project.projectId}" class="form-control" disabled>

                        </div>
                        <div class="form-group">
                            <label>Project Title </label>
                            <input type="text" value="${theStudent.project.title}" class="form-control" disabled>

                        </div>


                        <div class="form-group">
                            <label>Project Tools</label>
                            <textarea cols="30" rows="10" class="form-control"
                                      disabled>${theStudent.project.tools}</textarea>
                        </div>

                        <div class="form-group">
                            <label>Project Technologies</label>
                            <textarea cols="30" rows="10" class="form-control"
                                      disabled>${theStudent.project.technologies}</textarea>
                        </div>


                        <div class="form-group row">

                            <div class="col-xs-3">
                                <label>Starting Date</label>
                                <input type="text"
                                       value="<%if (student.getProject() != null){out.print(new SimpleDateFormat("MMM dd, yyyy").format(student.getProject().getStartingDate()));}%>"
                                       class="form-control"
                                       disabled>
                            </div>
                            <c:if test="${theStudent.project != null}">
                                <div class="col-xs-4">
                                    <label>Project Synopsis</label>
                                    <p style="font-size: 16px"><a href="synopsis?id=${theStudent.project.id}"
                                                                  class="btn btn-primary btn-outline">Download</a></p>
                                </div>
                            </c:if>
                        </div>

                    </div>

                    <aside class="col-md-4" style="padding-top: 20px">
                        <div class="form-group">
                            <label>Company Name</label>
                            <input type="text" value="${theStudent.project.company.name}" class="form-control" disabled>
                        </div>
                        <div class="form-group">
                            <label>Contact No</label>
                            <input type="text" value="${theStudent.project.company.contactno}" class="form-control"
                                   disabled>
                        </div>
                        <div class="form-group">
                            <label>External Guide Name </label>
                            <input type="text" value="${theStudent.project.company.externalGuideName}"
                                   class="form-control"
                                   disabled>
                        </div>
                        <div class="form-group">
                            <label>External Guide Email</label>
                            <input type="email" value="${theStudent.project.company.externalGuideEmail}"
                                   class="form-control"
                                   disabled>
                        </div>
                        <div class="form-group">
                            <label>External Guide Contact </label>
                            <input type="text" value="${theStudent.project.company.externalGuideContact}"
                                   class="form-control"
                                   disabled>
                        </div>
                        <div class="form-group">
                            <label>HR Name </label>
                            <input type="text" value="${theStudent.project.company.hrname}" class="form-control"
                                   disabled>
                        </div>
                        <div class="form-group">
                            <label>HR Contact </label>
                            <input type="text" value="${theStudent.project.company.hrContact}" class="form-control"
                                   disabled>
                        </div>
                        <div class="form-group">
                            <label>Company Address </label>
                            <textarea cols="30" rows="10" class="form-control"
                                      disabled>${theStudent.project.company.address}</textarea>
                        </div>
                    </aside>

                </div>

                <div class="tab-pane fade" id="action-pills">
                    <div class="col-md-6" style="padding-top: 20px">

                        <form action="deleteStudent" method="post">
                            <div class="form-group">
                                <label>Delete Student</label>
                                <p style="font-size: 16px"><a href="#" data-toggle="modal" data-target="#deleteStudent"
                                                              class="btn btn-danger btn-outline">Delete</a></p>
                            </div>
                            <input type="hidden" name="roll" value="${theStudent.username}">
                            <%=printAlert.getSubmitBox("deleteStudent", "Confirm Deletion?", "Deleting the Student will also delete his/her\n" +
                                    "                                            evaluations and exams information. If the Student is alone in the Project then the\n" +
                                    "                                            Project Information will also get Deleted. Are you Sure?")%>
                        </form>

                        <c:if test="${theStudent.status != 0 && theStudent.status != 3 && theStudent.status != 4}">

                            <form action="removeFromProject" method="post">
                                <div class="form-group">
                                    <label>Remove Current Student From Project</label>
                                    <p style="font-size: 16px"><a href="#" data-toggle="modal"
                                                                  data-target="#removeFromProjModal"
                                                                  class="btn btn-danger btn-outline">Remove</a></p>
                                </div>
                                <input type="hidden" name="roll" value="${theStudent.username}">
                                <%=printAlert.getSubmitBox("removeFromProjModal", "Confirm Removal?", "Select \"Confirm\" below to remove the student from the project. If the Student is alone in project then Project Info will also be deleted.")%>
                            </form>
                        </c:if>

                        <c:if test="${theStudent.status == 3}">
                            <form action="finishReq" method="post">
                                <div class="form-group">
                                    <label>Student Request For Completion</label>
                                    <select name="finApprove" class="form-control" required>
                                        <option value="1">Approve</option>
                                        <option value="0">Reject</option>
                                    </select>
                                </div>
                                <input type="hidden" name="roll" value="${theStudent.username}">
                                <p style="font-size: 16px"><a href="#" data-toggle="modal" data-target="#appFinReq"
                                                              class="btn btn-primary btn-outline">Submit</a></p>
                                <%=printAlert.getSubmitBox("appFinReq", "Confirm ?", "This Action Cant be Undone.")%>
                            </form>
                        </c:if>

                        <c:if test="${theStudent.allowProjectEditing == 0 && theStudent.project != null}">
                            <form action="allowEditing" method="post">
                                <label>Allow Student to edit Project Info for Single time :</label>
                                <input type="hidden" name="roll" value="${theStudent.username}">
                                <p style="font-size: 16px"><a href="#" data-toggle="modal" data-target="#allowEdit"
                                                              class="btn btn-primary btn-outline">Allow</a></p>
                                <%=printAlert.getSubmitBox("allowEdit", "Confirm Allowance?", "This Action Cant be Undone.")%>
                            </form>
                        </c:if>

                        <c:if test="${theStudent.weeklySub == 0 && theStudent.project != null}">
                            <form action="allowSubmission" method="post">
                                <label>Allow Student to submit weekly evaluation :</label>
                                <input type="hidden" name="roll" value="${theStudent.username}">
                                <p style="font-size: 16px"><a href="#" data-toggle="modal" data-target="#allowSub"
                                                              class="btn btn-primary btn-outline">Allow</a></p>
                                <%=printAlert.getSubmitBox("allowSub", "Confirm Submission Allowance?", "This Action Cant be Undone.")%>
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