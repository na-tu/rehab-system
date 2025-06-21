package rehab_system;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RehabRecord {
  private Long id;
  private Long patientId;
  private LocalDate date;
  private String content;
}
