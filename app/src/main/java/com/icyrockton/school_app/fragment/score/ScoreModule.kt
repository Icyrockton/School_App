package com.icyrockton.school_app.fragment.score

import com.icyrockton.school_app.fragment.score.autoevaluation.AutoEvaluationRepository
import com.icyrockton.school_app.fragment.score.autoevaluation.AutoEvaluationViewModel
import com.icyrockton.school_app.fragment.score.cet.CetScoreRepository
import com.icyrockton.school_app.fragment.score.cet.CetScoreViewModel
import com.icyrockton.school_app.fragment.score.daily.DailyScoreRepository
import com.icyrockton.school_app.fragment.score.daily.DailyScoreViewModel
import com.icyrockton.school_app.fragment.score.overview.ScoreOverViewRepository
import com.icyrockton.school_app.fragment.score.overview.ScoreOverViewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import kotlin.math.sign


val scoreModule= module {

    single { ScoreViewModel() }

    single { ScoreOverViewRepository(get()) }
    viewModel { ScoreOverViewViewModel(get()) }

    single { DailyScoreRepository(get()) }
    viewModel { DailyScoreViewModel(get()) }

    single { CetScoreRepository(get()) }
    viewModel { CetScoreViewModel(get()) }

    single { AutoEvaluationRepository(get()) }
    viewModel { AutoEvaluationViewModel(get()) }
}