package rs.ac.uns.ftn.Bookify.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.Bookify.dto.PriceListItemDTO;
import rs.ac.uns.ftn.Bookify.model.Availability;
import rs.ac.uns.ftn.Bookify.model.PricelistItem;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@Component
public class PriceListItemDTOMapper {
    private static ModelMapper modelMapper;

    public PriceListItemDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static PricelistItem fromDTOtoPriceListItem(PriceListItemDTO dto){
        PricelistItem item = new PricelistItem();
        item.setStartDate(dto.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        item.setEndDate(dto.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        item.setPrice(dto.getPrice());
        return item;
    }

    public static PriceListItemDTO fromPriceListItemtoDTO(PricelistItem dto){
        return modelMapper.map(dto, PriceListItemDTO.class);
    }

    public static Collection<PriceListItemDTO> fromPriceListItemtoDTO(Collection<PricelistItem> dto){
        Collection<PriceListItemDTO> priceListItemDTOS = new ArrayList<>();
        for(PricelistItem plt : dto){
            PriceListItemDTO itemDTO = new PriceListItemDTO(Date.from(plt.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    Date.from(plt.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant()), plt.getPrice());
            priceListItemDTOS.add(itemDTO);
        }
        return priceListItemDTOS;
    }

    public static Availability fromDTOtoAvailability(PriceListItemDTO dto){
        Availability item = new Availability();
        item.setStartDate(dto.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        item.setEndDate(dto.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        return item;
    }

    public static PriceListItemDTO fromAvailabilitytoDTO(Availability dto){
        return modelMapper.map(dto, PriceListItemDTO.class);
    }
}
