package com.iglo.trabea.workshifts;

import com.iglo.trabea.workshifts.dto.WorkShiftResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkShiftService {
    private final WorkShiftRepository workShiftRepository;
    private final WorkShiftMapper workShiftMapper;

    public List<WorkShiftResponse> getAllWorkShifts() {
        return workShiftRepository.findAll()
                .stream()
                .map(workShiftMapper::toWorkShiftResponse)
                .toList();
    }
}
