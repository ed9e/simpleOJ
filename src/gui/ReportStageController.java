package gui;

import database.AccessDB;
import database.ProblemCell;
import database.SubmissionCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.util.List;

public class ReportStageController {
    @FXML
    private Text user;
    @FXML
    private TableView<UserSubmission> submissionTable = new TableView<>();
    @FXML
    private  TableColumn<UserSubmission, Integer> submission_id = new TableColumn<UserSubmission, Integer>();
    @FXML
    private  TableColumn<UserSubmission, Integer> problem_id = new TableColumn<UserSubmission, Integer>();
    @FXML
    private  TableColumn<UserSubmission, String> info = new TableColumn<UserSubmission, String>();
    @FXML
    private  TableColumn<UserSubmission, String> result = new TableColumn<UserSubmission, String>();
    @FXML
    private ObservableList<UserSubmission> tableViewData;
    /**Class owned variables**/
    private Integer user_id;

    /**Class owned functions**/
    public ReportStageController(){
        tableViewData = FXCollections.observableArrayList();
    }
    @FXML
    private void initialize() {
        StageManager.CONTROLLER.put("Report",this);
        MainStageController controller = (MainStageController)StageManager.CONTROLLER.get("OnlineJudge");
        user.setText("用户"+controller.getUser());
        user_id = controller.getUser_id(controller.getUser());
        submission_id.setCellValueFactory(
                new PropertyValueFactory<>("submission_id")
        );
        problem_id.setCellValueFactory(
                new PropertyValueFactory<>("problem_id")
        );
        info.setCellValueFactory(
                new PropertyValueFactory<>("info")
        );
        result.setCellValueFactory(
                new PropertyValueFactory<>("result")
        );
        List<UserSubmission> list= AccessDB.AccessUserSubmission(user_id);
        for(int x=0;x<list.size();x++)
        {
            UserSubmission temp=list.get(x);
            tableViewData.add(temp);
        }
        submissionTable.setItems(tableViewData);
    }
}