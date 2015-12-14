<%-- 
    Document   : edit-part
    Created on : Oct 17, 2015, 7:10:54 PM
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
        <title>Add/Edit Part</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    </head>
    <body>
        
        <h1 style="text-align:center">Add/Edit Part</h1>
        
        <a href="PartController?action=showHomePage" style="margin-bottom: 25px; margin-left:5px">Home</a>
        
        <form id="editForm" name="editForm" method="POST" action="PartController?action=edit" style="margin: 25px" class="form-horizontal">
          
           <sec:csrfInput />
            
           <sec:authorize access="hasAnyRole('ROLE_MGR')">  
         
               <c:set var="part" scope="page" value="${selectedPart}"/> 

                <c:choose>
                        <c:when test="${not empty selectedPart}">
                            <input type="hidden" name="partId" id="partId" value="${selectedPart.partId}" >        
                        </c:when>
                </c:choose>


               <div class="form-group">
                <label for="partName">Name:  </label>
                <input  class="form-control" id="partName" name="partName" type="text" value="${selectedPart.partName}" required>
               </div>

               <div class="form-group">
                <label for="partDescription">Description:  </label>
                <input  class="form-control" id="partDescription" name="partDescription" type="text" 
                        value="${selectedPart.partDescription}" required>
               </div>

            <!--   <div class="form-group">
                <label for="manufacturer">Manufacturer:  </label>
                <input  class="form-control" id="manufacturer" name="manufacturer" type="text" value="${selectedPart.manufacturerId.manufacturerName}" required >
               </div> -->
               
               <div class="form-group">
                    <label for="manufacturerDD">Manufacturer:  </label>
                    <select id="manufacturerDD" name="manufacturerId">
                        <c:choose>
                            <c:when test="${not empty selectedPart.manufacturerId}">
                                <option value="-1">None</option> 
                                <c:forEach var="manufacturer" items="${manufacturers}">                                       
                                    <option value="${manufacturer.manufacturerId}" <c:if test="${selectedPart.manufacturerId.manufacturerId == manufacturer.manufacturerId}">selected</c:if>>${manufacturer.manufacturerName}</option>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <option value="-1" selected>None</option>
                                <c:forEach var="manufacturer" items="${manufacturers}" varStatus="rowCount">                                       
                                    <option value="${manufacturer.manufacturerId}">${manufacturer.manufacturerName}</option>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </select>

                </div>
               
               

               <div class="form-group">
                <label for="partImage">Image URL:  </label>
                <input  class="form-control" id="partImage" name="partImage" type="url" value="${selectedPart.partImage}" required>
               </div>

               <div class="form-group">
                <label for="salePrice">Sale Price:  </label>
                <input  class="form-control" id="salePrice" name="salePrice" type="number"
                        value="${selectedPart.salePrice}" step="0.01" min="0" required>
               </div>

               <div class="form-group">
                <label for="qty">Quantity:  </label>
                <input  class="form-control" id="qty" name="qty" type="number" value="${selectedPart.qty}" min="0" required >
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
        <script src="http://cdn.jsdelivr.net/jquery.validation/1.14.0/jquery.validate.min.js"></script>
        <script src="http://cdn.jsdelivr.net/jquery.validation/1.14.0/additional-methods.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js">  </script>
        <script src="resources/js/partFormValidation.js" type="text/javascript"></script>
    </body>
</html>
