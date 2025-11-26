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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("part-timer")
public class PartTimeEmployeeController {
    public PartTimeEmployeeService partTimeEmployeeService;

    public PartTimeEmployeeController(PartTimeEmployeeService partTimeEmployeeService) {
        this.partTimeEmployeeService = partTimeEmployeeService;
    }

    @GetMapping("{id}")
    public ResponseEntity<PartTimeEmployeePlaceholderResponse> findPartTimePlaceholderById(@PathVariable Integer id) {
        return ResponseEntity.ok(partTimeEmployeeService.findPartTimePlaceholderById(id));
    }

    @GetMapping()
    public ResponseEntity<Page<PartTimeEmployeeSummaryResponse>> findAllEmployee(@PageableDefault(sort = "id", size = 10, page=0) Pageable pageable) {
        return ResponseEntity.ok(partTimeEmployeeService.findAllPartTimeEmployees(pageable));
    }

    @PostMapping()
    public ResponseEntity<PartTimeEmployeeSummaryResponse> addEmployee(@RequestBody @Valid PartTimeEmployeeFormRequest partTimeEmployeeFormRequest) {
        return ResponseEntity.ok(partTimeEmployeeService.addPartTimeEmployee(partTimeEmployeeFormRequest));
    }

    @PutMapping("{id}")
    public ResponseEntity<PartTimeEmployeeSummaryResponse> updateEmployee(@PathVariable Integer id, @RequestBody @Valid PartTimeEmployeeFormRequest partTimeEmployeeFormRequest) {
        return ResponseEntity.ok(partTimeEmployeeService.updatePartTimeEmployee(id, partTimeEmployeeFormRequest));
    }

    @GetMapping("contact/{id}")
    public ResponseEntity<PartTimeEmployeeContactResponse> findPartTimeEmployeeContactById(@PathVariable Integer id){
        return ResponseEntity.ok(partTimeEmployeeService.findPartTimeEmployeeContactById(id));
    }
 }
