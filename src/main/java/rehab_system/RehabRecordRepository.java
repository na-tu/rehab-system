package rehab_system;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RehabRecordRepository {

  List<RehabRecord> findByPatientId(Long patientId);
  void insertRehabRecord(RehabRecord record);

  // 追加：更新用
  void updateRehabRecord(RehabRecord record);

  // 追加：削除用
  void deleteRehabRecord(@Param("id") Long id);

  RehabRecord findById(Long id);
    // Mapで受け取る形に統一
    Double getMonthlyBarthelAverage(@Param("patientId") Long patientId,
        @Param("yearMonth") String yearMonth);

}
