package com.example.fixnow.utils;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class PersmissionHelper {

        /**
         * This method is responsible for providing a request to user whether he wants to share a
         * certain permission to application.
         *
         * @param appCompatActivity            Activity for which permission is requested
         * @param appCompatPermissionInterface Interface providing onGranted method
         * @param manifest                     Permission to be asked for from Manifest.permission
         */
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
