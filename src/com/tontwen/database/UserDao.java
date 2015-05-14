package com.tontwen.database;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.tontwen.bottledetection.OperatorInfo;
import com.tontwen.database.DBUtil;

@SuppressWarnings("unused")
public class UserDao {
	//login
	public boolean isLoginSuccess(OperatorInfo op){
		String sql = "select * from OperatorInfo where Operatornumber=? and OperatorPwd=?";
		String[] parameters = {op.getOperatorNumber(),op.getOperatorPwd()};
		System.out.println(op.getOperatorNumber()+" "+op.getOperatorPwd());
		ResultSet rs = DBUtil.executeQuery(sql, parameters);
		boolean result = false;
		try {
			if(rs.next()){
				result = true;
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
	
	//鏇存柊-娣诲姞涓�釜鐢ㄦ埛
	/*public boolean executeAddUser(Staff user){
		boolean result = true;
		//鑾峰彇褰撳墠鏃堕棿
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M'鏈�-yyyy"); 
		String nowTime = simpleDateFormat.format(new java.util.Date());
		System.out.println(nowTime);
		
		String sql = "insert into users(id,username,password,email,hiredate) values(user_seq.nextval,?,?,?,?)";
		String[] parameters = {user.getUsername(),user.getPassword(),user.getEmail(),nowTime};
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
	
	//鏍规嵁鎻愪緵姣忛〉鏄剧ず鐨勮褰曟暟璁＄畻鍑哄叡鏈夊灏戦〉
	public int getPageCount(int pageSize){
		int rowCount = 0;
		String sql = "select count(*) from users";
		ResultSet rs = null;;
		
		try {
			rs = DBUtil.executeQuery(sql, null);
			rs.next();
			rowCount = rs.getInt(1);
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
	//鍒嗛〉鏌ヨ
	public ArrayList<Staff> executeQueryByPage(int pageNow,int pageSize){
		ArrayList<Staff> list  = new ArrayList<Staff>();
		String sql = "select * from (select a1.*, rownum rn from(select * from users order by id) a1 where rownum<=?) where rn>=?";
		String[] parameters= {(pageNow*pageSize)+"",((pageNow-1)*pageSize+1)+""};
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
	
	
	
	
	
	*/
}