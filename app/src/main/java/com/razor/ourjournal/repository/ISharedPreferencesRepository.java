package com.razor.ourjournal.repository;


public interface ISharedPreferencesRepository {
    boolean hasPartner();

    void setPartnerEmail(String partnerEmail);

    String getPartnerEmail();
}
