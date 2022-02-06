package com.example.complaintappdemo;

public class UserInfo {
    private String name;
    private String RegisterNo;
    private String Address;
    private String Issue;
    public UserInfo(){}

    public UserInfo(String name,String RegistrationNo,String Address,String Issue){
        name=this.name;
        RegistrationNo=this.RegisterNo;
        Address=this.Address;
        Issue=this.Issue;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getIssue() {
        return Issue;
    }

    public void setIssue(String issue) {
        Issue = issue;
    }
}
