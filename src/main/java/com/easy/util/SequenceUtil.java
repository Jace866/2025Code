package com.easy.util;

import com.easy.service.SysParameterService;
import com.easy.service.SysSequenceService;
import com.easy.service.ThreadService;
import com.easy.service.impl.ThreadServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class SequenceUtil implements InitializingBean {
    private final static char[] CHAR_SET = "iGH0KL1PQ2w3NYZ4TUqrst7IJ8jzBC9AdefgDEFchuvMOnoypxRS5Wk6VlmXab".toCharArray();
    private final static char[] CHAR2_SET = "GHKpLPQwNYyZTUqrstIJjzBCAdefgDEFchuvMOnoxRSWkVmXab".toCharArray();
    private final static char[] CHAR_SET2 = "iGH0KL1PQ2w3NYZ4TUqrst7IJ8jzBC9AdefgDEFchuvMOnoypxRS5Wk6VlmXab".toCharArray();
    private final static char[] CHAR_SET3 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private final static char[] CHAR_SET5 = "STW2A3B1M4NGQRCD9HK8Y6PXEF".toCharArray();
    private final static char[] CHAR_SET6 = "057IJUOZLV".toCharArray();
    private final static ConcurrentMap<String, SequenceBean> MAX_MAP = new ConcurrentHashMap<String, SequenceBean>(30, 0.8f);
    private final static Object lockObj = new Object();
    private final static String[] CODES_ARR={"AAA","BBB","CCC","DDD","EEE","FFF","HHH","KKK","QQQ","PPP"};
    private static String CHARGE_ID = "";

    private static ThreadService threadService;

    private static SysParameterService sysParameterService;

    private static SysSequenceService sysSequenceService;

    @Autowired
    public void setThreadService(ThreadService threadService) {
        SequenceUtil.threadService = threadService;
    }
    @Autowired
    public void setSysParameterService(SysParameterService sysParameterService) {
        SequenceUtil.sysParameterService = sysParameterService;
    }
    @Autowired
    public void setSysSequenceService(SysSequenceService sysSequenceService) {
        SequenceUtil.sysSequenceService = sysSequenceService;
    }

    //最大值 max=62*62*62*62*62=916132832(五位,九亿多)
    public static String _10To62(int num) {
        int idx0 = num % 14776336; //62*62*62*62
        int idx1 = num % 238328; //62*62*62
        int idx2 = num % 3844;   //62*62
        return CHAR_SET[num / 14776336] + "" + CHAR_SET[idx0 / 238328] + CHAR_SET[idx1 / 3844] + CHAR_SET[idx2 / 62] + CHAR_SET[num % 62];
    }

    //最大值 max=36*36*36*36*36*36=2176782336(六位,二十一亿多)
    public static String _10To36B(int num){
        int idx0=num%60466176; //36*36*36*36*36
        int idx1=num%1679616; //36*36*36*36
        int idx2=num%46656;   //36*36*36
        int idx3=num%1296;   //36*36
        return CHAR_SET3[num/60466176]+""+CHAR_SET3[idx0/1679616]+CHAR_SET3[idx1/46656]+CHAR_SET3[idx2/1296]+CHAR_SET3[idx3/36]+CHAR_SET3[num%36];
    }

    public static String _10To36B1(int num) {
        //int idx0=num%60466176; //36*36*36*36*36
        int idx0 = num % 1679616; //36*36*36*36
        int idx1 = num % 46656;   //36*36*36
        int idx2 = num % 1296;   //36*36
        return CHAR_SET3[num / 1679616] + "" + CHAR_SET3[idx0 / 46656] + CHAR_SET3[idx1 / 1296] + CHAR_SET3[idx2 / 36] + CHAR_SET3[num % 36];
    }
    //最大值 max=36*36*36*36*36=60466176(五位,六千多万)
    public static String _10To36B2(int num){
        int idx1=num/1679616; //36*36*36*36
        int idx2=num/46656;   //36*36*36
        int idx3=num/1296;   //36*36
        return CHAR_SET3[idx1]+""+CHAR_SET3[(num-1679616*idx1)/46656]+CHAR_SET3[(num-46656*idx2)/1296]+CHAR_SET3[(num-1296*idx3)/36]+CHAR_SET3[num%36];
    }

    /**
     * 调用接口 根据名称获取seq的下一个值 每次递增 len
     *
     * @param seqName
     * @return
     */
    public static int getSeq(String seqName, int len) {
        SequenceUtil util = new SequenceUtil();
        int result = -1;
        synchronized (lockObj) {
            result = util.getSeqFromDB(seqName, len);
        }
        return result;
    }

    //投注订单编号
    public static String getPlayNo(String prefix) {
        int id = getCurrentNo("playNo", prefix, 100000);
        return prefix + "8" + String.format("%07d", id);
    }

    //消息编号
    public static String getMessageId(String prefix) {
        String seqName = "MSG" + prefix;
        int id = getCurrentNo("MessageId", seqName, 50000);
        int month = Integer.parseInt(prefix.substring(4, 6), 10);
        int date = Integer.parseInt(prefix.substring(6, 8), 10);
        return CHAR2_SET[month] + "" + CHAR2_SET[date] + "" + _10To62(id);
    }

    //账变订单编号
    public static String getTransNo(String prefix) {
        String seqName = "T" + prefix;
        int id = getCurrentNo("transNo", seqName, 10000);
        return seqName + "4" + String.format("%07d", id);
    }

    //充值订单编号
    public static String getDepositNo(String prefix) {
        String seqName = CHARGE_ID + prefix;
        int id = getCurrentNo("depositNo", seqName, 30000);
        return seqName + "8" + String.format("%06d", id);
    }

    //提款订单编号
    public static String getWithdrawNo(String prefix) {
        String seqName = "W" + prefix + CHARGE_ID;
        int id = getCurrentNo("withdrawNo", seqName, 100000);
        //return seqName+_10To36B(id);
        return seqName + "8" + _10To36B1(id);
    }

    //额度转换订单编号
    public static String getTransferNo(String prefix){
        String seqName="T"+prefix+CHARGE_ID;
        int id=getCurrentNo("transferNo", seqName,100000);
        return seqName+_10To36B(id);
    }

    //额度转换订单编号(专门用于 BG游戏，因为BG游戏 转账编号不能是字符类型，只能整数,API接口需要特别处理)
    public static String getTransferNo1(String prefix){
        String seqName="T"+prefix+CHARGE_ID;
        int id=getCurrentNo("getTransferNo1",seqName,10000);
        return seqName+String.format("%07d",id);
    }

    //额度转换订单编号
    public static String getTransferNo2(String prefix){
        String seqName="TS"+prefix+CHARGE_ID;
        int id=getCurrentNo("transferNo2", seqName,100000);
        return seqName+_10To36B(id);
    }

    //获取注册链接唯一码
    public static String getRegisterCode(){
        String typeNo="RegisterCode";
        String code=getCurrentNoStr(typeNo, typeNo);
        Random rnd = new Random();
        int rndInt = rnd.nextInt(14776333);
        String pre = _10To62(rndInt);
        if( pre.length()<4) pre=CODES_ARR[rndInt%10].substring(0,4-pre.length())+pre;
        return pre+code.substring(12);
    }

    //生成附言
    public static String gePostscript(String date){
        Random rnd = new Random();
        int idx = rnd.nextInt(62);
        String typeNo="P.S.";
        String code=CHAR_SET2[idx]+getCurrentNoStr(typeNo, typeNo+date);
        return code;
    }
    /**
     * 从db中获取seq的下一个值
     *
     * @param seqName
     * @return
     */
    public int getSeqFromDB(String seqName, int length) {
        final int[] tmpArr = new int[]{-1};
        try {
            sysSequenceService.getSeqFromDBTx1(tmpArr, seqName, length);
        } catch (Exception e) {
            threadService.addRunLog(ThreadServiceImpl.RUN_LOG_TYPE_GEN_ID, seqName + ":" + tmpArr[0] + "~" + length + "; 发生错误1:" + e.getMessage());
            try {
                Thread.sleep(100);
                sysSequenceService.getSeqFromDBTx2(tmpArr, seqName, length);
            } catch (Exception ee) {
                threadService.addRunLog(ThreadServiceImpl.RUN_LOG_TYPE_GEN_ID, seqName + ":" + tmpArr[0] + "~" + length + "; 发生错误2:" + e.getMessage());
                throw new RuntimeException(e.getMessage()); //抛出运行时异常，由具体业务决定是否扑捉异常
            }
        }
        String value = sysParameterService.getParamValue("log_config", "gen_id_log");
        if ("1".equals(value)) {
            threadService.addRunLog(ThreadServiceImpl.RUN_LOG_TYPE_GEN_ID, seqName + ":" + tmpArr[0] + "~" + (tmpArr[0] + length));
        }
        return tmpArr[0];
    }

    public synchronized static int getCurrentNo(String key, String seqName, int len) {
        int id = 0;
        SequenceBean bean = MAX_MAP.get(key);
        if (bean != null) {
            if (seqName.equals(bean.name)) {
                id = bean.getCurrent();
                if (id == bean.max) {
                    id = getSeq(seqName, len);
                    bean.current = id;
                    bean.max = id + len;
                }
            } else {
                id = getSeq(seqName, len);
                bean.name = seqName;
                bean.current = id;
                bean.max = id + len;
            }
        } else {
            id = getSeq(seqName, len);
            bean = new SequenceBean(seqName, id, id + len);
            MAX_MAP.put(key, bean);
        }
        return id;
    }

    public synchronized static String getCurrentNoStr(String key, String seqName){
        int id, len=5000;
        SequenceBean bean = MAX_MAP.get(key);
        if( bean != null ){
            if( seqName.equals( bean.name ) ){
                id = bean.getCurrent();
                if( id==bean.max){
                    id = getSeq(seqName, len);
                    bean.current=id;
                    bean.max=id+len;
                }
            }else{
                id = getSeq(seqName, len);
                bean.name=seqName;
                bean.current=id;
                bean.max=id+len;
            }
        }else{
            id = getSeq(seqName, len);
            bean = new SequenceBean(seqName, id, id+len);
            MAX_MAP.put(key, bean);
        }
        return seqName+_10To62(id);
    }

    @Override
    public void afterPropertiesSet() {
        CHARGE_ID = sysParameterService.getParamValue("sys_param", "charge_id");
    }
}
