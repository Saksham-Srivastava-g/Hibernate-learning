package com.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Employee {
    @Id
    private int eid;
    private String ename;
    private String eaddress;
    public int getEid() {
        return eid;
    }
    @Override
    public String toString() {
        return "Employee [eid=" + eid + ", ename=" + ename + ", eaddress=" + eaddress + "]";
    }
    public void setEid(int eid) {
        this.eid = eid;
    }
    public String getEname() {
        return ename;
    }
    public void setEname(String ename) {
        this.ename = ename;
    }
    public String getEaddress() {
        return eaddress;
    }
    public void setEaddress(String eaddress) {
        this.eaddress = eaddress;
    }
    public Employee(int eid, String ename, String eaddress) {
        this.eid = eid;
        this.ename = ename;
        this.eaddress = eaddress;
    }
    public Employee() {
    }
    
}
