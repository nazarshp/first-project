package ua.lviv.iot.project.logic;

import ua.lviv.iot.project.model.Hive;

import java.io.FileNotFoundException;
import java.util.Collection;

public interface HiveService {
    Collection<Hive> create(Collection<Hive> hives, String fileName, boolean changeId) throws Exception;

    Hive update(Hive hive) throws Exception;

    Hive getById(Integer Id) throws Exception;

    Hive getByStreet(String street) throws Exception;

    Hive getByTemperature(double temperature) throws Exception;

    Hive getByHumidity(double humidity) throws Exception;


    Collection<Hive> getAll() throws FileNotFoundException;

    Hive deleteById(Integer id) throws Exception;


}
