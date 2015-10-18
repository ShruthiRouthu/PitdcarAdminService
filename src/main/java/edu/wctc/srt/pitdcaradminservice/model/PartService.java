
package edu.wctc.srt.pitdcaradminservice.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shruthi Routhu
 */
public class PartService {
    
    private PartDAOStrategy dao;

    public PartService(PartDAOStrategy dao) {
        this.dao = dao;
    }
    
    public final List<Part> findAllParts() throws SQLException , Exception {
       return dao.findAllParts();
    }
    
    public final List<Part> findPartsByCondition(String propertyName, Object propertyVal)throws SQLException , Exception{
        return dao.findPartsByCondition(propertyName, propertyVal);
    }

    public final Part findPartById(int part_id)throws SQLException  , Exception {
       return dao.findPartById(part_id);
    }
           
    public final int deletePartById(int part_id) throws SQLException  , Exception{
       return dao.deletePartById(part_id);
    }
     
    public final int updatePart(int part_id, List<String> propertyNameList ,List<Object> propertyValueList) throws SQLException  , Exception{
       return dao.updatePart(part_id, propertyNameList, propertyValueList);
    }
    
    public final int insertPart(List<String> propertyNameList ,List<Object> propertyValueList) throws SQLException  , Exception{
       return dao.insertPart(propertyNameList, propertyValueList);
    }
    
    public final int deleteAllParts() throws SQLException  , Exception{
        return dao.deleteAllParts();
    }
    
// UNIT TESTING
    public static void main(String[] args) throws Exception{
                
        DBStrategy db = new  MySqlDbStrategy();
        PartDAO dao = new PartDAO(db,"com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/pitdcar","root","DJ2015");
        PartService ps = new PartService(dao);
//        
// ALL PARTS
        List<Part> list = ps.findAllParts();
        for(Part a : list) {
            System.out.println(a);
        }
        
// PARTS BY SOME CONDITION
//        List<Part> list = ps.findPartsByCondition("manufacturer","Beck Arnley ");
//        for(Part a : list) {
//            System.out.println(a);
//        }            

// PART BY ID 
//        Part p  = ps.findPartById(8);
//        System.out.println(p);         
        

// EMPTY THE TABLE        
//      int no = ps.
        
// DELETE RECORDS BY ID  
//      int no = ps.deletePartById(14);
//      System.out.println(no);    
       
// LISTS FOR RUNNING INSERT OR UPDATE         
//        List<String> key = new ArrayList();
////        key.add("part_id");
//        key.add("eff_date");
//        key.add("part_name");
//        key.add("part_description");
//        key.add("manufacturer");
//        key.add("part_image");
//        key.add("salePrice");
//        key.add("qty");
        

        
//        List<Object> value = new ArrayList();
////        value.add(12);
//        value.add("2015-12-12");
//        value.add("dddd");
//        value.add("dddd");
//        value.add("dddd");
//        value.add("dddd");
//        value.add(3);
//        value.add(3);
        

       
// DELETE RECORDS BY COMPLEX PK  
//      int no = dao.deletePartById(13);
//      System.out.println(no);        

//INSERT A RECORD 
//      System.out.println(dao.insertPart( key, value));

//UPDATE A RECORD        
//       System.out.println(dao.updatePart(15, key, value));
      
      
      
    }
    
}
