package com.tverdokhlebd.coin.reward;

import static com.tverdokhlebd.mining.commons.http.ErrorCode.API_ERROR;
import static com.tverdokhlebd.mining.commons.http.ErrorCode.HTTP_ERROR;
import static com.tverdokhlebd.mining.commons.http.ErrorCode.PARSE_ERROR;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.tverdokhlebd.coin.reward.requestor.CoinRewardRequestor;
import com.tverdokhlebd.coin.reward.requestor.CoinRewardRequestorException;
import com.tverdokhlebd.coin.reward.requestor.CoinRewardRequestorFactory;
import com.tverdokhlebd.mining.commons.coin.CoinType;
import com.tverdokhlebd.mining.commons.utils.HttpClientUtils;

import okhttp3.OkHttpClient;

/**
 * Utils for tests.
 *
 * @author Dmitry Tverdokhleb
 *
 */
public class Utils {

    /**
     * Tests coin reward.
     *
     * @param httpClient HTTP client
     * @param coinRewardType type of coin reward
     * @param coinType type of coin
     * @param reportedHashrate reportedHashrate reported hashrate in H/s
     * @param rewardPerHour reward per hour
     * @param rewardPerDay reward per day
     * @param rewardPerWeek reward per week
     * @param rewardPerYear reward per year
     * @throws CoinRewardRequestorException if there is any error in coin reward requesting
     */
    public static void testCoinInfo(OkHttpClient httpClient, CoinRewardType coinRewardType, CoinType coinType, BigDecimal reportedHashrate,
            BigDecimal rewardPerHour, BigDecimal rewardPerDay, BigDecimal rewardPerWeek, BigDecimal rewardPerMonth,
            BigDecimal rewardPerYear)
            throws CoinRewardRequestorException {
        CoinRewardRequestor coinRewardRequestor = CoinRewardRequestorFactory.create(coinRewardType, httpClient, 0);
        CoinReward coinReward = coinRewardRequestor.requestCoinReward(coinType, reportedHashrate);
        assertEquals(coinType, coinReward.getCoinType());
        assertEquals(reportedHashrate, coinReward.getReportedHashrate());
        assertEquals(rewardPerHour, coinReward.getRewardPerHour());
        assertEquals(rewardPerDay, coinReward.getRewardPerDay());
        assertEquals(rewardPerWeek, coinReward.getRewardPerWeek());
        assertEquals(rewardPerMonth, coinReward.getRewardPerMonth());
        assertEquals(rewardPerYear, coinReward.getRewardPerYear());
    }

    /**
     * Tests API error.
     *
     * @param httpClient HTTP client
     * @param coinRewardType type of coin reward
     * @param coinType type of coin
     * @param expectedErrorMessage expected error message
     * @throws CoinRewardRequestorException if there is any error in coin reward requesting
     */
    public static void testApiError(OkHttpClient httpClient, CoinRewardType coinRewardType, CoinType coinType, String expectedErrorMessage)
            throws CoinRewardRequestorException {
        CoinRewardRequestor coinRewardRequestor = CoinRewardRequestorFactory.create(coinRewardType, httpClient, 0);
        try {
            coinRewardRequestor.requestCoinReward(coinType, BigDecimal.valueOf(0));
        } catch (CoinRewardRequestorException e) {
            assertEquals(API_ERROR, e.getErrorCode());
            assertEquals(expectedErrorMessage, e.getMessage());
            throw e;
        }
    }

    /**
     * Tests internal server error.
     *
     * @param coinRewardType type of coin reward
     * @param coinType type of coin
     * @throws CoinRewardRequestorException if there is any error in coin reward requesting
     */
    public static void testInternalServerError(CoinRewardType coinRewardType, CoinType coinType) throws CoinRewardRequestorException {
        OkHttpClient httpClient = HttpClientUtils.createHttpClient(new JSONObject().toString(), 500);
        CoinRewardRequestor coinRewardRequestor = CoinRewardRequestorFactory.create(coinRewardType, httpClient, 0);
        try {
            coinRewardRequestor.requestCoinReward(coinType, BigDecimal.valueOf(0));
        } catch (CoinRewardRequestorException e) {
            assertEquals(HTTP_ERROR, e.getErrorCode());
            throw e;
        }
    }

    /**
     * Tests empty response.
     *
     * @param coinRewardType type of coin reward
     * @param coinType type of coin
     * @throws CoinRewardRequestorException if there is any error in coin reward requesting
     */
    public static void testEmptyResponse(CoinRewardType coinRewardType, CoinType coinType) throws CoinRewardRequestorException {
        OkHttpClient httpClient = HttpClientUtils.createHttpClient(new JSONObject().toString(), 200);
        CoinRewardRequestor coinRewardRequestor = CoinRewardRequestorFactory.create(coinRewardType, httpClient, 0);
        try {
            coinRewardRequestor.requestCoinReward(coinType, BigDecimal.valueOf(0));
        } catch (CoinRewardRequestorException e) {
            assertEquals(PARSE_ERROR, e.getErrorCode());
            throw e;
        }
    }

    /**
     * Tests unsupported coin.
     *
     * @param coinRewardType type of coin reward
     * @param coinType type of coin
     * @throws CoinRewardRequestorException if there is any error in coin reward requesting
     */
    public static void testUnsupportedCoin(CoinRewardType coinRewardType, CoinType coinType) throws CoinRewardRequestorException {
        OkHttpClient httpClient = HttpClientUtils.createHttpClient(new JSONObject().toString(), 200);
        CoinRewardRequestor coinRewardRequestor = CoinRewardRequestorFactory.create(coinRewardType, httpClient, 0);
        try {
            coinRewardRequestor.requestCoinReward(coinType, BigDecimal.valueOf(0));
        } catch (IllegalArgumentException e) {
            assertEquals(coinType.name() + " is not supported", e.getMessage());
            throw e;
        }
    }

}
