package database;

/**
 * @author ed9e
 * @ClassName: AccessDB
 * @Description: database module
 * @date 2019/11/19
 * @Copyright
 */
import com.sun.source.doctree.SummaryTree;
import gui.UserSubmission;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccessDB {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/query?useSSL=false&serverTimezone=UTC";
    static final String USER = "USERNAME";
    static final String PASS = "PASSWORD";
    public static List Init()
    {
        List<ProblemCell> list = new ArrayList<ProblemCell>();
        try {
            String sql = "select id,title,description,runtime_limit,memory_limit,runtime_limit from problem";
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next())
            {
                //问题记录逐条添加到集合中
                ProblemCell problem = new ProblemCell();
                problem.setId(rs.getInt("id"));
                problem.setTitle(rs.getString("title"));
                problem.setDescription(rs.getString("description"));
                problem.setMemoryLimit(rs.getInt("memory_limit"));
                problem.setRuntimeLimit(rs.getInt("runtime_limit"));
                list.add(problem);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static List AccessUserSubmission(Integer userid)
    {
        List<UserSubmission> list = new ArrayList<UserSubmission>();
        try {
            String sql = "select * from submission where userid="+userid;
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next())
            {
                UserSubmission submission = new UserSubmission();
                submission.setSubmission_id(rs.getInt("submission_id"));
                submission.setProblem_id(rs.getInt("problem_id"));                
                submission.setProblem_id(rs.getInt("problem_id"));
                submission.setInfo(rs.getString("info"));
                submission.setResult(rs.getInt("result"));
                list.add(submission);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void Connect()
    {
        try{
            Class.forName(JDBC_DRIVER);
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stat = conn.createStatement();
            String sql="use query";
            stat.executeQuery(sql);
            sql="delete from submission";
            stat.execute(sql);
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void Close()
    {
        try{
            if(conn!=null) conn.close();
            if(stat!=null) stat.close();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }
    public static SubmissionCell AccessSubmission(Integer id)
    {
        SubmissionCell res=new SubmissionCell();
        try{
            String sql="select *"+" from submission"+" where submission_id="+id;
            ResultSet result = stat.executeQuery(sql);
            if(result.next())
            {
                res.setSubmission_id(result.getInt("submission_id"));
                res.setProblem_id(result.getInt("problem_id"));
                res.setUserid(result.getInt("userid"));
                res.setCode(result.getString("code"));
                res.setResult(result.getInt("result"));
                res.setInfo(result.getString("info"));
                res.setRuntime(result.getInt("runtime"));
                res.setMemory(result.getInt("memory"));
                res.setInput(result.getString("input"));
                res.setOutput(result.getString("output"));
                res.setExpected(result.getString("expected"));
            }
            result.close();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return res;

    }
    public static ProblemCell AccessProblems(Integer id)
    {
        ProblemCell res=new ProblemCell();
        try{
            String sql="select *"+" from problem"+" where id="+id;
            ResultSet result = stat.executeQuery(sql);
            result.next();
            res.setMemoryLimit(result.getInt("memory_limit"));
            res.setTitle(result.getString("title"));
            res.setDescription(result.getString("description"));
            res.setId(result.getInt("id"));
            res.setRuntimeLimit(result.getInt("runtime_limit"));
            result.close();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return res;

    }
    public static List AccessTestCases(Integer id)
    {
       List<TestCase> list=new ArrayList<TestCase>();
        try{
            String sql="select *"+" from testcase"+" where id="+id;
            ResultSet result = stat.executeQuery(sql);
            while (result.next()) {
                //将筛选后的记录逐条添加到集合中，
                TestCase testcase = new TestCase();
                testcase.setInput(result.getString("input"));
                testcase.setOutput(result.getString("output"));
                testcase.setCaseId(result.getInt("case_id"));
                testcase.setProblemId(result.getInt("id"));
                list.add(testcase);
            }
            result.close();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return list;

    }
    public static void Publish(SubmissionCell res)
    {
        try {
            String sql = "update submission set result=?,info=?,memory=?,runtime=? where submission_id=?";
            PreparedStatement presta = conn.prepareStatement(sql);
            presta.setInt(1, res.getResult());
            presta.setString(2, res.getInfo());
            presta.setLong(3,res.getMemory());
            presta.setLong(4,res.getRuntime());
            presta.setInt(5, res.getSubmission_id());
            presta.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String CreateUser(String username,String password)
    {
        try {
            String sql = "select *"+" from user"+" where username='"+username+"'";
            ResultSet result = stat.executeQuery(sql);
            if(result.next())
            {
                result.close();
                return "用户名已存在！";
            }
            else
            {
                result.close();
                String count="select count(*) from user";
                result = stat.executeQuery(count);
                int ind=0;
                if(result.next())
                    ind=result.getInt(1);
                String insert = "insert into user values (?,?,?)";
                PreparedStatement presta = conn.prepareStatement(insert);

                presta.setString(1, username);
                presta.setString(2, password);
                presta.setInt(3, ind+1);
                presta.execute();
                return "用户创建成功！";
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static boolean Login(String username,String password)
    {
        try {
            String sql = "select *"+" from user"+" where username='"+username+"'";
            ResultSet result = stat.executeQuery(sql);
            if(result.next())
            {
                if(result.getString("password").equals(password))
                    return true;
                else
                    return false;
            }
            else
            {
                return false;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static Integer getUserID(String username)
    {
        try {
            String sql = "select userid"+" from user"+" where username='"+username+"'";
            ResultSet result = stat.executeQuery(sql);
            if(result.next())
            {
                return result.getInt("userid");
            }
            else
            {
                return 0;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
static private Connection conn;
static Connection getConn(){return conn;}
static private Statement stat;
static Statement getStat(){return stat;}
}
