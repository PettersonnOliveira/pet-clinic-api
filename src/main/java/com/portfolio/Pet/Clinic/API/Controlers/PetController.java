package com.portfolio.Pet.Clinic.API.Controlers;

import com.portfolio.Pet.Clinic.API.DTOS.PetRequestDTO;
import com.portfolio.Pet.Clinic.API.DTOS.PetResponseDTO;
import com.portfolio.Pet.Clinic.API.DTOS.PetUpdateDTO;
import com.portfolio.Pet.Clinic.API.Services.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pets")
public class PetController {

   private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<PetResponseDTO> create ( @RequestBody PetRequestDTO inputData){
        PetResponseDTO petResponseDTO = petService.create(inputData);
        return ResponseEntity.status(HttpStatus.CREATED).body(petResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<PetResponseDTO>> listAll(){
        List<PetResponseDTO> pets = petService.listAll();
        return ResponseEntity.ok(pets);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDTO> findById(@PathVariable Long id){
        PetResponseDTO petResponseDTO = petService.findById(id);
        return ResponseEntity.ok(petResponseDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PetResponseDTO> update (@PathVariable Long id, @RequestBody PetUpdateDTO updateData){
        PetResponseDTO petResponseDTO = petService.update(id,updateData);
        return ResponseEntity.ok(petResponseDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
