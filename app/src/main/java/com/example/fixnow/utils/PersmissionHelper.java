package com.example.fixnow.utils;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class PersmissionHelper {
        public void startPermissionRequest(AppCompatActivity appCompatActivity,
                                           PermissionInterface appCompatPermissionInterface
                , String manifest) {
            ActivityResultLauncher<String> requestPermissionLauncher =
                    appCompatActivity
                            .registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                                    appCompatPermissionInterface::onGranted);
            requestPermissionLauncher.launch(manifest);
        }

    }
