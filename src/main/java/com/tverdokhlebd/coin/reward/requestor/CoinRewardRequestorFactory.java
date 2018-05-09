package com.tverdokhlebd.coin.reward.requestor;

import com.tverdokhlebd.coin.reward.CoinRewardType;
import com.tverdokhlebd.coin.reward.whattomine.WhatToMineRequestor;
import com.tverdokhlebd.mining.commons.http.HttpClientFactory;

import okhttp3.OkHttpClient;

/**
 * Factory for creating coin reward requestor.
 *
 * @author Dmitry Tverdokhleb
 *
 */
public class CoinRewardRequestorFactory {

    /** Endpoints update. */
    private static final int ENDPOINTS_UPDATE = 4;

    /**
     * Creates coin reward requestor.
     *
     * @param coinRewardType coin reward type
     * @return coin reward requestor
     */
    public static CoinRewardRequestor create(CoinRewardType coinRewardType) {
        OkHttpClient httpClient = HttpClientFactory.create();
        return create(coinRewardType, httpClient);
    }

    /**
     * Creates coin reward requestor.
     *
     * @param coinRewardType coin reward type
     * @param httpClient HTTP client
     * @return coin reward requestor
     */
    public static CoinRewardRequestor create(CoinRewardType coinRewardType, OkHttpClient httpClient) {
        return create(coinRewardType, httpClient, ENDPOINTS_UPDATE);
    }

    /**
     * Creates coin reward requestor.
     *
     * @param coinRewardType coin reward type
     * @param httpClient HTTP client
     * @param endpointsUpdate endpoints update
     * @return coin reward requestor
     */
    public static CoinRewardRequestor create(CoinRewardType coinRewardType, OkHttpClient httpClient, int endpointsUpdate) {
        switch (coinRewardType) {
        case WHAT_TO_MINE: {
            return new WhatToMineRequestor(httpClient, endpointsUpdate);
        }
        default:
            throw new IllegalArgumentException(coinRewardType.name());
        }
    }

}
