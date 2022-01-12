package at.frank.chuckjokes

import io.reactivex.Scheduler

class RxSchedulers(val subscriptionScheduler: Scheduler, val observerScheduler: Scheduler)