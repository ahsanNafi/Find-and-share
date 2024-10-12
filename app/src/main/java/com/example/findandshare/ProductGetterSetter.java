package com.example.findandshare;

public class ProductGetterSetter {

    private String pid, pname, plocation;

    public ProductGetterSetter(){

    }

    public ProductGetterSetter(String pid, String pname, String plocation) {
        this.pid = pid;
        this.pname = pname;
        this.plocation = plocation;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getProductPlocation() {
        return plocation;
    }

    public void setProductLocation(String plocation) {
        this.plocation = plocation;
    }
}
