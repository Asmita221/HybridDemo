package org.procto.Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;
import org.procto.Base.TestBase;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReport extends TestBase implements ITestListener {
    ExtentSparkReporter htmlreport;
    ExtentReports reports;
    ExtentTest test;
    TestBase B;

    public void setReports() {
        String ReportName = "Procto.html";
        //String timestamp = new SimpleDateFormat("yyyy.mm.dd.hh.mm.ss").format(new Date());
        htmlreport = new ExtentSparkReporter(System.getProperty("user.dir") + "//Reports//" + " "+ ReportName);
        reports = new ExtentReports();
        reports.attachReporter(htmlreport);
        //----------setting environment
        reports.setSystemInfo("Title", "Procto Care");
        reports.setSystemInfo("OS", "Windows 11");
        reports.setSystemInfo("browser", "Chrome");
        reports.setSystemInfo("created_By", "Asmita Raut");
        //----------report configuration-
        htmlreport.config().setDocumentTitle("Procto Care report");
        htmlreport.config().setReportName("Procto test report");
        htmlreport.config().setTheme(Theme.DARK);
        //htmlreport.config().setTimeStampFormat("EEEE,MM,DD,hh:mm a '('zzz')'");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test =reports.createTest(result.getName());
        test.log(Status.PASS, MarkupHelper.createLabel("Name of Passed test case: "+ result.getName(), ExtentColor.GREEN));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Name of failed test case: "+ result.getName());
        test =reports.createTest(result.getName());
        test.log(Status.FAIL, MarkupHelper.createLabel("Name of failed test case: "+ result.getName(), ExtentColor.RED));
        B = new TestBase();
        try {
            B.Capture_Screenshot(result.getName());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Name of skip test case: "+ result.getName());
        test =reports.createTest(result.getName());
        test.log(Status.SKIP, MarkupHelper.createLabel("Name of Skipped test case: "+ result.getName(), ExtentColor.PURPLE));

    }

    @Override
    public void onStart(ITestContext context) {
        setReports();
        System.out.println("Script Execution is started");
    }

    @Override
    public void onFinish(ITestContext context) {
        reports.flush();
    }


}


