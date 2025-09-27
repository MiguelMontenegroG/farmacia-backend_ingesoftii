package com.farmacia;

import com.farmacia.model.Categoria;
import com.farmacia.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CategoriaTest {

    @Autowired
    private CategoriaRepository categoriaRepo;

    @Test
    public void crearCategoriasTest() {
        Categoria cat1 = new Categoria("Analgesicos", "Medicamentos para el dolor y fiebre");
        cat1.setImagenUrl("img/analgesicos.png");
        cat1.setKeywords(Arrays.asList("dolor", "analgesico", "pastillas"));

        Categoria cat2 = new Categoria("Antibioticos", "Medicamentos contra bacterias e infecciones");
        cat2.setImagenUrl("img/antibioticos.png");
        cat2.setKeywords(Arrays.asList("bacterias", "infeccion", "antibiotico"));

        Categoria cat3 = new Categoria("Vitaminas", "Suplementos vitamínicos");
        cat3.setImagenUrl("img/vitaminas.png");
        cat3.setKeywords(Arrays.asList("energia", "vitaminas", "suplemento"));

        Categoria cat4 = new Categoria("Dermatologicos", "Productos para la piel y cuidado personal");
        cat4.setImagenUrl("img/dermatologicos.png");
        cat4.setKeywords(Arrays.asList("piel", "cremas", "derma"));

        Categoria cat5 = new Categoria("Cardiovasculares", "Medicamentos para el corazón y circulación");
        cat5.setImagenUrl("img/cardiovasculares.png");
        cat5.setKeywords(Arrays.asList("cardio", "corazon", "circulacion"));

        categoriaRepo.save(cat1);
        categoriaRepo.save(cat2);
        categoriaRepo.save(cat3);
        categoriaRepo.save(cat4);
        categoriaRepo.save(cat5);

        assertNotNull(cat1.getId());
        assertNotNull(cat2.getId());
        assertNotNull(cat3.getId());
        assertNotNull(cat4.getId());
        assertNotNull(cat5.getId());
    }
}
