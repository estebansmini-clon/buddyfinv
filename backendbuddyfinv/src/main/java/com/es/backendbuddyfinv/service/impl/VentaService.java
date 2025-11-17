package com.es.backendbuddyfinv.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.es.backendbuddyfinv.dto.DetalleProductoDTO;
import com.es.backendbuddyfinv.dto.DetalleVentaCrearDTO;
import com.es.backendbuddyfinv.dto.VentaCrearDTO;
import com.es.backendbuddyfinv.dto.VentaDetalladaDTO;
import com.es.backendbuddyfinv.model.DetalleInventario;
import com.es.backendbuddyfinv.model.DetalleVenta;
import com.es.backendbuddyfinv.model.EstadoVenta;
import com.es.backendbuddyfinv.model.MetodoPago;
import com.es.backendbuddyfinv.model.Producto;
import com.es.backendbuddyfinv.model.Usuario;
import com.es.backendbuddyfinv.model.Venta;
import com.es.backendbuddyfinv.repository.DetalleInventarioRepository;
import com.es.backendbuddyfinv.repository.DetalleVentaRepository;
import com.es.backendbuddyfinv.repository.EstadoVentaRepository;
import com.es.backendbuddyfinv.repository.MetodoPagoRepository;
import com.es.backendbuddyfinv.repository.ProductoRepository;
import com.es.backendbuddyfinv.repository.UsuarioRepository;
import com.es.backendbuddyfinv.repository.VentaRepository;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;
    //repositorios necesarios apra crear venta esteban moreno 

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private DetalleInventarioRepository detalleInventarioRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @Autowired
    private EstadoVentaRepository estadoVentaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;



    public List<VentaDetalladaDTO> listarVentasDetalladas(Long idPropietario) {
        List<Venta> ventas = ventaRepository.findVentasDetalladasByPropietarioId(idPropietario);
    
        return ventas.stream().map(v -> {
            List<DetalleProductoDTO> productos = v.getDetalleVentas().stream()
                .map(dv -> new DetalleProductoDTO(
                    dv.getProducto().getNombre(),
                    dv.getCantidad(),
                    dv.getSubtotal(),
                    dv.getProducto().getEstadoProducto().getObservacion(),
                    dv.getProducto().getPrecio()
                ))
                .collect(Collectors.toList());
    
            return new VentaDetalladaDTO(
                v.getIdVenta(),
                v.getFecha(),
                v.getTotal(),
                v.getEstadoVenta().getObservacion(),
                v.getMetodoPago().getDescripcion(),
                v.getUsuario().getNombre(),
                productos
            );
        }).collect(Collectors.toList());
    }









    // Crear una nueva venta
    public Venta createVenta(Venta venta) {
        return ventaRepository.save(venta);
    }

    // Obtener todas las ventas
    public List<Venta> getAllVentas() {
        return ventaRepository.findAll();
    }

    // Obtener una venta por ID
    public Optional<Venta> getVentaById(Long id) {
        return ventaRepository.findById(id);
    }

    // Actualizar una venta
    public Venta updateVenta(Long id, Venta ventaDetails) {
        Optional<Venta> optionalVenta = ventaRepository.findById(id);
        if (optionalVenta.isPresent()) {
            Venta venta = optionalVenta.get();
            // Aquí puedes actualizar los campos específicos de la venta
            // venta.setCampo(ventaDetails.getCampo());
            return ventaRepository.save(venta);
        }
        return null;
    }

    // Eliminar una venta
    public boolean deleteVenta(Long id) {
        if (ventaRepository.existsById(id)) {
            ventaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Verificar si existe una venta
    public boolean existsById(Long id) {
        return ventaRepository.existsById(id);
    }


    //implementacion esteban moreno feature crear ventas

    @Transactional
    public Venta registrarVenta(VentaCrearDTO dto, Authentication authentication) {
        // 1. Usuario autenticado
        String username = authentication == null ? null : authentication.getName();
        Usuario usuario = username == null ? null : usuarioRepository.findByUsuario(username).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario autenticado no encontrado");
        }

        // 2. Validar campos básicos
        if (dto.getCliente() == null || dto.getCliente().trim().isEmpty()) {
            throw new IllegalArgumentException("Cliente es obligatorio");
        }
        if (dto.getMetodoPagoId() == null) {
            throw new IllegalArgumentException("Método de pago es obligatorio");
        }

        MetodoPago metodoPago = metodoPagoRepository.findById(dto.getMetodoPagoId())
                .orElseThrow(() -> new IllegalArgumentException("Método de pago inválido"));

        EstadoVenta estadoVenta = estadoVentaRepository.findById(dto.getEstadoVentaId())
                .orElseThrow(() -> new IllegalArgumentException("Estado de venta inválido"));

        // 3. Preparar Venta
        Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now());
        venta.setCliente(dto.getCliente());
        venta.setMetodoPago(metodoPago);
        venta.setEstadoVenta(estadoVenta);
        venta.setUsuario(usuario);
        venta.setPropietario(usuario.getAdministrador() != null ? usuario.getAdministrador() : usuario);
        venta.setDetalleVentas(new ArrayList<>());

        double total = 0.0;

        // 4. Procesar cada detalle: validar existencia, stock y calcular subtotal
        for (DetalleVentaCrearDTO dDto : dto.getDetalles()) {
            if (dDto.getCantidad() == null || dDto.getCantidad() <= 0) {
                throw new IllegalArgumentException("Cantidad inválida para productoId: " + dDto.getProductoId());
            }

            Producto producto = productoRepository.findById(dDto.getProductoId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + dDto.getProductoId()));

            // Buscar detalle inventario disponible para este producto y propietario
            Optional<DetalleInventario> detalleInvOpt = detalleInventarioRepository
                    .findByProductoIdProductoAndPropietarioId(producto.getIdProducto(), venta.getPropietario().getId());

            // Si el proyecto no trabaja con propietario en inventario, puedes usar findByProductoIdProducto(productoId)
            DetalleInventario detalleInventario = detalleInvOpt.orElseGet(() -> {
                // si no hay registro de inventario para este propietario, intentamos cualquiera con stock
                List<DetalleInventario> lista = detalleInventarioRepository.findByProductoIdProducto(producto.getIdProducto());
                return lista.stream().filter(di -> di.getCantidadDisponible() > 0).findFirst().orElse(null);
            });

            if (detalleInventario == null) {
                throw new IllegalArgumentException("Stock insuficiente. Producto sin inventario disponible: " + producto.getNombre());
            }

            if (detalleInventario.getCantidadDisponible() < dDto.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para productoId " + producto.getIdProducto() +
                        ". Disponibles: " + detalleInventario.getCantidadDisponible());
            }

            double subtotal = producto.getPrecio() * dDto.getCantidad();
            total += subtotal;

            // Crear DetalleVenta (sin persistir aún)
            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setCantidad(dDto.getCantidad());
            detalleVenta.setPrecioUnitario(producto.getPrecio());
            detalleVenta.setSubtotal(subtotal);
            detalleVenta.setProducto(producto);
            detalleVenta.setVenta(venta);

            // Añadir a la venta en memoria
            venta.getDetalleVentas().add(detalleVenta);

            // Actualizar inventario en memoria (persistiremos al final por cascada o manual)
            detalleInventario.setCantidadDisponible(detalleInventario.getCantidadDisponible() - dDto.getCantidad());
            detalleInventarioRepository.save(detalleInventario); // guardar cambios de inventario inmediatamente
        }

        venta.setTotal(total);

        // 5. Persistir venta (cascade crea detalles si mapped correctamente)
        Venta ventaGuardada = ventaRepository.save(venta);

        // Si no tienes cascade, persistir detalles manualmente
        for (DetalleVenta dv : ventaGuardada.getDetalleVentas()) {
            dv.setVenta(ventaGuardada);
            detalleVentaRepository.save(dv);
        }

        return ventaGuardada;
    }

}
