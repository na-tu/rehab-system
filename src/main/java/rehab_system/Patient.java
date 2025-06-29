package rehab_system;

import java.util.List;
import lombok.Data;
import java.sql.Date;

@Data
public class Patient {
  private Long id;
  private String name;
  private Integer age;
  private String gender;
  private String diseaseName;
  private Date startDate;
  private Date endDate;
  private String notes;
  private List<RehabRecord> rehabRecords;

}



