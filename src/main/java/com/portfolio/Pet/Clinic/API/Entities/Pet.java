package com.portfolio.Pet.Clinic.API.Entities;

import com.portfolio.Pet.Clinic.API.DTOS.PetRequestDTO;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

public class Pet {
    private Long id;
    private String name;
    private String species;
    private String breed;
    private Integer age;

    @ManyToOne
    private Owner owner;

    public Pet(){
    }

    public Pet(PetRequestDTO inputData){
    this.name = inputData.name();
    this.species = inputData.species();
    this.breed = inputData.breed();
    this.age = inputData.age();
    this.owner = inputData.ow
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
