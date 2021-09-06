package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


@Entity
@Table( name = "addresses" )
public class Address
{
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE )
    private long id;
    
    @Column( name = "is_deleted" )
    private Boolean isDeleted = Boolean.FALSE;
    
    private String country;
    
    private String city;
    
    private String zipCode;
    
    @OneToOne(mappedBy = "address")
    @JsonIgnore
    private Supplier supplier;
    
    public long getId()
    {
        return id;
    }
    
    public void setId( long id )
    {
        this.id = id;
    }
    
    public Boolean getDeleted()
    {
        return isDeleted;
    }
    
    public void setDeleted( Boolean deleted )
    {
        isDeleted = deleted;
    }
    
    public String getCountry()
    {
        return country;
    }
    
    public void setCountry( String country )
    {
        this.country = country;
    }
    
    public String getCity()
    {
        return city;
    }
    
    public void setCity( String city )
    {
        this.city = city;
    }
    
    public String getZipCode()
    {
        return zipCode;
    }
    
    public void setZipCode( String zipCode )
    {
        this.zipCode = zipCode;
    }
    
    public Supplier getSupplier()
    {
        return supplier;
    }
    
    public void setSupplier( Supplier supplier )
    {
        this.supplier = supplier;
    }
}