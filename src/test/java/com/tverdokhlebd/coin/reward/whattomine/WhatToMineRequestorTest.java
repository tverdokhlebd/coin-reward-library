package com.tverdokhlebd.coin.reward.whattomine;

import static com.tverdokhlebd.coin.reward.CoinRewardType.WHAT_TO_MINE;
import static com.tverdokhlebd.mining.commons.coin.CoinType.BCH;
import static com.tverdokhlebd.mining.commons.coin.CoinType.BTC;

import java.math.BigDecimal;

import org.json.JSONObject;
import org.junit.Test;

import com.tverdokhlebd.coin.reward.Utils;
import com.tverdokhlebd.coin.reward.requestor.CoinRewardRequestorException;
import com.tverdokhlebd.mining.commons.utils.HttpClientUtils;

import okhttp3.OkHttpClient;

/**
 * Tests of WhatToMine coin reward requestor.
 *
 * @author Dmitry Tverdokhleb
 *
 */
public class WhatToMineRequestorTest {

    @Test
    public void testCoinInfo() throws CoinRewardRequestorException {
        JSONObject response = new JSONObject("{  \n" +
                "  \"id\":1,\n" +
                "  \"name\":\"Bitcoin\",\n" +
                "  \"tag\":\"BTC\",\n" +
                "  \"algorithm\":\"SHA-256\",\n" +
                "  \"block_time\":\"564.0\",\n" +
                "  \"block_reward\":12.7031,\n" +
                "  \"block_reward24\":12.7241159663866,\n" +
                "  \"block_reward3\":12.7312580999067,\n" +
                "  \"block_reward7\":12.7439783372504,\n" +
                "  \"last_block\":521964,\n" +
                "  \"difficulty\":4022059196164.0,\n" +
                "  \"difficulty24\":4022059196164.0,\n" +
                "  \"difficulty3\":4022059196164.0,\n" +
                "  \"difficulty7\":4022059196164.0,\n" +
                "  \"nethash\":30628745939894379432,\n" +
                "  \"exchange_rate\":9259.8,\n" +
                "  \"exchange_rate24\":9191.3956616052,\n" +
                "  \"exchange_rate3\":9284.96001220173,\n" +
                "  \"exchange_rate7\":9490.45024959021,\n" +
                "  \"exchange_rate_vol\":23077.49332256,\n" +
                "  \"exchange_rate_curr\":\"BTC\",\n" +
                "  \"market_cap\":\"$157,643,807,713\",\n" +
                "  \"pool_fee\":\"0.000000\",\n" +
                "  \"estimated_rewards\":\"0.000889\",\n" +
                "  \"btc_revenue\":\"0.00088949\",\n" +
                "  \"revenue\":\"$8.24\",\n" +
                "  \"cost\":\"$3.29\",\n" +
                "  \"profit\":\"$4.95\",\n" +
                "  \"status\":\"Active\",\n" +
                "  \"lagging\":false,\n" +
                "  \"timestamp\":1525899632\n" +
                "}");
        OkHttpClient httpClient = HttpClientUtils.createHttpClient(response.toString(), 200);
        BigDecimal reportedHashrate = BigDecimal.valueOf(14000000000000L);
        BigDecimal rewardPerHour = BigDecimal.valueOf(0.000037);
        BigDecimal rewardPerDay = BigDecimal.valueOf(0.000889);
        BigDecimal rewardPerWeek = BigDecimal.valueOf(0.006223);
        BigDecimal rewardPerMonth = new BigDecimal("0.026670");
        BigDecimal rewardPerYear = BigDecimal.valueOf(0.324485);
        Utils.testCoinInfo(httpClient,
                           WHAT_TO_MINE,
                           BTC,
                           reportedHashrate,
                           rewardPerHour,
                           rewardPerDay,
                           rewardPerWeek,
                           rewardPerMonth,
                           rewardPerYear);
    }

    @Test(expected = CoinRewardRequestorException.class)
    public void testApiError() throws CoinRewardRequestorException {
        JSONObject response = new JSONObject("{  \n" +
                "  \"errors\":[  \n" +
                "    \"Could not find active coin with id 0\"\n" +
                "  ]\n" +
                "}");
        OkHttpClient httpClient = HttpClientUtils.createHttpClient(response.toString(), 200);
        Utils.testApiError(httpClient, WHAT_TO_MINE, BTC, "Could not find active coin with id 0");
    }

    @Test(expected = CoinRewardRequestorException.class)
    public void testInternalServerError() throws CoinRewardRequestorException {
        Utils.testInternalServerError(WHAT_TO_MINE, BTC);
    }

    @Test(expected = CoinRewardRequestorException.class)
    public void testEmptyResponse() throws CoinRewardRequestorException {
        Utils.testEmptyResponse(WHAT_TO_MINE, BTC);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnsupportedCoin() throws CoinRewardRequestorException {
        Utils.testUnsupportedCoin(WHAT_TO_MINE, BCH);
    }

}
