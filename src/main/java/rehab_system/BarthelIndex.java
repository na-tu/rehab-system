package rehab_system;

public class BarthelIndex {
  private int patientId;
  private String yearMonth; // 例 "2025-06"
  private Double average;   // 月の平均値

  // コンストラクタ
  public BarthelIndex() {}

  public BarthelIndex(int patientId, String yearMonth, Double average) {
    this.patientId = patientId;
    this.yearMonth = yearMonth;
    this.average = average;
  }

  // Getter/Setter
  public int getPatientId() {
    return patientId;
  }

  public void setPatientId(int patientId) {
    this.patientId = patientId;
  }

  public String getYearMonth() {
    return yearMonth;
  }

  public void setYearMonth(String yearMonth) {
    this.yearMonth = yearMonth;
  }

  public Double getAverage() {
    return average;
  }

  public void setAverage(Double average) {
    this.average = average;
  }
}
