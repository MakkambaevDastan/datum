package datum.authenticate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;

@Schema(enumAsRef = true)
@RequiredArgsConstructor
public enum Role {

    USER,ADMIN,ROOT;

}
