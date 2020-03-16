package cn.edu.nju.studentdata.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "students")
public class Student extends BaseEntity{

    private static final long serialVersionUID = 1L;
    
    @Column(name = "name")
    @NotEmpty
    private String name;

    @Column(name = "sex")
    @NotEmpty
    private String sex;

    @Column(name = "birthDate")
    @NotEmpty
    private String birthDate;

    @Column(name = "nativePlace")
    @NotEmpty
    private String nativePlace;

    @Column(name = "department")
    @NotEmpty
    private String department;

    @Column(name = "studentNum")
    @NotEmpty
    private String studentNum;

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getSex(){
        return this.sex;
    }

    public void setSex(String sex){
        this.sex=sex;
    }

    public String getBirthDate(){
        return this.birthDate;
    }

    public void setBirthDate(String birthDate){
        this.birthDate=birthDate;
    }

    public String getNativePlace(){
        return this.nativePlace;
    }

    public void setNativePlace(String nativePlace){
        this.nativePlace=nativePlace;
    }

    public String getDepartment(){
        return this.department;
    }

    public void setDepartment(String department){
        this.department=department;
    }

    public String getStudentNum(){
        return this.studentNum;
    }

    public void setStudentNum(String studentNum){
        this.studentNum=studentNum;
    }
}