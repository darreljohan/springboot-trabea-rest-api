package com.iglo.trabea.partTimeEmployee;

import com.iglo.trabea.error.exception.ResourceNotFound;
import com.iglo.trabea.partTimeEmployee.dto.PartTimeEmployeeFormRequest;
import com.iglo.trabea.partTimeEmployee.dto.PartTimeEmployeePlaceholderResponse;
import com.iglo.trabea.partTimeEmployee.dto.PartTimeEmployeeContactResponse;
import com.iglo.trabea.partTimeEmployee.dto.PartTimeEmployeeSummaryResponse;
import com.iglo.trabea.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PartTimeEmployeeService {
    private final PartTimeEmployeeRepository partTimeEmployeeRepository;
    private final PartTimeMapper partTimeMapper;

    public PartTimeEmployeePlaceholderResponse findPartTimePlaceholderById(Integer id){
        return partTimeMapper.toPartTimeEmployeePlaceholderResponse(partTimeEmployeeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("Part Time Employee with id "+id+" is not exist")));
    }

    public Page<PartTimeEmployeeSummaryResponse> findAllPartTimeEmployees(Pageable pageable){
        return partTimeEmployeeRepository.findAll(pageable).map(partTimeMapper::toPartTimeEmployeeSummaryResponse);
    }

    public PartTimeEmployeeSummaryResponse addPartTimeEmployee(PartTimeEmployeeFormRequest partTimeEmployeeFormRequest){
       User user = User.builder()
               .workEmail(partTimeEmployeeFormRequest.getEmailPrefix()+"@trabea.co.id")
               .password("Trabea123")
               .build();

        PartTimeEmployee partTimeEmployee = PartTimeEmployee.builder()
               .firstName(partTimeEmployeeFormRequest.getFirstName())
               .lastName(partTimeEmployeeFormRequest.getLastName())
               .joinDate(LocalDateTime.now())
               .lastEducation(partTimeEmployeeFormRequest.getLastEducation())
               .onGoingEducation(partTimeEmployeeFormRequest.getOnGoingEducation())
               .personalEmail(partTimeEmployeeFormRequest.getPersonalEmail())
               .phoneNumber(partTimeEmployeeFormRequest.getPhoneNumber())
                .user(user)
               .build();

        user.setPartTimeInformation(partTimeEmployee);
        PartTimeEmployee result = partTimeEmployeeRepository.save(partTimeEmployee);
        return partTimeMapper.toPartTimeEmployeeSummaryResponse(result);
    }

    @Transactional
    public PartTimeEmployeeSummaryResponse updatePartTimeEmployee(Integer id, PartTimeEmployeeFormRequest partTimeEmployeeFormRequest){
        PartTimeEmployee existingPartTimeEmployee = partTimeEmployeeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("Part Time Employee with id "+id+" is not exist"));

        existingPartTimeEmployee.setFirstName(partTimeEmployeeFormRequest.getFirstName());
        existingPartTimeEmployee.setLastName(partTimeEmployeeFormRequest.getLastName());
        existingPartTimeEmployee.setLastEducation(partTimeEmployeeFormRequest.getLastEducation());
        existingPartTimeEmployee.setOnGoingEducation(partTimeEmployeeFormRequest.getOnGoingEducation());
        existingPartTimeEmployee.setPersonalEmail(partTimeEmployeeFormRequest.getPersonalEmail());
        existingPartTimeEmployee.setPhoneNumber(partTimeEmployeeFormRequest.getPhoneNumber());

        return partTimeMapper.toPartTimeEmployeeSummaryResponse(existingPartTimeEmployee);
    }

    public PartTimeEmployeeContactResponse findEmployeeContactById(Integer id){
        PartTimeEmployee partTimeEmployee = partTimeEmployeeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("Part Time Employee with id "+id+" is not exist"));
        return partTimeMapper.toPartTimeEmployeeSummaryForManagerResponse(partTimeEmployee);
    }

}
