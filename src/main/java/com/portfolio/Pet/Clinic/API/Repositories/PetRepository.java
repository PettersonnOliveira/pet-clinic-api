package com.portfolio.Pet.Clinic.API.Repositories;

import com.portfolio.Pet.Clinic.API.Entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
