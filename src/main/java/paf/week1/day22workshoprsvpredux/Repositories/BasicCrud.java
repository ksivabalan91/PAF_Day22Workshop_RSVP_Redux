package paf.week1.day22workshoprsvpredux.Repositories;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class BasicCrud {
    @Autowired
    private JdbcTemplate template;

    //! QUERY STATEMENTS
    //todo-> Useful for finding a list of single value items strings/integers/float
    public <T> List<T> findOneField (String selectField, String fromTable, Class<T> classType){
        String SQL = "select "+selectField+" from "+fromTable;
        return template.queryForList(SQL, classType);
    }
    
    //todo-> able to accept multiple fields for equals operation in sql statement
    public <T> List<T> findItemBy (String selectField, String fromTable, String where, String equalsTo, Class<T> classType){
        String SQL="";
        try{
            SQL = "select "+selectField+" from "+fromTable+" where "+where+"="+equalsTo;
            return template.query(SQL, BeanPropertyRowMapper.newInstance(classType));
        } catch(Exception e){
            System.out.println(e.getMessage());
            SQL = "select "+selectField+" from "+fromTable+" where "+where+"="+"\""+equalsTo+"\"";
            return template.query(SQL, BeanPropertyRowMapper.newInstance(classType));
        }        
    }
    //todo-> open ended where statement to include other operations
    public <T> List<T> findItems (String selectField, String fromTable, String open, Class<T> classType){
        String SQL = "select "+selectField+" from "+fromTable+" "+open;
        return template.query(SQL, BeanPropertyRowMapper.newInstance(classType));
    }

    //todo-> finds all items and maps to model object
    public <T> List<T> findall(String fromTable,Class<T> classType){
        return findItems("*", fromTable, "",classType);
    }
    //todo-> Generic object, works as long as model is made
    public <T> List<T> findAnything (String SQL, Class<T> classType){
        return template.query(SQL, BeanPropertyRowMapper.newInstance(classType));
    }    
    //todo-> takes any and every SQL statements and returns a map
    public List<Map<String,Object>> genericMap(String SQL){
        return template.queryForList(SQL);       
    }

    //! INSERT STATEMENTS
    //todo-> insert items with partial information
    public int insert(String tableName, String fieldsList, String valuesList){        
        String SQL = "insert into "+tableName+"("+ fieldsList+") values("+valuesList+")";
        return template.update(SQL);
    }
    
    //todo-> insert items with all information, auto increment id can be input as null
    public int insert(String tableName, String valuesList){
        String SQL = "insert into "+tableName+" values("+valuesList+")";
        return template.update(SQL);
    }
    //todo-> insert items with partial information
    public int upsert(String tableName, String fieldsList, String valuesList){        
        String SQL = "replace into "+tableName+"("+ fieldsList+") values("+valuesList+")";
        return template.update(SQL);
    }
    
    //todo-> insert items with all information, auto increment id can be input as null
    public int upsert(String tableName, String valuesList){
        String SQL = "replace into "+tableName+" values("+valuesList+")";
        return template.update(SQL);
    }

    //! UPDATE STATEMENTS
    //todo-> open ended where statements
    public int updateBy(String tableName, String set, String where){
        String SQL = "update "+tableName+" set "+set+" where "+where;
        return template.update(SQL);
    }
    //todo-> Update by specfic field name
    public int updateBy(String tableName, String set, String where, String equalsTo){
        try{
            String SQL = "update "+tableName+" set "+set+" where "+where+"="+equalsTo;
            return template.update(SQL);
        } catch(Exception ex){
            String SQL = "update "+tableName+" set "+set+" where "+where+"='"+equalsTo+"'";
            return template.update(SQL);            
        }
    }
    
    //! DELETE STATMENTS
    public int deleteById(String tableName, String where, String equalsTo){
        try{
            String SQL = "delete from "+tableName+" where "+where+"="+equalsTo;
            return template.update(SQL);
        } catch(Exception ex){
            String SQL = "delete from "+tableName+" where "+where+"='"+equalsTo+"'";
            return template.update(SQL);            
        }
    }
}
