package com.exalttech.trex.ui.models.json.stats.latency;


public class JSONStatsStream {
    private JSONStatsLatency latency;
    private JSONStatsErrCntrs err_cntrs;

    public JSONStatsLatency getLatency() {
        return latency;
    }
    public void setLatency(JSONStatsLatency latency) {
        this.latency = latency;
    }

    public JSONStatsErrCntrs getErr_cntrs() {
        return err_cntrs;
    }
    public void setErr_cntrs(JSONStatsErrCntrs err_cntrs) {
        this.err_cntrs = err_cntrs;
    }
}