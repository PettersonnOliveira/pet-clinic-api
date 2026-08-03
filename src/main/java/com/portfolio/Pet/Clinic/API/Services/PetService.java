package com.portfolio.Pet.Clinic.API.Services;

import com.portfolio.Pet.Clinic.API.DTOS.PetRequestDTO;
import com.portfolio.Pet.Clinic.API.DTOS.PetResponseDTO;
import com.portfolio.Pet.Clinic.API.DTOS.PetUpdateDTO;
import com.portfolio.Pet.Clinic.API.Entities.Owner;
import com.portfolio.Pet.Clinic.API.Entities.Pet;
import com.portfolio.Pet.Clinic.API.Exceptions.BusinessRuleException;
import com.portfolio.Pet.Clinic.API.Exceptions.ResourceNotFoundException;
import com.portfolio.Pet.Clinic.API.Repositories.OwnerRepository;
import com.portfolio.Pet.Clinic.API.Repositories.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PetService {
    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    public PetService(PetRepository petRepository, OwnerRepository ownerRepository) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
    }

    public PetResponseDTO create(PetRequestDTO inputData){
        Owner owner = ownerRepository.findById(inputData.ownerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner não encontrado"));

        Pet pet = new Pet(inputData);
        pet.setOwner(owner);
        Pet petSalva = petRepository.save(pet);
        return new PetResponseDTO(petSalva);
    }

    public List<PetResponseDTO> listAll(){
        List<Pet> petList = petRepository.findAll();

        return petList.stream()
                .map(PetResponseDTO::new)
                .toList();
    }
    public PetResponseDTO findById(Long id){
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado"));

        return new PetResponseDTO(pet);
    }

    public PetResponseDTO update(Long id, PetUpdateDTO updateData){
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado"));

        pet.setName(updateData.name());
        pet.setSpecies(updateData.species());
        pet.setBreed(updateData.breed());
        pet.setAge(updateData.age());

        Pet petUpdate = petRepository.save(pet);
        return new PetResponseDTO(petUpdate);
    }

    public void delete(Long id){
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado"));

        petRepository.delete(pet);
    }
}
