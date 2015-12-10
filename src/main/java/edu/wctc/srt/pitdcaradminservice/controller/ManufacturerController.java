package edu.wctc.srt.pitdcaradminservice.controller;

import edu.wctc.srt.pitdcaradminservice.entity.Manufacturer;
import edu.wctc.srt.pitdcaradminservice.service.ManufacturerService;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.SQLException;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Shruthi Routhu
 */

// Not using annotations bcause this servlet is configured using web.xml
public class ManufacturerController extends HttpServlet {
    
    // PARAMETER constants
    private static final String PARAM_ACTION = "action";
    private static final String PARAM_MANUFACTURERID = "manufacturerId";
    private static final String PARAM_MANUFACTURER_NAME = "manufacturerName";
    private static final String PARAM_ADDRESS1 ="address1";  
    private static final String PARAM_ADDRESS2="address2";
    private static final String PARAM_CITY="city";
    private static final String PARAM_STATE = "state";
    private static final String PARAM_ZIPCODE= "zipcode"; 
    private static final String PARAM_PHONE = "phone";
    
    //JNDI NAME
    private static final String JNDI_NAME= "jdbc/pitdcar";     
            
    // ACTION constants
    private static final String ACTION_LIST_PAGE = "showListPage";
    private static final String ACTION_MANAGE_PAGE = "showManagePage";
    private static final String ACTION_EDIT_PAGE = "showEditPage";
    private static final String ACTION_HOME_PAGE = "showHomePage";
    private static final String ACTION_EDIT = "edit";
    private static final String ACTION_DELETE="delete";
    private static final String ACTION_ADD="add";
    private static final String AJAX_LIST_ACTION = "listAjax";
    private static final String AJAX_DELETE_ACTION = "deleteAjax";
    private static final String AJAX_FINDBY_ID = "findByIdAjax";
    
    // PAGE URL constants
    private static final String PAGE_LIST = "/list-manufacturers.jsp";
    private static final String PAGE_MANAGE = "/manage-manufacturers.jsp";
    private static final String PAGE_HOME = "/index.jsp";
    private static final String PAGE_EDIT = "/edit-manufacturer.jsp";
    private static final String PAGE_ERROR = "/error-page.jsp";
    
    // ATTRIBUTE constants
    private static final String ATTRIBUTE_MANUFACTURERS = "manufacturers" ;
    private static final String ATTRIBUTE_SELECTED_MANUFACTURER = "selectedManufacturer";
    
 

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        /*
            Injecting Spring Service object.
        */
        ServletContext sctx = getServletContext();
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sctx);
        ManufacturerService manufacturerService = (ManufacturerService) ctx.getBean("manufacturerService");
        PrintWriter out = response.getWriter();
        
        String action = request.getParameter(PARAM_ACTION);
        
        String destination = "";
        int manufacturerId;
        
        try{
            
            switch(action){
                
                case  AJAX_LIST_ACTION :
                    
                    List<Manufacturer> manufacturers = manufacturerService.findAll();
                        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

                    for(Manufacturer m: manufacturers ){
                        jsonArrayBuilder.add(
                                Json.createObjectBuilder()
                                .add(PARAM_MANUFACTURERID, m.getManufacturerId().toString())
                                .add(PARAM_MANUFACTURER_NAME, m.getManufacturerName())
                                .add(PARAM_ADDRESS1, m.getAddress1())
                                .add(PARAM_ADDRESS2, m.getAddress2())
                                .add(PARAM_CITY, m.getCity())
                                .add(PARAM_STATE, m.getState())
                                .add(PARAM_ZIPCODE, m.getZipcode())
                                .add(PARAM_PHONE, m.getPhone())
                        );
                    }

                    JsonArray manufacturersJson = jsonArrayBuilder.build();
                    response.setContentType("application/json");
                    out.write(manufacturersJson.toString());
                    out.flush();
                    return; // must not continue at bottom!
                    
                case AJAX_DELETE_ACTION :
                    manufacturerId = getParameterManufacturerId(request);
                    if(manufacturerId != -1){
                        Manufacturer deleteManufacturer = manufacturerService.findById(Integer.toString(manufacturerId));
                        manufacturerService.remove(deleteManufacturer);
                    }
                    return; // must not continue at bottom!
                                  
                case ACTION_HOME_PAGE :
                    destination = PAGE_HOME;
                    break;
                    
                case ACTION_LIST_PAGE : 
                    resetManuList(request,manufacturerService);
                    destination = PAGE_LIST;
                    break;
                    
                case ACTION_MANAGE_PAGE :
                    resetManuList(request,manufacturerService);
                    destination = PAGE_MANAGE;
                    break;
                
                case ACTION_EDIT_PAGE :
                    manufacturerId = getParameterManufacturerId(request);
                    Manufacturer manufacturer = manufacturerService.findById(Integer.toString(manufacturerId));
                    request.setAttribute(ATTRIBUTE_SELECTED_MANUFACTURER, manufacturer);
                    destination = PAGE_EDIT;
                    break;
                    
                case ACTION_DELETE :
                    manufacturerId = getParameterManufacturerId(request);
                    if(manufacturerId != -1){
                        Manufacturer deleteManufacturer = manufacturerService.findById(Integer.toString(manufacturerId));
                        manufacturerService.remove(deleteManufacturer);
                    }
                    resetManuList(request,manufacturerService);
                    destination = PAGE_MANAGE;
                    break;    
                
                case ACTION_EDIT : 
                case ACTION_ADD :    
                    manufacturerId = getParameterManufacturerId(request);
                
                    String manufacturerName  = request.getParameter(PARAM_MANUFACTURER_NAME);                                   
                    String address1  = request.getParameter(PARAM_ADDRESS1);
                    String address2  = request.getParameter(PARAM_ADDRESS2);
                    String city  = request.getParameter(PARAM_CITY);
                    String state = request.getParameter(PARAM_STATE);
                    String zipcode = request.getParameter(PARAM_ZIPCODE);
                    String phone = request.getParameter(PARAM_PHONE);
                                  
                    // Logic to decide between INSERT or UPDATE
                    if(manufacturerId == -1){ 
                       Manufacturer newManufacturer = new Manufacturer(0);
                       
                       newManufacturer.setManufacturerName(manufacturerName);
                       newManufacturer.setAddress1(address1);
                       newManufacturer.setAddress2(address2);
                       newManufacturer.setCity(city);
                       newManufacturer.setState(state);
                       newManufacturer.setZipcode(zipcode);
                       newManufacturer.setPhone(phone);
                       
                       manufacturerService.edit(newManufacturer);
                    }
                    else{
                       Manufacturer editManufacturer = manufacturerService.findById(Integer.toString(manufacturerId));
                       
                       editManufacturer.setManufacturerName(manufacturerName);
                       editManufacturer.setAddress1(address1);
                       editManufacturer.setAddress2(address2);
                       editManufacturer.setCity(city);
                       editManufacturer.setState(state);
                       editManufacturer.setZipcode(zipcode);
                       editManufacturer.setPhone(phone);
                       
                       manufacturerService.edit(editManufacturer); 
                    }
                    
                    resetManuList(request,manufacturerService);
                    destination = PAGE_MANAGE;
                    break;    
                    
              
                
            }
            
        }catch(Exception e){
               destination = PAGE_ERROR ; 
               System.out.println(e.getMessage());
        }
      //  finally{    
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination);
            dispatcher.forward(request, response);
      //  }
        
    
     
    }
    
    private int getParameterManufacturerId(HttpServletRequest request) {
        int manufacturerId = -1; //flag
        String temp = request.getParameter(PARAM_MANUFACTURERID);
        if (temp != null) {
            manufacturerId = Integer.parseInt(temp);
        }
        return manufacturerId;
    }
    
    private void resetManuList(HttpServletRequest request,ManufacturerService manuService) throws SQLException,Exception{
        List<Manufacturer> manufacturers = manuService.findAll(); 
        request.setAttribute(ATTRIBUTE_MANUFACTURERS ,manufacturers);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
