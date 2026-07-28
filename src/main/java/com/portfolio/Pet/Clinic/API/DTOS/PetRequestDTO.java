package com.portfolio.Pet.Clinic.API.DTOS;

public record PetRequestDTO(
        String name,
        String species,
        String breed,
        Integer age

){
}
