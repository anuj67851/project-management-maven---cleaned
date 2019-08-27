<%@ page import="com.pms.utility.printAlert" %>
<%@ page import="com.pms.entity.Student" %>
<%@include file="templates/header.jsp" %>
<body>

<div id="wrapper">

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/student/index">Project Management</a>
        </div>

        <!-- /.navbar-header -->

        <ul class="nav navbar-top-links navbar-right">
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-user fa-fw"></i>
                    <%
                        Student user = (Student) session.getAttribute("user");
                        out.print(user.getFirstname() + " " + user.getLastname() + "(" + user.getUsername() + ")");
                    %>
                    <i class="fa fa-caret-down"></i>
                </a>
                <ul class="dropdown-menu dropdown-user">
                    <li>
                        <a href="profile"><i class="fa fa-user"></i> My Profile</a>
                    </li>
                    <li class="divider"></li>
                    <li><a href="#" class="dropdown-item" data-toggle="modal" data-target="#logoutModal"><i
                            class="fa fa-sign-out fa-fw"></i> Logout</a>
                    </li>
                </ul>
                <!-- /.dropdown-user -->
            </li>
            <!-- /.dropdown -->
        </ul>
    </nav>

    <div>

        <div class="container-fluid">


            <div class="col-md-12">
                <div class="row">
                    <h2 class="page-header" style="display: inline">
                        Submit Project Details
                        <span style="font-size: 16px"> <a href="files/synopsis" class="btn btn-primary btn-outline">Download Synopsis Format</a></span>
                    </h2>
                </div>


                <form action="submitProject" method="post" enctype="multipart/form-data" id="submitProjectForm">


                    <div class="col-md-8">
                        <div class="form-group">
                            <label>Project Title </label>
                            <input type="text" value="${title}" name="title" class="form-control" required>

                        </div>


                        <div class="form-group">
                            <label>Project Tools (Max length(255) chars)</label>
                            <textarea name="tools" maxlength="255" cols="30" rows="10" class="form-control"
                                      required>${tools}</textarea>
                        </div>

                        <div class="form-group">
                            <label>Project Technologies (Max length(255) chars)</label>
                            <textarea name="technologies" maxlength="255" cols="30" rows="10" class="form-control"
                                      required>${technologies}</textarea>
                        </div>


                        <div class="form-group row">

                            <div class="col-xs-3">
                                <label>Starting Date</label>
                                <input type="date" name="sdate" class="form-control" required>
                            </div>
                            <div class="col-xs-4">
                                <label>Project Synopsis (Max Size - 8MB<br/> Filname size max - 50 chars, <br/>(Doc,
                                    docx and Pdf only)</label>
                                <input type="file" name="synopsis" class="form-control" accept=".pdf,.doc,.docx"
                                       required>
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col-md-2">
                                <label>Group Members</label>
                            </div>
                            <div class="col-md-2">
                                <select name="gm1" class="form-control">
                                    <option></option>
                                    <c:forEach var="temp" items="${allStudents}">
                                        <option value="${temp}">${temp}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <select name="gm2" class="form-control">
                                    <option></option>
                                    <c:forEach var="temp" items="${allStudents}">
                                        <option value="${temp}">${temp}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <select name="gm3" class="form-control">
                                    <option></option>
                                    <c:forEach var="temp" items="${allStudents}">
                                        <option value="${temp}">${temp}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                    </div><!--Main Content-->


                    <!-- SIDEBAR-->


                    <aside id="admin_sidebar" class="col-md-4">
                        <div class="form-group">
                            <label>Company Name</label>
                            <input type="text" name="cmpname" value="${cmpname}" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label>Contact No</label>
                            <input type="number" pattern="[0-9]{10}" step="1" value="${cmpcontact}"
                                   title="Please Enter Valid Number" name="cmpcontact" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label>External Guide Name </label>
                            <input type="text" name="cmpextname" value="${cmpextname}" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label>External Guide Email</label>
                            <input type="email" name="cmpextemail" value="${cmpextemail}" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label>External Guide Contact </label>
                            <input type="number" pattern="[0-9]{10}" step="1" value="${cmpextcontact}"
                                   title="Please Enter Valid Number" name="cmpextcontact" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label>HR Name </label>
                            <input type="text" name="cmphrname" value="${cmphrname}" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label>HR Contact </label>
                            <input type="number" pattern="[0-9]{10}" step="1" value="${cmphrcontact}"
                                   title="Please Enter Valid Number" name="cmphrcontact" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label>Company Address </label>
                            <textarea name="cmpaddress" maxlength="255" cols="30" rows="10" class="form-control"
                                      required>${cmpaddress}</textarea>
                        </div>

                        <div class="form-group ">
                            <a href="#" class="btn btn-primary btn-outline" data-toggle="modal"
                               data-target="#submitModal">Submit Details</a>
                        </div>


                    </aside><!--SIDEBAR-->
                    <!-- Submit Details Modal-->
                    <div class="modal fade" id="submitModal" tabindex="-1" role="dialog"
                         aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Confirm Submission ?</h5>
                                    <button style="margin-top: -25px" class="close" type="button" data-dismiss="modal"
                                            aria-label="Close">
                                        <span aria-hidden="true">x</span>
                                    </button>
                                </div>
                                <div class="modal-body">Select "Submit" below if you are ready submit the details . (The
                                    action cannot be undone until edited by administrator)
                                </div>
                                <div class="modal-footer">
                                    <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                                    <input type="submit" class="btn btn-primary" value="Submit">
                                </div>
                            </div>
                        </div>
                    </div>


                </form>


            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->


    <%@include file="templates/footer.jsp" %>
<%
    String message = (String) request.getAttribute("message");
    if (message != null && message.equals("Extension")) {
        out.print(printAlert.getAlertBox("File Extension or Size Invalid",
                "The Synopsis you submitted has either invalid Extension or its size exceeds 16MB, Please check the file or download the sample Synopsis"));
    } else if (message != null && message.equals("Occupied")) {
        out.print(printAlert.getAlertBox("Group Members Occupied",
                "One of your group members is/are already occupied , please try again"));
    } else if (message != null && message.equals("Extension and Occupied")) {
        out.print(printAlert.getAlertBox("Group Members Occupied and Invalid File Extension",
                "One or more Group members are already occupied and Your synopsis extension or size was invalid, Please resolve all problems and try again"));
    } else if (message != null && message.equals("Project Not Approved")) {
        out.print(printAlert.getAlertBox("Project Not Approved",
                "Your Project was not approved by the administrator, please select another definition or contact him for any queries"));
    }
    request.setAttribute("message", null);
%>