package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {

    private DataGenerator() {
    }

    public static String generateDate(int daysToAdd) {
        Faker faker = new Faker(new Locale("ru"));
        return (LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            Faker faker = new Faker(new Locale(locale));
            // выборка города из массива
            Random random = new Random();
            String[] city = {
                    "Абакан", "Архангельск", "Благовещенск", "Владивосток",
                    "Владикавказ", "Горно-Алтайск", "Красноярск", "Майкоп",
                    "Махачкала", "Мурманск", "Нальчик", "Петрозаводск",
                    "Петропавловск-Камчатский", "Санкт-Петербург", "Саранск", "Хабаровск",
                    "Ханты-Мансийск", "Южно-Сахалинск"
            };
            // генерирование пользователя (город, имя, тлф)
            return new UserInfo(
                    city[random.nextInt(city.length)],
                    faker.name().fullName(),
                    faker.phoneNumber().phoneNumber()
            );
        }

        @Value
        public static class UserInfo {
            String city;
            String name;
            String phone;
        }
    }
}

