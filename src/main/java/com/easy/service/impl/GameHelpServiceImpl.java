package com.easy.service.impl;

import com.easy.entity.LotteryInfo;
import com.easy.service.GameHelpService;
import com.easy.service.LotteryInfoService;
import com.easy.service.LotteryMethodService;
import com.easy.service.LotteryRatioInstantService;
import com.easy.vo.LotteryInitVo;
import com.easy.vo.LotteryMethodInitVo;
import com.easy.vo.LotteryRatioInstantInitVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class GameHelpServiceImpl implements GameHelpService, InitializingBean {
	private static final Map<String,Map<String,String>> PARAM_MAP=new HashMap<String, Map<String,String>>();
	private static final Map<Integer,Map<String,Integer>> COMM_MAP=new HashMap<Integer, Map<String,Integer>>();
	private static final Map<Integer,Map<String,String>> NAME_MAP=new HashMap<Integer, Map<String,String>>();
	private static final Map<Integer,String> LOTT_ID_CODE_MAP=new HashMap<Integer, String>();
	private static final Map<String,String> LOTT_ID_NAME_MAP=new HashMap<String, String>();
	@Autowired
	private LotteryInfoService lotteryInfoService;
	@Autowired
	private LotteryMethodService lotteryMethodService;
	@Autowired
	private LotteryRatioInstantService lotteryRatioInstantService;

	private void initCommMap(){
		List<LotteryMethodInitVo> initVos = lotteryMethodService.findListVo();
		int lottId=initVos.get(0).getLotteryId();
		Map<String,Integer> lottCommMap = new HashMap<String,Integer>();
		for(LotteryMethodInitVo rec : initVos){
			if( rec.getLotteryId()!=lottId){
				COMM_MAP.put(lottId, lottCommMap);
				lottCommMap = new HashMap<String,Integer>();
				lottId=rec.getLotteryId();
			}
			lottCommMap.put(rec.getItemId()+"", rec.getCommId());
		}
		COMM_MAP.put(lottId, lottCommMap);
		initMethodNameMap();
	}
	@Override
	public Map<String,Integer> getCommMap(int lotteryId){
		return COMM_MAP.get(lotteryId);
	}

	private void initMethodNameMap(){
		List<LotteryRatioInstantInitVo> initVos = lotteryRatioInstantService.findVos();
		int lottId=initVos.get(0).getLotteryId();
		Map<String,String> lottNameMap = new HashMap<String,String>();
		for(LotteryRatioInstantInitVo rec : initVos){
			if( rec.getLotteryId()!=lottId){
				NAME_MAP.put(lottId, lottNameMap);
				lottNameMap = new HashMap<String,String>();
				lottId=rec.getLotteryId();
			}
			String methodName = rec.getMethodName();
			int lotteryId = rec.getLotteryId();
			if((lotteryId>=90 || lotteryId < 100) && "正特".equals(methodName)){
				methodName = rec.getSubName();
			}
			lottNameMap.put(rec.getItemId()+"", methodName+"#"+rec.getItemName());
		}
		NAME_MAP.put(lottId, lottNameMap);
	}
	@Override
	public Map<String,String> getNameMap(int lotteryId){
		return NAME_MAP.get(lotteryId);
	}

	private void initLottMap(){
		List<LotteryInitVo> initVos = lotteryInfoService.initFindVos();
		for( LotteryInitVo rec : initVos){
			LOTT_ID_CODE_MAP.put(rec.getLotteryId(), rec.getLotteryCode());
			LOTT_ID_NAME_MAP.put(""+rec.getLotteryId(), rec.getLotteryName());
			LOTT_ID_NAME_MAP.put(rec.getLotteryCode(), rec.getLotteryName());
		}
	}
	@Override
	public Map<String,String> getLottMap(){
		return LOTT_ID_NAME_MAP;
	}
	@Override
	public String getLotteryCode(int lotteryId){
		return LOTT_ID_CODE_MAP.get(lotteryId);
	}
	@Override
	public String getLotteryName(int lotteryId){
		return LOTT_ID_NAME_MAP.get(lotteryId);
	}

//	public static Map<String,String> getBankcodeMap(){
//		Map<String,String> bankcodeMap = new HashMap<String,String>();
//		List<Record> recList=Db.find("SELECT provider_code, bank_name, bank_code1 FROM sys_thirdparty_bank WHERE status=1");
//		for( Record rec : recList) bankcodeMap.put(rec.getStr("bank_code1"), rec.getStr("bank_name"));
//		return bankcodeMap;
//	}

	@Override
	public Map<String,String> getLottNameMap(){
		Map<String,String> lottNameMap = new HashMap<String,String>();
		List<LotteryInitVo> initVos = lotteryInfoService.initFindVos();
		for( LotteryInitVo rec : initVos){
			lottNameMap.put(""+rec.getLotteryId(), rec.getLotteryName());
			lottNameMap.put(rec.getLotteryCode(), rec.getLotteryName());
		}
		return lottNameMap;
	}
	@Override
	public List<LotteryInfo> getLotteryList(){
		return lotteryInfoService.getLotteryList();
	}

	public String getLotteryCodeName(){
		List<LotteryInitVo> initVos = lotteryInfoService.getLotteryCodeName();
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		if(initVos.size()>0) {
			for(LotteryInitVo rec:initVos){
				sb.append("\"").append(rec.getLotteryCode()).append("\":\"").append(rec.getLotteryName()).append("\",");
			}
			sb.replace(sb.length()-1, sb.length(), "}");
		}else{
			sb.append("}");
		}
		return sb.toString();
	}
	@Override
	public String getLotteryCode(){
		List<LotteryInfo> list = lotteryInfoService.getLotteryList();
		StringBuilder sb=new StringBuilder();
		if( list.size()>0){
			for(LotteryInfo rec:list) sb.append("|").append(rec.getLotteryCode());
			sb.delete(0, 1);
		}
		return sb.toString();
	}

//
//	public static Map<String,Integer> getPlayIssueMoney(int lotteryId, int userId, int tryFlag, String issue, String itemIds, String contents){
//		String sql = "SELECT item_id,item_code,content,SUM(money) money FROM "+(tryFlag==2?"test_play":"user_play");
//		sql += " WHERE lottery_id=? AND issue=? AND user_id=? AND item_id IN("+itemIds+")"+(contents==null?"":" AND content IN("+contents+")");
//		sql += " GROUP BY item_id,item_code,content";
//		//System.out.println("sql="+sql+"; lotteryId="+lotteryId+"; issue="+issue+"; userId="+userId);
//		List<Record> recList=Db.find(sql, lotteryId, issue, userId);
//		Map<String,Integer> moneyMap=new HashMap<String,Integer>();
//		for(Record rec : recList){
//			moneyMap.put(rec.getStr("item_code")+"_"+rec.getStr("content"), rec.getBigDecimal("money").intValue());
//		}
//		return moneyMap;
//	}
//
//	//定位玩法号码限制检查
//	public static int getPlayContent(int lotteryId, int userId, int tryFlag, String issue, String itemCode, Map<String,String> dwMethodMap){
//		String sql="SELECT item_code,content FROM "+(tryFlag==2?"test_play":"user_play")+" WHERE lottery_id=? AND issue=? AND user_id=? AND item_code IN("+itemCode+") GROUP BY item_code,content";
//		List<Record> recList=Db.find(sql, lotteryId, issue, userId);
//		//System.out.println("getPlayContent sql="+String.format(sql.replaceAll("\\?", "\\%s"),""+lotteryId, issue, ""+userId));
//		if( recList.size()==0 ) return 0;
//
//		String code="", content="", contents="";
//		for(Record rec : recList){
//			code=rec.getStr("item_code");
//			content=rec.getStr("content");
//			contents=dwMethodMap.get(code);
//			if( !(","+contents+",").contains(","+content+",")){
//				dwMethodMap.put(code, contents+","+content);
//			}
//		}
////        System.out.println("getPlayContent :"+dwMethodMap);
//		return 1;
//	}
//
//	//横向定位玩法号码限制检查
//	public static int getPlayContentHx(int lotteryId, int userId, int tryFlag, String issue, String pcontent, Map<String,String> hxdwMethodMap){
//		String sql="SELECT content,item_code FROM "+(tryFlag==2?"test_play":"user_play")+" WHERE lottery_id=? AND issue=? AND user_id=? AND item_code LIKE 'B%' AND content IN("+pcontent+") GROUP BY content,item_code";
//		List<Record> recList=Db.find(sql, lotteryId, issue, userId);
//		if( recList.size()==0 ) return 0;
//
//		String code="", content="", codes="";
//		for(Record rec : recList){
//			content=rec.getStr("content");
//			code=rec.getStr("item_code");
//			codes=hxdwMethodMap.get(content);
//			if( !(","+codes+",").contains(","+code+",")){
//				hxdwMethodMap.put(content, codes+","+code);
//			}
//		}
//		return 1;
//	}
//
//
//	public static Map<String,String> getMethodMap(){
//		String type="method_code";
//		Map<String,String> paramMap = PARAM_MAP.get(type);
//		if( paramMap != null){
//			return paramMap;
//		}else{
//			StringBuilder sql = new StringBuilder();
//			sql.append("SELECT DISTINCT CONCAT(lottery_id - 72,'_',item_code) code, IF(sub_id>1,item_name,CONCAT(method_name, ' ' , item_name)) name FROM lottery_ratio_instant WHERE lottery_id=80 OR lottery_id=81 UNION ALL ");
//			sql.append("SELECT DISTINCT CONCAT(lottery_id - 27,'_',item_code) code, CONCAT(method_name, ' ' , item_name) name FROM lottery_ratio_instant WHERE lottery_id=30 UNION ALL ");
//			sql.append("SELECT DISTINCT CONCAT(lottery_id - 36,'_',item_code) code, CONCAT(method_name, ' ' , item_name) name FROM lottery_ratio_instant WHERE lottery_id=40 UNION ALL ");
//			sql.append("SELECT DISTINCT CONCAT(lottery_id - 45,'_',item_code) code, CONCAT(method_name, ' ' , item_name) name FROM lottery_ratio_instant WHERE lottery_id=50 UNION ALL ");
//			sql.append("SELECT DISTINCT CONCAT(lottery_id - 18,'_',item_code) code, IF(INSTR(item_code,'ZD')>0 OR INSTR(item_code,'LH')>0, item_name, CONCAT(method_name, ' ' , item_name)) name FROM lottery_ratio_instant WHERE lottery_id=20 UNION ALL ");
//			sql.append("SELECT DISTINCT CONCAT(lottery_id - 9,'_',item_code) code, IF(INSTR(item_code,'ZD')>0 OR INSTR(item_code,'ZWD')>0, item_name, CONCAT(method_name, ' ' , item_name)) name FROM lottery_ratio_instant WHERE lottery_id=10 AND sub_id<19 UNION ALL ");
//			sql.append("SELECT DISTINCT CONCAT(lottery_id - 9,'_',item_code) code, sub_name name FROM lottery_ratio_instant WHERE lottery_id=10 AND sub_id>18 UNION ALL ");
//			sql.append("SELECT DISTINCT CONCAT('9_',item_code) code, CONCAT(sub_name, ' ' , item_name) name FROM lottery_ratio_instant WHERE lottery_id=90 AND method_id NOT IN(13,20,21,17,22) UNION ALL ");
//			sql.append("SELECT DISTINCT CONCAT('9_',SUBSTRING(item_code,1,INSTR(item_code,'_')-1)) code, CONCAT(method_name, ' ' , sub_name) name FROM lottery_ratio_instant WHERE lottery_id=90 AND method_id in(13,20,21) AND item_name IN('01','鼠','0尾') UNION ALL ");
//			sql.append("SELECT CONCAT('9_',substring(item_code,1,instr(item_code,'_')),item_id) code, CONCAT(method_name, ' ' , sub_name) name FROM lottery_ratio_instant WHERE lottery_id=90 AND method_id IN(17,22)");
//
//			paramMap = new HashMap<String,String>();
//			synchronized(paramMap){
//				List<Record> recList=Db.find(sql.toString());
//				for( Record rec : recList) paramMap.put(rec.get("code").toString(), rec.getStr("name"));
//				PARAM_MAP.put(type, paramMap);
//			}
//		}
//		return paramMap;
//	}


//	public static Map<String,String> getItemIdMap(){
//		String type="item_id";
//		Map<String,String> paramMap = PARAM_MAP.get(type);
//		if( paramMap != null){
//			return paramMap;
//		}else{
//			StringBuilder sql = new StringBuilder();
//			sql.append("SELECT DISTINCT CONCAT(lottery_id,'_',item_code) code, item_id FROM lottery_ratio_instant WHERE lottery_id IN(20,30,40,80,81) UNION ALL ");
//			sql.append("SELECT DISTINCT CONCAT(lottery_id,'_',item_code) code, item_id FROM lottery_ratio_instant WHERE lottery_id=10 AND sub_id<19 UNION ALL ");
//			sql.append("SELECT DISTINCT CONCAT(lottery_id,'_',item_code) code, sub_id item_id FROM lottery_ratio_instant WHERE lottery_id=10 and sub_id>18 UNION ALL ");
//			sql.append("SELECT DISTINCT CONCAT(lottery_id,'_',item_code) code, item_id FROM lottery_ratio_instant WHERE lottery_id=90 AND method_id NOT IN(13,20,21,17,22) UNION ALL ");
//			sql.append("SELECT DISTINCT CONCAT(lottery_id,'_',SUBSTRING(item_code,1,INSTR(item_code,'_')-1)) code, item_id FROM lottery_ratio_instant WHERE lottery_id=90 AND method_id in(13,20,21) AND item_name IN('01','鼠','0尾') UNION ALL ");
//			sql.append("SELECT CONCAT(lottery_id,'_',item_code) code, item_id FROM lottery_ratio_instant WHERE lottery_id=90 AND method_id IN(17,22)");
//
//			paramMap = new HashMap<String,String>();
//			synchronized(paramMap){
//				List<Record> recList=Db.find(sql.toString());
//				for( Record rec : recList) paramMap.put(rec.get("code").toString(), ""+rec.getInt("item_id"));
//				PARAM_MAP.put(type, paramMap);
//			}
//		}
//		return paramMap;
//	}
//
//	public static Map<String,Double> getRatioMap(int lotteryId, String ptype){
//		if( ptype==null||"".equals(ptype)) ptype="A";
//		String column = "b.differ"+(" ABCD".indexOf(ptype));
//
//		Map<String,Double>ratioMap = new HashMap<String,Double>();
//		List<Record> recList=null;
//		StringBuilder sql = new StringBuilder();
//
//		if(lotteryId>=90&&lotteryId<=99||lotteryId>=900&&lotteryId<=999){ //六合彩
//			sql.append("SELECT * FROM (");
//			sql.append("SELECT DISTINCT item_code code, IF(b.enable_flag=1,0,a.ratio - "+column+") ratio FROM lottery_ratio_instant a, lottery_ratio_def b WHERE a.lottery_id="+lotteryId+" AND a.lottery_id=b.lottery_id AND a.ratio_id=b.ratio_id AND a.method_id NOT IN(13,20,21) UNION ALL ");
//			sql.append("SELECT DISTINCT REPLACE(item_code,'_0','') code, IF(b.enable_flag=1,0,a.ratio - "+column+") ratio FROM lottery_ratio_instant a, lottery_ratio_def b WHERE a.lottery_id="+lotteryId+"  AND a.lottery_id=b.lottery_id AND a.ratio_id=b.ratio_id AND a.method_id=13 AND a.item_name='01' UNION ALL ");
//			sql.append("SELECT item_code code, IF(b.enable_flag=1,0,a.ratio - "+column+") ratio FROM lottery_ratio_instant a, lottery_ratio_def b WHERE a.lottery_id="+lotteryId+" AND a.lottery_id=b.lottery_id AND a.ratio_id=b.ratio_id AND a.method_id=21 AND a.item_name IN('0尾','1尾') UNION ALL ");
//			sql.append("SELECT DISTINCT CONCAT(SUBSTRING(item_code,1,INSTR(item_code,'_')),a.ratio_id - 42000 - a.sub_id) code, IF(b.enable_flag=1,0,a.ratio - "+column+") ratio FROM lottery_ratio_instant a, lottery_ratio_def b WHERE a.lottery_id="+lotteryId+" AND a.lottery_id=b.lottery_id AND a.ratio_id=b.ratio_id AND a.method_id=20");
//			sql.append(") a ORDER BY code,ratio DESC");
//			//System.out.println(sql.toString());
//			recList=Db.find(sql.toString());
//		}else{ //时时彩,PK10
//			//sql.append("SELECT DISTINCT item_code code, ratio FROM lottery_ratio_instant WHERE lottery_id=?");
//			sql.append("SELECT DISTINCT item_code code, IF(b.enable_flag=1,0,a.ratio - "+column+") ratio");
//			sql.append(" FROM lottery_ratio_instant a, lottery_ratio_def b");
//			sql.append(" WHERE a.lottery_id=? AND a.lottery_id=b.lottery_id AND a.ratio_id=b.ratio_id");
//
//			recList=Db.find(sql.toString(),lotteryId);
//		}
//
//		for( Record rec : recList) ratioMap.put(rec.get("code").toString(), rec.getBigDecimal("ratio").doubleValue());
//		return ratioMap;
//	}


	//获取彩种投注限额信息
//	public static Map<Integer,Record> getQuotaMap(int lotteryId, String ptype){
//		if( ptype==null||"".equals(ptype)) ptype="A";
//		int ptypeid = " ABC".indexOf(ptype);
//		Map<Integer,Record> quotaMap = new HashMap<Integer,Record>(20,0.95f);
//		List<Record> commList=Db.find("SELECT comm_id,comm"+ptypeid+" pct,low_limit,single_quota,issue_quota FROM lottery_commission WHERE lottery_id=?",lotteryId);
//		for(Record rec :commList) quotaMap.put(rec.getInt("comm_id"), rec);
//		return quotaMap;
//	}
//	public static Map<String,String> getMethodItemIdMap(){
//		String type="method_item_id";
//		Map<String,String> paramMap = PARAM_MAP.get(type);
//		if( paramMap != null){
//			return paramMap;
//		}else{
//			paramMap = new HashMap<String,String>();
//			synchronized(paramMap){
//				List<Record> recList=Db.find("SELECT CONCAT(CONVERT(method_id,CHAR),'_',item_name) AS code, item_id AS value FROM lottery_method WHERE method_id<>1011");
//				for( Record rec : recList) paramMap.put(rec.get("code").toString(), ""+rec.getInt("value"));
//				PARAM_MAP.put(type, paramMap);
//			}
//		}
//		return paramMap;
//
//	}

	//返回当前期号和投注截止时间
//	public static LotteryDataEntity getIssueAndTimeHK6(){
//		Record rec = Db.findFirst( "SELECT * FROM lottery_issue ORDER BY issue DESC LIMIT 0,1" );
//		if( rec==null ) return null;
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String closeTimeStr=rec.getStr("close_time1")+":00";
//		String openTimeStr=rec.getStr("open_time")+":00";
//		String drawDateStr=rec.getStr("award_time")+":00";
//		String issue=rec.getStr("issue");
//		boolean status=(rec.getInt("open_flag")==0||rec.getInt("close_flag")==1||rec.getInt("settle_flag")==1)?false:true;
//		Record li=Db.findFirst("SELECT status, table_name FROM lottery_info WHERE lottery_id=" + Constants.ID_HK6);
//		if(li == null) return null;
//		Integer hk6_status = li.getInt("status");
//		String tab = li.getStr("table_name");
//		if( hk6_status!=null && hk6_status==0) status=false;
//		long closeTime=Timestamp.valueOf(closeTimeStr).getTime();
//		long openTime=Timestamp.valueOf(openTimeStr).getTime();
//		long drawDate=Timestamp.valueOf(drawDateStr).getTime();
//		//获取当前期数和开盘时间
//		Record recs = HistoryDataService.getDayResult(tab);
//		if( recs==null )return null;
//		LotteryDataEntity data = new LotteryDataEntity();
//		if( drawDate < System.currentTimeMillis() || openTime> System.currentTimeMillis() ){
//			status = false;
//		}else{
//			if( status && closeTime<=System.currentTimeMillis()) status=false;//已过封盘时间
//		}
//		data.setIssue(""+issue);
//		data.setEndtime(format.format(closeTime));
//		data.setLotteryTime(format.format(drawDate));
//		data.setPreLotteryTime(format.format(openTime));
//		data.setPreIsOpen(status);
//		data.setGameId(Constants.ID_HK6);
//		data.setPreNum(recs.getStr("numbers"));
//		data.setPreIssue(""+recs.getLong("issue"));
//		data.setServerTime(format.format(recs.getTimestamp("hit_time").getTime()));
//		return data;
//	}
//	public static LotteryDataEntity getIssueAndTime(Integer lotteryId){
//		return getIssueAndTime(lotteryId, null);
//	}

	//返回当前期号和投注截止时间
//	public static LotteryDataEntity getIssueAndTime(Integer lotteryId, Integer merchantId){
//		if( lotteryId==null ) return null;
//		//DISTINCT 31-幸运飞艇; 22-新疆时时彩; 35-快乐飞艇; 1152(75秒赛车,75秒时时彩)
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String nowTime = DateUtil.nowtime();
//		/*String sql = "SELECT " +
//				"a.start_time, " +
//				"IF(c.merchant_id IS NULL, b.table_name, CONCAT(b.table_name,c.merchant_id)) table_name, " +
//				"b.status lott_status " +
//				"FROM lottery_issue_future a " +
//				"INNER JOIN lottery_info b ON a.lottery_id=b.lottery_id " +
//				"LEFT JOIN lottery_issue_future_ext c ON  a.lottery_id=c.lottery_id AND a.issue2=c.issue2 WHERE a.lottery_id=? AND ((a.start_time<=? AND ?<a.open_time) OR ?<a.open_time) ORDER BY a.issue  LIMIT 1";*/
//		//System.out.println("lotteryId="+lotteryId+"; nowtime="+currTime+"; sql="+sb.toString());
//		//Record record = Db.findFirst( sql, lotteryId, nowTime, nowTime, nowTime );
//		Record record;
//		if(merchantId!=null && lotteryId>=200 && lotteryId < 1000){
//			String sql = "SELECT a.*, CONCAT(b.table_name,a.merchant_id) table_name, b.status lott_status FROM lottery_issue_future_ext a, lottery_info b where a.lottery_id=b.lottery_id and a.lottery_id=? and merchant_id=? and ((a.start_time<=? and ?<a.open_time) or ?<a.open_time) order by a.issue  limit 1";
//			record = Db.findFirst( sql, lotteryId, merchantId, nowTime, nowTime, nowTime );
//		}else{
//			String sql = "SELECT a.*, b.table_name, b.status lott_status FROM lottery_issue_future a, lottery_info b where a.lottery_id=b.lottery_id and a.lottery_id=? and ((a.start_time<=? and ?<a.open_time) or ?<a.open_time) order by a.issue  limit 1";
//			record = Db.findFirst( sql, lotteryId, nowTime, nowTime, nowTime );
//		}
//		//System.out.println("lotteryId="+lotteryId+"; nowtime="+currTime+"; sql="+sb.toString());
//		LotteryDataEntity data = new LotteryDataEntity();
//		if(record != null){
//			String startTime = record.getStr("start_time");
//			String endTime = record.getStr("end_time");
//			String openTime = record.getStr("open_time");
//			int lottery_id = record.getInt("lottery_id");
//			String tab = record.getStr("table_name");
//			Timestamp startTm = Timestamp.valueOf(startTime);
//			Timestamp endTm = Timestamp.valueOf(endTime);
//			Timestamp openTm = Timestamp.valueOf(openTime);
//			String issue=record.getStr("issue").replaceAll("-", "");
//			//获取当前期数和开盘时间
//			Record rec = HistoryDataService.getDayResult(tab);
//			boolean status;
//			if( rec==null ) return null;
//			if( rec.getInt("status")==0 || startTm.getTime()> System.currentTimeMillis()){
//				status=false;
//			}else{
//				status=record.getInt("lott_status")==1;
//				if(status && endTm.getTime()<=System.currentTimeMillis()) status=false;//已过封盘时间
//			}
//			data.setIssue(issue);
//			data.setEndtime(format.format(endTm.getTime()));
//			data.setLotteryTime(format.format(openTm.getTime()));
//			data.setPreLotteryTime(format.format(startTm.getTime()));
//			data.setPreIsOpen(status);
//			data.setGameId(lottery_id);
//			data.setPreNum(rec.getStr("numbers"));
//			data.setPreIssue(""+rec.getLong("issue"));
//			data.setServerTime(format.format(System.currentTimeMillis()));
//		}else{
//			data = getIssueAndTime1(lotteryId);
//		}
//		return data;
//	}

	//返回当前期号和投注截止时间
//	public static LotteryDataEntity getIssueAndTime1(Integer lotteryId){
//		if( lotteryId==null ) return null;
//		//DISTINCT 31-幸运飞艇; 22-新疆时时彩; 35-快乐飞艇; 1152(75秒赛车,75秒时时彩)
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String nowTime=DateUtil.nowtime().substring(11), currTime=nowTime;
//		boolean flag;
//		if(lotteryId==10 || lotteryId==21){//广东快乐十分、天津时时彩
//			flag = DateUtil.belongCalendar("22:59:59", "23:59:59");
//		}else if(lotteryId == 11){//幸运农场
//			flag = DateUtil.belongCalendar("23:39:59", "23:59:59");
//		}else if(lotteryId == 30){//北京赛车
//			flag = DateUtil.belongCalendar("23:49:59", "23:59:59");
//		}else if(lotteryId == 80||lotteryId == 81){//PC蛋蛋
//			flag = DateUtil.belongCalendar("23:54:59", "23:59:59");
//		}else if(lotteryId == 40){//江苏快3
//			flag = DateUtil.belongCalendar("22:09:59", "23:59:59");
//		}else if(lotteryId == 41) {//安徽快3
//			flag = DateUtil.belongCalendar("21:59:59", "23:59:59");
//		}else if(lotteryId == 50){//广东11选5
//			flag = DateUtil.belongCalendar("23:09:59", "23:59:59");
//		}else if(lotteryId == 51){//山东11选5
//			flag = DateUtil.belongCalendar("23:00:59", "23:59:59");
//		}else if(lotteryId == 92){//澳门六合彩
//			flag = DateUtil.belongCalendar("21:30:00", "23:59:59");
//		}else if(lotteryId == 93){//台湾六合彩
//			flag = DateUtil.belongCalendar("21:00:00", "23:59:59");
//		}else if(lotteryId == 94){//新加坡六合彩
//			flag = DateUtil.belongCalendar("18:30:00", "23:59:59");
//		}else{
//			flag = false;
//		}
//		if(flag)currTime="00:00:00";
//		if(lotteryId == 20) {//重庆时时彩 - 做特殊处理
//			flag = DateUtil.belongCalendar("23:49:59", "23:59:59");
//			boolean key = DateUtil.belongCalendar("00:00:00", "00:09:59");
//			if (flag || key) currTime = "00:10:00";
//		}
//		Integer merchantId=null;
//		String issue2 = "";
//		StringBuilder sb = new StringBuilder();
//		sb.append("SELECT CONCAT(DATE_FORMAT(IF((a.lottery_id IN(31,33) AND a.issue>'131') OR (a.lottery_id=22 AND a.issue>'042') OR (a.lottery_id=35 AND a.issue>'1047') OR a.issue='1152',ADDDATE(CURDATE(),-1),CURDATE()),'%Y%m%d'),'-',a.issue) issue,");
//		sb.append(" a.enable_flag, b.status, DATE_FORMAT(CURDATE(),'%Y-%m-%d') currdate,");
//		sb.append(" a.start_time, a.end_time, a.open_time, a.get_time, a.lottery_id, IF(c.merchant_id IS NULL, b.table_name, CONCAT(b.table_name,c.merchant_id)) table_name, c.merchant_id, c.issue issue2");
//		sb.append(" FROM lottery_time a INNER JOIN lottery_info b ON a.lottery_id=b.lottery_id LEFT JOIN lottery_issue_future_ext c ON  a.lottery_id=c.lottery_id AND CONCAT(DATE_FORMAT(CURDATE(),'%Y%m%d'),'-',a.issue)=c.issue2");
//		sb.append(" WHERE a.lottery_id=? AND b.seq_no=1");
//		sb.append(" AND a.get_time > ? ORDER BY get_time LIMIT 0, 1");
//		//System.out.println("lotteryId="+lotteryId+"; nowtime="+currTime+"; sql="+sb.toString());
//		Record record = Db.findFirst( sb.toString(), lotteryId, currTime );
//		if( record == null){
//			sb.delete(0, sb.length());
//			if((lotteryId==11||lotteryId==12||lotteryId==23||lotteryId==32||lotteryId==42||lotteryId==91) && nowTime.compareTo("23:53:00")>=0){//幸运农场, 澳洲彩
//				sb.append("SELECT CONCAT(DATE_FORMAT(ADDDATE(CURDATE(),1),'%Y%m%d'),'-',a.issue) issue,");
//				sb.append(" a.enable_flag, b.status, DATE_FORMAT(ADDDATE(CURDATE(),1),'%Y-%m-%d') currdate,");
//				sb.append(" a.start_time, end_time, a.open_time, a.get_time, a.lottery_id, b.table_name");
//				sb.append(" FROM lottery_time a, lottery_info b");
//				sb.append(" WHERE a.lottery_id=b.lottery_id AND a.lottery_id=? AND a.issue='001'");
//				record = Db.findFirst( sb.toString(), lotteryId );
//			}else if((lotteryId==24||lotteryId==34||lotteryId==43) && nowTime.compareTo("23:59:00")>=0){//75秒时时彩,75秒赛车
//				sb.append("SELECT CONCAT(DATE_FORMAT(CURDATE(),'%Y%m%d'),'-',a.issue) issue,");
//				sb.append(" a.enable_flag, b.status, DATE_FORMAT(ADDDATE(CURDATE(),1),'%Y-%m-%d') currdate,");
//				sb.append(" a.start_time, end_time, a.open_time, a.get_time, a.lottery_id, b.table_name");
//				sb.append(" FROM lottery_time a, lottery_info b");
//				sb.append(" WHERE a.lottery_id=b.lottery_id AND a.lottery_id=? AND a.issue='1152'");
//				record = Db.findFirst( sb.toString(), lotteryId );
//			}else if(lotteryId==35 && nowTime.compareTo("23:59:45")>=0){//快乐飞艇
//				sb.append("SELECT CONCAT(DATE_FORMAT(CURDATE(),'%Y%m%d'),'-',a.issue) issue,");
//				sb.append(" a.enable_flag, b.status, DATE_FORMAT(ADDDATE(CURDATE(),-1),'%Y-%m-%d') currdate,");
//				sb.append(" a.start_time, end_time, a.open_time, a.get_time, a.lottery_id, b.table_name");
//				sb.append(" FROM lottery_time a, lottery_info b");
//				sb.append(" WHERE a.lottery_id=? AND a.issue='1048'");
//				record = Db.findFirst( sb.toString(), lotteryId );
//			}
//		}else{
//			merchantId=record.getInt("merchant_id");
//			issue2 = record.getStr("issue2");
//		}
//		LotteryDataEntity data = new LotteryDataEntity();
//		if(record != null){
//			String currDate = flag?DateUtil.tomorrow("yyyy-MM-dd"):record.getStr("currdate");
//			String startTime = record.getStr("start_time");
//			String endTime = record.getStr("end_time");
//			String openTime = record.getStr("open_time");
//			int lottery_id = record.getInt("lottery_id");
//			String tab = record.getStr("table_name");
//			String beginDate=currDate;
//			if( startTime.compareTo(endTime)>0 ){//开始时间大于结束时间
//				beginDate=DateUtil.getPlusDays("yyyy-MM-dd", currDate, -1);
//			}
//
//			Timestamp startTm = Timestamp.valueOf(beginDate+" "+startTime);
//			Timestamp endTm = Timestamp.valueOf(currDate+" "+endTime);
//			Timestamp openTm = Timestamp.valueOf(currDate+" "+openTime);
//
//			String issue=flag?LotteryUtil.formatIssue3(beginDate.replaceAll("-", "")+"001", lottery_id):LotteryUtil.formatIssue3(record.getStr("issue"), lottery_id);
//			if(lotteryId==92||lotteryId==93||lotteryId==94){
//				String newTime = DateUtil.nowtime();
//				Record rec = Db.findFirst("select * from lottery_issue_future where lottery_id=? and ((start_time<=? and ?<open_time) or ?<open_time) order by issue limit 1", lotteryId, newTime, newTime, newTime);
//				if(rec!=null){
//					issue=rec.getStr("issue").replaceAll("-", "");
//					startTm = rec.getTimestamp("start_time");
//					endTm = rec.getTimestamp("end_time");
//					openTm = rec.getTimestamp("open_time");
//				}
//			}
//
//			//获取当前期数和开盘时间
//			Record rec = null;
//			if((lotteryId>=200 && lotteryId<300)||(lotteryId>=300 && lotteryId<400)||(lotteryId>=400 && lotteryId<500)||(lotteryId>=900 && lotteryId<1000)){
//				if(merchantId!=null){
//					rec = HistoryDataService.getDayResult(tab);
//					issue = issue2;
//				}
//			}else{
//				rec = HistoryDataService.getDayResult(tab);
//			}
//
//			boolean status;
//			if( rec==null ) return null;
//			if( record.getInt("status")==0 || startTm.getTime()> System.currentTimeMillis()){
//				status=false;
//			}else{
//				status=record.getInt("enable_flag")==1;
//				if(status && endTm.getTime()<=System.currentTimeMillis()) status=false;//已过封盘时间
//			}
//			data.setIssue(issue);
//			data.setEndtime(format.format(endTm.getTime()));
//			data.setLotteryTime(format.format(openTm.getTime()));
//			data.setPreLotteryTime(format.format(startTm.getTime()));
//			data.setPreIsOpen(status);
//			data.setGameId(lottery_id);
//			data.setPreNum(rec.getStr("numbers"));
//			data.setPreIssue(""+rec.getLong("issue"));
//			data.setServerTime(format.format(System.currentTimeMillis()));
//		}
//		return data;
//	}

	//查询采种是否已封盘
//	public static boolean getPreIsOpen(Integer lotteryId, String issue){
//		if( lotteryId==null || issue==null) return false;
//		if(lotteryId==90){  //香港六合彩
//			LotteryDataEntity data = getIssueAndTimeHK6();
//			boolean status = false;
//			if(data!=null && issue.equals(data.getIssue())) {
//				status = data.isPreIsOpen();
//			}
//			return status;
//		}else{
//			issue = issue.replaceAll("-", "");
//			String nowTime = DateUtil.nowtime();
//			String sql = "SELECT a.*, b.table_name, b.status lott_status FROM lottery_issue_future a, lottery_info b where a.lottery_id=b.lottery_id and a.lottery_id=? and ((a.start_time<=? and ?<a.open_time) or ?<a.open_time) order by a.issue  limit 1";
//			//System.out.println("lotteryId="+lotteryId+"; nowtime="+currTime+"; sql="+sb.toString());
//			Record record = Db.findFirst( sql, lotteryId, nowTime, nowTime, nowTime );
//			if(record != null){
//				String startTime = record.getStr("start_time");
//				String endTime = record.getStr("end_time");
//				String tab = record.getStr("table_name");
//				Timestamp startTm = Timestamp.valueOf(startTime);
//				Timestamp endTm = Timestamp.valueOf(endTime);
//				String currIssue=record.getStr("issue").replaceAll("-", "");
//				if(!issue.equals(currIssue)) return false;
//				//获取当前期数和开盘时间
//				Record rec = HistoryDataService.getDayResult(tab);
//				boolean status;
//				if( rec==null ) return false;
//				if( rec.getInt("status")==0 || startTm.getTime()> System.currentTimeMillis()){
//					status=false;
//				}else{
//					status=record.getInt("lott_status")==1;
//					if(status && endTm.getTime()<=System.currentTimeMillis()) status=false;//已过封盘时间
//				}
//				return status;
//			}else{
//				LotteryDataEntity data = getIssueAndTime1(lotteryId);
//				boolean status = false;
//				if(issue.equals(data.getIssue())) {
//					status = data.isPreIsOpen();
//				}
//				return status;
//			}
//		}
//	}

	//游戏大厅时间显示
//	public static LotteryDataEntity getGamesLobbyTime(Integer lotteryId){
//		if( lotteryId==null ) return null;
//		//DISTINCT 31-幸运飞艇; 22-新疆时时彩; 35-快乐飞艇; 1152(75秒赛车,75秒时时彩)
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String nowTime=DateUtil.nowtime().substring(11), currTime=nowTime;
//		boolean flag;
//		if(lotteryId==10 || lotteryId==21){//广东快乐十分、天津时时彩
//			flag = DateUtil.belongCalendar("22:59:59", "23:59:59");
//		}else if(lotteryId == 11){//幸运农场
//			flag = DateUtil.belongCalendar("23:39:59", "23:59:59");
//		}else if(lotteryId == 30){//北京赛车
//			flag = DateUtil.belongCalendar("23:49:59", "23:59:59");
//		}else if(lotteryId == 80||lotteryId == 81){//PC蛋蛋
//			flag = DateUtil.belongCalendar("23:54:59", "23:59:59");
//		}else if(lotteryId == 40){//江苏快3
//			flag = DateUtil.belongCalendar("22:09:59", "23:59:59");
//		}else if(lotteryId == 41){//安徽快3
//			flag = DateUtil.belongCalendar("21:59:59", "23:59:59");
//		}else if(lotteryId == 50){//广东11选5
//			flag = DateUtil.belongCalendar("23:09:59", "23:59:59");
//		}else if(lotteryId == 51){//山东11选5
//			flag = DateUtil.belongCalendar("23:00:59", "23:59:59");
//		}else{
//			flag = false;
//		}
//		if(flag)currTime="00:00:00";
//		if(lotteryId == 20) {//重庆时时彩 - 做特殊处理
//			flag = DateUtil.belongCalendar("23:49:59", "23:59:59");
//			boolean key = DateUtil.belongCalendar("00:00:00", "00:09:59");
//			if (flag || key) currTime = "00:10:00";
//		}
//		StringBuilder sb = new StringBuilder();
//		sb.append("SELECT CONCAT(DATE_FORMAT(IF((a.lottery_id IN(31,33) AND a.issue>'131') OR (a.lottery_id=22 AND a.issue>'042') OR (a.lottery_id=35 AND a.issue>'1047') OR a.issue='1152',ADDDATE(CURDATE(),-1),CURDATE()),'%Y%m%d'),'-',a.issue) issue,");
//		sb.append(" a.enable_flag, b.status, DATE_FORMAT(CURDATE(),'%Y-%m-%d') currdate,");
//		sb.append(" a.start_time, end_time, a.open_time, a.get_time, a.lottery_id, b.table_name");
//		sb.append(" FROM lottery_time a, lottery_info b");
//		sb.append(" WHERE a.lottery_id=b.lottery_id AND a.lottery_id=? AND b.seq_no=1");
//		sb.append(" AND a.get_time > ? ORDER BY get_time LIMIT 0, 1");
////		System.out.println("lotteryId="+lotteryId+"; nowtime="+currTime+"; sql="+sb.toString());
//		Record record = Db.findFirst( sb.toString(), lotteryId, currTime );
//		if( record == null){
//			sb.delete(0, sb.length());
//			if((lotteryId==11||lotteryId==12||lotteryId==23||lotteryId==32||lotteryId==42||lotteryId==91) && nowTime.compareTo("23:53:00")>=0){//幸运农场, 澳洲彩
//				sb.append("SELECT CONCAT(DATE_FORMAT(ADDDATE(CURDATE(),1),'%Y%m%d'),'-',a.issue) issue,");
//				sb.append(" a.enable_flag, b.status, DATE_FORMAT(ADDDATE(CURDATE(),1),'%Y-%m-%d') currdate,");
//				sb.append(" a.start_time, end_time, a.open_time, a.get_time, a.lottery_id, b.table_name");
//				sb.append(" FROM lottery_time a, lottery_info b");
//				sb.append(" WHERE a.lottery_id=b.lottery_id AND a.lottery_id=?");
//				sb.append(" AND a.issue='001'");
//				record = Db.findFirst( sb.toString(), lotteryId );
//			}else if((lotteryId==24||lotteryId==34||lotteryId==43) && nowTime.compareTo("23:59:00")>=0){//75秒时时彩,75秒赛车
//				sb.append("SELECT CONCAT(DATE_FORMAT(CURDATE(),'%Y%m%d'),'-',a.issue) issue,");
//				sb.append(" a.enable_flag, b.status, DATE_FORMAT(ADDDATE(CURDATE(),1),'%Y-%m-%d') currdate,");
//				sb.append(" a.start_time, end_time, a.open_time, a.get_time, a.lottery_id, b.table_name");
//				sb.append(" FROM lottery_time a, lottery_info b ");
//				sb.append(" WHERE a.lottery_id=b.lottery_id AND a.lottery_id=?");
//				sb.append(" AND a.issue='1152'");
//				record = Db.findFirst( sb.toString(), lotteryId );
//			}else if(lotteryId==35 && nowTime.compareTo("23:59:45")>=0){//快乐飞艇
//				sb.append("SELECT CONCAT(DATE_FORMAT(CURDATE(),'%Y%m%d'),'-',a.issue) issue,");
//				sb.append(" a.enable_flag, b.status, DATE_FORMAT(ADDDATE(CURDATE(),-1),'%Y-%m-%d') currdate,");
//				sb.append(" a.start_time, end_time, a.open_time, a.get_time, a.lottery_id, b.table_name");
//				sb.append(" FROM lottery_time a, lottery_info b ");
//				sb.append(" WHERE a.lottery_id=b.lottery_id AND a.lottery_id=?");
//				sb.append(" AND a.issue='1048'");
//				record = Db.findFirst( sb.toString(), lotteryId );
//			}
//		}
//		LotteryDataEntity data = new LotteryDataEntity();
//		if(record != null){
//			String currDate = flag?DateUtil.tomorrow("yyyy-MM-dd"):record.getStr("currdate");
//			String startTime = record.getStr("start_time");
//			String endTime = record.getStr("end_time");
//			String openTime = record.getStr("open_time");
//			int lottery_id = record.getInt("lottery_id");
//			String tab = record.getStr("table_name");
//			String beginDate=currDate;
//			if( startTime.compareTo(endTime)>0 ){//开始时间大于结束时间
//				beginDate=DateUtil.getPlusDays("yyyy-MM-dd", currDate, -1);
//			}
//			Timestamp startTm = Timestamp.valueOf(beginDate+" "+startTime);
//			Timestamp endTm = Timestamp.valueOf(currDate+" "+endTime);
//			Timestamp openTm = Timestamp.valueOf(currDate+" "+openTime);
//			//获取当前期数和开盘时间
//			Record rec = HistoryDataService.getDayResult(tab);
//			boolean status;
//			if( rec==null ) return null;
//			if( record.getInt("status")==0 || startTm.getTime()> System.currentTimeMillis()){
//				status=false;
//			}else{
//				status=record.getInt("enable_flag")==1;
//				if(status && endTm.getTime()<=System.currentTimeMillis()) status=false;//已过封盘时间
//			}
//			data.setEndtime(format.format(endTm.getTime()));
//			data.setLotteryTime(format.format(openTm.getTime()));
//			data.setPreLotteryTime(format.format(startTm.getTime()));
//			data.setPreIsOpen(status);
//			data.setGameId(lottery_id);
//			data.setServerTime(format.format(System.currentTimeMillis()));
//		}
//		return data;
//	}

	//循环查询处理
//	public static List<LotteryDataEntity> getIssueAndTimes(){
//		List<LotteryDataEntity> list = new ArrayList<>();
//		List<Record> recList = getLotteryList();
//		if(recList!=null && recList.size()>0){
//			for (Record rec : recList){
// 				list.add(getGamesLobbyTime(rec.getInt("lottery_id")));
//			}
//		}
//		return list;
//	}

	/**
	 * 查询长龙投注数
	 * @param lotteryId
	 * @param issue
	 * @param itemId
	 * @return
	 */
//	private static int getLongBetCount(int lotteryId, String issue, int itemId){
//		Integer count = Db.queryInt("select count(*) count from user_play where lottery_id=? and issue=? and item_id=?", lotteryId, issue, itemId);
//		return count==null||count==0?1:count;
//	}

	/**
	 * 查询长龙投注信息
	 * @return
	 */
//	public static List<LongDragonEntity> getLongIssueAndTime(int userId, int tryFlag, int count, int merchantId){
//		List<LongDragonEntity> ldList = new ArrayList<>();
//		StringBuilder sb = new StringBuilder();
//		sb.append("SELECT lottery_id,lottery_name,count,title,linkplay_id,linkplay_oddsa,linkplay_oddsb,linkplay_oddsc,linkplay_name,play_id,play_oddsa,play_oddsb,play_oddsc,play_name,create_time FROM lottery_longbet");
////		sb.append(" UNION ALL ");
////		sb.append("SELECT lottery_id,lottery_name,count,title,linkplay_id,linkplay_oddsa,linkplay_oddsb,linkplay_oddsc,linkplay_name,play_id,play_oddsa,play_oddsb,play_oddsc,play_name,create_time FROM lottery_longbet_ext WHERE merchant_id=? AND lottery_id IN(?)");
//		//List<Record> recList = Db.find(sb.toString(), merchantId);
//		List<Record> recList = Db.find(sb.toString());
//		if(recList.size()>0){
//			String pType = "A";
//			if(tryFlag!=2){
//				pType = Db.queryStr("select ptype from user_info where id=?", userId);
//				if(pType==null || "".equals(pType))return ldList;
//			}
//			for(Record rec : recList){
//				int lotteryId = rec.getInt("lottery_id");
//				LotteryDataEntity lde = getIssueAndTime(lotteryId, merchantId);
//				if(lde!=null && rec.getInt("count")>=count){
//					int linkPlayIdBetsRatio=(int)(1+Math.random()*(10-1+1));//getLongBetCount(lotteryId, lde.getIssue(), rec.getInt("linkplay_id"));
//					int playIdBetsRatio=(int)(1+Math.random()*(10-1+1));//getLongBetCount(lotteryId, lde.getIssue(), rec.getInt("play_id"));
//					double lOddsA = rec.getBigDecimal("linkplay_oddsa").doubleValue();
//					double lOddsB = rec.getBigDecimal("linkplay_oddsb").doubleValue();
//					double lOddsC = rec.getBigDecimal("linkplay_oddsc").doubleValue();
//					double oddsA = rec.getBigDecimal("play_oddsa").doubleValue();
//					double oddsB = rec.getBigDecimal("play_oddsb").doubleValue();
//					double oddsC = rec.getBigDecimal("play_oddsc").doubleValue();
//					NumberFormat numberFormat = NumberFormat.getInstance();
//
//					// 设置精确到小数点后2位
//					linkPlayIdBetsRatio = linkPlayIdBetsRatio==0?1:linkPlayIdBetsRatio;
//					numberFormat.setMaximumFractionDigits(0);
//					int t;
//					if(playIdBetsRatio>linkPlayIdBetsRatio){
//						t = playIdBetsRatio;
//						playIdBetsRatio=linkPlayIdBetsRatio;
//						linkPlayIdBetsRatio = t;
//					}
//					Integer ratio = Integer.parseInt(numberFormat.format((float) playIdBetsRatio / (float) linkPlayIdBetsRatio * 100));
//					if(linkPlayIdBetsRatio==playIdBetsRatio) ratio=50;
//					LongDragonEntity ld = new LongDragonEntity();
//					ld.setGameId(lotteryId);
//					ld.setGameName(rec.getStr("lottery_name"));
//					ld.setCount(rec.getInt("count"));
//					ld.setIssue(lde.getIssue());
//					ld.setLotteryTime(lde.getLotteryTime());
//					ld.setEndTime(lde.getEndtime());
//					ld.setPreLotteryTime(lde.getPreLotteryTime());
//					ld.setLinkPlayId(String.valueOf(rec.getInt("linkplay_id")));
//					ld.setLinkPlayName(rec.getStr("linkplay_name"));
//					ld.setLinkPlayIdBetsRatio(ratio);
//					ld.setLinkPlayOdds("A".equals(pType)?lOddsA:("B".equals(pType)?lOddsB:lOddsC));
//					ld.setPlayId(String.valueOf(rec.getInt("play_id")));
//					ld.setPlayName(rec.getStr("play_name"));
//					ld.setPlayBetsRatio(100-ratio);
//					ld.setPlayOdds("A".equals(pType)?oddsA:("B".equals(pType)?oddsB:oddsC));
//					ld.setPlayCateName(rec.getStr("title"));
//					ldList.add(ld);
//				}
//			}
//		}
//		return ldList;
//	}

	public static String timeToStr(long time){
		if( time<=0 ) return "00:00:00";
		int minutes = (int)(time/60000);
		int secodes = (int)((time - 60000*minutes)/1000);
		int hours = (int)(minutes/60);
		minutes = minutes%60;
		return (hours<10?"0":"")+hours+":"+(minutes<10?"0":"")+(minutes>0?minutes:0)+":"+(secodes<10?"0":"")+(secodes>0?secodes:0);
	}

	@Override
	public void afterPropertiesSet() {
		this.initCommMap();
		this.initLottMap();
	}
}
