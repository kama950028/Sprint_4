package org.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class OrderPage {

    private final WebDriver webDriver;
    private final WebDriverWait wait;


    public OrderPage(WebDriver driver) {
        this.webDriver = driver;
        this.wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
    }

    // Форма заказа
    @FindBy(xpath = ".//div[starts-with(@class, 'Order_Form')]")
    private WebElement orderForm;

    // Поле для ввода имени
    @FindBy(xpath = ".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Имя')]")
    private WebElement nameInput;

    // Поле для ввода фамилии
    @FindBy(xpath = ".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Фамилия')]")
    private WebElement surnameInput;

    // Поле для ввода адреса
    @FindBy(xpath = ".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Адрес')]")
    private WebElement addressInput;

    // Поле для ввода метро
    @FindBy(xpath = ".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Станция метро')]")
    private WebElement inputMetro;

    // Поле для ввода телефона
    @FindBy(xpath = ".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Телефон')]")
    private WebElement phoneInput;

    // Поле для ввода комментария
    @FindBy(xpath = ".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Комментарий')]")
    private WebElement commentInput;

    // Обёртка для списка доступных станций метро
    @FindBy(className = "select-search__select")
    private WebElement metroList;

    // Список доступных для выбора станций метро
    @FindBy(xpath = ".//div[@class='select-search__select']//div[starts-with(@class,'Order_Text')]")
    private List<WebElement> metroListItems;

    // Кнопка "Далее"
    @FindBy(xpath = ".//div[starts-with(@class, 'Order_NextButton')]/button")
    private WebElement nextButton;

    // Выбранная в календарике дата
    @FindBy(className = "react-datepicker__day--selected")
    private WebElement dateSelected;

    // Поле для ввода даты
    @FindBy(xpath = ".//div[starts-with(@class, 'react-datepicker__input-container')]//input")
    private WebElement dateInput;

    // Обёртка для выпадающего списка по срочности аренды
    @FindBy(className = "Dropdown-root")
    private WebElement termDropdownRoot;

    // Список доступных для выбора срочностей аренды
    @FindBy(className = "Dropdown-option")
    private List<WebElement> termDropdownOption;

    // Список доступных для выбора цветов
    @FindBy(xpath = ".//div[starts-with(@class, 'Order_Checkboxes')]//label")
    private List<WebElement> colorLabels;

    // Кнопка "Заказать"
    @FindBy(xpath = ".//div[starts-with(@class, 'Order_Buttons')]/button[not(contains(@class,'Button_Inverted'))]")
    private WebElement orderButton;

    // Кнопка "Да" в окне подтверждения заказа
    @FindBy(xpath = ".//div[starts-with(@class, 'Order_Modal')]//button[not(contains(@class,'Button_Inverted'))]")
    private WebElement acceptOrderButton;

    // Текст об успешном оформлении заказа во всплывающем окне
    @FindBy(xpath = ".//div[starts-with(@class, 'Order_Modal')]//div[starts-with(@class,'Order_ModalHeader')]")
    private WebElement newOrderSuccessMessage;

    /**
     * Метод для ожидания загрузки формы заказа
     */
    public void waitForLoadForm() {
        new WebDriverWait(this.webDriver, 3)
                .until(ExpectedConditions.visibilityOf(orderForm));
    }

    //Метод для ожидания загрузки элемента страницы
    private void waitForElementLoad(By elementLoad) {
        new WebDriverWait(this.webDriver, 3)
                .until(ExpectedConditions.visibilityOf(this.webDriver.findElement(elementLoad)));

    }

    //Метод для установки значения в поле "Имя"
    public void setName(String name) {
        nameInput.sendKeys(name);
    }

    //Метод для установки значения в поле "Фамилия"
    public void setSurname(String surname) {
        surnameInput.sendKeys(surname);
    }

    //Метод для установки значения в поле "Адрес"
    public void setAddress(String address) {
        addressInput.sendKeys(address);
    }

    //Метод для установки значения в поле "Станция метро"
    public void selectMetroStation(String metro) {
        inputMetro.click();
        inputMetro.clear();
        inputMetro.sendKeys(metro);

        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        // Ждём, пока введённый текст отобразится в атрибуте value
        wait.until(ExpectedConditions.attributeToBeNotEmpty(inputMetro, "value"));

        // Выбираем первый вариант автокомплита
        inputMetro.sendKeys(Keys.ARROW_DOWN);
        inputMetro.sendKeys(Keys.ENTER);
    }

    //Метод для установки значения в поле "Телефон"
    public void setPhone(String phone) {
        phoneInput.sendKeys(phone);
    }

    //Метод для нажатия на кнопку "Далее" для продолжения оформления заказа
    public void clickNextButton() {
        nextButton.click();
    }

    //Метод для установки значения в поле "Дата"
    public void setDate(String date) {
        dateInput.sendKeys(date);
        wait.until(ExpectedConditions.elementToBeClickable(dateSelected));
        dateSelected.click();
    }

    //Метод для установки значения в поле "Срочность аренды"
    public void setTerm(String termToChoose) {
        termDropdownRoot.click();
        for (WebElement option : termDropdownOption) {
            if (option.getText().equals(termToChoose)) {
                option.click();
                break;
            }
        }
    }

    //Метод для установки значения в поле "Цвет"
    public void setColor(String colorToChoose) {
        for (WebElement label : colorLabels) {
            if (label.getText().equals(colorToChoose)) {
                label.click();
                break;
            }
        }
    }

    //Метод для установки значения в поле "Комментарии"
    public void setComment(String comment) {
        commentInput.sendKeys(comment);
    }

    //Метод для оформления заказ
    public void makeOrder() {
        orderButton.click();
        wait.until(ExpectedConditions.visibilityOf(acceptOrderButton));
        acceptOrderButton.click();
    }

    //Метод для получения сообщения об успешном оформлении заказа
    public String getNewOrderSuccessMessage() {
        return newOrderSuccessMessage.getText();
    }

    //Метод для нажатия на кнопку "Заказать"
    private void clickOrderButton() {
        orderButton.click();
    }

    //Метод для нажатия на кнопку подтверждения заказа
    private void clickAcceptOrderButton() {
        acceptOrderButton.click();
    }

    //Методя для выбора элемента выпадающего списка
    private void chooseElementFromDropdown(By dropdownElements, String elementToChoose) {
        List<WebElement> elementsFiltered = this.webDriver.findElements(dropdownElements);
        for (WebElement element : elementsFiltered) {
            if (element.getText().equals(elementToChoose)) {
                element.click();
                break;
            }
        }
    }

    //Метод для нажатия на выбранную дату в календаре
    private void clickDateSelected() {
        dateSelected.click();
    }

    //Метод для ракрытия списка аренды
    private void clickTermDropdown() {
        termDropdownRoot.click();
    }
}
