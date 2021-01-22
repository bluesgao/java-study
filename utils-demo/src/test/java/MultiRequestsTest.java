import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;
import org.junit.Test;

public class MultiRequestsTest {
    @Test
    public void MultiRequestsTest() {
        // 构造一个Runner
        TestRunnable runner = new TestRunnable() {
            @Override
            public void runTest() throws Throwable {
                //TODO 测试内容
                System.out.println("ThreadId:"+Thread.currentThread().getId());
            }
        };
        int runnerCount = 100;
        //Runner数组，想当于并发多少个。
        TestRunnable[] arrTestRunner = new TestRunnable[runnerCount];
        for (int i = 0; i < runnerCount; i++) {
            arrTestRunner[i] = runner;
        }
        // 用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(arrTestRunner);
        try {
            // 开发并发执行数组里定义的内容
            mttr.runTestRunnables();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
