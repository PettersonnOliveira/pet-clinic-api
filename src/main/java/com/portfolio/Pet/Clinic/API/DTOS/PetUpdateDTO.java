package com.portfolio.Pet.Clinic.API.DTOS;

public record PetUpdateDTO(
        Long id,
        String name,
        String species,
        String breed,
        Integer age
) {
    public PetUpdateDTO(Long id, String name, String species, String breed, Integer age) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.age = age;
    }
}
