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
                <h1 class="page-header">
                    Project Approval Pending
                </h1>
                <h3> Dashboard Page will unlock after the project is approved and you will be Notified by Email on
                    Approval by Admin</h3>
                <h6>(You will be redirected to submit project page if your project was rejected by admin.)</h6>

            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->


    <%@include file="templates/footer.jsp" %>
<%
    if (request.getAttribute("message") != null && request.getAttribute("message").equals("already submitted")) {
        out.print(printAlert.getAlertBox("Project already submitted",
                "Your Project is already Submitted by some another student , Please contact admin if you dont know about it"));
    }
    request.setAttribute("message", null);
%>