package commonutilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;

public class LogConfig {
    private ExtentReports extentReport = null;
    private static Logger logger;

    public static Logger getLogger() {
        logger = LoggerFactory.getLogger(LogConfig.class);
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        return logger;
    }

}
