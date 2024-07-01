package com.hzw.service;

import com.hzw.entity.AdmissionCard;
import com.hzw.entity.Person;
import com.itextpdf.text.DocumentException;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


public interface PdfCustomService {


    void generatorAdmissionCard(AdmissionCard admissionCard, HttpServletResponse response) throws IOException;

}
