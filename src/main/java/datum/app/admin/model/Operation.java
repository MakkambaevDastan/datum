package datum.app.admin.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum Operation {
    CREATE,
    READ,
    UPDATE,
    DELETE
}
