package com.socksManagementSystem.service;

import com.socksManagementSystem.model.Sock;
import com.socksManagementSystem.repository.SockRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ExelService {
    private static SockRepository sockRepository;

    public List<Sock> importExelToDatabase(MultipartFile file) throws IOException {

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        List<Sock> socksFromExel = new ArrayList<>();

        for(int i = 1; i < sheet.getLastRowNum(); i++){
            Row row = sheet.getRow(i);

            Sock sock = Sock.builder()
                    .color(row.getCell(0).getStringCellValue())
                    .cottonPart((int) row.getCell(1).getNumericCellValue())
                    .quantity((int) row.getCell(2).getNumericCellValue())
                    .build();

            socksFromExel.add(sock);
            sockRepository.save(sock);
        }
        workbook.close();

        return socksFromExel;
    }
}
