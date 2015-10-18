
package edu.wctc.srt.pitdcaradminservice.controller;

import edu.wctc.srt.pitdcaradminservice.model.DBStrategy;
import edu.wctc.srt.pitdcaradminservice.model.MySqlDbStrategy;
import edu.wctc.srt.pitdcaradminservice.model.Part;
import edu.wctc.srt.pitdcaradminservice.model.PartDAO;
import edu.wctc.srt.pitdcaradminservice.model.PartService;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
                                       
    // ACTION constants
    private static final String ACTION_LIST_PAGE = "showListPage";
    private static final String ACTION_MANAGE_PAGE = "showManagePage";
    private static final String ACTION_EDIT_PAGE = "showEditPage";
    private static final String ACTION_EDIT = "edit";
    private static final String ACTION_DELETE="delete";
    private static final String ACTION_ADD="add";
    
    // PAGE URL constants
    private static final String PAGE_LIST = "/list-parts.jsp";
    private static final String PAGE_MANAGE = "/manage-parts.jsp";
    private static final String PAGE_HOME = "/index.jsp";
    private static final String PAGE_EDIT = "/edit-part.jsp";
            
    // ATTRIBUTE constants
    private static final String ATTRIBUTE_PARTS = "parts" ;
    private static final String ATTRIBUTE_SELECTED_PART = "selectedPart";
    
    // do something with these they are not supposed to be here , things declared here are global
    DBStrategy db = new  MySqlDbStrategy();
    PartDAO dao = new PartDAO(db,"com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/pitdcar","root","DJ2015");
    private PartService partService = new PartService(dao);
    
   

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
        
        String destination = "";
        int part_id;
        String action = request.getParameter(PARAM_ACTION);
        
       
        
        try{
            
            switch(action){
                case ACTION_LIST_PAGE : 
                    resetPartList(request,partService);
                    destination = PAGE_LIST;
                    break;
                    
                case ACTION_MANAGE_PAGE :
                    resetPartList(request,partService);
                    destination = PAGE_MANAGE;
                    break;
                
                case ACTION_EDIT_PAGE :
                    part_id = getParameterPart_id(request);
                    Part part = partService.findPartById(part_id);
                    request.setAttribute(ATTRIBUTE_SELECTED_PART, part);
                    destination = PAGE_EDIT;
                    break;
                    
                case ACTION_DELETE :
                    part_id = getParameterPart_id(request);
                    if(part_id != -1){
                        partService.deletePartById(part_id);
                    }
                    resetPartList(request,partService);
                    destination = PAGE_MANAGE;
                    break;    
                
                case ACTION_EDIT : 
                    part_id = getParameterPart_id(request);
                    
                    String temp = request.getParameter(PARAM_EFF_DATE);
                    String pattern = "yyyy-MM-dd";
                    SimpleDateFormat format = new SimpleDateFormat(pattern);
                    Date eff_date = format.parse(temp);
                    
                    temp = request.getParameter(PARAM_SALE_PRICE);
                    double salePrice = Double.parseDouble(temp);
                    
                    temp = request.getParameter(PARAM_QTY);
                    int qty = Integer.parseInt(temp);
                    
                    String part_name  = request.getParameter(PARAM_PART_NAME);
                    String part_description = request.getParameter(PARAM_PART_DESCRIPTION);
                    String manufacturer = request.getParameter(PARAM_MANUFACTURER);
                    String part_image = request.getParameter(PARAM_PART_IMAGE);
                                           
                    List<String> key = new ArrayList();
                    key.add(PARAM_EFF_DATE);
                    key.add(PARAM_PART_NAME);
                    key.add(PARAM_PART_DESCRIPTION);
                    key.add(PARAM_MANUFACTURER);
                    key.add(PARAM_PART_IMAGE);
                    key.add(PARAM_SALE_PRICE);
                    key.add(PARAM_QTY);        

                    List<Object> value = new ArrayList();
                    value.add(eff_date);
                    value.add(part_name);
                    value.add(part_description);
                    value.add(manufacturer);
                    value.add(part_image);
                    value.add(salePrice);
                    value.add(qty);
                    
                    partService.updatePart(part_id, key, value);
                    resetPartList(request,partService);
                    destination = PAGE_MANAGE;
                    break;
                
                case ACTION_ADD :
//                    part_id = getParameterPart_id(request);
                    
                    temp = request.getParameter(PARAM_EFF_DATE);
                    pattern = "yyyy-MMdd";
                    format = new SimpleDateFormat(pattern);
                    eff_date = format.parse(temp);
                    
                    temp = request.getParameter(PARAM_SALE_PRICE);
                    salePrice = Double.parseDouble(temp);
                    
                    temp = request.getParameter(PARAM_QTY);
                    qty = Integer.parseInt(temp);
                    
                    part_name  = request.getParameter(PARAM_PART_NAME);
                    part_description = request.getParameter(PARAM_PART_DESCRIPTION);
                    manufacturer = request.getParameter(PARAM_MANUFACTURER);
                    part_image = request.getParameter(PARAM_PART_IMAGE);
                                           
                    key = new ArrayList();
                    key.clear();
                    key.add(PARAM_EFF_DATE);
                    key.add(PARAM_PART_NAME);
                    key.add(PARAM_PART_DESCRIPTION);
                    key.add(PARAM_MANUFACTURER);
                    key.add(PARAM_PART_IMAGE);
                    key.add(PARAM_SALE_PRICE);
                    key.add(PARAM_QTY);        

                    value = new ArrayList();
                    value.clear();
                    value.add(eff_date);
                    value.add(part_name);
                    value.add(part_description);
                    value.add(manufacturer);
                    value.add(part_image);
                    value.add(salePrice);
                    value.add(qty);
                    
                    partService.insertPart(key, value);
                    resetPartList(request,partService);
                    destination = PAGE_MANAGE;
                    break;  
                    
                default:
            }
            
        }catch(Exception e){ System.out.println(e.getMessage());}
                                
        
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);
        
        
 
    }
    
    private int getParameterPart_id(HttpServletRequest request){
        int part_id = -1; //flag
        String temp = request.getParameter(PARAM_PARTID);
        if(temp != null){
            part_id = Integer.parseInt(temp);
        }
        return part_id;
    }
    
    private void resetPartList(HttpServletRequest request,PartService partService) throws SQLException,Exception{
        List<Part> parts = partService.findAllParts(); 
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
