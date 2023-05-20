package com.agom.agom.service.jpa;

import com.agom.agom.model.Producto;
import com.agom.agom.repository.ProductoRepository;
import com.agom.agom.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements IProductoService {

    @Autowired
    ProductoRepository productoRepository;

    @Override
    public List<Producto> findall() {
        return productoRepository.findAll();
    }

    public List<Producto> findAllByOrderByPrecio(){
        return productoRepository.findAllByOrderByPrecio();
    }

    @Override
    public Producto findById(Long productoId) {
        Producto producto = null;

        Optional<Producto> optionalProducto = productoRepository.findById(productoId);

        if (!optionalProducto.isEmpty()) {
            producto = optionalProducto.get();
        }

        return producto;
    }

    public Producto findByNombre(String nombre) {
        Producto producto = null;

        Optional<Producto> optionalProducto = productoRepository.findByNombre(nombre);

        if (!optionalProducto.isEmpty()) {
            producto = optionalProducto.get();
        }

        return producto;
    }

    @Override
    public Producto save(Producto producto) {
        if(producto.getId() != null){
            throw new IllegalArgumentException("Ya existe el producto que intenta crear.");
        }
        return productoRepository.save(producto);
    }

    @Override
    public Producto update(Producto producto) {
        if(producto.getId() == null || productoRepository.findById(producto.getId()).isEmpty()){
            throw new IllegalArgumentException("El elemento que intenta actualizar no existe");
        }
        Producto updatedProducto = productoRepository.save(producto);
        return updatedProducto;
    }

    @Override
    public void deleteById(Long productoId) {
        if(productoRepository.findById(productoId).isEmpty()){
            throw new IllegalArgumentException("No se pudo eliminar el producto con id: " + productoId +  " por que no existe.");
        }else{
            productoRepository.deleteById(productoId);
        }
    }
}
