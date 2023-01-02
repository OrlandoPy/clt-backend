package com.cltbackend.service;

import com.cltbackend.dto.ResponseDTO;
import com.cltbackend.util.JasperReportManager;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService{

    private final JasperReportManager reportManager;
    private final  DataSource dataSource;

    public ResponseEntity<?> historialDePagosPorFecha(String fechaDesde, String fechaHasta) {
        try{
            String fileName = "historial_pago_fecha";
            Map<String, Object> params = new HashMap<>();
            params.put("fechaDesde", fechaDesde);
            params.put("fechaHasta", fechaHasta);
            ByteArrayOutputStream stream = reportManager.export(fileName, params, dataSource.getConnection());
            byte[] bs = stream.toByteArray();
            InputStreamResource streamResource = new InputStreamResource(new ByteArrayInputStream(bs));

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_PDF).contentLength(bs.length).body(streamResource);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body( new ResponseDTO(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo generar el reporte debido a un error inesperado", null));
        }
    }

}
