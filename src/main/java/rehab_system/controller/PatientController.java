package rehab_system.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rehab_system.data.Patient;
import rehab_system.Patient_RehabRecordsDTO;
import rehab_system.data.RehabRecord;
import rehab_system.service.PatientService;
import rehab_system.service.RehabRecordService;

@Controller
@RequestMapping("/patients")
public class PatientController {

  private final PatientService patientService;

  @Autowired
  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }
  @Autowired
  private RehabRecordService rehabRecordService;


  // 日付がnullでも登録できるように設定
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
  }

  // 新規患者登録フォーム表示
  @GetMapping("/new")
  public String showRegistrationForm(Model model) {
    model.addAttribute("patient", new Patient());
    return "patient_new";
  }

  // 患者登録処理
  @PostMapping("/register")
  public String registerPatient(@ModelAttribute Patient patient, RedirectAttributes redirectAttributes) {
    patientService.registerPatient(patient);
    redirectAttributes.addFlashAttribute("message", "新規登録が成功しました。");
    return "redirect:/patients/list";
  }

  // 患者一覧表示
  @GetMapping("/list")
  public String listPatients(Model model) {
    model.addAttribute("patients", patientService.getAllPatientsForView());
    return "patient_list";
  }

  // 患者情報編集フォーム表示（患者情報＋リハビリ情報）
  @GetMapping("/{id}/edit")
  public String showEditForm(@PathVariable Long id, Model model) {
    Patient patient = patientService.getPatientForView(id);
    List<RehabRecord> rehabRecords = rehabRecordService.getRecordsByPatientId(id);

    rehab_system.dto.RehabRecordListWrapper wrapper = new rehab_system.dto.RehabRecordListWrapper();
    wrapper.setRehabRecords(rehabRecords);

    model.addAttribute("patient", patient);
    model.addAttribute("rehabRecords", rehabRecords);      // 表示用
    model.addAttribute("rehabRecord", new RehabRecord());  // 追加用
    model.addAttribute("wrapper", wrapper);                // 一括更新用

    return "patient_update";
  }
  // 患者情報更新処理
  @GetMapping("/{id}")
  public String showPatientDetail(@PathVariable Long id, Model model) {
    Patient_RehabRecordsDTO detail = patientService.getPatientDetail(id);
    model.addAttribute("patient", detail.getPatient());
    model.addAttribute("rehabRecords", detail.getRehabRecords());
    return "patient_detail"; // patient_detail.html に対応
  }

  @PostMapping("/{id}/edit")
  public String updatePatient(@PathVariable Long id, @ModelAttribute Patient patient,
      RedirectAttributes redirectAttributes) {
    patient.setId(id);
    patientService.updatePatient(patient);
    redirectAttributes.addFlashAttribute("message", "患者情報を更新しました。");
    return "redirect:/patients/" + id;
  }

  // 患者情報削除処理
  @PostMapping("/{id}/delete")
  public String deletePatient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    patientService.deletePatient(id);
    redirectAttributes.addFlashAttribute("message", "患者情報を削除しました。");
    return "redirect:/patients/list";
  }
}