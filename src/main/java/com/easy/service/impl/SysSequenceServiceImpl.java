package com.easy.service.impl;

import com.easy.dao.SysSequenceDao;
import com.easy.entity.SysSequence;
import com.easy.service.SysRunLogService;
import com.easy.service.SysSequenceService;
import com.easy.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author : hello_@
 * @Date : 2024/7/1
 */
@Service
public class SysSequenceServiceImpl implements SysSequenceService {

    @Autowired
    private SysSequenceDao sysSequenceDao;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private SysRunLogService sysRunLogService;

    @Override
    public Integer queryPostValue(String seqName) {
        return sysSequenceDao.queryPostValue(seqName);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void getSeqFromDBTx1(int[] tmpArr, String seqName, int length) {
        Integer postValue = sysSequenceDao.queryPostValue(seqName);
        if (postValue != null) {
            String logcontent = sysRunLogService.getLogContent(ThreadServiceImpl.RUN_LOG_TYPE_GEN_ID, seqName + ":" + tmpArr[0] + "~" + length);
            if (logcontent == null) {
                tmpArr[0] = postValue;
                SysSequence sysSequence = new SysSequence();
                sysSequence.setSeqName(seqName);
                sysSequence.setPrevValue(tmpArr[0]);
                sysSequence.setPostValue((tmpArr[0] + length));
                sysSequenceDao.updateById(sysSequence);
            } else {
                threadService.addRunLog(ThreadServiceImpl.RUN_LOG_TYPE_GEN_ID, seqName + ":" + tmpArr[0] + "~" + length + "; 发生错误0:编号重复");
                tmpArr[0] = postValue + length;
                SysSequence sysSequence = new SysSequence();
                sysSequence.setSeqName(seqName);
                sysSequence.setPrevValue(tmpArr[0]);
                sysSequence.setPostValue((tmpArr[0] + length));
                sysSequenceDao.updateById(sysSequence);
            }
        } else {
            tmpArr[0] = 0;
            SysSequence sysSequence = new SysSequence();
            sysSequence.setPrevValue(0);
            sysSequence.setPostValue(length);
            sysSequence.setSeqName(seqName);
            sysSequence.setCreateTime(new Date());
            sysSequenceDao.insert(sysSequence);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void getSeqFromDBTx2(int[] tmpArr, String seqName, int length) {
        Integer postValue = sysSequenceDao.queryPostValue(seqName);
        tmpArr[0] = postValue;
        SysSequence sysSequence = new SysSequence();
        sysSequence.setSeqName(seqName);
        sysSequence.setPrevValue(tmpArr[0]);
        sysSequence.setPostValue((tmpArr[0] + length));
        sysSequenceDao.updateById(sysSequence);
    }
}
