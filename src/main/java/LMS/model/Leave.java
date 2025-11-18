package LMS.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Table("leaves")
public class Leave {

	@Id
	private Integer id;

	@Column("employee_id")
	private Integer employeeId;

	@Column("description")
	private String description;

	@Column("start_date")
	private LocalDate startDate;

    @Column("end_date")
	private LocalDate endDate;

    @Column("approved")
	private Boolean approved;

	public Leave() {

	}

	public Leave(Integer employeeId, String description, LocalDate startDate, LocalDate endDate, Boolean approved) {
		this.employeeId = employeeId;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.approved = approved;
	}

    // getters
	public Integer getId() {
		return id;
	}

    public Integer getEmployeeId() {
		return employeeId;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}

    public Boolean getApproved() {
		return approved;
	}

	// setters
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

    public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

    public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	
	@Override
	public String toString() {
		return "Leave [id=" + id + ", employeeId=" + employeeId + ", description=" + description + ", startDate=" + startDate
                + ", endDate=" + endDate + ", approved=" + approved + "]";
	}
}
