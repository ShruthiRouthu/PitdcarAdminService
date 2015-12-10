<%-- 
    Document   : manage-manufacturers-ajax
    Created on : Dec 8, 2015, 1:16:55 PM
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
        <link href="resources/css/myStyles.css" rel="stylesheet" type="text/css"/>
    </head>
    <body class="manufacturerList">
        
        <h1 style="text-align:center">Manufacturer List</h1>
        
        <sec:authorize access="hasAnyRole('ROLE_MGR')">
            
            <div class="table-responsive" style="margin: 25px">
                <table class="table table-striped table-bordered">
                    <thead>
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
                    </thead>
                    
                    <tbody id="tableBody">  </tbody>

                </table>
                
                  
            </div>     
 
            <div id="addEditDiv"  style="display:none;" >
                <form style="margin: 25px" class="form-horizontal">
                    <c:set var="part" scope="page" value=""/> 

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

                </form> 
           
            </div>
                    
        </sec:authorize>
            
        <br>
        <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
            Logged in as: <sec:authentication property="principal.username"></sec:authentication> <br>
            <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
        </sec:authorize>
            
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" type="text/javascript"></script>    
        <script src="resources/js/manageManufacturers.js" type="text/javascript"></script>    
    </body>
</html>
