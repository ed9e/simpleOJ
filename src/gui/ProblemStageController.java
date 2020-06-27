package gui;

import database.AccessDB;
import database.ProblemCell;
import database.SubmissionCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.List;

public class ProblemStageController {
    @FXML
    private Button Submit;
    @FXML
    private ObservableList<ProblemCell> listViewData = FXCollections.observableArrayList();
    @FXML
    private ListView<ProblemCell> myListView= new ListView<ProblemCell>(listViewData);
    @FXML
    private TextArea ProblemDescription;
    @FXML
    private TextArea CodeArea;
    @FXML
    private Alert subalert = new Alert(Alert.AlertType.WARNING);
    @FXML
    private Text username;
    /**Class owned functions**/

    public Integer getID(){return SubmissionID-1;}

    /**Class owned variables**/
    private static List<ProblemCell> list;
    private Integer SubmissionID=1;
    private Integer CurrProblemID=0;
    private Integer userid;
    public ProblemStageController() {
        list=AccessDB.Init();
    }
    @FXML
    private void initialize() {
        StageManager.CONTROLLER.put("Problem",this);
        MainStageController controller = (MainStageController)StageManager.CONTROLLER.get("OnlineJudge");
        username.setText("用户"+controller.getUser());
        userid=controller.getUser_id(controller.getUser());
        for(int x=0;x<list.size();x++)
        {
            ProblemCell temp=list.get(x);
            listViewData.add(temp);
        }
        subalert.setTitle("警告");
        subalert.setHeaderText("提交代码错误");
        subalert.setContentText("请选择一个题目，并提交有效代码！");
        myListView.setPlaceholder(new Label("无题目"));
        myListView.setItems(listViewData);
        myListView.setFixedCellSize(50);
        myListView.getSelectionModel().selectedItemProperty().addListener(new NoticeListItemChangeListener());
    }
    @FXML
    private void handleButtonActionSubmit(ActionEvent event)throws Exception
    {
        String Code= CodeArea.getText();
        if(Code.isEmpty())
            subalert.showAndWait();
        else if(CurrProblemID==0)
            subalert.showAndWait();
        else
        {
            try {
                SubmissionCell submissionCell = new SubmissionCell(SubmissionID++, userid,CurrProblemID, Code);
                submissionCell.PublishSubmission();
                SubmissionStage submission = new SubmissionStage();
                submission.showWindow();
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
    @FXML
    private void handleButtonActiongenReport(ActionEvent event)throws Exception
    {
        ReportStage report=new ReportStage();
        report.showWindow();
    }
    private class NoticeListItemChangeListener implements ChangeListener<Object> {
        @Override
        public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue)
        {
            String title=newValue.toString();
            for(int i=0;i<list.size();i++)
            {
                if(list.get(i).getTitle().equals(title))
                {
                    ProblemDescription.setText(list.get(i).getDescription());
                    CurrProblemID=list.get(i).getId();
                }
            }
        }
    }


}