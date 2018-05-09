package com.tverdokhlebd.coin.reward;

import java.math.BigDecimal;

import com.tverdokhlebd.mining.commons.coin.CoinType;

/**
 * Estimated rewards for coin according to reported hashrate.
 *
 * @author Dmitry Tverdokhleb
 *
 */
public class CoinReward {

    /** Type of coin. */
    private final CoinType coinType;
    /** Reported hashrate in H/s. */
    private final BigDecimal reportedHashrate;
    /** Reward per hour. */
    private final BigDecimal rewardPerHour;
    /** Reward per day. */
    private final BigDecimal rewardPerDay;
    /** Reward per week. */
    private final BigDecimal rewardPerWeek;
    /** Reward per month. */
    private final BigDecimal rewardPerMonth;
    /** Reward per year. */
    private final BigDecimal rewardPerYear;

    /**
     * Creates instance.
     *
     * @param coinType type of coin
     * @param reportedHashrate reported hashrate in H/s
     * @param rewardPerHour reward per hour
     * @param rewardPerDay reward per day
     * @param rewardPerWeek reward per week
     * @param rewardPerMonth reward per month
     * @param rewardPerYear reward per year
     */
    public CoinReward(CoinType coinType, BigDecimal reportedHashrate, BigDecimal rewardPerHour, BigDecimal rewardPerDay,
            BigDecimal rewardPerWeek,
            BigDecimal rewardPerMonth, BigDecimal rewardPerYear) {
        super();
        this.coinType = coinType;
        this.reportedHashrate = reportedHashrate;
        this.rewardPerHour = rewardPerHour;
        this.rewardPerDay = rewardPerDay;
        this.rewardPerWeek = rewardPerWeek;
        this.rewardPerMonth = rewardPerMonth;
        this.rewardPerYear = rewardPerYear;
    }

    /**
     * Gets coin type.
     *
     * @return coin type
     */
    public CoinType getCoinType() {
        return coinType;
    }

    /**
     * Gets reported hashrate in H/s.
     *
     * @return reported hashrate in H/s
     */
    public BigDecimal getReportedHashrate() {
        return reportedHashrate;
    }

    /**
     * Gets reward per hour.
     *
     * @return reward per hour
     */
    public BigDecimal getRewardPerHour() {
        return rewardPerHour;
    }

    /**
     * Gets reward per day.
     *
     * @return reward per day
     */
    public BigDecimal getRewardPerDay() {
        return rewardPerDay;
    }

    /**
     * Gets reward per week.
     *
     * @return reward per week
     */
    public BigDecimal getRewardPerWeek() {
        return rewardPerWeek;
    }

    /**
     * Gets reward per month.
     *
     * @return reward per month
     */
    public BigDecimal getRewardPerMonth() {
        return rewardPerMonth;
    }

    /**
     * Gets reward per year.
     *
     * @return reward per year
     */
    public BigDecimal getRewardPerYear() {
        return rewardPerYear;
    }

    /**
     * Builder of coin reward.
     *
     * @author Dmitry Tverdokhleb
     *
     */
    public static class Builder {

        /** Type of coin. */
        private CoinType coinType;
        /** Reported hashrate in H/s. */
        private BigDecimal reportedHashrate;
        /** Reward per hour. */
        private BigDecimal rewardPerHour;
        /** Reward per day. */
        private BigDecimal rewardPerDay;
        /** Reward per week. */
        private BigDecimal rewardPerWeek;
        /** Reward per month. */
        private BigDecimal rewardPerMonth;
        /** Reward per year. */
        private BigDecimal rewardPerYear;

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
         * Sets reported hashrate.
         *
         * @param reportedHashrate new reported hashrate
         * @return builder
         */
        public Builder setReportedHashrate(BigDecimal reportedHashrate) {
            this.reportedHashrate = reportedHashrate;
            return this;
        }

        /**
         * Sets reward per hour.
         *
         * @param rewardPerHour new reward per hour
         * @return builder
         */
        public Builder setRewardPerHour(BigDecimal rewardPerHour) {
            this.rewardPerHour = rewardPerHour;
            return this;
        }

        /**
         * Sets reward per day.
         *
         * @param rewardPerDay new reward per day
         * @return builder
         */
        public Builder setRewardPerDay(BigDecimal rewardPerDay) {
            this.rewardPerDay = rewardPerDay;
            return this;
        }

        /**
         * Sets reward per week.
         *
         * @param rewardPerWeek new reward per week
         * @return builder
         */
        public Builder setRewardPerWeek(BigDecimal rewardPerWeek) {
            this.rewardPerWeek = rewardPerWeek;
            return this;
        }

        /**
         * Sets reward per month.
         *
         * @param rewardPerMonth new reward per month
         * @return builder
         */
        public Builder setRewardPerMonth(BigDecimal rewardPerMonth) {
            this.rewardPerMonth = rewardPerMonth;
            return this;
        }

        /**
         * Sets reward per year.
         *
         * @param rewardPerYear new reward per year
         * @return builder
         */
        public Builder setRewardPerYear(BigDecimal rewardPerYear) {
            this.rewardPerYear = rewardPerYear;
            return this;
        }

        /**
         * Builds coin reward.
         *
         * @return coin reward
         */
        public CoinReward build() {
            return new CoinReward(coinType, reportedHashrate, rewardPerHour, rewardPerDay, rewardPerWeek, rewardPerMonth, rewardPerYear);
        }

    }

}
