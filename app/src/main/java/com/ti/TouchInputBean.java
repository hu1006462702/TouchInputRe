package com.ti;


import java.util.Comparator;

/**
 * Created by Administrator on 2017/4/13/013.
 */

public class TouchInputBean implements Comparator<TouchInputBean> {
    private int index ;
    private String data;

    public TouchInputBean(){
    }
    public TouchInputBean(int index, String data){
        this.index=index;
        this.data=data;
    }
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int compare(TouchInputBean o1, TouchInputBean o2) {
        if (o1.getIndex()>o2.getIndex()){
            return -1;
        }else if (o1.getIndex()==o2.getIndex()){
            return 0;
        }else {
            return 1;
        }

    }
}
