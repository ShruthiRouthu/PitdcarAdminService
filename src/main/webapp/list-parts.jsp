<%-- 
    Document   : list-parts
    Created on : Oct 17, 2015, 11:34:59 AM
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
        <title>Part List</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    </head>
    <body>
        <p style="text-align:right; margin:25px 25px 0px 25px"> ${user_name} </p>
        <h1 style="text-align:center">Part List</h1>
        
        <a href="PartController?action=showHomePage" style="margin-bottom: 25px; margin-left:5px">Home</a>
        <div class="table-responsive" style="margin: 25px">
        <table class="table table-striped table-bordered">
            
            <tr style="background-color: #1E90FF; color:FFFFF0;">               
                <th align="left" >ID</th>
                
                <th align="left" >Name</th>
                <th align="left" >Description</th>
                <th align="left" >Manufacturer</th>
                <th align="right" >SalePrice</th>
                <th align="right" >Qty</th>
                <th align="left" >ImageURL</th>
            </tr>
            
                <c:forEach var="p" items="${parts}" varStatus="rowCount">
                    <c:choose>
                        <c:when test="${rowCount.count % 2 == 0}">
                            <tr style="background-color: white;">
                        </c:when>
                        <c:otherwise>
                        <tr style="background-color: #FFFFF0">
                        </c:otherwise>
                    </c:choose>          
                                <td align="left">${p.partId}</td>
                                
                                <td align="left">${p.partName}</td>
                                <td align="left">${p.partDescription}</td>
                                <td align="left">${p.manufacturer}</td>
                                <td align="right">$${p.salePrice}</td>
                                <td align="right">${p.qty}</td>
                                <td align="left">${p.partImage}</td>
                            </tr>
                </c:forEach>
                            
        </table>
        </div>
        
             
        <c:if test="${parts == null}">
            <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br> </p>
        </c:if>
      
        <p style="text-align:center; margin:30px; color:red"> ${admin_message}<p>    
            
        <script src="https://code.jquery.com/jquery-2.1.4.min.js"> </script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js">  </script>    
    </body>
</html>
