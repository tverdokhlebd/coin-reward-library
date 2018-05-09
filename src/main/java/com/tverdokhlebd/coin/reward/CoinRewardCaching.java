package com.tverdokhlebd.coin.reward;

import java.util.Date;

import com.tverdokhlebd.mining.commons.coin.CoinType;

/**
 * Interface for coin reward caching.
 *
 * @author Dmitry Tverdokhleb
 *
 */
public interface CoinRewardCaching {

    /**
     * Gets cached next update.
     *
     * @param coinType type of coin
     * @return cached next update
     */
    Date getCachedNextUpdate(CoinType coinType);

    /**
     * Gets cached coin reward calculator.
     *
     * @param coinType type of coin
     * @return cached coin reward calculator
     */
    CoinRewardCalculator getCachedCoinRewardCalculator(CoinType coinType);

    /**
     * Sets cached coin reward calculator.
     *
     * @param coinType type of coin
     * @param coinRewardCalculator coin reward calculator
     */
    void setCachedCoinRewardCalculator(CoinType coinType, CoinRewardCalculator coinRewardCalculator);

}
