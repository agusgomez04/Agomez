package com.agom.agom.controller;

import com.agom.agom.model.Producto;
import com.agom.agom.service.jpa.ProductoServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.InputMismatchException;
import java.util.List;

@RestController
@RequestMapping("/producto")
@AllArgsConstructor
public class ProductoController {

    @Autowired
    ProductoServiceImpl service;

    @GetMapping()
    public ResponseEntity<List<Producto>> getAllProductos() {
        return new ResponseEntity<>(service.findall(), HttpStatus.OK);
    }

    @GetMapping("/orderbyprecio")
    public ResponseEntity<List<Producto>> getAllProductosByOrderByPrecio() {
        return new ResponseEntity<>(service.findAllByOrderByPrecio(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable("id") Long productoId) {
        try {
            Producto producto = service.findById(productoId);
            return new ResponseEntity<>(producto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/byname")
    public ResponseEntity<Producto> getProductoByName(@RequestParam String nombre) {
        try {
            Producto producto = service.findByNombre(nombre);
            return new ResponseEntity<>(producto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto){
        try{
            Producto productoCreated = service.save(producto);
            return new ResponseEntity<>(productoCreated, HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@RequestBody Producto producto, @PathVariable("id") Long productoId){
        try{
            if(!producto.getId().equals(productoId)){
                throw new InputMismatchException("El id de la url y el enviado en el body no coinciden.");
            }
            Producto updatedProducto = service.update(producto);
            return new ResponseEntity<>(updatedProducto,HttpStatus.OK);
        }catch (IllegalArgumentException ile){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (InputMismatchException ipm){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Producto> deleteProductoById(@PathVariable("id") Long productoId) {
        try {
            service.deleteById(productoId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
