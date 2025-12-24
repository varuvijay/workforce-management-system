package com.varun.workforce_management.timesheet.service;

import com.varun.workforce_management.helper.MessageResponseDTO;
import com.varun.workforce_management.timesheet.dto.*;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface TimesheetService {

     MessageResponseDTO checkedIn();

     TimesheetResponse checkedOut(@Valid CreateTimesheetRequest request);

     TimesheetResponse getMyTimesheet();

     java.util.List<TimesheetResponse> getManagerTimesheet();

     MessageResponseDTO approve(Long id);

     MessageResponseDTO reject(Long id);
}
