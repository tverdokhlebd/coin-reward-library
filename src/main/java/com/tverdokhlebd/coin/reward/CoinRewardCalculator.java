package com.tverdokhlebd.coin.reward;

import static com.tverdokhlebd.mining.commons.utils.TimeUtils.DAYS_IN_MONTH;
import static com.tverdokhlebd.mining.commons.utils.TimeUtils.DAYS_IN_WEEK;
import static com.tverdokhlebd.mining.commons.utils.TimeUtils.DAYS_IN_YEAR;
import static com.tverdokhlebd.mining.commons.utils.TimeUtils.HOURS_IN_DAY;
import static java.math.RoundingMode.DOWN;

import java.math.BigDecimal;

import com.tverdokhlebd.mining.commons.coin.CoinType;

/**
 * Coin reward calculator for specific reported hashrate.
 *
 * @author Dmitry Tverdokhleb
 *
 */
public class CoinRewardCalculator {

    /** Type of coin. */
    private final CoinType coinType;
    /** Base hashrate in H/s. */
    private final BigDecimal baseHashrate;
    /** Base reward per day. */
    private final BigDecimal baseRewardPerDay;

    /**
     * Creates instance.
     *
     * @param coinType type of coin
     * @param baseHashrate base hashrate in H/s
     * @param baseRewardPerDay base reward per day
     */
    public CoinRewardCalculator(CoinType coinType, BigDecimal baseHashrate, BigDecimal baseRewardPerDay) {
        super();
        this.coinType = coinType;
        this.baseHashrate = baseHashrate;
        this.baseRewardPerDay = baseRewardPerDay;
    }

    /**
     * Calculates estimated coin rewards according to reported hashrate.
     *
     * @param reportedHashrate reported hashrate in H/s
     * @return coin reward
     */
    public CoinReward calculateRewards(BigDecimal reportedHashrate) {
        CoinReward.Builder builder = new CoinReward.Builder();
        BigDecimal calculatedRewardPerDay = reportedHashrate.multiply(baseRewardPerDay).divide(baseHashrate, DOWN);
        builder.setCoinType(coinType)
               .setReportedHashrate(reportedHashrate)
               .setRewardPerHour(calculatedRewardPerDay.divide(HOURS_IN_DAY, DOWN))
               .setRewardPerDay(calculatedRewardPerDay)
               .setRewardPerWeek(calculatedRewardPerDay.multiply(DAYS_IN_WEEK))
               .setRewardPerMonth(calculatedRewardPerDay.multiply(DAYS_IN_MONTH))
               .setRewardPerYear(calculatedRewardPerDay.multiply(DAYS_IN_YEAR));
        return builder.build();
    }

    /**
     * Builder of coin reward calculator.
     *
     * @author Dmitry Tverdokhleb
     *
     */
    public static class Builder {

        /** Type of coin. */
        private CoinType coinType;
        /** Base hashrate in H/s. */
        private BigDecimal baseHashrate;
        /** Base reward per day. */
        private BigDecimal baseRewardPerDay;

        /**
         * Creates instance.
         */
        public Builder() {
            super();
        }

        /**
         * Sets coin type.
         *
         * @param coinType new coin type
         * @return builder
         */
        public Builder setCoinType(CoinType coinType) {
            this.coinType = coinType;
            return this;
        }

        /**
         * Sets base hashrate.
         *
         * @param baseHashrate new base hashrate
         * @return builder
         */
        public Builder setBaseHashrate(BigDecimal baseHashrate) {
            this.baseHashrate = baseHashrate;
            return this;
        }

        /**
         * Sets base reward per day.
         *
         * @param baseRewardPerDay new base reward per day
         * @return builder
         */
        public Builder setBaseRewardPerDay(BigDecimal baseRewardPerDay) {
            this.baseRewardPerDay = baseRewardPerDay;
            return this;
        }

        /**
         * Builds coin reward calculator.
         *
         * @return coin reward calculator
         */
        public CoinRewardCalculator build() {
            return new CoinRewardCalculator(coinType, baseHashrate, baseRewardPerDay);
        }

    }

}
