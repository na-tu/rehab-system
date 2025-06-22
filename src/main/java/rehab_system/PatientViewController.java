package rehab_system;

import java.text.SimpleDateFormat;
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
    public String registerPatient(@ModelAttribute Patient patient) {
      patientService.registerPatient(patient);
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
    System.out.println("rehabRecords: " + rehabRecords);
    patient.setRehabRecords(rehabRecords);
    // modelに追加して、Thymeleafで使えるようにする
    model.addAttribute("patient", patient);
    model.addAttribute("rehabRecord", new RehabRecord());//追加用フォームの空オブジェクト
    model.addAttribute("rehabRecords", rehabRecords);

    return "patient_update";
  }

  // 患者更新処理
  @PostMapping("/{id}/update")
  public String updatePatient(@PathVariable Long id, @ModelAttribute Patient patient) {
    patient.setId(id);  // 念のためIDセット
    patientService.updatePatient(patient);
    return "patient_detail";
  }

  // 削除処理
  @PostMapping("/{id}/delete")
  public String deletePatient(@PathVariable Long id) {
    patientService.deletePatient(id);
    return "redirect:/patients/list";
  }
//リハビリ記録の新規追加
  @PostMapping("/{id}/rehabRecord/add")
  public String addRehabRecord(@PathVariable Long id, @ModelAttribute RehabRecord rehabRecord) {
    rehabRecord.setPatientId(id);  // 患者IDセット
    rehabRecordService.addRehabRecord(rehabRecord);
    return "redirect:/patients/" + id + "/edit";  // 編集画面に戻る
  }

//★リハビリ記録の更新処理（編集画面に戻る）
@PostMapping("/{id}/rehabRecord/update")
public String updateRehabRecord(@PathVariable("id") Long id, @ModelAttribute RehabRecord rehabRecord) {
  rehabRecord.setPatientId(id);
  rehabRecordService.updateRehabRecord(rehabRecord);
  return "redirect:/patients/" + id;  // ← 編集画面に戻る
}
  // リハビリ記録の削除処理
  @PostMapping("/{patientId}/rehabRecord/{recordId}/delete")
  public String deleteRehabRecord(@PathVariable ("patientId")Long patientId,@PathVariable("recordId")Long recordId) {
    rehabRecordService.deleteRehabRecord(recordId);
    return "redirect:/patients/" + patientId + "/edit";
  }

}
