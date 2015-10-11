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
    
   // Exception will be handled where the notifications are needed. Do validation too
    
    void openConnection(String driverClass, String url, String userName, String password) throws Exception;
    
    void openConnection(DataSource ds) throws Exception;
    
    List<Map<String, Object>> findAllRecords(String tableName) throws SQLException;
    
    List<Map<String,Object>> findRecordsByCondition(String tableName, String condColName, Object condColVal)throws SQLException ;

    Map<String,Object> findRecordByID(String tableName, String pkName, Object pkVal)throws SQLException ;
    
    Map<String,Object> findRecordByID(String tableName, List<String> keyList ,List<Object> valueList)throws SQLException ;
    
    int emptyTable(String tableName) throws SQLException;
    
    int deleteRecordByID(String tableName,String pkName ,Object pkValue ) throws SQLException;
    
    int deleteRecordByID(String tableName, List<String> keyList ,List<Object> valueList ) throws SQLException;
    
    int updateRecord(String tableName, String conditionColName , Object conditionColValue,
            List<String> keyList ,List<Object> valueList) throws SQLException;
    
    int insertRecord(String tableName,List<String> colNameList ,List<Object> colValueList) throws SQLException;
    
}
