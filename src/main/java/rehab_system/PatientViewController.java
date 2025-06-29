package rehab_system;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rehab_system.dto.RehabRecordListWrapper;

@Controller
@RequestMapping("/patients")
public class PatientViewController {

  private final PatientService patientService;

  @Autowired
  private RehabRecordService rehabRecordService;


  @Autowired
  public PatientViewController(PatientService patientService) {
    this.patientService = patientService;
  }

  //日付がnullでも登録できるように追加
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    dateFormat.setLenient(false); // 厳密な日付変換
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    // ↑ true = ""(空文字)のときnullを許す
  }

  // 登録フォーム表示
  @GetMapping("/new")
  public String showRegistrationForm(Model model) {
    model.addAttribute("patient", new Patient());
    return "patient_new";
  }

  // 患者登録処理
  @PostMapping("/register")
  public String registerPatient(@ModelAttribute Patient patient,
      RedirectAttributes redirectAttributes) {
    patientService.registerPatient(patient);
    // 成功メッセージをフラッシュ属性に保存
    redirectAttributes.addFlashAttribute("message", "新規登録が成功しました。");
    return "redirect:/patients/list";
  }

  // 一覧表示
  @GetMapping("/list")
  public String listPatients(Model model) {
    model.addAttribute("patients", patientService.getAllPatientsForView());
    return "patient_list";
  }

  // 詳細表示フォーム
  @GetMapping("/{id}")
  public String showPatientDetail(@PathVariable Long id, Model model) {
    PatientDetailDTO detail = patientService.getPatientDetail(id);

    model.addAttribute("patient", detail.getPatient());
    model.addAttribute("rehabRecords", detail.getRehabRecords());
    return "patient_detail";  // 詳細表示用テンプレート名
  }

  // 更新フォーム表示
  @GetMapping("/{id}/edit")
  public String showEditForm(@PathVariable Long id, Model model) {
    //System.out.println("patientId:" +id);
    Patient patient = patientService.getPatientForView(id);// ここにリハビリ記録をセットするコードを追加する
    List<RehabRecord> rehabRecords = rehabRecordService.getRecordsByPatientId(id);

    RehabRecordListWrapper wrapper = new RehabRecordListWrapper();
    wrapper.setRehabRecords(rehabRecords);

    // modelに追加して、Thymeleafで使えるようにする
    model.addAttribute("patient", patient);
    model.addAttribute("rehabRecord", new RehabRecord());//追加用フォームの空オブジェクト
    model.addAttribute("wrapper", wrapper);
    model.addAttribute("rehabRecords", rehabRecords);

    return "patient_update";
  }

  // 患者更新処理
  @PostMapping("/{id}/edit")
  public String updatePatient(@PathVariable Long id, @ModelAttribute Patient patient,
      RedirectAttributes redirectAttributes) {
    patient.setId(id);  // 念のためIDセット
    patientService.updatePatient(patient);
    // 成功メッセージをフラッシュ属性に保存
    redirectAttributes.addFlashAttribute("message", "患者情報を更新しました。");
    return "redirect:/patients/" + id;
  }

  // 削除処理
  @PostMapping("/{id}/delete")
  public String deletePatient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    patientService.deletePatient(id);
    redirectAttributes.addFlashAttribute("message", "患者情報を削除しました。");
    return "redirect:/patients/list";
  }

  //リハビリ記録の新規追加
  @PostMapping("/{id}/rehabRecord/add")
  public String addRehabRecord(@PathVariable Long id, @ModelAttribute RehabRecord rehabRecord,
      RedirectAttributes redirectAttributes) {
    rehabRecord.setPatientId(id);  // 患者IDセット
    rehabRecordService.addRehabRecord(rehabRecord);
    redirectAttributes.addFlashAttribute("message", "リハビリ情報を追加しました。");
    return "redirect:/patients/" + id + "/edit";  // 編集画面に戻る
  }

  //★リハビリ記録の更新処理（編集画面に戻る）
  @PostMapping("/{id}/rehabRecord/update")
  public String updateRehabRecord(@PathVariable("id") Long id,
      @ModelAttribute RehabRecordListWrapper wrapper, RedirectAttributes redirectAttributes) {
    for (RehabRecord record : wrapper.getRehabRecords()) {
      record.setPatientId(id);
      rehabRecordService.updateRehabRecord(record);
    }
    redirectAttributes.addFlashAttribute("message", "リハビリ情報を更新しました。");
    return "redirect:/patients/" + id;  // ← 編集画面に戻る
  }

  // リハビリ記録の削除処理
  @PostMapping("/{patientId}/rehabRecord/{recordId}/delete")
  public String deleteRehabRecord(@PathVariable("patientId") Long patientId,
      @PathVariable("recordId") Long recordId, RedirectAttributes redirectAttributes) {
    rehabRecordService.deleteRehabRecord(recordId);
    redirectAttributes.addFlashAttribute("message", "リハビリ情報を削除しました。");
    return "redirect:/patients/" + patientId + "/edit";
  }

  @GetMapping("/{id}/barthelIndex")
  public String getMonthlyBarthelIndex(@PathVariable("id") Long patientId,
      @RequestParam(value = "yearMonth", required = false) String yearMonth,
      Model model) {

    if (yearMonth == null || yearMonth.isEmpty()) {
      yearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    Double average = rehabRecordService.getMonthlyBarthelAverage(patientId, yearMonth);

    BarthelIndex barthelIndex;
    if (average == null) {
      barthelIndex = new BarthelIndex(patientId, yearMonth, 0);
    } else {
      barthelIndex = new BarthelIndex(patientId, yearMonth, average.intValue());
    }

    RehabRecord newRecord = new RehabRecord();
    newRecord.setPatientId(patientId);

    model.addAttribute("newRecord", newRecord);
    model.addAttribute("yearMonth", yearMonth);
    model.addAttribute("barthelIndex", barthelIndex);

    return "barthelIndex";
  }
}
