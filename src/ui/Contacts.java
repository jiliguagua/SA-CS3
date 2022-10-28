package ui;

import data.DataAccess;
import data.Student;
import services.Services;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;




//Java图形用户界面
public class Contacts extends JFrame
{
    String URL = "jdbc:mysql://localhost:3306/sa";
    Connection con=null;
    JTabbedPane tabbedPane = new JTabbedPane(); //选项卡
    JPanel panelA,panelB,panelC,panelD;  //选项卡上的三个面板

    //面板A上的控件
    JLabel lblCno,lblName,lblSex,lblBirth,lblPhone,lblEmail;
    JComboBox cboSex;
    JTextField txtCno,txtName,txtBirth,txtPhone,txtEmail;
    JButton btnInsertData,buttonCancel;

    //面板B上的控件
    JLabel lblNoToDel;
    JTextField txtNoToDel;
    JButton btnNoToDel;

    //面板C上的控件
    JLabel lblNameToFind;
    JTextField txtNameToFind;
    JButton btnNameToFind;
    JTextArea  areaShowResult;

    //面板D上的控件
    JLabel lblCno1,lblName1,lblSex1,lblBirth1,lblPhone1,lblEmail1;
    JComboBox cboSex1;
    JTextField txtCno1,txtName1,txtBirth1,txtPhone1,txtEmail1;
    JButton btnChangeData;
    //构造方法
    public Contacts()
    {
        super("个人通讯录系统(By 202031061367周龙)"); //设置窗口标题栏文字
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        panelA=new JPanel();
        panelB=new JPanel();
        panelC=new JPanel();
        panelD=new JPanel();
        tabbedPane.add("添加",panelA);
        tabbedPane.add("删除",panelB);
        tabbedPane.add("查看",panelC);
        tabbedPane.add("修改",panelD);

        //调用方法，往三个面板上添加组件
        addControlToPanelAdd();
        addControlToPanelDel();
        addControlToPanelQuery();
        addControlToPanelChange();


        //创建一个标签，用来显示文字，添加在North
        JLabel labelTitle=new JLabel(" 个人通讯录");
        labelTitle.setFont(new Font(null,Font.BOLD,36)); //设置字体
        labelTitle.setForeground( new Color(60,60,160) ); //设置颜色
        getContentPane().add("North",labelTitle);

        //添加选项卡组件到居中位置
        getContentPane().add("Center",tabbedPane);

        //给按钮注册事件侦听
        MyButtonListener listen=new MyButtonListener();
        btnInsertData.addActionListener(listen);
        btnNoToDel.addActionListener(listen);
        btnNameToFind.addActionListener(listen);
        btnChangeData.addActionListener(listen);
    } // end of constructor


    //添加控件到面板B（删除数据）上的方法
    public void   addControlToPanelDel()
    {
        lblNoToDel=new JLabel("请输入要删除的ID：");
        txtNoToDel=new JTextField("1" ,15);
        btnNoToDel=new JButton("删除");
        panelB.add(lblNoToDel);
        panelB.add(txtNoToDel);
        panelB.add(btnNoToDel);
    }

    //添加控件到面板C（查询数据）上的方法
    public void   addControlToPanelQuery()
    {
        btnNameToFind=new JButton("刷新");
        areaShowResult=new JTextArea(20,80); //20行，80列

        JPanel pTmp=new JPanel();
        pTmp.add(btnNameToFind);

        //面板C采用边界布局，上方是输入信息组件，下方是显示信息组件
        BorderLayout bl=new BorderLayout();
        panelC.setLayout(bl);

        //面板C的靠上组件pTmp，居中组件areaShowResult
        panelC.add("North" ,pTmp);
        panelC.add("Center" ,areaShowResult);
    }

    //添加控件到面板A上的方法
    public void addControlToPanelAdd()
    {
//        lblCno=new JLabel("学号 *  ");
        lblName=new JLabel("姓名 *  "); //标签后面跟上1个星号提醒用户，这是必须输入的字段
        lblSex=new JLabel("性别 *  ");
        lblBirth=new JLabel("出生日期");
        lblPhone=new JLabel("电话号码");
        lblEmail=new JLabel("电子邮箱");

        txtCno=new JTextField("201731061234",12);
        txtName=new JTextField("张三",10);
        String sex[]={"女","男"};cboSex=new JComboBox(sex);
        //为了便于测试，文本框中给了一个默认数据
        txtBirth=new JTextField("1998-09-18",10);
        txtPhone=new JTextField("028-83039876",15);
        txtEmail=new JTextField("zhangs@yahoo.com.cn",18);


        btnInsertData=new JButton("添加");
        buttonCancel=new JButton("清空");
        //对当前面板设置布局管理
        GridBagLayout gbl=new GridBagLayout();
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.anchor=GridBagConstraints.NORTHWEST;
        panelA.setLayout(gbl);

        //添加控件 第1行
        gbc.gridy=2;gbc.gridx=1;
        gbl.setConstraints(lblName,gbc);
        panelA.add(lblName);
        gbc.gridy=2;gbc.gridx=2;
        gbl.setConstraints(txtName,gbc);
        panelA.add(txtName);
        //添加控件 第2行
        gbc.gridy=3;gbc.gridx=1;
        gbl.setConstraints(lblSex,gbc);
        panelA.add(lblSex);
        gbc.gridy=3;gbc.gridx=2;
        gbl.setConstraints(cboSex,gbc);
        panelA.add(cboSex);
        //添加控件 第3行
        gbc.gridy=4;gbc.gridx=1;
        gbl.setConstraints(lblBirth,gbc);
        panelA.add(lblBirth);
        gbc.gridy=4;gbc.gridx=2;
        gbl.setConstraints(txtBirth,gbc);
        panelA.add(txtBirth);
        //添加控件 第4行
        gbc.gridy=5;gbc.gridx=1;
        gbl.setConstraints(lblPhone,gbc);
        panelA.add(lblPhone);
        gbc.gridy=5;gbc.gridx=2;
        gbl.setConstraints(txtPhone,gbc);
        panelA.add(txtPhone);
        //添加控件 第5行
        gbc.gridy=6;gbc.gridx=1;
        gbl.setConstraints(lblEmail,gbc);
        panelA.add(lblEmail);
        gbc.gridy=6;gbc.gridx=2;
        gbl.setConstraints(txtEmail,gbc);
        panelA.add(txtEmail);

        //添加控件 第6行
        gbc.gridy=7;gbc.gridx=3;
        gbl.setConstraints(btnInsertData,gbc);
        panelA.add(btnInsertData);
        gbc.gridy=7;gbc.gridx=4;
        gbl.setConstraints(buttonCancel,gbc);
        panelA.add(buttonCancel);
    }

    //添加控件到面板A上的方法
    public void addControlToPanelChange()
    {
        lblCno1=new JLabel("ID *  ");
        lblName1=new JLabel("姓名 *  "); //标签后面跟上1个星号提醒用户，这是必须输入的字段
        lblSex1=new JLabel("性别 *  ");
        lblBirth1=new JLabel("出生日期");
        lblPhone1=new JLabel("电话号码");
        lblEmail1=new JLabel("电子邮箱");

        txtCno1=new JTextField("1",12);
        txtName1=new JTextField("张三",10);
        String sex[]={"女","男"};cboSex1=new JComboBox(sex);
        //为了便于测试，文本框中给了一个默认数据
        txtBirth1=new JTextField("1998-09-18",10);
        txtPhone1=new JTextField("028-83039876",15);
        txtEmail1=new JTextField("zhangs@yahoo.com.cn",18);


        btnChangeData=new JButton("修改");
        //对当前面板设置布局管理
        GridBagLayout gbl=new GridBagLayout();
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.anchor=GridBagConstraints.NORTHWEST;
        panelD.setLayout(gbl);
        //添加控件 第1行
        gbc.gridy=1;gbc.gridx=1;
        gbl.setConstraints(lblCno1,gbc);
        panelD.add(lblCno1);
        gbc.gridy=1;gbc.gridx=2;
        gbl.setConstraints(txtCno1,gbc);
        panelD.add(txtCno1);
        //添加控件 第2行
        gbc.gridy=2;gbc.gridx=1;
        gbl.setConstraints(lblName1,gbc);
        panelD.add(lblName1);
        gbc.gridy=2;gbc.gridx=2;
        gbl.setConstraints(txtName1,gbc);
        panelD.add(txtName1);
        //添加控件 第3行
        gbc.gridy=3;gbc.gridx=1;
        gbl.setConstraints(lblSex1,gbc);
        panelD.add(lblSex1);
        gbc.gridy=3;gbc.gridx=2;
        gbl.setConstraints(cboSex1,gbc);
        panelD.add(cboSex1);
        //添加控件 第4行
        gbc.gridy=4;gbc.gridx=1;
        gbl.setConstraints(lblBirth1,gbc);
        panelD.add(lblBirth1);
        gbc.gridy=4;gbc.gridx=2;
        gbl.setConstraints(txtBirth1,gbc);
        panelD.add(txtBirth1);
        //添加控件 第5行
        gbc.gridy=5;gbc.gridx=1;
        gbl.setConstraints(lblPhone1,gbc);
        panelD.add(lblPhone1);
        gbc.gridy=5;gbc.gridx=2;
        gbl.setConstraints(txtPhone1,gbc);
        panelD.add(txtPhone1);
        //添加控件 第6行
        gbc.gridy=6;gbc.gridx=1;
        gbl.setConstraints(lblEmail1,gbc);
        panelD.add(lblEmail1);
        gbc.gridy=6;gbc.gridx=2;
        gbl.setConstraints(txtEmail1,gbc);
        panelD.add(txtEmail1);


        //添加控件 第7行
        gbc.gridy=7;gbc.gridx=3;
        gbl.setConstraints(btnChangeData,gbc);
        panelD.add(btnChangeData);
    }

    //显示提示信息的方法
    private void showMsg(String sArgs)
    {
        JOptionPane.showMessageDialog(Contacts.this,sArgs);
    }//end of showMsg


    class MyButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            //根据事件源是不同按钮，调用不同的方法
            if (evt.getSource()==btnInsertData) insertData();
            if (evt.getSource()== btnNoToDel) deleteData();
            if (evt.getSource()==btnNameToFind) selectData();
            if (evt.getSource() == btnChangeData) changeData();
        }
    } // end of class MyButtonListener

    public static void main(String args[])
    {
        Contacts obj=new Contacts();
        obj.setBounds(640, 300, 640,480);
        obj.setVisible(true);
    }//end of main

    //添加数据对应的代码封装在这里
    private void  insertData()
    {
//        String sNo=txtCno.getText();
        String sName=txtName.getText();
        String sSex=(String)cboSex.getSelectedItem();
        String sBirth=txtBirth.getText();
        String sPhone=txtPhone.getText();
        String sEmail=txtEmail.getText();

        String sSapce="    ";//四个空格
        String sTmp=sSapce+ sName +sSapce+ sSex +sSapce+ sBirth +sSapce+ sPhone +sSapce+ sEmail;
        showMsg(sTmp);
        if (new Services().insertStu(new Student(sName, sSex, sBirth, sPhone, sEmail)))
            showMsg("添加成功！");
        else
            showMsg("出现SQL异常，添加失败！");

    }//end of insertData()


    //删除数据对应的代码封装在这里
    private void  deleteData()
    {
        String sNo=txtNoToDel.getText();
        showMsg("您要删除的id是："+sNo);
        int flag = new Services().deleteStu(sNo);
        if (flag == -1)  showMsg("出现SQL异常，删除失败！");
        else if (flag == 0) showMsg("联系人不存在！删除失败！");
        else if (flag == 1) showMsg("删除成功！");
    }//end of deleteData()

    //查询数据对应的代码封装在这里
    private void  selectData()
    {
        try{
            ResultSet rs = new Services().selectStu();
            if(rs != null && rs.first()){
                rs.last();
                areaShowResult.setColumns(6);
                areaShowResult.setRows(rs.getRow());
                String[][] data=new String[rs.getRow()][6];rs.beforeFirst();
                int count=0;
                while(rs.next()){
                    data[count][0]=rs.getString("cNo");
                    data[count][1]=rs.getString("vName");
                    data[count][2]=rs.getString("cSex");
                    data[count][3]=rs.getString("dBirth");
                    data[count][4]=rs.getString("cPhone");
                    data[count][5]=rs.getString("vEmail");
                    count++;
                }
                areaShowResult.setText("");
                areaShowResult.append("ID"+"             "+"姓名"+"            "+"性别"+"            "+
                        "出生日期"+"                      "+"电话号码"+"                     "+"电子邮箱");
                areaShowResult.append("\n");
                for(int i=0;i<data.length;i++){
                    for(int j=0;j<6;j++){
                        areaShowResult.append(data[i][j]);
                        areaShowResult.append("              ");
                    }
                    areaShowResult.append("\n");
                }
            }else{
                areaShowResult.setText("");
            }
        }catch(SQLException e){
            e.printStackTrace();
            showMsg("出现SQL异常，查询失败！");
        }
    }

    private void changeData() {
        String sNo=txtCno1.getText();
        String sName=txtName1.getText();
        String sSex=(String)cboSex1.getSelectedItem();
        String sBirth=txtBirth1.getText();
        String sPhone=txtPhone1.getText();
        String sEmail=txtEmail1.getText();

        try{
            if (new Services().changeStu(new Student(Integer.parseInt(sNo), sName, sSex, sBirth, sPhone, sEmail)))   showMsg("修改成功！");
            else showMsg("修改成功！");
        }catch(SQLException e){
            e.printStackTrace();
            showMsg("出现SQL异常，添加失败！");
        }
    }
}
