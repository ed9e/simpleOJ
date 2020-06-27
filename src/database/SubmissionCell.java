package database;
/**
 * @author ed9e
 * @ClassName: SubmissionCell
 * @Description: Submission ADT
 * @date 2019/11/27
 * @Copyright
 */
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class SubmissionCell {
    public final static int LANGUAGE_JAVA = 0;

    public final static int STATUS_QUEUE = -1;//Blocked
    public final static int STATUS_AC = 0;//ACCEPTED
    public final static int STATUS_CE = 1;//Compile Error
    public final static int STATUS_RE = 2;//Runtime Error
    public final static int STATUS_TLE = 3;//Time Limit Exceeded
    public final static int STATUS_MLE = 4;//Memory Limit Exceeded
    public final static int STATUS_WA = 5;//Wrong Answer

    private Integer submission_id;
    private Integer userid;
    private Integer problem_id;
    private Integer language;
    private String code;
    private Integer result;
    private String info;
    private Integer runtime;
    private Integer memory;
    private String input;
    private String output;
    private String expected;

    public SubmissionCell(Integer submission_id, Integer problem_id, Integer language, String code, Integer result, String info, Integer runtime, Integer memory)
    {
        this.submission_id=submission_id;
        this.problem_id=problem_id;
        this.language=language;
        this.code=code;
        this.result=result;
        this.info=info;
        this.runtime=runtime;
        this.memory=memory;
    }
    public SubmissionCell(Integer submission_id,Integer userid, Integer problem_id,String code)
    {
        this.submission_id=submission_id;
        this.userid=userid;
        this.language=LANGUAGE_JAVA;
        this.problem_id=problem_id;
        this.code=code;
        this.result=STATUS_QUEUE;
    }
    public void PublishSubmission()
    {
        try {
            String insert = "insert into submission values (?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement presta = AccessDB.getConn().prepareStatement(insert);
            presta.setInt(1, this.getSubmission_id());
            presta.setInt(2, this.getUserid());
            presta.setInt(3, this.getProblem_id());
            presta.setInt(4,this.getLanguage());
            presta.setString(5,this.getCode());
            presta.setInt(6,0);//result
            presta.setString(7,"");//info
            presta.setInt(8,0);//runtime
            presta.setInt(9,0);//memory
            presta.setString(10,"");//input
            presta.setString(11,"");//output
            presta.setString(12,"");//expected

            presta.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    SubmissionCell(){}
    public void setUserid(Integer userid){this.userid=userid;}
    public void setSubmission_id(Integer submission_id){this.submission_id=submission_id;}
    public void setCode(String code){this.code=code;}
    public void setResult(Integer result){this.result=result;}
    public void setInfo(String info){this.info=info;}
    public void setProblem_id(int problem_id){this.problem_id=problem_id;}
    public void setRuntime(Long runtime){this.runtime=runtime.intValue();}
    public void setRuntime(Integer runtime){this.runtime=runtime;}

    public void setMemory(Long memory){this.memory=memory.intValue();}
    public void setMemory(Integer memory){this.memory=memory;}
    public void setInput(String input){this.input=input;}
    public void setOutput(String output){this.output=output;}
    public void setExpected(String expected){this.expected=expected;}

    public Integer getSubmission_id(){return submission_id;}
    public String getCode(){return code;}
    public Integer getResult(){return result;}
    public String getInfo(){return info;}
    public Integer getRuntime(){return runtime;}
    public Integer getMemory(){return memory;}
    public Integer getProblem_id(){return problem_id;}
    public Integer getLanguage(){return language;}
    public Integer getUserid(){return userid;}

}
