/**package com.farmacia;
import com.farmacia.model.Producto;
import com.farmacia.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class ProductoRepoTest {

    @Autowired
    private ProductoRepository productoRepo;

    @Test
    void testCrearCincoProductos() {
        // Arrange - Crear 5 productos
        Producto p1 = new Producto("Paracetamol 500mg", "Analgésico y antipirético",
                new BigDecimal("2500"), null);
        p1.setStock(100);
        p1.setLaboratorio("Genéricos S.A.");
        p1.setPrincipioActivo("Paracetamol");
        p1.setCodigoBarras("1111111111111");

        Producto p2 = new Producto("Ibuprofeno 400mg", "Antiinflamatorio no esteroideo",
                new BigDecimal("3500"), null);
        p2.setStock(80);
        p2.setLaboratorio("Salud Pharma");
        p2.setPrincipioActivo("Ibuprofeno");
        p2.setCodigoBarras("2222222222222");

        Producto p3 = new Producto("Amoxicilina 500mg", "Antibiótico de amplio espectro",
                new BigDecimal("7500"), null);
        p3.setStock(50);
        p3.setLaboratorio("FarmaLife");
        p3.setPrincipioActivo("Amoxicilina");
        p3.setCodigoBarras("3333333333333");
        p3.setRequiereReceta(true);

        Producto p4 = new Producto("Loratadina 10mg", "Antihistamínico para alergias",
                new BigDecimal("4200"), null);
        p4.setStock(120);
        p4.setLaboratorio("Medic Plus");
        p4.setPrincipioActivo("Loratadina");
        p4.setCodigoBarras("4444444444444");

        Producto p5 = new Producto("Vitamina C 1000mg", "Suplemento antioxidante",
                new BigDecimal("5000"), null);
        p5.setStock(200);
        p5.setLaboratorio("Nutri Salud");
        p5.setPrincipioActivo("Ácido ascórbico");
        p5.setCodigoBarras("5555555555555");

        // Act - Guardar todos
        productoRepo.saveAll(List.of(p1, p2, p3, p4, p5));

        // Assert - Verificar que se guardaron
        List<Producto> productos = productoRepo.findAll();
        assertThat(productos).hasSize(5);
        assertThat(productos).extracting(Producto::getNombre)
                .containsExactlyInAnyOrder(
                        "Paracetamol 500mg",
                        "Ibuprofeno 400mg",
                        "Amoxicilina 500mg",
                        "Loratadina 10mg",
                        "Vitamina C 1000mg"
                );
    }
}
**/