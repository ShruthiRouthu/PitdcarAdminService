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
    </head>
    <body>
        
        <h1>PitDCar Admin</h1> 
       
        <ol>
            <li><a href="PartController?action=showListPage">View all Parts</a></li>
            <li><a href="PartController?action=showManagePage">Manage Parts</a></li>
        </ol>
    </body>
</html>
