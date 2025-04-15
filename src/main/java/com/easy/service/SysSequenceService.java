package com.easy.service;

/**
 * @author : hello_@
 * @Date : 2024/7/1
 */
public interface SysSequenceService {

    Integer queryPostValue(String seqName);

    void getSeqFromDBTx1(int[] tmpArr, String seqName, int length);

    void getSeqFromDBTx2(int[] tmpArr, String seqName, int length);
}
