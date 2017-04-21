package com.touchmenotapps.bookdemo.dao;

import java.util.Arrays;
import java.util.List;

/**
 * Created by i7 on 20-04-2017.
 */

public class FeeDataFactory {

    public static List<FeePeriod> makeFeeDetails() {
        return Arrays.asList(makeRockGenre(),
                makeJazzGenre(),
                makeClassicGenre(),
                makeSalsaGenre(),
                makeBluegrassGenre());
    }

    public static FeePeriod makeRockGenre() {
        return new FeePeriod("April 2017", makeRockArtists());
    }

    public static List<FeeDetail> makeRockArtists() {
        FeeDetail queen = new FeeDetail("Queen", "20 Jun, 2017", "Tuition Fees", 2000, true);
        FeeDetail styx = new FeeDetail("Styx", "20 Jun, 2017", "Tuition Fees", 2000, false);
        FeeDetail reoSpeedwagon = new FeeDetail("REO Speedwagon", "20 Jun, 2017", "Tuition Fees", 2000, false);
        FeeDetail boston = new FeeDetail("Boston", "20 Jun, 2017", "Tuition Fees", 2000, true);
        return Arrays.asList(queen, styx, reoSpeedwagon, boston);
    }

    public static FeePeriod makeJazzGenre() {
        return new FeePeriod("May 2017", makeJazzArtists());
    }

    public static List<FeeDetail> makeJazzArtists() {
        FeeDetail milesDavis = new FeeDetail("Miles Davis", "20 Jun, 2017", "Tuition Fees", 2000, true);
        FeeDetail ellaFitzgerald = new FeeDetail("Ella Fitzgerald", "20 Jun, 2017", "Tuition Fees", 2000, true);
        FeeDetail billieHoliday = new FeeDetail("Billie Holiday", "20 Jun, 2017", "Tuition Fees", 2000, false);
        return Arrays.asList(milesDavis, ellaFitzgerald, billieHoliday);
    }

    public static FeePeriod makeClassicGenre() {
        return new FeePeriod("June 2017", makeClassicArtists());
    }

    public static List<FeeDetail> makeClassicArtists() {
        FeeDetail beethoven = new FeeDetail("Ludwig van Beethoven", "20 Jun, 2017", "Tuition Fees", 2000, false);
        FeeDetail bach = new FeeDetail("Johann Sebastian Bach", "20 Jun, 2017", "Tuition Fees", 2000, true);
        FeeDetail brahms = new FeeDetail("Johannes Brahms", "20 Jun, 2017", "Tuition Fees", 2000, false);
        FeeDetail puccini = new FeeDetail("Giacomo Puccini", "20 Jun, 2017", "Tuition Fees", 2000, false);
        return Arrays.asList(beethoven, bach, brahms, puccini);
    }

    public static FeePeriod makeSalsaGenre() {
        return new FeePeriod("July 2017", makeSalsaArtists());
    }

    public static List<FeeDetail> makeSalsaArtists() {
        FeeDetail hectorLavoe = new FeeDetail("Hector Lavoe", "20 Jun, 2017", "Tuition Fees", 2000, true);
        FeeDetail celiaCruz = new FeeDetail("Celia Cruz", "20 Jun, 2017", "Tuition Fees", 2000, false);
        FeeDetail willieColon = new FeeDetail("Willie Colon", "20 Jun, 2017", "Tuition Fees", 2000, false);
        FeeDetail marcAnthony = new FeeDetail("Marc Anthony", "20 Jun, 2017", "Tuition Fees", 2000, false);
        return Arrays.asList(hectorLavoe, celiaCruz, willieColon, marcAnthony);
    }

    public static FeePeriod makeBluegrassGenre() {
        return new FeePeriod("August 2017", makeBluegrassArtists());
    }

    public static List<FeeDetail> makeBluegrassArtists() {
        FeeDetail billMonroe = new FeeDetail("Bill Monroe", "20 Jun, 2017", "Tuition Fees", 2000, false);
        FeeDetail earlScruggs = new FeeDetail("Earl Scruggs", "20 Jun, 2017", "Tuition Fees", 2000, false);
        FeeDetail osborneBrothers = new FeeDetail("Osborne Brothers", "20 Jun, 2017", "Tuition Fees", 2000, true);
        FeeDetail johnHartford = new FeeDetail("John Hartford", "20 Jun, 2017", "Tuition Fees", 2000, false);
        return Arrays.asList(billMonroe, earlScruggs, osborneBrothers, johnHartford);
    }

}