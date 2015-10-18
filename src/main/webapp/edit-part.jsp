<%-- 
    Document   : edit-part
    Created on : Oct 17, 2015, 7:10:54 PM
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
        <title>Edit Part</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    </head>
    <body>
        <h1 style="text-align:center">Edit Part</h1>
        
        
        <form id="editForm" name="editForm" method="POST" action="PartController?action=edit" style="margin: 25px">
            
         
           <c:set var="part" scope="page" value="${selectedPart}"/> 
           <c:set var="eff_date" scope="page" value="<%<fmt:formatDate pattern='yyyy-MM-dd' value='${part.eff_date}'%>" />
           
           <input type="hidden" name="partID" id="partID" value="${selectedPart.part_id}" >

           <div class="form-group">
            <label for="part_name">Name:  </label>
            <input  class="form-control" id="part_name" name="part_name" type="text" value="${selectedPart.part_name}" required>
           </div>

          <div class="form-group">
            <label for="eff_date"> Effective Date :  </label>
            <input  class="form-control" id="eff_date" name="eff_date" type="text" value="${selectedPart.eff_date}" required>
          </div>

           <div class="form-group">
            <label for="part_description">Description:  </label>
            <input  class="form-control" id="part_description" name="part_description" type="text" 
                    value="${selectedPart.part_description}" required>
           </div>

           <div class="form-group">
            <label for="manufacturer">Manufacturer:  </label>
            <input  class="form-control" id="manufacturer" name="manufacturer" type="text" value="${selectedPart.manufacturer}" required >
           </div>

           <div class="form-group">
            <label for="part_image">Image URL:  </label>
            <input  class="form-control" id="part_image" name="part_image" type="text" value="${selectedPart.part_image}" required>
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
        
        </form>
        
        
        
        <script src="https://code.jquery.com/jquery-2.1.4.min.js"> </script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js">  </script>
    </body>
</html>
