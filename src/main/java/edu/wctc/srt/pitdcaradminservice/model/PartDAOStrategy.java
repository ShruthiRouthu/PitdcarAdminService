
package edu.wctc.srt.pitdcaradminservice.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author Shruthi Routhu
 */
public interface PartDAOStrategy {
       
    List<Part> findAllParts() throws SQLException , Exception ;
    
    List<Part> findPartsByCondition(String propertyName, Object propertyVal)throws SQLException , Exception;

    Part findPartById(int part_id)throws SQLException  , Exception ;
           
    int deletePartById(int part_id) throws SQLException  , Exception;
     
    int updatePart(int part_id, List<String> propertyNameList ,List<Object> propertyValueList) throws SQLException  , Exception;
    
    int insertPart(List<String> propertyNameList ,List<Object> propertyValueList) throws SQLException  , Exception;
    
    int deleteAllParts() throws SQLException  , Exception;
     
}
