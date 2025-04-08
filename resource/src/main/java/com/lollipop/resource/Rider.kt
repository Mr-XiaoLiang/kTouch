package com.lollipop.resource

import com.lollipop.resource.sound.SoundKey

enum class Rider(
    val icon: Int,
    val nameSound: SoundKey,
    val skillSound: SoundKey
) {

    Agito(R.drawable.ic_agito, SoundKey.NameAgito, SoundKey.SkillAgito),
    Blade(R.drawable.ic_blade, SoundKey.NameBlade, SoundKey.SkillBlade),
    Build(R.drawable.ic_build, SoundKey.NameBuild, SoundKey.SkillBuild),
    Decade(R.drawable.ic_decade, SoundKey.NameDecade, SoundKey.SkillDecade),
    Deno(R.drawable.ic_deno, SoundKey.NameDeno, SoundKey.SkillDeno),
    Double(R.drawable.ic_double, SoundKey.NameDouble, SoundKey.SkillDouble),
    Drive(R.drawable.ic_drive, SoundKey.NameDrive, SoundKey.SkillDrive),
    ExAid(R.drawable.ic_exaid, SoundKey.NameExAid, SoundKey.SkillExAid),
    Faiz(R.drawable.ic_faiz, SoundKey.NameFaiz, SoundKey.SkillFaiz),
    Fourze(R.drawable.ic_fourze, SoundKey.NameFourze, SoundKey.SkillFourze),
    Gaim(R.drawable.ic_gaim, SoundKey.NameGaim, SoundKey.SkillGaim),
    Ghost(R.drawable.ic_ghost, SoundKey.NameGhost, SoundKey.SkillGhost),
    Hibiki(R.drawable.ic_hibiki, SoundKey.NameHikibi, SoundKey.SkillHikibi),
    Kabuto(R.drawable.ic_kabuto, SoundKey.NameKabuto, SoundKey.SkillKabuto),
    Kiva(R.drawable.ic_kiva, SoundKey.NameKiva, SoundKey.SkillKiva),
    Kuuga(R.drawable.ic_kuuga, SoundKey.NameKuuga, SoundKey.SkillKuuga),
    Ooo(R.drawable.ic_ooo, SoundKey.NameOoo, SoundKey.SkillOoo),
    Ryuki(R.drawable.ic_ryuki, SoundKey.NameRyuki, SoundKey.SkillRyuki),
    Wizard(R.drawable.ic_wizard, SoundKey.NameWizard, SoundKey.SkillWizard),
    ZeroOne(R.drawable.ic_zero_one, SoundKey.NameZeroOne, SoundKey.SkillZeroOne),
    Zio(R.drawable.ic_zio, SoundKey.NameZio, SoundKey.SkillZio);

    companion object {
        fun findByIcon(icon: Int): Rider? {
            return entries.find { it.icon == icon }
        }
    }

}