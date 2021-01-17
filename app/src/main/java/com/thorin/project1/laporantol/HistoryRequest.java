package com.thorin.project1.laporantol;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class HistoryRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://databaseprojectlaportol.000webhostapp.com/historyup.php";
    private final Map<String, String> params;

    public HistoryRequest(String kodelaporan, String nik, String namauser, String tanggal_laporan, String shift_laporan,
                          String jmlh_total_bri, String jmlh_trans_bri, String hasil_ht_bri,
                          String jmlh_total_bca, String jmlh_trans_bca, String hasil_ht_bca,
                          String jmlh_total_mandri, String jmlh_trans_mandri, String hasil_ht_mandiri,
                          String hasil_keseluruhan, Response.Listener<String> listener) {

        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("kodelaporan", kodelaporan);
        params.put("nik", nik);
        params.put("namauser", namauser);
        params.put("tanggal_laporan", tanggal_laporan);
        params.put("shift_laporan", shift_laporan);
        params.put("jmlh_total_bri", jmlh_total_bri);
        params.put("jmlh_trans_bri", jmlh_trans_bri);
        params.put("hasil_ht_bri", hasil_ht_bri);
        params.put("jmlh_total_bca", jmlh_total_bca);
        params.put("jmlh_trans_bca", jmlh_trans_bca);
        params.put("hasil_ht_bca", hasil_ht_bca);
        params.put("jmlh_total_mandri", jmlh_total_mandri);
        params.put("jmlh_trans_mandri", jmlh_trans_mandri);
        params.put("hasil_ht_mandiri", hasil_ht_mandiri);
        params.put("hasil_keseluruhan", hasil_keseluruhan);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
