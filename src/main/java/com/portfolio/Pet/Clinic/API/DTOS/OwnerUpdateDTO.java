package com.portfolio.Pet.Clinic.API.DTOS;

public record OwnerUpdateDTO(
        Long id,
        String name,
        String email,
        String phone
) {
    public OwnerUpdateDTO(Long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
