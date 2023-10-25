package datum.config;

import datum.Main;
import datum.app.clinic.model.Privilege;
import datum.app.clinic.service.PrivilegeService;
import datum.config.exception.ExceptionApp;
import datum.config.exception.Message;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ClinicInterceptor implements HandlerInterceptor {
    private final PrivilegeService privilegeService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        var map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Privilege privilege = privilegeService.getPrivilege(
                request,
                Main.parseLong(map.get("clinicId")),
                Main.parseLong(map.get("employeeId"))
        );
        if(privilege.getBool())
            return true;
        else
            throw new ExceptionApp(403, Message.FORBIDDEN);
    }
}
