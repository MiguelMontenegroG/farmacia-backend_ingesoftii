package com.farmacia.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface ImagenesService {

    String subirImagen(MultipartFile multipartFile) throws IOException;

    void eliminarImagen(String publicId) throws IOException;
}