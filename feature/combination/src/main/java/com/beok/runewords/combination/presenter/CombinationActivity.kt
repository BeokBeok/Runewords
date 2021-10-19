package com.beok.runewords.combination.presenter

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.beok.runewords.common.BundleKeyConstants
import com.beok.runewords.common.model.Rune
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CombinationActivity : AppCompatActivity() {

    private val viewModel by viewModels<CombinationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (intent.extras?.get(BundleKeyConstants.RUNE_NAME) as? Rune)
            ?.let(viewModel::fetchRuneWords)
    }
}
