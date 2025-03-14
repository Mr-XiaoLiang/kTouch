package com.lollipop.resource.sound

import com.lollipop.resource.R

enum class Rider(
    val icon: Int,
    val nameSound: SoundKey
) {

    Agito(R.drawable.ic_agito, SoundKey.NameAgito),
    Blade(R.drawable.ic_blade, SoundKey.NameBlade),
    Build(R.drawable.ic_build, SoundKey.NameBuild),
    Decade(R.drawable.ic_decade, SoundKey.NameDecade),
    Deno(R.drawable.ic_deno, SoundKey.NameDeno),
    Double(R.drawable.ic_double, SoundKey.NameDouble),
    Drive(R.drawable.ic_drive, SoundKey.NameDrive),
    ExAid(R.drawable.ic_exaid, SoundKey.NameExAid),
    Faiz(R.drawable.ic_faiz, SoundKey.NameFaiz),
    Fourze(R.drawable.ic_fourze, SoundKey.NameFourze),
    Gaim(R.drawable.ic_gaim, SoundKey.NameGaim),
    Ghost(R.drawable.ic_ghost, SoundKey.NameGhost),
    Hibiki(R.drawable.ic_hibiki, SoundKey.NameHikibi),
    Kabuto(R.drawable.ic_kabuto, SoundKey.NameKabuto),
    Kiva(R.drawable.ic_kiva, SoundKey.NameKiva),
    Kuuga(R.drawable.ic_kuuga, SoundKey.NameKuuga),
    Ooo(R.drawable.ic_ooo, SoundKey.NameOoo),
    Ryuki(R.drawable.ic_ryuki, SoundKey.NameRyuki),
    Wizard(R.drawable.ic_wizard, SoundKey.NameWizard),
    ZeroOne(R.drawable.ic_zero_one, SoundKey.NameZeroOne),
    Zio(R.drawable.ic_zio, SoundKey.NameZio);

    companion object {
        fun findByIcon(icon: Int): Rider? {
            return entries.find { it.icon == icon }
        }
    }

}