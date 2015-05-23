package com.tontwen.database;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.tontwen.bottledetection.BottleInfo;
import com.tontwen.bottledetection.GlobalDetectWaitedBottle;
import com.tontwen.bottledetection.OperatorInfo;
import com.tontwen.bottledetection.BottleInfo_CarInfo;
import com.tontwen.database.DBUtil;

@SuppressWarnings("unused")
public class UserDao {
	//login
	public boolean isLoginSuccess(OperatorInfo op){
		boolean result = true;
		String sql = "select * from OperatorInfo where Operatornumber=? and OperatorPwd=?";
		String[] parameters = {op.getOperatorNumber(),op.getOperatorPwd()};
		System.out.println(op.getOperatorNumber()+" "+op.getOperatorPwd());
		ResultSet rs = DBUtil.executeQuery(sql, parameters);
		try {
			if(rs.next()){
				result = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}finally{
			DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/*	
	public Staff execute(Staff user){
		Staff u = new Staff();
		String sql = "select * from users where username=? and password=?";
		String[] parameters = {u.getName()};
		ResultSet rs = DBUtil.executeQuery(sql, parameters);
		try {
			if(rs.next()){
				u.setId(rs.getInt("id"));
				u.setName(rs.getString("username"));
				u.setPassword(rs.getString("password"));
				u.setEmail(rs.getString("email"));
				u.setHiredate(rs.getString("hiredate"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return u;
	}
	*/

	//鏌ヨ鎵�湁绗﹀悎鏉′欢鐨勮褰�
	
	
	/*
	
	public ArrayList<Staff> executeQueryAll(String attribute,String text){
		ArrayList<Staff> list  = new ArrayList<Staff>();
		String sql = "select * from Staff where "+attribute+"=?";
		String[] parameters = {text};
		ResultSet rs = DBUtil.executeQuery(sql, parameters);
		try {
			while(rs.next()){
				Staff user = new Staff();
				user.setName(rs.getString("name"));
				user.setSex(rs.getString("sex"));
				user.setHometown(rs.getString("hometown"));
				user.setStaffno(rs.getString("staffno"));
				user.setTelephonenumber(rs.getString("telephonenumber"));
				user.setId(rs.getString("id"));
				user.setEducation(rs.getString("education"));
				user.setSchool(rs.getString("school"));
				user.setSchooltype(rs.getString("schooltype"));
				user.setMajor(rs.getString("major"));
				user.setForeignlanguagelevel(rs.getString("foreignlanguagelevel"));
				user.setGraduationdate(rs.getString("graduationdate"));
				user.setTd(rs.getString("td"));
				user.setEpw(rs.getString("epw"));
				user.setExp(rs.getString("exp"));
				user.setTeamname(rs.getString("teamname"));
				user.setSenddate(rs.getString("senddate"));
				user.setStafftype(rs.getString("stafftype"));
				user.setProject(rs.getString("project"));
				user.setExd(rs.getString("exd"));
				user.setQexd(rs.getString("qexd"));
				user.setStaffcondition(rs.getString("staffcondition"));
				list.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	//鏌ヨ鎵�湁璁板綍
	public ArrayList<Staff> executeQuery(){
		ArrayList<Staff> list  = new ArrayList<Staff>();
		String sql = "select * from Staff";
		
		ResultSet rs = DBUtil.executeQuery(sql, null);
		try {
			while(rs.next()){
				Staff user = new Staff();
				user.setName(rs.getString("name"));
				user.setSex(rs.getString("sex"));
				user.setHometown(rs.getString("hometown"));
				user.setStaffno(rs.getString("staffno"));
				user.setTelephonenumber(rs.getString("telephonenumber"));
				user.setId(rs.getString("id"));
				user.setEducation(rs.getString("education"));
				user.setSchool(rs.getString("school"));
				user.setSchooltype(rs.getString("schooltype"));
				user.setMajor(rs.getString("major"));
				user.setForeignlanguagelevel(rs.getString("foreignlanguagelevel"));
				user.setGraduationdate(rs.getString("graduationdate"));
				user.setTd(rs.getString("td"));
				user.setEpw(rs.getString("epw"));
				user.setExp(rs.getString("exp"));
				user.setTeamname(rs.getString("teamname"));
				user.setSenddate(rs.getString("senddate"));
				user.setStafftype(rs.getString("stafftype"));
				user.setProject(rs.getString("project"));
				user.setExd(rs.getString("exd"));
				user.setQexd(rs.getString("qexd"));
				user.setStaffcondition(rs.getString("staffcondition"));
				list.add(user);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	//鏇存柊--鍒犻櫎涓�釜STAFF
	public boolean executeDelete(String userid){
		boolean result = true;
		String sql = "delete from STAFF where STAFFNO=?";
		String[] parameters={userid};
		try{
			DBUtil.executeUpdate(sql,parameters);
		}catch(Exception e){
			e.printStackTrace();
			result = false;
		}finally{
			DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());
		}
		return result;
	}
	
	//淇敼涓�釜鐢ㄦ埛淇℃伅
	public boolean executeUpdate(Staff user){
		boolean result = true;
		System.out.println(user.getStaffno());
		String sql = "update STAFF set NAME=?,sex=?,hometown=?,telephonenumber=?,id=?,education=?,school=?,schooltype=?,major=?,foreignlanguagelevel=?,graduationdate=?,td=?,epw=?,exp=?,teamname=?,senddate=?,stafftype=?,project=?,exd=?,qexd=?,staffcondition=? where staffno=?";
		String[] parameters = {user.getName(),user.getSex(),user.getHometown(),user.getTelephonenumber(),
				user.getId(),user.getEducation(),user.getSchool(),user.getSchooltype(),user.getMajor(),
				user.getForeignlanguagelevel(),user.getGraduationdate(),user.getTd(),user.getEpw(),user.getExp(),
				user.getTeamname(),user.getSenddate(),user.getStafftype(),user.getProject(),user.getExd(),user.getQexd(),user.getStaffcondition(),user.getStaffno()};
		
		try{
			DBUtil.executeUpdate(sql,parameters);
		}catch(Exception e){
			e.printStackTrace();
			result = false;
		}finally{
			DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());
		}
		System.out.println(result);
		return result;
	}
	
	//鏍规嵁ID鏌ュ嚭鐢ㄦ埛
	public Staff getUserById(String staffno){
		Staff user = new Staff();
		String sql = "select * from Staff where staffno=?";
		String[] parameters={staffno};
		ResultSet rs = DBUtil.executeQuery(sql, parameters);
		try {
			if(rs.next()){
				user.setName(rs.getString("name"));
				user.setSex(rs.getString("sex"));
				user.setHometown(rs.getString("hometown"));
				user.setTelephonenumber(rs.getString("telephonenumber"));
				user.setId(rs.getString("id"));
				user.setEducation(rs.getString("education"));
				user.setSchool(rs.getString("school"));
				user.setSchooltype(rs.getString("schooltype"));
				user.setMajor(rs.getString("major"));
				user.setForeignlanguagelevel(rs.getString("foreignlanguagelevel"));
				user.setGraduationdate(rs.getString("graduationdate"));
				user.setTd(rs.getString("td"));
				user.setEpw(rs.getString("epw"));
				user.setExp(rs.getString("exp"));
				user.setTeamname(rs.getString("teamname"));
				user.setSenddate(rs.getString("senddate"));
				user.setStafftype(rs.getString("stafftype"));
				user.setProject(rs.getString("project"));
				user.setExd(rs.getString("exd"));
				user.setQexd(rs.getString("qexd"));
				user.setStaffcondition(rs.getString("staffcondition"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return user;
	}
	*/
	
	//add new bottle
	public boolean executeAddBottleInfo(BottleInfo bi){
		boolean result = true;
		String sql = "insert into BottleInfo(BottleNumber,CarNumber,BottleType,BottleMadeCountry,BottleMadeCompany,BottleMadeCompanyID,BottleMadeLicense,BottleNominalPressure,BottleWaterTestPressure,BottleDesignThickness,BottleActualWeight,BottleActualVolume,BottleMadeDate,BottleFirstInstallDate,BottleLastCheckDate,BottleNextCheckDate,BottleServiceYears,BottleBelonged,SaveDate,HasDeleted,BottleLicense,BottleGuige,BottleInstall,BottleStdVol) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,0,?,?,?,?)";
		String[] parameters = {bi.getBottleNumber(),bi.getCarNumber(),Integer.toString(bi.getBottleType()),bi.getBottleMadeCountry(),bi.getBottleMadeCompany(),bi.getBottleMadeCompanyID(),bi.getBottleMadeLicense(),bi.getBottleNominalPressure(),bi.getBottleWaterTestPressure(),bi.getBottleDesignThickness(),bi.getBottleActualWeight(),bi.getBottleActualVolume(),bi.getBottleMadeDate(),bi.getBottleFirstInstallDate(),bi.getBottleLastCheckDate(),bi.getBottleNextCheckDate(),Integer.toString(bi.getBottleServiceYears()),bi.getBottleBelonged(),bi.getSaveDate(),bi.getBottleLicense(),bi.getBottleGuide(),bi.getBottleInstall(),bi.getBottleStdVol()};
		try{
			DBUtil.executeUpdate(sql, parameters);
		}catch(Exception e){
			e.printStackTrace();
			result = false;
		}finally{
			DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());
		}
		return result;
	}
	
	
	//count pages
	public int getPageCount(int pageSize){
		int rowCount = 0;
		String sql = "select count(*) from users"; //get the number of all records
		ResultSet rs = null;;
		
		try {
			rs = DBUtil.executeQuery(sql, null);
			rs.next();
			rowCount = rs.getInt(1); //getInt is a method used to get the value which is an Int of the column designated in ()
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return (rowCount-1)/pageSize+1;
	}

	//all bottles by page
	public ArrayList<BottleInfo_CarInfo> executeAllBottleQueryByPage(int pageNow,int pageSize){
		ArrayList<BottleInfo_CarInfo> list  = new ArrayList<BottleInfo_CarInfo>();
		String sql = "select * from (select row_number()over(order by SaveDate DESC)rownumber,* from BottleDetectionLine.dbo.BottleInfo_CarInfo)a where rownumber between ? and ?";
		String[] parameters= {((pageNow-1)*pageSize+1)+"",(pageNow*pageSize)+""};
		ResultSet rs = DBUtil.executeQuery(sql, parameters);
		try {
			while(rs.next()){
				BottleInfo_CarInfo bc = new BottleInfo_CarInfo();
				bc.setBottleNumber(rs.getString("BottleNumber"));
				bc.setBottleType(rs.getInt("BottleType"));
				bc.setBottleMadeCountry(rs.getString("BottleMadeCountry"));
				bc.setBottleMadeCompany(rs.getString("BottleMadeCompany"));
				bc.setBottleMadeCompanyID(rs.getString("BottleMadeCompanyID"));
				bc.setBottleMadeLicense(rs.getString("BottleMadeLicense"));
				bc.setBottleBelonged(rs.getString("BottleBelonged"));
				bc.setBottleServiceYears(rs.getInt("BottleServiceYears"));
				bc.setBottleNominalPressure(rs.getString("BottleNominalPressure"));
				bc.setBottleWaterTestPressure(rs.getString("BottleWaterTestPressure"));
				bc.setBottleDesignThickness(rs.getString("BottleDesignThickness"));
				bc.setBottleActualWeight(rs.getString("BottleActualWeight"));
				bc.setBottleActualVolume(rs.getString("BottleActualVolume"));
				//bc.setBottleNominalVolume(rs.getString("BottleNominalVolume"));
				bc.setBottleMadeDate(rs.getString("BottleMadeDate"));
				bc.setBottleFirstInstallDate(rs.getString("BottleFirstInstallDate"));
				bc.setBottleLastCheckDate(rs.getString("BottleLastCheckDate"));
				bc.setBottleNextCheckDate(rs.getString("BottleNextCheckDate"));
				bc.setBottleLicense(rs.getString("BottleLicense"));
				//bc.setBottleGuide(rs.getString("BottleGuide"));
				bc.setBottleInstall(rs.getString("BottleInstall"));
				bc.setCarNumber(rs.getString("CarNumber"));
				bc.setCarType(rs.getInt("CarType"));
				bc.setCarMadeFactory(rs.getString("CarMadeFactory"));
				bc.setCarBelongedName(rs.getString("CarBelongedName"));
				bc.setCarBelongedTel(rs.getString("CarBelongedTel"));
				bc.setCarBelongedCompany(rs.getString("CarBelongedCompany"));
				bc.setCarBelongedCompanyAddress(rs.getString("CarBelongedCompanyAddress"));
				list.add(bc);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	//count records
	public int getCount(String condition){
		int Count=0;
		String sql="select count(name) where "+condition+"=?";
		ResultSet rs = null;	
				try {
					rs = DBUtil.executeQuery(sql, null);
					rs.next();
					Count = rs.getInt(1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());
					try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return Count;
		}
	
	public ArrayList<GlobalDetectWaitedBottle> executeAllGlobalDetectWaitedBottleQuery(){
		ArrayList<GlobalDetectWaitedBottle> list  = new ArrayList<GlobalDetectWaitedBottle>();
		String sql ="select BottleDetectNumber ,BottleNumber ,CarNumber ,BottleType  from dbo.BottleInfo_BottleDectectInfo "
				+ "where PreDetectOver =1 and GlobalDetectOver=0";
		ResultSet rs = DBUtil.executeQuery(sql, null);
		try {
			while(rs.next()){
				GlobalDetectWaitedBottle gd = new GlobalDetectWaitedBottle();
				gd.setBottleNumber(rs.getString("BottleNumber"));
				gd.setBottleType(rs.getInt("BottleType"));
				gd.setBottleDetectNumber(rs.getString("BottleDetectNumber"));
				gd.setCarNumber(rs.getString("CarNumber"));
				list.add(gd);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
}