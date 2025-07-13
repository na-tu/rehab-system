package rehab_system.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import rehab_system.data.RehabRecord;

@Mapper
public interface RehabRecordRepository {

  List<RehabRecord> findByPatientId(Long patientId);
  void insertRehabRecord(RehabRecord record);

  // 追加：更新用
  void updateRehabRecord(RehabRecord record);

  // 追加：削除用
  void deleteRehabRecord(@Param("id") Long id);
  void deleteByPatientId(Long patientId);

  RehabRecord findById(Long id);
    // Mapで受け取る形に統一
    Double getMonthlyBarthelAverage(@Param("patientId") Long patientId,
        @Param("yearMonth") String yearMonth);

}
