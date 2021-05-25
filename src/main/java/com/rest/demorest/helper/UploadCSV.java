package com.rest.demorest.helper;

import com.rest.demorest.model.Employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;


public class UploadCSV {

    public static String TYPE = "text/csv";
    static String[] HEADERS = {"id", "name", "title", "anniversary"};

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }


    public static List<Employee> cvsItems(InputStream inputStream){
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Employee> employees = new ArrayList<Employee>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Employee employee = new Employee(
                        UUID.fromString(csvRecord.get("id")),
                        csvRecord.get("name"),
                        csvRecord.get("title"),
                        Integer.parseInt(csvRecord.get("anniversary"))
                );
                         System.out.println(csvRecord.get("name"));
                         employees.add(employee);
            }

            return employees;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }


}

