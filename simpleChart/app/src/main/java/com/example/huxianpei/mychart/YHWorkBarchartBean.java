package com.example.huxianpei.mychart;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 工作项目管理 统计
 * Created by HXP on 2017/1/3.
 */
public class YHWorkBarchartBean implements Serializable{
    private String min_time;
    private String max_time;
    private int counts;
    private ArrayList<Info> infos;
    private Info detail;
    private ArrayList<Statistics> statistics;

    private YHWorkBarchartBean data;

    public String getMin_time() {
        return min_time;
    }

    public void setMin_time(String min_time) {
        this.min_time = min_time;
    }

    public String getMax_time() {
        return max_time;
    }

    public void setMax_time(String max_time) {
        this.max_time = max_time;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public ArrayList<Info> getInfos() {
        return infos;
    }

    public void setInfos(ArrayList<Info> infos) {
        this.infos = infos;
    }

    public Info getDetail() {
        return detail;
    }

    public void setDetail(Info detail) {
        this.detail = detail;
    }

    public ArrayList<Statistics> getStatistics() {
        return statistics;
    }

    public void setStatistics(ArrayList<Statistics> statistics) {
        this.statistics = statistics;
    }

    public YHWorkBarchartBean getData() {
        return data;
    }

    public void setData(YHWorkBarchartBean data) {
        this.data = data;
    }

    public class Info implements Serializable{
        private String project_id;
        private String title;
        private int counts;
        private int work_num;
        private int group_work_num;
        private int visit_num;
        private String apply_string;
        private String admin_string;

        public Info() {
        }

        public String getProject_id() {
            return project_id;
        }

        public void setProject_id(String project_id) {
            this.project_id = project_id;
        }

        public String getTitle() {
            return title+">";
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCounts() {
            return counts;
        }

        public void setCounts(int counts) {
            this.counts = counts;
        }

        public int getWork_num() {
            return work_num;
        }

        public void setWork_num(int work_num) {
            this.work_num = work_num;
        }

        public int getGroup_work_num() {
            return group_work_num;
        }

        public void setGroup_work_num(int group_work_num) {
            this.group_work_num = group_work_num;
        }

        public int getVisit_num() {
            return visit_num;
        }

        public void setVisit_num(int visit_num) {
            this.visit_num = visit_num;
        }

        public String getApply_string() {
            return "申请次数"+counts;
        }

        public String getAdmin_string() {
            return "申请人数"+group_work_num+"      申请次数"+work_num+"      拜访次数"+visit_num;
        }
    }

    public class Statistics implements Serializable{
        private String x_time;
        private String add_time;
        private int inited;
        private int counts;
        private int total;
        private ArrayList<Info> list;

        public String getX_time() {
            return x_time;
        }

        public void setX_time(String x_time) {
            this.x_time = x_time;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public int getInited() {
            return inited;
        }

        public void setInited(int inited) {
            this.inited = inited;
        }

        public int getCounts() {
            return counts;
        }

        public void setCounts(int counts) {
            this.counts = counts;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public ArrayList<Info> getList() {
            return list;
        }

        public void setList(ArrayList<Info> list) {
            this.list = list;
        }
    }

    public class State implements Serializable{
        private String state;
        private int counts;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public int getCounts() {
            return counts;
        }

        public void setCounts(int counts) {
            this.counts = counts;
        }
    }
}
