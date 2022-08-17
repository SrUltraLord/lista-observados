package com.example.micro2.utils;

import com.example.micro2.dto.TransactionDTO;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class FileUtils {
  private final String fileName;
  private com.opencsv.CSVReader CSVReader;
  private com.opencsv.CSVWriter CSVWriter;
  private FileReader fileReader;
  private FileWriter fileWriter;
  private File file;

  public TransactionDTO readLine() {
    try {
      if (CSVReader == null) initReader();
      String[] line = CSVReader.readNext();

      if (line == null) return null;
      line = Arrays.stream(line).map(this::removeBOM).toArray(String[]::new);


      return TransactionDTO.builder()
          .accountOrigin(Integer.parseInt(line[0]))
          .accountDestination(Integer.parseInt(line[1]))
          .amount(BigDecimal.valueOf(Long.parseLong(line[2])))
          .build();
    } catch (Exception e) {
      log.error("Error while reading line in file: {}", this.fileName);
      return null;
    }
  }

  private String removeBOM(String line) {
    boolean hasBOM = line.startsWith("\uFEFF");
    return hasBOM ? line.substring(1) : line;
  }

  public void writeLine(TransactionDTO transaction) {
    try {
      if (CSVWriter == null) initWriter();
      String[] lineStr = new String[4];
      lineStr[0] = transaction.getAccountOrigin().toString();
      lineStr[1] = transaction.getAccountDestination().toString();
      lineStr[2] = transaction.getAmount().toString();
      lineStr[3] = transaction.getState();

      CSVWriter.writeNext(lineStr);
    } catch (Exception e) {
      log.error("Error while writing line in file: " + this.fileName);
    }
  }

  private void initReader() throws Exception {
    ClassLoader classLoader = this.getClass().getClassLoader();
    if (file == null)
      file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
    if (fileReader == null) fileReader = new FileReader(file);
    if (CSVReader == null) CSVReader = new CSVReader(fileReader);
  }

  private void initWriter() throws Exception {
    if (file == null) {
      file = new File(fileName);
      file.createNewFile();
    }
    if (fileWriter == null) fileWriter = new FileWriter(file, true);
    if (CSVWriter == null) CSVWriter = new CSVWriter(fileWriter);
  }

  public void closeWriter() {
    try {
      CSVWriter.close();
      fileWriter.close();
    } catch (IOException e) {
      log.error("Error while closing writer.");
    }
  }

  public void closeReader() {
    try {
      CSVReader.close();
      fileReader.close();
    } catch (IOException e) {
      log.error("Error while closing reader.");
    }
  }
}
