package com.portfolio.Pet.Clinic.API.Repositories;


import com.portfolio.Pet.Clinic.API.Entities.Owner;
import com.portfolio.Pet.Clinic.API.Entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

}
