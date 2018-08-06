package commonutilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.log4j.PropertyConfigurator;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;

public class CommonListener extends RunListener implements Configurable {
    private ExtentReports extentReport = null;
    private Status status;

    public CommonListener() {
        init();
    }

    public void testStarted(Description description) {
        getLogger().info("\n\n\nStarting .... " + description.getMethodName());
    }

    public void testFinished(Description description) {
        if (status == Status.FAIL) {
            reportTest(description.getMethodName(), Status.PASS);
        } else if (status == Status.SKIP) {
            reportTest(description.getMethodName(), Status.SKIP);
        } else {
            reportTest(description.getMethodName(), Status.PASS);
        }
        getLogger().info("Finished .... " + description.getMethodName());
    }

    public void testFailure(Failure failure) throws Exception {
        status = Status.FAIL;
    }

    public void testIgnored(Description description) throws Exception {
        status = Status.FAIL;
        getLogger().info("Skipped .... " + description.getMethodName());
    }

    public void testRunFinished(Result result) {
        getLogger().info("\n\n");
    }

    public Logger getLogger() {
        Logger logger = LoggerFactory.getLogger(getClass());
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        return logger;
    }

    public void init() {
        String dateTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        if (dateTime.isEmpty() || dateTime == null) {
            dateTime = now().format(ofPattern("dd-MM-yyyy-HH.mm.ss"));
        }
        String filePath = format("%s//%s_%s.html", "target", "extent", dateTime);
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(new File(filePath));
        htmlReporter.config().setDocumentTitle("ExtentReports - Created by TestNG Listener");
        htmlReporter.config().setReportName("ExtentReports - Created by TestNG Listener");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setTheme(Theme.STANDARD);
        extentReport = new ExtentReports();
        extentReport.attachReporter(htmlReporter);
        extentReport.setReportUsesManualConfiguration(true);
    }

    @Override
    public void reportTest(String methodName, Status status) {
        ExtentTest test = extentReport.createTest(methodName);
        test.log(status, "Test " + status.toString().toLowerCase() + "ed");
        extentReport.flush();
    }

}