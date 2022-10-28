package services;

import data.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Services {
    public boolean insertStu(Student stu) {
        return new DataAccess().insertStu(stu);
    }

    public int deleteStu(String id) {
        return new DataAccess().deleteStu(id);
    }

    public ResultSet selectStu() throws SQLException {
        return new DataAccess().selectStu();
    }

    public boolean changeStu(Student stu) throws SQLException {
        return new DataAccess().changeStu(stu);
    }
}
