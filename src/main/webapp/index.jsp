<%-- 
    Document   : index
    Created on : Oct 17, 2015, 11:34:15 AM
    Author     : Shruthi Routhu 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
      
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    </head>
    <body>
        
        <p style="text-align:right; margin:25px 25px 0px 25px"> ${user_name} </p>
        <h2 style="text-align:center">PitDCar Admin Page</h1> 
        
        
        <form style="margin: 25px" action="PartController?action=showHomePage" method="POST" class="form-horizontal" >
            
            
            <div class="form-group">
                <label for="user_name">  User Name : </label>
                <input  class="form-control" id="user_name" name="user_name" type="text" value="" placeholder="session" required>
            </div>
            
           <div class="form-group">
                <label for="admin_message">  Admin Message:  </label>
                <input  class="form-control" id="admin_message" name="admin_message" type="text" value="" placeholder="application" >
           </div>
            
            <input type="submit" value="Submit" class="btn btn-primary" >
        </form>
       
        <h4 style="margin:5px">Administrative tasks : </h4>
        <ol>
            <li><a href="PartController?action=showListPage">View all Parts</a></li>
            <li><a href="PartController?action=showManagePage">Manage Parts</a></li>
        </ol>
        
        <p style="text-align:center; margin:30px; color:red"> ${admin_message}<p>
            
            
     
        
        <script src="https://code.jquery.com/jquery-2.1.4.min.js"> </script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js">  </script>
    </body>
</html>

