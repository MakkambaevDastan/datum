package datum.app.clinic.repositoy;

import datum.app.clinic.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
