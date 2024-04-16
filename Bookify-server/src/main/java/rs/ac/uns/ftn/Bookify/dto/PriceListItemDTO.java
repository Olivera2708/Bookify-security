package rs.ac.uns.ftn.Bookify.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceListItemDTO {
    @FutureOrPresent
    private Date startDate;
    @FutureOrPresent
    private Date endDate;
    @Min(0)
    private double price;
}
