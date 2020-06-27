package gui;
public  class UserSubmission {
    private  Integer submission_id;
    private  Integer problem_id;
    private  String info;
    private  Integer result;
    public UserSubmission(Integer submission_id, Integer problem_id, String title,Integer result) {
        this.submission_id = submission_id;
        this.problem_id = problem_id;
        this.info = title;
        this.result = result;
    }
    public UserSubmission() { }

    public Integer getSubmission_id() { return submission_id; }
    public Integer getProblem_id() { return problem_id; }
    public String getInfo() { return info; }
    public String getResult() {
        switch (result)
        {
            case 0:
                return "ACCEPTED";
            case 1:
                return "Compile Error";
            case 2:
                return "Runtime Error";
            case 3:
                return "Time Limit Exceeded";
            case 4:
                return "Memory Limit Exceeded";
            case 5:
                return "Wrong Answer";
            default:
        }
        return "";
    }

    public void setSubmission_id(Integer submission_id) { this.submission_id=submission_id; }
    public void setProblem_id(Integer problem_id) { this.problem_id=problem_id; }
    public void setInfo(String info){ this.info=info; }
    public void setResult(Integer result) { this.result=result;}
}