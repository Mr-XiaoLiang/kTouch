package com.lollipop.resource


enum class FaizOption(val icon: Int, val code: String = "", val command: Int = 0) {

    /**
     * Faiz变身
     */
    FaizHeisei(R.drawable.ic_logo, "555"),

    /**
     * 凯撒变身
     */
    KaixaHeisei(R.drawable.ic_logo, "913"),

    /**
     * 德尔塔变身
     */
    DeltaHeisei(R.drawable.ic_logo, "333"),

    /**
     * 天帝变身
     */
    PsygaHeisei(R.drawable.ic_logo, "315"),

    /**
     * 地帝变身
     */
    OrgaHeisei(R.drawable.ic_logo, "000"),

    /**
     * 单发模式
     * Phone Blaster Single Mode
     * 最多12发
     */
    PhoneBlasterSingleMode(R.drawable.ic_logo, "103"),

    /**
     * 连发模式
     * 三连发，最多4次
     * Phone Blaster Burst Mode，Phone Blaster的三连发模式
     */
    PhoneBlasterBurstMode(R.drawable.ic_logo, "106"),

    /**
     * 充能
     * Phone Blaster Charge，让各发射模式下的Phone Blaster再次充填能量。
     */
    PhoneBlasterCharge(R.drawable.ic_logo, "279"),

    /**
     * 召唤巨型载具
     *  呼叫Jet Sliger，输入后会发出“Jet Sliger come closer”的语音，
     *  Jet Sliger会以自动驾驶的方式到达使用者身边。
     */
    JetSligerComeCloser(R.drawable.ic_logo, "3821"),

    /**
     * 巨型载具
     * 喷射滑行者 准备行动
     */
    JetSligerGetIntoAction(R.drawable.ic_logo, "3814"),

    /**
     * 巨型载具
     * 喷射滑行者 起飞
     */
    JetSligerTakeOff(R.drawable.ic_logo, "3846"),


    /**
     * 加速手表
     */
    FaizAxelMode(R.drawable.ic_logo, "Axel"),

    /**
     * 真红大炮
     * Faiz Pointer(faiz光标) Put into motion
     * 功能存疑
     */
    FaizPointer(R.drawable.ic_logo, "5576"),

    /**
     * 真红大炮
     * 深红电风扇
     * 爆裂faiz发动踢技必杀
     * Faiz Pointer(faiz光标)Exceed Charge
     * 让Faiz直接使用Blaster Crimson Smash。
     */
    FaizBlasterCrimsonSmash(R.drawable.ic_logo, "5532"),

    /**
     * 真红大炮
     * Faiz Shot(faiz拳套) Put into motion
     * 功能存疑
     */
    FaizShot(R.drawable.ic_logo, "5276"),

    /**
     * 真红大炮
     * Faiz Shot(faiz拳套)Exceed Charge
     * 让Faiz直接使用Blaster Grand Impact。
     */
    FaizBlasterGrandImpact(R.drawable.ic_logo, "5232"),

    /**
     * 真红大炮
     * Faiz Edge (faiz利刃) Put into motion
     * 功能存疑
     */
    FaizEdge(R.drawable.ic_logo, "5476"),

    /**
     * 真红大炮
     * Faiz Edge (faiz利刃)Exceed Charge
     */
    FaizEdgeExceed(R.drawable.ic_logo, "5432"),

    /**
     * 真红大炮
     * 爆裂形态飞行
     * Faiz Blaster Take Off
     */
    FaizBlasterTakeOff(R.drawable.ic_logo, "5246"),

    /**
     * 真红大炮
     * 发射肩跑
     */
    FaizBlasterDischarge(R.drawable.ic_logo, "5214"),

    /**
     * 真红大炮
     * Blade Mode，Faiz Blaster会变成大剑型的Breaker Mode
     */
    FaizBlasterBladeMode(R.drawable.ic_logo, "143"),

    /**
     * 真红大炮
     * Blaster Mode，Faiz Blaster会变成散弹枪型的Blaster Mode。
     */
    FaizBlasterBlasterMode(R.drawable.ic_logo, "103"),

    /**
     * 真红大炮、Faiz
     * 机动天马，准备行动
     * Auto Vajin Get Into Action
     */
    FaizAutoVajinGetIntoAction(R.drawable.ic_logo, "5814"),

    /**
     * 真红大炮、Faiz
     * 机动天马 靠近
     * AutoVajin Come Closer
     */
    FaizAutoVajinComeCloser(R.drawable.ic_logo, "5821"),

    /**
     * 真红大炮、Faiz
     * 机动天马 起飞
     * Auto Vajin Take Off
     */
    FaizAutoVajinTakeOff(R.drawable.ic_logo, "5846"),

    /**
     * 真红大炮、Faiz
     * 机动天马 战斗模式
     * Auto Vajin Battle Mode
     */
    FaizAutoVajinBattleMode(R.drawable.ic_logo, "5826"),

    /**
     * 真红大炮、Faiz
     * 机动天马 载具形态
     * Auto Vajin Vehicle Mode
     */
    FaizAutoVajinVehicleMode(R.drawable.ic_logo, "5886"),

    /**
     * Kaixa
     * 疑似载具攻击
     */
//    Temp9814(R.drawable.ic_logo, "9814"),

    /**
     * Kaixa
     * 疑似召唤载具
     */
//    Temp9821(R.drawable.ic_logo, "9821"),

    /**
     * Kaixa
     * battle mode
     */
//    Temp9826(R.drawable.ic_logo, "9826"),

    /**
     * Kaixa
     * vehicle mode(战车模式)
     */
//    Temp9886(R.drawable.ic_logo, "9886"),

    /**
     * 真红大炮
     * 圣诞快乐
     */
//    Temp1224(R.drawable.ic_logo, "1224"),

    /**
     * 真红大炮
     * 新年快乐
     */
//    Temp2004(R.drawable.ic_logo, "2004"),

    /**
     * 真红大炮
     * 循环音效模式
     */
//    Temp391(R.drawable.ic_logo, "391"),

}