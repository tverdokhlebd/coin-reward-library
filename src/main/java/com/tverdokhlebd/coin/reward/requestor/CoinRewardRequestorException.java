package com.tverdokhlebd.coin.reward.requestor;

import com.tverdokhlebd.mining.commons.http.ErrorCode;
import com.tverdokhlebd.mining.commons.http.RequestException;

/**
 * Exception for working with coin reward requestor.
 *
 * @author Dmitry Tverdokhleb
 *
 */
public class CoinRewardRequestorException extends RequestException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4078780829401257645L;

    /**
     * Creates instance.
     *
     * @param e request exception
     */
    public CoinRewardRequestorException(RequestException e) {
        super(e.getErrorCode(), e.getMessage());
    }

    /**
     * Creates instance.
     *
     * @param errorCode error code
     * @param message the detail message
     */
    public CoinRewardRequestorException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    /**
     * Creates instance.
     *
     * @param errorCode error code
     * @param cause the cause
     */
    public CoinRewardRequestorException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

}
