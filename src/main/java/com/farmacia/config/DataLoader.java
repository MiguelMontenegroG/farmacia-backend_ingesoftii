package com.farmacia.config;

import com.farmacia.Enum.Rol;
import com.farmacia.model.Categoria;
import com.farmacia.model.Producto;
import com.farmacia.model.Usuario;
import com.farmacia.repository.CategoriaRepository;
import com.farmacia.repository.ProductoRepository;
import com.farmacia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataLoader {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {

            productoRepository.deleteAll();
            categoriaRepository.deleteAll();


            Categoria medicamentos = new Categoria("Medicamentos", "Medicamentos de venta libre y recetados");
            medicamentos.setImagenUrl("/images/medicamentos.jpg");
            medicamentos.setKeywords(Arrays.asList("medicina", "fármacos", "pastillas", "jarabe"));
            medicamentos.setOrden(1);

            Categoria cuidadoPersonal = new Categoria("Cuidado Personal", "Productos para el cuidado e higiene personal");
            cuidadoPersonal.setImagenUrl("/images/cuidado-personal.jpg");
            cuidadoPersonal.setKeywords(Arrays.asList("higiene", "belleza", "cuidado", "cosmética"));
            cuidadoPersonal.setOrden(2);

            Categoria vitaminas = new Categoria("Vitaminas y Suplementos", "Suplementos alimenticios y vitaminas");
            vitaminas.setImagenUrl("/images/vitaminas.jpg");
            vitaminas.setKeywords(Arrays.asList("vitaminas", "suplementos", "minerales", "nutrición"));
            vitaminas.setOrden(3);

            Categoria maternidad = new Categoria("Maternidad y Bebés", "Productos para mamás y bebés");
            maternidad.setImagenUrl("/images/maternidad.jpg");
            maternidad.setKeywords(Arrays.asList("bebés", "mamás", "lactancia", "pañales"));
            maternidad.setOrden(4);

            categoriaRepository.saveAll(Arrays.asList(medicamentos, cuidadoPersonal, vitaminas, maternidad));


            Categoria analgesicos = new Categoria("Analgésicos", "Medicamentos para el dolor");
            analgesicos.setCategoriaPadre(medicamentos);
            analgesicos.setImagenUrl("/images/analgesicos.jpg");
            analgesicos.setOrden(1);

            Categoria antibioticos = new Categoria("Antibióticos", "Medicamentos antibióticos");
            antibioticos.setCategoriaPadre(medicamentos);
            antibioticos.setImagenUrl("/images/antibioticos.jpg");
            antibioticos.setOrden(2);
            antibioticos.setKeywords(Arrays.asList("infecciones", "bacterias"));

            categoriaRepository.saveAll(Arrays.asList(medicamentos, cuidadoPersonal, vitaminas, maternidad, analgesicos, antibioticos));

            List<Producto> productos = Arrays.asList(
                    crearProducto("Paracetamol 500mg", "Analgésico y antipirético para el dolor y fiebre",
                            new BigDecimal("10000"), new BigDecimal("7000"), true, analgesicos,
                            "https://res.cloudinary.com/dtcpxlmbk/image/upload/v1759043987/farmacia/exehkzcsuyyktzbunlmv.webp", 100, "Bayer", "Paracetamol", "7501006557012", false),

                    crearProducto("Ibuprofeno 400mg", "Antiinflamatorio no esteroideo para el dolor e inflamación",
                            new BigDecimal("12000"), new BigDecimal("6000"), true, analgesicos,
                            "https://res.cloudinary.com/dtcpxlmbk/image/upload/v1759044416/farmacia/vk47adlolcpo82lqemea.webp", 85, "Pfizer", "Ibuprofeno", "7501006557029", false),

                    crearProducto("Aspirina 500mg", "Ácido acetilsalicílico para el dolor y fiebre",
                            new BigDecimal("8000"), null, false, analgesicos,
                            "https://res.cloudinary.com/dtcpxlmbk/image/upload/v1759044537/farmacia/visopcevdj4s4flxyvtc.webp", 120, "Bayer", "Ácido Acetilsalicílico", "7501006557036", false),

                    crearProducto("Amoxicilina 500mg", "Antibiótico de amplio espectro",
                            new BigDecimal("35000"), new BigDecimal("29000"), true, antibioticos,
                            "https://res.cloudinary.com/dtcpxlmbk/image/upload/v1759044646/farmacia/shlwhbx36y6jitimb6vi.webp", 60, "Roche", "Amoxicilina", "7501006557043", true),

                    crearProducto("Azitromicina 500mg", "Antibiótico macrólido",
                            new BigDecimal("40000"), null, false, antibioticos,
                            "https://res.cloudinary.com/dtcpxlmbk/image/upload/v1759044740/farmacia/ph0orinzwf26rslyyz2p.webp", 45, "Novartis", "Azitromicina", "7501006557050", true),

                    crearProducto("Jabón Neutro", "Jabón suave para piel sensible",
                            new BigDecimal("18000"), new BigDecimal("15000"), true, cuidadoPersonal,
                            "https://res.cloudinary.com/dtcpxlmbk/image/upload/v1759044854/farmacia/whxawavo2xc2xu2ssbvw.webp", 200, "Dove", "Glicerina", "7501006557067", false),

                    crearProducto("Shampoo Anticaspa", "Shampoo para control de caspa",
                            new BigDecimal("25000"), null, false, cuidadoPersonal,
                            "https://res.cloudinary.com/dtcpxlmbk/image/upload/v1759045129/test_hknwwk.webp", 150, "Head & Shoulders", "Piroctona Olamina", "7501006557074", false),

                    crearProducto("Vitamina C 1000mg", "Suplemento de vitamina C",
                            new BigDecimal("35000"), new BigDecimal("31000"), true, vitaminas,
                            "https://res.cloudinary.com/dtcpxlmbk/image/upload/v1759045218/test_usuib2.webp", 90, "Nature Made", "Ácido Ascórbico", "7501006557081", false),

                    crearProducto("Multivitamínico", "Complejo multivitamínico completo",
                            new BigDecimal("70000"), null, false, vitaminas,
                            "https://res.cloudinary.com/dtcpxlmbk/image/upload/v1759045263/test_kf11xy.webp", 75, "Centrum", "Multivitamínico", "7501006557098", false),

                    crearProducto("Pañales Talla 1", "Pañales para recién nacidos",
                            new BigDecimal("35000"), new BigDecimal("30000"), true, maternidad,
                            "https://res.cloudinary.com/dtcpxlmbk/image/upload/v1759045178/test_iklqva.webp", 50, "Huggies", "Algodón", "7501006557104", false),

                    crearProducto("Leche Materna", "Fórmula infantil etapa 1",
                            new BigDecimal("28000"), null, false, maternidad,
                            "https://res.cloudinary.com/dtcpxlmbk/image/upload/v1759045302/test_feumi7.webp", 30, "Similac", "Proteínas de leche", "7501006557111", false)
            );


            productoRepository.saveAll(productos);

            System.out.println("=== DATOS DE PRUEBA CARGADOS ===");
            System.out.println("Categorías creadas: " + categoriaRepository.count());
            System.out.println("Productos creados: " + productoRepository.count());
            System.out.println("================================");
            System.out.println("API disponible en: http://localhost:8080");
            System.out.println("Swagger UI disponible en: http://localhost:8080/swagger-ui.html");
            System.out.println("================================");
        };
    }
    @Bean
    public CommandLineRunner cargarUsuariosPrueba() {
        return args -> {

            usuarioRepository.deleteAll();


            Usuario cliente = new Usuario(
                    "Juan",
                    "Pérez",
                    "cliente@farmacia.com",
                    "3001234567",
                    passwordEncoder.encode("1234"),
                    Rol.CLIENTE
            );


            Usuario admin = new Usuario(
                    "Ana",
                    "Gómez",
                    "admin@farmacia.com",
                    "3007654321",
                    passwordEncoder.encode("1234"),
                    Rol.ADMINISTRADOR
            );

            usuarioRepository.saveAll(Arrays.asList(cliente, admin));

            System.out.println("=== USUARIOS DE PRUEBA CARGADOS ===");
            System.out.println("Cliente: cliente@farmacia.com / 1234");
            System.out.println("Administrador: admin@farmacia.com / 1234");
            System.out.println("===================================");
        };
    }

    private Producto crearProducto(String nombre, String descripcion, BigDecimal precio,
                                   BigDecimal precioOferta, boolean enOferta, Categoria categoria,
                                   String imagenUrl, int stock, String laboratorio,
                                   String principioActivo, String codigoBarras, boolean requiereReceta) {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setPrecioOferta(precioOferta);
        producto.setEnOferta(enOferta);
        producto.setCategoria(categoria);
        producto.setImagenUrl(imagenUrl);
        producto.setStock(stock);
        producto.setLaboratorio(laboratorio);
        producto.setPrincipioActivo(principioActivo);
        producto.setCodigoBarras(codigoBarras);
        producto.setRequiereReceta(requiereReceta);
        producto.setActivo(true);
        return producto;
    }
}