package database;
/**
 * @author ed9e
 * @ClassName: TestCase
 * @Description: TestCase ADT
 * @date 2019/12/1
 * @Copyright
 */
public class TestCase {

    private Integer problemId;
    private Integer caseId;
    private String input;
    private String output;

    public String getInput(){return input;}
    public String getOutput(){return output;}
    public Integer getCaseId(){return caseId;}
    public Integer getProblemId(){return problemId;}

    public void setCaseId(Integer caseId){this.caseId=caseId;}
    public void setProblemId(Integer problemId){this.problemId=problemId;}
    public void setInput(String input){this.input=input;}
    public void setOutput(String output){this.output=output;}
}
