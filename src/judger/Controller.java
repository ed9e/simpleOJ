package judger;
/**
 * @author ed9e
 * @ClassName: Controller
 * @Description: Context of Judger
 * @date 2019/12/1
 * @Copyright
 */
import database.AccessDB;
import database.ProblemCell;
import database.SubmissionCell;
import database.TestCase;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Controller {
    public Controller(){ThreadPool =  Executors.newCachedThreadPool();baseDir = FileUtils.getTempDirectoryPath();}
    public Runnable TaskWrapper(String cwd)
    {
        Runnable task= () -> {
            Worker worker=new Worker();
            try
            {
                worker.save(cwd,submission);
            }
            catch (IOException e)
            {
                System.out.println("IO Error"+e.getMessage());
                return;
            }
            try
            {
                worker.compile(cwd,submission);
            }catch (RuntimeException e)
            {
                System.out.println("Compile Error"+e.getMessage());
                return;
            }
            try
            {
                worker.run(cwd,problem,testCases,submission);
            }catch (RuntimeException e)
            {
                System.out.println("Runtime Error"+e.getMessage());
                return;
            }
        };
        return task;
    }
    public void Submit(Integer id)
    {
        submission = AccessDB.AccessSubmission(id);
        problem= AccessDB.AccessProblems(submission.getProblem_id());
        testCases= AccessDB.AccessTestCases(submission.getProblem_id());
        String cwd = baseDir;
        result = ThreadPool.submit(TaskWrapper(cwd),submission);
    }
    public void Publish()  {
        try {
            baseDir = FileUtils.getTempDirectoryPath();
            String cwd = baseDir + submission.getSubmission_id();
            submission = result.get();
            FileUtils.deleteDirectory(new File(cwd));
            AccessDB.Publish(submission);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    private static ExecutorService ThreadPool;
    private static String baseDir;
    private static SubmissionCell submission;
    private static ProblemCell problem;
    private static List<TestCase> testCases;
    private static Future<SubmissionCell> result;
}