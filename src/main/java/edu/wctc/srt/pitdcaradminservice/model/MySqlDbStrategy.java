
package edu.wctc.srt.pitdcaradminservice.model;

import java.sql.Connection;
import java.sql.Date;
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
    
    private static final boolean debug = true;
    private Connection conn;
    
    // Do EXCEPTIONS, VALIDATION and wrap connection closing in finally,
    // search api and find out what exceptions do the methods throw
    
    
    @Override
    public final void openConnection(String driverClass, String url, String userName, String password)
            throws Exception{     
        Class.forName (driverClass); 
        conn = DriverManager.getConnection(url, userName, password);
    }
    
    @Override
    public final void openConnection(DataSource ds) throws Exception {
        conn = ds.getConnection();
    }
        
    @Override
    public final List<Map<String,Object>> findAllRecords(String tableName) throws SQLException,Exception{
        
        String sql = "SELECT * FROM " + tableName ;
        List<Map<String,Object>> recordList = new ArrayList<>();
        Statement stmt = null;
        try{
            stmt = conn.createStatement();
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
        }
        catch(SQLException sqle){
           throw sqle ; 
        }
        catch(Exception e){
            throw e;
        }
        finally{
            stmt.close();
            closeConnection();  
        }
        
        
        return recordList;
    }
    
    @Override
    public final List<Map<String,Object>> findRecordsByCondition(String tableName, String condColName, Object condColVal)
            throws SQLException , Exception {
        
        String sql = "SELECT * FROM " + tableName + " WHERE " +  condColName + " = ? " ;
        if(debug) System.out.println(sql);
        List<Map<String,Object>> recordList = new ArrayList<>();
        PreparedStatement stmt = null;
        
        try{
            
            stmt = conn.prepareStatement(sql);
            stmt.setObject(1,condColVal);

            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();

            int columnCount = metaData.getColumnCount();
            
             while( rs.next()){
                Map<String,Object> record = new HashMap<>();
                for(int i=1; i <= columnCount; i++){
                    record.put(metaData.getColumnName(i), rs.getObject(i));
                }
                recordList.add(record);    
            }
             
        }catch(SQLException sqle){
            throw sqle;
        }catch(Exception e){
            throw e;
        }finally{
            
            stmt.close();
            closeConnection(); 
        }
        return recordList;
        
    }
    
    @Override
    public final Map<String, Object> findRecordByPK(String tableName, String pkName, Object pkVal) 
            throws SQLException, Exception {
        
        String sql = "SELECT * FROM " + tableName + " WHERE " +  pkName + " = ? " ;
        if(debug) System.out.println(sql);
        Map<String,Object> record = new  HashMap<>();
        PreparedStatement stmt = null;
        
        try {
            
            stmt = conn.prepareStatement(sql);
            stmt.setObject(1,pkVal);

            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();

            int columnCount = metaData.getColumnCount();
            while( rs.next()){
                for(int i=1; i <= columnCount; i++){
                    record.put(metaData.getColumnName(i), rs.getObject(i));
                } 
            }  
        }catch(SQLException sqle) {
            throw sqle;      
        }catch(Exception e){
            throw e;    
        }finally{
            
            stmt.close();
            closeConnection();
        }        
        return record;
    }
       
    @Override
    public final int emptyTable(String tableName) throws SQLException ,Exception {
        
        String sql = "DELETE FROM " + tableName ;
        Statement stmt = null;
        int noOfRecords ;
        try{
            
            stmt = conn.createStatement();
            noOfRecords = stmt.executeUpdate(sql);
            
        }catch(SQLException sqle) {
            throw sqle;      
        }catch(Exception e){
            throw e;    
        }finally{ 
            
            stmt.close();
            closeConnection();
        }    
        return noOfRecords;
    }
    
    @Override
    public final int deleteRecordByPK(String tableName, String pkName ,Object pkValue ) 
            throws SQLException ,Exception{
   
        String sql = "DELETE FROM " + tableName + " WHERE " + pkName + " = ? " ;
        PreparedStatement stmt = null;
        int recordsDeleted ;
        try{
            
            stmt = conn.prepareStatement(sql);
            stmt.setObject(1 , pkValue);
            if(debug)System.out.println(sql);
            recordsDeleted = stmt.executeUpdate();
            
        }catch(SQLException sqle) {
            throw sqle;      
        }catch(Exception e){
            throw e;    
        }finally{        

            stmt.close();
            closeConnection();
        }
        return  recordsDeleted ;
    }
    
    @Override
    public final int deleteRecordByComplexPK(String tableName, List<String> keyList, List<Object> valueList) 
        throws SQLException,Exception {
        
        String sql = this.buildDeleteSql(tableName, keyList);
        PreparedStatement stmt = null;
        int recordsDeleted ;
        try{
        
            stmt = conn.prepareStatement(sql);
            for(int i=0; i < valueList.size(); i++){
                stmt.setObject(i+1,valueList.get(i));
            }
            if(debug)System.out.println(stmt);

            recordsDeleted = stmt.executeUpdate();
        
        }catch(SQLException sqle) {
            throw sqle;      
        }catch(Exception e){
            throw e;    
        }finally{
            stmt.close();
            closeConnection();
        }
        return  recordsDeleted ;
        
    }
    
    @Override
    public final int updateRecord(String tableName, String conditionColName , Object conditionColValue,
            List<String> keyList ,List<Object> valueList) throws SQLException, Exception{
       
        String sql = buildUpdateSql(tableName, conditionColName ,conditionColValue, keyList);
        PreparedStatement stmt = null;
        int recordsUpdated ;
        
        try{
            stmt = conn.prepareStatement(sql);

            int index = 1;
            for(Object value: valueList){
                stmt.setObject(index, value);
                index += 1;
            }

            stmt.setObject(index, conditionColValue); 

            recordsUpdated = stmt.executeUpdate();
        
        }catch(SQLException sqle) {
            throw sqle;      
        }catch(Exception e){
            throw e;    
        }finally{
            stmt.close();
            closeConnection();
        }
        return  recordsUpdated ;
        
    }
    
    @Override
    public final int insertRecord(String tableName,List<String> colNameList ,List<Object> colValueList)
            throws SQLException ,Exception{
    
        String sql = buildInsertSql(tableName,colNameList);
        PreparedStatement stmt = null;
        int recordsUpdated ;
        
        try{
            
            stmt = conn.prepareStatement(sql);
            int index = 0;
            for(Object value: colValueList){
                index += 1;
                stmt.setObject(index, value);
            }

            recordsUpdated = stmt.executeUpdate();
        
        }catch(SQLException sqle) {
            throw sqle;      
        }catch(Exception e){
            throw e;    
        }finally{
            stmt.close();
            closeConnection();
        }
        
        return  recordsUpdated ;
    }

    private String buildInsertSql(String tableName,List<String> colNameList) throws Exception{
       
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
    
    private String buildUpdateSql(String tableName, String conditionColName , Object conditionColValue, 
            List<String> keyList ) throws Exception{
            
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
   
    private String buildDeleteSql(String tableName, List<String> keyList ) throws Exception{
        StringBuffer sql = new StringBuffer();
        int size = keyList.size();
                
            sql = new StringBuffer("Delete from ");
            sql.append(tableName);
            sql.append(" Where ") ;

            for(int i=0; i < size; i++){
            
                sql.append(keyList.get(i));
                sql.append(" = ? ");
                // loop for appending AND
                if(i < (size-1)){
                    sql.append(" AND ");
                } 
            }
        
        if(debug)System.out.println(sql);
        return sql.toString(); 
    }
    
    private void closeConnection() throws SQLException {
        conn.close();
    }
          
    public static void main(String args[]) throws Exception{
//       MySqlDbStrategy db = new  MySqlDbStrategy();
//       db.openConnection("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/pitdcar","root","DJ2015");
 
//FIND ALL RECORDS       
//      List<Map<String,Object>> records = db.findAllRecords("part");
//      for(Map record : records) 
//         {  System.out.println(record);  }

//FIND RECORDS BY SOME VALUE / CONDITION
//        List<Map<String,Object>> records = db.findRecordsByCondition("part","manufacturer", "Beck Arnley");
//        for(Map record : records) 
//           {  System.out.println(record);  }
       
//FIND RECORDS BY ID 
//        Map<String,Object> record = db.findRecordByPK("part","part_id", 1);
//             System.out.println(record);        

//EMPTY THE TABLE        
//      int no = db.emptyTable();
        
//DELETE RECORDS BY ID  
//      int no = db.deleteRecordByPK("part","part_id", 15);
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
//        
//        List<Object> value = new ArrayList();
////        value.add(12);
//        value.add("1990-04-10");
//        value.add("sonu");
//        value.add("this is sonu");
//        value.add("janardhanratna");
//        value.add("bbbb");
//        value.add(10.89);
//        value.add(36);
//       
// DELETE RECORDS BY COMPLEX PK  
//      int no = db.deleteRecordByComplexPK("part", key ,value );  
//      System.out.println(no);        

//INSERT A RECORD        
//      System.out.println(db.insertRecord("part", key, value));

//UPDATE A RECORD        
//       System.out.println(db.updateRecord("part" ,"part_id", 16, key, value));   
        
        
//      HashMap<String,Object> hm=new HashMap<String,Object>();  
//      hm.put("author_id",4);  
//      hm.put("name","vijay");  
//      hm.put("date","10/20/2015"); 
      

        
        
      
//        db.conn.close(); 
    }

    

    
    
}
