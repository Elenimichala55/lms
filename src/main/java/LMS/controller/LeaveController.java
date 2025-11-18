package LMS.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import LMS.model.Leave;
import LMS.service.LeaveService;

@RequestMapping("/api")
@RestController
public class LeaveController {

  @Autowired
  LeaveService leaveService;

  @GetMapping("/leaves")
    public ResponseEntity<List<Leave>> getAllLeaves(
        @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        @RequestParam(required = false) Boolean approved) {
    try {
        List<Leave> result;

        if (startDate != null && endDate != null && approved != null) {
            result = leaveService.getLeavesByFilters(startDate, endDate, approved);
        } else if (startDate != null && endDate != null) {
            result = leaveService.getLeavesByStartDateAndEndDate(startDate, endDate);
        } else if (startDate != null && approved != null) {
            result = leaveService.getLeavesByStartDateAndApproval(startDate, approved);
        } else if (endDate != null && approved != null) {
            result = leaveService.getLeavesByEndDateAndApproval(endDate, approved);
        } else if (startDate != null) {
            result = leaveService.getLeavesByStartDate(startDate);
        } else if (endDate != null) {
            result = leaveService.getLeavesByEndDate(endDate);
        } else if (approved != null) {
            result = leaveService.getLeavesByApproval(approved);
        } else {
            result = leaveService.getAllLeaves();
        }

        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);

    } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/leaves/{id}")
  public ResponseEntity<Leave> getLeaveById(@PathVariable("id") Integer id) {
    Leave leave = leaveService.getLeaveById(id);

    if (leave != null) {
      return new ResponseEntity<>(leave, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/leaves/employees/{eid}")
    public ResponseEntity<Leave> createLeaveForEmployee(
            @PathVariable("eid") Integer employeeId,
            @RequestBody Leave leave) {
        try {
          if (leave.getEndDate() != null && leave.getStartDate() != null &&
                leave.getEndDate().isBefore(leave.getStartDate())) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // 400 Bad Request
            }
            Leave createdLeave = leaveService.createLeaveForEmployee(employeeId, leave);
            return new ResponseEntity<>(createdLeave, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  @PutMapping("/leaves/{id}")
  public ResponseEntity<Leave> updateLeave(@PathVariable("id") Integer id, @RequestBody Leave leave) {
    Leave _leave = leaveService.getLeaveById(id);

    if (_leave != null) {
        if (leave.getEndDate() != null && leave.getStartDate() != null && leave.getEndDate().isBefore(leave.getStartDate())) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // 400 Bad Request
        }

        _leave.setEmployeeId(leave.getEmployeeId ());
        _leave.setDescription(leave.getDescription());
        _leave.setStartDate(leave.getStartDate());
        _leave.setEndDate(leave.getEndDate());
        _leave.setApproved(leave.getApproved());

      return new ResponseEntity<>(leaveService.saveLeave(_leave), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/leaves")
  public ResponseEntity<HttpStatus> deleteAllLeaves() {
    try {
      leaveService.deleteAllLeaves();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/leaves/{id}")
  public ResponseEntity<HttpStatus> deleteLeave(@PathVariable("id") Integer id) {
    try {
      leaveService.deleteLeaveById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
