package datum.app.clinic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

//
//import jakarta.persistence.Column;
//import jakarta.persistence.Embeddable;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.extern.jackson.Jacksonized;
//
//import java.io.Serializable;
//
//@Data
//@Builder(builderMethodName = "having")
//@NoArgsConstructor
//@AllArgsConstructor
//@Jacksonized
//@Embeddable
//public class EndpointId implements Serializable {
//            @Column(name = "method")
//        private String method;
//        @Column(name = "endpoint")
//        private String endpoint;
//}
@Data
@Builder
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class EndpointId implements Serializable {
//    @Column(name = "method")
    private String method;
//    @Column(name = "endpoint")
    private String endpoint;
}
