package com.tverdokhlebd.coin.reward.requestor;

import java.math.BigDecimal;

import com.tverdokhlebd.coin.reward.CoinReward;
import com.tverdokhlebd.mining.commons.coin.CoinType;

/**
 * Interface for requesting coin reward.
 *
 * @author Dmitry Tverdokhleb
 *
 */
public interface CoinRewardRequestor {

    /**
     * Requests coin reward.
     *
     * @param coinType type of coin
     * @param reportedHashrate reported hashrate in H/s
     * @return coin reward
     * @throws CoinRewardRequestorException if there is any error in coin reward requesting
     */
    CoinReward requestCoinReward(CoinType coinType, BigDecimal reportedHashrate) throws CoinRewardRequestorException;

}
