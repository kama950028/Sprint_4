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

import static org.hamcrest.CoreMatchers.equalTo;


@RunWith(Parameterized.class)
public class MainPageFaqListTest {

    private WebDriver webDriver;

    @Parameterized.Parameter(0)
    public String browser;
    @Parameterized.Parameter(1)
    public int numberOfElement;
    @Parameterized.Parameter(2)
    public String expectedHeaderText;
    @Parameterized.Parameter(3)
    public String expectedItemText;

    //Параметризация теста
    @Parameterized.Parameters(name = "{0} ▶ вопрос #{1}")
    public static Object[][] setTestData() {
        return new Object[][] {
                {"chrome",
                        0,
                        "Сколько это стоит? И как оплатить?",
                        "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {"chrome",
                        1,
                        "Хочу сразу несколько самокатов! Так можно?",
                        "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {"chrome",
                        2,
                        "Как рассчитывается время аренды?",
                        "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {"chrome",
                        3,
                        "Можно ли заказать самокат прямо на сегодня?",
                        "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {"chrome",
                        4,
                        "Можно ли продлить заказ или вернуть самокат раньше?",
                        "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {"chrome",
                        5,
                        "Вы привозите зарядку вместе с самокатом?",
                        "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {"chrome",
                        6,
                        "Можно ли отменить заказ?",
                        "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {"chrome",
                        7,
                        "Я живу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области." },

                { "firefox", 0,
                        "Сколько это стоит? И как оплатить?",
                        "Сутки — 400 рублей. Оплата курьеру — наличными или картой."
                },
                { "firefox", 1,
                        "Хочу сразу несколько самокатов! Так можно?",
                        "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."
                },
                { "firefox", 2,
                        "Как рассчитывается время аренды?",
                        "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."
                },
                { "firefox", 3,
                        "Можно ли заказать самокат прямо на сегодня?",
                        "Только начиная с завтрашнего дня. Но скоро станем расторопнее."
                },
                { "firefox", 4,
                        "Можно ли продлить заказ или вернуть самокат раньше?",
                        "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."
                },
                { "firefox", 5,
                        "Вы привозите зарядку вместе с самокатом?",
                        "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."
                },
                { "firefox", 6,
                        "Можно ли отменить заказ?",
                        "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."
                },
                { "firefox", 7,
                        "Я живу за МКАДом, привезёте?",
                        "Да, обязательно. Всем самокатов! И Москве, и Московской области."
                }
        };
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

    //Тест для проверки работы аккордеона и для проверки текста в заголовках и в раскрывающимся блоке
    @Test
    public void checkAccordionIsCorrect() {
        MainPage mainPage = new MainPage(webDriver);

        mainPage.clickOnCookieAcceptButton();
        mainPage.clickAccordionHeader(numberOfElement);

        // вот здесь передаём ожидаемый текст в метод ожидания
        mainPage.waitForLoadItem(numberOfElement, expectedItemText);

        // теперь делаем ассерции по простой схеме actual→expected
        String actualHeader = mainPage.getAccordionHeaderText(numberOfElement);
        MatcherAssert.assertThat(
                "Проблема текста в заголовке #" + numberOfElement,
                actualHeader,
                equalTo(expectedHeaderText)
        );

        String actualItem = mainPage.getAccordionItemText(numberOfElement);
        MatcherAssert.assertThat(
                "Проблема текста в содержимом #" + numberOfElement,
                actualItem,
                equalTo(expectedItemText)
        );
    }


    @After
    public void tearDown() {
        this.webDriver.quit();
    }
}
