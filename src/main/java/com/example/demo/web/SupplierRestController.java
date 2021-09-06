package com.example.demo.web;

import com.example.demo.domain.Supplier;
import com.example.demo.domain.SupplierRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping( value = "/api",
                 produces = MediaType.APPLICATION_JSON_VALUE )
public class SupplierRestController
{
    private final SupplierRepository repository;
    
    public SupplierRestController( SupplierRepository repository )
    {
        this.repository = repository;
    }
    
    //Операция сохранения поставщика в базу данных
    @PostMapping( "/SUPPLIERS" )
    @ResponseStatus( HttpStatus.CREATED )
    public Supplier saveSupplier(
            @RequestBody
                    Supplier supplier )
    {
        return repository.save( supplier );
    }
    
    //Получение списка поставщикoB
    @GetMapping( "/SUPPLIERS" )
    @ResponseStatus( HttpStatus.OK )
    public List <Supplier> getAllSuppliers()
    {
        List <Supplier> lst = repository.findAll();
        for ( int i = 0 ; i < lst.toArray().length ; i++ )
        {
            if ( lst.get( i ).getDeleted() )
            {
                lst.remove( i );
                i--;
            }
        }
        return lst;
    }
    
    //Получение поставщика по id
    @GetMapping( "/SUPPLIERS/{id}" )
    @ResponseStatus( HttpStatus.OK )
    public Supplier getSupplierById(
            @PathVariable
                    long id )
    {
        Supplier supplier = repository.findById( id )
                                      .orElseThrow( () -> new EntityNotFoundException(
                                              "Supplier not found with id = " + id ) );
        
        if ( supplier.getDeleted() )
        {
            throw new EntityNotFoundException( "Supplier was deleted with id = " + id );
        }
        
        return supplier;
    }
    
    //Обновление поставщика
    @PutMapping( "/SUPPLIERS/{id}" )
    @ResponseStatus( HttpStatus.OK )
    public Supplier refreshSupplier(
            @PathVariable( "id" )
                    long id ,
            @RequestBody
                    Supplier supplier )
    {
        
        return repository.findById( id )
                         .map( entity ->
                               {
                                   entity.setName( supplier.getName() );
                                   entity.setAddress( supplier.getAddress() );
                                   entity.setPersonsList( supplier.getPersonsList() );
                                   return repository.save( entity );
                               } )
                         .orElseThrow( () -> new EntityNotFoundException(
                                 "Supplier not found with id = " + id ) );
    }
    
    //Удаление поставщика по id
    @DeleteMapping( "/SUPPLIERS/{id}" )
    @ResponseStatus( HttpStatus.OK )
    public void removeSupplierById(
            @PathVariable
                    long id )
    {
        repository.findById( id )
                  .map( supplier ->
                        {
                            supplier.setDeleted( Boolean.TRUE );
                            return repository.save( supplier );
                        } )
                  .orElseThrow( () -> new EntityNotFoundException(
                          "Supplier not found with id = " + id ) );
    }
    
    //Удаление всех поставщиков
    @DeleteMapping( "/SUPPLIERS" )
    @ResponseStatus( HttpStatus.OK )
    public void removeAllSuppliers()
    {
        repository.findAll().forEach( supplier ->
                                      {
                                          supplier.setDeleted( Boolean.TRUE );
                                          repository.save( supplier );
                                      }
                                    );
    }
}