package judger;
/**
 * @author ed9e
 * @ClassName: Worker
 * @Description: Judge thread implements
 * @date 2019/12/1
 * @Copyright
 */
import database.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
public class Worker {
    final private String javac="javac";
    final private String java="java";
    final private Integer outputMaxLength=1000;
    final private Integer watchdogTimeout=10000;

    public void save(String cwd, SubmissionCell submission) throws IOException {
        File directory = new File(cwd+submission.getSubmission_id());
        if ( !(directory).exists()){
            directory.mkdir();
        }
        Files.write(Paths.get(cwd +submission.getSubmission_id()+File.separator + "Main.java"), submission.getCode().getBytes());
        /*policyFile = cwd  + "policy";
        Files.createFile(Paths.get(policyFile));//No Policy File Currently
        */
    }
    public void compile(String cwd, SubmissionCell submission) throws RuntimeException {
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();
        ExecuteWatchdog watchdog = new ExecuteWatchdog(watchdogTimeout);

        DefaultExecutor executor = new DefaultExecutor();
        executor.setWorkingDirectory(new File(cwd));
        executor.setStreamHandler(new PumpStreamHandler(null, stderr, null));
        executor.setWatchdog(watchdog);
        CommandLine cmd = new CommandLine(javac);
        cmd.addArgument("-J-Duser.language=en");
        cmd.addArgument(cwd+submission.getSubmission_id()+File.separator+"Main.java");
        System.out.println("Compiler cmd:\t" + cmd.toString());
        try {
            executor.execute(cmd);
            System.out.println("Compile OK");
        } catch (IOException e) {
            if (watchdog.killedProcess()) {
                submission.setResult(SubmissionCell.STATUS_CE);
                submission.setInfo("Compile Time Exceeded");
                System.out.println("Compile Time Exceeded:\t" + e.getMessage());
            } else {
                submission.setResult(SubmissionCell.STATUS_CE);
                submission.setInfo("Compile error"+e.getMessage());
                System.out.println("Compile error:\t" + e.getMessage());
            }
            throw new RuntimeException("Compile Aborted.");
        }
    }

    public void run(String cwd, ProblemCell problem, List<TestCase> testCases, SubmissionCell submission) throws RuntimeException {
        CommandLine cmd = new CommandLine(java);
        cmd.addArgument("-Djava.security.manager");
        //cmd.addArgument("-Djava.security.policy==" + policyFile);//No Policy
        cmd.addArgument("-Xmx" + problem.getMemoryLimit() + "m");
        cmd.addArgument("-classpath");
        cmd.addArgument(cwd+submission.getSubmission_id()+File.separator);
        cmd.addArgument("Main");

        System.out.println("Sandbox cmd:\t" + cmd.toString());

        long cost = 0;
        for (TestCase testCase : testCases) {
            ByteArrayInputStream stdin = new ByteArrayInputStream(testCase.getInput().getBytes());
            ByteArrayOutputStream stdout = new ByteArrayOutputStream(), stderr = new ByteArrayOutputStream();
            ExecuteWatchdog watchdog = new ExecuteWatchdog(problem.getRuntimeLimit());

            DefaultExecutor executor = new DefaultExecutor();
            executor.setWorkingDirectory(new File(cwd));
            executor.setStreamHandler(new PumpStreamHandler(stdout, stderr, stdin));
            executor.setWatchdog(watchdog);

            long startTime = System.nanoTime();
            try {
                executor.execute(cmd);
            } catch (IOException e) {
                if (watchdog.killedProcess()) {
                    submission.setResult(SubmissionCell.STATUS_TLE);
                    submission.setInfo("Time Limit Exceeded");
                } else {
                    submission.setResult(SubmissionCell.STATUS_RE);
                    submission.setInfo(stderr.toString());
                }
                System.out.println("Runtime error:\t" + e.toString());
                throw new RuntimeException("Execution Aborted.");
            }
            cost += System.nanoTime() - startTime;

            String o = stdout.toString().trim();
            if (!o.equals(testCase.getOutput())) {
                submission.setResult(SubmissionCell.STATUS_WA);
                submission.setInput(testCase.getInput());
                submission.setOutput(o.length() > outputMaxLength ? o.substring(0, outputMaxLength) + "..." : o);
                submission.setExpected(testCase.getOutput());
                String info="Input: " + testCase.getInput()+"\n"+"Output: " + o.substring(0, Math.min(outputMaxLength, o.length()))+"\n"+"Expected: " + testCase.getOutput();
                submission.setInfo(info);
                throw new RuntimeException("Wrong Answer.");
            }

        }
        submission.setRuntime(cost / 1000000);
        submission.setMemory(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed()/ (1024 * 1024));
        submission.setResult(SubmissionCell.STATUS_AC);
        submission.setInfo("Answer Accepted!");
    }
}