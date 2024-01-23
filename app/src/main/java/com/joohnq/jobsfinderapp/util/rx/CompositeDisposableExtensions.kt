package com.joohnq.jobsfinderapp.util.rx

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

object CompositeDisposableExtensions {
    operator fun CompositeDisposable.plusAssign(disposable: Disposable){
        this.add(disposable)
    }
}