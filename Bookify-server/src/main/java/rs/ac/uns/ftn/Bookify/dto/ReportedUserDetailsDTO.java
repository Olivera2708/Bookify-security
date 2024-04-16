package rs.ac.uns.ftn.Bookify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.Bookify.model.ReportedUser;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportedUserDetailsDTO {

    private Long id;
    private String reason;
    private Date created;
    private UserDTO reportedUser;
    private UserDTO createdBy;

    public ReportedUserDetailsDTO(ReportedUser report){
        this.id = report.getId();
        this.reason = report.getReason();
        this.created = report.getCreated();
        this.reportedUser = new UserDTO(report.getReportedUser());
        this.createdBy = new UserDTO(report.getCreatedBy());
    }
}
