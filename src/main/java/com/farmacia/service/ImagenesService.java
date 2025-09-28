package com.farmacia.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImagenesService {

    private final Cloudinary cloudinary;

    public String subirImagen(MultipartFile multipartFile) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(),
                ObjectUtils.asMap("folder", "farmacia"));
        return uploadResult.get("secure_url").toString();
    }

    public void eliminarImagen(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}
