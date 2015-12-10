<%-- 
    Document   : manage-manufacturers
    Created on : Dec 5, 2015, 7:20:37 PM
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
        <title>Manage Manufactures</title>
         <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    </head>
    <body>
      <h1 style="text-align:center">Manufacturer List</h1>
        
        <a href="ManufacturerController?action=showHomePage" style="margin-bottom: 25px; margin-left:5px">Home</a>
        
        <sec:authorize access="hasAnyRole('ROLE_MGR')">
            <div class="table-responsive" style="margin: 25px">
            <table class="table table-striped table-bordered">

                <tr style="background-color: #1E90FF; color:FFFFF0;">               
                    <th align="left" >ID</th>
                    <th align="left" >Name</th>
                    <th align="left" >Street Address1 </th>
                    <th align="left" >Street Address2</th>
                    <th align="left" >City</th>
                    <th align="left" >State</th>
                    <th align="left" >Zip</th>
                    <th align="left" >Phone</th>
                    <th align="center" >Edit</th>
                    <th align="center" >Delete</th>
                </tr>

                    <c:forEach var="m" items="${manufacturers}" varStatus="rowCount">
                        <c:choose>
                            <c:when test="${rowCount.count % 2 == 0}">
                                <tr style="background-color: white;">
                            </c:when>
                            <c:otherwise>
                            <tr style="background-color: #FFFFF0">
                            </c:otherwise>
                        </c:choose>          
                                    <td align="left">${m.manufacturerId}</td>

                                    <td align="left">${m.manufacturerName}</td>
                                    <td align="left">${m.address1}</td>
                                    <td align="left">${m.address2}</td>
                                    <td align="left">${m.city}</td>
                                    <td align="left">${m.state}</td>
                                    <td align="left">${m.zipcode}</td>
                                    <td align="left">${m.phone}</td>
                                    <td align="center" onclick="location.href='ManufacturerController?action=showEditPage&manufacturerId=${m.manufacturerId}'">
                                        <span class="glyphicon glyphicon-pencil" aria-hidden="true" ></span></td>
                                    <td align="center" onclick="location.href='ManufacturerController?action=delete&manufacturerId=${m.manufacturerId}'">
                                        <span class="glyphicon glyphicon-trash" aria-hidden="true" ></span></td> 
                                </tr>
                    </c:forEach>

            </table>

            <br>
            <input type="button"  name="add"  value="Add" onclick="location.href='ManufacturerController?action=showEditPage'" >
            <br>

            </div>


            <c:if test="${manufacturers == null}">
                <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br> </p>
            </c:if>
      
        </sec:authorize>
        
        <br>    
             
        <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
            Logged in as: <sec:authentication property="principal.username"></sec:authentication> ::
            <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
        </sec:authorize>   
            
        <script src="https://code.jquery.com/jquery-2.1.4.min.js"> </script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js">  </script>   
    </body>
</html>
