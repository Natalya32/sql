package ru.netology.sql.page;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import ru.netology.sql.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static ru.netology.sql.data.DataHelper.getRandomPassword;

public class LoginPage {
    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        $("[data-test-id=login] input").setValue(info.getLogin());
        $("[data-test-id=password] input").setValue(info.getPassword());
        $("[data-test-id=action-login]").click();
        return new VerificationPage();
    }

    public void invalidPasswordAssert(DataHelper.AuthInfo info) {
        $("[data-test-id=login] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=password] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=login] input").setValue(info.getLogin());
        $("[data-test-id=password] input").setValue(getRandomPassword());
        $("[data-test-id=action-login]").click();
    }

    public void tooManyAttemptsAssert(DataHelper.AuthInfo info) {
        for (int i = 0; i < 3; i++) {
            invalidPasswordAssert(info);
        }
        String actual = $("[data-test-id=error-notification] .notification__content").getText();
        Assertions.assertTrue(actual.contains("заблокирован"));
    }
}