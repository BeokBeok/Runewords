package com.beok.runewords

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.beok.runewords.home.BuildConfig
import com.beok.runewords.home.R
import com.beok.runewords.inapp.InAppUpdateState
import com.beok.runewords.inapp.InAppUpdateViewModel
import com.beok.runewords.navigation.RuneWordsNavHost
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.review.ReviewManagerFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class RuneWordsActivity : ComponentActivity() {

    private val inAppUpdateViewModel by viewModels<InAppUpdateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setupScreenAd()
        refreshAppUpdateType()
        observeInAppUpdate()
    }

    override fun onResume() {
        super.onResume()

        if (inAppUpdateViewModel.appUpdateType == AppUpdateType.IMMEDIATE) {
            inAppUpdateViewModel.forceUpdate()
        }
    }

    private fun setupScreenAd() {
        InterstitialAd.load(
            this,
            getString(
                if (BuildConfig.DEBUG) {
                    R.string.test_admob_screen_app_key
                } else {
                    R.string.admob_screen_app_key
                }
            ),
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    setContent()
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    ad.show(this@RuneWordsActivity)
                    setContent()
                }
            }
        )
    }

    private fun setContent() {
        setContent {
            MaterialTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    RuneWordsNavHost(showReviewWriteForm = ::showReviewWriteForm)
                }
            }
        }
    }

    private fun refreshAppUpdateType() {
        inAppUpdateViewModel.refreshAppUpdateType(
            version = packageManager.getPackageInfo(packageName, 0).versionName
        )
    }

    private fun observeInAppUpdate() {
        inAppUpdateViewModel.state.observe(this) { state ->
            when (state) {
                InAppUpdateState.None,
                InAppUpdateState.Impossible -> Unit
                is InAppUpdateState.Possible -> {
                    inAppUpdateViewModel.registerForHome(
                        appUpdateInfo = state.info,
                        target = this
                    )
                }
            }
        }
    }

    private fun showReviewWriteForm() {
        val reviewManager = ReviewManagerFactory.create(this)
        reviewManager
            .requestReviewFlow()
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                reviewManager
                    .launchReviewFlow(this, it.result)
                    .addOnCompleteListener { }
            }
    }
}
