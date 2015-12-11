(function ($, window, document) {
    $( function(){
        
        // properties
        
        var controllerName = "ManufacturerController";
        
        var $document = $(document);
        var $body = $('body');
        var $tableDiv = $("#tableDiv");
        var $table = $('table');
        var $tableBody = $('#tableBody');
        var $addEditDiv = $('#addEditDiv');
        var addButton = false; // boolean
       
        
        hideAddForm();    
        findAllManufacturers();  
        //  showAddForm();
       
        function findAllManufacturers() {
            alert('Finding Manufacturers');
            $.ajax({
                    type: 'GET',
                    url: controllerName + "?action=listAjax",
                    success: function (manufacturers) {
                        alert("successfully found manufacturers");
                        displayManufacturers(manufacturers);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert("Could not get manufacturers for this user due to: " + errorThrown.toString());
                    }
            });
            
        } 
        
        function displayManufacturers(manufacturers) {
            alert("Displaying manufacturers");
            $tableDiv.show();
            $('tbody tr').remove(); //to avoid duplicate entries
            $.each(manufacturers, function (index, manufacturer) {
                var row = '<tr class="manufacturerListRow">' +
                        '<td align="center">' + manufacturer.manufacturerId + '</td>' +
                        '<td align="left">' + manufacturer.manufacturerName + '</td>' +
                        '<td align="left">' + manufacturer.address1 + '</td>' +
                        '<td align="left">' + manufacturer.address2 + '</td>' +
                        '<td align="left" >' + manufacturer.city + '</td>' +
                        '<td align="center">' + manufacturer.state + '</td>' +
                        '<td align="center">' + manufacturer.zipcode + '</td>' +
                        '<td align="center">' + manufacturer.phone + '</td>' +
                        '<td align="center">' + '<button id="editBtn"  class="btn btn-default" value="' + manufacturer.manufacturerId + '"><span class="glyphicon glyphicon-pencil" aria-hidden="true" ></span></button></td>' +
                        '<td align="center">' + '<button id="deleteBtn" class="btn btn-default" value="' + manufacturer.manufacturerId + '"><span class="glyphicon glyphicon-trash" aria-hidden="true" ></span></button></td>' +
                        '</tr>';
                $tableBody.append(row);
            });
            if(!addButton){             
                $tableDiv.append('<br><button id="showAddFormBtn" class="btn btn-primary">Add New</button></td></tr>'); 
                addButton = true;           
            }
        }
   
        // Delete event
        $tableBody.on('click', '#deleteBtn', function(){
            var clickedManufacturer = $(this).val();
            alert('click working ' + clickedManufacturer);
            $.ajax({
                type: 'GET',
                url: controllerName + "?action=deleteAjax&manufacturerId=" + clickedManufacturer ,
                success: function (jqXHR, textStatus, errorThrown) {
                    alert('Manufacturer deleted successfully ' + clickedManufacturer);
                    findAllManufacturers();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert('Manufacturer deletion error' + textStatus);
                }
            }); 
        });
        
        function hideAddForm(){
            alert('hiding add form');
            $addEditDiv.hide();
        }
        
        $tableDiv.on('click' ,'#showAddFormBtn',function(){
            alert("Click working");
            showAddForm();
        });
      
        function showAddForm() {
            alert('show add form');
            $('#manufacturerName').val("");
            $('#address1').val("");
            $('#address2').val("");
            $('#city').val("");
            $('#state').val("");
            $('#zipcode').val("");
            $('#phone').val("");
            $('#manufacturerId').val(0);
            $('#manufacturerId').hide();
            $addEditDiv.show();
            $tableDiv.hide();
            
        }
      
        $tableBody.on('click', '#editBtn', function(){
            alert('edit click working');
            var clickedManufacturerId = $(this).val();
            $.ajax({
                type: 'GET',
                url: controllerName + "?action=findByIdAjax&manufacturerId=" + clickedManufacturerId ,
                dataType: 'json',
                success: showEditForm
            });
            
        });
 
        function showEditForm(manufacturer) {
           alert(manufacturer.manufacturerId + "filling form");
           $('#manufacturerId').val(manufacturer.manufacturerId);
           $('#manufacturerId').hide();
           $('#manufacturerName').val(manufacturer.manufacturerName);
           $('#address1').val(manufacturer.address1);
           $('#address2').val(manufacturer.address2);
           $('#city').val(manufacturer.city);
           $('#state').val(manufacturer.state);
           $('#zipcode').val(manufacturer.zipcode);
           $('#phone').val(manufacturer.phone);
           $addEditDiv.show();
           $tableDiv.hide();
        }
        
        $addEditDiv.on('click', '#addManufacturer', function () {
            alert("Save manufacturer click working");
            var clickedManufacturerId = $('#manufacturerId').val();
            if(clickedManufacturerId == 0){
                alert("its add");
                $.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    url: controllerName + "?action=addAjax",
                    dataType: "json",
                    data: formToJSON(),
                    success: function (data, textStatus, jqXHR) {
                        alert('Manufacturer added successfully');
                        findAllManufacturers();
                        hideAddForm();  
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        console.log(jqXHR.status);
                        // Fix a bug in JQuery: 201 means the insert succeeded!
                        if (jqXHR.status === 201 || jqXHR.status === 200) {
                            alert('manufacturer added successfully');
                            findAllManufacturers();
                            hideAddForm();
                        }
                        else {
                            alert('addManufacturer error:  ' + textStatus);
                        }
                    }
                }); 
            }
            else{
                alert("its edit url : " +controllerName + "manufacturers/" + clickedManufacturerId );
                $.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    url: controllerName + "?action=editAjax" ,
                    dataType: "html",
                    data: formToJSONEdit(),
                    success: function (data, textStatus, jqXHR) {
                        alert('Manufacturer updated successfully');
                        findAllManufacturers();
                        hideAddForm();
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert('updateManufacturer error: ' + textStatus);
                    }
                });
            }
        });
                
        // Helper functions to serialize all the form fields into a JSON string
        function formToJSON() {
            return JSON.stringify({
                "manufacturerId": "-1",
                "manufacturerName": $('#manufacturerName').val(),
                "address1": $('#address1').val() ,  
                "address2": $('#address2').val() , 
                "city": $('#city').val() ,
                "state": $('#state').val() ,
                "zipcode": $('#zipcode').val() ,
                "phone": $('#phone').val() 
            });
        }
        
        function formToJSONEdit() {
              return JSON.stringify({
                "manufacturerId": $('#manufacturerId').val(),  
                "manufacturerName": $('#manufacturerName').val(),
                "address1": $('#address1').val() ,  
                "address2": $('#address2').val() , 
                "city": $('#city').val() ,
                "state": $('#state').val() ,
                "zipcode": $('#zipcode').val() ,
                "phone": $('#phone').val() 
            });
        }
        
        
    });
    
}(window.jQuery, window, document));


   