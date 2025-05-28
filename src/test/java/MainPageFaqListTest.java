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
public class MainPageFaqListTest extends BaseTest {

    @Parameterized.Parameter(0)
    public int numberOfElement;
    @Parameterized.Parameter(1)
    public String expectedHeaderText;
    @Parameterized.Parameter(2)
    public String expectedItemText;

    private static final Object[][] FAQ_DATA = {
            { 0, "Сколько это стоит? И как оплатить?",
                    "Сутки — 400 рублей. Оплата курьеру — наличными или картой." },
            { 1, "Хочу сразу несколько самокатов! Так можно?",
                    "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим." },
            { 2, "Как рассчитывается время аренды?",
                    "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30." },
            { 3, "Можно ли заказать самокат прямо на сегодня?",
                    "Только начиная с завтрашнего дня. Но скоро станем расторопнее." },
            { 4, "Можно ли продлить заказ или вернуть самокат раньше?",
                    "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010." },
            { 5, "Вы привозите зарядку вместе с самокатом?",
                    "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится." },
            { 6, "Можно ли отменить заказ?",
                    "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои." },
            { 7, "Я живу за МКАДом, привезёте?",
                    "Да, обязательно. Всем самокатов! И Москве, и Московской области." }
    };

    //Параметризация теста
    @Parameterized.Parameters(name = "{0} ▶ вопрос #{1}")
    public static Object[][] setTestData() {
        return FAQ_DATA;
    }

    //Тест для проверки работы аккордеона и для проверки текста в заголовках и в раскрывающимся блоке
    @Test
    public void FaqHeaderTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnCookieAcceptButton();
        mainPage.clickAccordionHeader(numberOfElement);
        mainPage.waitForLoadItem(numberOfElement, expectedItemText);

        String actualHeader = mainPage.getAccordionHeaderText(numberOfElement);
        MatcherAssert.assertThat(
                "Неверный заголовок FAQ #" + numberOfElement,
                actualHeader,
                equalTo(expectedHeaderText)
        );
    }

    @Test
    public void FaqContentTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnCookieAcceptButton();
        mainPage.clickAccordionHeader(numberOfElement);
        mainPage.waitForLoadItem(numberOfElement, expectedItemText);

        String actualContent = mainPage.getAccordionItemText(numberOfElement);
        MatcherAssert.assertThat(
                "Неверное содержимое FAQ #" + numberOfElement,
                actualContent,
                equalTo(expectedItemText)
        );
    }
}
