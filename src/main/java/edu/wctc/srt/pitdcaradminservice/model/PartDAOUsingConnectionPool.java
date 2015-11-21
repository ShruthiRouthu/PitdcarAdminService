
package edu.wctc.srt.pitdcaradminservice.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author Shruthi Routhu
 */
public class PartDAOUsingConnectionPool  implements PartDAOStrategy {

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
    private DataSource dataSource;
    
//    private String datePattern = "yyyy/MM/dd";
//    private SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
    
    //CONSTRUCTOR
    public PartDAOUsingConnectionPool(DBStrategy dbStrat, DataSource ds) {
        this.dbStrat = dbStrat;
        this.dataSource = ds;
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
        dbStrat.openConnection(this.dataSource);
    }
    
 
      
    
    
    
}
