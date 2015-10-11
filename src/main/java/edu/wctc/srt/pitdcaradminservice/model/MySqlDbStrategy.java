
package edu.wctc.srt.pitdcaradminservice.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author Shruthi Routhu
 */
public class MySqlDbStrategy implements DBStrategy{
    
    private static final Boolean debug = true;
    private Connection conn;
    
    // Exception will be handled where the notifications are needed. Do validation too
    
    @Override
    public final void openConnection(String driverClass, String url, String userName, String password) throws Exception{     
        Class.forName (driverClass); // why am i doing this ???
        conn = DriverManager.getConnection(url, userName, password);
    }
    
    @Override
    public final void openConnection(DataSource ds) throws Exception {
        conn = ds.getConnection();
    }
    
    private void closeConnection() throws SQLException{
        conn.close();
    }
    
    @Override
    public final List<Map<String,Object>> findAllRecords(String tableName) throws SQLException{
        
        String sql = "SELECT * FROM " + tableName ;
        List<Map<String,Object>> recordList = new ArrayList<>();
        Statement stmt =conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        
        while( rs.next()){
            Map<String,Object> record = new HashMap<>();
            for(int i=1; i <= columnCount; i++){
                record.put(metaData.getColumnName(i), rs.getObject(i));
            }
            recordList.add(record);    
        }
        
        stmt.close();
        closeConnection();
        
        return recordList;
    }
    
    @Override
    public List<Map<String,Object>> findRecordsByCondition(String tableName, String condColName, Object condColVal)throws SQLException {
        
        String sql = "SELECT * FROM " + tableName + " WHERE " +  condColName + " = ? " ;
        if(debug) System.out.println(sql);
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setObject(1,condColVal);
        
        ResultSet rs = stmt.executeQuery();
        ResultSetMetaData metaData = rs.getMetaData();
        
        int columnCount = metaData.getColumnCount();
        List<Map<String,Object>> recordList = new ArrayList<>();
        
         while( rs.next()){
            Map<String,Object> record = new HashMap<>();
            for(int i=1; i <= columnCount; i++){
                record.put(metaData.getColumnName(i), rs.getObject(i));
            }
            recordList.add(record);    
        }
        
        stmt.close();
        closeConnection(); 
        return recordList;
        
    }
    
     @Override
    public Map<String, Object> findRecordByID(String tableName, String pkName, Object pkVal) throws SQLException {
        
        String sql = "SELECT * FROM " + tableName + " WHERE " +  pkName + " = ? " ;
        if(debug) System.out.println(sql);
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setObject(1,pkVal);
        
        ResultSet rs = stmt.executeQuery();
        ResultSetMetaData metaData = rs.getMetaData();
        
        int columnCount = metaData.getColumnCount();
        Map<String,Object> record = new  HashMap<>();
        
         while( rs.next()){
            for(int i=1; i <= columnCount; i++){
                record.put(metaData.getColumnName(i), rs.getObject(i));
            }  
        }
        
        stmt.close();
        closeConnection(); 
        return record;
    }
    
    @Override
    public Map<String, Object> findRecordByID(String tableName, List<String> keyList, List<Object> valueList) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public final int emptyTable(String tableName) throws SQLException{
        
        String sql = "DELETE FROM " + tableName ;
        Statement stmt = conn.createStatement();
        int noOfRecords = stmt.executeUpdate(sql);
        
        
        stmt.close();
        closeConnection();
        return noOfRecords;
        
    }
    
    @Override
    public final int deleteRecordByID(String tableName, String pkName ,Object pkValue ) throws SQLException {
   
        String sql = "DELETE FROM " + tableName + " WHERE " + pkName + " = ? " ;
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setObject(1 , pkValue);
        if(debug)System.out.println(sql);
        //int recordsDeleted = stmt.executeUpdate();
        
        
        int recordsUpdated = stmt.executeUpdate();
        stmt.close();
        closeConnection();
        
        return  recordsUpdated ;
    }
    
    @Override
    public int deleteRecordByID(String tableName, List<String> keyList, List<Object> valueList) throws SQLException {
        
        String sql = "DELETE FROM " + tableName + " WHERE " ;
        
        PreparedStatement stmt = conn.prepareStatement(sql);
       // stmt.setObject(1 , pkValue);
        if(debug)System.out.println(sql);
        //int recordsDeleted = stmt.executeUpdate();
        
        
        int recordsUpdated = stmt.executeUpdate();
        stmt.close();
        closeConnection();
        
        return  recordsUpdated ;
        
    }
    
    // Will validate later
    @Override
    public final int updateRecord(String tableName, String conditionColName , Object conditionColValue,
            List<String> keyList ,List<Object> valueList) throws SQLException{
       
        String sql = buildUpdateSql(tableName, conditionColName ,conditionColValue, keyList);
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        int index = 1;
        for(Object value: valueList){
            stmt.setObject(index, value);
            index += 1;
        }
        
        stmt.setObject(index, conditionColValue); 
        
        int recordsUpdated = stmt.executeUpdate();
        stmt.close();
        closeConnection();
        
        return  recordsUpdated ;
        
    }
    
    @Override
    public final int insertRecord(String tableName,List<String> colNameList ,List<Object> colValueList) throws SQLException{
    
        String sql = buildInsertSql(tableName,colNameList);
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        int index = 0;
        for(Object value: colValueList){
            index += 1;
            stmt.setObject(index, value);
        }
        
        int recordsUpdated = stmt.executeUpdate();
        stmt.close();
        closeConnection();
        
        return  recordsUpdated ;
    }

    private String buildInsertSql(String tableName,List<String> colNameList){
       
        String sql = "Insert into " + tableName + "(" ;
        for(String name: colNameList){
            sql += name + ",";
        }
        
        int length = sql.length();
        String temp = sql.substring(0, length-1);
        sql = temp;
        
        length = colNameList.size();
        sql += ") values(" ;
        for(int i=0; i < length; i++){
            sql += "?,"; 
        }
        
        length = sql.length();
        temp = sql.substring(0, length-1);
        sql = temp;
        
        sql += ");" ;
        if(debug) System.out.println(sql);
         
        
        return sql;  
    }
    
    private String buildUpdateSql(String tableName, String conditionColName , Object conditionColValue, List<String> keyList ){
            
            String sql = "UPDATE " + tableName + " SET " ;
            for(int i=0; i< keyList.size() ; i++) {
                sql += " " + keyList.get(i) + " = ?," ;
            } 
            
            int length = sql.length();
            String temp = sql.substring(0, length-1);
            sql = temp;
            
            sql += " WHERE " + conditionColName + " = ?" ;
            if(debug)System.out.println(sql);
            return sql;
    }

    private StringBuffer buildDeleteSql(String tableName, List<String> keyList, List<Object> valueList){
       
        StringBuffer sql = new StringBuffer("Delete from ");
        sql.append(tableName);
        sql.append(" Where ") ;
        
        
        return sql;
        
        
        
        
        
    }
   

 
    
    
}
