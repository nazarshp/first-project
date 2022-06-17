package ua.lviv.iot.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.project.logic.HiveService;
import ua.lviv.iot.project.model.Hive;

import java.io.FileNotFoundException;
import java.util.Collection;

@RestController
@RequestMapping("/hive")
public class HiveController {

    private final HiveService hiveService;

    @Autowired
    public HiveController(final HiveService hiveService) {
        this.hiveService = hiveService;
    }

    @PostMapping
    public final Collection<Hive> create(@RequestBody Collection<Hive> hives) throws Exception {
        return hiveService.create(hives, null, false);
    }

//    @GetMapping("/{id}")
//    public final Hive getById(@PathVariable final Integer id) throws Exception {
//        return HiveService.getById(id);
//    }
//
//    @GetMapping("/{street}")
//    public final Hive getByStreet(@PathVariable final String street) throws Exception {
//        return HiveService.getByStreet(street);
//    }
//
//    @GetMapping("/{temperature}")
//    public final Hive getByTemperature(@PathVariable final double temperature) throws Exception {
//        return HiveService.getByTemperature(temperature);
//    }
//
//    @GetMapping("/{humidity}")
//    public final Hive getByHumidity(@PathVariable final double humidity) throws Exception {
//        return HiveService.getByHumidity(humidity);
//    }

    @GetMapping
    public final Collection<Hive> getAll() throws FileNotFoundException {
        return hiveService.getAll();
    }

    @DeleteMapping("/{Id}")
    public final void deleteById(@PathVariable final Integer Id) throws Exception {
        hiveService.deleteById(Id);
    }

    @DeleteMapping("/{street}")
    public final void deleteByStreet(@PathVariable final String street) throws Exception {
        hiveService.deleteByStreet(street);
    }

    @DeleteMapping("/{temperature}")
    public final void deleteByTemperature(@PathVariable final double temperature) throws Exception {
        hiveService.deleteByTemperature(temperature);
    }

    @DeleteMapping("/{humidity}")
    public final void deleteByHumidity(@PathVariable final double humidity) throws Exception {
        hiveService.deleteByHumidity(humidity);
    }

    @PutMapping()
    public final void update(@RequestBody final Hive hive) throws Exception {
        hiveService.update(hive);
    }
}

