package Colectv_Login;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.util.SystemOutLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrokenLinks {

	public static void main(String[] args) {

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://hr.duke.edu/benefits");

		List<WebElement> links = driver.findElements(By.tagName("a"));

		System.out.println("No of links are " + links.size());

		List<String> urlList = new ArrayList<String>();

		for (WebElement e : links) {

			String url = e.getAttribute("href");
			// urlList.add(url);
			if (url != null && !url.isEmpty() && url.startsWith("http")) { // Filter valid URLs
				urlList.add(url);
			}

		}

		long stTime = System.currentTimeMillis();
		urlList.parallelStream().forEach(e -> checkBrokenLink(e));
		long endTime = System.currentTimeMillis();

		System.out.println("Total time taken: " + (endTime - stTime) / 1000);

		driver.quit();

	}

	public static void checkBrokenLink(String linkUrl) {

		try {

			URL url = new URL(linkUrl);
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
			httpUrlConnection.setConnectTimeout(5000);
			httpUrlConnection.connect();

			if (httpUrlConnection.getResponseCode() >= 400) {

				System.out.println(linkUrl + " ---> " + httpUrlConnection.getResponseMessage() + " is a broken link ");

			} else {
				System.out.println(linkUrl + " ---> " + httpUrlConnection.getResponseMessage());
			}

		}

		catch (Exception e) {
			System.out.println(linkUrl + " ---> Error: " + e.getMessage());

		}

	}

}
