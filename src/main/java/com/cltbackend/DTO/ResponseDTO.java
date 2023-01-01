package com.cltbackend.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseDTO {

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private Date timestamp;
  private Integer code;
  private String message;
  private Object data;
  private List<Object> listData;
  private String alertType;
  private boolean bandera;

  public ResponseDTO() {
  }

  public ResponseDTO(Date timestamp, Integer code, String message, Object data) {
    this.timestamp = timestamp;
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public ResponseDTO(Date timestamp, Integer code, String message, Object data, String alertType) {
    this.timestamp = timestamp;
    this.code = code;
    this.message = message;
    this.data = data;
    this.alertType = alertType;
  }

  public ResponseDTO(String message, HttpStatus httpStatus) {
    this.code = httpStatus.value();
    this.message = message;
  }

  public ResponseDTO(Object data, HttpStatus httpStatus) {
    this.data = data;
    this.code = httpStatus.value();
  }

  public ResponseDTO(String message, String alertType, HttpStatus httpStatus) {
    this.message = message;
    this.alertType = alertType;
    this.code = httpStatus.value();
  }

  public ResponseDTO(String message, String alertType, HttpStatus httpStatus, boolean bandera) {
    this.message = message;
    this.alertType = alertType;
    this.code = httpStatus.value();
    this.bandera = bandera;
  }

  public ResponseDTO(String message, Object data, HttpStatus httpStatus) {
    this.message = message;
    this.data = data;
    this.code = httpStatus.value();
  }

  public ResponseDTO(String message, Object data, String alertType, HttpStatus httpStatus) {
    this.message = message;
    this.data = data;
    this.alertType = alertType;
    this.code = httpStatus.value();
  }

  public ResponseDTO(String message, Object data, String alertType, HttpStatus httpStatus, boolean bandera) {
    this.message = message;
    this.data = data;
    this.alertType = alertType;
    this.code = httpStatus.value();
    this.bandera = bandera;
  }

  public ResponseDTO(String message, Object data, List<Object> listdata, String alertType, HttpStatus httpStatus) {
    this.message = message;
    this.data = data;
    this.listData = listdata;
    this.alertType = alertType;
    this.code = httpStatus.value();
  }

  public ResponseDTO(String message, Object data, List<Object> listdata, String alertType, Integer code,
                     boolean bandera) {
    this.message = message;
    this.data = data;
    this.listData = listdata;
    this.alertType = alertType;
    this.code = code;
    this.bandera = bandera;
  }

  public ResponseDTO(List<Object> listdata, HttpStatus httpStatus) {
    this.listData = listdata;
    this.code = httpStatus.value();
  }

  public ResponseDTO(Date date, int i, String s, Object save) {
    this.timestamp = date;
    this.code = i;
    this.message = s;
    this.data = save;
  }

  public HttpStatus getStatus() {
    HttpStatus status = null;
    switch (code) {
      case 200:
        status = HttpStatus.OK;
        break;
      case 400:
        status = HttpStatus.BAD_REQUEST;
        break;
      case 401:
        status = HttpStatus.UNAUTHORIZED;
        break;
      case 403:
        status = HttpStatus.FORBIDDEN;
        break;
      case 500:
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        break;
    }
    return status;
  }

  public ResponseEntity<ResponseDTO> build() {
    ResponseDTO dto = new ResponseDTO(this.timestamp, this.code, this.message, this.data);
    return new ResponseEntity<>(dto, getStatus());
  }

  public ResponseEntity<ResponseDTO> construirLogin() {
    ResponseDTO dto = new ResponseDTO(this.timestamp, this.code, this.message, this.data, this.alertType);
    return new ResponseEntity<>(dto, getStatus());
  }

  public ResponseEntity<ResponseDTO> buildFlujoExpediente() {
    ResponseDTO dto = new ResponseDTO(this.message, this.data, this.listData, this.alertType, this.code,
        this.bandera);
    return new ResponseEntity<>(dto, getStatus());
  }

}