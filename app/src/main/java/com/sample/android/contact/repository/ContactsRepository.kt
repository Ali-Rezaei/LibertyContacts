package com.sample.android.contact.repository

import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sample.android.contact.domain.Contact
import com.sample.android.contact.util.ContactUtil
import com.sample.android.contact.util.ContactUtil.PROJECTION
import com.sample.android.contact.util.Resource
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactsRepository @Inject constructor(
        private val context: Context) {

    private val compositeDisposable = CompositeDisposable()

    private val _liveData = MutableLiveData<Resource<List<Contact>>>()
    val liveData: LiveData<Resource<List<Contact>>>
        get() = _liveData

    fun loadContacts() {
        _liveData.value = Resource.Loading()
        loadContactsProvider()
    }

    fun refreshContacts() {
        _liveData.value = Resource.Reloading()
        loadContactsProvider()
    }

    private fun loadContactsProvider() {
        val cursor = context.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PROJECTION,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE UNICODE ASC")
        Observable.create(ObservableOnSubscribe<List<Contact>>
        { emitter -> emitter.onNext(ContactUtil.getContacts(cursor, context)) })
                .doOnComplete { cursor?.close() }
                .doFinally { compositeDisposable.clear() }
                .subscribe {
                    _liveData.postValue(Resource.Success(it))
                }.also { compositeDisposable.add(it) }
    }
}