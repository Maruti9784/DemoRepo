package Colectv_Login;


import org.testng.annotations.Test;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.Test;

import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;


public class Login_Page {
	
	public static final String ACCOUNT_SID = "AC9107508d214f837b5edfb68f0827dc48";
	//public static final String AUTH_TOKEN = "5b475ad4603a8ddb2d483c4d7b783c1b";
	public static final String AUTH_TOKEN = "82df4eac568ea8cd625074a63af0bc3c";
	
	
	public WebDriver driver;
	
	@Test
	public void Colectiv_MFA_Login() throws InterruptedException  {
		

	
		
		WebDriver driver = new ChromeDriver();
		
	
		
		driver.navigate().to("http://radxup-cde-test.duhs.duke.edu");
		
		driver.manage().window().maximize();
		
		Set<Cookie> allcookies = driver.manage().getCookies();

		System.out.println("Count of cookies = " + allcookies.size());

		driver.manage().deleteAllCookies();

		Set<Cookie> afterDel = driver.manage().getCookies();

		System.out.println("Count after delete = " + afterDel.size());
		
		
		
		//driver.findElement(By.xpath("//button[@type='button']")).click();
		
		driver.findElement(By.xpath("//button[@type='buttonsadf']")).click();
		
		//Thread.sleep(2000);
		
		driver.findElement(By.xpath("//a[@id='idp_248482781_button']")).click();
		
		driver.findElement(By.xpath("//button[@id='expand-netid']")).click();
		
		//driver.findElement(By.id("j_username")).click();
		
		Thread.sleep(2000);
		
		driver.findElement(By.id("j_username")).sendKeys("mbg42");
		
		driver.findElement(By.id("j_password")).sendKeys("Krishna*9784");
		
		Thread.sleep(2000);
		
	
		driver.findElement(By.xpath("//ul[@id='duoOptions']//li/label[text()='Send SMS codes (Maruti Twilo)']")).click();
		
		Thread.sleep(2000);
		
	
		
		//driver.findElement(By.id("Submit")).click();
		
		//get the OTP using Twilio APIs:
		
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		String smsBody = getMessage();
		System.out.println(smsBody);
		String allNumbers = smsBody.replaceAll("[^-?0-9]+"," ");
		String[] numbers = allNumbers.trim().split(" ");
		String smsNumber = numbers[numbers.length - 1];
		System.out.println(smsNumber);
		
		driver.findElement(By.xpath("//input[@id ='duoPasscodeInput']")).sendKeys(smsNumber);
		
		driver.findElement(By.xpath("//button[@id='Submit']")).click();
	}
	
	public static String getMessage() {
		return getMessages().filter(m -> m.getDirection().compareTo(Message.Direction.INBOUND) == 0)
				.filter(m -> m.getTo().equals("+18336390006")).map(Message::getBody).findFirst()
				.orElseThrow(IllegalStateException::new);
	}
	

	
	private static Stream<Message> getMessages() {
	    ResourceSet<Message> messages = Message.reader(ACCOUNT_SID).read();
	    return StreamSupport.stream(messages.spliterator(), false);
	}
	
}
