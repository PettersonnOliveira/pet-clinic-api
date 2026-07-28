package com.portfolio.Pet.Clinic.API.DTOS;

import com.portfolio.Pet.Clinic.API.Entities.Owner;

public record OwnerResponseDTO (
        Long id,
        String name,
        String email,
        String phone
) {
    public OwnerResponseDTO (Owner owner){
        this(owner.getId(), owner.getName(), owner.getEmail(), owner.getPhone());
    }
}
