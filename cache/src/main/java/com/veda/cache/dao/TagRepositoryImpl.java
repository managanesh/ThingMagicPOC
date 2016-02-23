package com.veda.cache.dao;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.PreparedStatement;
import com.veda.cache.model.ShelfInv;

@Repository
public class TagRepositoryImpl {
	
	@Autowired
	DataSource datasource;
	
	JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("deprecation")
	@PostConstruct
	public void init(){
		//datasource.
		jdbcTemplate = new JdbcTemplate(datasource);
		System.out.println("Result: "+jdbcTemplate.queryForInt("select 1 from dual"));
	}
	
	public boolean updateShelfInvInvalid(List<ShelfInv> shelfInvs){
		if(shelfInvs!=null && shelfInvs.isEmpty()){
			return false;
		}
		
		System.out.println("data to be inserted size: "+shelfInvs.size());
		 String sql = "UPDATE ts_shelf_inv set ItemStatus='N' where Rfid=? and ItemStatus='Y'";
		 
		 
		 System.out.println("SQL: "+sql);
		 //jdbcTemplate.update("insert into ts_shelf_inv(Rfid) VALUES(?,'test1234');");
		int i[]= jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(java.sql.PreparedStatement ps, int i) throws SQLException {
					ShelfInv shelfInv = shelfInvs.get(i);
					ps.setString(1, shelfInv.getRfId());
				}
						
				@Override
				public int getBatchSize() {
					return shelfInvs.size();
				}
						
			  });
		//if(true) throw new RuntimeException();
		System.out.println("Done.........: "+i.length);
		return true;
	}
	
	
	public boolean updateShelfInv(List<ShelfInv> shelfInvs){
		if(shelfInvs!=null && shelfInvs.isEmpty()){
			return false;
		}
		System.out.println("data to be inserted size: "+shelfInvs.size());
		 String sql = "INSERT into ts_shelf_inv (Shelfdetid, Rfid, ItemStatus, Itemaddedby, Itemadddt, lastuser, lastupdttm, SentStatus)"
				 +" select ?, ?, ?,?, ?, ?,?, ? from dual where NOT EXISTS (select Rfid from ts_shelf_inv where Rfid =? and ItemStatus ='Y')";
		 System.out.println("SQL: "+sql);
		int i[]= jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(java.sql.PreparedStatement ps, int i) throws SQLException {
					ShelfInv shelfInv = shelfInvs.get(i);
					ps.setString(1, shelfInv.getShelfDetId());
					ps.setString(2, shelfInv.getRfId());
					ps.setString(3, shelfInv.getItemStatus());
					ps.setString(4, shelfInv.getItemAddedBy());
					ps.setDate(5, shelfInv.getItemAddDate());
					ps.setString(6, shelfInv.getLastUser());
					ps.setDate(7, shelfInv.getLastUpdttm());
					ps.setString(8, shelfInv.getSentStatus());
					ps.setString(9, shelfInv.getRfId()); 
				
				}
						
				@Override
				public int getBatchSize() {
					return shelfInvs.size();
				}
						
			  });
		//if(true) throw new RuntimeException();
		System.out.println("Done.........: "+i.length);
		return true;
	}
	
	
	
	

}
