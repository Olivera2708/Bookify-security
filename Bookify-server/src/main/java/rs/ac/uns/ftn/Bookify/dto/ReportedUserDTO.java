package rs.ac.uns.ftn.Bookify.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.Bookify.model.ReportedUser;
import rs.ac.uns.ftn.Bookify.model.User;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportedUserDTO {

    private Long id;
    @NotNull
    @NotEmpty
    private String reason;
    private Date created;
    @NotNull
    private Long reportedUser;
    @NotNull
    private Long createdBy;
}



