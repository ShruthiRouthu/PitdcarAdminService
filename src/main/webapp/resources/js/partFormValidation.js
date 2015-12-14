(function ($, window, document) {
    $( function(){
 
        $("#editForm").validate({
            rules: {
                partImage: {
                    url: true
                }
            }

        });
        
       
    });
    
}(window.jQuery, window, document));

