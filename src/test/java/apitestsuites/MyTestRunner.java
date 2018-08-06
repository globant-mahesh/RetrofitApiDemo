package apitestsuites;


import commonutilities.CommonListener;
import org.junit.runner.JUnitCore;

public class MyTestRunner {
    public static void main(String args[]) {
        JUnitCore runner = new JUnitCore();
        runner.addListener(new CommonListener());
        runner.run(AllTests.class);
//        Result result = JUnitCore.runClasses(AllTests.class);
    }
}
