package com.easy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easy.dao.ChatPlatDao;
import com.easy.dao.LotteryInfoDao;
import com.easy.dao.LotteryItemDao;
import com.easy.dao.SysParameterDao;
import com.easy.entity.ChatPlat;
import com.easy.entity.LotteryInfo;
import com.easy.entity.LotteryItem;
import com.easy.entity.SysParameter;
import com.easy.service.SysParameterService;
import com.easy.vo.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author : hello_@
 * @Date : 2024/7/1
 */
@Service
public class SysParameterServiceImpl implements SysParameterService, InitializingBean {

    private static final Map<String, Map<String, String>> PARAM_MAP = new HashMap<>();
    private static final Map<Integer, LotteryBean> LOTT_ID_MAP=new TreeMap<Integer, LotteryBean>();
    private static final Map<String,LotteryBean> LOTT_CODE_MAP=new HashMap<String, LotteryBean>();
    private static final Map<String,LotteryBean> LOTT_MERCHANT_ID_MAP=new TreeMap<String, LotteryBean>();
    private static final Map<String, ItemBean> ITEM_MAP=new HashMap<String, ItemBean>();

    @Autowired
    private SysParameterDao sysParameterDao;
    @Autowired
    private LotteryItemDao lotteryItemDao;
    @Autowired
    private ChatPlatDao chatPlatDao;
    @Autowired
    private LotteryInfoDao lotteryInfoDao;

    private static Map<String,String> generateMap(String type){
        Map<String,String> paramMap = PARAM_MAP.get(type);
        if( paramMap!=null ){
            paramMap.clear();  //考虑参数删除情况
            return paramMap;
        }
        if( "trans_type".equals(type) ){
            paramMap = new TreeMap<String,String>((e1,e2)->{
                int cmp = e1.substring(1).compareTo(e2.substring(1));
                if(cmp==0) return e1.substring(0,1).compareTo(e2.substring(0,1));
                else return cmp;
            });
        }else{
            paramMap = new TreeMap<String,String>();
        }
        return paramMap;
    }

    //刷新系统参数
    @Override
    public Map<String,Map<String,String>> refreshParameter(String type){
        if( type==null || "".equals(type) ){ //刷新全部参数( 只需要需要缓存的参数 )
            List<SysParameterVo> paramList = sysParameterDao.getAllParameter();
            if( paramList.size()==0 ) return PARAM_MAP;

            String preType=paramList.get(0).getType();
            Map<String,String> paramMap = generateMap(preType);

            for( SysParameterVo rec : paramList ){
                if( preType.equals(rec.getType()) ){
                    paramMap.put(rec.getCode(), rec.getValue());
                }else{
                    PARAM_MAP.put(preType, paramMap);
                    preType = rec.getType();
                    paramMap = generateMap(preType);
                    paramMap.put(rec.getCode(), rec.getValue());
                }
            }
            PARAM_MAP.put(preType, paramMap);

        }else{//刷新指定参数( 只需要需要缓存的参数 )
            List<SysParameterVo> paramList = sysParameterDao.getParameterByType(type);
            Map<String,String> paramMap = generateMap(type);
            for( SysParameterVo rec : paramList) paramMap.put(rec.getCode(), rec.getValue());
            PARAM_MAP.put(type, paramMap);
        }

        Map<String,String> paraMap=this.getParamMap("sys_param");
        paraMap.put("version_no_ext" ,""+System.currentTimeMillis());
        return PARAM_MAP;
    }

//    @Override
//    public String getParamValue(String type, String code) {
//        Map<String,String> paramMap = PARAM_MAP.get(type);
//        if(paramMap!=null){
//            if (paramMap.get(code) != null) {
//                return paramMap.get(code);
//            }
//            String paramValue = sysParameterDao.getParamValue(type, code);
//            paramMap.put(code, paramValue);
//            return paramValue;
//        } else {
//            String paramValue = sysParameterDao.getParamValue(type, code);
//            PARAM_MAP.putIfAbsent(type, new HashMap<>());
//            PARAM_MAP.get(type).put(code, paramValue);
//            return paramValue;
//        }
//    }
    @Override
    public String getParamValue(String type, String code){
        Map<String,String> paramMap = PARAM_MAP.get(type);
        if( paramMap != null){
            return paramMap.get(code);
        }
        return null;
    }
    @Override
    public String getParamValue2(String type, String code){
        String value=sysParameterDao.getParamValue(type, code);
        return value;
    }
    @Override
    public String getParamValue3(String type, String code){
        String value = sysParameterDao.getParamValue3(type, code, 1);
        return value;
    }

    @Override
    public Map<String,String> getParamMap(String type) {
        Map<String,String> paramMap = PARAM_MAP.get(type);
        if( paramMap != null){
            return paramMap;
        }else{
            paramMap = new TreeMap<String,String>();
            synchronized(paramMap){
                if( "trans_type".equals(type) ){
                    paramMap = new TreeMap<String,String>((e1,e2)->{
                        int cmp = e1.substring(1).compareTo(e2.substring(1));
                        if(cmp==0) return e1.substring(0,1).compareTo(e2.substring(0,1));
                        else return cmp;
                    });
                }
                List<SysParameterVo> paramList=sysParameterDao.getParameterByType(type);
                for( SysParameterVo rec : paramList) paramMap.put(rec.getCode(), rec.getValue());
                PARAM_MAP.put(type, paramMap);
            }
        }
        return paramMap;
    }

    private void initItemMap(){
        List<LotteryItem> list = lotteryItemDao.selectList(new QueryWrapper<LotteryItem>());
        for( LotteryItem r : list){
            ItemBean bean = new ItemBean(r.getItemId(),r.getLotteryType(),r.getItemCode(),r.getItemName());
            bean.setGame(r.getItemCode().split("_")[0]);
            ITEM_MAP.put(r.getItemId().toString(), bean);
        }
    }
    @Override
    public Map<String,ItemBean> getItemBeanMap(){
        return ITEM_MAP;
    }
    @Override
    public void initLotteryMap(){
        String sendAccount=null;
        List<LotteryInfoVo> list = lotteryInfoDao.getLotteryExtList();
        for( LotteryInfoVo r : list){
            LotteryBean bean = new LotteryBean(r.getLotteryId(),r.getLotteryCode(),r.getLotteryName(),r.getLotteryType(),r.getTableName(),r.getIssues().toString(),r.getIssueType());
            bean.setStatus(r.getStatus());
            bean.setSendFlag(r.getSendFlag());
            bean.setIssues(r.getIssues());
            bean.setMemberLayer(null);
            sendAccount=null;
            if(sendAccount!=null){
                sendAccount=sendAccount.trim();
            }else{
                sendAccount="";
            }
            if(!"".equals(sendAccount)) sendAccount=","+sendAccount+",";
            bean.setSendAccount( sendAccount );
            LOTT_ID_MAP.put(r.getLotteryId(), bean);
            LOTT_CODE_MAP.put(r.getLotteryCode(), bean);
            int merchantId = r.getMerchantId()==null?0:r.getMerchantId().intValue();
            LOTT_MERCHANT_ID_MAP.put(r.getLotteryId()+"_"+merchantId, bean);
        }
    }

    @Override
    public LotteryBean getLotteryBeanById(Integer lotteryId){
        return LOTT_ID_MAP.get(lotteryId);
    }
    @Override
    public LotteryBean getLotteryBeanByCode(String lotteryCode){
        return LOTT_CODE_MAP.get(lotteryCode);
    }
    @Override
    public Map<Integer,LotteryBean> getAllLotteryBean(){
        return LOTT_ID_MAP;
    }
    @Override
    public LotteryBean getLotteryBeanMerchantById(String lotteryId){
        return LOTT_MERCHANT_ID_MAP.get(lotteryId);
    }
    @Override
    public List<LotteryBean> getLotteryBeanList(){
        List<LotteryBean> lottList = new ArrayList<LotteryBean>();
        List<LotteryInfo> infos = lotteryInfoDao.getLotteryBeanList();
        for( LotteryInfo r : infos){
            LotteryBean bean = new LotteryBean(r.getLotteryId(),r.getLotteryCode(),r.getLotteryName()+(r.getStatus()==0?"(停)":""),
                    r.getLotteryType(),r.getTableName(),r.getIssues().toString(),r.getIssueType());
            bean.setStatus(r.getStatus());
            bean.setSendFlag(r.getSendFlag());
            bean.setIssues(r.getIssues());
            lottList.add(bean);
        }
        return lottList;
    }
    @Override
    public List<BankcodeEntity> getUserBankcodeList(){
        List<SysParameter> list = sysParameterDao.getUserBankcodeList("bank_code2", 1);
        List<BankcodeEntity> bankList = new ArrayList<BankcodeEntity>(list.size());
        for(SysParameter rec : list){
            BankcodeEntity entity = new BankcodeEntity();
            entity.setValue( rec.getCode() );
            entity.setName( rec.getValue() );
            bankList.add(entity);
        }

        return bankList;
    }

    //查询平台信息
    @Override
    public ChatPlat getPlatInfo(String platCode){
        return chatPlatDao.selectOne(new QueryWrapper<ChatPlat>());
    }

    @Override
    public void updateValue(int value, String type, String code) {
        sysParameterDao.updateValue(value, type, code);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.initItemMap();
        this.initLotteryMap();
    }
}
