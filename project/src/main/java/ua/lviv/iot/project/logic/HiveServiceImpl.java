package ua.lviv.iot.project.logic;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ua.lviv.iot.project.model.Hive;

import javax.management.OperationsException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service

public class HiveServiceImpl implements HiveService {

    @Override
    public final Collection<Hive> create(final Collection<Hive> hives, String fileName, final boolean updateId) throws Exception {
        Assert.notNull(hives, "Hive cannot be null");

        if (!updateId) {
            setCorrectId(hives);
        }

        int firstEntityId = hives.stream().findFirst().get().getId();

        if (fileName == null) {
            fileName = "Hive-" + LocalDate.now() + ".csv";
        }

        if (existFileByName(fileName)) {
            throw new Exception("Exist file with name" + fileName + " try update your file");
        }

        String writerResPath = String.format("%s%s%s", "C:\\nazar\\project\\src\\main\\resources\\templates", File.separator, fileName);

        try (FileWriter writer = new FileWriter(writerResPath)) {
            String lastClassName = "";
            for (var hive : hives) {
                if (!hive.getClass().getSimpleName().equals(lastClassName)) {
                    if (hive.getId() == firstEntityId) {
                        writer.write(hive.obtainHeaders());
                    }

                    writer.write("\n");
                    lastClassName = hives.getClass().getSimpleName();
                }
                writer.write(hive.toCSV());
                writer.write("\n");
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return hives;
    }


    @Override
    public final Hive update(Hive actualHive) throws Exception {
        Assert.notNull(actualHive, "Hive cannot be null");
        Assert.notNull(actualHive.getId(), "Hive ID cannot be null");

        int hiveId = actualHive.getId();

        File file;
        Hive hiveFromFiles = new Hive();

        file = getFileWithGivenId(hiveId);

        Collection<Hive> hives = getRecordsFromFile(file.getAbsolutePath());

        Hive hivesFromFiles = getById(hiveId);

        hives.remove(hiveFromFiles);


        hives.add(actualHive);


        if (file.delete()) {
            create(hives, file.getName(), true);
        } else {
            throw new OperationsException("Cannot delete file:" + file.getName());
        }


        return actualHive;
    }

    @Override
    public final Hive getById(final Integer id) throws Exception {

        Optional<Hive> Hive;

        if (getAllRecords() != null) {

            Hive = getAllRecords().stream().filter(hive -> hive.getId() == id).findFirst();
        } else {
            throw new Exception("Not found hive with id:" + id);
        }

        return Hive.get();
    }

    @Override
    public final Hive getByStreet(final String street) throws Exception {

        Optional<Hive> Hive;

        if (getAllRecords() != null) {

            Hive = getAllRecords().stream().filter(hive -> hive.getStreet() == street).findFirst();
        } else {
            throw new Exception("Not found hive with street:" + street);
        }

        return Hive.get();
    }

    @Override
    public final Hive getByTemperature(final double temperature) throws Exception {

        Optional<Hive> Hive;

        if (getAllRecords() != null) {

            Hive = getAllRecords().stream().filter(hive -> hive.getTemperature() == temperature).findFirst();
        } else {
            throw new Exception("Not found hive with temperature:" + temperature);
        }

        return Hive.get();
    }

    @Override
    public final Hive getByHumidity(final double humidity) throws Exception {

        Optional<Hive> Hive;

        if (getAllRecords() != null) {

            Hive = getAllRecords().stream().filter(hive -> hive.getHumidity() == humidity).findFirst();
        } else {
            throw new Exception("Not found hive with humidity:" + humidity);
        }

        return Hive.get();
    }


    @Override
    public final Collection<Hive> getAll() throws FileNotFoundException {

        return getAllRecords();
    }

    @Override
    public final Hive deleteById(final Integer id) throws Exception {

        File fileToChange = getFileWithGivenId(id);
        Collection<Hive> Hive = getRecordsFromFile(fileToChange.getAbsolutePath());
        String fileName = fileToChange.getName();
        Hive hiveForDelete = getById(id);
        Hive.remove(hiveForDelete);
        fileToChange.delete();
        create(Hive, fileName, true);

        return hiveForDelete;
    }


    public final Integer getLastId() throws FileNotFoundException {
        OptionalInt biggestId;
        if (getAllRecords() != null) {
            biggestId = getAllRecords().stream().mapToInt(Hive::getId).max();
        } else {
            return 0;
        }
        return biggestId.getAsInt();
    }


    private Collection<Hive> getAllRecords() throws FileNotFoundException {

        List<File> files = getAllFiles();
        if (files == null) {
            return null;
        }
        Collection<Collection<Hive>> entities = new HashSet<>();

        for (File file : files) {
            entities.add(getRecordsFromFile(file.getAbsolutePath()));
        }

        if (entities.isEmpty()) {
            return null;
        }
        return entities.stream().flatMap(Collection::stream).collect(Collectors.toCollection(HashSet::new));
    }

    private Collection<Hive> getRecordsFromFile(String fileAbsolutePath) {
        List<List<String>> lines = new ArrayList<>();

        try (Scanner scanner = new Scanner((new File(fileAbsolutePath)))) {
            while (scanner.hasNext()) {
                lines.add(getRecordFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Collection<Hive> entities = new HashSet<>();

        for (List<String> line : lines) {
            if (!line.isEmpty() && !line.get(0).replaceAll("\\D", "").isEmpty()) {
                entities.add(fromSCVToEntity(line));
            }
        }
        return entities;
    }

    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(", ");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    private Hive fromSCVToEntity(List<String> fields) {
        Hive hive = new Hive();

        hive.setId(Integer.parseInt(fields.get(0)));
        return hive;
    }

    private void setCorrectId(Collection<Hive> hives) throws FileNotFoundException {
        int lastId = getLastId();

        for (Hive hive : hives) {
            lastId++;
            hive.setId(lastId);
        }

    }

    private boolean existFileByName(String name) {

        List<File> existFiles = getAllFiles();

        if (existFiles != null) {
            for (File file : existFiles) {
                if (file.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<File> getAllFiles() {

        List<File> files = Arrays.asList(Objects.requireNonNull(new File("C:\\nazar\\project\\src\\main\\resources\\templates").listFiles()));
        if (files.isEmpty()) {
            return null;
        } else {
            return files;
        }
    }

    private File getFileWithGivenId(Integer id) throws Exception {
        List<File> allFiles = getAllFiles();
        Assert.notNull(allFiles, "There are no files with Hive");
        if (!allFiles.isEmpty()) {
            for (File file : allFiles) {
                Collection<Hive> hives = getRecordsFromFile(file.getAbsolutePath());
                if (hives.stream().anyMatch(Hive -> Hive.getId() == (id))) {
                    return file;
                }
            }
        }
        return null;
    }
}
