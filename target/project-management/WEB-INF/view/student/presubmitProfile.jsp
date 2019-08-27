<%@ page import="com.pms.entity.Student" %>
<%@include file="templates/header.jsp" %>
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
            <a class="navbar-brand" href="index">Project Management</a>
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
                        Student Profile
                    </h2>
                </div>


                <form action="updateProfile" method="post">


                    <div class="col-md-8">

                        <div class="form-group row">

                            <div class="col-xs-4">
                                <label for="product-quantity">Roll Number</label>
                                <input type="text" value="${theUser.username}" class="form-control" size="60" disabled>
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col-xs-4">
                                <label for="user-name">First Name </label>
                                <input type="text" value="${theUser.firstname}" class="form-control" name="fname"
                                       required><br>
                            </div>

                            <div class="col-xs-4">
                                <label for="user-name">Middle Name </label>
                                <input type="text" value="${theUser.middlename}" class="form-control" name="mname"><br>
                            </div>
                            <div class="col-xs-4">
                                <label for="product-price">Last Name</label>
                                <input type="text" value="${theUser.lastname}" name="lname"
                                       class="form-control" required><br>
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col-xs-4">
                                <label for="user-name">Email </label>
                                <input type="email" value="${theUser.email}" class="form-control" name="email"
                                       required><br>
                            </div>

                            <div class="col-xs-4">
                                <label for="user-name">Phone No </label>
                                <input type="number" step="1" value="${theUser.phone}" class="form-control" name="phone"
                                       pattern="[0-9]{10}" title="Please enter a valid phone number"
                                       required><br>
                            </div>
                        </div>
                        <br>
                        <div class="form-group ">
                            <input type="button" data-toggle="modal" data-target="#updateModal" name="update"
                                   class="btn btn-primary btn-outline" value="Update Profile">
                        </div>


                    </div><!--Main Content-->
                    <!-- Submit Details Modal-->
                    <div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
                         aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Confirm Update ?</h5>
                                    <button style="margin-top: -25px" class="close" type="button" data-dismiss="modal"
                                            aria-label="Close">
                                        <span aria-hidden="true">x</span>
                                    </button>
                                </div>
                                <div class="modal-body">Select "Submit" below if you surely want to update your profile
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

    <%@include file="templates/footer.jsp" %>
