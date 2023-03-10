package com.cltbackend.util;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@Component
public class JasperReportManager {

    private static final String REPORT_FOLDER = "reports";

    private static final String JASPER = ".jasper";

    public ByteArrayOutputStream export(String fileName, Map<String, Object> params,
                                        Connection con) throws IOException, JRException, SQLException {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ClassPathResource resource = new ClassPathResource(REPORT_FOLDER + File.separator + fileName + JASPER);
        InputStream inputStream = resource.getInputStream();
        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, con);
        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
        con.close();

        return stream;
    }
}
