package com.lollipop.resource.sound

import com.lollipop.resource.R

enum class SoundKey(
    val resId: Int,
    val timeMillis: Long
) {

    DeviceBoot(R.raw.s_device_boot, 4000),
    DeviceBoot21(R.raw.s_device_boot_21, 6000),
    DeviceExit21(R.raw.s_device_exit_21, 2000),
    DeviceSpace(R.raw.s_device_space, 1000),

    NameAgito(R.raw.s_name_agito, 1000),
    NameBlade(R.raw.s_name_blade, 1000),
    NameBuild(R.raw.s_name_build, 1000),
    NameDecade(R.raw.s_name_decade, 2000),
    NameDeno(R.raw.s_name_deno, 1000),
    NameDouble(R.raw.s_name_double, 1000),
    NameDrive(R.raw.s_name_drive, 1000),
    NameExAid(R.raw.s_name_exaid, 1000),
    NameFaiz(R.raw.s_name_faiz, 1000),
    NameFourze(R.raw.s_name_fourze, 1000),
    NameGaim(R.raw.s_name_gaim, 1000),
    NameGhost(R.raw.s_name_ghost, 1000),
    NameHikibi(R.raw.s_name_hibiki, 1000),
    NameKabuto(R.raw.s_name_kabuto, 1000),
    NameKiva(R.raw.s_name_kiva, 1000),
    NameKuuga(R.raw.s_name_kuuga, 1000),
    NameOoo(R.raw.s_name_ooo, 1000),
    NameRyuki(R.raw.s_name_ryuki, 1000),
    NameWizard(R.raw.s_name_wizard, 1000),
    NameZeroOne(R.raw.s_name_zero_one, 1000),
    NameZio(R.raw.s_name_zio, 1000),

    HeiseiDcdFinally(R.raw.s_heisei_dcd_finally, 16000),
    HeiseiDcdFinally21(R.raw.s_heisei_dcd_finally_21, 14000),

    SkillAgito(R.raw.s_skill_agito, 10000),
    SkillBlade(R.raw.s_skill_blade, 10000),
    SkillDecade(R.raw.s_skill_dcd, 10000),
    SkillBuild(R.raw.s_skill_build, 10000),
    SkillDeno(R.raw.s_skill_deno, 10000),
    SkillDouble(R.raw.s_skill_double, 13000),
    SkillDrive(R.raw.s_skill_drive, 10000),
    SkillExAid(R.raw.s_skill_exaid, 9000),
    SkillFaiz(R.raw.s_skill_faiz, 10000),
    SkillFourze(R.raw.s_skill_fourze, 11000),
    SkillGaim(R.raw.s_skill_gaim, 11000),
    SkillGhost(R.raw.s_skill_ghost, 11000),
    SkillHikibi(R.raw.s_skill_hibiki, 9000),
    SkillKabuto(R.raw.s_skill_kabuto, 9000),
    SkillKiva(R.raw.s_skill_kiva, 10000),
    SkillKuuga(R.raw.s_skill_kuuga, 11000),
    SkillOoo(R.raw.s_skill_ooo, 10000),
    SkillRyuki(R.raw.s_skill_ryuki, 10000),
    SkillWizard(R.raw.s_skill_wizard, 11000),
    SkillZeroOne(R.raw.s_skill_zero_one, 9000),
    SkillZio(R.raw.s_skill_zio, 11000);

}