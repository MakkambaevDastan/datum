package datum.app.clinic.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.math.BigDecimal;

@Schema
public class MonetaryAmount implements Serializable {
    @JsonProperty("amount")
    @Schema(example = "199.96")
    private BigDecimal amount;
    @JsonProperty("currency")
    @Schema(example = "KGS")
    private CurrencyCode currency;
}
