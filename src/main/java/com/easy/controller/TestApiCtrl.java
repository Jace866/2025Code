package com.easy.controller;

import com.easy.common.Result;
import com.easy.entity.SysMerchant;
import com.easy.service.BusinessService;
import com.easy.service.SysParameterService;
import com.easy.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping(value = "/test")
public class TestApiCtrl {
    @Autowired
    private BusinessService businessService;
    @Autowired
    private SysParameterService sysParameterService;

    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public Result<Object> post(@RequestParam(value = "flag", defaultValue = "") String flag,
                               @RequestParam(value = "actype", defaultValue = "1") String actype,
                               @RequestParam(value = "username", defaultValue = "test03") String username,
                               @RequestParam(value = "password", defaultValue = "test03") String password,
                               @RequestParam(value = "way", defaultValue = "1") String way,
                               @RequestParam(value = "type", defaultValue = "") String type,
                               @RequestParam(value = "amt", defaultValue = "0") String amt,
                               @RequestParam(value = "billno", defaultValue = "") String billno,
                               @RequestParam(value = "pid", defaultValue = "TEST9988") String pid,
                               @RequestParam(value = "code", defaultValue = "") String code){
        /*String username="5246841CNY0";
        String password = "f287ec2752e4e59e0b3c";*/
        SysMerchant res = businessService.getCagent(pid);
        if(res==null){
            return new Result<>().error("-1","商户账号错误");
        }
        String[] domain = res.getDomain().split(",");

        String https_enable = sysParameterService.getParamValue2("sys_param", "https_enable");
        String protocol = "1".equals(https_enable) ? "https://" : "http://";
        String apiUrl = protocol+domain[domain.length-1]+("".equals(flag)?"/openGame":"/businessprocess");
        //System.out.println(apiUrl);
        String deskey=res.getDesKey();//"042B44JS";
        String md5key=res.getMd5Key();//"0Q9ir99c886";
        HttpServletRequest request = IpAddressUtil.getHttpServletRequest();
        String ip = IpAddressUtil.getIpAddress(request);
        //String params="method=bal/\\\\/loginname=test01/\\\\/password=a123456/\\\\/actype=1";  //查询余额
        String params="loginname="+username+"&password="+password+"&gametype=LOTT&actype="+actype+"&currencyType=CNY&way="+way+"&oddtype=A";  //打开游戏  dm-返回上一个域名
        if(!"".equals(code)){
            params+="&code="+code;
        }

        if("tran".equals(flag)){
            String rand = this.getStringRandom(16);
            params="method=tran&type="+type+"&credit="+amt+"&loginname="+username+"&password="+password+"&billno="+rand;  //会员额度转换
        }else if("bal".equals(flag)){
            params="method=bal&loginname="+username+"&password="+password;  //查询余额
        }else if("report".equals(flag)){  //获取注单
            String beginDate = DateUtil.getPastDateTime(-10);
            String endDate = DateUtil.nowtime();
            params="method=report&beginDate="+beginDate+"&endDate="+endDate;
        }else if("verifyorder".equals(flag)){  //获取注单
            params="method=verifyorder&billno="+billno;
        }

        params = DesUtility.encrypt(params, deskey);
        //TjdYGBxJkNxESSoERnI/8iLzUSmo6tbWkC5coR/i+v6iGq2PJBv7+DntkiGe16C+jTMog/Y9DvAK
        //S9L6BsMw9uHmyC9ADcCH
        params = params.replaceAll("[\\t\\n\\r]", "");
        //System.out.println(params);
        String key = MD5Utils.md5(pid + params+md5key);
        //System.out.println(key);
        //DecimalFormat df = new DecimalFormat("#.00");
        //System.out.println( df.format(Double.valueOf((float)1/1)*100) );
        String result = http(pid, key, params, apiUrl); //获取打开游戏连接
        //System.out.println(result);
        return new Result<>().success(result);
    }

    private String http(String pid,String sign, String params, String line){
        OutputStreamWriter out = null;
        InputStream is = null;
        Map<String , String> paramMap = new HashMap<>();
        paramMap.put("pid", pid);
        paramMap.put("sign", sign);
        paramMap.put("params", params);
        String result = "";
        try {
            System.out.println(paramMap);
            result = new HttpClientUtil2().doPost(line, paramMap, "UTF-8");
        } catch (Exception e) {
            result = "请求出现异常！"+e;
        }
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(is!=null){
                    is.close();
                }
            }
            catch(IOException ex){
                result = "请求出现异常！"+ex;
                //ex.printStackTrace();
            }
        }
        return result;
    }

    //生成随机数字和字母
    public String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val.toLowerCase();
    }

}
