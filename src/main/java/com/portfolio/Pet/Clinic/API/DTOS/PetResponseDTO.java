package com.portfolio.Pet.Clinic.API.DTOS;

import com.portfolio.Pet.Clinic.API.Entities.Pet;

public record PetResponseDTO(
        Long id,
        String name,
        String species,
        String breed,
        Integer age,
        OwnerResponseDTO ownerName;
) {
    public PetResponseDTO (Pet pet){
        this(pet.getId(), pet.getName(), pet.getSpecies(), pet.getBreed(), pet.getAge(),pet.);
    }
}
