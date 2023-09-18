package datum.app.clinic;

import datum.app.clinic.model.Endpoint;
import datum.app.clinic.model.Post;
import datum.app.clinic.model.repositoy.EndpointRepository;
import datum.app.clinic.model.repositoy.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/app")
public class AppController {
    private final AppService appService;

    @GetMapping("get")
    public ResponseEntity<Object> get() {
        return ResponseEntity.ok().body(
                List.of(appService.post(),
                        appService.endpoint(),
                        appService.day()));
    }
    @PutMapping("/post")
    public ResponseEntity<List<Post>> post(@RequestBody List<Post> posts){
        return ResponseEntity.ok(appService.post(posts));
    }
}
