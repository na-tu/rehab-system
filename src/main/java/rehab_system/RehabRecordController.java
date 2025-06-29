package rehab_system;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import rehab_system.dto.RehabRecordListWrapper;

@Controller
@RequestMapping("/patients/{id}/rehabRecord")
public class RehabRecordController {

  private final RehabRecordService rehabRecordService;

  @Autowired
  public RehabRecordController(RehabRecordService rehabRecordService) {
    this.rehabRecordService = rehabRecordService;
  }

  // 新規追加
  @PostMapping("/add")
  public String addRehabRecord(@PathVariable("id") Long id,
      @ModelAttribute RehabRecord rehabRecord,
      RedirectAttributes redirectAttributes) {
    rehabRecord.setPatientId(id);
    rehabRecordService.addRehabRecord(rehabRecord);
    redirectAttributes.addFlashAttribute("message", "リハビリ情報を追加しました。");
    return "redirect:/patients/" + id + "/edit";
  }

  // 複数リハビリ記録更新
  @PostMapping("/update")
  public String updateRehabRecord(@PathVariable("id") Long id,
      @ModelAttribute RehabRecordListWrapper wrapper,
      RedirectAttributes redirectAttributes) {
    for (RehabRecord record : wrapper.getRehabRecords()) {
      record.setPatientId(id);
      rehabRecordService.updateRehabRecord(record);
    }
    redirectAttributes.addFlashAttribute("message", "リハビリ情報を更新しました。");
    return "redirect:/patients/" + id;
  }

  // 削除処理
  @PostMapping("/{recordId}/delete")
  public String deleteRehabRecord(@PathVariable("id") Long id,
      @PathVariable Long recordId,
      RedirectAttributes redirectAttributes) {
    rehabRecordService.deleteRehabRecord(recordId);
    redirectAttributes.addFlashAttribute("message", "リハビリ情報を削除しました。");
    return "redirect:/patients/" + id + "/edit";
  }

  // Barthel Index 月別平均表示
  @GetMapping("/barthelIndex")
  public String getMonthlyBarthelIndex(@PathVariable("id") Long id,
      @RequestParam(value = "yearMonth", required = false) String yearMonth,
      Model model) {
    if (yearMonth == null || yearMonth.isEmpty()) {
      yearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    Double average = rehabRecordService.getMonthlyBarthelAverage(id, yearMonth);
    BarthelIndex barthelIndex = new BarthelIndex(id, yearMonth,
        average == null ? 0 : average.intValue());

    RehabRecord newRecord = new RehabRecord();
    newRecord.setPatientId(id);

    model.addAttribute("newRecord", newRecord);
    model.addAttribute("yearMonth", yearMonth);
    model.addAttribute("barthelIndex", barthelIndex);

    return "barthelIndex";
  }
}