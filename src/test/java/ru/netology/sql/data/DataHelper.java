package ru.netology.sql.data;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.experimental.UtilityClass;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper {

    private static final Faker faker = new Faker();

    private DataHelper() {
    }

    public static class AuthInfo {
        private final String login;
        private final String password;

        public AuthInfo(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static String getRandomPassword() {
        return faker.internet().password();
    }

    /*@SneakyThrows*/
    public static String getAuthCode() {
        var runner = new QueryRunner();
        var select = "select code FROM users JOIN auth_codes on users.id = user_id WHERE login = '" + getAuthInfo().getLogin() + "';";

        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            return runner.query(conn, select, new ScalarHandler<>());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

   /* @SneakyThrows*/
    public static void deleteOldData() {
        var runner = new QueryRunner();
        var deleteCodes = "DELETE from auth_codes;";
        var deleteTransactions = "DELETE from card_transactions;";
        var deleteCards = "DELETE from cards;";
        var deleteUsers = "DELETE from users;";

        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            runner.update(conn, deleteCards);
            runner.update(conn, deleteCodes);
            runner.update(conn, deleteTransactions);
            runner.update(conn, deleteUsers);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}