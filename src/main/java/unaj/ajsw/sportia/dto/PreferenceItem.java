package unaj.ajsw.sportia.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreferenceItem implements Serializable {
    private String name;
    private String description;
    private Integer quantity;
    private String currencyId;
    private BigDecimal price;
}
