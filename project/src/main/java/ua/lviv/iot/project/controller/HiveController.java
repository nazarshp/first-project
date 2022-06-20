package ua.lviv.iot.project.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import ua.lviv.iot.project.logic.HiveService;
import ua.lviv.iot.project.model.Hive;

import java.io.FileNotFoundException;

import java.util.*;


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

    @GetMapping("/{id}")
    public final Hive getById(@PathVariable final Integer id) throws Exception {
        return hiveService.getById(id);
    }

    @GetMapping("/{street}")
    public final Hive getByStreet(@PathVariable final String street) throws Exception {
        return hiveService.getByStreet(street);
    }

    @GetMapping("/{temperature}")
    public final Hive getByTemperature(@PathVariable final double temperature) throws Exception {
        return hiveService.getByTemperature(temperature);
    }

    @GetMapping("/{humidity}")
    public final Hive getByHumidity(@PathVariable final double humidity) throws Exception {
        return hiveService.getByHumidity(humidity);
    }

    @GetMapping
    public final Collection<Hive> getAll() throws FileNotFoundException {
        return hiveService.getAll();
    }

    @DeleteMapping("/Id")
    public final void deleteById(@PathVariable final Integer Id) throws Exception {
        hiveService.deleteById(Id);
    }

    @PutMapping()
    public final void update(@RequestBody final Hive hive) throws Exception {
        hiveService.update(hive);
    }
}

