# 🏥 Healthcare Appointment App
**Praktikum PAB Week 3 — Android Intent (Explicit & Implicit)**

---

## 📋 Deskripsi Aplikasi
Aplikasi **Healthcare Appointment** adalah aplikasi Android berbasis Kotlin yang memungkinkan pengguna melihat profil mahasiswa sekaligus mengakses berbagai fitur layanan kesehatan digital. Aplikasi ini dibuat untuk memenuhi tugas Praktikum PAB Week 3 dengan mengimplementasikan **Explicit Intent** dan **>1 Implicit Intent**.

---

## ✅ Checklist Fitur yang Diimplementasikan

| No | Fitur | Jenis Intent | Status |
|----|-------|-------------|--------|
| 1 | Go To Profile (kirim data NIM, Nama, Jurusan, Angkatan, Deskripsi) | **Explicit Intent** | ✅ |
| 2 | Share Profil ke aplikasi lain | **Implicit Intent 1** (ACTION_SEND) | ✅ |
| 3 | Buka GitHub di Browser | **Implicit Intent 2** (ACTION_VIEW) | ✅ *(+nilai)* |
| 4 | Cari Rumah Sakit di Google Maps | **Implicit Intent 3** (ACTION_VIEW geo) | ✅ *(+nilai)* |
| 5 | Hubungi Darurat 119 | **Implicit Intent 4** (ACTION_DIAL) | ✅ *(+nilai)* |
| 6 | Kirim Email dari ProfileActivity | **Implicit Intent 5** (ACTION_SENDTO) | ✅ *(+nilai)* |

---

## 🏗️ Struktur Project

```
HealthcareApp/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/healthcareapp/
│   │   │   ├── MainActivity.kt          ← Activity 1 (Halaman Utama)
│   │   │   └── ProfileActivity.kt       ← Activity 2 (Halaman Profil)
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   ├── activity_main.xml    ← UI Halaman Utama
│   │   │   │   └── activity_profile.xml ← UI Halaman Profil
│   │   │   ├── values/
│   │   │   │   ├── colors.xml
│   │   │   │   ├── strings.xml
│   │   │   │   └── themes.xml
│   │   │   └── drawable/
│   │   │       └── badge_background.xml
│   │   └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
└── settings.gradle
```

---

## 🔑 Penjelasan Intent yang Digunakan

### 1. Explicit Intent (MainActivity → ProfileActivity)
```kotlin
val intent = Intent(this, ProfileActivity::class.java).apply {
    putExtra("NIM", nim)
    putExtra("NAMA", namaLengkap)
    putExtra("JURUSAN", jurusan)
    putExtra("ANGKATAN", angkatan)
    putExtra("DESKRIPSI", deskripsi)
}
startActivity(intent)
```
**Penjelasan:** Explicit Intent digunakan ketika kita tahu persis Activity tujuan. Di sini kita mengirim data mahasiswa dari `MainActivity` ke `ProfileActivity` menggunakan `putExtra()`.

---

### 2. Implicit Intent 1 — Share (ACTION_SEND)
```kotlin
val shareIntent = Intent(Intent.ACTION_SEND).apply {
    type = "text/plain"
    putExtra(Intent.EXTRA_SUBJECT, "Profil Mahasiswa")
    putExtra(Intent.EXTRA_TEXT, shareText)
}
startActivity(Intent.createChooser(shareIntent, "Bagikan profil via..."))
```
**Penjelasan:** Android akan menampilkan chooser dialog sehingga user bisa memilih aplikasi untuk berbagi (WhatsApp, Telegram, Email, dll).

---

### 3. Implicit Intent 2 — GitHub (ACTION_VIEW)
```kotlin
val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/username"))
startActivity(intent)
```
**Penjelasan:** Membuka URL GitHub di browser default perangkat Android.

---

### 4. Implicit Intent 3 — Google Maps (ACTION_VIEW geo)
```kotlin
val geoUri = Uri.parse("geo:0,0?q=rumah+sakit+terdekat")
val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
mapIntent.setPackage("com.google.android.apps.maps")
startActivity(mapIntent)
```
**Penjelasan:** Membuka Google Maps dengan query pencarian rumah sakit terdekat dari lokasi user saat ini.

---

### 5. Implicit Intent 4 — Telepon Darurat (ACTION_DIAL)
```kotlin
val phoneIntent = Intent(Intent.ACTION_DIAL).apply {
    data = Uri.parse("tel:119")
}
startActivity(phoneIntent)
```
**Penjelasan:** Membuka aplikasi telepon default dengan nomor 119 (hotline kesehatan Indonesia) sudah terisi, user tinggal menekan tombol call.

---

### 6. Implicit Intent 5 — Email (ACTION_SENDTO)
```kotlin
val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
    data = Uri.parse("mailto:")
    putExtra(Intent.EXTRA_EMAIL, arrayOf("email@mail.ugm.ac.id"))
    putExtra(Intent.EXTRA_SUBJECT, "Healthcare Appointment")
    putExtra(Intent.EXTRA_TEXT, emailBody)
}
startActivity(emailIntent)
```
**Penjelasan:** Membuka aplikasi email default dengan field To, Subject, dan Body sudah terisi otomatis.

---

## 🛠️ Cara Setup di Android Studio

1. **Clone/download** repository ini
2. Buka **Android Studio** → `File` → `Open` → pilih folder `HealthcareApp`
3. Tunggu Gradle sync selesai
4. **Ubah data mahasiswa** di `MainActivity.kt`:
   ```kotlin
   private val nim = "22/XXXXXX/TK/XXXXX"      // ← Ganti dengan NIM kamu
   private val namaLengkap = "Nama Kamu"          // ← Ganti nama kamu
   private val jurusan = "Teknik Informatika"
   private val angkatan = "2022"
   private val deskripsi = "Deskripsi diri kamu..." 
   private val githubUrl = "https://github.com/username_kamu" // ← GitHub kamu
   ```
5. Jalankan di **emulator** atau **device fisik** (min API 24 / Android 7.0)

---

## 📱 Tampilan Aplikasi

### MainActivity (Halaman Utama)
- Header biru dengan ikon 🏥
- Card informasi mahasiswa (NIM, Nama, Jurusan, Angkatan)
- Tombol **Go To Profile** (Explicit Intent)
- Tombol **Share Profil** (Implicit Intent - ACTION_SEND)
- Tombol **Go To My Github** (Implicit Intent - ACTION_VIEW)
- Tombol **Cari Rumah Sakit** (Implicit Intent - Maps)
- Tombol **Hubungi Darurat 119** (Implicit Intent - Dial)

### ProfileActivity (Halaman Profil)
- Header hijau dengan avatar dan badge
- Menampilkan data yang diterima dari MainActivity via Intent
- Card detail akademik
- Card deskripsi diri
- Tombol **Share Profil** (Implicit Intent - ACTION_SEND)
- Tombol **Kirim Email** (Implicit Intent - ACTION_SENDTO)
- Tombol kembali

---

## 📦 Dependencies

```gradle
implementation 'androidx.core:core-ktx:1.12.0'
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'com.google.android.material:material:1.11.0'
implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
implementation 'androidx.cardview:cardview:1.0.0'
```

---

## 🎯 Kesimpulan

Aplikasi ini berhasil mengimplementasikan:
- **2 Activity**: `MainActivity` dan `ProfileActivity`
- **1 Explicit Intent**: Navigasi dari MainActivity ke ProfileActivity dengan mengirim data
- **5 Implicit Intent**: Share, Browser/GitHub, Google Maps, Telepon, dan Email

Melalui praktikum ini, kita memahami perbedaan antara **Explicit Intent** (tujuan spesifik, kita tentukan class-nya) dan **Implicit Intent** (memberikan action, sistem yang menentukan aplikasi mana yang menanganinya).

---

*Praktikum PAB Week 3 — Universitas Gadjah Mada*
