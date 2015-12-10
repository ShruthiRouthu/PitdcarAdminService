<%-- 
    Document   : index
    Created on : Oct 17, 2015, 11:34:15 AM
    Author     : Shruthi Routhu 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    </head>
    <body>
        
        
        <h2 style="text-align:center">PitDCar Admin Page</h1> 
        
        <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')"> 
                   
            <h4 style="margin:5px">Administrative tasks : </h4>
            <ol>
                <li><a href="ManufacturerController?action=showListPage">View all Manufacturers</a></li>
                <li><a href="ManufacturerController?action=showManagePage">Manage Manufacturers</a></li>
                <li><a href="PartController?action=showListPage">View all Parts</a></li>
                <li><a href="PartController?action=showManagePage">Manage Parts</a></li> 
                <li><a href="manage-manufacturers-ajax.jsp">Manage Parts AJAX</a></li>
            </ol>
        
         </sec:authorize> 
        
        <p style="text-align:center; margin:30px; color:red"> ${admin_message}</p>
            
        <br><br>    
             
        <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
            Logged in as: <sec:authentication property="principal.username"></sec:authentication> ::
            <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
        </sec:authorize>     
     
        
        <script src="https://code.jquery.com/jquery-2.1.4.min.js"> </script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js">  </script>
    </body>
</html>

