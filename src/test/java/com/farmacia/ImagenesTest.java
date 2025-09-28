package com.farmacia;



import com.farmacia.service.ImagenesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ImagenesServiceTest {

    @Autowired
    private ImagenesService imagenesService;

    @Test
    void testSubirYEliminarImagen() throws Exception {

        FileInputStream fis = new FileInputStream("src/test/resources/test.png");
        MockMultipartFile mockFile = new MockMultipartFile(
                "imagen", "test.png", "image/png", fis);


        String url = imagenesService.subirImagen(mockFile);
        System.out.println(" Imagen subida a: " + url);

        assertThat(url).isNotNull();
        assertThat(url).contains("cloudinary.com");


        String publicId = url.substring(url.indexOf("farmacia/"), url.lastIndexOf("."));
        System.out.println("Public ID: " + publicId);

        assertThat(publicId).isNotEmpty();

    }
}
