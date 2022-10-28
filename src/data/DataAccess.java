package data;

import java.sql.*;

public class DataAccess {
    String URL = "jdbc:mysql://localhost:3306/sa";
    String username = "root";
    String password = "123456";
    Connection con=null;

    public DataAccess(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getConnection() {
        try {
            con = DriverManager.getConnection(URL, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean insertStu(Student stu) {
        getConnection();
        if (con == null)    return false;
        try {
            PreparedStatement pst = con.prepareStatement("INSERT INTO Student(vName,cSex,dBirth,cPhone,vEmail) VALUES(?,?,?,?,?)",
                    ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            pst.setString(1,stu.vName);
            pst.setString(2,stu.cSex);
            pst.setString(3,stu.dBirth);
            pst.setString(4,stu.cPhone);
            pst.setString(5,stu.vEmail);
            pst.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int deleteStu(String id){
        getConnection();
        if (con == null) return -1;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = con.prepareStatement("SELECT * FROM Student WHERE cNo=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setString(1, id);
            rs = pst.executeQuery();
            if (rs.first()) {
                PreparedStatement pst1 = con.prepareStatement("DELETE FROM Student WHERE cNo=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                pst1.setString(1, id);
                pst1.executeUpdate();
                pst1.close();
                rs.close(); pst.close();con.close();
                return 1;
            }
            rs.close(); pst.close();con.close();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ResultSet selectStu() throws SQLException{
        getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT cNo,vName,cSex,dBirth,cPhone,vEmail FROM Student", ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
        return pst.executeQuery();
    }

    public boolean changeStu(Student stu) throws SQLException{
        getConnection();
        PreparedStatement pst1 = con.prepareStatement("SELECT * FROM Student WHERE cNo=?",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
        pst1.setInt(1,stu.cNo);
        ResultSet rs = pst1.executeQuery();
        if(!rs.first()){
            con.close();pst1.close();rs.close();
            return false;
        }
        PreparedStatement pst = con.prepareStatement("UPDATE Student SET vName=?, cSex=?, dBirth=?, cPhone=?, vEmail=? WHERE cNo=?",
                ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
        pst.setString(1,stu.vName);
        pst.setString(2,stu.cSex);
        pst.setString(3,stu.dBirth);
        pst.setString(4,stu.cPhone);
        pst.setString(5,stu.vEmail);
        pst.setInt(6,stu.cNo);
        pst.executeUpdate();
        pst.close();con.close();
        pst1.close();rs.close();
        return true;
    }
}
