package com.portfolio.Pet.Clinic.API.Services;

import com.portfolio.Pet.Clinic.API.DTOS.OwnerRequestDTO;
import com.portfolio.Pet.Clinic.API.DTOS.OwnerResponseDTO;
import com.portfolio.Pet.Clinic.API.DTOS.OwnerUpdateDTO;
import com.portfolio.Pet.Clinic.API.Entities.Owner;
import com.portfolio.Pet.Clinic.API.Exceptions.BusinessRuleException;
import com.portfolio.Pet.Clinic.API.Exceptions.ResourceNotFoundException;
import com.portfolio.Pet.Clinic.API.Repositories.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public OwnerResponseDTO create(OwnerRequestDTO inputData) {

        Owner owner = new Owner(inputData);
        Owner ownerSalva = ownerRepository.save(owner);
        return new OwnerResponseDTO(ownerSalva);
    }

    public List<OwnerResponseDTO> listAll(){
        List<Owner> Ownerlist = ownerRepository.findAll();
        return Ownerlist.stream()
                .map(OwnerResponseDTO::new)
                .toList();
    }
    public OwnerResponseDTO findById(Long id){
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner não encontrado"));
        return new OwnerResponseDTO(owner);
    }
    public OwnerResponseDTO update(Long id, OwnerUpdateDTO updadeData){
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner não encontrado"));

        owner.setName(updadeData.name());
        owner.setEmail(updadeData.email());
        owner.setPhone(updadeData.phone());

        Owner ownerUpdate = ownerRepository.save(owner);
        return new OwnerResponseDTO(ownerUpdate);
    }

    public void delete(Long id){
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner não encontrado"));

        ownerRepository.delete(owner);
    }
}
