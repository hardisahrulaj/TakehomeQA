# Automation-Katalon — Student Form DDT

Proyek automation testing untuk https://demoqa.com/automation-practice-form menggunakan Katalon Studio dengan metode Data Driven Test (DDT).

---

## Requirements

| Komponen | Versi |
|----------|-------|
| Katalon Studio | v9.6.0 (build 217) |
| Java (OpenJDK Temurin) | 17.0.14 (64-bit) |
| Browser | Google Chrome (terbaru) |
| OS | Windows 11 (x64) |

---

## Struktur Project

```
Automation-Katalon/
├── Data Files/
│   ├── Student Data Scenario.csv   ← data DDT (9 baris test case)
│   ├── DDT Student Scenario.dat    ← konfigurasi data file Katalon
│   └── student_photo.jpg           ← file foto untuk upload
├── Scripts/
│   └── TC_StudentForm_DDT/         ← script utama DDT
├── Test Cases/
│   └── TC_StudentForm_DDT.tc
├── Test Suites/
│   └── StudentFormDDTSuite.ts      ← suite untuk menjalankan DDT
├── Reports/                        ← hasil report otomatis
└── Test Case Design - Student Form.xlsx  ← dokumen desain test case
```

---

## Test Case Design

| TC ID | Judul | Jenis |
|-------|-------|-------|
| TC001_ValidSubmit | Submit form dengan data lengkap & valid | Positif |
| TC002_NoLastName | Submit tanpa Last Name | Negatif |
| TC003_NoFirstName | Submit tanpa First Name | Negatif |
| TC004_InvalidEmail | Submit dengan format email tidak valid | Negatif |
| TC005_NoGender | Submit tanpa memilih Gender | Negatif |
| TC006_NoMobile | Submit tanpa Mobile Number | Negatif |
| TC007_MaxlengthMobile | Verifikasi maxlength field Mobile = 10 | Negatif |
| TC008_InvalidMobileAlpha | Submit dengan Mobile berisi huruf | Negatif |
| TC009_EmptyAllFields | Submit dengan semua field kosong | Negatif |

Detail step, expected result, dan actual result tersedia di file `Test Case Design - Student Form.xlsx`.

---

## Cara Menjalankan Test

1. Buka **Katalon Studio v9.6.0**
2. Pilih **Open Project** → arahkan ke folder `Automation-Katalon`
3. Di panel **Test Explorer**, buka folder **Test Suites**
4. Klik dua kali file **`StudentFormDDTSuite`**
5. Pastikan checkbox **Run** pada `TC_StudentForm_DDT` sudah dicentang
6. Klik tanda panah hitam (▼) di samping tombol **Run** (▶)
7. Pilih browser **Chrome**
8. Test akan berjalan otomatis untuk semua 9 baris data dari `Student Data Scenario.csv`

---

## Cara Generate Report

Report otomatis tersimpan di folder `Reports/` setelah test selesai.

### Export PDF
1. Klik tab **Test Explorer** (ikon file hijau)
2. Buka folder **Reports** → pilih folder `YYYYMMDD_HHMMSS` terbaru
3. Buka folder **StudentFormDDTSuite** → klik file `YYYYMMDD_HHMMSS`
4. Klik tombol **Export Report** (kanan atas)
5. Pilih format **PDF** → pilih folder tujuan → klik **Select Folder**

### Report XML (JUnit)
File `JUnit_Report.xml` sudah otomatis tersedia di dalam folder report:
```
Reports/YYYYMMDD_HHMMSS/StudentFormDDTSuite/YYYYMMDD_HHMMSS/JUnit_Report.xml
```
