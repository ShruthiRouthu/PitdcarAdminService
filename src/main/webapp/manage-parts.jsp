<%-- 
    Document   : manage-parts.jsp
    Created on : Oct 17, 2015, 12:29:32 AM
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
        <title> Manage Parts </title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    </head>
    <body>
        
        <p style="text-align:right; margin:25px 25px 0px 25px"> ${user_name} </p>
        <h1 style="text-align:center">Manage Parts</h1>
        
        <a href="PartController?action=showHomePage" style="margin-bottom: 25px; margin-left:5px">Home</a>
        <div class="table-responsive" style="margin: 25px">
        <table class="table table-striped table-bordered">
            
            <tr style="background-color: #1E90FF; color:FFFFF0;">               
                <th align="left" >ID</th>
                
                <th align="left" >Name</th>
                <th align="left" >Description</th>
                <th align="left" >Manufaturer</th>
                <th align="right" >SalePrice</th>
                <th align="right" >Qty</th>
                <th align="left" >ImageURL</th>
                <th align="center" >Edit</th>
                <th align="center" >Delete</th>
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
                                <td align="left">${p.part_id}</td>
                                
                                <td align="left">${p.part_name}</td>
                                <td align="left">${p.part_description}</td>
                                <td align="left">${p.manufacturer}</td>
                                <td align="right">$${p.salePrice}</td>
                                <td align="right">${p.qty}</td>
                                <td align="left">${p.part_image}</td>
                                <td align="center" onclick="location.href='PartController?action=showEditPage&partID=${p.part_id}'">
                                    <span class="glyphicon glyphicon-pencil" aria-hidden="true" ></span></td>
                                <td align="center" onclick="location.href='PartController?action=delete&partID=${p.part_id}'">
                                    <span class="glyphicon glyphicon-trash" aria-hidden="true" ></span></td> 
                                
                           
                            </tr>
                </c:forEach>
                            
        </table>
            
            <br>
            <input type="button"  name="addPart"  value="Add" data-toggle="modal" data-target="#addModal" >
            <br>
        </div>
        
        
        
        <c:if test="${parts == null}">
            <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br> </p>
        </c:if>
            
            
            
    <!-- Bootstrap Modal Dialog Boxes -->        
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <form id="addForm" name="addForm" method="POST" action="PartController?action=add" class="form-horizontal">  
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="myModalLabel">Add Part</h4>
          </div>
            <div class="modal-body">
                
                   
                                                               
                   <div class="form-group">
                    <label for="part_name">Name:  </label>
                    <input  class="form-control" id="part_name" name="part_name" type="text" value="" required>
                   </div>

                   <div class="form-group">
                    <label for="part_description">Description:  </label>
                    <input  class="form-control" id="part_description" name="part_description" type="text" value="" required >
                   </div>
                
                   <div class="form-group">
                    <label for="manufacturer">Manufacturer:  </label>
                    <input  class="form-control" id="manufacturer" name="manufacturer" type="text" value="" required>
                   </div>
                
                   <div class="form-group">
                    <label for="part_image">Image URL:  </label>
                    <input  class="form-control" id="part_image" name="part_image" type="text" value="" required >
                   </div>
                
                   <div class="form-group">
                    <label for="salePrice">Sale Price:  </label>
                    <input  class="form-control" id="salePrice" name="salePrice" type="number"
                            value="" step="0.01" min="0" required>
                   </div>
                
                   <div class="form-group">
                    <label for="qty">Quantity:  </label>
                    <input  class="form-control" id="qty" name="qty" type="number" value="" min="0" required >
                   </div> 
                  
                
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary">Add</button>
            </div>
          </form>
        </div>
      </div>
    </div>    
        
       
        <p style="text-align:center; margin:30px; color:red"> ${admin_message}<p>
        
        <script src="https://code.jquery.com/jquery-2.1.4.min.js"> </script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js">  </script>
    </body>
</html>
