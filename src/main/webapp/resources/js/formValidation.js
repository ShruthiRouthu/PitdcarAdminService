(function ($, window, document) {
    $( function(){
 
        $("#editForm").validate({
            rules: {
                zipcode: {
                    zipcodeUS: true
                },
                phone: {
                    phoneUS: true
                }
            }

        });
    });
    
}(window.jQuery, window, document));