package com.flatangle.rubbishsearch.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.flatangle.rubbishsearch.POJO.entity.AnswerRecord;
import com.flatangle.rubbishsearch.POJO.entity.Garbage;
import com.flatangle.rubbishsearch.mapper.AnswerRecordMapper;
import com.flatangle.rubbishsearch.mapper.GarbageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author DaY1zz
 * @create 2022-03-29-11:18
 */
@Service
public class AnswerService {

    @Resource
    GarbageMapper garbageMapper;

    @Resource
    AnswerRecordMapper answerRecordMapper;

    /**
     * 随机获取十个垃圾的分类信息作为题目
     * @param cityID
     * @return
     */
    public List<Garbage> getQuestions(int cityID) {
        LambdaQueryWrapper<Garbage> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Garbage::getCityID,cityID % 5);
        List<Garbage> garbageList = garbageMapper.selectList(lambdaQueryWrapper);

        List<Garbage> recycleGList = new ArrayList<>();
        List<Garbage> harmfulGList = new ArrayList<>();
        List<Garbage> wetGList = new ArrayList<>();
        List<Garbage> dryGList = new ArrayList<>();

        garbageList.forEach(e -> {
            switch (e.getType()){
                case 1:recycleGList.add(e);break;
                case 2:harmfulGList.add(e);break;
                case 3:wetGList.add(e);break;
                case 4:dryGList.add(e);break;
            }
        });

        List<Garbage> result = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 3; i++) {
            int index = random.nextInt(recycleGList.size());
            result.add(recycleGList.get(index));
        }
        for(int i = 0; i < 2; i++) {
            int index = random.nextInt(harmfulGList.size());
            result.add(harmfulGList.get(index));
        }
        for(int i = 0; i < 3; i++) {
            int index = random.nextInt(wetGList.size());
            result.add(wetGList.get(index));
        }
        for(int i = 0; i < 2; i++) {
            int index = random.nextInt(dryGList.size());
            result.add(dryGList.get(index));
        }
        Collections.shuffle(result);
        return result;
    }

    /**
     * 一轮答题结束，更新答题记录
     */
    public void completeAnswer(String userID, int score, int[] falseRecord) {
        AnswerRecord answerRecord = answerRecordMapper.selectById(userID);
        boolean flag = true;

        if(answerRecord == null){
            answerRecord = new AnswerRecord(userID);
            flag = false;
        }

        answerRecord.setScore(answerRecord.getScore()+score);

        for(int i : falseRecord) {
            switch (i) {
                case 1:answerRecord.setRecycleFalseCount(answerRecord.getRecycleFalseCount() + 1); break;
                case 2:answerRecord.setHarmfulFalseCount(answerRecord.getHarmfulFalseCount() + 1); break;
                case 3:answerRecord.setKitchenFalseCount(answerRecord.getKitchenFalseCount() + 1); break;
                case 4:answerRecord.setOtherFalseCount(answerRecord.getOtherFalseCount() + 1); break;
            }
        }
        if(flag)
            answerRecordMapper.updateById(answerRecord);
        else{
            answerRecordMapper.insert(answerRecord);
        }
    }



}