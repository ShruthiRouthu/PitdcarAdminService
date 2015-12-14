package edu.wctc.srt.pitdcaradminservice.controller;

import edu.wctc.srt.pitdcaradminservice.entity.Manufacturer;
import edu.wctc.srt.pitdcaradminservice.entity.Part;
import edu.wctc.srt.pitdcaradminservice.service.ManufacturerService;
import edu.wctc.srt.pitdcaradminservice.service.PartService;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Shruthi Routhu
 */

// Not using annotations bcause this servlet is configured using web.xml
public class PartController extends HttpServlet {
    
    // PARAMETER constants
    private static final String PARAM_ACTION = "action";
    private static final String PARAM_PARTID = "partId";
    private static final String PARAM_EFF_DATE = "effDate" ;
    private static final String PARAM_PART_NAME = "partName";
    private static final String PARAM_PART_DESCRIPTION ="partDescription";  
    private static final String PARAM_MANUFACTURERID="manufacturerId";
    private static final String PARAM_PART_IMAGE="partImage";
    private static final String PARAM_SALE_PRICE = "salePrice";
    private static final String PARAM_QTY = "qty";  
    private static final String PARAM_USER_NAME = "user_name";
    private static final String PARAM_ADMIN_MESSAGE = "admin_message" ;       
    
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
    
    // PAGE URL constants
    private static final String PAGE_LIST = "/list-parts.jsp";
    private static final String PAGE_MANAGE = "/manage-parts.jsp";
    private static final String PAGE_HOME = "/index.jsp";
    private static final String PAGE_EDIT = "/edit-part.jsp";
    private static final String PAGE_ERROR = "/error-page.jsp";
            
    // ATTRIBUTE constants
    private static final String ATTRIBUTE_PARTS = "parts" ;
    private static final String ATTRIBUTE_SELECTED_PART = "selectedPart";
    private static final String ATTRIBUTE_MANUFACTURERS = "manufacturers" ;
          

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
           Injecting a service objects into  servlet.
        */
        ServletContext sctx = getServletContext();
        WebApplicationContext ctx
                = WebApplicationContextUtils.getWebApplicationContext(sctx);
        PartService partService = (PartService) ctx.getBean("partService");
        ManufacturerService manufacturerService = (ManufacturerService) ctx.getBean("manufacturerService");
       
      
        String destination = "";
        int partId;
        
        String action = request.getParameter(PARAM_ACTION);

        try{

            switch(action){
                case ACTION_HOME_PAGE :
                    destination = PAGE_HOME;
                    break;
                    
                case ACTION_LIST_PAGE : 
                    resetPartList(request,partService);
                    destination = PAGE_LIST;
                    break;
                    
                case ACTION_MANAGE_PAGE :
                    resetPartList(request,partService);
                    destination = PAGE_MANAGE;
                    break;
                
                case ACTION_EDIT_PAGE :
                    partId = getParameterPartId(request);
                    Part part = partService.findById(Integer.toString(partId));
                    request.setAttribute(ATTRIBUTE_SELECTED_PART, part);
                    resetManuList(request,manufacturerService);
                    destination = PAGE_EDIT;
                    break;
                    
                case ACTION_DELETE :
                    partId = getParameterPartId(request);
                    if(partId != -1){
                        Part deletePart = partService.findById(Integer.toString(partId));
                        partService.remove(deletePart);
                    }
                    resetPartList(request,partService);
                    destination = PAGE_MANAGE;
                    break;    
                
                case ACTION_EDIT : 
                case ACTION_ADD :    
                    partId = getParameterPartId(request);
                
                    String salePriceTemp  = request.getParameter(PARAM_SALE_PRICE);
                    BigDecimal salePrice = new BigDecimal(salePriceTemp);
                                     
                    String qtyTemp  = request.getParameter(PARAM_QTY);
                    int qty = Integer.parseInt(qtyTemp);
                    
                    String partName  = request.getParameter(PARAM_PART_NAME);
                    String partDescription = request.getParameter(PARAM_PART_DESCRIPTION);
                    String partImage = request.getParameter(PARAM_PART_IMAGE);
                    
                    String manufacturerId = request.getParameter(PARAM_MANUFACTURERID);
                    Manufacturer manufacturer = null;
                    if((manufacturerId != null) && ( Integer.parseInt(manufacturerId)!= -1 )){
                        manufacturer = manufacturerService.findById(manufacturerId);
                    }
                                  
                    // Logic to decide between INSERT or UPDATE
                    if(partId == -1){ 
                       Part newPart = new Part(0);
                       
                       newPart.setPartName(partName);
                       newPart.setPartDescription(partDescription);
                       newPart.setPartImage(partImage);
                       newPart.setSalePrice(salePrice);
                       newPart.setQty(qty);
                     //  if(manufacturer != null){
                            newPart.setManufacturerId(manufacturer);
                      // }
                       
                       partService.edit(newPart);
                    }
                    else{
                       Part editPart = partService.findById(Integer.toString(partId));
                       
                       editPart.setPartName(partName);
                       editPart.setPartDescription(partDescription);
                       editPart.setPartImage(partImage);
                       editPart.setSalePrice(salePrice);
                       editPart.setQty(qty);
                     //  if(manufacturer != null){
                            editPart.setManufacturerId(manufacturer);
                     //  }
                       
                       partService.edit(editPart); 
                    }
                    
                    resetPartList(request,partService);
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
       
    private int getParameterPartId(HttpServletRequest request){
        int partId = -1; //flag
        String temp = request.getParameter(PARAM_PARTID);
        if(temp != null){
            partId = Integer.parseInt(temp);
        }
        return partId;
    }
    
    private void resetPartList(HttpServletRequest request,PartService partService) throws SQLException,Exception{
        List<Part> parts = partService.findAll(); 
        request.setAttribute(ATTRIBUTE_PARTS ,parts);
    }
    
    private void resetManuList(HttpServletRequest request,ManufacturerService manuService) throws SQLException,Exception{
        List<Manufacturer> manufacturers = manuService.findAll(); 
        request.setAttribute(ATTRIBUTE_MANUFACTURERS ,manufacturers);
    }

    @Override
    public void init() throws ServletException {
       
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
