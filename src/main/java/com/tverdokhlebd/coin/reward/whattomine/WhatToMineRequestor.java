package com.tverdokhlebd.coin.reward.whattomine;

import static com.tverdokhlebd.coin.reward.CoinRewardType.WHAT_TO_MINE;
import static com.tverdokhlebd.coin.reward.whattomine.UrlList.BTC_BASE_HASHRATE;
import static com.tverdokhlebd.coin.reward.whattomine.UrlList.BTC_REQUEST_NAME;
import static com.tverdokhlebd.coin.reward.whattomine.UrlList.ETC_BASE_HASHRATE;
import static com.tverdokhlebd.coin.reward.whattomine.UrlList.ETC_REQUEST_NAME;
import static com.tverdokhlebd.coin.reward.whattomine.UrlList.ETH_BASE_HASHRATE;
import static com.tverdokhlebd.coin.reward.whattomine.UrlList.ETH_REQUEST_NAME;
import static com.tverdokhlebd.coin.reward.whattomine.UrlList.URL_MAP;
import static com.tverdokhlebd.coin.reward.whattomine.UrlList.XMR_BASE_HASHRATE;
import static com.tverdokhlebd.coin.reward.whattomine.UrlList.XMR_REQUEST_NAME;
import static com.tverdokhlebd.coin.reward.whattomine.UrlList.ZEC_BASE_HASHRATE;
import static com.tverdokhlebd.coin.reward.whattomine.UrlList.ZEC_REQUEST_NAME;
import static com.tverdokhlebd.mining.commons.coin.CoinType.BTC;
import static com.tverdokhlebd.mining.commons.coin.CoinType.ETC;
import static com.tverdokhlebd.mining.commons.coin.CoinType.ETH;
import static com.tverdokhlebd.mining.commons.coin.CoinType.XMR;
import static com.tverdokhlebd.mining.commons.coin.CoinType.ZEC;
import static com.tverdokhlebd.mining.commons.http.ErrorCode.API_ERROR;
import static com.tverdokhlebd.mining.commons.http.ErrorCode.PARSE_ERROR;

import java.math.BigDecimal;
import java.util.AbstractMap.SimpleEntry;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tverdokhlebd.coin.reward.CoinRewardCalculator;
import com.tverdokhlebd.coin.reward.CoinRewardCalculator.Builder;
import com.tverdokhlebd.coin.reward.CoinRewardType;
import com.tverdokhlebd.coin.reward.requestor.CoinRewardBaseRequestor;
import com.tverdokhlebd.mining.commons.coin.CoinType;
import com.tverdokhlebd.mining.commons.http.RequestException;
import com.tverdokhlebd.mining.commons.utils.TimeUtils;

import okhttp3.OkHttpClient;

/**
 * WhatToMine coin reward requestor.
 *
 * @author Dmitry Tverdokhleb
 *
 */
public class WhatToMineRequestor extends CoinRewardBaseRequestor {

    /** Endpoints update. */
    private final int endpointsUpdate;
    /** Map of cached coin reward calculators. */
    private static final Map<CoinType, SimpleEntry<CoinRewardCalculator, Date>> CACHED_COIN_REWARD_CALCULATOR_MAP =
            new ConcurrentHashMap<>();

    /**
     * Creates instance.
     *
     * @param httpClient HTTP client
     * @param endpointsUpdate endpoints update
     */
    public WhatToMineRequestor(OkHttpClient httpClient, int endpointsUpdate) {
        super(httpClient);
        this.endpointsUpdate = endpointsUpdate;
    }

    @Override
    public Date getCachedNextUpdate(CoinType coinType) {
        SimpleEntry<CoinRewardCalculator, Date> cachedCoinRewardCalculator = CACHED_COIN_REWARD_CALCULATOR_MAP.get(coinType);
        return cachedCoinRewardCalculator == null ? new Date(0) : cachedCoinRewardCalculator.getValue();
    }

    @Override
    public CoinRewardCalculator getCachedCoinRewardCalculator(CoinType coinType) {
        return CACHED_COIN_REWARD_CALCULATOR_MAP.get(coinType).getKey();
    }

    @Override
    public void setCachedCoinRewardCalculator(CoinType coinType, CoinRewardCalculator coinRewardCalculator) {
        SimpleEntry<CoinRewardCalculator, Date> cachedCoinRewardCalculator = CACHED_COIN_REWARD_CALCULATOR_MAP.get(coinType);
        CACHED_COIN_REWARD_CALCULATOR_MAP.replace(coinType,
                                                  new SimpleEntry<CoinRewardCalculator, Date>(coinRewardCalculator,
                                                                                              cachedCoinRewardCalculator.getValue()));
    }

    @Override
    protected CoinRewardType geCoinRewardType() {
        return WHAT_TO_MINE;
    }

    @Override
    protected List<SimpleEntry<String, String>> getUrlList(CoinType coinType) {
        return URL_MAP.get(coinType);
    }

    @Override
    protected void checkApiError(String responseBody, String requestName) throws RequestException {
        try {
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONArray errors = jsonResponse.optJSONArray("errors");
            if (errors != null) {
                throw new RequestException(API_ERROR, errors.getString(0));
            }
        } catch (JSONException e) {
            throw new RequestException(PARSE_ERROR, e);
        }
    }

    @Override
    protected void parseResponse(String responseBody, String requestName, Builder result) throws RequestException {
        try {
            CoinType coinType = null;
            BigDecimal baseHashrate = null;
            switch (requestName) {
            case BTC_REQUEST_NAME: {
                coinType = BTC;
                baseHashrate = BTC_BASE_HASHRATE;
                break;
            }
            case ETH_REQUEST_NAME: {
                coinType = ETH;
                baseHashrate = ETH_BASE_HASHRATE;
                break;
            }
            case ETC_REQUEST_NAME: {
                coinType = ETC;
                baseHashrate = ETC_BASE_HASHRATE;
                break;
            }
            case XMR_REQUEST_NAME: {
                coinType = XMR;
                baseHashrate = XMR_BASE_HASHRATE;
                break;
            }
            case ZEC_REQUEST_NAME: {
                coinType = ZEC;
                baseHashrate = ZEC_BASE_HASHRATE;
                break;
            }
            }
            JSONObject jsonResponse = new JSONObject(responseBody);
            result.setBaseHashrate(baseHashrate);
            BigDecimal baseRewardPerDay = BigDecimal.valueOf(jsonResponse.getDouble("estimated_rewards"));
            result.setBaseRewardPerDay(baseRewardPerDay);
            Date lastUpdated = new Date(jsonResponse.getLong("timestamp") * 1000);
            Date nextUpdate = TimeUtils.addMinutes(lastUpdated, endpointsUpdate);
            CACHED_COIN_REWARD_CALCULATOR_MAP.put(coinType, new SimpleEntry<CoinRewardCalculator, Date>(null, nextUpdate));
        } catch (JSONException e) {
            throw new RequestException(PARSE_ERROR, e);
        }
    }

}
