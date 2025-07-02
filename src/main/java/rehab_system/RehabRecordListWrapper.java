package rehab_system.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import rehab_system.data.RehabRecord;

@Setter
@Getter
public class RehabRecordListWrapper {
  private List<RehabRecord> rehabRecords;

}