package LMS.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import LMS.model.Leave;

@Repository
public interface LeaveRepository extends CrudRepository<Leave, Integer> {
    List<Leave> findByApproved(Boolean approved);
    List<Leave> findByEndDateLessThanEqual(LocalDate end);
    List<Leave> findByStartDateGreaterThanEqual(LocalDate start);
    List<Leave> findByEndDateLessThanEqualAndApproved(LocalDate end, Boolean approved);
    List<Leave> findByStartDateGreaterThanEqualAndApproved(LocalDate start, Boolean approved);
    List<Leave> findByStartDateGreaterThanEqualAndEndDateLessThanEqual(LocalDate start, LocalDate end);
    List<Leave> findByStartDateGreaterThanEqualAndEndDateLessThanEqualAndApproved(LocalDate start, LocalDate end, Boolean approved); 
}