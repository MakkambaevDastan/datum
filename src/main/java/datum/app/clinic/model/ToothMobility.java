package datum.app.clinic.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true, description = "I, II, III")
public enum ToothMobility {
    I, II, III;
}
