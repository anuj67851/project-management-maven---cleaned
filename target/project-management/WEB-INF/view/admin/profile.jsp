<%@ page import="com.pms.utility.printAlert" %>
<%@include file="templates/header.jsp" %>
<%@include file="templates/top-side-nav.jsp" %>
<div id="page-wrapper">
    <div class="container-fluid">


        <div class="col-md-6">
            <div class="row">
                <h1 class="page-header" style="display: inline">
                    <br>Profile
                </h1>
            </div>
            <br>

            <form action="updateProfile" method="post">

                <div class="form-group">
                    <label>Username</label>
                    <input type="text" value="${theAdmin.username}" class="form-control" disabled>
                </div>
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" name="email" value="${theAdmin.email}" class="form-control" required>
                </div>
                <div class="form-group">
                    <label>Full Name </label>
                    <input type="text" name="name" value="${theAdmin.name}" class="form-control" required>
                </div>
                <div class="form-group">
                    <label>Phone </label>
                    <input type="number" name="phone" value="${theAdmin.phone}" pattern="[0-9]{10}" class="form-control"
                           required>
                </div>
                <div class="form-group">
                    <p style="font-size: 16px"><a href="#" data-toggle="modal"
                                                  data-target="#addAdmin"
                                                  class="btn btn-danger btn-outline">Update</a></p>
                </div>
                <%=printAlert.getSubmitBox("addAdmin", "Confirm Updation ?", "Select \"Confirm\" below to update profile ")%>

            </form>
        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->


<%@include file="templates/footer.jsp" %>