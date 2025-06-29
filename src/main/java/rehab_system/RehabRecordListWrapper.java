package rehab_system.dto;

import java.util.List;
import rehab_system.RehabRecord;

public class RehabRecordListWrapper {
  private List<RehabRecord> rehabRecords;

  public List<RehabRecord> getRehabRecords() {
    return rehabRecords;
  }

  public void setRehabRecords(List<RehabRecord> rehabRecords) {
    this.rehabRecords = rehabRecords;
  }
}