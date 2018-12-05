package ir.co.sadad.restclients.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;

/**
 * @author ammac
 */

public class Employee extends User {


    private String employeeNo;

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

}