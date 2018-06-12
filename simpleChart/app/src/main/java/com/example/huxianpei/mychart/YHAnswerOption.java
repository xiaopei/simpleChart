package com.example.huxianpei.mychart;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by HXP on 2017/1/11.
 * 答案单份
 */
public class YHAnswerOption implements Comparable<YHAnswerOption> ,Serializable{
    //    private String id = "1";
    private String option;//选项描述
    private String type;//是否支持自定义
    private String text;//填空题辅助参数
    private int selectNum;//选中该选项人数

    private boolean isCheck;//是否选中
    private float percent;//选择该选项人数占比
    private int next = 0;//跳转
    private float angle = 0;
    private int score = 0;
    private String userDefineAnswer;//自定义答案
    private String upload = "";//上传图片路径

    public static final int USER_DIFINE = 1;
    public static final int NOT_USER_DIFINE = 0;

    public YHAnswerOption() {
    }

    public YHAnswerOption(String option, int selectNum) {
        this.option = option;
        this.selectNum = selectNum;
    }

    public YHAnswerOption(String option, int selectNum, boolean isCheck) {
        this.option = option;
        this.selectNum = selectNum;
        this.isCheck = isCheck;
    }

    public YHAnswerOption(String option, int selectNum, boolean isCheck, int next) {
        this.option = option;
        this.selectNum = selectNum;
        this.isCheck = isCheck;
        this.next = next;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getIntType(){
        return Float.valueOf(type);
    }

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public int getSelectNum() {
        return selectNum;
    }

    public void setSelectNum(int selectNum) {
        this.selectNum = selectNum;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public String getUserDefineAnswer() {
        return userDefineAnswer;
    }

    public void setUserDefineAnswer(String userDefineAnswer) {
        this.userDefineAnswer = userDefineAnswer;
    }

    @Override
    public int compareTo(YHAnswerOption yhAnswerOption) {
        return yhAnswerOption.selectNum - selectNum;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
