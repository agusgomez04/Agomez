package com.agom.agom.service;

import com.agom.agom.model.Producto;

import java.util.List;

public interface IProductoService {

    List<Producto> findall();
    Producto findById(Long productoId);
    Producto save(Producto producto);
    Producto update(Producto Producto);
    void deleteById(Long productoId);
}
