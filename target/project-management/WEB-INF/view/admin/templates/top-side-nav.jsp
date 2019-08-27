<%@ page import="com.pms.entity.Admin" %>
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
            <a class="navbar-brand" href="students">Project Management</a>
        </div>
        <!-- /.navbar-header -->

        <ul class="nav navbar-top-links navbar-right">
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-user fa-fw"></i>
                    <%
                        Admin user = (Admin) session.getAttribute("user");
                        out.print(user.getName() + "(" + user.getUsername() + ")");
                    %>
                    <i class="fa fa-caret-down"></i>
                </a>
                <ul class="dropdown-menu dropdown-user">
                    <li><a href="profile"><i class="fa fa-user fa-fw"></i> User Profile</a>
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
        <!-- /.navbar-top-links -->

        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse">
                <ul class="nav" id="side-menu">

                    <li>
                        <a href="students"><i class="fa fa-users fa-fw"></i> Students</a>
                    </li>
                    <li>
                        <a href="guides"><i class="fa fa-user-md fa-fw"></i> Faculty Guides</a>
                    </li>
                    <li>
                        <a href="projects"><i class="fa fa-tasks fa-fw"></i> Projects</a>
                    </li>

                    <li>
                        <a href="templates"><i class="fa fa-file-archive-o fa-fw"></i> File Templates</a>
                    </li>
                    <li>
                        <a href="admins"><i class="fa fa-user-secret fa-fw"></i> Admins</a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-paper-plane fa-fw"></i> Exams<span
                                class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="#"><i class="fa fa-table fa-fw"></i> All Exams</a>
                            </li>
                            <li>
                                <a href="examiners"><i class="fa fa-user-md fa-fw"></i> Examiners</a>
                            </li>
                        </ul>
                        <!-- /.nav-second-level -->
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-user-plus fa-fw"></i> Add Users<span
                                class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="addStudentsPage"><i class="fa fa-plus fa-fw"></i> Add Student(s)</a>
                            </li>
                            <li>
                                <a href="addGuidePage"><i class="fa fa-plus fa-fw"></i> Add Guide(s)</a>
                            </li>
                            <li>
                                <a href="addAdminPage"><i class="fa fa-plus fa-fw"></i> Add Admin</a>
                            </li>
                            <li>
                                <a href="addExaminerPage"><i class="fa fa-plus fa-fw"></i> Add Examiner</a>
                            </li>
                        </ul>
                        <!-- /.nav-second-level -->
                    </li>
                    <li>
                        <a href="genStudentInfo"><i class="fa fa-download fa-fw"></i> Download Student Info.
                            (Excel)<br/> &nbsp;&nbsp;&nbsp;&nbsp;(May Take a
                            while)</a>
                    </li>
                    <li>
                        <a href="announcement"><i class="fa fa-envelope fa-fw"></i> Announcement</a>
                    </li>
                </ul>
            </div>
            <!-- /.sidebar-collapse -->
        </div>
        <!-- /.navbar-static-side -->
    </nav>