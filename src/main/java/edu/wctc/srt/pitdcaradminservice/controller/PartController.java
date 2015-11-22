
package edu.wctc.srt.pitdcaradminservice.controller;


import edu.wctc.srt.pitdcaradminservice.entity.Part;
import edu.wctc.srt.pitdcaradminservice.service.PartFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author Shruthi Routhu
 */

// Not using annotations bcause this servlet is configured using web.xml
public class PartController extends HttpServlet {
    
    // PARAMETER constants
    private static final String PARAM_ACTION = "action";
    private static final String PARAM_PARTID = "partID";
    private static final String PART_ID = "part_id";
    private static final String PARAM_EFF_DATE = "eff_date" ;
    private static final String PARAM_PART_NAME = "part_name";
    private static final String PARAM_PART_DESCRIPTION ="part_description";  
    private static final String PARAM_MANUFACTURER="manufacturer";
    private static final String PARAM_PART_IMAGE="part_image";
    private static final String PARAM_SALE_PRICE = "salePrice";
    private static final String PARAM_QTY = "qty";  
    private static final String PARAM_USER_NAME = "user_name";
    private static final String PARAM_ADMIN_MESSAGE = "admin_message" ; 
    
    private static final String IP_PART_DAO = "partDAO";
    private static final String IP_DB_STRATEGY = "dbStrategy";
    private static final String IP_DRIVER_CLASS = "driverClass";
    private static final String IP_USER_NAME  = "userName";      
    private static final String IP_PASSWORD ="password";
    private static final String IP_URL = "url";        
    
    //JNDI NAME
    private static final String JNDI_NAME= "jdbc/pitdcar";     
            
    // ACTION constants
    private static final String ACTION_LIST_PAGE = "showListPage";
    private static final String ACTION_MANAGE_PAGE = "showManagePage";
    private static final String ACTION_EDIT_PAGE = "showEditPage";
    private static final String ACTION_HOME_PAGE = "showHomePage";
    private static final String ACTION_ADD_PAGE = "showAddPage";
    private static final String ACTION_EDIT = "edit";
    private static final String ACTION_DELETE="delete";
    private static final String ACTION_ADD="add";
    
    // PAGE URL constants
    private static final String PAGE_LIST = "/list-parts.jsp";
    private static final String PAGE_MANAGE = "/manage-parts.jsp";
    private static final String PAGE_HOME = "/index.jsp";
    private static final String PAGE_EDIT = "/edit-part.jsp";
    private static final String PAGE_ERROR = "/error-page.jsp";
            
    // ATTRIBUTE constants
    private static final String ATTRIBUTE_PARTS = "parts" ;
    private static final String ATTRIBUTE_SELECTED_PART = "selectedPart";
    
    // Variables to hold data from xml
    private String partDAOStrategyClassName ;
    private String dbStrategyClassName ;
    private String driverClass ;
    private String url;
    private String user ;
    private String password ;
      
    // Injecting Service class PartFacade
    @Inject
    private PartFacade partFacade;
    
    

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
       
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
      
        String user_name = request.getParameter(PARAM_USER_NAME);
        String admin_message = request.getParameter(PARAM_ADMIN_MESSAGE);
        
        if((user_name != null) && (user_name.length() > 0 )){
            session.setAttribute(PARAM_USER_NAME,user_name);
        }
        if((admin_message != null) && (admin_message.length() > 0)){
            context.setAttribute(PARAM_ADMIN_MESSAGE,admin_message);
        }
                
        String action = request.getParameter(PARAM_ACTION);
       
        String destination = "";
        int part_id;
        
        try{      
            switch(action){
                
                case ACTION_HOME_PAGE :
                    destination = PAGE_HOME;
                    break;
                    
                case ACTION_LIST_PAGE : 
                    resetPartList(request,partFacade);
                    destination = PAGE_LIST;
                    break;
                    
                case ACTION_MANAGE_PAGE :
                    resetPartList(request,partFacade);
                    destination = PAGE_MANAGE;
                    break;
                    
                case ACTION_ADD_PAGE : 
                    destination = PAGE_EDIT;
                    break;
                
                case ACTION_EDIT_PAGE :
                    part_id = getParameterPart_id(request);
                    Part part = partFacade.find(part_id);
                    request.setAttribute(ATTRIBUTE_SELECTED_PART, part);
                    destination = PAGE_EDIT;
                    break;
                    
                case ACTION_DELETE :
                    part_id = getParameterPart_id(request);
                    if(part_id != -1){
                        Part tempPart = partFacade.find(part_id);
                        partFacade.remove(tempPart);
                    }
                    resetPartList(request,partFacade);
                    destination = PAGE_MANAGE;
                    break;    
                
                case ACTION_EDIT : 
                case ACTION_ADD :    
                    part_id = getParameterPart_id(request);
                
                    String temp  = request.getParameter(PARAM_SALE_PRICE);
                    double tempPrice = Double.parseDouble(temp);
                    BigDecimal salePrice = new BigDecimal(tempPrice, MathContext.DECIMAL64);
                    
                    String temp2  = request.getParameter(PARAM_QTY);
                    int qty = Integer.parseInt(temp2);
                    
                    String part_name  = request.getParameter(PARAM_PART_NAME);
                    String part_description = request.getParameter(PARAM_PART_DESCRIPTION);
                    String manufacturer = request.getParameter(PARAM_MANUFACTURER);
                    String part_image = request.getParameter(PARAM_PART_IMAGE);
                                                               
                    Part tempPart = new Part();
                    tempPart.setPartId(part_id);
                    tempPart.setPartName(part_name);
                    tempPart.setPartDescription(part_description);
                    tempPart.setManufacturer(manufacturer);
                    tempPart.setPartImage(part_image);
                    tempPart.setSalePrice(salePrice);
                    tempPart.setQty(qty);
                                        
                    // Logic to decide between INSERT or UPDATE
                    if(part_id == -1){ 
                       partFacade.create(tempPart);
                    }
                    else{
                       partFacade.edit(tempPart); 
                    }
                    
                    resetPartList(request,partFacade);
                    destination = PAGE_MANAGE;
                    break;
            } 
            
        }catch(Exception e){
               destination = PAGE_ERROR ; 
               System.out.println(e.getMessage());
        }
        finally{    
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination);
            dispatcher.forward(request, response);
        }
        
    }
    
    private int getParameterPart_id(HttpServletRequest request){
        int part_id = -1; //flag
        String temp = request.getParameter(PARAM_PARTID);
        if(temp != null && temp.length() != 0){
            part_id = Integer.parseInt(temp);
        }
        return part_id;
    }
    
    private void resetPartList(HttpServletRequest request,PartFacade partFacade) throws SQLException,Exception{
        List<Part> parts = partFacade.findAll(); 
        request.setAttribute(ATTRIBUTE_PARTS ,parts);
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
