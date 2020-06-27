package gui;

import database.AccessDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainStageController {
    @FXML
    private Button Signin;
    @FXML
    private Button Signup;
    @FXML
    private Button Register;
    @FXML
    private TextArea userName;
    @FXML
    private PasswordField passWd;
    @FXML
    private TextField registName;
    @FXML
    private TextField registPassword;
    @FXML
    private Alert alert = new Alert(Alert.AlertType.WARNING);
    @FXML
    private Alert regalert = new Alert(Alert.AlertType.INFORMATION);
    /**Class owned functions**/
    public String getUser(){
        return userName.getText();
    }
    public Integer getUser_id(String userName)
    {
        return AccessDB.getUserID(userName);
    }
    public MainStageController() {
        AccessDB.Connect();
    }
    @FXML
    private void initialize() {
        StageManager.CONTROLLER.put("OnlineJudge",this);
        alert.setTitle("警告");
        alert.setHeaderText("登录错误");
        alert.setContentText("不正确的用户名或密码!");
        regalert.setTitle("Information");
        regalert.setHeaderText("注册状态");
    }
    @FXML
    private void handleButtonActionSignIn(ActionEvent event) throws Exception{
        String username = userName.getText();
        String passwd = passWd.getText();
        if(UserPassWd.Log(username,passwd))
        {
            ProblemStage problemdisplay=new ProblemStage();
            problemdisplay.showWindow();
        }
        else
            alert.showAndWait();
    }
    @FXML
    private void handleButtonActionSignUp(ActionEvent event)throws Exception
    {
        RegisterStage registage=new RegisterStage();
        registage.showWindow();
    }
    @FXML
    private void handleButtonActionRegister(ActionEvent event)throws Exception
    {
        String username = registName.getText();
        String passwd = registPassword.getText();
        String res=UserPassWd.create(username,passwd);
        regalert.setContentText(res);
        regalert.showAndWait();
    }
}