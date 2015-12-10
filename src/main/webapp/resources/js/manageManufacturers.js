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
       
        $addEditDiv.hide();
            
        findAllManufacturers();     
        
       
        
        function findAllManufacturers() {
            alert('Getting All Manufacturers from Controller');
            $.ajax({
                    type: 'GET',
                    url: controllerName + "?action=listAjax",
                    success: function (manufacturers) {
                        alert("success");
                        displayManufacturers(manufacturers);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert("Could not get manufacturers for this user due to: " + errorThrown.toString());
                    }
            });
            
        } 
        
        // function to display manufacturers received from controller
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
                        '<td align="center" class="editBtn" value="'+ manufacturer.manufacturerId +'"><span class="glyphicon glyphicon-pencil" aria-hidden="true" ></span></td>' +
                        '<td align="center" class="deleteBtn" value="'+ manufacturer.manufacturerId +'"></td>' +
                        '</tr>';
                $tableBody.append(row);
            });
            if(!addButton){             
                $table.append('<br><button id="showAddFormBtn">Add Author</button></td></tr>'); 
                addButton = true;           
            }
        }
   
        // Delete event
        $tableBody.on('click', '.deleteBtn', function(){
            alert('click working');
            var clickedManufacturer = $(this).val();
            alert('click working ' + clickedManufacturer);
            $.ajax({
                type: 'GET',
                url: controllerName + "?action=deleteAjax&manufacturerId=14", // + clickedManufacturer ,<span class="glyphicon glyphicon-trash" aria-hidden="true" ></span>
                success: function (jqXHR, textStatus, errorThrown) {
                    alert('Manufacturer deleted successfully ' + clickedManufacturer);
                    findAllManufacturers();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert('Manufacturer deletion error' + textStatus);
                }
            }); 
        });
        
        
    });
    
}(window.jQuery, window, document));


   