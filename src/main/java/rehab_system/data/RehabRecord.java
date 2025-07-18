package rehab_system.data;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RehabRecord {
  private Long id;
  private Long patientId;
  private LocalDate date;
  private String content;
  private Integer barthelIndex;
}
