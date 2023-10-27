package datum.app.clinic.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum ToothNumber {
    N11(11), N12(12), N13(13), N14(14), N15(15), N16(16), N17(17), N18(18),
    N21(21), N22(22), N23(23), N24(24), N25(25), N26(26), N27(27), N28(28),
    N31(31), N32(32), N33(33), N34(34), N35(35), N36(36), N37(37), N38(38),
    N41(41), N42(42), N43(43), N44(44), N45(45), N46(46), N47(47), N48(48),

    N51(51), N52(52), N53(53), N54(54), N55(55),
    N61(61), N62(62), N63(63), N64(64), N65(65),
    N71(71), N72(72), N73(73), N74(74), N75(75),
    N81(81), N82(82), N83(83), N84(84), N85(85),
    ;

    private Integer i;

    ToothNumber(int i) {
        this.i = i;
    }
}
