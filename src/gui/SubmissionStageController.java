package gui;

import database.AccessDB;
import database.SubmissionCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import judger.Controller;

public class SubmissionStageController  {
    @FXML
    private TextArea code;
    @FXML
    private TextField submission_id;
    @FXML
    private TextField result;
    @FXML
    private TextArea info;
    @FXML
    private TextField runtime;
    @FXML
    private TextField memory;
    @FXML
    private Button run;


    /**Class owned variables**/
    private static Controller context;
    private SubmissionCell subCell;

    public SubmissionStageController() {
        context = new Controller();
    }
    @FXML
    private void initialize() {
        ProblemStageController previous=(ProblemStageController)StageManager.CONTROLLER.get("Problem");
        Integer ID=previous.getID();
        subCell=AccessDB.AccessSubmission(ID);
        code.setText(subCell.getCode());
        submission_id.setText(ID.toString());
    }
    @FXML
    private void handleButtonActionRun(ActionEvent event)
    {
        Integer ID=subCell.getSubmission_id();
        context.Submit(ID);
        context.Publish();
        subCell=AccessDB.AccessSubmission(ID);
        switch (subCell.getResult())
        {
            case 0:
                 result.setText("ACCEPTED");
                break;
            case 1:
                result.setText("Compile Error");
                break;
            case 2:
                result.setText("Runtime Error");
                break;
            case 3:
                result.setText("Time Limit Exceeded");
                break;
            case 4:
                result.setText("Memory Limit Exceeded");
                break;
            case 5:
                result.setText("Wrong Answer");
                break;
            default:
        }
        info.setText(subCell.getInfo());
        runtime.setText(subCell.getRuntime().toString());
        memory.setText(subCell.getMemory().toString());
    }
}