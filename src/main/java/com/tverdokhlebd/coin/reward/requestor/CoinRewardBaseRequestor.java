package com.tverdokhlebd.coin.reward.requestor;

import java.math.BigDecimal;
import java.util.AbstractMap.SimpleEntry;
import java.util.Date;
import java.util.List;

import com.tverdokhlebd.coin.reward.CoinReward;
import com.tverdokhlebd.coin.reward.CoinRewardCaching;
import com.tverdokhlebd.coin.reward.CoinRewardCalculator;
import com.tverdokhlebd.coin.reward.CoinRewardCalculator.Builder;
import com.tverdokhlebd.coin.reward.CoinRewardType;
import com.tverdokhlebd.mining.commons.coin.CoinType;
import com.tverdokhlebd.mining.commons.http.BaseRequestor;
import com.tverdokhlebd.mining.commons.http.RequestException;

import okhttp3.OkHttpClient;

/**
 * Coin reward base HTTP requestor.
 *
 * @author Dmitry Tverdokhleb
 *
 */
public abstract class CoinRewardBaseRequestor extends BaseRequestor<CoinRewardCalculator.Builder>
        implements CoinRewardRequestor, CoinRewardCaching {

    /**
     * Creates instance.
     *
     * @param httpClient HTTP client
     */
    protected CoinRewardBaseRequestor(OkHttpClient httpClient) {
        super(httpClient);
    }

    @Override
    public CoinReward requestCoinReward(CoinType coinType, BigDecimal reportedHashrate) throws CoinRewardRequestorException {
        if (geCoinRewardType().getCoinTypeList().indexOf(coinType) == -1) {
            throw new IllegalArgumentException(coinType.name() + " is not supported");
        }
        try {
            if (new Date().after(getCachedNextUpdate(coinType))) {
                CoinRewardCalculator.Builder coinRewardCalculatorBuilder = new Builder();
                coinRewardCalculatorBuilder.setCoinType(coinType);
                List<SimpleEntry<String, String>> urlList = getUrlList(coinType);
                for (int i = 0; i < urlList.size(); i++) {
                    SimpleEntry<String, String> urlEntry = urlList.get(i);
                    String requestName = urlEntry.getKey();
                    String preparedUrl = urlEntry.getValue();
                    super.request(preparedUrl, requestName, coinRewardCalculatorBuilder);
                }
                setCachedCoinRewardCalculator(coinType, coinRewardCalculatorBuilder.build());
            }
            CoinRewardCalculator coinRewardCalculator = getCachedCoinRewardCalculator(coinType);
            return coinRewardCalculator.calculateRewards(reportedHashrate);
        } catch (RequestException e) {
            throw new CoinRewardRequestorException(e);
        }
    }

    /**
     * Gets coin reward type.
     *
     * @return coin reward type
     */
    protected abstract CoinRewardType geCoinRewardType();

    /**
     * Gets list of urls.
     *
     * @param coinType type of coin
     * @return list of urls
     */
    protected abstract List<SimpleEntry<String, String>> getUrlList(CoinType coinType);

}
