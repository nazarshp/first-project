package ua.lviv.iot.project.logic;

import org.springframework.stereotype.Service;
import ua.lviv.iot.project.model.Hive;

import java.io.FileNotFoundException;
import java.util.Collection;

@Service

public class HiveServiceImpl implements HiveService {


    @Override
    public Collection<Hive> create(Collection<Hive> hives, String fileName, boolean changeId) throws Exception {
        return null;
    }

    @Override
    public Hive update(Hive hive) throws Exception {
        return null;
    }

//    @Override
//    public Hive getById(Integer id) throws Exception {
//        return null;
//    }
//
//    @Override
//    public Hive getByStreet(String street) throws Exception {
//        return null;
//    }
//
//    @Override
//    public Hive getByTemperature(double temperature) throws Exception {
//        return null;
//    }
//
//    @Override
//    public Hive getByHumidity(double humidity) throws Exception {
//        return null;
//    }

    @Override
    public Collection<Hive> getAll() throws FileNotFoundException {
        return null;
    }

    @Override
    public Hive deleteById(Integer id) throws Exception {
        return null;
    }

    @Override
    public Hive deleteByStreet(String street) throws Exception {
        return null;
    }

    @Override
    public Hive deleteByTemperature(double temperature) throws Exception {
        return null;
    }

    @Override
    public Hive deleteByHumidity(double temperature) throws Exception {
        return null;
    }
}
