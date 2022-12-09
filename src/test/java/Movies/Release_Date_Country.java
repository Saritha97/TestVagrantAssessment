package Movies;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Release_Date_Country {
	
	WebDriver driver;
	String movie_title = "Pushpa: The Rise";
	
	@BeforeMethod
	public void setUp()
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\chromedriver.exe");			
		driver = new ChromeDriver();
	}
	
	@Test
	public String[] getDetailsWiki() 
	{
		driver.get("https://en.wikipedia.org/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		WebElement search = driver.findElement(By.id("searchInput"));
		search.sendKeys(movie_title);
		driver.findElement(By.id("searchButton")).click();

		String release_date = driver.findElement(By.xpath("//tbody/tr[12]/td[1]/div[1]/ul[1]/li[1]")).getText();
		String country = driver.findElement(By.xpath("//td[normalize-space()='India']")).getText();
		
		return new String[] {release_date, country};
	}
	
	@Test
	public String[] getDetailsIMDB()
	{
		driver.get("https://www.imdb.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		WebElement search = driver.findElement(By.id("suggestion-search"));
		search.sendKeys(movie_title);
		driver.findElement(By.id("suggestion-search-button")).click();

		driver.findElement(By.xpath("//a[normalize-space()='Pushpa: The Rise - Part 1']")).click();
		String get_release_date = driver.findElement(By.xpath("//a[normalize-space()='December 17, 2021 (United States)']")).getText();
		String country = driver.findElement(By.xpath("//a[normalize-space()='India']")).getText();
		String[] release_date_imdb = get_release_date.split(" ");
		release_date_imdb[1] = release_date_imdb[1].replace(",", "");
		String release_date = release_date_imdb[1] + " " + release_date_imdb[0] + " " + release_date_imdb[2];
		
		return new String[] {release_date, country};
	}

	@Test
	public void validate() 
	{
		String[] details_imdb = getDetailsIMDB();
		String[] details_wiki = getDetailsWiki();

		boolean validate_release = details_imdb[0].equals(details_wiki[0]);
		boolean validate_country = details_imdb[1].equals(details_wiki[1]);

		if(validate_release)
		{
			System.out.println("Release Dates are the same");
		}
		else 
		{
			System.out.println("Release Dates are not the same");
		}
		
		if(validate_country)
		{
			System.out.println("Countries are the same");
		}
		else 
		{
			System.out.println("Countries are not the same");
		}
	}
	
	@AfterMethod
	public void tearDown()  
	{
		driver.quit();
	}
}
