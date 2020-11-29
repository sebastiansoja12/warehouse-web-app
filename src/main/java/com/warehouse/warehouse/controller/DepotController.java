package com.warehouse.warehouse.controller;

import com.warehouse.warehouse.dto.RefreshTokenRequest;
import com.warehouse.warehouse.model.Depot;
import com.warehouse.warehouse.model.Parcel;
import com.warehouse.warehouse.service.DepotService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/api/depots")
public class DepotController {

    private final DepotService depotService;

    @GetMapping("/all")
    public List<Depot> getAllDepots(){
        return depotService.findAllDepots();
    }

    @GetMapping("/all/parcelId/{id}")
    public List<Depot> findAllDepotByParcelId(@PathVariable UUID id){
        return depotService.findAllDepotByParcelId(id);
    }

    @GetMapping("/all/parcelCode/{parcelCode}")
    public List<Depot> findByParcelCode(@PathVariable String parcelCode){
        return depotService.findByParcelCode(parcelCode);
    }
    @PostMapping("/all/parcelId/delete/{id}")
    public ResponseEntity<String> deleteDepotByParcelId(  @Valid @PathVariable UUID id){
            depotService.deleteDepotByParcelId( id);
            return ResponseEntity.status(OK).body("Zarejestrowana paczka usunięta");
    }
}
