<%@ page import="com.pms.utility.printAlert" %>
<%@include file="templates/header.jsp" %>
<%@include file="templates/top-side-nav.jsp" %>
<div id="page-wrapper">
    <div class="container-fluid">


        <div class="col-md-12">
            <div class="row">
                <h1 class="page-header" style="display: inline">
                    <br>Add Temporary Examiner
                </h1>
            </div>
            <br>

            <div class="col-md-5">
                <form action="addExaminer" method="post">
                    <div class="form-group">
                        <label>Username</label>
                        <input type="text" name="username" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Password(Remember it)</label>
                        <input type="text" name="password" minlength="6" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Name</label>
                        <input type="text" name="name" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" name="email" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Phone </label>
                        <input type="number" name="phone" class="form-control" required>
                    </div>
                    <div class="form-group ">
                        <input type="submit" class="btn btn-primary btn-outline" value="Add"/>
                    </div>

                </form>
            </div>
            <!-- SIDEBAR-->
        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->


<%@include file="templates/footer.jsp" %>
<%
    String message = (String) request.getAttribute("message");
    if (message != null && message.equals("clashg")) {
        out.print(printAlert.getAlertBox("Guide with same username Already Exists",
                "The Guide username you entered already exists in database, Please check Again"));
    }
%>