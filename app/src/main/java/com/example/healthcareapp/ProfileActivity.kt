package com.example.healthcareapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Ambil data dari Intent (Explicit Intent - dikirim dari MainActivity)
        val nim = intent.getStringExtra("NIM") ?: "N/A"
        val nama = intent.getStringExtra("NAMA") ?: "N/A"
        val jurusan = intent.getStringExtra("JURUSAN") ?: "N/A"
        val angkatan = intent.getStringExtra("ANGKATAN") ?: "N/A"
        val deskripsi = intent.getStringExtra("DESKRIPSI") ?: "N/A"
        val github = intent.getStringExtra("GITHUB") ?: ""

        // Tampilkan data ke UI
        findViewById<TextView>(R.id.tvProfileNim).text = nim
        findViewById<TextView>(R.id.tvProfileNama).text = nama
        findViewById<TextView>(R.id.tvProfileJurusan).text = jurusan
        findViewById<TextView>(R.id.tvProfileAngkatan).text = angkatan
        findViewById<TextView>(R.id.tvProfileDeskripsi).text = deskripsi

        // Tombol kembali
        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // =============================================
        // IMPLICIT INTENT: Kirim Email ke profil
        // =============================================
        findViewById<Button>(R.id.btnSendEmail).setOnClickListener {
            val emailBody = """
                Halo $nama,
                
                Saya ingin menghubungi Anda terkait appointment kesehatan.
                
                Data Anda:
                NIM     : $nim
                Jurusan : $jurusan
                Angkatan: $angkatan
                
                Harap balas email ini untuk konfirmasi appointment.
                
                Terima kasih.
            """.trimIndent()

            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("$nim@mail.ugm.ac.id"))
                putExtra(Intent.EXTRA_SUBJECT, "Healthcare Appointment - $nama")
                putExtra(Intent.EXTRA_TEXT, emailBody)
            }

            if (emailIntent.resolveActivity(packageManager) != null) {
                startActivity(emailIntent)
            } else {
                // Fallback: gunakan ACTION_SEND jika tidak ada mail client
                val fallbackIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "message/rfc822"
                    putExtra(Intent.EXTRA_SUBJECT, "Healthcare Appointment - $nama")
                    putExtra(Intent.EXTRA_TEXT, emailBody)
                }
                try {
                    startActivity(Intent.createChooser(fallbackIntent, "Kirim email via..."))
                } catch (e: Exception) {
                    Toast.makeText(this, "Tidak ada aplikasi email yang tersedia", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Tombol Share dari Profile
        // =============================================
        // IMPLICIT INTENT 1: Share via aplikasi lain
        // =============================================
        findViewById<Button>(R.id.btnShare).setOnClickListener {
            val shareText = """
                🏥 Healthcare Appointment App
                ━━━━━━━━━━━━━━━━━━
                👤 Nama   : $nama
                🎓 NIM    : $nim
                📚 Jurusan: $jurusan
                📅 Angkatan: $angkatan
                
                📝 Tentang Saya:
                $deskripsi
                
                #HealthcareApp
            """.trimIndent()

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Profil Mahasiswa - $nama")
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            startActivity(Intent.createChooser(shareIntent, "Bagikan profil via..."))
        }

        // =============================================
        // IMPLICIT INTENT 2: Buka GitHub di Browser
        // =============================================
        findViewById<Button>(R.id.btnGithub).setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(github)
                    addCategory(Intent.CATEGORY_BROWSABLE)
                }
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Gagal membuka browser", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
