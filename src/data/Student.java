package data;

import java.io.Serializable;

public class Student extends Object implements Serializable
{
    public int cNo; //学生学号
    public String vName; //姓名
    public String cSex; //性别
    public String dBirth; //出生日期
    public String cPhone; //电话号码
    public String vEmail; //电子邮箱

    public Student(String vName, String cSex, String dBirth, String cPhone, String vEmail) {
        this.vName = vName;
        this.cSex = cSex;
        this.dBirth = dBirth;
        this.cPhone = cPhone;
        this.vEmail = vEmail;
    }

    public Student(int cNo, String vName, String cSex, String dBirth, String cPhone, String vEmail) {
        this.cNo = cNo;
        this.vName = vName;
        this.cSex = cSex;
        this.dBirth = dBirth;
        this.cPhone = cPhone;
        this.vEmail = vEmail;
    }
}//end of class Student

