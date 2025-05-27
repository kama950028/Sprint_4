import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.pages.MainPage;

import static org.junit.Assert.assertTrue;


@RunWith(Parameterized.class)
public class PageSourceTest {
    @Parameterized.Parameter(0)
    public String browser;

    private WebDriver webDriver;


    @Parameterized.Parameters(name = "Запуск в {0}")
    public static Object[] browsers() {
        return new Object[]{ "chrome", "firefox" };
    }

    @Before
    public void startUp() {
        if ("firefox".equalsIgnoreCase(browser)) {
            WebDriverManager.firefoxdriver().setup();
            webDriver = new FirefoxDriver();
        } else {
            WebDriverManager.chromedriver().setup();
            webDriver = new ChromeDriver();
        }
        webDriver.get("https://qa-scooter.praktikum-services.ru");
    }

    //Тест для проверки открытия  ссылки в логотипе "Яндекс"
    @Test
    public void checkYandexLinkIsCorrect() {
        MainPage mainPage = new MainPage(this.webDriver);

        //URL ожидаемый
        String yandexUrl = "//yandex.ru";
        assertTrue(
                "Yandex Logo Link doesn't go to " + yandexUrl,
                mainPage.getYandexLogoLink().contains(yandexUrl)
        );

        assertTrue(
                "Yandex Logo Link doesn't open in new tab",
                mainPage.isYandexLogoLinkOpenedInNewTab()
        );
    }

    //Тест для проверки лого Самоката
    @Test
    public void checkScooterLinkIsCorrect() {
        MainPage mainPage = new MainPage(this.webDriver);

        //URL ожидаемый для лого самоката
        String scooterUrl = "//qa-scooter.praktikum-services.ru";
        assertTrue(
                "Scooter Logo Link doesn't go to " + scooterUrl,
                mainPage.getScooterLogoLink().contains(scooterUrl)
        );
    }

    @After
    public void tearDown() {
        this.webDriver.quit();
    }
}
