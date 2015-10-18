package edu.wctc.srt.pitdcaradminservice.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author Shruthi Routhu
 */
public interface DBStrategy {
       
    void openConnection(String driverClass, String url, String userName, String password) throws Exception;
    
    void openConnection(DataSource ds) throws Exception;
    
    List<Map<String, Object>> findAllRecords(String tableName) throws SQLException,Exception;
    
    List<Map<String,Object>> findRecordsByCondition(String tableName, String condColName, Object condColVal)throws SQLException, Exception ;

    Map<String,Object> findRecordByPK(String tableName, String pkName, Object pkVal)throws SQLException ,Exception ;
       
    int emptyTable(String tableName) throws SQLException ,Exception;
    
    int deleteRecordByPK(String tableName,String pkName ,Object pkValue ) throws SQLException ,Exception;
    
    int deleteRecordByComplexPK(String tableName, List<String> keyList ,List<Object> valueList ) throws SQLException, Exception;
    
    int updateRecord(String tableName, String conditionColName , Object conditionColValue,
            List<String> keyList ,List<Object> valueList) throws SQLException ,Exception;
    
    int insertRecord(String tableName,List<String> colNameList ,List<Object> colValueList) throws SQLException ,Exception;
    
}
