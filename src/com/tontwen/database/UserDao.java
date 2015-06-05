package com.tontwen.database;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;








//import com.sun.crypto.provider.RSACipher;
import com.tontwen.bottledetection.BottleDetectNumber_RptNo;
import com.tontwen.bottledetection.BottleInfo_ValveInfo;
import com.tontwen.bottledetection.BottleValveChangeResult;
import com.tontwen.bottledetection.InnerDryResult;
//import com.tontwen.bottledetection.BottleInfo;
import com.tontwen.bottledetection.TestWaited;
import com.tontwen.bottledetection.ChubuPanduanResult;
import com.tontwen.bottledetection.GlobalDetectionResult;
import com.tontwen.bottledetection.NoneDestructiveDetectionResult;
import com.tontwen.bottledetection.OperatorInfo;
import com.tontwen.bottledetection.BottleInfo_CarInfo;
import com.tontwen.bottledetection.ValveInfo;
import com.tontwen.bottledetection.WaterTestResult;
import com.tontwen.database.DBUtil;

public class UserDao {
	//login
	public OperatorInfo checkLogin(String operatorNumber,String operatorPwd){
		String sql = "select * from OperatorInfo where Operatornumber=? and OperatorPwd=?";
		String[] parameters = {operatorNumber,operatorPwd};
		//System.out.println(op.getOperatorNumber()+" "+op.getOperatorPwd());
		ResultSet rs = DBUtil.executeQuery(sql, parameters);
		OperatorInfo operatorInfo=new OperatorInfo();
		try {
			if(rs.next()){
				operatorInfo.setOperatorName(rs.getString("OperatorName"));
				operatorInfo.setOperatorNumber(rs.getString("Operatornumber"));
				operatorInfo.setOperatorPwd(rs.getString("OperatorPwd"));
				operatorInfo.setOperatorRights(rs.getString("OperatorRights"));
				return operatorInfo;
			}else{
				return null;
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
		return null;
	}

	//add new bottle
	public boolean executeAddBottleInfoCarInfo(BottleInfo_CarInfo bc){
		boolean result = true;
		String sql = "insert into BottleInfo_CarInfo(BottleNumber,CarNumber,BottleType,BottleMadeCountry,BottleMadeCompany,BottleMadeCompanyID,BottleMadeLicense,BottleNominalPressure,BottleWaterTestPressure,BottleDesignThickness,BottleActualWeight,BottleActualVolume,BottleMadeDate,BottleFirstInstallDate,BottleLastCheckDate,BottleNextCheckDate,BottleServiceYears,BottleBelonged,SaveDate,HasDeleted,BottleLicense,BottleGuige,BottleInstall,BottleStdVol) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,0,?,?,?,?)";
		String[] parameters = {bc.getBottleNumber(),bc.getCarNumber(),Integer.toString(bc.getBottleType()),bc.getBottleMadeCountry(),bc.getBottleMadeCompany(),bc.getBottleMadeCompanyID(),bc.getBottleMadeLicense(),bc.getBottleNominalPressure(),bc.getBottleWaterTestPressure(),bc.getBottleDesignThickness(),bc.getBottleActualWeight(),bc.getBottleActualVolume(),bc.getBottleMadeDate(),bc.getBottleFirstInstallDate(),bc.getBottleLastCheckDate(),bc.getBottleNextCheckDate(),Integer.toString(bc.getBottleServiceYears()),bc.getBottleBelonged(),bc.getSaveDate(),bc.getBottleLicense(),bc.getBottleGuige(),bc.getBottleInstall(),bc.getBottleStdVol()};
		try{
			DBUtil.executeUpdate(sql, parameters);
		}catch(Exception e){
			e.printStackTrace();
			result = false;
		}finally{
			DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());
		}
		
		//find whether this car exists or not
		sql = "select * from BottleDetectionLine.dbo.CarInfo where CarNumber = ?";
		String[] parameters2 = {bc.getCarNumber()};
		ResultSet rs = DBUtil.executeQuery(sql, parameters2);
		try {
			if(rs.next()){
				//update car info
				sql = "update BottleDetectionLine.dbo.CarInfo set CarType = ?, CarBelongedCompany = ?, CarBelongedCompanyAddress = ?, CarBelongedTel = ?, CarMadeFactory = ?, CarBelongedName = ? where CarNumber = ?";
				//String[] parameters3 = {"4","陈","","","","","川A12000"};
				String[] parameters3 = {Integer.toString(bc.getCarType()),bc.getCarBelongedCompany(),bc.getCarBelongedCompanyAddress(),bc.getCarBelongedTel(),bc.getCarMadeFactory(),bc.getCarBelongedName(),bc.getCarNumber()};
				try{
					DBUtil.executeUpdate(sql, parameters3);
				}catch(Exception e){
					e.printStackTrace();
					result = false;
				}finally{
					DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());
				}
			}else{
				sql = "insert into BottleDetectionLine.dbo.CarInfo values(?,?,?,?,?,?,?,0,0)";
				String[] parameters4 = {bc.getCarNumber(),Integer.toString(bc.getCarType()),bc.getCarBelongedCompany(),bc.getCarBelongedCompanyAddress(),bc.getCarBelongedTel(),bc.getCarMadeFactory(),bc.getCarBelongedName()};
				try{
					DBUtil.executeUpdate(sql, parameters4);
				}catch(Exception e){
					e.printStackTrace();
					result = false;
				}finally{
					DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());
				}
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
				bc.setCarNumber(rs.getString("CarNumber"));
				bc.setRfidNumber(rs.getString("RFIDNumber"));
				bc.setBottleMadeCountry(rs.getString("BottleMadeCountry"));
				bc.setBottleType(rs.getInt("BottleType"));
				bc.setBottleTypeC(rs.getString("BottleTypeC"));
				bc.setBottleMadeCompany(rs.getString("BottleMadeCompany"));
				bc.setBottleMadeCompanyID(rs.getString("BottleMadeCompanyID"));
				bc.setBottleMadeLicense(rs.getString("BottleMadeLicense"));
				bc.setBottleNominalPressure(rs.getString("BottleNominalPressure"));
				bc.setBottleWaterTestPressure(rs.getString("BottleWaterTestPressure"));
				bc.setBottleDesignThickness(rs.getString("BottleDesignThickness"));
				bc.setBottleActualWeight(rs.getString("BottleActualWeight"));
				bc.setBottleActualVolume(rs.getString("BottleActualVolume"));
				bc.setBottleMadeDate(rs.getString("BottleMadeDate"));
				bc.setBottleFirstInstallDate(rs.getString("BottleFirstInstallDate"));
				bc.setBottleLastCheckDate(rs.getString("BottleLastCheckDate"));
				bc.setBottleNextCheckDate(rs.getString("BottleNextCheckDate"));
				bc.setBottleServiceYears(rs.getInt("BottleServiceYears"));
				bc.setBottleBelonged(rs.getString("BottleBelonged"));
				bc.setSaveDate(rs.getString("SaveDate"));
				bc.setHasDeleted(rs.getInt("HasDeleted"));
				bc.setBottleLicense(rs.getString("BottleLicense"));
				bc.setBottleGuige(rs.getString("BottleGuige"));
				bc.setBottleInstall(rs.getString("BottleInstall"));
				bc.setBottleStdVol(rs.getString("BottleStdVol"));
				bc.setCarInfoID(rs.getString("CarInfoID"));
				bc.setCarType(rs.getInt("CarType"));
				bc.setCarTypeC(rs.getString("CarTypeC"));
				bc.setCarBelongedName(rs.getString("CarBelongedName"));
				bc.setCarMadeFactory(rs.getString("CarMadeFactory"));
				bc.setCarBelongedTel(rs.getString("CarBelongedTel"));
				bc.setCarBelongedCompanyAddress(rs.getString("CarBelongedCompanyAddress"));
				bc.setCarBelongedCompany(rs.getString("CarBelongedCompany"));
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

	//count pages
	public int getPageCount(int pageSize){
		int rowCount = 0;
		String sql = "select count(*) from BottleDetectionLine.dbo.BottleInfo_CarInfo"; //get the number of all records
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

	//return bottles waiting in first-step detection
	public ArrayList<BottleInfo_CarInfo> executeQueryBottleCP(){
		ArrayList<BottleInfo_CarInfo> list = new ArrayList<BottleInfo_CarInfo>();
		String sql = "select * from BottleDetectionLine.dbo.BottleInfo_CarInfo where BottleNumber not in (select bottlenumber from BottleDetectionLine.dbo.BottleInfo_BottleDectectInfo) order by SaveDate desc";
		ResultSet rs = DBUtil.executeQuery(sql,null);
		try {
			while(rs.next()){
				BottleInfo_CarInfo bc = new BottleInfo_CarInfo();
				bc.setBottleNumber(rs.getString("BottleNumber"));
				bc.setCarNumber(rs.getString("CarNumber"));
				bc.setRfidNumber(rs.getString("RFIDNumber"));
				bc.setBottleMadeCountry(rs.getString("BottleMadeCountry"));
				bc.setBottleType(rs.getInt("BottleType"));
				bc.setBottleTypeC(rs.getString("BottleTypeC"));
				bc.setBottleMadeCompany(rs.getString("BottleMadeCompany"));
				bc.setBottleMadeCompanyID(rs.getString("BottleMadeCompanyID"));
				bc.setBottleMadeLicense(rs.getString("BottleMadeLicense"));
				bc.setBottleNominalPressure(rs.getString("BottleNominalPressure"));
				bc.setBottleWaterTestPressure(rs.getString("BottleWaterTestPressure"));
				bc.setBottleDesignThickness(rs.getString("BottleDesignThickness"));
				bc.setBottleActualWeight(rs.getString("BottleActualWeight"));
				bc.setBottleActualVolume(rs.getString("BottleActualVolume"));
				bc.setBottleMadeDate(rs.getString("BottleMadeDate"));
				bc.setBottleFirstInstallDate(rs.getString("BottleFirstInstallDate"));
				bc.setBottleLastCheckDate(rs.getString("BottleLastCheckDate"));
				bc.setBottleNextCheckDate(rs.getString("BottleNextCheckDate"));
				bc.setBottleServiceYears(rs.getInt("BottleServiceYears"));
				bc.setBottleBelonged(rs.getString("BottleBelonged"));
				bc.setSaveDate(rs.getString("SaveDate"));
				bc.setHasDeleted(rs.getInt("HasDeleted"));
				bc.setBottleLicense(rs.getString("BottleLicense"));
				bc.setBottleGuige(rs.getString("BottleGuige"));
				bc.setBottleInstall(rs.getString("BottleInstall"));
				bc.setBottleStdVol(rs.getString("BottleStdVol"));
				bc.setCarInfoID(rs.getString("CarInfoID"));
				bc.setCarType(rs.getInt("CarType"));
				bc.setCarTypeC(rs.getString("CarTypeC"));
				bc.setCarBelongedName(rs.getString("CarBelongedName"));
				bc.setCarMadeFactory(rs.getString("CarMadeFactory"));
				bc.setCarBelongedTel(rs.getString("CarBelongedTel"));
				bc.setCarBelongedCompanyAddress(rs.getString("CarBelongedCompanyAddress"));
				bc.setCarBelongedCompany(rs.getString("CarBelongedCompany"));
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

	//Execute first-step detection, and return bottle detection number, as well as report number
	public BottleDetectNumber_RptNo executeChubuPanduan(ChubuPanduanResult cpr){

		//System.out.println("cpResult = " + cpr.getPreDetectResult());
		BottleDetectNumber_RptNo bnrn = new BottleDetectNumber_RptNo();
		UserDao ud = new UserDao();
		String bdn = ud.generateBottleDetectNumber(cpr.getBottleType());
		String rptNo = ud.generateRptNo(cpr.getCarNumber());

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		String nowTime = simpleDateFormat.format(new java.util.Date());

		String sql = "";

		if (Integer.parseInt(cpr.getPreDetectResult()) == 1) {
			sql = "insert BottleDetectionLine.dbo.BottleDetectInfo(BottleDetectNumber,BottleNumber,FinalDetectResult,HasInstalled,BottleDetectStatus,PreDetectResult,PreDetectDetail,PreDetectOver,PreDetectOperator,PreDetectDate,GlobalDetectOver,NoneDestructiveOver,WeightVacuumOver,WaterTestOver,ThicknessResult,InnerDryOver,BottleValveChangeOver,AirProofTestResult,AirProofTestOver,VacuumPressure,VacuumOver,PreMemo,GlobalSub1,GlobalSub2,GlobalSub3,GlobalSub4,GlobalSub5,GlobalSub6,HasWriteRFID,RptNo,OperateState,HasPrint,CheckState) values(?,?,'-',0,0,1,'',1,?,?,0,0,0,0,2,0,0,'待检',0,0.09,0,'',1,1,1,1,1,1,0,?,0,0,0)";
			String[] parameters = {bdn,cpr.getBottleNumber(),cpr.getOperatorName(),nowTime,rptNo};
			DBUtil.executeUpdate(sql, parameters);
		}else if (Integer.parseInt(cpr.getPreDetectResult()) == 0) {
			sql = "insert BottleDetectionLine.dbo.BottleDetectInfo(BottleDetectNumber,BottleNumber,FinalDetectResult,FinalDetectDate,HasInstalled,BottleDetectStatus,PreDetectResult,PreDetectDetail,PreDetectOver,PreDetectOperator,PreDetectDate,GlobalDetectOver,NoneDestructiveOver,WeightVacuumOver,WaterTestOver,ThicknessResult,InnerDryOver,BottleValveChangeOver,AirProofTestResult,AirProofTestOver,VacuumOver,PreMemo,GlobalSub1,GlobalSub2,GlobalSub3,GlobalSub4,GlobalSub5,GlobalSub6,HasWriteRFID,FailPos,RptNo,OperateState,HasPrint,CheckState) values(?,?,'判废',?,0,0,0,'气瓶标志不清晰',1,?,?,0,0,0,0,2,0,0,'待检',0,0,'',1,1,1,1,1,1,0,'CP',?,0,0,0)";
			String[] parameters2 = {bdn,cpr.getBottleNumber(),nowTime,cpr.getOperatorName(),nowTime,rptNo};
			//System.out.println(sql);
			DBUtil.executeUpdate(sql, parameters2);
		}
		DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());

		bnrn.setBottleDetectNumber(bdn);
		bnrn.setRptNo(rptNo);

		return bnrn;
	}

	//generate Bottle Detection Number
	private String generateBottleDetectNumber(int bottleType) {
		String tmpNumber;
		String maxNumber = null;
		int maxNumberInt;

		//generate xx15000001
		if (bottleType == 0) {
			tmpNumber = "GP";
		}else if (bottleType == 1) {
			tmpNumber = "CR";
		}else {
			tmpNumber = "WZ";
		}

		//generate GPxx000001
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		String nowTime = simpleDateFormat.format(new java.util.Date());
		tmpNumber = tmpNumber + nowTime.substring(2, 4);

		//convert to no parameters
		String sql = "select max(BottleDetectNumber)as max_CLN from BottleDetectionLine.dbo.BottleDetectInfo where BottleDetectNumber like '";
		sql = sql + tmpNumber +"%'";
		//String[] parameters = {tmpNumber};
		ResultSet rs = DBUtil.executeQuery(sql, null);
		try {
			if(rs.next()){
				maxNumber = rs.getString("max_CLN");
				if (maxNumber == null) {
					maxNumber = tmpNumber.substring(0,2) + nowTime.substring(2, 4) + "000000";
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//generate GP15xxxxxx
		maxNumberInt = Integer.parseInt(maxNumber.substring(4)) + 1;
		if (maxNumberInt < 10) {
			tmpNumber = tmpNumber + "00000" + Integer.toString(maxNumberInt);
		}else if (maxNumberInt < 100) {
			tmpNumber = tmpNumber + "0000" + Integer.toString(maxNumberInt);
		}else if (maxNumberInt < 1000) {
			tmpNumber = tmpNumber + "000" + Integer.toString(maxNumberInt);
		}else if (maxNumberInt < 10000) {
			tmpNumber = tmpNumber + "00" + Integer.toString(maxNumberInt);
		}else if (maxNumberInt < 100000) {
			tmpNumber = tmpNumber + "0" + Integer.toString(maxNumberInt);
		}else if (maxNumberInt < 1000000) {
			tmpNumber = tmpNumber + Integer.toString(maxNumberInt);
		}
		return tmpNumber;
	}

	//generate Report Number
	private String generateRptNo(String carNumber) {
		String maxNumber = null;
		String tmpNumber = null;
		int maxNumberInt;

		//tmpNumber generates in the format QP2015
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		String nowTime = simpleDateFormat.format(new java.util.Date());

		String sql = "select max(RptNo)as max_CLN from BottleDetectionLine.dbo.RptInfo where RptNo like 'QP";
		sql = sql + nowTime.substring(0,4) + "%' and CarNumber = '";
		sql = sql + carNumber + "' and FinalDetectResult = '-'";

		ResultSet rs = DBUtil.executeQuery("select max(RptNo)as max_CLN from BottleDetectionLine.dbo.RptInfo where RptNo like 'QP" + nowTime.substring(0,4) + "%' and CarNumber = '" + carNumber + "' and FinalDetectResult = '-'", null);
		try {
			if(rs.next()){
				//get max report number of this car
				tmpNumber = rs.getString("max_CLN");
				if (tmpNumber == null) {
					sql = "select max(RptNo)as max_CLN from BottleDetectionLine.dbo.BottleDetectInfo where RptNo like '";
					sql = sql + "QP" + nowTime.substring(0,4) + "%'";
					//System.out.println(sql);
					rs = DBUtil.executeQuery(sql, null);
					try {
						if(rs.next()){
							//get max report number in BottleDetectInfo
							maxNumber = rs.getString("max_CLN");
							tmpNumber = "QP" + nowTime.substring(0, 4);

							//generate GP2015xxxxxx
							maxNumberInt = Integer.parseInt(maxNumber.substring(6)) + 1;
							if (maxNumberInt < 10) {
								tmpNumber = tmpNumber + "00000" + Integer.toString(maxNumberInt);
							}else if (maxNumberInt < 100) {
								tmpNumber = tmpNumber + "0000" + Integer.toString(maxNumberInt);
							}else if (maxNumberInt < 1000) {
								tmpNumber = tmpNumber + "000" + Integer.toString(maxNumberInt);
							}else if (maxNumberInt < 10000) {
								tmpNumber = tmpNumber + "00" + Integer.toString(maxNumberInt);
							}else if (maxNumberInt < 100000) {
								tmpNumber = tmpNumber + "0" + Integer.toString(maxNumberInt);
							}else if (maxNumberInt < 1000000) {
								tmpNumber = tmpNumber + Integer.toString(maxNumberInt);
							}
						} 
					}catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			} 
		}catch (SQLException e) {
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
		return tmpNumber;
	}

	//get bottles waiting in global detection
	public ArrayList<TestWaited> executeAllGlobalDetectWaitedBottleQuery(String bottleType){

		ArrayList<TestWaited> list  = new ArrayList<TestWaited>();
		String sql ="select BottleDetectNumber ,BottleNumber ,CarNumber ,BottleType  from dbo.BottleInfo_BottleDectectInfo "
				+ "where PreDetectOver =1 and GlobalDetectOver=0 and BottleType = ?";
		String[] parameters = {bottleType};
		ResultSet rs = DBUtil.executeQuery(sql, parameters);
		try {
			while(rs.next()){
				TestWaited gd = new TestWaited();
				gd.setBottleNumber(rs.getString("BottleNumber"));
				gd.setBottleType(rs.getString("BottleType"));
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
	
	public ArrayList<TestWaited> executeNoneDestructiveDetectWaitedBottleQuery(){

		ArrayList<TestWaited> list  = new ArrayList<TestWaited>();
		String sql ="select BottleDetectNumber ,BottleNumber ,CarNumber ,BottleType  from dbo.BottleInfo_BottleDectectInfo "
				+ "where NoneDestructiveOver =0 and GlobalDetectOver=1";
		String[] parameters = null;
		ResultSet rs = DBUtil.executeQuery(sql, parameters);
		try {
			while(rs.next()){
				TestWaited tw = new TestWaited();
				tw.setBottleNumber(rs.getString("BottleNumber"));
				tw.setBottleType(rs.getString("BottleType"));
				tw.setBottleDetectNumber(rs.getString("BottleDetectNumber"));
				tw.setCarNumber(rs.getString("CarNumber"));
				list.add(tw);
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
	
	public ArrayList<TestWaited> executeWaterTestWaitedBottleQuery(){

		ArrayList<TestWaited> list  = new ArrayList<TestWaited>();
		String sql ="select BottleDetectNumber ,BottleNumber ,CarNumber ,BottleType  from dbo.BottleInfo_BottleDectectInfo "
				+ "where WaterTestOver =0 and NoneDestructiveOver=1";
		String[] parameters =null;
		ResultSet rs = DBUtil.executeQuery(sql, parameters);
		try {
			while(rs.next()){
				TestWaited tw = new TestWaited();
				tw.setBottleNumber(rs.getString("BottleNumber"));
				tw.setBottleType(rs.getString("BottleType"));
				tw.setBottleDetectNumber(rs.getString("BottleDetectNumber"));
				tw.setCarNumber(rs.getString("CarNumber"));
				list.add(tw);
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
	
	public ArrayList<TestWaited> executeInnerDryWaitedBottleQuery(){

		ArrayList<TestWaited> list  = new ArrayList<TestWaited>();
		String sql ="select BottleDetectNumber ,BottleNumber ,CarNumber ,BottleType  from dbo.BottleInfo_BottleDectectInfo "
				+ "where WaterTestOver =1 and InnerDryOver=0";
		String[] parameters =null;
		ResultSet rs = DBUtil.executeQuery(sql, parameters);
		try {
			while(rs.next()){
				TestWaited tw = new TestWaited();
				tw.setBottleNumber(rs.getString("BottleNumber"));
				tw.setBottleType(rs.getString("BottleType"));
				tw.setBottleDetectNumber(rs.getString("BottleDetectNumber"));
				tw.setCarNumber(rs.getString("CarNumber"));
				list.add(tw);
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
	
	public ArrayList<BottleInfo_ValveInfo> executeValveChangeWaitedBottleQuery(){

		ArrayList<BottleInfo_ValveInfo> list  = new ArrayList<BottleInfo_ValveInfo>();
		String sql ="select BottleDetectNumber ,BottleNumber ,CarNumber ,BottleValveTestNo, BottleValveType, "
				+ "BottleValveTestPressure, BottleValveTestMedium  from dbo.BottleInfo_BottleDectectInfo "
				+ "where BottleValveChangeOver =0 and InnerDryOver=1";
		String[] parameters =null;
		ResultSet rs = DBUtil.executeQuery(sql, parameters);
		try {
			while(rs.next()){
				BottleInfo_ValveInfo bv = new BottleInfo_ValveInfo();
				bv.setBottleNumber(rs.getString("BottleNumber"));
				bv.setBottleDetectNumber(rs.getString("BottleDetectNumber"));
				bv.setCarNumber(rs.getString("CarNumber"));
				bv.setBottleValveTestNo(rs.getString("BottleValveTestNo"));
				bv.setBottleValveType(rs.getString("BottleValveType"));
				bv.setBottleValveTestPressure(rs.getString("BottleValveTestPressure"));
				bv.setBottleValveTestMedium(rs.getString("BottleValveTestMedium"));
				list.add(bv);
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
	
	public ArrayList<ValveInfo> executeValveQuery(){

		ArrayList<ValveInfo> list  = new ArrayList<ValveInfo>();
		String sql ="select ValveType ,ValveTestPressure ,ValveTestMedium from dbo.ValveInfo";
		String[] parameters =null;
		ResultSet rs = DBUtil.executeQuery(sql, parameters);
		try {
			while(rs.next()){
				ValveInfo vi = new ValveInfo();
				vi.setValveType(rs.getString("ValveType"));
				vi.setValveTestPressure(rs.getString("ValveTestPressure"));
				vi.setValveTestMedium(rs.getString("ValveTestMedium"));
				list.add(vi);
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
	
	public int executeGlobalDetect(GlobalDetectionResult gdr){
		int rc=0;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		String nowTime = simpleDateFormat.format(new java.util.Date());
		String detectDetailResult=gdr.getDetectDetailResult();
		System.out.println(detectDetailResult);
		String globalSub1,globalSub2,globalSub3,globalSub4,globalSub5,globalSub6;
		String sql="";
		if(gdr.getBottleType()=="0"){
			int globalDetectResult0=detectDetailResult=="111111" ? 1 : 0;
			globalSub1=detectDetailResult.substring(0, 1);
			globalSub2=detectDetailResult.substring(1, 2);
			globalSub3=detectDetailResult.substring(2, 3);
			globalSub4=detectDetailResult.substring(3, 4);
			globalSub5=detectDetailResult.substring(4, 5);
			globalSub6=detectDetailResult.substring(5, 6);
			if(globalDetectResult0==0){
				sql="update BottleDetectInfo set FinalDetectResult='判废', FinalDetectDate=?, GlobalDetectDetailResult=?,"
						+ "AppearDetail=?, SoundDetail=?, WhorlDetail=?, InnerDetail=?, GlobalDetectOver='1', GlobalDetectResult='0',"
						+ "GlobalDetectOperator=?, GlobalDetectDate=?, GlobalSub1=?, GlobalSub2=?, GlobalSub3=?, GlobalSub4=?,"
						+ "GlobalSub5=?, GlobalSub6=?, GlobalSub5Detail=?, GlobalSub6Detail=?, FailPos='HG'"
						+ "where BottleDetectNumber=?";
				String[] parameters1={nowTime,detectDetailResult,gdr.getAppearDetail(),gdr.getSoundDetail(),gdr.getWhorlDetail(),gdr.getInnerDetail(),gdr.getOperatorName(),nowTime,globalSub1,globalSub2,globalSub3,globalSub4,globalSub5,globalSub6,gdr.getGlobalSub5Detail(),gdr.getGlobalSub6Detail(),gdr.getBottleDetectNumber()};
				DBUtil.executeUpdate(sql, parameters1);
			}else{
				sql="update BottleDetectInfo set GlobalDetectDetailResult=?,"
						+ "AppearDetail=?, SoundDetail=?, WhorlDetail=?, InnerDetail=?, GlobalDetectOver='1', GlobalDetectResult='1',"
						+ "GlobalDetectOperator=?, GlobalDetectDate=?, GlobalSub5Detail=?, GlobalSub6Detail=?"
						+ "where BottleDetectNumber=?";
				String[] parameters2={detectDetailResult,gdr.getAppearDetail(),gdr.getSoundDetail(),gdr.getWhorlDetail(),gdr.getInnerDetail(),gdr.getOperatorName(),nowTime,gdr.getGlobalSub5Detail(),gdr.getGlobalSub6Detail(),gdr.getBottleDetectNumber()};
				DBUtil.executeUpdate(sql, parameters2);
			}
		}else{
			int globalDetectResult1;
//			int globalDetectResult1=detectDetailResult=="111" ? 1 : 0;
			System.out.println(detectDetailResult);
			if(detectDetailResult=="111"){
				globalDetectResult1=1;
			}else{
				globalDetectResult1=0;
			}
			System.out.println(globalDetectResult1);
			globalSub1=detectDetailResult.substring(0, 1);
			globalSub2=detectDetailResult.substring(1, 2);
			globalSub3=detectDetailResult.substring(2, 3);
			if(globalDetectResult1==0){
				sql="update BottleDetectInfo set FinalDetectResult='判废', FinalDetectDate=?, GlobalDetectDetailResult=?,"
						+ "AppearDetail=?, SoundDetail=?, GlobalDetectOver='1', GlobalDetectResult='0',"
						+ "GlobalDetectOperator=?, GlobalDetectDate=?, GlobalSub1=?, GlobalSub2=?, GlobalSub3=?, FailPos='HG'"
						+ "where BottleDetectNumber=?";
				String[] parameters3={nowTime,detectDetailResult,gdr.getAppearDetail(),gdr.getSoundDetail(),gdr.getOperatorName(),nowTime,globalSub1,globalSub2,globalSub3,gdr.getBottleDetectNumber()};
				DBUtil.executeUpdate(sql, parameters3);
			}else{
				sql="update BottleDetectInfo set GlobalDetectDetailResult=?,"
						+ "AppearDetail=?, SoundDetail=?, GlobalDetectOver='1', GlobalDetectResult='1',"
						+ "GlobalDetectOperator=?, GlobalDetectDate=?"
						+ "where BottleDetectNumber=?";
				String[] parameters4={detectDetailResult,gdr.getAppearDetail(),gdr.getSoundDetail(),gdr.getOperatorName(),nowTime,gdr.getBottleDetectNumber()};
				DBUtil.executeUpdate(sql, parameters4);
			}
		}
		rc=1;
		DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());
		return rc;
	}
	
	public int executeNoneDestructiveDetection(NoneDestructiveDetectionResult nddr){
		int rc=0;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		String nowTime = simpleDateFormat.format(new java.util.Date());
		String result=nddr.getNoneDestructiveResult();
		String sql="";
		sql="update BottleDetectInfo set FinalDetectResult=?, FinalDetectDate=?, NoneDestructivePositon=?, "
				+ "NoneDestructiveDetail=?, NoneDestructiveResult=?, NoneDestructiveOver=?, NoneDestructiveOperator=?, "
				+ "NoneDestructiveDate=?, FailPos=? where BottleDetectNumber=?";
		if(result=="0"){
			String[] parameters={"判废",nowTime,nddr.getNoneDestructivePosition(),nddr.getNoneDestructiveDetail(),"0","1",nddr.getOperatorName(),nowTime,"WS",nddr.getBottleDetectNumber()};
			DBUtil.executeUpdate(sql, parameters);
		}else{
			String[] parameters={"-",null,null,null,"1","1",nddr.getOperatorName(),nowTime,null,nddr.getBottleDetectNumber()};
			DBUtil.executeUpdate(sql, parameters);
		}
		rc=1;
		DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());
		return rc;
	}
	
	public int executeWaterTest(WaterTestResult wtr){
		int rc=0;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		String nowTime = simpleDateFormat.format(new java.util.Date());
		String result=wtr.getWaterTestResult();
		String sql="";
		sql="update BottleDetectInfo set FinalDetectResult=?, FinalDetectDate=?, "
				+ "WaterTestResult=?, WaterTestOver=?,  WaterTestOperator=?, "
				+ "WaterTestDate=?, FailPos=? where BottleDetectNumber=?";
		if(result=="0"){
			String[] parameters={"判废",nowTime,"0","1",wtr.getOperatorName(),nowTime,"WT",wtr.getBottleDetectNumber()};
			DBUtil.executeUpdate(sql, parameters);
		}else{
			String[] parameters={"-",null,"1","1",wtr.getOperatorName(),nowTime,null,wtr.getBottleDetectNumber()};
			DBUtil.executeUpdate(sql, parameters);
		}
		rc=1;
		DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());
		return rc;
	}
	
	public int executeInnerDry(InnerDryResult idr){
		int rc=0;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		String nowTime = simpleDateFormat.format(new java.util.Date());
		String sql="";
		sql="update BottleDetectInfo set InnerDryOver=1,  InnerDryOperator=?, "
				+ "InnerDryDate=? where BottleDetectNumber=?";
		String[] parameters={idr.getOperatorName(),nowTime,idr.getBottleDetectNumber()};
		DBUtil.executeUpdate(sql, parameters);
		rc=1;
		DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());
		return rc;
	}
	
	public int executeBottleValveChange(BottleValveChangeResult bvcr){
		int rc=0;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		String nowTime = simpleDateFormat.format(new java.util.Date());
		String sql="";
		sql="update BottleDetectInfo set BottleValveType=?, BottleValveTestPressure=?, BottleValveTestMedium=?, BottleValveTestNo=?, "
				+ "BottleValveChangeResult=?, BottleValveChangeOver=1,  BottleValveChangeOperator=?, "
				+ "BottleValveChangeDate=? where BottleDetectNumber=?";
		String[] parameters={bvcr.getBottleValveType(),bvcr.getBottleValveTestPressure(),bvcr.getBottleValveTestMedium(),
				bvcr.getBottleValveTestNo(),bvcr.getBottleValveChangeResult(),bvcr.getOperatorName(),nowTime,bvcr.getBottleDetectNumber()};
		DBUtil.executeUpdate(sql, parameters);
		rc=1;
		DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());
		return rc;
	}
	
	public int addValveInfo(ValveInfo vi){
		int rc=0;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		String nowTime = simpleDateFormat.format(new java.util.Date());
		String sql="";
		sql="insert into ValveInfo (ValveType,ValveTestPressure,ValveTestMedium,ValveAddOperator,ValveAddTime,ValveDefault) "
				+ "values (?,?,?,?,?,0)";
		String[] parameters={vi.getValveType(),vi.getValveTestPressure(),vi.getValveTestMedium(),vi.getValveAddOperator(),nowTime};
		DBUtil.executeUpdate(sql, parameters);
		rc=1;
		DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());
		return rc;
	}
	
	public int deleteValveInfo(ValveInfo vi){
		int rc=0;
		String sql="";
		sql="delete from ValveInfo where ValveType=?";
		String[] parameters={vi.getValveType()};
		DBUtil.executeUpdate(sql, parameters);
		rc=1;
		DBUtil.close(DBUtil.getConn(), DBUtil.getPs(), DBUtil.getRs());
		return rc;
	}

	/*public Staff execute(Staff user){
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

	public ArrayList<Staff> executeQueryOnCondition(String attribute,String text){
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

	public boolean executeUpdate(Staff user){
		boolean result = true;
		System.out.println(user.getStaffno());
		String sql = "update STAFF set 
NAME=?,sex=?,hometown=?,telephonenumber=?,id=?,education=?,school=?,schooltype=?,major=?,foreignl
anguagelevel=?,graduationdate=?,td=?,epw=?,exp=?,teamname=?,senddate=?,stafftype=?,project=?,exd=
?,qexd=?,staffcondition=? where staffno=?";
		String[] parameters = {user.getName(),user.getSex(),user.getHometown(),user.getTelephonenumber(),
				user.getId(),user.getEducation(),user.getSchool(),user.getSchooltype(),user.getMajor(),
				
user.getForeignlanguagelevel(),user.getGraduationdate(),user.getTd(),user.getEpw(),user.g
etExp(),
				
user.getTeamname(),user.getSenddate(),user.getStafftype(),user.getProject(),user.getExd()
,user.getQexd(),user.getStaffcondition(),user.getStaffno()};

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
	}*/
}