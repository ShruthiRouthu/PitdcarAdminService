<%-- 
    Document   : edit-manufacturers
    Created on : Dec 5, 2015, 7:20:55 PM
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
        <title>Add/Edit Manufacturers</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    </head>
    <body>
        
        <h1 style="text-align:center">Add/Edit Part</h1>
        
        <a href="PartController?action=showHomePage" style="margin-bottom: 25px; margin-left:5px">Home</a>
        
        <form id="editForm" name="editForm" method="POST" action="ManufacturerController?action=edit" style="margin: 25px" class="form-horizontal">
            
           <sec:csrfInput />
            
           <sec:authorize access="hasAnyRole('ROLE_MGR')"> 
         
               <c:set var="part" scope="page" value="${selectedManufacturer}"/> 

                <c:choose>
                        <c:when test="${not empty selectedManufacturer}">
                            <input type="hidden" name="manufacturerId" id="manufacturerId" value="${selectedManufacturer.manufacturerId}" >        
                        </c:when>
                </c:choose>

               <div class="form-group">
                <label for="manufacturerName">Name:  </label>
                <input  class="form-control" id="manufacturerName" name="manufacturerName" type="text" value="${selectedManufacturer.manufacturerName}" required>
               </div>

               <div class="form-group">
                <label for="address1">Street Address1:  </label>
                <input  class="form-control" id="address1" name="address1" type="text" 
                        value="${selectedManufacturer.address1}" required>
               </div>

               <div class="form-group">
                <label for="address2">Street Address2:  </label>
                <input  class="form-control" id="address2" name="address2" type="text" value="${selectedManufacturer.address2}"  >
               </div>

               <div class="form-group">
                <label for="city">City:  </label>
                <input  class="form-control" id="city" name="city" type="text" value="${selectedManufacturer.city}" required>
               </div>

               <div class="form-group">
                <label for="state">State:  </label>
                <input  class="form-control" id="state" name="state" type="text" value="${selectedManufacturer.state}" required>
               </div>

               <div class="form-group">
                <label for="zipcode">Zip:  </label>
                <input  class="form-control" id="zipcode" name="zipcode" type="text" value="${selectedManufacturer.zipcode}" required>
               </div>

               <div class="form-group">
                <label for="phone">Phone:  </label>
                <input  class="form-control" id="phone" name="phone" type="text" value="${selectedManufacturer.phone}" required>
               </div>

               <button type="submit" class="btn btn-primary">Save changes</button>
           
        
           </sec:authorize>  
        
        </form> 
          
        <br><br>    
             
        <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
            Logged in as: <sec:authentication property="principal.username"></sec:authentication> ::
            <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
        </sec:authorize>     
        
        <script src="https://code.jquery.com/jquery-2.1.4.min.js"> </script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js">  </script>
    </body>
</html>
