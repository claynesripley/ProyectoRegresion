package scripts;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.base.web.base.base.ThreadLocalDriver;
import io.cucumber.java.After;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class CambioTasaCTS {

    WebDriver driver;
	public ExtentSparkReporter spark;
	public ExtentReports extent;
	public ExtentTest logger;
	@BeforeTest
    public void startTest() {

    }
        
	@BeforeMethod
    public void openApplication() {
    	System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/drivers/chromedriver");

	}


	@Test
	public void CambioTasaCTS()throws IOException, InterruptedException, AWTException {

		// AQUI PEGAS EXTENT
		extent = new ExtentReports();
		spark = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/reports/CambioTasaCTS/Report.html");
		extent.attachReporter(spark);
		extent.setSystemInfo("Host Name", "SoftwareTestingMaterial");
		extent.setSystemInfo("Environment", "Production");
		extent.setSystemInfo("User Name", "Rajkumar SM");
		spark.config().setDocumentTitle("Title of the Report Comes here ");
		spark.config().setReportName("Name of the Report Comes here ");
		spark.config().setTheme(Theme.STANDARD);

    	Thread.sleep(1500);

		ArrayList<String> usuario=readExcelData(0);
		ArrayList<String> contrase??a =readExcelData(1);
		ArrayList<String> cuenta =readExcelData(2);
		ArrayList<String> fijo =readExcelData(3);

		int filas=usuario.size();
  		for(int i=0;i<usuario.size();i++) {
			  try {

  			if(i<(filas)) {

					System.out.println("-----------------------------------");
					System.out.println("Nuevo Test " + i);
					int caso = i+1;
					logger = extent.createTest("Nuevo Test " + caso);

					// ** DESDE AQUI EMPIEZA EL TEST

				driver = new ChromeDriver();
				driver.manage().window().maximize();
				driver.get("https://10.167.21.100:8480/BrowserWebSAD/servlet/BrowserServlet?");

				Thread.sleep(1000);
				driver.findElement(By.id("details-button")).click();
				driver.findElement(By.id("proceed-link")).click();

				WebDriverWait wait = new WebDriverWait(driver, 60);
				wait.until(ExpectedConditions.elementToBeClickable(By.id("signOnName")));

				driver.findElement(By.id("signOnName")).sendKeys(usuario.get(i));
				driver.findElement(By.id("password")).sendKeys(contrase??a.get(i));
				driver.findElement(By.id("sign-in")).click();

				//WebDriverWait wait = new WebDriverWait(driver, 30);
				//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Sign Off')]")));

				WebElement iframe = driver.findElement(By.xpath("/html/frameset/frame[1]"));
				driver.switchTo().frame(iframe);

				Thread.sleep(2000);
				String exp_message = "Sign Off";
				String actual = driver.findElement(By.xpath("//a[contains(text(),'Sign Off')]")).getText();
				Assert.assertEquals(exp_message, actual);
				System.out.println("assert complete");
				driver.switchTo().parentFrame();

				Thread.sleep(1000);
				WebElement iframe1 = driver.findElement(By.xpath("/html/frameset/frame[2]"));
				driver.switchTo().frame(iframe1);

				driver.findElement(By.id("imgError")).click();

				driver.findElement(By.xpath("//img[@alt='Transacciones CTS']")).click();

				driver.findElement(By.xpath("//a[contains(text(),'Buscar Cuenta CTS ')]")).click();
				driver.switchTo().parentFrame();
				System.out.println("Buscar cuenta CTS");

				String MainWindow=driver.getWindowHandle();
				Set<String> s1=driver.getWindowHandles();
				Iterator<String> i1=s1.iterator();

				while(i1.hasNext())
				{
					String ChildWindow=i1.next();

					if(!MainWindow.equalsIgnoreCase(ChildWindow))
					{
						driver.switchTo().window(ChildWindow);
					}
				}


				wait.until(ExpectedConditions.elementToBeClickable(By.id("value:1:1:1")));
				driver.findElement(By.id("value:1:1:1")).clear();
				Thread.sleep(200);
				driver.findElement(By.id("value:2:1:1")).clear();
				Thread.sleep(200);
				String attr = driver.findElement(By.xpath("//label[contains(text(),'N??mero de cuenta')]")).getAttribute("for");
				driver.findElement(By.id(attr)).sendKeys(cuenta.get(i));
				driver.findElement(By.xpath("//a[@alt='Run Selection']")).click();
				Thread.sleep(2500);

				driver.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[2]/td/div[3]/div/form/div/table/tbody/tr[2]/td[2]/div[2]/div/table[1]/tbody/tr/td[7]/a/img")).click();

				String MainWindow2=driver.getWindowHandle();
				Set<String> s2=driver.getWindowHandles();
				Iterator<String> i2=s2.iterator();

				while(i2.hasNext())
				{
					String ChildWindow=i2.next();

					if(!MainWindow2.equalsIgnoreCase(ChildWindow))
					{
						driver.switchTo().window(ChildWindow);
					}
				}

				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td[1]/table/tbody/tr[8]/td/table/tbody/tr[2]/td/table/tbody/tr/td[2]/div[3]/div/form/div/table/tbody/tr[2]/td[2]/div[2]/div/table[1]/tbody/tr[1]/td[8]/a/img"))).click();

				driver.findElement(By.xpath("//img[@alt='Validate a deal']")).click();


				wait.until(ExpectedConditions.elementToBeClickable(By.id("fieldName:FIXED.RATE:1"))).sendKeys(fijo.get(i));

				driver.findElement(By.xpath("//img[@alt='Validate a deal']")).click();


				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[@alt='Commit the deal']")));
				driver.findElement(By.xpath("//img[@alt='Commit the deal']")).click();
				System.out.println("Commit");

				//String cod = driver.findElement(By.id("transactionId")).getCssValue("value");
				String cod = driver.findElement(By.xpath("//*[@id='messages']/tbody/tr[2]/td[2]/table[2]/tbody/tr/td")).getText();
				String cod2 = "ALTA CUENTA";
				System.out.println("este es : " +cod);
				String[] datoWrite = {cod,cod2};
				//Escriba los datos a llenar


				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.get("https://10.167.21.100:8480/BrowserWebSAD/servlet/BrowserServlet?");
				Thread.sleep(1000);


				wait.until(ExpectedConditions.elementToBeClickable(By.id("signOnName")));

				driver.findElement(By.id("signOnName")).sendKeys(usuario.get(i));
				driver.findElement(By.id("password")).sendKeys(contrase??a.get(i));
				driver.findElement(By.id("sign-in")).click();

				//WebDriverWait wait = new WebDriverWait(driver, 30);
				//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Sign Off')]")));

				WebElement iframe2 = driver.findElement(By.xpath("/html/frameset/frame[1]"));
				driver.switchTo().frame(iframe2);

				Thread.sleep(2000);
				String exp_message1 = "Sign Off";
				String actual1 = driver.findElement(By.xpath("//a[contains(text(),'Sign Off')]")).getText();
				Assert.assertEquals(exp_message, actual1);
				System.out.println("assert complete");
				driver.switchTo().parentFrame();

				Thread.sleep(1000);
				WebElement iframe3 = driver.findElement(By.xpath("/html/frameset/frame[2]"));
				driver.switchTo().frame(iframe3);

				driver.findElement(By.id("imgError")).click();

				driver.findElement(By.xpath("//img[@alt='Operaciones Minoristas']")).click();

				driver.findElement(By.xpath("//a[contains(text(),'Buscar Pr??stamo ')]")).click();
				driver.switchTo().parentFrame();

				String attr1 = driver.findElement(By.xpath("//label[contains(text(),'ID de Arreglo')]")).getAttribute("for");
				driver.findElement(By.id(attr1)).clear();
				driver.findElement(By.id(attr1)).sendKeys(cuenta.get(i));
				driver.findElement(By.xpath("//a[@alt='Run Selection']")).click();
				Thread.sleep(1000);

				driver.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[2]/td/div[3]/div/form/div/table/tbody/tr[2]/td[2]/div[2]/div/table[1]/tbody/tr/td[7]/a/img")).click();


				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[@alt='Select Drilldown']"))).click();

				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[@alt='Authorises a deal']"))).click();

				//String cod = driver.findElement(By.id("transactionId")).getCssValue("value");
				String cod1 = driver.findElement(By.xpath("//*[@id='messages']/tbody/tr[2]/td[2]/table[2]/tbody/tr/td")).getText();
				String sSubCadena = cod1.substring(22,39);
				System.out.println(sSubCadena);
				write(i+1, 5, sSubCadena);

				String screenshotPath = getScreenShot(driver, "Fin del Caso");
				logger.log(Status.PASS, MarkupHelper.createLabel(logger.addScreenCaptureFromPath(screenshotPath) + " Fin del Caso", ExtentColor.GREEN));
				extent.flush();
				write(i+1, 4, "PASSED");

				DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy, HH:mm:ss");
				String fecha = dateFormat.format(new Date());
				System.out.println(fecha);
				write(i+1, 6, fecha);

				// HASTA AQUI ELIMINA
					driver.quit();

				}

			}catch (Exception e){

				  String screenshotPath = getScreenShot(driver, "Error");
				  logger.log(Status.FAIL, MarkupHelper.createLabel(logger.addScreenCaptureFromPath(screenshotPath) + " Error: "+e, ExtentColor.RED));
				  extent.flush();
				  write(i+1, 4, "FAILED");
				  write(i+1, 5, "");

				  DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy, HH:mm:ss");
				  String fecha = dateFormat.format(new Date());
				  System.out.println(fecha);
				  write(i+1, 6, fecha);
				  System.out.println("Error: " + e);
				driver.quit();

			}
  		}

}

	public static ArrayList<String> readExcelData(int colNo) throws IOException {

		FileInputStream fis=new FileInputStream(System.getProperty("user.dir") + "/src/Excel/CambioTasaCTS.xlsx");
		XSSFWorkbook wb=new XSSFWorkbook(fis);
		XSSFSheet s=wb.getSheet("CambioTasaCTS");
		Iterator<Row> rowIterator=s.iterator();
		rowIterator.next();
		//rowIterator.next();
		ArrayList<String> list=new ArrayList<String>();
		while(rowIterator.hasNext()) {
			list.add(rowIterator.next().getCell(colNo).getStringCellValue());
		}
		System.out.println("List: "+list);
		return list;
	}

	public void write(int i, int celda, String dato) throws IOException {
		String path = System.getProperty("user.dir") + "/src/Excel/CambioTasaCTS.xlsx";
		FileInputStream fs = new FileInputStream(path);
		Workbook wb = new XSSFWorkbook(fs);
		Sheet sheet1 = wb.getSheetAt(0);
		int lastRow = sheet1.getLastRowNum();
		//for(int i=0; i<=lastRow; i++){
		Row row = sheet1.getRow(i);
		Cell cell = row.createCell(celda);
		cell.setCellValue(dato);
		//}
		FileOutputStream fos = new FileOutputStream(path);
		wb.write(fos);
		fos.close();
	}

	public static String getScreenShot(WebDriver driver, String screenshotName) throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots" under src folder
		String destination = System.getProperty("user.dir") + "/reports/CambioTasaCTS/Images/" + screenshotName + dateName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}






	@After
	public void tearDown() throws Exception
	{
		driver = ThreadLocalDriver.getTLWebDriver();
		if(driver != null){
			driver.quit();
		}
	}
	
	
}
