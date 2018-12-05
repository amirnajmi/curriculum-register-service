package ir.co.sadad.restclients.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;

/**
 * @author ammac
 */

public class Student extends User {


    private String studentNo;

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

}