package ua.lviv.iot.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;


@Data
@AllArgsConstructor
@NoArgsConstructor
@With

public class Hive {

    private int id;
    private int year;
    private int date;
    private double temperature;
    private double humidity;
    private String street;


    public static String obtainHeaders() {

        return "ID, Year, Street, Date, Humidity, Temperature";
    }

    public String toCSV() {
        return String.format("%s; %s; %s; %s; %s; %s", getId(), getYear(), getStreet(),
                getDate(), getHumidity(), getTemperature());

    }
}
