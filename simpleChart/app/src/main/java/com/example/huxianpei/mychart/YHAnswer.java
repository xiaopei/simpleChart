package com.example.huxianpei.mychart;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by HXP on 2017/1/11.
 * 答案单份
 */
public class YHAnswer implements Serializable {
    private String input_name;//填空题文字
    private String text;//辅助参数。辅助答填空题
    private String input_rules;//填空题空规则
    private String scale_rules;//量表题规则
    private String scale;//量表题值
    private ScaleRule scale_rules1;//量表题规则
    private String title;//标题
    private int answerNum = 10;//答题人数
    private String id;//题号
    private String type;//题型
    private String required = REQUIRE;//是否必答
    private String hasRelation = HAS_RELATION;//是否有关联逻辑
    private String selectId;//选择选项
    private ArrayList<YHAnswerOption> option;//选项
    private Object op;//选项
    private String user_defined;//自定义答案
    private String userDefineAnswer;//文字答案，
    private int next = 0;//跳题
    private String jump = "";//接跳题
    private String valid = "";//正确答案
    private int score;//分值
    private int index = -1;//位置
    private ArrayList<String> input_pic1;//填空题图片列表
    private String input_pic;//填空题图片字符串
    private Relation correlation_show1;//逻辑关联
    private String correlation_show;//逻辑关联
    public static final String TYPE_MULTI = "checkbox";//多选
    public static final String TYPE_SINGLE = "radio";//单选
    public static final String TYPE_INPUT = "textarea";//叙述
    public static final String TYPE_FILL = "input";//填空
    public static final String TYPE_UPLOAD = "image";//图片上传
    public static final String TYPE_SINGLE_FILL = "inputradio";//组合单选
    public static final String TYPE_MULTI_FILL = "inputcheck";//组合多选
    public static final String TYPE_SCALE = "scale";//量表题
    public static final String TYPE_USER = "user";//用户统计
    public static final String TYPE_USER_AREA = "user_area";//用户地区统计
    public static final String SEPARATOR = "#@#";//填空题-空的占位符
    public static final String IMAGE_SEPARATOR = "&@&";//填空题图片的占位符
    public static final String RETURN_SEPARATOR = "<br>";//换行符

    public static final String REQUIRE = "1";//必答
    public static final String HAS_RELATION = "1";//有逻辑
    public static final String NOT_REQUIRE = "0";//非必答

    public YHAnswer() {
    }

    public YHAnswer(boolean required) {
        this.required = required ? REQUIRE : NOT_REQUIRE;
    }

    public YHAnswer(String title) {
        this.title = title;
        required = NOT_REQUIRE;
    }

    public YHAnswer(String title, int answerNum) {
        this.title = title;
        this.answerNum = answerNum;
        required = NOT_REQUIRE;
    }

    public int getNext() {
        if (jump == null || jump.length() == 0)
            return 0;
        next = Integer.valueOf(jump);
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(int answerNum) {
        this.answerNum = answerNum;
    }

    public String getUser_defined() {
        return user_defined;
    }

    public void setUser_defined(String user_defined) {
        this.user_defined = user_defined;
    }

    public String getUserDefineAnswer() {
        return userDefineAnswer;
    }

    public void setUserDefineAnswer(String userDefineAnswer) {
        this.userDefineAnswer = userDefineAnswer;
    }

    public String getJump() {
        return jump;
    }

    public void setJump(String jump) {
        this.jump = jump;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getScale_rules() {
        return scale_rules;
    }

    public void setScale_rules(String scale_rules) {
        this.scale_rules = scale_rules;
    }

    public ScaleRule getScale_rules1() {
        return scale_rules1;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public ArrayList<YHAnswerOption> getOption() {
        return option;
    }

    public void setOption(ArrayList<YHAnswerOption> options) {
        if (options != null && !options.isEmpty()) {
            YHAnswerOption lastOption = null;
            YHAnswerOption lastSecondOption = null;
            YHAnswerOption lastThirdOption = null;
            for (int i = options.size() - 1; i >= 0; i--) {
                YHAnswerOption option1 = options.get(i);
                if (option1.getOption() == null || option1.getOption().length() == 0) {
                    lastOption = option1;
                    options.remove(option1);
                }
                if ("其它".equals(option1.getOption())) {
                    lastSecondOption = option1;
                    options.remove(option1);
                }
                if ("未评定".equals(option1.getOption())) {
                    lastThirdOption = option1;
                    options.remove(option1);
                }
            }
            if (lastThirdOption != null) {
                options.add(lastThirdOption);
            }
            if (lastSecondOption != null) {
                options.add(lastSecondOption);
            }
            if (lastOption != null) {
                options.add(lastOption);
            }
        }
        this.option = options;
    }

    public String getOp() {
        return op.toString();
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getInput_name() {
        return input_name;
    }

    public void setInput_name(String input_name) {
        this.input_name = input_name;
    }


    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getHasRelation() {
        return hasRelation;
    }

    public void setHasRelation(String hasRelation) {
        this.hasRelation = hasRelation;
    }


    public String getSelectId() {
        return selectId;
    }

    public void setSelectId(String selectId) {
        this.selectId = selectId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getInput_rules() {
        return input_rules;
    }

    public void setInput_rules(String input_rules) {
        this.input_rules = input_rules;
    }

    public ArrayList<String> getInput_pic1() {
        return input_pic1;
    }

    public void setInput_pic1(ArrayList<String> input_pic) {
        this.input_pic1 = input_pic;
    }

    public Relation getCorrelation_show1() {
        return correlation_show1;
    }

    public String getInput_pic() {
        return input_pic;
    }

    public void setInput_pic(String input_pic) {
        this.input_pic = input_pic;
    }

    public String getCorrelation_show() {
        return correlation_show;
    }

    public void setCorrelation_show(String correlation_show) {
        this.correlation_show = correlation_show;
    }

    public void setCorrelation_show1(Relation correlation_show) {
        this.correlation_show1 = correlation_show;
    }

    public class Relation implements Serializable {
        private String question_no;
        private String option_key;
        private ArrayList<String> option_key1;

        public String getQuestion_no() {
            return question_no;
        }

        public void setQuestion_no(String question_no) {
            this.question_no = question_no;
        }

        public ArrayList<String> getOption_key1() {
            return option_key1;
        }

        public void setOption_key1(ArrayList<String> option_key) {
            this.option_key1 = option_key;
        }

        public String getOption_key() {
            return option_key;
        }

        public void setOption_key(String option_key) {
            this.option_key = option_key;
        }
    }


    public class ScaleRule implements Serializable {
        private YHTag max;
        private YHTag min;

        public YHTag getMax() {
            return max;
        }

        public void setMax(YHTag max) {
            this.max = max;
        }

        public YHTag getMin() {
            return min;
        }

        public void setMin(YHTag min) {
            this.min = min;
        }
    }
}
