
package edu.wctc.srt.pitdcaradminservice.model;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Shruthi Routhu
 */
public class PartDAO implements PartDAOStrategy{
       
    private static final String PART_TABLE = "part" ;
    private static final String PART_ID = "part_id" ;
    private static final String EFF_DATE = "eff_date" ;
    private static final String PART_NAME = "part_name";
    private static final String PART_DESCRIPTION = "part_description" ;      
    private static final String MANUFACTURER = "manufacturer" ;      
    private static final String PART_IMAGE = "part_image" ;      
    private static final String SALE_PRICE = "salePrice";       
    private static final String QTY = "qty";
    
    private DBStrategy dbStrat; 
    private String driverClassName;
    private String url;
    private String userName;
    private String password;
//    private String datePattern = "yyyy/MM/dd";
//    private SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
    
    //CONSTRUCTOR
    public PartDAO(DBStrategy dbStrat, String driverClassName, String url, String userName, String password) {
        this.dbStrat = dbStrat;
        this.driverClassName = driverClassName;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }
           
    @Override
    public final List<Part> findAllParts() throws SQLException , Exception {
       
        List<Part> partList = new ArrayList<>();
        openDBConnection();
        
        List<Map<String,Object>> rawData =  dbStrat.findAllRecords(PART_TABLE);
        
        for(Map m : rawData){
            Part part = new Part();
           
            Object obj = (m.get(PART_ID) == null)? "" : m.get(PART_ID);
            part.setPart_id(Integer.parseInt(obj.toString()));            
            
            obj = (m.get(EFF_DATE) == null)? "" : m.get(EFF_DATE);
            part.setEff_date((Date)obj);
            
            obj = (m.get(PART_NAME)== null)? "" : m.get(PART_NAME);
            part.setPart_name(obj.toString());
            
            obj = (m.get(PART_DESCRIPTION)== null)? "" : m.get(PART_DESCRIPTION);
            part.setPart_description(obj.toString());
            
            obj = (m.get(MANUFACTURER)== null)? "" : m.get(MANUFACTURER);
            part.setManufacturer(obj.toString());
            
            obj = (m.get(PART_IMAGE)== null)? "" : m.get(PART_IMAGE);
            part.setPart_image(obj.toString());
            
            obj = (m.get(SALE_PRICE) == null)? "" : m.get(SALE_PRICE);
            part.setSalePrice(Double.parseDouble(obj.toString()));
            
            obj = (m.get(QTY) == null)? "" : m.get(QTY);
            part.setQty(Integer.parseInt(obj.toString()));
            
            partList.add(part);
        }
        
        return partList ;
    }

    @Override
    public final List<Part> findPartsByCondition(String propertyName, Object propertyVal) throws SQLException, Exception {
        List<Part> partList = new ArrayList<>();
        openDBConnection();
        
        List<Map<String,Object>> rawData =  dbStrat.findRecordsByCondition(PART_TABLE, propertyName, propertyVal);
        
        for(Map m : rawData){
            Part part = new Part();
           
            Object obj = (m.get(PART_ID) == null)? "" : m.get(PART_ID);
            part.setPart_id(Integer.parseInt(obj.toString()));            
            
            obj = (m.get(EFF_DATE) == null)? "" : m.get(EFF_DATE);
            part.setEff_date((Date)obj);
                        
            obj = (m.get(PART_NAME)== null)? "" : m.get(PART_NAME);
            part.setPart_name(obj.toString());
            
            obj = (m.get(PART_DESCRIPTION)== null)? "" : m.get(PART_DESCRIPTION);
            part.setPart_description(obj.toString());
            
            obj = (m.get(MANUFACTURER)== null)? "" : m.get(MANUFACTURER);
            part.setManufacturer(obj.toString());
            
            obj = (m.get(PART_IMAGE)== null)? "" : m.get(PART_IMAGE);
            part.setPart_image(obj.toString());
            
            obj = (m.get(SALE_PRICE) == null)? "" : m.get(SALE_PRICE);
            part.setSalePrice(Double.parseDouble(obj.toString()));
            
            obj = (m.get(QTY) == null)? "" : m.get(QTY);
            part.setQty(Integer.parseInt(obj.toString()));
            
            partList.add(part);
        }
        
        return partList;
    }

    @Override
    public final Part findPartById(int part_id) throws SQLException, Exception {
        
        openDBConnection();
        
        Map<String,Object> rawData =  dbStrat.findRecordByPK(PART_TABLE, PART_ID ,part_id);
        
        
            Part part = new Part();
           
            Object obj = (rawData.get(PART_ID) == null)? "" : rawData.get(PART_ID);
            part.setPart_id(Integer.parseInt(obj.toString()));            
            
            obj = (rawData.get(EFF_DATE) == null)? "" : rawData.get(EFF_DATE);
            part.setEff_date((Date)obj);
                        
            obj = (rawData.get(PART_NAME)== null)? "" : rawData.get(PART_NAME);
            part.setPart_name(obj.toString());
            
            obj = (rawData.get(PART_DESCRIPTION)== null)? "" : rawData.get(PART_DESCRIPTION);
            part.setPart_description(obj.toString());
            
            obj = (rawData.get(MANUFACTURER)== null)? "" : rawData.get(MANUFACTURER);
            part.setManufacturer(obj.toString());
            
            obj = (rawData.get(PART_IMAGE)== null)? "" : rawData.get(PART_IMAGE);
            part.setPart_image(obj.toString());
            
            obj = (rawData.get(SALE_PRICE) == null)? "" : rawData.get(SALE_PRICE);
            part.setSalePrice(Double.parseDouble(obj.toString()));
            
            obj = (rawData.get(QTY) == null)? "" : rawData.get(QTY);
            part.setQty(Integer.parseInt(obj.toString()));
        
        return part;
    }

    @Override
    public final int deletePartById(int part_id) throws SQLException, Exception {
        openDBConnection();
        int partsDeleted = dbStrat.deleteRecordByPK(PART_TABLE, PART_ID, part_id);
        return partsDeleted;
    }

    @Override
    public final int updatePart(int part_id, List<String> propertyNameList, List<Object> propertyValueList) throws SQLException , Exception{
        openDBConnection();
        int partsUpdated = dbStrat.updateRecord(PART_TABLE, PART_ID , part_id, propertyNameList ,propertyValueList);
        return partsUpdated;
    }

    @Override
    public final int insertPart(List<String> propertyNameList, List<Object> propertyValueList) throws SQLException , Exception {
        openDBConnection();
        int partsInserted = dbStrat.insertRecord(PART_TABLE, propertyNameList , propertyValueList);
        return partsInserted;        
    }

    @Override
    public final int deleteAllParts() throws SQLException, Exception {
        openDBConnection();
        int partsDeleted = dbStrat.emptyTable(PART_TABLE);
        return partsDeleted;
    }
    
    private void openDBConnection() throws Exception{
        dbStrat.openConnection(driverClassName, url, userName, password);
    }
    
    // UNIT TESTING
    public static void main(String[] args) throws Exception{
                
//        DBStrategy db = new  MySqlDbStrategy();
//        PartDAO dao = new PartDAO(db,"com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/pitdcar","root","DJ2015");
//        
// ALL PARTS
//        List<Part> list = dao.findAllParts();
//        for(Part a : list) {
//            System.out.println(a);
//        }
        
// PARTS BY SOME CONDITION
//        List<Part> list = dao.findPartsByCondition(PART_NAME,"Pronto 14132");
//        for(Part a : list) {
//            System.out.println(a);
//        }            

// PART BY ID 
//        Part p  = dao.findPartById(8);
//        System.out.println(p);         
//        

// EMPTY THE TABLE        
//      int no = db.emptyTable("author");
        
// DELETE RECORDS BY ID  
//      int no = db.deleteRecordByPK("part","part_id", 11);
//      System.out.println(no);    
       
// LISTS FOR RUNNING INSERT OR UPDATE         
//        List<String> key = new ArrayList();
//        key.add("part_id");
//        key.add("eff_date");
//        key.add("part_name");
//        key.add("part_description");
//        key.add("manufacturer");
//        key.add("part_image");
//        key.add("salePrice");
//        key.add("qty");
//        
//
//        
//        List<Object> value = new ArrayList();
//        value.add(12);
//        value.add("2000-01-01");
//        value.add("cccc");
//        value.add("ccccc");
//        value.add("cccccc");
//        value.add("cccccc");
//        value.add(3);
//        value.add(3);
        

       
// DELETE RECORDS BY COMPLEX PK  
//      int no = dao.deletePartById(13);
//      System.out.println(no);        

//INSERT A RECORD 
//      System.out.println(dao.insertPart( key, value));

//UPDATE A RECORD        
//       System.out.println(dao.updatePart(14, key, value));
      
      
      
    }
    
}
