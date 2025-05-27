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
import org.pages.OrderPage;

import static org.hamcrest.CoreMatchers.containsString;

@RunWith(Parameterized.class)
public class OrderPagesTest {
    private WebDriver webDriver;

    private final String browser;
    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final String date;
    private final String term;
    private final String color;
    private final String comment;
    private static final String expectedOrderSuccessText = "Заказ оформлен";

    // Конструктор для Parameterized
    public OrderPagesTest(String browser,
                          String name,
                          String surname,
                          String address,
                          String metro,
                          String phone,
                          String date,
                          String term,
                          String color,
                          String comment) {
        this.browser = browser;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.term = term;
        this.color = color;
        this.comment = comment;
    }

    //Параметры для запуска теста
    @Parameterized.Parameters(name = "[Browser={0}] Оформление заказа. Позитивный сценарий. Пользователь: {0} {1}")
    public static Object[][] setDataForOrder() {
        return new Object[][] {
                //{"chrome", "Кира", "Найтли", "Москва, ул. Садовая, д. 12", "Юго-западная", "89834567890", "01.01.2025", "четверо суток", "чёрный жемчуг", "Комментарий!!!"},
                //{"chrome","Джэк ", "Воробьев", "Москва, ул. Потерянная, д. 404", "Чистые пруды", "11111111111", "30.05.2025", "трое суток", "серая безысходность", "Привезите пожалуйста :)"},
                {"firefox", "Кира", "Найтли", "Москва, ул. Садовая, д. 12", "Юго-западная", "89834567890", "01.01.2025", "четверо суток", "чёрный жемчуг", "Комментарий!!!"},
                {"firefox","Джэк ", "Воробьев", "Москва, ул. Потерянная, д. 404", "Чистые пруды", "11111111111", "30.05.2025", "трое суток", "серая безысходность", "Привезите пожалуйста :)"},
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


    //Тест для проверки процесса оформления заказа после нажатия на кнопку "Заказать" в шапке
    @Test
    public void orderWithHeaderButtonWhenSuccess() {
        MainPage mainPage = new MainPage(this.webDriver);
        OrderPage orderPage = new OrderPage(this.webDriver);
        mainPage.clickOrderButtonHeader();
        makeOrder(orderPage);

        MatcherAssert.assertThat(
                "Не получилось создать заказ через кнопку",
                orderPage.getNewOrderSuccessMessage(),
                containsString(expectedOrderSuccessText)
        );
    }

    //Тест для проверки процесса оформления заказа после нажатия на кнопку "Заказать" в теле сайта
    @Test
    public void orderWithBodyButtonWhenSuccess() {
        MainPage mainPage = new MainPage(this.webDriver);
        OrderPage orderPage = new OrderPage(this.webDriver);
        mainPage.clickOnCookieAcceptButton();
        mainPage.clickOrderButtonBody();
        makeOrder(orderPage);

        MatcherAssert.assertThat(
                "Не получилось создать заказ через кнопку",
                orderPage.getNewOrderSuccessMessage(),
                containsString(expectedOrderSuccessText)
        );
    }

    //Метод, описывающий процедуру оформления заказ
    private void makeOrder(OrderPage orderPage) {
        orderPage.waitForLoadForm();

        orderPage.setName(this.name);
        orderPage.setSurname(this.surname);
        orderPage.setAddress(this.address);
        orderPage.selectMetroStation(this.metro);
        orderPage.setPhone(this.phone);

        orderPage.clickNextButton();

        orderPage.setDate(this.date);
        orderPage.setTerm(this.term);
        orderPage.setColor(this.color);
        orderPage.setComment(this.comment);

        orderPage.makeOrder();
    }

    @After
    public void tearDown() {
        this.webDriver.quit();
    }
}