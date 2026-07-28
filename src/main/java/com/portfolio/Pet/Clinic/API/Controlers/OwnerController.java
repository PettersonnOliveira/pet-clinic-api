package com.portfolio.Pet.Clinic.API.Controlers;

import com.portfolio.Pet.Clinic.API.DTOS.OwnerRequestDTO;
import com.portfolio.Pet.Clinic.API.DTOS.OwnerResponseDTO;
import com.portfolio.Pet.Clinic.API.DTOS.OwnerUpdateDTO;
import com.portfolio.Pet.Clinic.API.Services.OwnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("owners")
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping
    public ResponseEntity<OwnerResponseDTO> create(@RequestBody OwnerRequestDTO inputData){
        OwnerResponseDTO ownerResponseDTO = ownerService.create(inputData);
        return ResponseEntity.status(HttpStatus.CREATED).body(ownerResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<OwnerResponseDTO>> listAll(){
        List<OwnerResponseDTO> owners = ownerService.listAll();
        return ResponseEntity.ok(owners);

    }
    @GetMapping("/{id}")
    public ResponseEntity<OwnerResponseDTO> findById(@PathVariable Long id){
        OwnerResponseDTO ownerResponseDTO = ownerService.findById(id);
        return ResponseEntity.ok(ownerResponseDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<OwnerResponseDTO> update(@PathVariable Long id, @RequestBody OwnerUpdateDTO updateData){
        OwnerResponseDTO ownerResponseDTO = ownerService.update(id, updateData);
        return ResponseEntity.ok(ownerResponseDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        ownerService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
