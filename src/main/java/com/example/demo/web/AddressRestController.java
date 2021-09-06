package com.example.demo.web;

import com.example.demo.domain.Address;
import com.example.demo.domain.AddressRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressRestController {
    
    private final AddressRepository repository;
    
    public AddressRestController(AddressRepository repository) {
        this.repository = repository;
    }
    
    //Операция сохранения адреса в базу данных
    @PostMapping("/ADDRESSES")
    @ResponseStatus(HttpStatus.CREATED)
    public Address saveAddress(@RequestBody Address address) {
        
        return repository.save(address);
    }
    
    //Получение списка адресов
    @GetMapping("/ADDRESSES")
    @ResponseStatus(HttpStatus.OK)
    public List<Address> getAllAddresses() {
        
        return repository.findAll();
    }
    
    //Получение адресов по id
    @GetMapping("/ADDRESSES/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Address getAddressById(@PathVariable long id) {
        
        return repository.findById(id)
                         .orElseThrow(() -> new EntityNotFoundException("Address not found with id = " + id));
    }
    
    //Обновление адресов
    @PutMapping("/ADDRESSES/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Address refreshAddress(@PathVariable("id") long id, @RequestBody Address address) {
        
        return repository.findById(id)
                         .map(entity -> {
                             entity.setCountry(address.getCountry());
                             entity.setCity(address.getCity());
                             entity.setZipCode(address.getZipCode());
                             return repository.save(entity);
                         })
                         .orElseThrow(() -> new EntityNotFoundException("Address not found with id = " + id));
    }
    
    //Удаление адресов по id
    @DeleteMapping("/ADDRESSES/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAddressById(@PathVariable long id) {
        repository.findById(id)
                  .map(address -> {
                      address.setDeleted(Boolean.TRUE);
                      return repository.save(address);
                  })
                  .orElseThrow(() -> new EntityNotFoundException("Address not found with id = " + id));
    }
    
    //Удаление всех адресов
    @DeleteMapping("/ADDRESSES")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllSuppliers() {
        repository.deleteAll();
    }
}