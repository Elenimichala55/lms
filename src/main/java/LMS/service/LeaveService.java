package LMS.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import LMS.repository.LeaveRepository;
import LMS.model.Leave;

@Service
public class LeaveService {
    @Autowired LeaveRepository leaveRepository;

    public List<Leave> getAllLeaves() {
        List<Leave> leaves = new ArrayList<Leave>();
        this.leaveRepository.findAll().forEach(leaves::add);
        return leaves;
    }    

    public Leave getLeaveById(Integer id) {
        return leaveRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Leave not found with id: " + id));
    }    

    public Leave createLeaveForEmployee(Integer employeeId, Leave leave) {
        leave.setEmployeeId(employeeId);
        return leaveRepository.save(leave);
    }

    public Leave saveLeave(Leave leave) {
        return leaveRepository.save(leave);
    }

    public void deleteLeaveById(Integer id) {
        leaveRepository.deleteById(id);
    }

    public void deleteAllLeaves() {
        leaveRepository.deleteAll();
    }

    public List<Leave> getLeavesByStartDate(LocalDate start) {
        return leaveRepository.findByStartDateGreaterThanEqual(start);
    }

    public List<Leave> getLeavesByEndDate(LocalDate end) {
        return leaveRepository.findByEndDateLessThanEqual(end);
    }

    public List<Leave> getLeavesByStartDateAndEndDate(LocalDate start, LocalDate end) {
        return leaveRepository.findByStartDateGreaterThanEqualAndEndDateLessThanEqual(start, end);
    }

    public List<Leave> getLeavesByApproval(Boolean approved) {
        return leaveRepository.findByApproved(approved);
    }

    public List<Leave> getLeavesByStartDateAndApproval(LocalDate start, Boolean approved) {
        return leaveRepository.findByStartDateGreaterThanEqualAndApproved(start, approved);
    }

    public List<Leave> getLeavesByEndDateAndApproval(LocalDate end, Boolean approved) {
        return leaveRepository.findByEndDateLessThanEqualAndApproved(end, approved);
    }

    public List<Leave> getLeavesByFilters(LocalDate start, LocalDate end, Boolean approved) {
        return leaveRepository.findByStartDateGreaterThanEqualAndEndDateLessThanEqualAndApproved(start, end, approved);
    }
}
