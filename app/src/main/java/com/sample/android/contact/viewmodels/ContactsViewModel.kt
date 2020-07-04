package com.sample.android.contact.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.android.contact.repository.ContactsRepository
import com.sample.android.contact.domain.Contact
import com.sample.android.contact.util.Resource
import javax.inject.Inject

class ContactsViewModel(repository: ContactsRepository) : ViewModel() {

    private val _liveData = repository.liveData
    val liveData: LiveData<Resource<List<Contact>>>
        get() = _liveData

    /**
     * Factory for constructing ContactsViewModel with parameter
     */
    class Factory @Inject constructor(
            private val repository: ContactsRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ContactsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}