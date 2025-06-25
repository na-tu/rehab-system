package rehab_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BarthelIndexService {

  @Autowired
  private RehabRecordRepository rehabRecordRepository; // 正しいMapper名

  public BarthelIndex calculateMonthlyBarthelIndex(Long patientId, String yearMonth) {
    Double average = rehabRecordRepository.getMonthlyBarthelAverage(patientId, yearMonth);
    return new BarthelIndex(patientId.intValue(), yearMonth, average);
  }
}
