package com.iglo.trabea.partTimeEmployee;

import com.iglo.trabea.partTimeEmployee.dto.PartTimeEmployeePlaceholderResponse;
import com.iglo.trabea.partTimeEmployee.dto.PartTimeEmployeeContactResponse;
import com.iglo.trabea.partTimeEmployee.dto.PartTimeEmployeeSummaryResponse;
import org.springframework.stereotype.Component;

@Component
public class PartTimeMapper {

    public PartTimeEmployeeSummaryResponse toPartTimeEmployeeSummaryResponse(PartTimeEmployee partTimeEmployee) {
        return PartTimeEmployeeSummaryResponse.builder()
                .id(partTimeEmployee.getId())
                .fullname(partTimeEmployee.getFullName())
                .joinDate(partTimeEmployee.getJoinDate().toLocalDate())
                .phoneNumber(partTimeEmployee.getPhoneNumber())
                .privateEmail(partTimeEmployee.getPersonalEmail())
                .workEmail(partTimeEmployee.getUser().getWorkEmail())
                .build();
    }

    public PartTimeEmployeePlaceholderResponse toPartTimeEmployeePlaceholderResponse(PartTimeEmployee partTimeEmployee) {
        return PartTimeEmployeePlaceholderResponse.builder()
                .id(partTimeEmployee.getId())
                .firstName(partTimeEmployee.getFirstName())
                .lastName(partTimeEmployee.getLastName())
                .personalEmail(partTimeEmployee.getPersonalEmail())
                .phoneNumber(partTimeEmployee.getPhoneNumber())
                .lastEducation(partTimeEmployee.getLastEducation())
                .onGoingEducation(partTimeEmployee.getOnGoingEducation())
                .build();
    }

    public PartTimeEmployeeContactResponse toPartTimeEmployeeSummaryForManagerResponse(PartTimeEmployee partTimeEmployee) {
        return PartTimeEmployeeContactResponse.builder()
                .fullname(partTimeEmployee.getFullName())
                .personalEmail(partTimeEmployee.getPersonalEmail())
                .workEmail(partTimeEmployee.getUser().getWorkEmail())
                .phoneNumber(partTimeEmployee.getPhoneNumber())
                .build();
    }


}
