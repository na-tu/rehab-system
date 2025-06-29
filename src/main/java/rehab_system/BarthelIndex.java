package rehab_system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BarthelIndex {
  private Long patientId;
  private String yearMonth;
  private Integer average;
}
