package org.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

//Описание org.pages.MainPage

public class MainPage {

    private final WebDriver driver;
    private final WebDriverWait wait;


    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
    }

    // Список элементов (локаторы):
    // 1. Заголовок страницы
    @FindBy(css = "div.Header_Header__logo")
    private WebElement pageTitle;

    // 2. Кнопка «Заказать» в шапке
    @FindBy(xpath = "//button[text()='Заказать'][@class='Button_Button__ra12g']")
    private WebElement orderButtonTop;

    // 3. Кнопка «Заказать» внизу
    @FindBy(xpath = "(//button[text()='Заказать'])[2]")
    private WebElement orderButtonBottom;

    // 4. Заголовок для раскрывающегося блока
    @FindBy(className = "accordion__heading")
    private List<WebElement> accordionHeaders;

    // 5. Абзац в раскрывающемся блоке
    @FindBy(xpath = ".//div[@class='accordion__panel']/p")
    private List<WebElement> accordionItems;

    // 6. Кнопка "Принять куки"
    @FindBy(id = "rcc-confirm-button")
    private WebElement cookieAcceptButton;

    // 7. Отдельная панель
    @FindBy(css = "div.accordion__panel")
    private List<WebElement> accordionPanels;

    // 8. Ссылка-лого
    @FindBy(xpath = ".//a[starts-with(@class,'Header_LogoYandex')]")
    private WebElement yandexLogoLink;

    // 9. Линк скутера
    @FindBy(xpath = ".//a[starts-with(@class,'Header_LogoScooter')]")
    private WebElement scooterLogoLink;

    //Метод для ожидания загрузки элемента списка
    public void waitForLoadItem(int index, String expectedText) {
        WebElement item = accordionItems.get(index);
        // 1) Прокручиваем его в зону видимости
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", item);
        // 2) Ждём, пока элемент вообще появится и станет виден
        wait.until(ExpectedConditions.visibilityOf(item));
        // 3) Ждём, пока в него запишется именно тот текст
        wait.until(ExpectedConditions.textToBePresentInElement(item, expectedText));
    }

    //Метод для получения текста на заголовке блока в списка
    public String getAccordionHeaderText(int index) {
        return accordionHeaders.get(index).getText();
    }

    //Методя для получения текста из раскрывающегося блока в списке
    public String getAccordionItemText(int index) {
        return accordionItems.get(index).getText();
    }

    //Метод для нажатия на заголовок блока в списке
    public void clickAccordionHeader(int index) {
        WebElement header = accordionHeaders.get(index);

        // Прокрутить в центр экрана
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", header);

        // Скрыть мешающие элементы (картинку самоката, любые оверлеи)
        ((JavascriptExecutor) driver)
                .executeScript(
                        "document"
                                + ".querySelectorAll(\"img[src='/assets/scooter.png'], .overlay-selector\")"
                                + ".forEach(el=>el.style.display='none');"
                );

        // Дождаться, что заголовок станет кликабельным
        wait.until(ExpectedConditions.elementToBeClickable(header));

        // Click через Actions
        new Actions(driver)
                .moveToElement(header)
                .click()
                .perform();

        // На всякий случай — ещё JS-клик
        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].dispatchEvent(new MouseEvent('click', {bubbles:true,cancelable:true}));",
                        header
                );
    }



    public void waitForPanelOpen(int index) {
        WebElement panel = accordionPanels.get(index);
        wait.until(driver -> {
            String style = panel.getAttribute("style");
            return style != null && !style.contains("max-height: 0px");
        });
    }


    //Метод для проверки раскрытия блока списка
    public boolean isAccordionItemDisplayed(int index) {
        return accordionItems.get(index).isDisplayed();
    }

    //Метод для нажатия на кнопку оформления заказа в шапке
    public void clickOrderButtonHeader() {
        orderButtonTop.click();
    }

    //Метод для нажатия на кнопку оформления заказа в теле сайта
    public void clickOrderButtonBody() {
        orderButtonBottom.click();
    }
    //Метод для нажатия на кнопку "Принять куки"
    public void clickOnCookieAcceptButton() {
        cookieAcceptButton.click();
    }
    public String getYandexLogoLink() {
        return yandexLogoLink.getAttribute("href");
    }

    public String getScooterLogoLink() {
        return scooterLogoLink.getAttribute("href");
    }

    public boolean isYandexLogoLinkOpenedInNewTab() {
        return "_blank".equals(yandexLogoLink.getAttribute("target"));
    }



}
