import io.github.bonigarcia.wdm.WebDriverManager;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.pages.MainPage;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertTrue;


@RunWith(Parameterized.class)
public class PageSourceTest  extends BaseTest{
    @Parameterized.Parameter(0)
    public String browser;



    @Parameterized.Parameters(name = "Запуск в {0}")
    public static Object[] browsers() {
        return new Object[]{ "chrome", "firefox" };
    }



    //Тест для проверки открытия  ссылки в логотипе "Яндекс"
    @Test
    public void YandexHrefTest() {
        MainPage mainPage = new MainPage(driver);
        String expectedYandexUrl = "//yandex.ru";
        MatcherAssert.assertThat(
                "Yandex Logo Link не ведёт на " + expectedYandexUrl,
                mainPage.getYandexLogoLink(),
                containsString(expectedYandexUrl)
        );
    }

    @Test
    public void YandexOpensInNewTabTest() {
        MainPage mainPage = new MainPage(driver);
        assertTrue(
                "Yandex Logo Link не открывается в новом табе",
                mainPage.isYandexLogoLinkOpenedInNewTab()
        );
    }

    @Test
    public void ScooterHrefTest() {
        MainPage mainPage = new MainPage(driver);
        String expectedScooterUrl = "//qa-scooter.praktikum-services.ru";
        MatcherAssert.assertThat(
                "Scooter Logo Link не ведёт на " + expectedScooterUrl,
                mainPage.getScooterLogoLink(),
                containsString(expectedScooterUrl)
        );
    }
}
