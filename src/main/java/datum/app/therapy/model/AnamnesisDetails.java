package datum.app.therapy.model;

import datum.config.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
public class AnamnesisDetails  extends Auditable<String> implements Serializable {
    private String name;
    private String description;
    private String date;
}
