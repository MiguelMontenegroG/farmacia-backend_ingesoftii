package com.farmacia.controller;

import com.farmacia.dto.ApiResponse;
import com.farmacia.model.Pedido;
import com.farmacia.model.Producto;
import com.farmacia.model.Usuario;
import com.farmacia.dto.UsuarioDetalleDTO;
import com.farmacia.service.PedidoService;
import com.farmacia.service.ProductoService;
import com.farmacia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000", "https://*.vercel.app"}, allowCredentials = "true")
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PedidoService pedidoService;

    /**
     * GET /api/admin/dashboard/stats
     * Obtiene estadísticas para el dashboard de administración
     * 
     * @return Estadísticas del sistema
     */
    @GetMapping("/dashboard/stats")
    public ResponseEntity<ApiResponse> obtenerEstadisticas() {
        try {
            Map<String, Object> stats = new HashMap<>();
            
            // Contar usuarios
            List<UsuarioDetalleDTO> usuarios = usuarioService.listarUsuarios();
            int totalUsuarios = usuarios.size();
            int usuariosActivos = (int) usuarios.stream().filter(u -> u.isActivo()).count();
            
            // Contar productos
            List<Producto> productos = productoService.obtenerProductos();
            int totalProductos = productos.size();
            int productosActivos = (int) productos.stream().filter(p -> p.isActivo()).count();
            
            // Contar pedidos
            List<Pedido> pedidos = pedidoService.obtenerTodosPedidos();
            int totalPedidos = pedidos.size();
            long pedidosPendientes = pedidos.stream().filter(p -> "pendiente".equalsIgnoreCase(p.getEstado())).count();
            
            // Calcular ingresos
            double ingresoTotal = pedidos.stream()
                .filter(p -> !"cancelado".equalsIgnoreCase(p.getEstado()))
                .mapToDouble(p -> p.getTotal() != null ? p.getTotal().doubleValue() : 0.0)
                .sum();
            
            // Ingresos de hoy
            LocalDateTime inicioHoy = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
            LocalDateTime finHoy = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
            double ingresoHoy = pedidos.stream()
                .filter(p -> !"cancelado".equalsIgnoreCase(p.getEstado()))
                .filter(p -> {
                    LocalDateTime fecha = p.getFechaCreacion();
                    return fecha != null && fecha.isAfter(inicioHoy) && fecha.isBefore(finHoy);
                })
                .mapToDouble(p -> p.getTotal() != null ? p.getTotal().doubleValue() : 0.0)
                .sum();
            
            // Pedidos de hoy
            int pedidosHoy = (int) pedidos.stream()
                .filter(p -> {
                    LocalDateTime fecha = p.getFechaCreacion();
                    return fecha != null && fecha.isAfter(inicioHoy) && fecha.isBefore(finHoy);
                })
                .count();
            
            // Construir respuesta
            stats.put("totalUsuarios", totalUsuarios);
            stats.put("usuariosActivos", usuariosActivos);
            stats.put("totalProductos", totalProductos);
            stats.put("productosActivos", productosActivos);
            stats.put("totalPedidos", totalPedidos);
            stats.put("pedidosPendientes", pedidosPendientes);
            stats.put("ingresoTotal", ingresoTotal);
            stats.put("ingresoHoy", ingresoHoy);
            stats.put("pedidosHoy", pedidosHoy);
            
            ApiResponse response = new ApiResponse(true, "Estadísticas del dashboard", stats);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al obtener estadísticas: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * GET /api/admin/usuarios
     * Obtiene lista de todos los usuarios
     * 
     * @return Lista de usuarios con sus detalles
     */
    @GetMapping("/usuarios")
    public ResponseEntity<ApiResponse> obtenerUsuarios() {
        try {
            List<UsuarioDetalleDTO> usuarios = usuarioService.listarUsuarios();
            Map<String, Object> data = new HashMap<>();
            data.put("usuarios", usuarios);
            data.put("total", usuarios.size());
            
            ApiResponse response = new ApiResponse(true, "Usuarios obtenidos exitosamente", data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al obtener usuarios: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * GET /api/admin/usuarios/:id
     * Obtiene detalles de un usuario específico
     * 
     * @param id ID del usuario
     * @return Detalles del usuario
     */
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<ApiResponse> obtenerUsuario(@PathVariable String id) {
        try {
            UsuarioDetalleDTO usuario = usuarioService.obtenerDetallesUsuario(id);
            ApiResponse response = new ApiResponse(true, "Usuario obtenido exitosamente", usuario);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al obtener usuario: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * PUT /api/admin/usuarios/:id
     * Actualiza un usuario (alias para /api/usuarios/admin/:id)
     * 
     * @param id ID del usuario
     * @param usuarioData Datos a actualizar
     * @return Usuario actualizado
     */
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<ApiResponse> actualizarUsuario(@PathVariable String id, @RequestBody Map<String, Object> usuarioData) {
        try {
            // Convertir Map a Usuario
            Usuario usuario = new Usuario();
            if (usuarioData.get("nombre") != null) {
                usuario.setNombre((String) usuarioData.get("nombre"));
            }
            if (usuarioData.get("apellido") != null) {
                usuario.setApellido((String) usuarioData.get("apellido"));
            }
            if (usuarioData.get("email") != null) {
                usuario.setEmail((String) usuarioData.get("email"));
            }
            if (usuarioData.get("telefono") != null) {
                usuario.setTelefono((String) usuarioData.get("telefono"));
            }
            
            // Redireccionar a UsuarioController
            usuarioService.actualizarUsuario(id, usuario);
            UsuarioDetalleDTO usuarioActualizado = usuarioService.obtenerDetallesUsuario(id);
            ApiResponse response = new ApiResponse(true, "Usuario actualizado exitosamente", usuarioActualizado);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al actualizar usuario: " + e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * GET /api/admin/pedidos
     * Obtiene lista de pedidos con filtros opcionales
     * 
     * @param estado Estado del pedido (opcional)
     * @param fechaInicio Fecha inicio (opcional)
     * @param fechaFin Fecha fin (opcional)
     * @return Lista de pedidos
     */
    @GetMapping("/pedidos")
    public ResponseEntity<ApiResponse> obtenerPedidos(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {
        try {
            List<Pedido> pedidos = pedidoService.obtenerTodosPedidos();
            
            // Filtrar por estado si se proporciona
            if (estado != null && !estado.isEmpty()) {
                pedidos = pedidos.stream()
                    .filter(p -> estado.equalsIgnoreCase(p.getEstado()))
                    .collect(Collectors.toList());
            }
            
            // Filtrar por fecha si se proporciona
            if (fechaInicio != null && !fechaInicio.isEmpty()) {
                LocalDate inicio = LocalDate.parse(fechaInicio);
                LocalDateTime inicioDateTime = LocalDateTime.of(inicio, LocalTime.MIDNIGHT);
                pedidos = pedidos.stream()
                    .filter(p -> p.getFechaCreacion().isAfter(inicioDateTime))
                    .collect(Collectors.toList());
            }
            
            if (fechaFin != null && !fechaFin.isEmpty()) {
                LocalDate fin = LocalDate.parse(fechaFin);
                LocalDateTime finDateTime = LocalDateTime.of(fin, LocalTime.MAX);
                pedidos = pedidos.stream()
                    .filter(p -> p.getFechaCreacion().isBefore(finDateTime))
                    .collect(Collectors.toList());
            }
            
            Map<String, Object> data = new HashMap<>();
            data.put("pedidos", pedidos);
            data.put("total", pedidos.size());
            
            ApiResponse response = new ApiResponse(true, "Pedidos obtenidos exitosamente", data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al obtener pedidos: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * GET /api/admin/productos
     * Obtiene lista de productos con filtros opcionales
     * 
     * @param activo Filtrar por activo (opcional)
     * @param categoriaId Filtrar por categoría (opcional)
     * @param busqueda Buscar por término (opcional)
     * @return Lista de productos
     */
    @GetMapping("/productos")
    public ResponseEntity<ApiResponse> obtenerProductos(
            @RequestParam(required = false) Boolean activo,
            @RequestParam(required = false) String categoriaId,
            @RequestParam(required = false) String busqueda) {
        try {
            List<Producto> productos = productoService.obtenerProductos();
            
            // Filtrar por activo si se proporciona
            if (activo != null) {
                productos = productos.stream()
                    .filter(p -> p.isActivo() == activo)
                    .collect(Collectors.toList());
            }
            
            // Filtrar por categoría si se proporciona
            if (categoriaId != null && !categoriaId.isEmpty()) {
                productos = productos.stream()
                    .filter(p -> categoriaId.equals(p.getCategoriaId()))
                    .collect(Collectors.toList());
            }
            
            // Filtrar por búsqueda si se proporciona
            if (busqueda != null && !busqueda.isEmpty()) {
                productos = productos.stream()
                    .filter(p -> p.getNombre().toLowerCase().contains(busqueda.toLowerCase()) ||
                               p.getDescripcion().toLowerCase().contains(busqueda.toLowerCase()) ||
                               (p.getPrincipioActivo() != null && p.getPrincipioActivo().toLowerCase().contains(busqueda.toLowerCase())))
                    .collect(Collectors.toList());
            }
            
            Map<String, Object> data = new HashMap<>();
            data.put("productos", productos);
            data.put("total", productos.size());
            
            ApiResponse response = new ApiResponse(true, "Productos obtenidos exitosamente", data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al obtener productos: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }
}