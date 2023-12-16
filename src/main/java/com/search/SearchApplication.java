package com.search;

import java.io.*;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.domain.SearchKeyword;
import com.search.domain.SearchUpdate;
import com.search.dto.JsonData;
import com.search.service.SearchKeywordService;
import com.search.service.SearchUpdateService;

import jep.Interpreter;
import jep.JepException;
import jep.SharedInterpreter;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
public class SearchApplication {

    private final SearchUpdateService searchUpdateService;
    private final SearchKeywordService searchKeywordService;

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }

    @Scheduled(fixedRate = 100000)
    public void initUpdate() throws JepException {
        this.update();
    }

    private void update() throws JepException {
        String filepath = System.getProperty("user.dir") + "/searchdata";
        File dataFolder = new File(filepath);

        if (!dataFolder.exists())
            dataFolder.mkdir();

        String title = "data";
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(filepath + "/" + title + ".csv", true));

            fw.write("id" + '\t' + "keyword");
            List<SearchKeyword> list = searchKeywordService.list();
            for (int i = 0; i < list.size(); i++) {
                fw.newLine();
                fw.write(list.get(i).getKeywordNo() + "\t" + list.get(i).getKeyword());
            }

            fw.flush();
            fw.close();


            try (Interpreter interp = new SharedInterpreter()) {
                File tempFile = File.createTempFile("tmp", ".py");
                tempFile.deleteOnExit();

                InputStream inputStream = new ClassPathResource("static/python/pre.py").getInputStream();
                FileUtils.copyInputStreamToFile(inputStream, tempFile);

                interp.runScript(tempFile.getPath());
                String res = "";
                res = interp.getValue("json_data").toString();
                ObjectMapper objectMapper = new ObjectMapper();
                List<JsonData> jsonDataList = null;
                try {
                    jsonDataList = objectMapper.readValue(res, new TypeReference<List<JsonData>>() {
                    });
                    searchKeywordService.register(jsonDataList);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    inputStream.close();
                }



            } catch (Exception e) {
                e.printStackTrace();
            }

            File file1 = new File(filepath + "/data.csv");
            long updateCnt = searchUpdateService.count();
            File file2 = new File(filepath + "/data" + updateCnt + ".csv");

            SearchUpdate searchUpdate = new SearchUpdate();
            searchUpdate.setCsvName("data" + updateCnt + ".csv");
            searchUpdateService.register(searchUpdate);

            if (file2.exists())
                throw new java.io.IOException("file exists");
            boolean success = file1.renameTo(file2);
            if (success) {
                System.out.println("File Rename successfuly");
            } else
                System.out.println("File is not Rename");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
