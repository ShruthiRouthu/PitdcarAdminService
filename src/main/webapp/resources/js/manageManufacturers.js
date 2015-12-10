(function ($, window, document) {
    $( function(){
        
        // properties
        
        var controllerName = "ManufacturerController";
        
        var $document = $(document);
        var $body = $('body');
        var $table = $('table');
        var $tableBody = $('#tableBody');
        var $addEditDiv = $('#addEditDiv');
        var addButton = false; // boolean
       
        
        hideAddForm();    
        findAllManufacturers();     
       
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
            $('tbody tr').remove(); //to avoid duplicate entries
            $.each(manufacturers, function (index, manufacturer) {
                var row = '<tr class="manufacturerListRow">' +
                        '<td>' + manufacturer.manufacturerId + '</td>' +
                        '<td>' + manufacturer.manufacturerName + '</td>' +
                        '<td>' + manufacturer.address1 + '</td>' +
                        '<td>' + manufacturer.address2 + '</td>' +
                        '<td>' + manufacturer.city + '</td>' +
                        '<td>' + manufacturer.state + '</td>' +
                        '<td>' + manufacturer.zipcode + '</td>' +
                        '<td>' + manufacturer.phone + '</td>' +
                        '<td>' + '<button class="editBtn" value="' + manufacturer.manufacturerId + '">edit</button></td>' +
                        '<td>' + '<button class="deleteBtn" value="' + manufacturer.manufacturerId + '">delete</button></td>' +
                        '</tr>';
                $tableBody.append(row);
            });
            if(!addButton){             
                $table.append('<br><button id="showAddFormBtn">Add Manufacturer</button></td></tr>'); 
                addButton = true;           
            }
        }
   
        // Delete event
        $tableBody.on('click', '.deleteBtn', function(){
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
        
        $table.on('click' ,'#showAddFormBtn',function(){
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
        }
      
        $tableBody.on('click', '.editBtn', function(){
            alert('edit click working');
            var clickedManufacturerId = $(this).val();
            $.ajax({
                type: 'GET',
                url: controllerName + "?action=findByIdAjax&manufacturerId=" + clickedManufacturer ,
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
        }
        
        $addEditDiv.on('click', '#addManufacturer', function () {
            alert("Save manufacturer click working");
            var clickedManufacturerId = $('#manufacturerId').val();
            if(clickedManufacturerId === 0){
                alert("its add");
                $.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    url: controllerName + "manufacturers",
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
                    type: 'GET',
                    contentType: 'application/json',
                    url: controllerName + "manufacturers/" + clickedManufacturerId ,
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


   