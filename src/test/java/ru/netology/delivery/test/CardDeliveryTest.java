package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.conditions.Text;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private Faker faker;


    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
        faker = new Faker(new Locale("ru"));
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void ShouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var city = validUser.getCity();
        var name = validUser.getName();
        var phone = validUser.getPhone();

        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        //первичная отправка формы
        $("[data-test-id='city'] input").sendKeys(city);
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(firstMeetingDate);
        $("[data-test-id='name'] input").sendKeys(name);
        $("[data-test-id='phone'] input").sendKeys(phone);
        $("[data-test-id='agreement']").click();
        $(".button").shouldHave(Condition.exactText("Запланировать")).click();
        $$("div .notification__title").find(Text.text("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $$(".notification__content").find(Text.text("Встреча успешно запланирована на " + firstMeetingDate));

        //повторная оправка формы (данные не меняются)
        $(".button").shouldHave(Condition.exactText("Запланировать")).click();
        $$("div .notification__title").find(Text.text("Необходимо подтверждение")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $$(".notification__content").find(Text.text("У вас уже запланирована встреча на другую дату. Перепланировать?"));

        //перепланирование даты встречи (изменена только дата встречи)
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(secondMeetingDate);
        $$(".button").find(Text.text("Перепланировать")).click();
        $$("div .notification__title").find(Text.text("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $$(".notification__content").find(Text.text("Встреча успешно запланирована на " + secondMeetingDate));
    }
}
