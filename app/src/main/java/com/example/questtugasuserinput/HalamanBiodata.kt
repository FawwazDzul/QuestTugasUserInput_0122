package com.example.questtugasuserinput

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun BannerGeser(modifier: Modifier = Modifier) {
    val gambarBanner = listOf(
        R.drawable.banner_1,
        R.drawable.banner_1,
        R.drawable.banner_1,
        R.drawable.banner_1
    )
    val pagerState = rememberPagerState(pageCount = { gambarBanner.size })
    Box(modifier = modifier) {
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { halaman ->
            Image(
                painter = painterResource(gambarBanner[halaman]),
                contentDescription = stringResource(R.string.deskripsi_banner),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(dimensionResource(R.dimen.titik_padding)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.jarak_titik))
        ) {
            repeat(pagerState.pageCount) { indeks ->
                val aktif = pagerState.currentPage == indeks
                Box(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.ukuran_titik))
                        .clip(CircleShape)
                        .background(
                            colorResource(
                                if (aktif) R.color.indikator_aktif else R.color.indikator_nonaktif
                            )
                        )
                )
            }
        }
    }
}

@Composable
fun KartuBiodata(modifier: Modifier = Modifier) {
    var namaDepan by rememberSaveable { mutableStateOf("") }
    var namaBelakang by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var tanggalLahir by rememberSaveable { mutableStateOf("") }
    var pengalaman by rememberSaveable { mutableStateOf("") }
    val skillTerpilih = remember { mutableStateListOf<Int>() }

    val daftarPengalaman = stringArrayResource(R.array.pilihan_pengalaman).toList()
    val daftarSkill = stringArrayResource(R.array.daftar_skill)
    val polaTanggal = stringResource(R.string.format_tanggal)
    val konteks = LocalContext.current
    val pesanBerhasil = stringResource(R.string.pesan_berhasil, namaDepan)

    val emailSalah = email.isNotBlank() && !emailValid(email)
    val formLengkap = namaDepan.isNotBlank() &&
            namaBelakang.isNotBlank() &&
            email.isNotBlank() && !emailSalah &&
            tanggalLahir.isNotBlank() &&
            pengalaman.isNotBlank() &&
            skillTerpilih.isNotEmpty()

    val bentuk = RoundedCornerShape(
        topStart = dimensionResource(R.dimen.radius_kartu_atas),
        topEnd = dimensionResource(R.dimen.radius_kartu_atas),
        bottomStart = dimensionResource(R.dimen.radius_kartu_bawah),
        bottomEnd = dimensionResource(R.dimen.radius_kartu_bawah)
    )

    // Box ini "mengapung" di atas konten Scaffold: diberi bayangan dan bentuk sendiri.
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(dimensionResource(R.dimen.elevasi_kartu), bentuk)
            .background(colorResource(R.color.latar_kartu), bentuk)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = dimensionResource(R.dimen.padding_kartu_horizontal),
                    vertical = dimensionResource(R.dimen.padding_kartu_vertikal)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.jarak_antar_baris))
        ) {
            Text(
                text = stringResource(R.string.judul_form),
                color = colorResource(R.color.ungu_utama),
                fontSize = spResource(R.dimen.ukuran_judul),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.subjudul_form),
                color = colorResource(R.color.teks_abu),
                fontSize = spResource(R.dimen.ukuran_subjudul)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.jarak_antar_isian))
            ) {
                FieldBiodata(
                    value = namaDepan,
                    onValueChange = { namaDepan = it },
                    hint = stringResource(R.string.hint_nama_depan),
                    modifier = Modifier.weight(1f)
                )
                FieldBiodata(
                    value = namaBelakang,
                    onValueChange = { namaBelakang = it },
                    hint = stringResource(R.string.hint_nama_belakang),
                    modifier = Modifier.weight(1f)
                )
            }

            FieldBiodata(
                value = email,
                onValueChange = { email = it },
                hint = stringResource(R.string.hint_email),
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Email,
                isError = emailSalah,
                pesanError = if (emailSalah) stringResource(R.string.error_email) else null
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.jarak_antar_isian))
            ) {
                FieldTanggal(
                    nilai = tanggalLahir,
                    hint = stringResource(R.string.hint_tgl_lahir),
                    onTanggalDipilih = { tanggalLahir = formatTanggal(it, polaTanggal) },
                    modifier = Modifier.weight(1f)
                )
                FieldDropdown(
                    nilai = pengalaman,
                    hint = stringResource(R.string.hint_experience),
                    pilihan = daftarPengalaman,
                    onPilih = { pengalaman = it },
                    modifier = Modifier.weight(1f)
                )
            }

            Text(
                text = stringResource(R.string.label_skill),
                color = colorResource(R.color.teks_gelap),
                fontSize = spResource(R.dimen.ukuran_label),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                daftarSkill.forEachIndexed { indeks, skill ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = indeks in skillTerpilih,
                            onCheckedChange = { dicentang ->
                                if (dicentang) skillTerpilih.add(indeks) else skillTerpilih.remove(indeks)
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = colorResource(R.color.ungu_utama),
                                uncheckedColor = colorResource(R.color.garis_isian)
                            )
                        )
                        Text(
                            text = skill,
                            color = colorResource(R.color.teks_gelap),
                            fontSize = spResource(R.dimen.ukuran_skill)
                        )
                    }
                }
            }

            Button(
                onClick = { Toast.makeText(konteks, pesanBerhasil, Toast.LENGTH_SHORT).show() },
                enabled = formLengkap,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.tinggi_tombol)),
                shape = RoundedCornerShape(dimensionResource(R.dimen.radius_tombol)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.ungu_utama),
                    contentColor = colorResource(R.color.teks_tombol_aktif),
                    disabledContainerColor = colorResource(R.color.tombol_nonaktif),
                    disabledContentColor = colorResource(R.color.teks_tombol_nonaktif)
                )
            ) {
                Text(
                    text = stringResource(R.string.tombol_proceed),
                    fontSize = spResource(R.dimen.ukuran_tombol),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun HalamanBiodata(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        BannerGeser(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.tinggi_banner))
        )
        KartuBiodata(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = dimensionResource(R.dimen.margin_bawah_kartu))
        )
    }
}