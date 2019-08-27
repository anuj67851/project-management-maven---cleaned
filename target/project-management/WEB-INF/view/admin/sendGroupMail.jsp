<%@ page import="com.pms.utility.printAlert" %>
<%@include file="templates/header.jsp" %>
<%@include file="templates/top-side-nav.jsp" %>
<div id="page-wrapper">
    <div class="container-fluid">


        <div class="col-md-6">
            <div class="row">
                <h1 class="page-header" style="display: inline">
                    <br>Group Mail
                </h1>
            </div>
            <br>

            <form action="sendGrpMail" method="post">
                <div class="form-group">
                    <label>Receivers(separated by semicolon(;))</label>
                    <textarea name="receivers" cols="30" rows="5" class="form-control"
                              required>${receivers}</textarea>
                </div>

                <div class="form-group">
                    <label>Subject</label>
                    <input type="text" name="subject" class="form-control" required>
                </div>
                <div class="form-group">
                    <label>Body</label>
                    <textarea name="body" cols="30" rows="10" class="form-control"
                              required></textarea>
                </div>
                <div class="form-group">
                    <p style="font-size: 16px"><a href="#" data-toggle="modal"
                                                  data-target="#ann"
                                                  class="btn btn-primary btn-outline">Send</a></p>
                </div>
                <%=printAlert.getSubmitBox("ann", "Confirm Group Mail ?", "Select \"Confirm\" send the mail to selected students. ")%>

            </form>
        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->


<%@include file="templates/footer.jsp" %>