package com.razor.ourjournal.repository;


import android.os.Bundle;
import android.support.annotation.NonNull;

public interface IRepositoryCallback {

    void onSuccess(@NonNull Bundle bundle);

    void onCancelled(@NonNull Bundle bundle);
}
