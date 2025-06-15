package rehab_system;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Patient {
  private Long id;
  private String name;
  private Integer age;
  private String gender;
  private String diseaseName;
  private LocalDate startDate;
  private LocalDate endDate;
  private String notes;
}



