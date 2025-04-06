package com.lollipop.resource.sound

import com.lollipop.resource.R

enum class SoundKey(
    val resId: Int,
    val timeMillis: Long
) {

    DeviceBoot(R.raw.s_device_boot, 3856),
    DeviceBoot21(R.raw.s_device_boot_21, 4000),
    DeviceExit21(R.raw.s_device_exit_21, 1303),
    DeviceSpace(R.raw.s_device_space, 248),
    DeviceReady(R.raw.s_device_ready, 4178),

    NameAgito(R.raw.s_name_agito, 915),
    NameBlade(R.raw.s_name_blade, 946),
    NameBuild(R.raw.s_name_build, 1011),
    NameDecade(R.raw.s_name_decade, 970),
    NameDeno(R.raw.s_name_deno, 1012),
    NameDouble(R.raw.s_name_double, 972),
    NameDrive(R.raw.s_name_drive, 1091),
    NameExAid(R.raw.s_name_exaid, 1087),
    NameFaiz(R.raw.s_name_faiz, 895),
    NameFourze(R.raw.s_name_fourze, 959),
    NameGaim(R.raw.s_name_gaim, 969),
    NameGhost(R.raw.s_name_ghost, 1048),
    NameHikibi(R.raw.s_name_hibiki, 938),
    NameKabuto(R.raw.s_name_kabuto, 930),
    NameKiva(R.raw.s_name_kiva, 801),
    NameKuuga(R.raw.s_name_kuuga, 932),
    NameOoo(R.raw.s_name_ooo, 961),
    NameRyuki(R.raw.s_name_ryuki, 900),
    NameWizard(R.raw.s_name_wizard, 1048),
    NameZeroOne(R.raw.s_name_zero_one, 1534),
    NameZio(R.raw.s_name_zio, 1332),

    HeiseiDcdFinally(R.raw.s_heisei_dcd_finally, 14073),
    HeiseiDcdFinally21(R.raw.s_heisei_dcd_finally_21, 14709),

    SkillAgito(R.raw.s_skill_agito, 9389),
    SkillBlade(R.raw.s_skill_blade, 9006),
    SkillBuild(R.raw.s_skill_build, 9686),
    SkillDecade(R.raw.s_skill_dcd, 9336),
    SkillDeno(R.raw.s_skill_deno, 9212),
    SkillDouble(R.raw.s_skill_double, 10918),
    SkillDrive(R.raw.s_skill_drive, 9638),
    SkillExAid(R.raw.s_skill_exaid, 9671),
    SkillFaiz(R.raw.s_skill_faiz, 9359),
    SkillFourze(R.raw.s_skill_fourze, 9670),
    SkillGaim(R.raw.s_skill_gaim, 9562),
    SkillGhost(R.raw.s_skill_ghost, 9838),
    SkillHikibi(R.raw.s_skill_hibiki, 9304),
    SkillKabuto(R.raw.s_skill_kabuto, 9247),
    SkillKiva(R.raw.s_skill_kiva, 9188),
    SkillKuuga(R.raw.s_skill_kuuga, 9377),
    SkillOoo(R.raw.s_skill_ooo, 9296),
    SkillRyuki(R.raw.s_skill_ryuki, 9393),
    SkillWizard(R.raw.s_skill_wizard, 9822),
    SkillZeroOne(R.raw.s_skill_zero_one, 9208),
    SkillZio(R.raw.s_skill_zio, 10086);

}