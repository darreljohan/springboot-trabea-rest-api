package com.iglo.trabea.partTimeEmployee;

import com.iglo.trabea.partTimeEmployee.dto.PartTimeEmployeeFormRequest;
import com.iglo.trabea.partTimeEmployee.dto.PartTimeEmployeePlaceholderResponse;
import com.iglo.trabea.partTimeEmployee.dto.PartTimeEmployeeContactResponse;
import com.iglo.trabea.partTimeEmployee.dto.PartTimeEmployeeSummaryResponse;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("part-timer")
public class PartTimeEmployeeController {
    public PartTimeEmployeeService partTimeEmployeeService;


    public PartTimeEmployeeController(PartTimeEmployeeService partTimeEmployeeService) {
        this.partTimeEmployeeService = partTimeEmployeeService;
    }

    @PreAuthorize("hasRole('Administrator')")
    @GetMapping("{id}")
    public ResponseEntity<PartTimeEmployeePlaceholderResponse> findPartTimePlaceholderById(@PathVariable Integer id) {
        return ResponseEntity.ok(partTimeEmployeeService.findPartTimePlaceholderById(id));
    }

    @PreAuthorize("hasRole('Administrator')")
    @GetMapping()
    public ResponseEntity<Page<PartTimeEmployeeSummaryResponse>> findAllPartTimeEmployee(@PageableDefault(sort = "id", size = 10, page=0) Pageable pageable) {
        return ResponseEntity.ok(partTimeEmployeeService.findAllPartTimeEmployees(pageable));
    }

    @PreAuthorize("hasRole('Administrator')")
    @PostMapping()
    public ResponseEntity<PartTimeEmployeeSummaryResponse> addEmployee(@RequestBody @Valid PartTimeEmployeeFormRequest partTimeEmployeeFormRequest) {
        return ResponseEntity.ok(partTimeEmployeeService.addPartTimeEmployee(partTimeEmployeeFormRequest));
    }

    @PreAuthorize("hasRole('Administrator')")
    @PutMapping("{id}")
    public ResponseEntity<PartTimeEmployeeSummaryResponse> updateEmployee(@PathVariable Integer id, @RequestBody @Valid PartTimeEmployeeFormRequest partTimeEmployeeFormRequest) {
        return ResponseEntity.ok(partTimeEmployeeService.updatePartTimeEmployee(id, partTimeEmployeeFormRequest));
    }

    @PreAuthorize("hasRole('Manager')")
    @GetMapping("contact/{id}")
    public ResponseEntity<PartTimeEmployeeContactResponse> findPartTimeEmployeeContactById(@PathVariable Integer id){
        return ResponseEntity.ok(partTimeEmployeeService.findPartTimeEmployeeContactById(id));
    }
 }
